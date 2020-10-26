package com.mp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.mp.common.DynamicDataSourceHolder;
import com.mp.entity.tenpo;
import com.mp.service.tenpoService;

@Controller
public class tenpoController {
	@Autowired
	private tenpoService tenpoService;


	@ResponseBody
	@RequestMapping(value = "/getTenpo", method = RequestMethod.POST)
	private JSONObject getTenpo(HttpServletResponse response, HttpServletRequest request) {
		JSONObject object = new JSONObject();

		DynamicDataSourceHolder.setDataSource("defultdataSource");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Cache-Control", "no-cache");

		List<tenpo> tenpo = tenpoService.getTenpo();

		object.put("tenpo", tenpo);
		return object;
	}

	@ResponseBody
	@RequestMapping(value = "/uploadCsv_kakaku2", method = RequestMethod.POST)
	private JSONObject uploadCsv_kakaku2(@RequestParam(value = "file") MultipartFile file, HttpServletResponse response,
			HttpServletRequest request, String tenpo) {
		JSONObject object = new JSONObject();

		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Cache-Control", "no-cache");

			String originalFileName = file.getOriginalFilename();
			System.out.println(originalFileName);





		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}


		return object;
	}





}
