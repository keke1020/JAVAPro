package com.mp.controller;

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
import com.mp.dto.system;
import com.mp.entity.bbs;
import com.mp.service.bbsService;

@Controller
public class bbsController {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Autowired
	private bbsService bbsService;

	@ResponseBody
	@RequestMapping(value = "/getBBS", method = RequestMethod.POST)
	private JSONObject getBBS() {
		//pagelist:use bootstrap framework
		List<bbs> bbs = bbsService.getBBS(0, 0);

		JSONObject object = new JSONObject();
		object.put("total", 5);
		object.put("rows",bbs);
		return object;
	}

	@ResponseBody
	@RequestMapping(value = "/insertBBS", method = RequestMethod.POST)
	private void insertBBS(HttpServletRequest req, HttpSession session,
			@Param("message") String message) {
		if(!"".equals(session.getAttribute(system.usr)) && session.getAttribute(system.usr) != null) {
			System.out.println(session.getAttribute(system.usr));
			//Dateオブジェクトを生成する
		    Date dTime = new Date();
		    String now = sdf.format(dTime);


			bbsService.insertBBS(0,(String) session.getAttribute(system.usr), message, now);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/deleteBBS", method = RequestMethod.POST)
	private void deleteBBS(HttpServletRequest req, HttpSession session,
			@Param("ID") int ID) {
			bbsService.deleteBBS(ID);
	}





}
