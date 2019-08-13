package com.example.config.datasource;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.core.enums.IEnum;
import com.baomidou.mybatisplus.core.toolkit.EnumUtils;
import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 自定义枚举属性转换器，用来相互转换mysql的字段为枚举类型
 * @author zhangjie
 * @date 2019/8/12 16:13
 */
public class MybatisEnumTypeHandler<E extends Enum<?>> extends BaseTypeHandler<Enum<?>> {

    private static final Logger logger = LoggerFactory.getLogger(MybatisEnumTypeHandler.class);

    private static final Map<Class<?>, Method> TABLE_METHOD_OF_ENUM_TYPES = new ConcurrentHashMap<>();

    private final Class<E> type;

    private final Method method;

    public MybatisEnumTypeHandler(Class<E> type) {
        if(type == null){
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.type = type;
        if(IEnum.class.isAssignableFrom(type)){
            try {
                this.method = type.getMethod("getValue");
            } catch (NoSuchMethodException e) {
                throw new IllegalArgumentException(String.format("NoSuchMethod getValue() in Class: %s.", type.getName()));
            }
        } else {
            this.method = TABLE_METHOD_OF_ENUM_TYPES.computeIfAbsent(type,k -> {
                Field field = dealEnumType(this.type).orElseThrow(() ->
                        new IllegalArgumentException(String.format("Could not find @EnumValue in Class: %s.", type.getName())));
                return ReflectionKit.getMethod(this.type,field);
            });
        }
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Enum<?> anEnum, JdbcType jdbcType) throws SQLException {
        try {
            this.method.setAccessible(true);
            if (jdbcType == null) {
                ps.setObject(i, this.method.invoke(anEnum));
            } else {
                // see r3589
                ps.setObject(i, this.method.invoke(anEnum), jdbcType.TYPE_CODE);
            }
        } catch (IllegalAccessException e) {
            logger.error("unrecognized jdbcType, failed to set StringValue for type={}" , anEnum);
        } catch (InvocationTargetException e) {
            throw ExceptionUtils.mpe("Error: NoSuchMethod in %s.  Cause:", e, this.type.getName());
        }
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        if (null == rs.getObject(columnName) && rs.wasNull()) {
            return null;
        }
        return EnumUtils.valueOf(this.type, rs.getObject(columnName), this.method);
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        if (null == rs.getObject(columnIndex) && rs.wasNull()) {
            return null;
        }
        return EnumUtils.valueOf(this.type, rs.getObject(columnIndex), this.method);
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        if (null == cs.getObject(columnIndex) && cs.wasNull()) {
            return null;
        }
        return EnumUtils.valueOf(this.type, cs.getObject(columnIndex), this.method);
    }

    private static Optional<Field> dealEnumType(Class<?> clazz){
        return clazz.isEnum() ? Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(EnumValue.class))
                .findFirst() : Optional.empty();
    }
}
