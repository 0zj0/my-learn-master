package com.example.http;

import com.example.json.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import java.nio.charset.Charset;
import java.util.Map;

/**
 * @Auther: huangsenming
 * @Date: 2019/8/8 11:46
 * @Description: http请求工具类
 */
@Slf4j
public class HttpGetUtils {
    //默认编码
    public static String DEFAULT_ENCODING = "utf-8";

    /**
     * @param url   请求urL
     * @param clazz
     * @Author: huangsm on 2019/8/8 15:18
     * @Description:发送get请求
     */
    public static <T> T getRequest(String url, Class<T> clazz) throws Exception {
        return getRequest(url, null, null, clazz);
    }

    public static String getRequest(String url) throws Exception {
        return getRequest(url, null, null, String.class);
    }

    /**
     * @param url    请求urL
     * @param clazz
     * @param params
     * @Author: huangsm on 2019/8/8 15:18
     * @Description:发送get请求
     */
    public static <T> T getRequest(String url, Map<String, Object> params, Class<T> clazz) throws Exception {
        return getRequest(url, null, params, clazz);
    }

    public static String getRequest(String url, Map<String, Object> params) throws Exception {
        return getRequest(url, null, params);
    }

    public static String getRequest(String url, Map<String, String> headers,
                                     Map<String, Object> params) throws Exception {
        return getRequest(url,headers,params,String.class);
    }
    /**
     * @param url     请求url
     * @param headers 请求头
     * @param params  请求参数
     * @param clazz   返回实体类型
     * @Author: huangsm on 2019/8/8 11:56
     * @Description: 发送get请求
     */
    public static <T> T getRequest(String url, Map<String, String> headers,
                                    Map<String, Object> params, Class<T> clazz) throws Exception {
        //创建一个默认的httpClient
        CloseableHttpClient httpClient = HttpClients.createDefault();

        String getUrl = url;
        //插入请求参数
        StringBuilder paramStr = null;
        if (params != null && params.size() > 0) {
            paramStr = new StringBuilder("?");
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                paramStr.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
            paramStr.substring(0, paramStr.length() -1);
        }
        getUrl = getUrl + paramStr;

        HttpGet httpGet = new HttpGet(getUrl);
        //插入请求头数据
        if (headers != null && headers.size() > 0) {
            headers.forEach((key, value) -> {
                httpGet.addHeader(new BasicHeader(key, value));
            });
        }

        //开始发送请求
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                return JsonUtils.parseObject(EntityUtils.toString(entity, Charset.forName(DEFAULT_ENCODING)), clazz);
            }
        } catch (Exception e) {
            log.error("请求异常 ", e);
            throw e;
        } finally {
            if (response != null) {
                response.close();
            }
            if (httpClient != null) {
                httpClient.close();
            }
        }
        return null;
    }

}
