package com.mp.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
	/*
	 * 将时间转换为时间戳
	 */
	public static Long dateToStamp(Date d) throws ParseException{
	    Date date = d;
	    long ts = date.getTime();
	    return ts;
	}

	/*
	 * 将时间戳转换为时间
	 */
	public static String stampToDate(String s){
	    String res;
	    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    long lt = new Long(s);
	    Date date = new Date(lt);
	    res = simpleDateFormat.format(date);
	    return res;
	}
}
