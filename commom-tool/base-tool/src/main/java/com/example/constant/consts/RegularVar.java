package com.example.constant.consts;

/**
 * @ClassName RegularVar
 * @Description 一些通用的正则表达式常量
 * @Author 李光华
 * @Date 2019/8/7 15:44
 **/
public class RegularVar {

    /**
     * 自然数（含0）
     */
    public static final String NATURAL_NUMBER="^(0|[1-9][0-9]*)$";
    /**
     * 浮点数（不含0）- 保留2位小数
     */
    public static String FLOAT_NUMBER = "^[0-9]+(.[0-9]{2})?$";
    /**
     * 时间 23:23:59
     */
    public static final String TIME = "([01][0-9]|2[0-3]):([0-5][0-9])(:([0-5][0-9]))?";
    /**
     * 日期:20180505
     */
    public static final String DAY = "\\d{4}(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[0-1])";
    /**
     * 月份:201805
     */
    public static final String MONTH = "\\d{4}(0[1-9]|1[0-2])";
    /**
     * 邮箱
     */
    public static final String EMAIL = "^([\\w-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
    /**
     * IP
     */
    public static final String IP = "^(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";
    /**
     * URL
     */
    public static final String URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
    /**
     * 电话号码
     */
    public static final String PHONE = "^(\\d{3,4}-)?\\d{6,8}$";
    /**
     * 手机号码
     */
    public static final String CELL_PHONE = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$";
    /**
     * 邮政编码
     */
    public static final String POST_CODE = "^\\d{6}$";
    /**
     * 身份证号码（此处只是简单的校验长度是否合法）
     */
    public static final String IDCARD = "(^\\d{18}$)|(^\\d{15}$)";
    /**
     * 密码 -数字、字母、数字字母组合6~16位字符密码
     */
    public static final String PASSWORD = "^[a-zA-Z0-9]{6,16}$";
    /**
     * 中文
     */
    public static final String CHINESE_STR = "^[\u4e00-\u9fa5]$";
}
