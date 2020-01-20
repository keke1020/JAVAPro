package com.mp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mp.dto.system;

@Controller
public class testController {
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	private String index(HttpServletRequest req, HttpSession session) {
		String pg = null;
		pg = "login";
		System.out.println(session.getAttribute(system.usr));
		if (!"".equals(session.getAttribute(system.usr)) && session.getAttribute(system.usr) != null) {
			pg = "test";
		}
		return pg;
	}




}
