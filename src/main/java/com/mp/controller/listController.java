package com.mp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mp.entity.list1;
import com.mp.service.listService;

@Controller
public class listController {
	@Autowired
	private listService listService;

	@ResponseBody
	@RequestMapping(value = "/getList1", method = RequestMethod.POST)
	private List<list1> getList1() {
		List<list1> list1 = listService.getList1(0, 200);
		return list1;
	}






}
