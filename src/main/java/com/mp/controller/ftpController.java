//package com.mp.controller;
//
//import java.io.UnsupportedEncodingException;
//import java.util.List;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.alibaba.fastjson.JSONObject;
//import com.mp.common.DynamicDataSourceHolder;
//import com.mp.entity.bbs;
//import com.mp.service.ftpService;
//
//@Controller
//public class ftpController {
//	@Autowired
//	private ftpService ftpService;
//
//	@RequestMapping(value = "/getFtp_setting", method = RequestMethod.POST)
//	@ResponseBody
//	private JSONObject getFtp_setting(HttpServletResponse response, HttpServletRequest request) {
//		JSONObject object = new JSONObject();
//		try {
//			request.setCharacterEncoding("utf-8");
//			response.setCharacterEncoding("utf-8");
//			response.setHeader("Access-Control-Allow-Origin", "*");
//			response.setHeader("Cache-Control", "no-cache");
//
//			DynamicDataSourceHolder.setDataSource("defultdataSource");
//
//
//
//
//			object.put("rows", null);
//		} catch (UnsupportedEncodingException e) {
//			// TODO 自動生成された catch ブロック
//			e.printStackTrace();
//		}
//		return object;
//	}
//}
