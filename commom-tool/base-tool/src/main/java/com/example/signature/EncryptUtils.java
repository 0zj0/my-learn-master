package com.example.signature;

import java.security.MessageDigest;


/* *
 * 功能描述:
 * 加密帮助类
 * @author : wjs
 * @date : 2019/7/25 11:27
 * @Version : 1.0
 **/
public class EncryptUtils {

    /**
     * MD5 加密
     * @param inStr
     * @return
     */
    public static String md5(String inStr) {
        if (inStr == null) {
            return null;
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++) {
            byteArray[i] = (byte) charArray[i];
        }
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }
}
