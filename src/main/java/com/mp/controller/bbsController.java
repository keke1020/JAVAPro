package com.mp.controller;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.mp.dto.result;
import com.mp.entity.bbs;
import com.mp.service.bbsService;

import javax.servlet.http.HttpServletResponse;

@Controller
public class bbsController {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Autowired
	private bbsService bbsService;

	@RequestMapping(value = "/getBBS", method = RequestMethod.POST)
	@ResponseBody
	private JSONObject getBBS(HttpServletResponse response, int currentPage) {
		// pagelist:use bootstrap framework
		int startIndex = (currentPage - 1) * 5;
		List<bbs> bbs = bbsService.getBBS(startIndex, 5);
		int total = bbsService.getTotal();
		JSONObject object = new JSONObject();
		object.put("total", total);
		object.put("rows", bbs);
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Cache-Control", "no-cache");
		return object;
	}

	@RequestMapping(value = "/insertBBS", method = RequestMethod.POST)
	@ResponseBody
	private JSONObject insertBBS(HttpServletResponse response,HttpServletRequest request) {

		JSONObject object = new JSONObject();
		try {
			String loginuser=request.getParameter("loginuser");
			int loginuser_id = Integer.parseInt(new String(request.getParameter("loginuser_id").getBytes("ISO-8859-1"), "UTF-8"));
			String message=new String(request.getParameter("message").getBytes("ISO-8859-1"),"UTF-8");

			// Dateオブジェクトを生成する
			Date dTime = new Date();
			String now = sdf.format(dTime);
			bbsService.insertBBS(loginuser_id,loginuser, message, now);

			result result = new result();
			result.setState(1);
			object.put("rows", result);
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Cache-Control", "no-cache");
		} catch (UnsupportedEncodingException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return object;
	}

	@ResponseBody
	@RequestMapping(value = "/deleteBBS", method = RequestMethod.POST)
	private JSONObject deleteBBS(HttpServletResponse response,HttpServletRequest request, @Param("ID") int ID) {
		JSONObject object = new JSONObject();
		bbsService.deleteBBS(ID);
		result result = new result();
		result.setState(1);

		object.put("rows", result);
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Cache-Control", "no-cache");
		return object;
	}

}
