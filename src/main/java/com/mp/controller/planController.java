package com.mp.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mp.common.DynamicDataSourceHolder;
import com.mp.dto.result;
import com.mp.entity.file;
import com.mp.entity.location;
import com.mp.entity.plan;
import com.mp.service.planService;
import com.mp.service.fileService;

@Controller
public class planController {
	@Autowired
	private planService planService;

	@Autowired
	private fileService fileService;

	SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
	SimpleDateFormat sf2 = new SimpleDateFormat("yyyyMMdd");

	@ResponseBody
	@RequestMapping(value = "/getPlan", method = RequestMethod.POST)
	private JSONObject getPlan(HttpServletResponse response, HttpServletRequest request) {
		JSONObject object = new JSONObject();

		try {
			request.setCharacterEncoding("utf-8");
			DynamicDataSourceHolder.setDataSource("defultdataSource");
			List<plan> plans = planService.getPlan();
			object.put("rows", plans);
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return object;
	}

	@ResponseBody
	@RequestMapping(value = "/insertPlan", method = RequestMethod.POST)
	private JSONObject insertPlan(HttpServletResponse response, HttpServletRequest request, String title, String link,
			String info, String file) {
		DynamicDataSourceHolder.setDataSource("defultdataSource");
		JSONObject object = new JSONObject();
		result result = new result();
		try {
			request.setCharacterEncoding("utf-8");

			Date now = new Date();
			int loginuser_id = Integer
					.parseInt(new String(request.getParameter("loginuser_id").getBytes("ISO-8859-1"), "UTF-8"));

			plan plan = new plan();
			plan.setInfo(info);
			plan.setLink(link);
			plan.setState(0);
			plan.setTitle(title);
			plan.setUpdatetime(now);
			plan.setUser_id(loginuser_id);

			planService.insert(plan);

			String ids = "";
			if (file != "" && !"".equals(file)) {
				JSONArray json = (JSONArray) JSONArray.parse(file);
				if(json.size() >0) {
					for (Object obj : json) {
						JSONObject jo = (JSONObject)obj;
						int id = jo.getInteger("id");
						ids = ids + id + ",";
					}
				}
				if(!"".equals(ids)) {
					String[] ids_arr = ids.split(",");
					fileService.updateParentId(ids_arr,plan.getID());
				}
			}

			result.setState(0);
			object.put("rows", result);
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Cache-Control", "no-cache");
		} catch (Exception e) {
			// TODO: handle exception
		}
		return object;
	}

	@ResponseBody
	@RequestMapping(value = "/uploadplanBegin", method = RequestMethod.POST)
	private JSONObject uploadplanBegin(@RequestParam(value = "file") MultipartFile file, HttpServletResponse response,
			HttpServletRequest request) {
		DynamicDataSourceHolder.setDataSource("defultdataSource");
		JSONObject object = new JSONObject();

		String test_pl = "D:\\";
		String server_pl = "F:\\";
		String path = test_pl + "wanfang_file";

		try {
//			File filePath = new File(path);
			String originalFileName = file.getOriginalFilename();
//			System.out.println("原始文件名称：" + originalFileName);
//			String name = originalFileName.substring(0, originalFileName.lastIndexOf("."));
			Date now = new Date();
			String date = sf2.format(now);
			int loginuser_id = Integer
					.parseInt(new String(request.getParameter("loginuser_id").getBytes("ISO-8859-1"), "UTF-8"));

			String place_str = path + "\\" + date;
			File place = new File(place_str);
			if (!place.exists()) {// 如果文件夹不存在
				place.mkdir();// 创建文件夹
			}

			File file1 = new File(place, originalFileName);
			String newfilename = sf.format(now) + "_" + originalFileName;
			file.transferTo(file1);
			File file2 = new File(place + "\\" + newfilename);
			file1.renameTo(file2);
//			File file_moto = new File(file.get);
//			FileUtils.copyFile(file_moto,place);

//	        String type = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
//	        StringBuffer sb = new StringBuffer();
//			sb.append(date).append("_").append(name).append(".").append(type);

//	        System.out.println("新文件名称：" + sb.toString());

			file upfile = new file();
			upfile.setOriginalFileName(originalFileName);
			upfile.setName(newfilename);
			upfile.setPath(newfilename);
			upfile.setType("企画新規");
			upfile.setUpdatetime(now);
			upfile.setUser_id(loginuser_id);
			fileService.insert(upfile);

			file upfile_re = new file();
			System.out.println(upfile.getID());
			upfile_re.setID(upfile.getID());
			upfile_re.setName(upfile.getOriginalFileName());

			object.put("file", upfile_re);
			object.put("success", true);
			object.put("message", "アプロードしました!");
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Cache-Control", "no-cache");
		} catch (Exception e) {
			// TODO: handle exception
		}
		return object;
	}

}
