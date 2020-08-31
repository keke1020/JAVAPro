package com.mp.controller;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jasper.tagplugins.jstl.core.If;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.mp.common.DynamicDataSourceHolder;
import com.mp.dto.result;
import com.mp.entity.config;
import com.mp.entity.location;
import com.mp.entity.location_up;
import com.mp.entity.zaikorireki;
import com.mp.service.locationService;
import com.mp.service.zaikorirekiService;
import com.mp.util.CommonUtil;
import com.mp.util.CsvExportUtil;
import com.mp.util.FileUtil;

@Controller
public class locationController {
	@Autowired
	private locationService locationService;

	@Autowired
	private zaikorirekiService zaikorirekiService;

	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat sf2 = new SimpleDateFormat("yyyyMMddHHmmss");
	SimpleDateFormat sf3 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	SimpleDateFormat sf4 = new SimpleDateFormat("yyyyMMddHH");
	SimpleDateFormat sf5 = new SimpleDateFormat("yyyyMMddHHmmss");

	String place = "";

	@ResponseBody
	@RequestMapping(value = "/getLocation", method = RequestMethod.POST)
	private JSONObject getLocation(HttpServletResponse response, HttpServletRequest request, String router_index) {
		JSONObject object = new JSONObject();

		if(config.ISLOCAL) {
			place = config.LOCATION_PLACE_LOCAL;
		} else {
			place = config.LOCATION_PLACE_SERVER;
		}
		String masterPath = place + "xampp\\htdocs\\orderA\\dl_master\\master.csv";

//		String masterDatePath = place + "xampp\\htdocs\\ordery\\data\\souko_master.txt";
//		String masterDB_Path = place + "xampp\\htdocs\\ordery\\soukoDB\\master.csv";

//		test
		String masterDatePath = place + "xampp\\htdocs\\ordery\\data\\souko_master_wanfang.txt";
		String masterDB_Path = place + "xampp\\htdocs\\ordery\\soukoDB\\master_wanfang.csv";

		File masterDatePath_file = new File(masterDatePath);

		try {
			request.setCharacterEncoding("utf-8");
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Cache-Control", "no-cache");
			int loginuser_id = Integer
					.parseInt(new String(request.getParameter("loginuser_id").getBytes("ISO-8859-1"), "UTF-8"));
			String loginuser = java.net.URLDecoder.decode(request.getParameter("loginuser"), "UTF-8");

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

			DynamicDataSourceHolder.setDataSource("jrt_dataSource");

			int changeCount_ = 0;
			int newCount_ = 0;
			if (updateFlag) {
				Path path_masterDB = Paths.get(masterDB_Path);
				Files.copy(path_master, path_masterDB, StandardCopyOption.REPLACE_EXISTING);

				String file_name = place + "xampp\\htdocs\\ordery\\upload\\temp2.csv";
				Path path_file = Paths.get(file_name);
				Files.copy(path_masterDB, path_file, StandardCopyOption.REPLACE_EXISTING);

//				int error_no = UpdateLocationByCsv(1, file_name, loginuser_id, loginuser);
				location_up location_up = UpdateLocationByCsv(1, file_name, null, loginuser_id, loginuser);

				Date master_d = Date.from(instant_master);
				SimpleDateFormat formatter = new SimpleDateFormat("MMMM-dd-yyyy HH:mm:ss", Locale.ENGLISH);
				String master_ds = formatter.format(master_d);
				String master_ds2 = sf3.format(master_d);
				object.put("mDate", master_ds2);

				if (location_up.getErrorno() == 0) {
					FileWriter writer = new FileWriter(masterDatePath, false);
					BufferedWriter out = new BufferedWriter(writer);
					out.write(master_ds);
					out.flush();
					out.close();
				} else if (location_up.getErrorno() == 1) {
					object.put("resultMsg", "csvファイルをアップロードしてください。");
				} else if (location_up.getErrorno() == 2) {
					object.put("resultMsg", "ファイルの種類が異なるため更新できません。");
				}

				changeCount_ = location_up.getChangeCount();
				newCount_ = location_up.getNewCount();
				object.put("updateFlag", "true");
				bakupLogi();
			}

			String searchFlag = request.getParameter("searchFlag");
			String istana = request.getParameter("istana");
			String orderSC = request.getParameter("orderSC");
			String t_kbn = request.getParameter("t_kbn");
			String singuZaiko = request.getParameter("singuZaiko");
			String containerSc = request.getParameter("containerSc");
			String codeSc = request.getParameter("codeSc");
			String kaisosc = request.getParameter("kaisosc");
			String tanaSc = request.getParameter("tanaSc");
			String kosuSC1 = request.getParameter("kosuSC1");
			String kosuSC2 = request.getParameter("kosuSC2");

			int searchCount = Integer
					.parseInt(new String(request.getParameter("searchCount").getBytes("ISO-8859-1"), "UTF-8"));
			int list_currentPage = Integer
					.parseInt(new String(request.getParameter("list_currentPage").getBytes("ISO-8859-1"), "UTF-8"));
			list_currentPage = (list_currentPage - 1) * searchCount;

			List<location> locations = locationService.getlocation(searchFlag, "false", istana, orderSC, t_kbn,
					singuZaiko, codeSc, containerSc, kaisosc, tanaSc, kosuSC1, kosuSC2, "true", list_currentPage,
					searchCount);
			for (int i = 0; i < locations.size(); i++) {
				locations.get(i).setChk(false);
			}

			int total = locationService.getTotal(searchFlag, istana, orderSC, t_kbn, singuZaiko, codeSc, containerSc,
					kaisosc, tanaSc, kosuSC1, kosuSC2);
			int changeCount = locationService.getChangeCount();

			object.put("rows", locations);
			object.put("total", total);
			object.put("changeCount", changeCount);
			if (updateFlag) {
				object.put("changeCount_", changeCount_);
				StringBuffer sb = new StringBuffer();
				sb.append(changeCount_).append("件 修正しました。\n").append(newCount_).append("件 登録しました。");
				object.put("resultMsg", sb.toString());
			} else {
				String mDate = sf3.format(masterDate);
				object.put("mDate", mDate);
				object.put("updateFlag", "false");
			}
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		return object;
	}

	@ResponseBody
	@RequestMapping(value = "/getLocationCountById", method = RequestMethod.POST)
	private JSONObject getLocationCountById(HttpServletResponse response, HttpServletRequest request, String id) {
		JSONObject object = new JSONObject();

		try {
			request.setCharacterEncoding("utf-8");
			DynamicDataSourceHolder.setDataSource("jrt_dataSource");
			String[] ids = id.split(",");
			int count = 0;
			if (ids.length > 1) {
				count = locationService.getLocationCountByIds(ids);
			} else {
				count = locationService.getLocationCountById(id);
			}
			object.put("count", count);
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return object;
	}

	@ResponseBody
	@RequestMapping(value = "/getLocationById", method = RequestMethod.POST)
	private JSONObject getLocationById(HttpServletResponse response, HttpServletRequest request, String id) {
		JSONObject object = new JSONObject();

		try {
			request.setCharacterEncoding("utf-8");
			DynamicDataSourceHolder.setDataSource("jrt_dataSource");

			String[] ids = id.split(",");
			List<location> lo = null;
			if (ids.length > 1) {
				lo = locationService.getLocationByIds(ids);
			} else {
				lo = locationService.getLocationById(id);
			}

			object.put("rows", lo);
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return object;
	}

	@ResponseBody
	@RequestMapping(value = "/uploadCsv_Loc", method = RequestMethod.POST)
	private JSONObject uploadCsv_Loc(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "csvfile") MultipartFile csvfile, String uploadType) {
		JSONObject object = new JSONObject();
		try {
			request.setCharacterEncoding("utf-8");
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Cache-Control", "no-cache");
			result result = new result();

			int loginuser_id = Integer
					.parseInt(new String(request.getParameter("loginuser_id").getBytes("ISO-8859-1"), "UTF-8"));
			String loginuser = java.net.URLDecoder.decode(request.getParameter("loginuser"), "UTF-8");

			if (!"csv".equals(FileUtil.getFileType(csvfile.getOriginalFilename()))) {
				result.setState(0);
				result.setMsg("csvファイルをアップロードしてください。");
				object.put("rows", result);
				return object;
			}

			location_up location_up = new location_up();

			if ("ネクストエンジンcsv登録".equals(uploadType)) {
				location_up = UpdateLocationByCsv(3, "", csvfile, loginuser_id, loginuser);
			} else if ("「項目全て」登録編集".equals(uploadType)) {
				location_up = UpdateLocationByCsv(2, "", csvfile, loginuser_id, loginuser);
			} else if ("csvで「削除」する".equals(uploadType)) {
				location_up = UpdateLocationByCsv(4, "", csvfile, loginuser_id, loginuser);
			} else if ("ネクストエンジン在庫".equals(uploadType)) {
				location_up = UpdateLocationByCsv(5, "", csvfile, loginuser_id, loginuser);
			}

			if (location_up.getErrorno() == 1) {
				object.put("resultMsg", "csvファイルをアップロードしてください。");
				return object;
			} else if (location_up.getErrorno() == 2) {
				object.put("resultMsg", "ファイルの種類が異なるため更新できません。");
				return object;
			}

			if(config.ISLOCAL) {
				place = config.LOCATION_PLACE_LOCAL;
			} else {
				place = config.LOCATION_PLACE_SERVER;
			}

			String move_Path = place + "xampp\\htdocs\\ordery\\upload\\sf" + sf2.format(new Date()) + ".csv";
			Path path_move = Paths.get(move_Path);
			Files.copy(csvfile.getInputStream(), path_move, StandardCopyOption.REPLACE_EXISTING);

			int changeCount_ = location_up.getChangeCount();
			int newCount_ = location_up.getNewCount();
			int delCount_ = location_up.getDelCount();
			StringBuffer sb = new StringBuffer();
			sb.append(changeCount_).append("件 修正しました。\n").append(newCount_).append("件 登録しました。\n").append(delCount_)
					.append("件 削除しました。");
			object.put("resultMsg", sb.toString());
			object.put("rows", result);
			bakupLogi();
			return object;
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return object;
	}

	@ResponseBody
	@RequestMapping(value = "/uploadCsv_Loc2", method = RequestMethod.POST)
	private JSONObject uploadCsv_Loc2(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "csvfile") MultipartFile csvfile) {
		JSONObject object = new JSONObject();
		try {
			request.setCharacterEncoding("utf-8");
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Cache-Control", "no-cache");
			result result = new result();

			int loginuser_id = Integer
					.parseInt(new String(request.getParameter("loginuser_id").getBytes("ISO-8859-1"), "UTF-8"));
			String loginuser = java.net.URLDecoder.decode(request.getParameter("loginuser"), "UTF-8");

			if (!"csv".equals(FileUtil.getFileType(csvfile.getOriginalFilename()))) {
				result.setMsg("csvファイルをアップロードしてください。");
				object.put("rows", result);
				return object;
			}

			List<location> los = new ArrayList<location>();
			List<zaikorireki> zaikorirekis = new ArrayList<zaikorireki>();

			Date nowtime = new Date();
			InputStream is = null;
			is = csvfile.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			String encode = isr.getEncoding();

			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			if ("SHIFT_JIS".equals(encode) || "MS932".equals(encode)) {
				reader = new BufferedReader(new InputStreamReader(is, "SHIFT_JIS"));
			}

			DynamicDataSourceHolder.setDataSource("jrt_dataSource");
			int num = 0;
			String line = null;
			Integer codeNo = null;
			Integer zaikoNo = null;
			List<String> codes_ = new ArrayList<String>();

			while ((line = reader.readLine()) != null) {
				String item[] = line.split(",");

				if (num == 0) {// ヘッダー取込
					if (!CommonUtil.isHave(item, "") || !CommonUtil.isHave(item, "syohin_code")
							|| !CommonUtil.isHave(item, "zaiko_su")) {
						result.setMsg("ファイルの種類が異なるため更新できません。");
						object.put("rows", result);
						return object;
					}
					for (int i = 0; i < item.length; i++) {
						if ("syohin_code".equals(getValue(item, i))) {
							codeNo = i;
						} else if ("zaiko_su".equals(getValue(item, i))) {
							zaikoNo = i;
						}
					}
				} else {
					String code = getValue(item, codeNo).toLowerCase();
					String sZaiko = getValue(item, zaikoNo);

					if (!CommonUtil.isInteger(sZaiko)) {
						result.setMsg("検品済みが数値ではないため更新できません。");
						object.put("rows", result);
						return object;
					} else {
						location lo = new location();
						lo.setCode(code);
						lo.setsZaiko(Integer.parseInt(sZaiko));
						lo.setUser_id(loginuser_id);
						lo.setUpdate(nowtime);
						los.add(lo);
						codes_.add(code);
					}
				}
				num++;
			}

			String[] codes = new String[codes_.size()];
			codes_.toArray(codes);
			int count = locationService.getLocationCountByCodes(codes);
			if (count != codes_.size()) {
				result.setMsg("該当データが無いため更新できません。");
				object.put("rows", result);
				return object;
			}

			for (int i = 0; i < los.size(); i++) {
				zaikorireki z_rireki = new zaikorireki();
				location lo_old = locationService.getLocationByCode(los.get(i).getCode());
				z_rireki.setUpdate(nowtime);
				StringBuffer sb_msg = new StringBuffer();
				sb_msg.append(los.get(i).getCode() + " ");
				if (lo_old != null) {
					sb_msg.append(lo_old.getsZaiko());
				} else {
					sb_msg.append("null");
				}
				sb_msg.append(">").append(los.get(i).getsZaiko());
				z_rireki.setMessage(sb_msg.toString());
				z_rireki.setUser(loginuser);
				z_rireki.setUser_id(loginuser_id);
				z_rireki.setType("shingu");
				zaikorirekis.add(z_rireki);

				locationService.updateShinguByCode(los.get(i));
			}

			if (zaikorirekis.size() > 0) {
				for (int i = 0; i < zaikorirekis.size(); i++) {
					zaikorirekiService.insert(zaikorirekis.get(i));
				}
			}

			result.setMsg(los.size() + "件 変更しました。");
			object.put("rows", result);
			bakupLogi();
			return object;
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return object;
	}

	@ResponseBody
	@RequestMapping(value = "/editLocation", method = RequestMethod.POST)
	private JSONObject editLocation(HttpServletResponse response, HttpServletRequest request,
			@RequestBody String params, int active) {
		JSONObject object = new JSONObject();

		try {
			request.setCharacterEncoding("utf-8");
			Date nowtime = new Date();
			int loginuser_id = Integer
					.parseInt(new String(request.getParameter("loginuser_id").getBytes("ISO-8859-1"), "UTF-8"));
			String loginuser = java.net.URLDecoder.decode(request.getParameter("loginuser"), "UTF-8");
			JSONObject j1 = JSONObject.parseObject(params);
			List<location> locationNodes = JSONObject.parseArray(j1.getJSONArray("params").toJSONString(),
					location.class);
			List<zaikorireki> zaikorirekis = new ArrayList<zaikorireki>();
			List<location> zaiko_lo = new ArrayList<location>();
			if (locationNodes.size() > 0 && locationNodes != null) {
				DynamicDataSourceHolder.setDataSource("jrt_dataSource");
				for (int i = 0; i < locationNodes.size(); i++) {
					location lo = locationNodes.get(i);

					location lo_old = locationService.getLocationByCode(lo.getCode());
					// 新宮
					if (active == 2) {
						zaikorireki z_rireki = new zaikorireki();
						z_rireki.setUpdate(nowtime);
						StringBuffer sb_msg = new StringBuffer();
						sb_msg.append(lo.getCode() + " ");
						if (lo_old != null) {
							sb_msg.append(lo_old.getsZaiko());
						} else {
							sb_msg.append("null");
						}
						sb_msg.append(">").append(lo.getsZaiko());
						z_rireki.setMessage(sb_msg.toString());
						z_rireki.setUser(loginuser);
						z_rireki.setUser_id(loginuser_id);
						z_rireki.setType("shingu");
						zaikorirekis.add(z_rireki);
					}

					lo.setUser(loginuser);
					lo.setUser_id(loginuser_id);
					lo.setUpdate(nowtime);
					lo.setRouter_index(active);
					locationService.editlocation(lo);

					// 在庫変更
					if (active == 3) {
						zaiko_lo.add(lo);
						zaikorireki z_rireki = new zaikorireki();
						z_rireki.setUpdate(nowtime);
						StringBuffer sb_msg = new StringBuffer();
						sb_msg.append(lo.getCode() + " ");
						if (lo_old != null) {
							sb_msg.append(lo_old.getZaiko());
						} else {
							sb_msg.append("null");
						}
						sb_msg.append(">").append(lo.getZaiko());
						z_rireki.setMessage(sb_msg.toString());
						z_rireki.setUser(loginuser);
						z_rireki.setUser_id(loginuser_id);
						z_rireki.setType("zaiko");
						zaikorirekis.add(z_rireki);
					}
				}

				if (zaikorirekis.size() > 0) {
					for (int i = 0; i < zaikorirekis.size(); i++) {
						zaikorirekiService.insert(zaikorirekis.get(i));
					}
				}

				List<String> zaiko_list = new ArrayList<String>();
				if (zaiko_lo.size() > 0) {
					for (int i = 0; i < zaiko_lo.size(); i++) {
						StringBuffer sb = new StringBuffer();
						String now = sf5.format(new Date());
						sb.append("update,").append(zaiko_lo.get(i).getCode()).append(",")
								.append(zaiko_lo.get(i).getZaiko()).append(",").append(loginuser).append(",")
								.append(now);
						zaiko_list.add(sb.toString());
						CommonUtil.writeDataHubData(zaiko_list, "xampp\\htdocs\\ordery\\logi\\zaiko\\update\\" + zaiko_lo.get(i).getCode() + "_" + now);
					}
				}
			}
			bakupLogi();
			return object;
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return object;
	}

	@ResponseBody
	@RequestMapping(value = "/exportLocation", method = RequestMethod.POST)
	private JSONObject exportLocation(HttpServletResponse response, HttpServletRequest request) {
		JSONObject object = new JSONObject();
		try {
			DynamicDataSourceHolder.setDataSource("jrt_dataSource");
			List<location> lo = locationService.getlocation("false", "false", "", "コード昇順", "", "", "", "", "", "", "",
					"", "false", 0, 0);

			object.put("rows", lo);
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Cache-Control", "no-cache");
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return object;
	}

	@ResponseBody
	@RequestMapping(value = "/deleteLocation", method = RequestMethod.POST)
	private JSONObject deleteLocation(HttpServletResponse response, HttpServletRequest request, String id) {
		JSONObject object = new JSONObject();
		try {
			DynamicDataSourceHolder.setDataSource("jrt_dataSource");

			String[] ids = id.split(",");
			locationService.deleteLocationByIds(ids);

			result result = new result();
			result.setState(1);
			result.setMsg("削除しました！");
			object.put("rows", result);

			bakupLogi();
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Cache-Control", "no-cache");
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return object;
	}

	// 共通
	private void bakupLogi() {
		try {
			DynamicDataSourceHolder.setDataSource("jrt_dataSource");
			List<location> lo = locationService.getlocation("false", "false", "", "コード昇順", "", "", "", "", "", "", "",
					"", "false", 0, 0);
			// 构造导出数据结构
			String titles = "商品コード,商品名,商品分類タグ,代表商品コード,ship-weight,ロケーション,梱包サイズ,特殊,sw2"; // 设置表头
			String keys = "code,name,tag,dcode,sw,kaisou,ksize,sp,sw2"; // 设置每列字段

			// 构造导出数据
			List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
			Map<String, Object> map = null;
			for (location lo_ : lo) {
				map = new HashMap<String, Object>();
				map.put("code", lo_.getCode());
				map.put("name", lo_.getName());
				map.put("tag", lo_.getTag());
				map.put("dcode", lo_.getDcode());
				map.put("sw", lo_.getSw());
				map.put("kaisou", lo_.getKaisou() + lo_.getTana());
				map.put("ksize", lo_.getKsize());
				map.put("sp", lo_.getSp());
				map.put("sw2", lo_.getSw2());
				datas.add(map);
			}

			if(config.ISLOCAL) {
				place = config.LOCATION_PLACE_LOCAL;
			} else {
				place = config.LOCATION_PLACE_SERVER;
			}

			// 设置导出文件前缀
			File file = new File(place + "xampp\\htdocs\\ordery\\\\logi\\backup\\" + sf4.format(new Date()) + ".csv");
			if (!file.exists()) { // 文件不存在则创建文件，先创建目录
				file.createNewFile();
			}
			FileOutputStream os = new FileOutputStream(file);
			CsvExportUtil.doExport(datas, titles, keys, os);
			os.close();
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	// csvでアップデート
	// type=> 1:ネクストエンジン在庫 2:「項目全て」登録編集 3:ネクストエンジンcsv登録 4:csvで「削除」する
	private location_up UpdateLocationByCsv(Integer type, String file_name, MultipartFile csvfile, int loginuser_id,
			String loginuser) throws Exception {
		location_up location_up = new location_up();
		File newfile = null;
		InputStream is = null;

		if (type == 1) {
			if (!file_name.endsWith("csv")) {
				location_up.setErrorno(1);
				return location_up;
			}
			newfile = new File(file_name);
			Path path_file = Paths.get(file_name);

			if(config.ISLOCAL) {
				place = config.LOCATION_PLACE_LOCAL;
			} else {
				place = config.LOCATION_PLACE_SERVER;
			}
			String dest = place + "xampp\\htdocs\\ordery\\upload\\" + newfile.getName();
			File destfile = new File(dest);
			if (!destfile.exists()) {
				destfile.createNewFile();
				Path path_dest = Paths.get(dest);
				Files.copy(path_file, path_dest, StandardCopyOption.REPLACE_EXISTING);
				file_name = dest;
			}
			is = new FileInputStream(file_name);
		} else {
			is = csvfile.getInputStream();
		}

		InputStreamReader isr = new InputStreamReader(is);
		String encode = isr.getEncoding();

//		String encode = FileCharDetecter.detector();
//		System.out.println(encode);//SHIFT_JIS

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		if ("SHIFT_JIS".equals(encode) || "MS932".equals(encode)) {
			reader = new BufferedReader(new InputStreamReader(is, "SHIFT_JIS"));
		}
//		InputStreamReader in = new InputStreamReader(is, "SHIFT_JIS");
//		BufferedReader reader = new BufferedReader(new InputStreamReader(is, "SHIFT_JIS"));
		try {
//			https://www.cnblogs.com/bretgui/p/10156141.html
			DynamicDataSourceHolder.setDataSource("jrt_dataSource");
			List<location> lo = locationService.getlocation("false", "true", "", "コード昇順", "", "", "", "", "", "", "",
					"", "false", 0, 0);
			String[] checkCodeArray = new String[lo.size()]; // 商品コードをチェック用配列に入れる
			String[] checkStrArray = new String[lo.size()]; // 修正があるかチェックする配列
			String[] checkLocationArray = new String[lo.size()]; // ロケーションのチェック配列
			for (int i = 0; i < checkCodeArray.length; i++) {
				checkCodeArray[i] = lo.get(i).getCode().toLowerCase();
				StringBuffer sb = new StringBuffer();
				StringBuffer sb2 = new StringBuffer();
				if (type == 2) {
					sb.append(lo.get(i).getCode().toLowerCase());
					if (lo.get(i).getName() != null) {
						sb.append(lo.get(i).getName());
					}
					if (lo.get(i).getTag() != null) {
						sb.append(lo.get(i).getTag());
					}
					if (lo.get(i).getDcode() != null) {
						sb.append(lo.get(i).getDcode());
					}
					if (lo.get(i).getSw() != null) {
						sb.append(lo.get(i).getSw());
					}
					if (lo.get(i).getKaisou() != null) {
						sb.append(lo.get(i).getKaisou());
					}
					if (lo.get(i).getTana() != null) {
						sb.append(lo.get(i).getTana());
					}
					if (lo.get(i).getKsize() != null) {
						sb.append(lo.get(i).getKsize());
					}
					if (lo.get(i).getSp() != null) {
						sb.append(lo.get(i).getSp());
					}
					if (lo.get(i).getsZaiko() != null) {
						sb.append(lo.get(i).getsZaiko());
					}
					if (lo.get(i).getsBikou() != null) {
						sb.append(lo.get(i).getsBikou());
					}

					checkStrArray[i] = sb.toString();
				} else if (type == 1) {
					sb.append(lo.get(i).getCode().toLowerCase());
					if (lo.get(i).getZaiko() != null) {
						sb.append(lo.get(i).getZaiko());
					}
					if (lo.get(i).getHikiate() != null) {
						sb.append(lo.get(i).getHikiate());
					}
					if (lo.get(i).getYoyaku() != null) {
						sb.append(lo.get(i).getYoyaku());
					}
					if (lo.get(i).getT_kbn() != null) {
						sb.append(lo.get(i).getT_kbn());
					}
					checkStrArray[i] = sb.toString();
				} else {
					sb.append(lo.get(i).getCode().toLowerCase());
					if (lo.get(i).getName() != null) {
						sb.append(lo.get(i).getName());
					}
					if (lo.get(i).getTag() != null) {
						sb.append(lo.get(i).getTag());
					}
					if (lo.get(i).getDcode() != null) {
						sb.append(lo.get(i).getDcode());
					}
					checkStrArray[i] = sb.toString();
				}

				sb2.append(lo.get(i).getCode().toLowerCase());
				if (lo.get(i).getKaisou() != null) {
					sb2.append(lo.get(i).getKaisou());
				}
				if (lo.get(i).getTana() != null) {
					sb2.append(lo.get(i).getTana());
				}
				checkLocationArray[i] = sb2.toString();
			}

			String line = null;
			int num = 0;

			Integer codeNo = null;
			Integer nameNo = null;
			Integer hikiateNo = null;
			Integer katabanNo = null;
			Integer tagNo = null;
			Integer dcodeNo = null;
			Integer locaNo = null;
			Integer ksizeNo = null;
			Integer spNo = null;
			Integer zaikoNo = null;
			Integer t_kbnNo = null;
			Integer yoyakuNo = null;
			Integer sZaikoNo = null;
			Integer sBikouNo = null;

			int changeCount = 0;
			int newCount = 0;
			int delCount = 0;
			List<location> lo_update = new ArrayList<location>();
			List<location> lo_add = new ArrayList<location>();
			List<location> lo_del = new ArrayList<location>();
			Date nowtime = new Date();

			while ((line = reader.readLine()) != null) {
				String item[] = line.split(",");// CSV格式文件为逗号分隔符文件，这里根据逗号切分
//				System.out.println(getValue(item, 0));
				if (type == 1 || type == 2 || type == 3 || type == 5) {
					if (num == 0) {// ヘッダー取込
						if (type == 1 || type == 5) {// ネクストエンジン在庫
							if (!CommonUtil.isHave(item, "") || !CommonUtil.isHave(item, "商品名")
									|| !CommonUtil.isHave(item, "売価") || !CommonUtil.isHave(item, "原価")
									|| !CommonUtil.isHave(item, "商品区分") || !CommonUtil.isHave(item, "取扱区分")
									|| !CommonUtil.isHave(item, "在庫数") || !CommonUtil.isHave(item, "引当数")
									|| !CommonUtil.isHave(item, "フリー在庫") || !CommonUtil.isHave(item, "予約在庫")
									|| !CommonUtil.isHave(item, "予約引当") || !CommonUtil.isHave(item, "予約フリー")
									|| !CommonUtil.isHave(item, "JANコード") || !CommonUtil.isHave(item, "型番")
									|| !CommonUtil.isHave(item, "商品分類タグ") || !CommonUtil.isHave(item, "代表商品コード")) {
								location_up.setErrorno(2);
								return location_up;
							}
						} else if (type == 2) {
							if (!CommonUtil.isHave(item, "商品コード") || !CommonUtil.isHave(item, "商品名")
									|| !CommonUtil.isHave(item, "商品分類タグ") || !CommonUtil.isHave(item, "代表商品コード")
									|| !CommonUtil.isHave(item, "型番") || !CommonUtil.isHave(item, "ロケーション")
									|| !CommonUtil.isHave(item, "梱包サイズ") || !CommonUtil.isHave(item, "特殊")) {
								location_up.setErrorno(2);
								return location_up;
							}
						} else if (type == 3) {
							if (!CommonUtil.isHave(item, "商品コード") || !CommonUtil.isHave(item, "商品名")
									|| !CommonUtil.isHave(item, "型番") || !CommonUtil.isHave(item, "商品分類タグ")
									|| !CommonUtil.isHave(item, "代表商品コード")) {
								location_up.setErrorno(2);
								return location_up;
							}
						}
						for (int i = 0; i < item.length; i++) {
							if ("商品コード".equals(getValue(item, i))) {
								codeNo = i;
							} else if ("商品名".equals(getValue(item, i))) {
								nameNo = i;
							} else if ("引当数".equals(getValue(item, i))) {
								hikiateNo = i;
							} else if ("型番".equals(getValue(item, i)) || "ship-weight".equals(getValue(item, i))) {
								katabanNo = i;
							} else if ("商品分類タグ".equals(getValue(item, i))) {
								tagNo = i;
							} else if ("代表商品コード".equals(getValue(item, i))) {
								dcodeNo = i;
							} else if ("ロケーション".equals(getValue(item, i))) {
								locaNo = i;
							} else if ("梱包サイズ".equals(getValue(item, i))) {
								ksizeNo = i;
							} else if ("特殊".equals(getValue(item, i))) {
								spNo = i;
							} else if ("在庫数".equals(getValue(item, i))) {
								zaikoNo = i;
							} else if ("取扱区分".equals(getValue(item, i))) {
								t_kbnNo = i;
							} else if ("予約在庫".equals(getValue(item, i))) {
								yoyakuNo = i;
							} else if ("新宮在庫".equals(getValue(item, i))) {
								sZaikoNo = i;
							} else if ("新宮在庫備考".equals(getValue(item, i))) {
								sBikouNo = i;
							}
						}
					} else {
						// チェックする項目を一文にして文字列で検索できるようにする
						String chStr = "";
						String chtag = "";
						StringBuffer sb = new StringBuffer();
						// チェックする項目を一文にして文字列で検索できるようにする
						String code = getValue(item, codeNo).toLowerCase();
						String name = getValue(item, nameNo);
						String tag = "";
						if (tagNo != null) {
							tag = getValue(item, tagNo);
						}

						String dcode = "";
						if (dcodeNo != null) {
							dcode = getValue(item, dcodeNo);
						}

						String kataban = "";
						if (katabanNo != null) {
							kataban = getValue(item, katabanNo);
						}

						String loca = "";
						if (locaNo != null) {
							loca = getValue(item, locaNo);
						}

						String ksize = "";
						if (ksizeNo != null) {
							ksize = getValue(item, ksizeNo);
						}

						String sp = "";
						if (spNo != null) {
							sp = getValue(item, spNo);
						}

						Integer zaiko = null;
						if (zaikoNo != null) {
							zaiko = Integer.parseInt(getValue(item, zaikoNo));
						}

						Integer hikiate = null;
						if (hikiateNo != null) {
							hikiate = Integer.parseInt(getValue(item, hikiateNo));
						}

						String yoyaku = "";
						if (yoyakuNo != null) {
							yoyaku = getValue(item, yoyakuNo);
						}

						String t_kbn = "";
						if (t_kbnNo != null) {
							t_kbn = getValue(item, t_kbnNo);
						}

						Integer sZaiko = null;
						if (sZaikoNo != null) {
							sZaiko = Integer.parseInt(getValue(item, sZaikoNo));
						} else {
							sZaiko = 0;
						}

						String sBikou = "";
						if (sBikouNo != null) {
							sBikou = getValue(item, sBikouNo);
						}

						if (type == 2) {
							sb.append(code).append(name).append(tag).append(dcode).append(kataban).append(loca)
									.append(ksize).append(sp);
							if (sZaiko != null) {
								sb.append(sZaiko);
							}
							sb.append(sBikou);
							chStr = sb.toString();
						} else if (type == 1 || type == 5) {
							sb.append(code);
							if (zaiko != null) {
								sb.append(zaiko);
							}
							if (hikiate != null) {
								sb.append(hikiate);
							}
							sb.append(yoyaku).append(t_kbn);
							chStr = sb.toString();
						} else {
							sb.append(code).append(name).append(tag).append(dcode);
							chStr = sb.toString();
							if (tag.indexOf("物流倉庫") != -1) {
								chtag = "BS99";
							} else if (tag.indexOf("井相田") != -1) {
								chtag = "SD88";
							} else {
								chtag = "";
							}
						}

						// 修正点があるか確認する
						if (Arrays.asList(checkCodeArray).contains(code)) { // コードが登録済みか？
							if (!Arrays.asList(checkStrArray).contains(chStr)) { // 登録内容の確認
								location lo_update_ = new location();
								lo_update_.setUser(loginuser);
								lo_update_.setUser_id(loginuser_id);
								lo_update_.setUpdate(nowtime);
//								if (type == 2) {
//									String kaisouStr = "";
//									String tanaStr = "";
//									if (loca.length() > 4) {
//										kaisouStr = loca.substring(0, 2);
//										tanaStr = loca.substring(2, 3);
//									}
//									lo_update_.setCode(code);
//									lo_update_.setName(name);
//									lo_update_.setTag(tag);
//									lo_update_.setDcode(dcode);
//									lo_update_.setSw(kataban);
//									lo_update_.setKaisou(kaisouStr);
//									lo_update_.setTana(tanaStr);
//									lo_update_.setKsize(ksize);
//									lo_update_.setSp(sp);
//									lo_update_.setsZaiko(sZaiko);
//									lo_update_.setsBikou(sBikou);
//								} else if (type == 1 || type == 5) {
//									lo_update_.setCode(code);
//									lo_update_.setZaiko(zaiko);
//									lo_update_.setHikiate(hikiate);
//									lo_update_.setYoyaku(yoyaku);
//									lo_update_.setT_kbn(t_kbn);
//									lo_update_.setZaiko_update(nowtime);
//								} else if (type == 3) {
//									String kaisouStr = "";
//									String tanaStr = "";
//									if (loca.length() > 4) {
//										kaisouStr = loca.substring(0, 2);
//										tanaStr = loca.substring(2, 3);
//									}
//									lo_update_.setCode(code);
//									lo_update_.setName(name);
//									lo_update_.setTag(tag);
//									lo_update_.setDcode(dcode);
//									lo_update_.setKaisou(kaisouStr);
//									lo_update_.setTana(tanaStr);
//								}

								String kaisouStr = "";
								String tanaStr = "";
								if (loca.length() > 4) {
									kaisouStr = loca.substring(0, 2);
									tanaStr = loca.substring(2, 3);
								}

								lo_update_.setCode(code);
								lo_update_.setName(name);

								lo_update_.setTag(tag);
								lo_update_.setDcode(dcode);
								lo_update_.setSw(kataban);
								lo_update_.setKaisou(kaisouStr);
								lo_update_.setTana(tanaStr);
								lo_update_.setKsize(ksize);
								lo_update_.setSp(sp);
								lo_update_.setsZaiko(sZaiko);
								lo_update_.setsBikou(sBikou);

								lo_update_.setZaiko(zaiko);
								lo_update_.setHikiate(hikiate);
								lo_update_.setYoyaku(yoyaku);
								lo_update_.setT_kbn(t_kbn);
								lo_update_.setZaiko_update(nowtime);

								lo_update_.setKaisou(kaisouStr);

								if (tag.indexOf("物流倉庫") != -1) {
									lo_update_.setKaisou("BS");
									lo_update_.setTana("99");
								} else if (tag.indexOf("井相田") != -1) {
									lo_update_.setKaisou("SD");
									lo_update_.setTana("88");
								}
								lo_update.add(lo_update_);
								changeCount++;
							}
						} else { // コードの登録が無ければ登録
							location lo_add_ = new location();
							lo_add_.setUpdate(nowtime);
							lo_add_.setUser(loginuser);
							lo_add_.setUser_id(loginuser_id);
							if (type == 2) {// 「項目全て」登録編集
								String kaisouStr = "";
								String tanaStr = "";
								if (loca.length() > 4) {
									kaisouStr = loca.substring(0, 2);
									tanaStr = loca.substring(2, 3);
								}
								lo_add_.setCode(code);
								lo_add_.setName(name);
								lo_add_.setTag(tag);
								lo_add_.setDcode(dcode);
								lo_add_.setSw(kataban);
								lo_add_.setKaisou(kaisouStr);
								lo_add_.setTana(tanaStr);
								lo_add_.setKsize(ksize);
								lo_add_.setSp(sp);
								lo_add_.setsZaiko(sZaiko);
								lo_add_.setsBikou(sBikou);
							} else if (type == 1 || type == 5) {
								lo_add_.setCode(code);
								lo_add_.setZaiko(zaiko);
								lo_add_.setHikiate(hikiate);
								lo_add_.setYoyaku(yoyaku);
								lo_add_.setT_kbn(t_kbn);
							} else if (type == 3) {
								String ln = code + chtag;
								if (tag.indexOf("物流倉庫") != -1) {
									if (!Arrays.asList(checkLocationArray).contains(ln)) {
										lo_add_.setCode(code);
										lo_add_.setName(name);
										lo_add_.setTag(tag);
										lo_add_.setDcode(dcode);
										lo_add_.setKaisou("BS");
										lo_add_.setTana("99");
									}
								} else if (tag.indexOf("井相田") != -1) {
									if (!Arrays.asList(checkLocationArray).contains(ln)) {
										lo_add_.setCode(code);
										lo_add_.setName(name);
										lo_add_.setTag(tag);
										lo_add_.setDcode(dcode);
										lo_add_.setKaisou("SD");
										lo_add_.setTana("88");
									}
								} else {
									if (!Arrays.asList(checkLocationArray).contains(ln)) {
										lo_add_.setCode(code);
										lo_add_.setName(name);
										lo_add_.setTag(tag);
										lo_add_.setDcode(dcode);
									}
								}
							}
							lo_add.add(lo_add_);
							newCount++;
						}
					}
				} else if (type == 4) {
					if (num == 0) {// ヘッダー取込
						if (type == 4) {// csvで「削除」する
							if (!Arrays.asList(item).contains("商品コード")) {
								location_up.setErrorno(2);
								return location_up;
							}
						}
						for (int i = 0; i < item.length; i++) {
							if ("商品コード".equals(getValue(item, i))) {
								codeNo = i;
							}
						}
					} else {
						String code = getValue(item, codeNo);
						if (Arrays.asList(checkCodeArray).contains(code)) {
							location lo_del_ = new location();
							lo_del_.setCode(code);
							lo_del.add(lo_del_);
							delCount++;
						}
					}
				}
				num++;
			}

			if (lo_update.size() > 0) {
				if (type == 1 || type == 5) {
					locationService.updateByCSV1(lo_update);
				} else if (type == 2) {
					locationService.updateByCSV2(lo_update);
				} else if (type == 3) {
					locationService.updateByCSV3(lo_update);
				}
			}

			if (lo_add.size() > 0) {
				if (type == 2) {
					locationService.insertByCSV2(lo_add);
				} else {
					locationService.insertByCSV1(lo_add);
				}
			}

			if (lo_del != null && lo_del.size() > 0) {
//				DynamicDataSourceHolder.setDataSource("jrt_dataSource");
				locationService.deleteByCSV(lo_del);
			}
			location_up.setNewCount(newCount);
			location_up.setChangeCount(changeCount);
			location_up.setDelCount(delCount);
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} finally {
			is.close();
			isr.close();
			reader.close();
		}
		return location_up;
	}

//	private static void columnDataHandler(Iterator<Cell> cellList) {
//		while (cellList.hasNext()) {
//			Cell cell = cellList.next();
//			switch (cell.getCellTypeEnum()) {
//			case NUMERIC:
//				System.out.println(cell.getNumericCellValue() + " \t\t ");
//				break;
//			case STRING:
//				System.out.println(cell.getStringCellValue() + " \t\t ");
//				break;
//			case BOOLEAN:
//				System.out.println(cell.getBooleanCellValue() + " \t\t ");
//				break;
//			default:
//				System.out.println(cell.getStringCellValue() + " \t\t ");
//				break;
//			}
//		}
//	}

	@ResponseBody
	@RequestMapping(value = "/getShiGuRireki", method = RequestMethod.POST)
	private JSONObject getShiGuRireki(HttpServletResponse response, HttpServletRequest request,
			String rireki_shingu_view, int rireki_shingu_num) {
		JSONObject object = new JSONObject();
		try {
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Cache-Control", "no-cache");
			DynamicDataSourceHolder.setDataSource("jrt_dataSource");

			List<zaikorireki> zaikorirekis = zaikorirekiService.getShiGuRirekiByView(rireki_shingu_view,
					rireki_shingu_num);
			if (zaikorirekis.size() > 0) {
				for (int i = 0; i < zaikorirekis.size(); i++) {
					zaikorirekis.get(i).setUser(zaikorirekis.get(i).getUser().substring(0, 2));
				}
			}

			object.put("rows", zaikorirekis);
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return object;
	}

	@ResponseBody
	@RequestMapping(value = "/getRireki", method = RequestMethod.POST)
	private JSONObject getRireki(HttpServletResponse response, HttpServletRequest request, String rireki_view,
			int rireki_num) {
		JSONObject object = new JSONObject();
		try {
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Cache-Control", "no-cache");
			DynamicDataSourceHolder.setDataSource("jrt_dataSource");

			List<zaikorireki> zaikorirekis = zaikorirekiService.getRirekiByView(rireki_view, rireki_num);
			if (zaikorirekis.size() > 0) {
				for (int i = 0; i < zaikorirekis.size(); i++) {
					zaikorirekis.get(i).setUser(zaikorirekis.get(i).getUser().substring(0, 2));
//					if(zaikorirekis.get(i).getResult() != null) {
//						zaikorirekis.get(i).setResult(zaikorirekis.get(i).getResult().trim());
//					} else {
//						zaikorirekis.get(i).setResult("");
//					}
					if (zaikorirekis.get(i).getResult() != null) {
						zaikorirekis.get(i)
								.setResult(zaikorirekis.get(i).getResult().replace(" ", "").replace("\r\n", ""));
					} else {
						zaikorirekis.get(i).setResult("");
					}
				}
			}

			object.put("rows", zaikorirekis);
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return object;
	}

	public static String getValue(String[] item, int index) {
		String value = "";
		if (item == null) {

		} else if (item.length > index) {
			value = item[index].replace("\"", "");
		}
		if ("null".equals(value)) {
			value = "";
		}
		return value;
	}

//public static Date getCsvDate(String item) throws Exception{
//
//    if(item.indexOf("/") > 0){
//        item = item.replaceAll("/", "-");
//    }else if(item.indexOf("年") > 0){
//        item = item.replaceAll("年", "-").replaceAll("月", "-").replaceAll("日","");
//    }
//
//    Date birth = DateUtils.parse(item);
//    Date defaultDate = DateUtils.parse("1900-01-01");
//
//    if(birth.getTime() <= defaultDate.getTime()){
//        return defaultDate;
//    }
//    return birth;
//}

}
