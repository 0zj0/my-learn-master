package com.example.signature;

import sun.misc.BASE64Decoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author 罗俊生
 * @date 2019/6/28 14:55
 */
public class RSABuilder {

    /**
     * 私钥
     */
    private static RSAPrivateKey privateKey;

    /**
     * 公钥
     */
    private static RSAPublicKey publicKey;

    /**
     * 字节数据转字符串专用集合
     */
    private static final char[] HEX_CHAR = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};


    /**
     * 获取私钥
     *
     * @return 当前的私钥对象
     */
    public static RSAPrivateKey getPrivateKey() {
        return privateKey;
    }

    /**
     * 获取公钥
     *
     * @return 当前的公钥对象
     */
    public static RSAPublicKey getPublicKey() {
        return publicKey;
    }

    /**
     * 随机生成密钥对
     */
    public static void genKeyPair() {
        KeyPairGenerator keyPairGen = null;
        try {
            keyPairGen = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        keyPairGen.initialize(1024, new SecureRandom());
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSABuilder.privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSABuilder.publicKey = (RSAPublicKey) keyPair.getPublic();
    }

    public static void main(String[] args) {
        genKeyPair();

    }

    /**
     * 从文件中输入流中加载公钥
     *
     * @param in 公钥输入流
     * @throws Exception 加载公钥时产生的异常
     */
    public static void loadPublicKey(InputStream in) throws Exception {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String readLine = null;
            StringBuilder sb = new StringBuilder();
            while ((readLine = br.readLine()) != null) {
                if (readLine.charAt(0) == '-') {
                    continue;
                } else {
                    sb.append(readLine);
                    sb.append('\r');
                }
            }
            loadPublicKey(sb.toString());
        } catch (IOException e) {
            throw new Exception("公钥数据流读取错误");
        } catch (NullPointerException e) {
            throw new Exception("公钥输入流为空");
        }
    }


    /**
     * 从字符串中加载公钥
     *
     * @param publicKeyStr 公钥数据字符串
     * @throws Exception 加载公钥时产生的异常
     */
    public static void loadPublicKey(String publicKeyStr) throws Exception {
        try {
            BASE64Decoder base64Decoder = new BASE64Decoder();
            byte[] buffer = base64Decoder.decodeBuffer(publicKeyStr);

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            RSABuilder.publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("公钥非法");
        } catch (IOException e) {
            throw new Exception("公钥数据内容读取错误");
        } catch (NullPointerException e) {
            throw new Exception("公钥数据为空");
        }
    }

    public static void loadPublicPem(InputStream in) throws Exception {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(stream2ByteArray(in));
            RSABuilder.publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("公钥非法");
        } catch (IOException e) {
            throw new Exception("公钥数据内容读取错误");
        } catch (NullPointerException e) {
            throw new Exception("公钥数据为空");
        }
    }

    /**
     * 从文件中加载私钥
     *
     * @param in 私钥文件输入流
     * @return 是否成功
     * @throws Exception
     */
    public static void loadPrivateKey(InputStream in) throws Exception {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String readLine = null;
            StringBuilder sb = new StringBuilder();
            while ((readLine = br.readLine()) != null) {
                if (readLine.charAt(0) == '-') {
                    continue;
                } else {
                    sb.append(readLine);
                    sb.append('\r');
                }
            }
            loadPrivateKey(sb.toString());
        } catch (IOException e) {
            throw new Exception("私钥数据读取错误");
        } catch (NullPointerException e) {
            throw new Exception("私钥输入流为空");
        }
    }

    /**
     * 从字符串中加载私钥
     *
     * @param privateKeyStr 私钥数据字符串
     * @throws Exception 加载私钥时产生的异常
     */
    public static void loadPrivateKey(String privateKeyStr) throws Exception {
        try {
            BASE64Decoder base64Decoder = new BASE64Decoder();
            byte[] buffer = base64Decoder.decodeBuffer(privateKeyStr);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSABuilder.privateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("私钥非法");
        } catch (IOException e) {
            throw new Exception("私钥数据内容读取错误");
        } catch (NullPointerException e) {
            throw new Exception("私钥数据为空");
        }
    }

    public static void loadPrivatePem(InputStream in) throws Exception {
        try {
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(stream2ByteArray(in));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSABuilder.privateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("私钥非法");
        } catch (IOException e) {
            throw new Exception("私钥数据内容读取错误");
        } catch (NullPointerException e) {
            throw new Exception("私钥数据为空");
        }
    }

    public static byte[] getSignature(byte[] cipherText) {
        try {
            Signature sig = Signature.getInstance("MD5withRSA");
            sig.initSign(RSABuilder.privateKey);
            sig.update(cipherText);
            return sig.sign();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean verifySignature(byte[] cipherText, byte[] signature) {
        try {
            Signature sig = Signature.getInstance("MD5withRSA");
            sig.initVerify(RSABuilder.publicKey);
            sig.update(cipherText);
            return sig.verify(signature);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getSHA1Signature(byte[] data) throws Exception {
        Signature sig = Signature.getInstance("SHA1withRSA");
        sig.initSign(RSABuilder.privateKey);
        sig.update(data);
        return bytesToHex(sig.sign());
    }

    public static boolean verifySHA1Signature(byte[] data, byte[] signature) throws Exception {
        Signature sig = Signature.getInstance("SHA1withRSA");
        sig.initVerify(RSABuilder.publicKey);
        sig.update(data);
        return sig.verify(hexTobyte(signature));

    }

    public static String getSHA512Signature(byte[] data) throws Exception {
        Signature sig = Signature.getInstance("SHA512withRSA");
        sig.initSign(RSABuilder.privateKey);
        sig.update(data);
        return bytesToHex(sig.sign());
    }

    public static boolean verifySHA512Signature(byte[] data, byte[] signature) throws Exception {
        Signature sig = Signature.getInstance("SHA512withRSA");
        sig.initVerify(RSABuilder.publicKey);
        sig.update(data);
        return sig.verify(hexTobyte(signature));

    }

    public static String getSHA256Signature(byte[] data) throws Exception {
        Signature sig = Signature.getInstance("SHA256withRSA");
        sig.initSign(RSABuilder.privateKey);
        sig.update(data);
        return bytesToHex(sig.sign());
    }

    public static boolean verifySHA256Signature(byte[] data, byte[] signature) throws Exception {
        Signature sig = Signature.getInstance("SHA256withRSA");
        sig.initVerify(RSABuilder.publicKey);
        sig.update(data);
        return sig.verify(hexTobyte(signature));

    }

    /**
     * 加密过程
     *
     * @param publicKey     公钥
     * @param plainTextData 明文数据
     * @return
     * @throws Exception 加密过程中的异常信息
     */
    public static byte[] encrypt(RSAPublicKey publicKey, byte[] plainTextData) throws Exception {
        if (publicKey == null) {
            throw new Exception("加密公钥为空, 请设置");
        }
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] output = cipher.doFinal(plainTextData);
            return output;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此加密算法");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("加密公钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("明文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("明文数据已损坏");
        }
    }

    /**
     * 解密过程
     *
     * @param privateKey 私钥
     * @param cipherData 密文数据
     * @return 明文
     * @throws Exception 解密过程中的异常信息
     */
    public static byte[] decrypt(RSAPrivateKey privateKey, byte[] cipherData) throws Exception {
        if (privateKey == null) {
            throw new Exception("解密私钥为空, 请设置");
        }
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] output = cipher.doFinal(cipherData);
            return output;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此解密算法");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("解密私钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("密文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("密文数据已损坏");
        }
    }


    /**
     * 字节数据转十六进制字符串
     *
     * @param data 输入数据
     * @return 十六进制内容
     */
    public static String bytesToHex(byte[] data) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            //取出字节的高四位 作为索引得到相应的十六进制标识符 注意无符号右移
            stringBuilder.append(HEX_CHAR[(data[i] & 0xf0) >>> 4]);
            //取出字节的低四位 作为索引得到相应的十六进制标识符
            stringBuilder.append(HEX_CHAR[(data[i] & 0x0f)]);
        }
        return stringBuilder.toString().toUpperCase();
    }

    /**
     * 十六进制转十进制字节数据
     *
     * @param hexByte 输入16进制字节数据
     * @return 十进制字节数据
     */
    public static byte[] hexTobyte(byte[] hexByte) throws Exception {
        if ((hexByte.length % 2) != 0) {
            throw new Exception("input length is not 2 times");
        }
        byte[] b2 = new byte[hexByte.length / 2];
        for (int n = 0; n < hexByte.length; n += 2) {
            String pair = new String(hexByte, n, 2);
            //十六进制，两个字母可以转换为10进制的一个字节

            b2[n / 2] = (byte) Integer.parseInt(pair, 16);
        }
        return b2;
    }

    public static byte[] stream2ByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }

    /**
     * 从pem文件中获取key值字符串
     *
     * @param keyFilePath 文件路径
     * @return java.lang.String
     */
    public static String getKeyFromFile(String keyFilePath) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(keyFilePath));
            String readLine = null;
            while ((readLine = br.readLine()) != null) {
                if (readLine.charAt(0) == '-') {
                    continue;
                } else {
                    sb.append(readLine);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

}
