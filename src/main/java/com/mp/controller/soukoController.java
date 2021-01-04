package com.mp.controller;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.mp.common.DynamicDataSourceHolder;
import com.mp.dto.result;
import com.mp.entity.config;
import com.mp.entity.souko;
import com.mp.entity.zaikorireki;
import com.mp.service.soukoService;
import com.mp.service.zaikorirekiService;

@Controller
public class soukoController {

	@Autowired
	private soukoService soukoService;

	@Autowired
	private zaikorirekiService zaikorirekiService;

	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.JAPAN);
	SimpleDateFormat sf2 = new SimpleDateFormat("yyyyMMddHHmmss", Locale.JAPAN);
	SimpleDateFormat sf3 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.JAPAN);
	SimpleDateFormat sf4 = new SimpleDateFormat("yyyyMMddHH", Locale.JAPAN);
	SimpleDateFormat sf5 = new SimpleDateFormat("yyyyMMddHHmmss", Locale.JAPAN);

	private String dataSource = "";

	@ResponseBody
	@RequestMapping(value = "/getSouko", method = RequestMethod.POST)
	private JSONObject getSouko(HttpServletResponse response, HttpServletRequest request) {
		JSONObject object = new JSONObject();
		if (config.ISLOCAL) {
			dataSource = config.DATASOURCE_LOCAL_YUBIN;
		} else {
			dataSource = config.DATASOURCE_SERVER_YUBIN;
		}
		DynamicDataSourceHolder.setDataSource(dataSource);

		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Cache-Control", "no-cache");

			String place = "";
			if (config.ISLOCAL) {
				place = config.LOCATION_PLACE_LOCAL;
			} else {
				place = config.LOCATION_PLACE_SERVER;
			}
			String masterPath = place + "xampp\\htdocs\\ordery\\soukoDB\\master.csv";
			Path path_master = Paths.get(masterPath);
			BasicFileAttributeView basicview_master = Files.getFileAttributeView(path_master,
					BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
			BasicFileAttributes attr_master = basicview_master.readAttributes();
			Instant instant_master = attr_master.lastModifiedTime().toInstant();
			Date master_d = Date.from(instant_master);
			String master_ds = sf.format(master_d);

			String isNagoya = request.getParameter("isNagoya");
			String istana = request.getParameter("istana");
			String orderSC = request.getParameter("orderSC");
			String t_kbn = request.getParameter("t_kbn");
			String containerSc = request.getParameter("containerSc");
			String codeSc = request.getParameter("codeSc");
			String kaisosc = request.getParameter("kaisosc");
			String tanaSc = request.getParameter("tanaSc");
			String kosuSC1 = request.getParameter("kosuSC1");
			String kosuSC2 = request.getParameter("kosuSC2");
			String zaikoOnly = request.getParameter("zaikoOnly");

			int searchCount = Integer
					.parseInt(new String(request.getParameter("searchCount").getBytes("ISO-8859-1"), "UTF-8"));
			int list_currentPage = Integer
					.parseInt(new String(request.getParameter("list_currentPage").getBytes("ISO-8859-1"), "UTF-8"));
			list_currentPage = (list_currentPage - 1) * searchCount;

			List<souko> souko = new ArrayList<souko>();
			souko = soukoService.getSouko(isNagoya, istana, orderSC, t_kbn,
					codeSc, containerSc, kaisosc, tanaSc, kosuSC1, kosuSC2, list_currentPage,
					searchCount, zaikoOnly);
			int total = soukoService.getTotal(isNagoya, istana, orderSC, t_kbn,
					codeSc, containerSc, kaisosc, tanaSc, kosuSC1, kosuSC2, zaikoOnly);

			object.put("souko", souko);
			object.put("total", total);
			object.put("mDate", master_ds);
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		return object;
	}

	@ResponseBody
	@RequestMapping(value = "/getNagoyaRireki", method = RequestMethod.POST)
	private JSONObject getNagoyaRireki(HttpServletResponse response, HttpServletRequest request, String codeSc,
			String updateSc_s, String updateSc_e, String orderSC) {
		JSONObject object = new JSONObject();
		if (config.ISLOCAL) {
			dataSource = config.DATASOURCE_LOCAL_YUBIN;
		} else {
			dataSource = config.DATASOURCE_SERVER_YUBIN;
		}
		DynamicDataSourceHolder.setDataSource(dataSource);

		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Cache-Control", "no-cache");

			int searchCount = Integer
					.parseInt(new String(request.getParameter("searchCount").getBytes("ISO-8859-1"), "UTF-8"));
			int list_currentPage = Integer
					.parseInt(new String(request.getParameter("rireki_curr").getBytes("ISO-8859-1"), "UTF-8"));
			list_currentPage = (list_currentPage - 1) * searchCount;

			List<zaikorireki> rireki = zaikorirekiService.getNagoyaRireki(codeSc, updateSc_s, updateSc_e,orderSC,
					list_currentPage, searchCount);
			int total = zaikorirekiService.getNagoyaRireki_total(codeSc, updateSc_s, updateSc_e,orderSC);
			object.put("rireki", rireki);
			object.put("total", total);
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		return object;
	}

	@ResponseBody
	@RequestMapping(value = "/editSouko", method = RequestMethod.POST)
	private JSONObject editLocation(HttpServletResponse response, HttpServletRequest request,
			@RequestBody String params, int active) {
		JSONObject object = new JSONObject();
		if (config.ISLOCAL) {
			dataSource = config.DATASOURCE_LOCAL_YUBIN;
		} else {
			dataSource = config.DATASOURCE_SERVER_YUBIN;
		}
		DynamicDataSourceHolder.setDataSource(dataSource);

		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Cache-Control", "no-cache");

			int loginuser_id = Integer
					.parseInt(new String(request.getParameter("loginuser_id").getBytes("ISO-8859-1"), "UTF-8"));
			String loginuser = java.net.URLDecoder.decode(request.getParameter("loginuser"), "UTF-8");

			JSONObject j1 = JSONObject.parseObject(params);
			List<souko> soukoNodes = JSONObject.parseArray(j1.getJSONArray("params").toJSONString(),
					souko.class);
			Date now = new Date();
			if (soukoNodes.size() > 0 && soukoNodes != null) {
				List<String> codes = new ArrayList<String>();
				for (int i = 0; i < soukoNodes.size(); i++) {
					soukoNodes.get(i).setNagoya_update(now);
					if (!codes.contains(soukoNodes.get(i).getCode())) {
						codes.add(soukoNodes.get(i).getCode());
					}
				}

				boolean add_flag = false;
				List<zaikorireki> nagoyarirekis = new ArrayList<zaikorireki>();
				List<souko> soukos = soukoService.getSoukoByCodes(codes);
				StringBuilder message = new StringBuilder();
				if (soukos != null) {
					for (int i = 0; i < soukoNodes.size(); i++) {
						String code = soukoNodes.get(i).getCode();
						for (int j = 0; j < soukos.size(); j++) {
							if (code.equals(soukos.get(j).getCode())) {
								zaikorireki nagoyarireki = new zaikorireki();
								add_flag = false;

								message.setLength(0);
								if (soukoNodes.get(i).getKaisou_nagoya() != null
										&& soukos.get(j).getKaisou_nagoya() != null) {
									if (!soukoNodes.get(i).getKaisou_nagoya()
											.equals(soukos.get(j).getKaisou_nagoya())) {
										add_flag = true;
										message.append("階数:" + soukos.get(j).getKaisou_nagoya() + " > "
												+ soukoNodes.get(i).getKaisou_nagoya() + " ");
									}
								} else if (soukoNodes.get(i).getKaisou_nagoya() != null
										&& soukos.get(j).getKaisou_nagoya() == null) {
									add_flag = true;
									message.append("階数:" + "null" + " > "
											+ soukoNodes.get(i).getKaisou_nagoya() + " ");
								} else if (soukoNodes.get(i).getKaisou_nagoya() == null
										&& soukos.get(j).getKaisou_nagoya() != null) {
									add_flag = true;
									message.append("階数:" + soukos.get(j).getKaisou_nagoya() + " > "
											+ "null" + " ");
								}

								if (soukoNodes.get(i).getTana_nagoya() != null
										&& soukos.get(j).getTana_nagoya() != null) {
									if (!soukoNodes.get(i).getTana_nagoya().equals(soukos.get(j).getTana_nagoya())) {
										add_flag = true;
										message.append("棚:" + soukos.get(j).getTana_nagoya() + " > "
												+ soukoNodes.get(i).getTana_nagoya() + " ");
									}
								} else if (soukoNodes.get(i).getTana_nagoya() != null
										&& soukos.get(j).getTana_nagoya() == null) {
									add_flag = true;
									message.append("棚:" + "null" + " > "
											+ soukoNodes.get(i).getTana_nagoya() + " ");
								} else if (soukoNodes.get(i).getTana_nagoya() == null
										&& soukos.get(j).getTana_nagoya() != null) {
									add_flag = true;
									message.append("棚:" + soukos.get(j).getTana_nagoya() + " > "
											+ "null" + " ");
								}

								if (soukoNodes.get(i).getNagoyaZaiko() != soukos.get(j).getNagoyaZaiko()) {
									add_flag = true;
									message.append("在庫:" + soukos.get(j).getNagoyaZaiko() + " > "
											+ soukoNodes.get(i).getNagoyaZaiko() + " ");
								}

								if (add_flag) {
									nagoyarireki.setCode(code);
									nagoyarireki.setUpdate(now);
									nagoyarireki.setUser(loginuser);
									nagoyarireki.setMessage(
											message.toString().substring(0, message.toString().length() - 1));

									nagoyarirekis.add(nagoyarireki);
								}
								break;
							}
						}

					}
				}

				if (soukoNodes.size() > 0) {
					soukoService.upate(active, soukoNodes);
					if (nagoyarirekis.size() > 0) {
						zaikorirekiService.insertAllNagoya(nagoyarirekis);
					}
				}
			}

			result result = new result();
			result.setState(1);
			result.setMsg(soukoNodes.size() + "件を変更しました！");
			object.put("rows", result);
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		return object;
	}

}
