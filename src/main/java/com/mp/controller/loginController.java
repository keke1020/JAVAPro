package com.mp.controller;

import static org.hamcrest.CoreMatchers.nullValue;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mp.dto.system;
import com.mp.entity.user;
import com.mp.service.userService;

@Controller
public class loginController {
	@Autowired
	private userService userService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	private String index(HttpServletRequest req, HttpSession session) {
		String pg = null;
		pg = "login";
		if(!"".equals(session.getAttribute(system.usr)) && session.getAttribute(system.usr) != null) {
			System.out.println(session.getAttribute(system.usr));
			pg = "index";
		}
		return pg;
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	private ModelAndView login(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@Param("password") String password) throws UnsupportedEncodingException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		ModelAndView mv = new ModelAndView();

		if ("".equals(password) || password == null) {
			mv.setViewName("common/error");
		} else {
			user user_ = userService.login(password);
			if (user_ != null) {
				session.setAttribute(system.usr, user_.getRealname());
				mv.setViewName("index");
			} else {
				mv.setViewName("common/error");
			}
		}

		if("".equals(session.getAttribute(system.usr)) || session.getAttribute(system.usr) == null) {
			System.out.println(session.getAttribute(system.usr));
			mv.setViewName("login");
		}

//		Object token = session.getAttribute("token");
//        String tokenValue = request.getParameter("token");
//        System.out.println(token);
//        System.out.println(tokenValue);
//        if (token != null && token.equals(tokenValue)) {
//            session.removeAttribute("token");
//        } else {
//        	pg = "login";
//        }

//		mv.addObject("msg","hello myfirst mvc");
		return mv;
	}

	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	private ModelAndView logout(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws UnsupportedEncodingException {
		ModelAndView mv = new ModelAndView();
		session.invalidate();
		mv.setViewName("login");
		return mv;
	}







}
