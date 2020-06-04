package com.mp.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
import com.mp.dto.result;
import com.mp.entity.location;
import com.mp.service.locationService;
import com.mp.service.syouhinService;
import com.mp.util.FileUtil;
import com.mp.util.TimeUtil;
import com.sun.org.apache.bcel.internal.generic.NEW;

@Controller
public class locationController {
	@Autowired
	private locationService locationService;

	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat sf2 = new SimpleDateFormat("yyyyMMddHHmmss");

	@ResponseBody
	@RequestMapping(value = "/getLocation", method = RequestMethod.POST)
	private JSONObject getLocation(HttpServletResponse response, HttpServletRequest request, String index) {
		JSONObject object = new JSONObject();

		String masterPath = "D:\\xampp\\htdocs\\orderA\\dl_master\\master.csv";
		String masterDatePath = "D:\\xampp\\htdocs\\ordery\\data\\souko_master.txt";
		String masterDB_Path = "D:\\xampp\\htdocs\\ordery\\soukoDB\\master.csv";

		File masterDatePath_file = new File(masterDatePath);

		try {
			boolean updateFlag = false;
//			BufferedReader br = new BufferedReader(new FileReader(masterDatePath_file));

			// masterPath
			Path path_master = Paths.get(masterPath);
			BasicFileAttributeView basicview_master = Files.getFileAttributeView(path_master,
					BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
			BasicFileAttributes attr_master = basicview_master.readAttributes();
			Instant instant_master = attr_master.lastModifiedTime().toInstant();
//	        String time_master = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss").withZone(ZoneId.systemDefault()).format(instant_master);
			Long master_long = instant_master.getEpochSecond();

			// masterDatePath
//			Path path_masterDate= Paths.get(masterDatePath);
//	        BasicFileAttributeView basicview_masterDate= Files.getFileAttributeView(path_masterDate, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS );
//	        BasicFileAttributes attr_masterDate = basicview_masterDate.readAttributes();
//	        Instant instant_masterDate = attr_masterDate.creationTime().toInstant();
//	        String time_masterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss").withZone(ZoneId.systemDefault()).format(instant_masterDate);
			BufferedReader br = new BufferedReader(new FileReader(masterDatePath_file));
			Date masterDate = new Date(br.readLine());
//        	String masterDate_str = sf.format(masterDate);
//        	Date masterDate2 = sf.parse(masterDate_str);
			Long masterDate_long = masterDate.getTime() / 1000;

			if (!master_long.equals(masterDate_long)) {
				updateFlag = true;
			}

			if(updateFlag) {
				Path path_masterDB = Paths.get(masterDB_Path);
				Files.copy(path_master, path_masterDB, StandardCopyOption.REPLACE_EXISTING);

				Date master_d = Date.from(instant_master);
				SimpleDateFormat formatter = new SimpleDateFormat("MMMM-dd-yyyy HH:mm:ss", Locale.ENGLISH);
				String master_ds = formatter.format(master_d);

				FileWriter writer = new FileWriter(masterDatePath, false);
				BufferedWriter out = new BufferedWriter(writer);
				out.write(master_ds);
				out.flush();
				out.close();

				String file_name = "D:\\xampp\\htdocs\\ordery\\upload\\temp2.csv";
				Path path_file = Paths.get(file_name);
				Files.copy(path_masterDB, path_file, StandardCopyOption.REPLACE_EXISTING);

			} else {

			}

			DynamicDataSourceHolder.setDataSource("jrt_dataSource");
			List<location> locations = locationService.getlocation();
			object.put("rows", locations);

		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		return object;
	}

	@ResponseBody
	@RequestMapping(value = "/uploadCsv_Loc", method = RequestMethod.POST)
	private JSONObject uploadCsv_Loc(HttpServletResponse response, HttpServletRequest request, @RequestParam(value = "csvfile") MultipartFile csvfile) {
		JSONObject object = new JSONObject();
		try {
			request.setCharacterEncoding("utf-8");
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Cache-Control", "no-cache");
			result result = new result();

			if(!"csv".equals(FileUtil.getFileType(csvfile.getOriginalFilename()))) {
				result.setState(0);
				result.setMsg("csvファイルをアップロードしてください。");
				object.put("rows", result);
				return object;
			}

			String move_Path = "D:\\xampp\\htdocs\\ordery\\upload\\sf" + sf2.format(new Date()) + ".csv";
			Path path_move = Paths.get(move_Path);
			Files.copy(csvfile.getInputStream(), path_move, StandardCopyOption.REPLACE_EXISTING);






			result.setState(1);
			result.setMsg("アップリードしました！");
			object.put("rows", result);
			return object;
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return object;
	}
}
