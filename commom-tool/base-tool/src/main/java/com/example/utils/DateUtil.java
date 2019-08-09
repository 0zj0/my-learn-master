package com.example.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 获取或转换时间的工具类
 *
 * @author ylb
 * 2011-11-1上午09:42:00
 * 
 */
public class DateUtil {

	public final static String DEFAULT_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public final static String DEFAULT_DAY_FORMAT = "yyyy-MM-dd";
	public final static String DEFAULT_DATE_FORMAT = "MM-dd";
	public final static String DATE_HOUR_MINUTE_FORMAT = "yyyyMMdd";
	public final static String HOUR_MINUTE_FORMAT = "HH:mm";
	public final static int SHORT_TIMESTAMP_LEN = 10;

	
	/**
	 * 获取SimpleDateFormat实例
	 * 
	 * @param format
	 * @return
	 * 
	 */
	private static SimpleDateFormat getSimpleDateFormat(String format) {
		return new SimpleDateFormat(format);
	}

	/**
	 * 获取自定义格式的当前时间
	 * 
	 * @param format
	 * @return
	 */
	public static String now(String format) {
		return convertToString(new Date(), format);
	}
	

	/**
	 * 获取普通格式当前时间
	 *
	 */
	public static String now() {
		return convertToString(new Date(), DEFAULT_TIME_FORMAT);
	}

	/**
	 * 获取普通格式当前时间
	 *
	 */
	public static String today() {
		return convertToString(new Date(), DEFAULT_DAY_FORMAT);
	}

	/**
	 * 昨天0点的字符串  yyyy-MM-dd HH:mm:ss
	 *
	 */
	public static String yesterday() {
		return convertToString(getYesterdayTimestamp());
	}

	/**
	 * 明天0点的字符串  yyyy-MM-dd HH:mm:ss
	 */
	public static String tomorrow() {
		return convertToString(getTomorrowTimestamp());
	}
	/**
	 * 将Date类型转换为自定义类型的String类型时间
	 * 
	 * @param format
	 * @param date
	 * @return
	 */
	public static String convertToString(Date date, String format) {
		return getSimpleDateFormat(format).format(date);
	}

	/**
	 * 将Date类型转换为普通格式的String类型时间
	 * 
	 * @param date
	 * @return
	 */
	public static String convertToString(Date date) {
		return convertToString(date, DEFAULT_TIME_FORMAT);
	}

	/**
	 * 将指定格式的String类型时间转换为Date类型
	 * 
	 * @param date
	 * @param format
	 * @return
	 * 
	 */
	public static Date convertToDate(String date, String format) {
		try {
			return getSimpleDateFormat(format).parse(date);
		} catch (ParseException e) {
			System.err.println("未能转换时间：" + date + "到格式：" + format + "\n" + e);
		}
		return null;
	}

	/**
	 * 将普通格式的的String类型时间转换成Date类型
	 * 
	 * @param date
	 * @return
	 * 
	 */
	public static Date convertToDate(String date) {
		return convertToDate(date, DEFAULT_TIME_FORMAT);
	}

	/**
	 * 将字符串类型的时间转换为Date类型
	 * 
	 * @param date
	 * @return
	 */
	public static Date convertToDateFromLocaleUS(String date) {
		if (StringUtil.isEmpty(date)) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.US);
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			System.err.println("未能转换时间：" + date + "到格式：EEE MMM dd HH:mm:ss Z yyyy\n" + e);
		}
		return null;
	}

	/**
	 * LocaleUS时间格式转化为自定义时间格式
	 * 
	 * @param date
	 * @return
	 */
	public static String convertToString(String date) {
		return convertToString(convertToDateFromLocaleUS(date));
	}

	/**
	 * 时间戳转化为Date类型的时间
	 * 
	 * @param
	 * @return
	 */
	public static Date convertToDate(long timestamp) {
		if(String.valueOf(timestamp).length() == SHORT_TIMESTAMP_LEN){
			timestamp *= 1000;
		}
		return new Date(timestamp);
	}

	/**
	 * 时间戳转化为String类型的时间
	 * 
	 * @param timestamp
	 * @return
	 */
	public static String convertToString(long timestamp) {
		return convertToString(convertToDate(timestamp));
	}

	public static long convertToTimestamp(String date) {
		return convertToDate(date).getTime();
	}

	public static long convertToTimestamp(String date, String format) {
		return convertToDate(date, format).getTime();
	}

	/**
	 * 判断当前时间是否在指定的时间范围内
	 * 
	 * @auth 闫海鹏
	 * 
	 * @param str
	 *            数据库中取出来的时间
	 * @return
	 * @throws ParseException
	 * 
	 */
	/*
	public static boolean checkTime(String str) {
		List<String> allowedSendTime = StringUtil.splitToList(str);

		Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);// 获得当前小时数
		int minutes = c.get(Calendar.MINUTE);// 获得当前分钟数
		c.clear();
		c.set(Calendar.HOUR_OF_DAY, hour);
		c.set(Calendar.MINUTE, minutes);

		SimpleDateFormat sdf = getSimpleDateFormat("HH:mm");
		for (String sendTime : allowedSendTime) {
			try {
				Date d1 = sdf.parse(sendTime.split("-")[0]);
				Date d2 = sdf.parse(sendTime.split("-")[1]);
				if (d1.compareTo(c.getTime()) == -1 && d2.compareTo(c.getTime()) == 1)
					return true;
			} catch (ParseException e) {
				System.err.println("未能转换时间：" + sendTime + "到格式：HH:mm\n" + e);
			}
		}
		return false;
	}
	*/
	

	/**
	 * 返回一个时间几小时后的时间
	 * 
	 * @author 王勇
	 * @param date
	 *            string格式的时间
	 * @param hour
	 *            要增加的小时数
	 * @return
	 */
	public static Calendar addHoursToDate(String date, int hour) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(convertToDate(date));
		calendar.add(Calendar.HOUR, hour);
		return calendar;
	}

	/**
	 * 比较指定的参数是否与现在相同
	 * 
	 * @auth 闫海鹏
	 * 
	 * @param pamram
	 *            年份、月份、日期、小时、分钟
	 * @param date
	 *            需要比较的时间
	 * @return
	 * 
	 */
	public static boolean compareTime(int pamram, String date) {
		Calendar calendar = Calendar.getInstance();
		int nowTime = calendar.get(pamram);
		calendar.setTime(convertToDate(date, "yyyy-MM-dd"));
		return nowTime == calendar.get(pamram);
	}

	/**
	 * 当天的00:00:00.000时刻的时间戳
	 * 
	 * @return
	 * @author 郑德湖
	 * @date Oct 28, 2011 5:09:59 PM
	 */
	public static long getTodayTimestamp() {
		return convertToTimestamp(today(), DEFAULT_DAY_FORMAT);
	}

	/**
	 * 昨天0点时间戳
	 */
	public static long getYesterdayTimestamp() {
		long timestamp = System.currentTimeMillis() / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset();
		return timestamp - 86400000;
	}

	/**
	 * 明天0点的时间戳
	 */
	public static long getTomorrowTimestamp() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTimeInMillis();
	}

	/**
	 * 获取当天
	 * @param date
	 * @return
	 */
	public static int getDayWeek(String date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(DateUtil.convertToDate(date,DATE_HOUR_MINUTE_FORMAT));
		int week = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (week == 0){
			week = 7;
		}
		return week;
	}
	/**
	 * 获取当天
	 * @param date
	 * @return
	 */
	public static int getDayWeek(String date,String format){
		Calendar cal = Calendar.getInstance();
		cal.setTime(DateUtil.convertToDate(date,format));
		return cal.get(Calendar.DAY_OF_WEEK) - 1;
	}


	/**
	 * 判断当前时间是否在[startTime, endTime]区间，注意时间格式要一致
	 *
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 * @author xieqing
	 */
	public static boolean isEffectiveDate( String startTime, String endTime) throws Exception {
		Date nowDate = new SimpleDateFormat(HOUR_MINUTE_FORMAT).parse(now(HOUR_MINUTE_FORMAT));
		Date startDate = new SimpleDateFormat(HOUR_MINUTE_FORMAT).parse(startTime);
		Date endDate = new SimpleDateFormat(HOUR_MINUTE_FORMAT).parse(endTime);
		if (nowDate.getTime() == startDate.getTime()
				|| nowDate.getTime() == endDate.getTime()) {
			return true;
		}

		Calendar date = Calendar.getInstance();
		date.setTime(nowDate);

		Calendar begin = Calendar.getInstance();
		begin.setTime(startDate);

		Calendar end = Calendar.getInstance();
		end.setTime(endDate);

		if (date.after(begin) && date.before(end)) {
			return true;
		} else {
			return false;
		}

	}


	/**
	 * 是否在不受限制的星期内
	 * @param xingqi 不受限制的星期时间
	 * @return
	 * @author xieqing
	 * @date 2018/7/2 0002 10:56
	 */
	public static boolean isInUseWeek( String xingqi){
		try {
			String[] limitWeek = xingqi.split(",");
			String nowWeek = getWeek()+"";
			for (String str : limitWeek){
				if(str.equals(nowWeek)){
					return true;
				}
			}
			return false;
		}catch (Exception e){
		}
		return false;
	}


	/**
	 * 获取日期属于星期几，1到7表示星期一到星期日，7表示星期日
	 * @return
	 * @author xieqing
	 * @date 2018/7/2 0002 10:56
	 */
	public static int getWeek() {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int week = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (week == 0){
			week = 7;
		}
		return week;
	}


	/**
	 * 获取日期为星期几
	 * @param date
	 * @return
	 * @author xieqing
	 * @date 2019/5/31 0031 19:57
	 */
	public static int dateWeek(int date){
		String time;
		if (date<=0){
			time = now(DateUtil.DATE_HOUR_MINUTE_FORMAT);
		}else {
			time = date+"";
		}
		return DateUtil.getDayWeek(time);
	}


	/**
	 *
	 * @param count
	 * @return
	 */
	public static String afterDayDate(int count,String format) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, count);
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(calendar.getTime());
	}


	public static String dateAfterDayTime(int count,String date,String format) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(DateUtil.convertToDate(date,DateUtil.DATE_HOUR_MINUTE_FORMAT));
		calendar.add(Calendar.DATE, count);
		SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.DATE_HOUR_MINUTE_FORMAT);
		return sdf.format(calendar.getTime());
	}
}
