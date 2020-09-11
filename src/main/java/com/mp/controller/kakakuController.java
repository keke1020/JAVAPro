package com.mp.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.mp.common.DynamicDataSourceHolder;
import com.mp.entity.config;
import com.mp.entity.file;
import com.mp.entity.kakaku;
import com.mp.service.kakakuService;
import com.mp.util.CommonUtil;

@Controller
public class kakakuController {
	@Autowired
	private kakakuService kakakuService;

	@ResponseBody
	@RequestMapping(value = "/getKakaku", method = RequestMethod.POST)
	private JSONObject getKakaku(HttpServletResponse response, HttpServletRequest request,
			String sort, int currentPage, int pagesize) {
		DynamicDataSourceHolder.setDataSource("jrt_dataSource");
		JSONObject object = new JSONObject();

		String place = "";
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");

			if(config.ISLOCAL) {
				place = config.LOCATION_PLACE_LOCAL;
			} else {
				place = config.LOCATION_PLACE_SERVER;
			}

			String masterPath = place + "xampp\\htdocs\\orderA\\dl_master\\master.csv";
			String masterDatePath = place + "xampp\\htdocs\\orderm\\data\\kakaku_master.txt";
			String masterDB_Path = place + "xampp\\htdocs\\orderm\\kakakuDB\\master.csv";

			File masterDatePath_file = new File(masterDatePath);
			BufferedReader br = new BufferedReader(new FileReader(masterDatePath_file));
			Date masterDate = new Date(br.readLine());
			Long masterDate_long = masterDate.getTime() / 1000;

			// masterPath
			Path path_master = Paths.get(masterPath);
			BasicFileAttributeView basicview_master = Files.getFileAttributeView(path_master,
					BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
			BasicFileAttributes attr_master = basicview_master.readAttributes();
			Instant instant_master = attr_master.lastModifiedTime().toInstant();
			Long master_long = instant_master.getEpochSecond();

			boolean updateFlag = false;
			if (!master_long.equals(masterDate_long)) {
				updateFlag = true;
			}

			if(updateFlag) {
				Path path_masterDB = Paths.get(masterDB_Path);
				Files.copy(path_master, path_masterDB, StandardCopyOption.REPLACE_EXISTING);
			}

			InputStream is_mdb = new FileInputStream(masterDB_Path);
			InputStreamReader isr_mdb = new InputStreamReader(is_mdb);
			String encode = isr_mdb.getEncoding();
//			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//			if ("SHIFT_JIS".equals(encode) || "MS932".equals(encode)) {
//				reader = new BufferedReader(new InputStreamReader(is_mdb, "SHIFT_JIS"));
//			}
			BufferedReader reader = new BufferedReader(new InputStreamReader(is_mdb, "UTF-8"));

			List<kakaku> kakakus_match = new ArrayList<kakaku>();
			int num = 0;
			String line = null;
			Integer codeNo = null;
			Integer h_tzaiko_No = null;
			Integer h_yzaiko_No = null;

			while ((line = reader.readLine()) != null) {
				String item[] = line.split(",");
				if(num == 0) {
					if (!CommonUtil.isHave(item, "商品コード")
						|| !CommonUtil.isHave(item, "在庫数") || !CommonUtil.isHave(item, "予約在庫")) {
						break;
					}
					for (int i = 0; i < item.length; i++) {
						if ("商品コード".equals(getValue(item, i))) {
							codeNo = i;
						} else if ("在庫数".equals(getValue(item, i))) {
							h_tzaiko_No = i;
						} else if ("予約在庫".equals(getValue(item, i))) {
							h_yzaiko_No = i;
						}
					}
				} else {
					kakaku k = new kakaku();
					String codeStr = getValue(item, codeNo);
					if(codeStr != null && !"".equals(codeStr)) {
						String[] code_s = codeStr.split("-");
						k.setCode(code_s[0]);
					}

					k.setH_tzaiko(getValue(item, h_tzaiko_No));
					k.setH_yzaiko(getValue(item, h_yzaiko_No));
					kakakus_match.add(k);
				}
				num++;
			}

			int list_currentPage = (currentPage - 1) * pagesize;
			List<kakaku> kakakus = kakakuService.getKakaku(list_currentPage, pagesize, sort);
			int total = kakakuService.getTotal();

			for (int i = 0; i < kakakus.size(); i++) {
				kakakus.get(i).setChk(false);
				int haiso_ryo = 0;

				if(kakakus.get(i).getHaiso().contains("大型")) {
					kakakus.get(i).setHaiso_short("大型");
					haiso_ryo = 1000;
				} else if (kakakus.get(i).getHaiso().contains("宅配便")) {
					kakakus.get(i).setHaiso_short("宅");
					haiso_ryo = 500;
				} else if (kakakus.get(i).getHaiso().contains("メール便")) {
					kakakus.get(i).setHaiso_short("メ");
					haiso_ryo = 100;
				} else if (kakakus.get(i).getHaiso().contains("定形外")) {
					kakakus.get(i).setHaiso_short("定外");
					haiso_ryo = 100;
				} else {
					kakakus.get(i).setHaiso_short("/");
				}
				kakakus.get(i).setHaiso_ryo(haiso_ryo);

				double sale_kagen_ = (kakakus.get(i).getGenka() + haiso_ryo) *  1.4;
				int sale_kagen = (int)sale_kagen_;
				kakakus.get(i).setSale_kagen(sale_kagen);

				int syobun_kagen_ = (int)(kakakus.get(i).getGenka() *  1.25);
				kakakus.get(i).setSyobun_kagen_(syobun_kagen_);
				int syobun_kagen = syobun_kagen_  + haiso_ryo;
				kakakus.get(i).setSyobun_kagen(syobun_kagen);

				int h_tzaiko = 0;
				int h_yzaiko = 0;
				for (int j = 0; j < kakakus_match.size(); j++) {
					if(kakakus.get(i).getCode().equals(kakakus_match.get(j).getCode())) {
						h_tzaiko = h_tzaiko + Integer.parseInt(kakakus_match.get(j).getH_tzaiko());
						h_yzaiko = h_yzaiko + Integer.parseInt(kakakus_match.get(j).getH_yzaiko());
					}
				}
				kakakus.get(i).setH_tzaiko(String.valueOf(h_tzaiko));
				kakakus.get(i).setH_yzaiko(String.valueOf(h_yzaiko));

				String[] code_s = kakakus.get(i).getCode().split("-");
				if(code_s.length > 1) {
					kakakus.get(i).setHasEda(1);
				} else {
					kakakus.get(i).setHasEda(0);
				}
			}

			object.put("rows", kakakus);
			object.put("total", total);
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Cache-Control", "no-cache");
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return object;
	}













	public String getValue(String[] item, int index) {
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


}
