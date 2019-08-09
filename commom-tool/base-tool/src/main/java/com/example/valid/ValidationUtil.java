package com.example.valid;

import com.example.constant.enums.SystemCodeEnum;
import com.example.exception.ValidException;
import com.example.utils.StringUtil;
import com.sun.istack.internal.NotNull;
import org.springframework.util.NumberUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.SmartValidator;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author 袁康云
 * @Title ValidationUtil
 * @Description: 验证处理工具类
 * @Date 2018/6/29 21:17
 */
public final class ValidationUtil {
    private ValidationUtil() {
    }

    /**
     * 判断
     * @param <T>
     * @param compareValue
     * @param enumClass
     * @param function
     * @return
     */
    public static <T extends Enum<T>, V> boolean containsEnum(V compareValue, Class<T> enumClass, BiFunction<T, V, Boolean> function) {
        EnumSet<T> enumSet = EnumSet.allOf(enumClass);
        Iterator<T> iterator = enumSet.iterator();
        while (iterator.hasNext()) {
            T next = iterator.next();
            if (function.apply(next, compareValue)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断枚举是否存在
     * @param compareValue 判断值
     * @param enumClass 枚举类对象
     * @param tip 错误提示
     * @param function
     * @param <T> 枚举
     * @param <V> 判断值
     */
    public static <T extends Enum<T>, V> void assertEnum(@NotNull V compareValue, Class<T> enumClass, String tip, BiFunction<T, V, Boolean> function) {
        boolean b = containsEnum(compareValue, enumClass, function);
        assertTrue(b, tip);
    }

    /**
     * 处理校验异常信息:若存在多个异常,则以以下格式提示
     * 信息1|信息2|信息3
     * @param bindingResult
     */
    public static void handleBindingResult(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            String errorMessage = fieldErrors.stream().
                    map(FieldError::getDefaultMessage).
                    collect(Collectors.joining("|"));
            throwParamException(errorMessage);
        }
    }

    /**
     * 校验 value 是否 在 assertValues 内
     * @param tip 错误提示
     * @param value 判断值
     * @param assertValues 校验值
     * @param <T>
     */
    public static <T> void assertIn(String tip, T value, T... assertValues) {
        if (assertValues == null) {
            assertTrue(value == null, tip);
            return;
        }

        if (!Stream.of(assertValues).collect(Collectors.toList()).contains(value)) {
            throwParamException(tip);
        }
    }

    /**
     * 校验是否为空
     *
     * @param value 校验值
     * @param tip
     */
    public static void assertNotBlank(String value, String tip) {
        if (!StringUtil.isNotEmpty(value)) {
            throwParamException(tip);
        }
    }

    /**
     * 抛出一个参数校验异常
     *
     * @param tip
     */
    private static void throwParamException(String tip) {
        throw new ValidException(SystemCodeEnum.ERROR.getCode(), tip);
    }

    /**
     * 校验最小取值
     * @param val 校验值
     * @param assertVal 最小值
     * @param tip
     */
    public static void assertMinValue(Number val, Number assertVal, String tip, Object... params) {
        assertNotNull(val, tip);
        assertNotNull(assertVal, tip);
        BigDecimal one = NumberUtils.convertNumberToTargetClass(val, BigDecimal.class);
        BigDecimal another = NumberUtils.convertNumberToTargetClass(assertVal, BigDecimal.class);

        if (one.compareTo(another) < 0) {
            throwParamException(String.format(tip, params));
        }
    }

    /**
     * 校验取值是否在某范围
     *
     * @param val 校验值
     * @param assertMinVal 最小值
     * @param assertMaxVal 最大值
     * @param tip
     */
    public static void assertRangeValue(Integer val, int assertMinVal, int assertMaxVal, String tip) {
        if (val == null || val < assertMinVal || val > assertMaxVal) {
            throwParamException(tip);
        }
    }

    /**
     * 校验字符串长度
     *
     * @param val 校验值
     * @param length 长度
     * @param tip
     */
    public static void assertLength(String val, int length, String tip) {
        if (val == null || val.trim().length() > length) {
            throwParamException(tip);
        }
    }

    /**
     * 校验数字类型ID
     *
     * @param obj
     * @param tip
     */
    public static void assertNumericalId(Object obj, String tip) {
        if (obj != null) {
            try {
                long num = Long.valueOf(obj.toString());
                if (num > 0) {
                    return;
                }
            } catch (Exception e) {
            }
        }
        throwParamException(tip);
    }

    /**
     * 校验a是否大于b
     * @param small 较小数
     * @param large 较大数
     * @param tip 提示
     */
    public static void assertALessB(Object small, Object large, String tip) {
        if (small != null && large != null) {
            try {
                long lSmall = Long.valueOf(small.toString());
                long lLarge = Long.valueOf(large.toString());
                if (lSmall < lLarge) {
                    return;
                }
            } catch (Exception e) {
            }
        }
        throwParamException(tip);
    }

    /**
     * 检查对象是否为null
     * @param object
     * @param tip
     */
    public static void assertNotNull(Object object, String tip, Object... params) {
        if (object == null || StringUtil.isEmpty(object)) {
            throwParamException(String.format(tip, params));
        }
    }

    /**
     * 校验要比较的对象是否相等
     * 若为原始类型或者封装类型
     * @param tip 错误提示
     * @param objects 要比较的对象
     */
    public static void assertNotEquals(String tip, Object... objects) {
        assertEqualsOrNotEquals(false, tip, objects);
    }

    /**
     * 校验要比较的对象是否相等
     * 若为原始类型或者封装类型
     * @param tip 错误提示
     * @param objects 要比较的对象
     */
    public static void assertEquals(String tip, Object... objects) {
        assertEqualsOrNotEquals(true, tip, objects);
    }


    private static void assertEqualsOrNotEquals(boolean isEqualsOperate, String tip, Object... objects) {
        for (int i = 0; i < objects.length; i++) {
            for (int j = i + 1; j < objects.length; j++) {
                Object obj1 = objects[i];
                Object obj2 = objects[j];
                if (judgeType(obj1) && judgeType(obj2)) { // 数字比较
                    boolean match = isEqualsOperate ? Double.valueOf(obj1.toString()).compareTo(Double.valueOf(obj2.toString())) != 0 :
                            Double.valueOf(obj1.toString()).compareTo(Double.valueOf(obj2.toString())) == 0;
                    if (match) {
                        throwParamException(tip);
                    }
                } else {
                    boolean match = isEqualsOperate ? !obj1.equals(obj2) : obj1.equals(obj2);
                    if (match) { // 对象比较
                        throwParamException(tip);
                    }
                }
            }
        }
    }

    /**
     * 判断是否为数字类型
     * @param obj
     * @return
     */
    private static boolean judgeType(Object obj) {
        return obj instanceof Number;
    }

    /**
     * 校验是否为true,若为false则抛出校验异常
     * @param predict
     * @param tip
     */
    public static void assertTrue(boolean predict, String tip, Object... params) {
        if (!predict) {
            throwParamException(String.format(tip,params));
        }
    }

    /**
     * 校验是否false 若为true则抛出校验异常
     * @param predicate
     * @param tip
     */
    public static void assertFalse(boolean predicate, String tip) {
        if (predicate) {
            throwParamException(tip);
        }
    }

    /**
     * 若obj不为空则抛异常
     * @param obj
     * @param tip
     */
    public static void assertNull(Object obj, String tip) {
        if (obj != null) {
            throwParamException(tip);
        }
    }

    /**
     * 使用Spring校验器手动校验
     * @param validator 校验器
     * @param t 校验对象
     * @param <T>
     */
    public static <T> void validate(SmartValidator validator, T t) {
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(t, t.getClass().getName());
        validator.validate(t, bindingResult);
        handleBindingResult(bindingResult);
    }

    /**
     * @Description: mybatis通用判空方法
     * @param  object
     * @return boolean
     * @author Alex sun
     * @date 2019/5/30 17:23
     */
    public static boolean mybatisCheckNull(Object object){
        if (object == null){
            return false;
        }
        if (object instanceof String){
            if (((String)object).trim().length() == 0){
                return false;
            }
        }else if (object instanceof Integer){
            if ((Integer)object == 0){
                return false;
            }
        }else if (object instanceof Short){
            if (((Short) object) == null) {
                return false;
            }
        }else if (object instanceof Long){
            if (((Long) object) == null || ((Long) object) == 0) {
                return false;
            }
        }else if (object instanceof Collection){
            if (((Collection)object).isEmpty()){
                return false;
            }
        }else if (object instanceof Map){
            if (((Map)object).isEmpty()){
                return false;
            }
        }else if (object.getClass().isArray()){
            if (((Object[])object).length == 0){
                return false;
            }
        }else {
            return true;
        }
        return true;
    }
}

