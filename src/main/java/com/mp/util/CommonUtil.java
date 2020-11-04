package com.mp.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
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

	//判断一个字符串是否为数字型
	public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
	}

//	写入txt文件
	@SuppressWarnings("finally")
	public static boolean writeDataHubData(List<String> result, String fileName) {
//        long start = System.currentTimeMillis();
//        StringBuilder content = new StringBuilder();
        boolean flag = false;
        BufferedWriter out = null;
        try {
            if (result != null && !result.isEmpty() && !"".equals(fileName)) {
//                fileName += "_" + System.currentTimeMillis() + ".txt";
                fileName += ".txt";
//                File pathFile = new File(fileName);
//                if (!pathFile.exists()) {
//                    pathFile.mkdirs();
//                }
                File file = new File(fileName);
                if (!file.exists()) {
                    file.createNewFile();
                }
                out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "Shift-JIS"));
//                //标题头
//                out.write("curr_time,link_id,travel_time,speed,reliabilitycode,link_len,adcode,time_stamp,state,public_rec_time,ds");
//                out.newLine();
                for (String info : result) {
                    out.write(info);
                    out.newLine();
                }
                flag = true;
//                logger.info("写入文件耗时：*********************************" + (System.currentTimeMillis() - start) + "毫秒");
//                System.out.println("写入文件耗时：*********************************" + (System.currentTimeMillis() - start) + "毫秒");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return flag;
        }
    }
	/**
     * 截取字符串str中指定字符 strStart、strEnd之间的字符串
     *
     * @param string
     * @param str1
     * @param str2
     * @return
     */
    public static String subString(String str, String strStart, String strEnd) {

        int strStartIndex = str.indexOf(strStart);
        int strEndIndex = str.indexOf(strEnd);

        String result = "";

        /* index 为负数 即表示该字符串中 没有该字符 */
        if (strStartIndex < 0) {
//            return "字符串 :---->" + str + "<---- 中不存在 " + strStart + ", 无法截取目标字符串";
        	return result;
        }
        if (strEndIndex < 0) {
        	return result;
        }
        result = str.substring(strStartIndex, strEndIndex).substring(strStart.length());
        return result;
    }

}
