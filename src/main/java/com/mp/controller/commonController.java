package com.mp.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mp.common.DynamicDataSourceHolder;
import com.mp.dto.option;
import com.mp.entity.user;
import com.mp.service.commonService;
import com.mp.service.userService;
import com.mp.util.PoiUtil;

import sun.tools.jar.resources.jar;

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
	private JSONObject getUsersConfig(HttpServletResponse response, HttpServletRequest request, int currentPage,
			int pagesize, boolean limitFlag) {
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
	private JSONObject updateUserConfig(HttpServletResponse response, HttpServletRequest request, String id,
			boolean plan_contr_sw) {
		JSONObject object = new JSONObject();

		DynamicDataSourceHolder.setDataSource("defultdataSource");
		user user = new user();
		user.setID(id);
		if (plan_contr_sw) {
			user.setPlan_contr(1);
		} else {
			user.setPlan_contr(0);
		}

		userService.updateUserConfig(user);

		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Cache-Control", "no-cache");
		return object;
	}

	// tool
	@ResponseBody
	@RequestMapping(value = "/uploadCsv_tool", method = RequestMethod.POST)
	private JSONObject uploadCsv_tool(@RequestParam(value = "file") MultipartFile file, HttpServletResponse response,
			HttpServletRequest request, String zyodai_ch) {
		JSONObject object = new JSONObject();
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Cache-Control", "no-cache");

			InputStream is = file.getInputStream();
			List<String[]> records = new ArrayList<String[]>();
			String record_;

			// 设定UTF-8字符集，使用带缓冲区的字符输入流BufferedReader读取文件内容
			BufferedReader file1 = new BufferedReader(new InputStreamReader(is, "Shift-JIS"));
			int count_max1 = 0;
			int count_max2 = 0;
			while ((record_ = file1.readLine()) != null) {
				String fields[] = record_.split(",");
				count_max1 = fields.length;
				if (count_max2 < count_max1) {
					count_max2 = count_max1;
				}
				count_max1 = 0;

//				for (int i = 0; i < fields.length; i++) {
//					if (fields[i].trim().contains("\"")) {
//						fields[i] = fields[i].trim().replaceAll("\"", "");
//					} else {
//						fields[i] = "";
//					}
//				}
				records.add(fields);
			}
			file1.close();

			List<String[]> records_ = new ArrayList<String[]>();
			for (int i = 0; i < records.size(); i++) {
				if (records.get(i).length < count_max2) {
					String[] new_data = new String[count_max2];
					for (int j = 0; j < records.get(i).length; j++) {
						new_data[j] = records.get(i)[j];
					}
					records_.add(new_data);
				} else {
					records_.add(records.get(i));
				}
			}

			for (int i = 0; i < records_.size(); i++) {
				for (int j = 0; j < records_.get(i).length; j++) {
					if (records_.get(i)[j] == null) {
						records_.get(i)[j] = "";
					} else {
						if ("".equals(records_.get(i)[j].trim())) {
							records_.get(i)[j] = "";
						}
					}
				}
			}

			int[] header_ = new int[count_max2];
			List<option> selet_index_op = new ArrayList<option>();
			for (int i = 0; i < count_max2; i++) {
				header_[i] = i;

				option op = new option();
				op.setLabel(String.valueOf(i));
				op.setValue(String.valueOf(i));
				selet_index_op.add(op);
			}

			object.put("file_data", records_);
			object.put("header_", header_);
			object.put("selet_index_op", selet_index_op);
		} catch (Exception e) {
			System.out.println(e);
		}
		return object;
	}

	@ResponseBody
	@RequestMapping(value = "/syori_tool", method = RequestMethod.POST)
	private JSONObject syori_tool(HttpServletResponse response, HttpServletRequest request, String file_data,
			int selet_index_1, int selet_index_2, String action_name, String zyogai1_ch, String zyogai2_ch,
			String kugiri) {
		JSONObject object = new JSONObject();
		List<String[]> spare_result = new ArrayList<String[]>();
		try {
			if (file_data != "" && !"".equals(file_data)) {
				JSONArray json = (JSONArray) JSONArray.parse(file_data);

				List<String> spare_l = new ArrayList<String>();
				List<String> spare_2 = new ArrayList<String>();

				if (json.size() > 0) {
					for (int i = 0; i < json.size(); i++) {
//						System.out.println(json.get(i).toString());
						String str = json.get(i).toString().substring(1, json.get(i).toString().length() - 1);
						String[] str_ = str.split(",");
						if (!"".equals(str_[selet_index_1].trim())) {
							spare_l.add(str_[selet_index_1].trim().replaceAll("\"", ""));
						}
						if (!"".equals(str_[selet_index_2].trim())) {
							spare_2.add(str_[selet_index_2].trim().replaceAll("\"", ""));
						}
					}

					List<String> spare_l_ = new ArrayList<String>();
					List<String> spare_2_ = new ArrayList<String>();
					if (spare_l.size() > 0 && spare_2.size() > 0) {
						if ("true".equals(zyogai2_ch)) {
							// 同じレコードを除外する
							for (int i = 0; i < spare_l.size(); i++) {
								String tem1 = spare_l.get(i);
								if (tem1.contains("-")) {
									String tem[] = tem1.split("-");
									tem1 = tem[0];
								}
								if (!spare_l_.contains(tem1)) {
									spare_l_.add(tem1);
								}
							}

							for (int i = 0; i < spare_2.size(); i++) {
								if (!spare_2_.contains(spare_2.get(i))) {
									spare_2_.add(spare_2.get(i));
								}
							}
						} else {
							for (int i = 0; i < spare_l.size(); i++) {
								spare_l_.add(spare_l.get(i));
							}

							for (int i = 0; i < spare_2.size(); i++) {
								spare_2_.add(spare_2.get(i));
							}
						}

//						List<String> spare_l_2 = new ArrayList<String>();
//						if ("-".equals(kugiri)) {
//							for (int i = 0; i < spare_l_.size(); i++) {
//								if (spare_l_.get(i).contains("-")) {
//									String[] tem = spare_l_.get(i).split("-");
//									spare_l_2.add(tem[0]);
//								} else {
//									spare_l_2.add(spare_l_.get(i));
//								}
//							}
//						}

						// 比べる
						for (int i = 0; i < spare_l_.size(); i++) {
							if (!"".equals(spare_l_.get(i)) && spare_l_.get(i) != null) {
								String str = spare_l_.get(i);
								for (int j = 0; j < spare_2_.size(); j++) {
									if (spare_2_.get(j).contains("-")) {
										String[] tem = spare_2_.get(j).split("-");
										if (spare_l_.get(i).equals(tem[0])) {
											str = str + "," + spare_2_.get(j);
										}
									}
								}
								String[] arr = str.split(",");
								spare_result.add(arr);
							}
						}
					}

				}
			}
			object.put("spare_result", spare_result);
		} catch (Exception e) {
			System.out.println(e);
		}
		return object;
	}

	@ResponseBody
	@RequestMapping(value = "/getTenpoByNe", method = RequestMethod.POST)
	private JSONObject getTenpoByNe(HttpServletResponse response, HttpServletRequest request) {
		JSONObject object = new JSONObject();
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Cache-Control", "no-cache");
		DynamicDataSourceHolder.setDataSource("defultdataSource");
		List<option> option1 = commonService.getTenpoByNe();
		object.put("tenpo_rows", option1);
		return object;
	}


//	@ResponseBody
//	@RequestMapping(value = "/download_tool", method = RequestMethod.POST)
//	private JSONObject download_tool(HttpServletResponse response, HttpServletRequest request, String spare_result) {
//		JSONObject object = new JSONObject();
//		List<String[]> spare_export = new ArrayList<String[]>();
//		try {
//			if (spare_result != "" && !"".equals(spare_result)) {
//				JSONArray json = (JSONArray) JSONArray.parse(spare_result);
//
//				if (json.size() > 0) {
////					int count_max1 = 0;
////					int count_max2 = 0;
//					for (int i = 0; i < json.size(); i++) {
//						String str = json.get(i).toString().substring(1, json.get(i).toString().length() - 1);
//						String[] str_ = str.split(",");
////						count_max1 = str_.length;
////						if(count_max1 > count_max2) {
////							count_max2 = count_max1;
////						}
//
//					}
//
//
//
//				}
//
//			}
//			object.put("spare_result", spare_result);
//		} catch (Exception e) {
//			System.out.println(e);
//		}
//		return object;
//	}

}
