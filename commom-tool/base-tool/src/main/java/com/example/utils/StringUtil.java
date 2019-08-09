package com.example.utils;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author ylb
 */
public class StringUtil extends StringUtils {
	private static Random random = new Random();

	public static String removeParameter(String queryString, String name) {
		if (isEmpty(queryString)) {
			return queryString;
		} else {
			queryString = queryString.replaceAll("&" + name + "=[^&]*", "").replaceAll("\\?" + name + "=[^&]*&", "?").replaceAll("\\?" + name + "=[^&]*", "");
			return queryString;
		}
	}
	public static String addParameter(String url, String key, Object value) {
		if (isEmpty(url)) {
			return null;
		} else {
			return !isEmpty(key) && !isEmpty(String.valueOf(value)) ? url + (url.indexOf("?") > 0 ? "&" : "?") + key + "=" + value : url;
		}
	}

	public static String getParameter(String url, String name) {
		if (StringUtil.isEmpty(url)) {
			return null;
		}
		int index = url.indexOf("?" + name);
		if (index <= 0) {
			index = url.indexOf("&" + name);
			if (index <= 0) {
				return null;
			}
		}
		int lastIndex = url.indexOf("&", index+1);
		index += name.length() + 2;
		if(lastIndex <= 0){
			return url.substring(index);
		}else {
			return url.substring(index, lastIndex);
		}
	}

	public static String join(Object[] array, String str){
		if(array == null){ return null;}
		StringBuffer s = new StringBuffer();
		for (int i = 0; i < array.length; i++) {
			s.append(array[i].toString() + str);
		}
		s.deleteCharAt(s.length()-str.length());
		return s.toString();
	}
	
	public static String getAppPath() {
		String path = System.getProperty("user.dir");
		if(!path.endsWith(File.separator)){
			path = path+File.separator;
		}
		return path;
	}
	
	public static String trim(String str){
		return isEmpty(str)?null:str.trim();
	}
	public static boolean isEmpty(String str){
		return str==null || str.trim().length()==0 || "undefined".equals(str);
	}
	public static boolean isNotEmpty(String str){
		str = trim(str);
		return str!=null && str.length()>0 && !"undefined".equals(str);
	}
	public static int parseIntByObj(Object obj){
		if(obj==null){
			return 0;
		}
		if (obj instanceof Number) {
			return ((Number) obj).intValue();
		}else{
			return parseInt(obj.toString(), 0);
		}
	}
	public static int parseInt(String str){
		return parseInt(str, 0);
	}
	
	public static double parseDouble(String str){
		return parseDouble(str, 0);
	}
	
	public static int parseInt(String str, int defaultValue){
		try{
			return Integer.parseInt(trim(str));
		}catch (Exception e) {
			return defaultValue;
		}
	}
	
	public static long parseLong(String str){
		return parseLong(str, 0L);
	}
	public static long parseLongByObj(Object obj){
		if(obj==null){
			return 0;
		}
		if (obj instanceof Number) {
			return ((Number) obj).longValue();
		}else{
			return parseLong(obj.toString(), 0);
		}
	}
	public static double parseDoubleByObj(Object obj){
		if(obj==null){
			return 0;
		}
		if (obj instanceof Number) {
			return ((Number) obj).doubleValue();
		}else{
			return parseDouble(obj.toString(), 0);
		}
	}
	public static long parseLong(String str, long defaultValue){
		try{
			return Long.parseLong(trim(str));
		}catch (Exception e) {
			return defaultValue;
		}
	}
	
	public static float parseFloat(String str, float defaultValue){
		try{
			return Float.parseFloat(trim(str));
		}catch (Exception e) {
			return defaultValue;
		}
	}
	
	public static double parseDouble(String str, double defaultValue){
		try{
			return Double.parseDouble(trim(str));
		}catch (Exception e) {
			return defaultValue;
		}
	}
	
	public static boolean parseBoolean(String str){
		str = trim(str);
		return "true".equalsIgnoreCase(str) || "1".equalsIgnoreCase(str);
	}
	
	public static boolean toBoolean(int type){
		return type-1==1;
	}
	
	public static int getRandomInteger(int max) {
		return random.nextInt(max);
	}
	
	public static String getRandomString(int length) {
		String baseStr = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			sb.append(baseStr.charAt(random.nextInt(baseStr.length())));
		}
		return sb.toString();
	}
	
	public static String toFieldString(String str){
		if(str==null){
			return null;
		}else{
			return "'"+str.replace("\\'", "'").replace("'", "\\'") + "'";
		}
	}
	
	public static String toLikeStr(String str){
		if(isEmpty(str)){
			return null;
		}else{
			return "%"+str+"%";
		}
	}
	
	public static String formatNumber(double number, String format){
		DecimalFormat df = new DecimalFormat(format);
		return df.format(number);
	}
	
	/**
	 * 转化为两位数的货币数值
	 * 如number=11.2345,结果为11.23
	 * @param number
	 * @return
	 */
	public static String formatMoney(double number){
		DecimalFormat df = new DecimalFormat("#0.00");
		return df.format(number);
	}
	
	/**
	 * 转化为两位数的货币数值，数值前加上货币符号
	 * 如number=11.2345,currency=￥时，结果为￥11.23
	 * @param number
	 * @param currency 货币符号
	 * @return
	 */
	public static String formatMoney(double number, String currency){
		DecimalFormat df = new DecimalFormat(currency+"#0.00");
		return df.format(number);
	}
	
	/**
	 * 保留8位数数字
	 * @param number
	 * @return
	 */
	public static String format8Decimal(double number){
		DecimalFormat df = new DecimalFormat("#.########");
		return df.format(number);
	}
	
	public static String encodeStr(String str){
		if(str==null){
			return str;
		}
		try{
			return URLEncoder.encode(str, "utf-8");
		}catch (Exception e) {
			return str;
		}
	}
	
	public static String decodeStr(String str){
		if(str==null){
			return str;
		}
		try{
			return URLDecoder.decode(str, "utf-8");
		}catch (Exception e) {
			return str;
		}
	}
	
	public static String trimAndHtmlEscape(String str){
		if(str==null||"".equals(str)){
			return null;
		}
		return htmlEscape(str.trim());
	}
	
	public static String htmlEscape(String str){
		if(str==null||str.length()==0){
			return null;
		}
		return str.replace("&", "&amp;")
				.replace("<", "&lt;")
				.replace(">", "&gt;")
				.replace("\"", "&quot;")
				.replace("'", "&apos;");
	}
	public static String htmlUnescape(String str){
		if(str==null||str.length()==0){
			return null;
		}
		return str.replace("&amp;", "&")
				.replace("&lt;", "<")
				.replace("&gt;", ">")
				.replace("&quot;", "\"")
				.replace("&apos;", "'");
	}
	public static String[] filterEmpty(String[] array){
		if(array==null){
			return null;
		}
		List<String> list = new ArrayList<String>(array.length);
		for(int i=0;i<array.length; i++){
			String str = trim(array[i]);
			if(isNotEmpty(str)){
				list.add(str);
			}
		}
		if(list.size()<=0){
			return null;
		}
		return list.toArray(new String[]{});
	}
	
	public static String[] trim(String[] array){
		if(array==null){
			return null;
		}
		for(int i=0;i<array.length; i++){
			array[i] = trim(array[i]);
		}
		return array;
	}
	public static String[] trimAndHtmlEscape(String[] array){
		if(array==null){
			return null;
		}
		for(int i=0;i<array.length; i++){
			array[i] = trimAndHtmlEscape(array[i]);
		}
		return array;
	}

	/**
	 * string 转换成 BigDecimal
	 * 去除以前的 bd=bd.setScale(2, BigDecimal.ROUND_HALF_UP); 并加入try,catch异常返回
	 * @param str 待转化字符串
	 * @return BigDecimal
	 * @author 刘洋印
	 * @date 2018-06-24 20:49:57
	 */
	public static BigDecimal parseBigDecimal(String str){
		if(StringUtil.isEmpty(str)){
			return BigDecimal.ZERO;
		}
		BigDecimal bd;
		try{
			bd=new BigDecimal(str.trim());
		}catch (Exception e){
			return BigDecimal.ZERO;
		}
		return bd;
	}

	/**
	 * string 转换成 BigDecimal
	 * @param str 待转化字符串
	 * @param newScale 截取字符串
	 * @return BigDecimal
	 * @author 刘洋印
	 * @date 2018-06-24 20:49:57
	 */
	public static BigDecimal parseBigDecimal(String str,int newScale){
		if(StringUtil.isEmpty(str)){
			return BigDecimal.ZERO;
		}
		BigDecimal bd;
		try{
			bd=new BigDecimal(str.trim());
			bd=bd.setScale(newScale, BigDecimal.ROUND_HALF_UP);
		}catch (Exception e){
			return BigDecimal.ZERO;
		}
		return bd;
	}
	
	public static int[] parseIntArray(String[] array){
		if(array==null){
			return null;
		}
		int[] value = new int[array.length];
		for(int i=0;i<array.length; i++){
			value[i] = parseInt(array[i]);
		}
		return value;
	}
	
	public static String[] getStringArray(HttpServletRequest request, String paramName){
		if(paramName==null){
			return null;
		}
		String paramName1 = paramName.replace("[]", "");
		String paramName2 = paramName1+"[]";
		String[] strArray = request.getParameterValues(paramName2);
		if(strArray==null||strArray.length<=0){
			strArray = request.getParameterValues(paramName1);
			if(strArray==null||strArray.length<=0){
				strArray = new String[]{request.getParameter(paramName1)};
			}
		}
		return strArray;
	}
	
	public static int[] getIntArray(HttpServletRequest request, String paramName){
		String[] strArray = getStringArray(request, paramName);
		return parseIntArray(strArray);
	}
	
}
