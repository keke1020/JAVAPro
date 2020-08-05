package com.mp.util;

import java.util.regex.Pattern;

public class CommonUtil {
	/* 这是一个静态函数，不用声明对象就可以用的，如你的类名为Test,在任何情况下都可以调用Test.isHave函数 */
	public static boolean isHave(String[] strs, String s) {
		/*
		 * 此方法有两个参数，第一个是要查找的字符串数组，第二个是要查找的字符或字符串
		 */
		for (int i = 0; i < strs.length; i++) {
			if (strs[i].indexOf(s) != -1) {// 循环查找字符串数组中的每个字符串中是否包含所有查找的内容
				return true;// 查找到了就返回真，不在继续查询
			}
		}
		return false;// 没找到返回false
	}

	public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
	}



}
