package com.example.signature;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * java实现AES256加密解密
 * key.length = 64
 * iv.length = 32
 *
 * @author 罗俊生
 * @date 2019/8/7 14:07
 */
public class AES256Util {
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    public static final String AES_IV = "00000000000000000000000000000000";

    /**
     * 数据加密
     *
     * @param srcData
     * @param key
     * @param iv
     * @return
     */
    public static String encrypt(String srcData, String key, String iv) {
        byte[] keyByte = hexStringToByte(key);
        SecretKeySpec keySpec = new SecretKeySpec(keyByte, "AES");
        Cipher cipher;
        String encodeBase64String = null;
        try {
            cipher = Cipher.getInstance(ALGORITHM);
            byte[] ivByte = hexStringToByte(iv);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(ivByte));
            byte[] encData = cipher.doFinal(srcData.getBytes());
            encodeBase64String = Base64.encodeBase64String(encData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encodeBase64String;
    }

    public static byte[] hexStringToByte(String hexString) {
        if (hexString.length() % 2 != 0) {
            return null;
        }
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] bytes = new byte[length];
        for (int i = 0, j = 0; j < length; i++, j++) {
            String step = "" + hexChars[i++] + hexChars[i];
            int k = Integer.parseInt(step, 16);
            bytes[j] = new Integer(k).byteValue();
        }
        return bytes;
    }

    /**
     * 数据解密
     *
     * @param encDataStr
     * @param key
     * @param iv
     * @return
     */
    public static String decrypt(String encDataStr, String key, String iv) {
        byte[] keyByte = hexStringToByte(key);
        byte[] encData = Base64.decodeBase64(encDataStr);
        SecretKeySpec keySpec = new SecretKeySpec(keyByte, "AES");
        Cipher cipher;
        byte[] decbbdt = null;
        try {
            cipher = Cipher.getInstance(ALGORITHM);
            byte[] ivByte = hexStringToByte(iv);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(ivByte));
            decbbdt = cipher.doFinal(encData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String(decbbdt);
    }

}
