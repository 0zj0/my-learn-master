package com.example.utils;

import java.util.regex.Pattern;

/**
 * @author zhangjie
 * @date 2019/12/30 14:33
 */
public class Integer2BinaryUtil {

    /**
     * 10 进制 转 2 进制
     * 步骤：
     * 1.取原码
     * 2.负数获取反码- 原码取反
     * 3.负数获取补码- 反码加1
     * @param value
     * @return
     */
    public static String integer2BinaryString(int value){
        if(value == 0){
            return "0";
        }

        //标志位，正式为0， 负数为1
        boolean flag = value > 0;

        //计算数字 -- 取绝对值
        int num = flag ? value : -value;

        //存储转换值--倒叙的
        StringBuilder sb = new StringBuilder();
        while (num > 0){
            int temp = num % 2;
            //>>：带符号右移。正数右移高位补0，负数右移高位补1。
            //>>>：无符号右移。无论是正数还是负数，高位通通补0。
            num = num >> 1;
            sb.append(temp);
        }

        //负数需要补0,int 位数有32位
        int len = sb.length();
        if(!flag && len < 32){
            for(int i = 0; i < 32 - len; i++){
                sb.append("0");
            }
        }

        //获取正数的二进制码
        String binaryString = sb.reverse().toString();

        if(!flag){
            //取反
            binaryString = getReverseCode(binaryString);
            //加1
            binaryString = binaryAdd1(binaryString);
        }

        return binaryString;
    }

    /**
     * 获取binaryString 的反码
     * @param binaryString
     * @return
     */
    private static String getReverseCode(String binaryString){
        StringBuilder sb = new StringBuilder();
        char[] chars = binaryString.toCharArray();
        for(char c : chars){
            if('1' == c){
                sb.append("0");
            }else {
                sb.append("1");
            }
        }
        return sb.toString();
    }

    /**
     * 二进制 + 1
     * @param binaryString
     * @return
     */
    private static String binaryAdd1(String binaryString){
        char[] chars = binaryString.toCharArray();
        int length =chars.length;

        StringBuilder sb = new StringBuilder();
        //进位
        boolean carry = true;
        int i = 0;
        while (carry && i < length){
            if(chars[length - i - 1] == '0'){
                carry = false;
                sb.append("1");
            }else{
                sb.append("0");
            }
            i ++;
        }

        //防止溢出
        if(carry){
            System.out.println("数据溢出");
            return "0";
        }
        //不用进位的直接补位
        while (i < length){
            sb.append(chars[length - i - 1]);
            i ++;
        }
        //倒叙 输出
        return sb.reverse().toString();
    }

    /**
     * 二进制 转 10 进制
     * @param code
     * @return
     */
    public static int binary2Integer(String code){
        if(!isBinaryNumeric(code)){
            System.out.println("不为二进制数");
            return 0;
        }
        //1. 判断是否为负数
        char[] chars = code.toCharArray();
        boolean negative = chars.length == 32 && chars[0] == '1';

        //负数

        return 0;
    }

    private static Pattern BINARY_NUMBER_PATTERN = Pattern.compile("[0-1]*");

    public static boolean isBinaryNumeric(String str) {
        return BINARY_NUMBER_PATTERN.matcher(str).matches();
    }

    public static void main(String[] args) {
    System.out.println(integer2BinaryString(53));
    System.out.println(integer2BinaryString(-53));

    System.out.println("******************");
    System.out.println(Integer.toBinaryString(53));
    System.out.println(Integer.toBinaryString(-53));
    }

}
