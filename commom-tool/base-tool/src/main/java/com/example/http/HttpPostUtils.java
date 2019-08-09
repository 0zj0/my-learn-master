package com.example.http;

import com.doyd.core.json.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Auther: huangsenming
 * @Date: 2019/8/8 11:46
 * @Description: http请求工具类
 */
@Slf4j
public class HttpPostUtils {
    //默认编码
    public static String DEFAULT_ENCODING = "utf-8";

    /**
     * @param url   请求urL
     * @param clazz
     * @Author: huangsm on 2019/8/8 15:18
     * @Description:发送post请求
     */
    public static <T> T postRequest(String url, Class<T> clazz) throws Exception {
        return postRequest(url, null, null, clazz);
    }

    public static String postRequest(String url) throws Exception {
        return postRequest(url, null, null, String.class);
    }

    /**
     * @param url    请求urL
     * @param clazz
     * @param params
     * @Author: huangsm on 2019/8/8 15:18
     * @Description:发送post请求
     */
    public static <T> T postRequest(String url, Map<String, Object> params, Class<T> clazz) throws Exception {
        return postRequest(url, null, params, clazz);
    }

    public static String postRequest(String url, Map<String, Object> params) throws Exception {
        return postRequest(url, null, params);
    }

    public static String postRequest(String url, Map<String, String> headers,
                                     Map<String, Object> params) throws Exception {
        return postRequest(url,headers,params,String.class);
    }
    /**
     * @param url     请求url
     * @param headers 请求头
     * @param params  请求参数
     * @param clazz   返回实体类型
     * @Author: huangsm on 2019/8/8 11:56
     * @Description: 发送post请求
     */
    public static <T> T postRequest(String url, Map<String, String> headers,
                                    Map<String, Object> params, Class<T> clazz) throws Exception {
        //创建一个默认的httpClient
        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpPost httpPost = new HttpPost(url);
        //插入请求头数据
        if (headers != null && headers.size() > 0) {
            headers.forEach((key, value) -> {
                httpPost.addHeader(new BasicHeader(key, value));
            });
        }
        //插入请求参数
        if (params != null && params.size() > 0) {
            List<NameValuePair> pairList = new ArrayList<NameValuePair>(params.size());
            params.forEach((key, value) -> {
                pairList.add(new BasicNameValuePair(key, value.toString()));
            });
            httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName(DEFAULT_ENCODING)));
        }
        //开始发送请求
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                return JsonUtils.parseObject(EntityUtils.toString(entity, Charset.forName(DEFAULT_ENCODING)), clazz);
            }
        } catch (Exception e) {
            log.error("请求异常", e);
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
