package com.mp.controller;

import static org.hamcrest.CoreMatchers.nullValue;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.mp.dto.option;
import com.mp.dto.result;
import com.mp.entity.list1;
import com.mp.service.listService;

@Controller
public class listController {
	@Autowired
	private listService listService;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@ResponseBody
	@RequestMapping(value = "/getArrival_japan", method = RequestMethod.POST)
	private JSONObject getArrival_japan(HttpServletResponse response, HttpServletRequest request) {
		JSONObject object = new JSONObject();
		List<option> option = listService.getArrival_japan();
		object.put("rows", option);
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Cache-Control", "no-cache");
		return object;
	}

	@ResponseBody
	@RequestMapping(value = "/getList1", method = RequestMethod.POST)
	private JSONObject getList1(HttpServletResponse response, HttpServletRequest request) {
		JSONObject object = new JSONObject();
		try {
			// ID
			String searchId = new String(request.getParameter("searchId").getBytes("ISO-8859-1"), "UTF-8");
			String searchtime_s = new String(request.getParameter("searchtime_s").getBytes("ISO-8859-1"), "UTF-8");
			String searchtime_e = new String(request.getParameter("searchtime_e").getBytes("ISO-8859-1"), "UTF-8");
			String searchcontain = new String(request.getParameter("searchcontain").getBytes("ISO-8859-1"), "UTF-8");
			String searchkeiban = new String(request.getParameter("searchkeiban").getBytes("ISO-8859-1"), "UTF-8");
			String searchedaban = new String(request.getParameter("searchedaban").getBytes("ISO-8859-1"), "UTF-8");
			String search_arrival_japan = new String(
					request.getParameter("search_arrival_japan").getBytes("ISO-8859-1"), "UTF-8");
			String search_arrival_soko = new String(request.getParameter("search_arrival_soko").getBytes("ISO-8859-1"),
					"UTF-8");
			String radio_soko1 = new String(request.getParameter("radio_soko1").getBytes("ISO-8859-1"), "UTF-8");
			String radio_soko2 = new String(request.getParameter("radio_soko2").getBytes("ISO-8859-1"), "UTF-8");
			String radio_soko3 = new String(request.getParameter("radio_soko3").getBytes("ISO-8859-1"), "UTF-8");
			int searchCount = Integer
					.parseInt(new String(request.getParameter("searchCount").getBytes("ISO-8859-1"), "UTF-8"));
			int list_currentPage = Integer
					.parseInt(new String(request.getParameter("list_currentPage").getBytes("ISO-8859-1"), "UTF-8"));
			list_currentPage = (list_currentPage - 1) * searchCount;

			int total = listService.getCountAll();

			List<list1> list1 = listService.getList1(list_currentPage, searchCount, searchId, searchtime_s,
					searchtime_e, searchcontain, searchkeiban, searchedaban, search_arrival_japan, search_arrival_soko,
					radio_soko1, radio_soko2, radio_soko3);

			object.put("total", total);
			object.put("rows", list1);
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Cache-Control", "no-cache");
		} catch (UnsupportedEncodingException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return object;
	}

	@ResponseBody
	@RequestMapping(value = "/getList1ById", method = RequestMethod.POST)
	private JSONObject getList1ById(HttpServletResponse response, HttpServletRequest request) {
		JSONObject object = new JSONObject();
		try {
			String searchId = new String(request.getParameter("searchId").getBytes("ISO-8859-1"), "UTF-8");
			List<list1> list1 = listService.getList1ById(searchId);
			object.put("rows", list1);
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Cache-Control", "no-cache");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return object;
	}

	@ResponseBody
	@RequestMapping(value = "/updateList1", method = RequestMethod.POST)
	private JSONObject updateList1(HttpServletResponse response, HttpServletRequest request) throws Exception {
		JSONObject object = new JSONObject();
		try {
			request.setCharacterEncoding("utf-8");
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Cache-Control", "no-cache");

			list1 list1 = new list1();
			String updater = java.net.URLDecoder.decode(request.getParameter("updater"), "UTF-8");
			int id = Integer.parseInt(new String(request.getParameter("id").getBytes("ISO-8859-1"), "UTF-8"));
			String code = java.net.URLDecoder.decode(request.getParameter("code"), "UTF-8");
			String sub_code = java.net.URLDecoder.decode(request.getParameter("sub_code"), "UTF-8");
			String img = java.net.URLDecoder.decode(request.getParameter("img"), "UTF-8");
			String item_name = java.net.URLDecoder.decode(request.getParameter("item_name"), "UTF-8");
			int order_count = 0;
			if (request.getParameter("order_count") != null && !"".equals(request.getParameter("order_count"))) {
				order_count = Integer
						.parseInt(new String(request.getParameter("order_count").getBytes("ISO-8859-1"), "UTF-8"));
			}
			int inspect_count = 0;
			if (request.getParameter("inspect_count") != null && !"".equals(request.getParameter("inspect_count"))) {
				inspect_count = Integer
						.parseInt(new String(request.getParameter("inspect_count").getBytes("ISO-8859-1"), "UTF-8"));
			}
			String arrival_depo = java.net.URLDecoder.decode(request.getParameter("arrival_depo"), "UTF-8");
			Date departure = null;
			if (request.getParameter("departure") != null && !"".equals(request.getParameter("departure"))) {
				departure = sdf.parse(new String(request.getParameter("departure").getBytes("ISO-8859-1"), "UTF-8"));
			}
			Date arrival = null;
			if (request.getParameter("arrival") != null && !"".equals(request.getParameter("arrival"))) {
				arrival = sdf.parse(new String(request.getParameter("arrival").getBytes("ISO-8859-1"), "UTF-8"));
			}
			int fba_stock = 0;
			if (request.getParameter("fba_stock") != null && !"".equals(request.getParameter("fba_stock"))) {
				fba_stock = Integer
						.parseInt(new String(request.getParameter("fba_stock").getBytes("ISO-8859-1"), "UTF-8"));
			}
			double unit_ch = 0;
			if (request.getParameter("unit_ch") != null && !"".equals(request.getParameter("unit_ch"))) {
				unit_ch = Double
						.parseDouble(new String(request.getParameter("unit_ch").getBytes("ISO-8859-1"), "UTF-8"));
			}
			double total_ch = 0;
			if (request.getParameter("total_ch") != null && !"".equals(request.getParameter("total_ch"))) {
				total_ch = Double
						.parseDouble(new String(request.getParameter("total_ch").getBytes("ISO-8859-1"), "UTF-8"));
			}
			int unit_jp = 0;
			if (request.getParameter("unit_jp") != null && !"".equals(request.getParameter("unit_jp"))) {
				unit_jp = Integer.parseInt(new String(request.getParameter("unit_jp").getBytes("ISO-8859-1"), "UTF-8"));
			}
			int total_jp = 0;
			if (request.getParameter("total_jp") != null && !"".equals(request.getParameter("total_jp"))) {
				total_jp = Integer
						.parseInt(new String(request.getParameter("total_jp").getBytes("ISO-8859-1"), "UTF-8"));
			}
			double rate = 0;
			if (request.getParameter("rate") != null && !"".equals(request.getParameter("rate"))) {
				rate = Double.parseDouble(new String(request.getParameter("rate").getBytes("ISO-8859-1"), "UTF-8"));
			}
			double ne_stock = 0;
			if (request.getParameter("ne_stock") != null && !"".equals(request.getParameter("ne_stock"))) {
				ne_stock = Double
						.parseDouble(new String(request.getParameter("ne_stock").getBytes("ISO-8859-1"), "UTF-8"));
			}
			String container = java.net.URLDecoder.decode(request.getParameter("container"), "UTF-8");
			String box_num = java.net.URLDecoder.decode(request.getParameter("box_num"), "UTF-8");
			int box_count = 0;
			if (request.getParameter("box_count") != null && !"".equals(request.getParameter("box_count"))) {
				box_count = Integer
						.parseInt(new String(request.getParameter("box_count").getBytes("ISO-8859-1"), "UTF-8"));
			}
			double kg = 0;
			if (request.getParameter("kg") != null && !"".equals(request.getParameter("kg"))) {
				kg = Double.parseDouble(new String(request.getParameter("kg").getBytes("ISO-8859-1"), "UTF-8"));
			}
			double one_m3 = 0;
			if (request.getParameter("one_m3") != null && !"".equals(request.getParameter("one_m3"))) {
				one_m3 = Double.parseDouble(new String(request.getParameter("one_m3").getBytes("ISO-8859-1"), "UTF-8"));
			}
			double all_m3 = 0;
			if (request.getParameter("all_m3") != null && !"".equals(request.getParameter("all_m3"))) {
				all_m3 = Double.parseDouble(new String(request.getParameter("all_m3").getBytes("ISO-8859-1"), "UTF-8"));
			}
			String material = java.net.URLDecoder.decode(request.getParameter("material"), "UTF-8");
			String material_ch = java.net.URLDecoder.decode(request.getParameter("material_ch"), "UTF-8");
			double height = 0;
			if (request.getParameter("height") != null && !"".equals(request.getParameter("height"))) {
				height = Double.parseDouble(new String(request.getParameter("height").getBytes("ISO-8859-1"), "UTF-8"));
			}
			double width = 0;
			if (request.getParameter("width") != null && !"".equals(request.getParameter("width"))) {
				width = Double.parseDouble(new String(request.getParameter("width").getBytes("ISO-8859-1"), "UTF-8"));
			}
			double depth = 0;
			if (request.getParameter("depth") != null && !"".equals(request.getParameter("depth"))) {
				depth = Double.parseDouble(new String(request.getParameter("depth").getBytes("ISO-8859-1"), "UTF-8"));
			}

			Date now = new Date();

			list1.setLockuser(null);
			list1.setLocktime(null);
			list1.setUpdater(updater);
			list1.setUpdatetime(now);
			list1.setID(id);
			list1.setCode(code);
			list1.setSub_code(sub_code);
			list1.setImg(img);
			list1.setItem_name(item_name);
			list1.setOrder_count(order_count);
			list1.setInspect_count(inspect_count);
			list1.setArrival_depo(arrival_depo);
			list1.setDeparture(departure);
			list1.setArrival(arrival);
			list1.setFba_stock(fba_stock);
			list1.setUnit_ch(unit_ch);
			list1.setTotal_ch(total_ch);
			list1.setUnit_jp(unit_jp);
			list1.setTotal_jp(total_jp);
			list1.setRate(rate);
			list1.setNe_stock(ne_stock);
			list1.setContainer(container);
			list1.setBox_num(box_num);
			list1.setBox_count(box_count);
			list1.setKg(kg);
			list1.setOne_m3(one_m3);
			list1.setAll_m3(all_m3);
			list1.setMaterial(material);
			list1.setMaterial_ch(material_ch);
			list1.setHeight(height);
			list1.setWidth(width);
			list1.setDepth(depth);

			listService.updateList1(list1);

			result result = new result();
			result.setState(1);
			object.put("rows", result);
		} catch (UnsupportedEncodingException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			result result = new result();
			result.setState(0);
			object.put("rows", result);
		}
		return object;
	}
}
