package com.example.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangjie
 * @date 2020/8/7 15:07
 */
@Slf4j
public class ObjectMapperUtil {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        //反序列化的时候如果多了其他属性,不抛出异常
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //序列化的时候序列对象的所有属性
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        //属性为null的转换
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //如果是空对象的时候,不抛异常
        OBJECT_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    /**
     * 字符串 转 对象
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T readValue(String json, Class<T> clazz){
        T t = null;
        try {
            t = OBJECT_MAPPER.readValue(json,clazz);
        } catch (IOException e) {
            log.error(String.format("字符串%s解析为对象%s出错:{}", json, clazz.getName()), e);
        }
        return t;
    }

    /**
     * obj 转 对象
     * @param obj
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T readValueByObject(Object obj, Class<T> clazz){
        T t = null;
        try {
            String json = OBJECT_MAPPER.writeValueAsString(obj);
            t = OBJECT_MAPPER.readValue(json,clazz);
        } catch (IOException e) {
            log.error(String.format("对象%s解析为对象%s出错:{}", obj.toString(), clazz.getName()), e);
        }
        return t;
    }

    /**
     * obj 转 list
     * @param obj
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> readValueToList(Object obj, Class<T> clazz){
        JavaType javaType = OBJECT_MAPPER.getTypeFactory().constructParametricType(ArrayList.class, clazz);
        List<T> list = new ArrayList<>();
        try {
            String json = OBJECT_MAPPER.writeValueAsString(obj);
            list = OBJECT_MAPPER.readValue(json,javaType);
        } catch (IOException e) {
            log.error(String.format("对象%s解析为list%s出错:{}", obj.toString(), clazz.getName()), e);
        }
        return list;
    }
}
