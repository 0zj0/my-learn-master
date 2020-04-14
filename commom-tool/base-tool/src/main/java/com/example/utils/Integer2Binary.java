package com.example.utils;

/**
 * @author zhangjie
 * @date 2019/12/30 14:26
 */
public class Integer2Binary {

    /**
     * 不使用 BigInteger，实现十进制转二进制，同时，支持负数的进制转换
     * 负整数转换为二进制要点：取反加一
     * 1. 将该负数对应的正数先转换成二进制
     * 2. 将转换后的二进制数取反，然后加 1
     * @param integer
     * @return
     */
    public String integer2BinaryString(int integer) {
        if(integer == 0) {    // 如果输入数字为0，则直接返回
            return "0";
        }

        boolean negative = integer < 0 ? true : false;    // 标志位，用于记录输入是否是负数
        int num;    // 用于进行计算的数字，大于0
        if (negative) {
            num = -integer;
        }else {
            num = integer;
        }

        StringBuilder sb = new StringBuilder();    // 存储取余过程中产生的余数
        while(num > 0) {
            int tmp = num % 2;    // 取余
            num = num >>> 1;    // 算术右移
            sb.append(tmp);
        }

        int len = sb.length();
        if (negative && len < 64) {    // 以 64 位系统为例，不足的长度需要全部补0
            for (int i = 0; i < 64 - len; i++) {
                sb.append('0');
            }
        }
        String binaryString = sb.reverse().toString();   // 获取 num 对应的二进制码

        if(negative) {
            binaryString = dealNegativeBinary(binaryString);
        }


        return binaryString;
    }

    /**
     * 对负数的二进制数，进行安位取反
     *
     * @param binaryString
     * @return
     */
    public String dealNegativeBinary(String binaryString) {

        StringBuilder sb = new StringBuilder();
        char[] chars = binaryString.toCharArray();
        for ( char c : chars) {
            if (c == '1') {
                sb.append('0');
            }else {
                sb.append('1');
            }
        }
        return negativeAdd1(sb.toString());
    }

    /**
     * 对按位取反后的值执行加一操作
     *
     * @param binaryString
     * @return
     */
    public String negativeAdd1(String binaryString) {
        StringBuilder sb = new StringBuilder(binaryString);
        String calculateString = sb.reverse().toString();    // 对需要进行加一操作的二进制字符串取反，从低位开始操作
        char[] arrays = calculateString.toCharArray();
        if (arrays[0] == '0') {    // 如果最低位是 0，直接加一后返回
            arrays[0] = '1';
            StringBuilder sBuilder = new StringBuilder(new String(arrays));
            return sBuilder.reverse().toString();
        }
        // 需要进行循环进位计算
        boolean append = true;    // 进位标志
        StringBuilder stringBuilder = new StringBuilder('0');    // 用于添加执行加一后的二进制元素
        int i = 1;
        for (; i < arrays.length; i++) {
            if (arrays[i] == '1') {
                stringBuilder.append('0');
            }else {
                stringBuilder.append('1');
                append = false;
                break;
            }
        }

        if (append && i == arrays.length) {    // 如果到了最后仍存在进位，则发生了溢出，否则把剩余的值 append 到 stringBuilder
            System.out.println("数据溢出");
            return "0";
        }else {
            for (; i < arrays.length; i++) {
                stringBuilder.append(arrays[i]);
            }
        }

        return stringBuilder.reverse().toString();
    }


    /**
     * 测试
     */
    public static void main(String[] args) {
        Integer2Binary integer2Binary = new Integer2Binary();

        int a = 53;
        int b = -53;
        System.out.println(integer2Binary.integer2BinaryString(a));
        System.out.println(integer2Binary.integer2BinaryString(b));

        System.out.println("******************");
        System.out.println(Integer.toBinaryString(a));
        System.out.println(Integer.toBinaryString(b));
    }
}
