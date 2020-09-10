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
import com.mp.entity.user;
import com.mp.service.commonService;
import com.mp.service.userService;

@Controller
public class commonController {
	@Autowired
	private commonService commonService;

	@Autowired
	private userService userService;

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
	private JSONObject getCommonUser(HttpServletResponse response, HttpServletRequest request, String place) {
		JSONObject object = new JSONObject();

		DynamicDataSourceHolder.setDataSource("defultdataSource");
		List<option> option = commonService.getUsers(place);
		object.put("user_rows", option);

		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Cache-Control", "no-cache");
		return object;
	}

	@ResponseBody
	@RequestMapping(value = "/getUsersConfig", method = RequestMethod.POST)
	private JSONObject getUsersConfig(HttpServletResponse response, HttpServletRequest request,
			int currentPage, int pagesize, boolean limitFlag) {
		JSONObject object = new JSONObject();

		int current = (currentPage - 1) * pagesize;

		DynamicDataSourceHolder.setDataSource("defultdataSource");
		List<user> user = userService.getUsersConfig(limitFlag, current, pagesize);
		int total = userService.getTotal();
		object.put("user", user);
		object.put("total", total);

		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Cache-Control", "no-cache");
		return object;
	}

	@ResponseBody
	@RequestMapping(value = "/getUsersConfigById", method = RequestMethod.POST)
	private JSONObject getUsersConfigById(HttpServletResponse response, HttpServletRequest request, String id) {
		JSONObject object = new JSONObject();

		DynamicDataSourceHolder.setDataSource("defultdataSource");
		user user = userService.getUsersConfigById(id);
		object.put("user", user);

		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Cache-Control", "no-cache");
		return object;
	}

	@ResponseBody
	@RequestMapping(value = "/updateUserConfig", method = RequestMethod.POST)
	private JSONObject updateUserConfig(HttpServletResponse response, HttpServletRequest request, String id, boolean plan_contr_sw) {
		JSONObject object = new JSONObject();

		DynamicDataSourceHolder.setDataSource("defultdataSource");
		user user = new user();
		user.setID(id);
		if(plan_contr_sw) {
			user.setPlan_contr(1);
		} else {
			user.setPlan_contr(0);
		}

		userService.updateUserConfig(user);

		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Cache-Control", "no-cache");
		return object;
	}



}
