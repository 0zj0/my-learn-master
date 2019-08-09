package com.example.signature;


import org.springframework.util.StringUtils;

import java.util.*;


/* *
 * 功能描述:
 * 签名工具类
 * @param null 1
 * @return :
 * @author : wjs
 * @date : 2019/7/25 11:25
 * @Version : 1.0
 **/
public class SignUtil {



	//秘钥名称
	static final  String secretName = "secret";

	/* *
	 * 功能描述:
	 * MD5加密，生成签名秘钥
	 * @author : wjs
	 * @date : 2019/7/25 11:29
	 * @Version : 1.0
	 **/
	public static String sign(Map<String, String> params, String secret){
		//返回空说明签名失败
		if(params == null || params.size() <= 0 || StringUtils.isEmpty(secret)){ return null;}
		// 第一步：把参数按Key的字母顺序排序
		Collection<String> keyset= params.keySet();
		//对key值排序过程
		List<String> list = new ArrayList<String>(keyset); 
		Collections.sort(list);
		// 第二步：把所有参数名和参数值串在一起
        StringBuilder query = new StringBuilder();
        for(String key : list){
            String value = params.get(key);
            if (!StringUtils.isEmpty(value)){
                query.append(key).append("=").append(value).append("&");
            }
        }
        if( query.length()>0 ){
        	query.append(secretName + "=" + secret);
        }
        // 第三部：使用md5运算
        return EncryptUtils.md5(query.toString());
	}
}
