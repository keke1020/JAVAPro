package com.mp.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mp.common.DynamicDataSourceHolder;
import com.mp.dto.result;
import com.mp.entity.config;
import com.mp.entity.custom;
import com.mp.entity.file;
import com.mp.entity.plan;
import com.mp.entity.plan_info;
import com.mp.service.customService;
import com.mp.service.fileService;

@Controller
public class customController {
	SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
	SimpleDateFormat sf2 = new SimpleDateFormat("yyyyMMdd");

	@Autowired
	private fileService fileService;

	@Autowired
	private customService customService;

	@ResponseBody
	@RequestMapping(value = "/downloadFileById", method = RequestMethod.POST)
	private ResponseEntity<byte[]> downloadFileById(HttpServletResponse response, HttpServletRequest request, String fileId) throws IOException {
		DynamicDataSourceHolder.setDataSource("defultdataSource");
//		JSONObject object = new JSONObject();
//		result result = new result();
		String path = "";
		HttpHeaders headers = new HttpHeaders();

		try {
			file file = fileService.getFileById(fileId);
			path = file.getPath();
//			String name = file.getOriginalFileName();
//			if (dl_file.exists()) {
				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
				headers.setContentDispositionFormData("attachment", path.substring(path.lastIndexOf("/") + 1));
//			}
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Cache-Control", "no-cache");
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(new File(path)), headers, HttpStatus.CREATED);
	}

	@ResponseBody
	@RequestMapping(value = "/uploadCustom", method = RequestMethod.POST)
	private JSONObject uploadCustom(@RequestParam(value = "file") MultipartFile file, HttpServletResponse response,
			HttpServletRequest request) {
		DynamicDataSourceHolder.setDataSource("defultdataSource");
		JSONObject object = new JSONObject();

		String path = "";
		if(config.ISLOCAL) {
			path = config.PLAN_PIC_PLACE_LOCAL + "wanfang_file_Custom";
		} else {
			path = config.PLAN_PIC_PLACE_SERVER + "wanfang_file_Custom";
		}

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
			upfile.setChangeName(newfilename);
			upfile.setName(originalFileName);
			upfile.setPath(path + "\\" + date + "\\"+ newfilename);
			upfile.setType("0");
			upfile.setUpdatetime(now);
			upfile.setUser_id(loginuser_id);
			fileService.insert(upfile);

			file upfile_re = new file();
			upfile_re.setID(upfile.getID());
			upfile_re.setName(upfile.getName());

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

	@ResponseBody
	@RequestMapping(value = "/getCustom", method = RequestMethod.POST)
	private JSONObject getCustom(HttpServletResponse response, HttpServletRequest request) {
		JSONObject object = new JSONObject();

		try {
			request.setCharacterEncoding("utf-8");
			DynamicDataSourceHolder.setDataSource("defultdataSource");
			List<custom> plans = customService.getCustom();
			object.put("rows", plans);
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return object;
	}

	@ResponseBody
	@RequestMapping(value = "/getCustomById", method = RequestMethod.POST)
	private JSONObject getCustomById(HttpServletResponse response, HttpServletRequest request,
			String id) {
		JSONObject object = new JSONObject();

		try {
			request.setCharacterEncoding("utf-8");
			DynamicDataSourceHolder.setDataSource("defultdataSource");
			custom custom = customService.getCustomById(id);
			List<file> files = fileService.getFilesByParentId("0",id);
			customService.setView_count(id);
			object.put("custom", custom);
			object.put("files", files);
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return object;
	}

	@ResponseBody
	@RequestMapping(value = "/insertCustom", method = RequestMethod.POST)
	private JSONObject insertCustom(HttpServletResponse response, HttpServletRequest request, String title, String goods_code,
			String file, String info) {
		DynamicDataSourceHolder.setDataSource("defultdataSource");
		JSONObject object = new JSONObject();
		result result = new result();
		try {
			request.setCharacterEncoding("utf-8");

			Date now = new Date();
			int loginuser_id = Integer
					.parseInt(new String(request.getParameter("loginuser_id").getBytes("ISO-8859-1"), "UTF-8"));

			custom custom = new custom();
			custom.setGoods_code(goods_code);
			custom.setTitle(title);
			custom.setUser_id(loginuser_id);
			custom.setUpdatetime(now);
			custom.setView(1);
			custom.setInfo(info);

			customService.insert(custom);
			int custom_newid = custom.getID();

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
					fileService.updateParentId(ids_arr,custom_newid);
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
	@RequestMapping(value = "/updateCustom", method = RequestMethod.POST)
	private JSONObject updateCustom(HttpServletResponse response, HttpServletRequest request, String id,String title, String goods_code,
			String file, String info) {
		DynamicDataSourceHolder.setDataSource("defultdataSource");
		JSONObject object = new JSONObject();
		result result = new result();
		try {
			request.setCharacterEncoding("utf-8");

			Date now = new Date();
			int loginuser_id = Integer
					.parseInt(new String(request.getParameter("loginuser_id").getBytes("ISO-8859-1"), "UTF-8"));

			custom custom = new custom();
			custom.setID(Integer.parseInt(id));
			custom.setGoods_code(goods_code);
			custom.setTitle(title);
			custom.setUser_id(loginuser_id);
			custom.setUpdatetime(now);
			custom.setView(1);
			custom.setInfo(info);

			customService.update(custom);
			int custom_id = custom.getID();

			String file_ids = "";

			fileService.clearParentId("0", custom_id);
			if (file != "" && !"".equals(file)) {
				JSONArray json = (JSONArray) JSONArray.parse(file);
				if(json.size() >0) {
					for (Object obj : json) {
						JSONObject jo = (JSONObject)obj;
						int file_id = jo.getInteger("id");
						file_ids = file_ids + file_id + ",";
					}
				}
				if(!"".equals(file_ids)) {
					String[] file_ids_arr = file_ids.split(",");
					fileService.updateParentId(file_ids_arr,custom_id);
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
	@RequestMapping(value = "/deleteCustomById", method = RequestMethod.POST)
	private JSONObject deleteCustomById(HttpServletResponse response, HttpServletRequest request, String id) {
		DynamicDataSourceHolder.setDataSource("defultdataSource");
		JSONObject object = new JSONObject();
		result result = new result();
		try {
			customService.deleteCustomById(id);
			result.setState(0);
			object.put("rows", result);
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Cache-Control", "no-cache");
		} catch (Exception e) {
			// TODO: handle exception
		}
		return object;
	}







}
