package com.mp.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.mp.entity.user;
import com.mp.dto.result;
import com.mp.service.userService;

@Controller
public class loginController {
	@Autowired
	private userService userService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	private String index(HttpServletRequest req, HttpSession session) {
		String pg = null;
		pg = "login";
//		System.out.println(session.getAttribute(system.usr));
//		if (!"".equals(session.getAttribute(system.usr)) && session.getAttribute(system.usr) != null) {
//			pg = "index";
//		}
		return pg;
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	private JSONObject login(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@Param("password") String password) throws UnsupportedEncodingException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
//		ModelAndView mv = new ModelAndView();
		JSONObject object = new JSONObject();
		result result = new result();
		user user_ = new user();

		if ("".equals(password) || password == null) {
			result.setState(0);
			result.setMsg("もう一度入力してください。");
		} else {
			user_ = userService.login(password);
			if (user_ != null) {
				result.setState(1);
				object.put("user", user_);
			} else {
				result.setState(0);
				result.setMsg("パスワードを確認してください。");
			}
		}
		object.put("rows", result);
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Cache-Control", "no-cache");
		return object;
	}

//	@ResponseBody
//	@RequestMapping(value = "/logout", method = RequestMethod.POST)
//	private JSONObject logout(HttpServletRequest request, HttpServletResponse response, HttpSession session)
//			throws UnsupportedEncodingException {
//		session.invalidate();
//		JSONObject object = new JSONObject();
//		object.put("state", 0);
//		return object;
//	}

//	@ResponseBody
//	@RequestMapping(value = "/toPage", method = RequestMethod.POST)
//	private JSONObject toPage(HttpServletRequest request, HttpServletResponse response, HttpSession session,
//			int page_index) throws UnsupportedEncodingException {
//		JSONObject object = new JSONObject();
//		String toPage;
//
//		// 0: test
//		switch (page_index) {
//		default:
//			toPage = "";
//			break;
//		case 0:
//			toPage = "index";
//			break;
//		case 999:
//			toPage = "test";
//			break;
//		}
//
//		object.put("toPage", toPage);
//		return object;
//	}

}
