package com.mp.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.mp.entity.syouhin;
import com.mp.service.syouhinService;

@Controller
public class syouhinController {
	@Autowired
	private syouhinService syouhinService;

	@ResponseBody
	@RequestMapping(value = "/getSyohinByCode", method = RequestMethod.POST)
	private JSONObject getSyohinByCode(HttpServletResponse response, HttpServletRequest request) {
		JSONObject object = new JSONObject();
		try {
			String code = new String(request.getParameter("code").getBytes("ISO-8859-1"), "UTF-8");
			if(code != null && !"".equals(code)) {
				List<syouhin> syouhin = syouhinService.getSyohinByCode(code);
				object.put("rows", syouhin);
			} else {
				object.put("rows", null);
			}
		} catch (UnsupportedEncodingException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Cache-Control", "no-cache");
		return object;
	}
}
