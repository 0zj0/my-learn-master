package com.example.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * @author zhangjie
 * @date 2019/11/22 15:59
 */
public class DateUtils {

    //LocalDate : 2019-11-22
    //LocalTime : 14:15:56.411
    //LocalDateTime : 2019-11-22T16:15:54.071
    //ZonedDateTime : 2019-11-22T16:16:34.020+08:00[Asia/Shanghai]

    /**
     * 时间戳转LocalDateTime
     * @param timestamp
     * @return
     */
    public static LocalDateTime convertToLocalDateTime(long timestamp){
        Instant instant = Instant.ofEpochMilli(timestamp);
        return LocalDateTime.ofInstant(instant,ZoneId.systemDefault());
    }

    /**
     * 时间戳转Instant
     * @param timestamp
     * @return
     */
    public static Instant convertToInstant(long timestamp){
        return Instant.ofEpochMilli(timestamp);
    }

    /**
     * localDateTime 转 时间戳
     * @param localDateTime
     * @return
     */
    public static long localDateTimeToTimestamp(LocalDateTime localDateTime){
        return localDateTime.toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
    }

    /**
     * instant 转 时间戳
     * @param instant
     * @return
     */
    public static long instantToTimestamp(Instant instant){
        return instant.toEpochMilli();
    }

    /**
     * 方法有问题，不能通用  format 必须由年到秒
     * @param time
     * @param format
     * @return
     */
    public static LocalDateTime parseToLocalDateTime(String time,String format){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(time,formatter);
    }

    public static LocalDate parseToLocalDate(String time,String format){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalDate.parse(time,formatter);
    }

    public static LocalDateTime getDateToLocalDateTime(LocalDate localDate){
        return localDate.atStartOfDay();
    }

    public static void main(String[] args) {
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        System.out.println(zonedDateTime);
        Instant now = Instant.now();
        System.out.println(now);
        System.out.println(now.toEpochMilli());
        System.out.println(System.currentTimeMillis());
        System.out.println(localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        System.out.println(localDateTime.toInstant(ZoneOffset.ofHours(8)).toEpochMilli());
        System.out.println(System.currentTimeMillis());
        System.out.println(convertToInstant(System.currentTimeMillis()).toEpochMilli());
        System.out.println(convertToLocalDateTime(System.currentTimeMillis()));
        System.out.println(parseToLocalDateTime("2018-07-28","yyyy-MM-dd"));
    }

}
