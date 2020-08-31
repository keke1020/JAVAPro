package com.mp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.mp.common.DynamicDataSourceHolder;
import com.mp.dto.option;
import com.mp.service.commonService;

@Controller
public class commonController {
	@Autowired
	private commonService commonService;

	@ResponseBody
	@RequestMapping(value = "/getCommonLoad", method = RequestMethod.POST)
	private JSONObject getCommonLoad(HttpServletResponse response, HttpServletRequest request) {
		JSONObject object = new JSONObject();

		DynamicDataSourceHolder.setDataSource("defultdataSource");
		List<option> option1 = commonService.getArrival_japan();
		object.put("Arrival_japan_rows", option1);

		List<option> option2 = commonService.getBBS_user();
		object.put("BBS_user_rows", option2);

		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Cache-Control", "no-cache");
		return object;
	}

	@ResponseBody
	@RequestMapping(value = "/getCommonUser", method = RequestMethod.POST)
	private JSONObject getCommonUser(HttpServletResponse response, HttpServletRequest request) {
		JSONObject object = new JSONObject();

		DynamicDataSourceHolder.setDataSource("defultdataSource");
		List<option> option = commonService.getUsers();
		object.put("user_rows", option);

		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Cache-Control", "no-cache");
		return object;
	}




}
