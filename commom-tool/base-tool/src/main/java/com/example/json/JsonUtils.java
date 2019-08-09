package com.example.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * 类与json字符串互相转换的工具类
 * @version 1.0
 * @author zdh
 * @date 2018/4/19 17:49
 */
public final class JsonUtils {

    private static Logger logger = LoggerFactory.getLogger(JsonUtils.class);
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 对象转JSON字符串，会按照对象字段顺序格式化为json字符串
     *
     * @param obj 要转换的对象
     * @return
     */
    public static String toJsonString(Object obj) {
        try {
            return MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            logger.error(String.format("对象%s转为json字符串出错", obj == null ? null : obj.getClass().getName()), e);
        }
        return null;
    }

    /**
     * 对象转JSON字符串，对象格式化为json字符串后，字段顺序是乱的
     * @param obj
     * @return
     */
    public static String toJsonStringByJsonObject(Object obj) {
        try {
            return JSONObject.toJSONString(obj);
        }catch (Exception e){
            logger.error(String.format("对象%s转为json字符串出错", obj == null ? null : obj.getClass().getName()), e);
        }
        return null;
    }

    /**
     * json字符串转对象，json的字段与对象的字段需要完全匹配
     * 如果jsonText多出了字段，解析为对象时会出错
     * <p>
     * 目前使用场景为redis读取缓存，防止缓存类字段发生变化
     * 其他情况不要使用该接口
     *
     * @param jsonString
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T parseObject(String jsonString, Class<T> clazz) {
        try {
            T t = JSONObject.parseObject(jsonString, clazz);
            return t;
        } catch (Exception e) {
            logger.error(String.format("字符串%s解析为对象%s出错", jsonString, clazz.getName()), e);
        }
        return null;
    }

    /**
     * json 转 List<T>
     *
     * @param jsonString
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> parseList(String jsonString, Class<T> clazz) {
        try {
            List<T> list = JSONArray.parseArray(jsonString, clazz);
            return list;
        } catch (Exception e) {
            logger.error(String.format("字符串%s解析为对象List<%s>出错", jsonString, clazz.getName()), e);
        }
        return null;
    }

    /**
     * json字符串转对象，json的字段与对象的字段需要完全匹配：json可以缺少字段，但不能比对象T多出字段
     * 如果jsonText多出了字段，解析为对象时会出错
     * <p>
     * 目前使用场景为redis读取缓存，防止缓存类字段发生变化
     * 其他情况不要使用该接口
     *
     * @param jsonString
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T parseObjectFullMatch(String jsonString, Class<T> clazz) {

        try {
            return MAPPER.readValue(jsonString, clazz);
        } catch (IOException e) {
            logger.error(String.format("字符串%s解析为对象%s出错", jsonString, clazz.getName()), e);
        }
        return null;
    }

    /**
     * json字符串转List<对象>，json的字段与对象的字段需要完全匹配：json可以缺少字段，但不能比对象T多出字段
     * 如果json字符串多出了字段，解析为对象时会出错
     * <p>
     * 目前使用场景为redis读取缓存，防止缓存类字段发生变化
     * 其他情况不要使用该接口
     *
     * @param jsonString
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> parseListFullMatch(String jsonString, Class<T> clazz) {

        try {
            JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, clazz);
            List<T> list = MAPPER.readValue(jsonString, javaType);
            return list;
        } catch (IOException e) {
            logger.error(String.format("字符串%s解析为对象List<%s>出错", jsonString, clazz.getName()), e);
        }
        return null;
    }

    /**
     * string转换成Json
     *
     * @param jsonString
     * @return
     * @author lyy
     * @date 2019-06-24
     */
    public static JSONObject parseJsonObject(String jsonString) {
        try {
            return JSON.parseObject(jsonString);
        } catch (Exception e) {
            logger.error("{},解析为json出错", jsonString, e);
        }
        return null;
    }
}
