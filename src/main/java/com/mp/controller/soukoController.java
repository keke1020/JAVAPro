package com.mp.controller;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
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
import com.mp.entity.souko;
import com.mp.entity.zaikorireki;
import com.mp.service.locationService;
import com.mp.service.soukoService;
import com.mp.service.zaikorirekiService;
import com.mp.util.CommonUtil;
import com.mp.util.CsvUtil;
import com.mp.util.FileUtil;

@Controller
public class soukoController {

	@Autowired
	private soukoService soukoService;

	@Autowired
	private locationService locationService;

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
	@RequestMapping(value = "/getCodeDataBySoukoNagoya", method = RequestMethod.POST)
	private JSONObject getCodeDataBySoukoNagoya(HttpServletResponse response, HttpServletRequest request, String code) {
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
			int currentPage = Integer
					.parseInt(new String(request.getParameter("currentPage").getBytes("ISO-8859-1"), "UTF-8"));
			currentPage = (currentPage - 1) * searchCount;

			List<souko> souko = new ArrayList<souko>();
			souko = soukoService.getCodeDataBySoukoNagoya(code, currentPage, searchCount);
			int total = soukoService.getTotalBySoukoNagoya(code);

			object.put("souko", souko);
			object.put("total", total);
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		return object;
	}

	@ResponseBody
	@RequestMapping(value = "/deleteNagoyaZaikoById", method = RequestMethod.POST)
	private JSONObject deleteNagoyaZaikoById(HttpServletResponse response, HttpServletRequest request, int id) {
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
			soukoService.deleteNagoyaZaikoById(id);
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		return object;
	}

	@ResponseBody
	@RequestMapping(value = "/nyuushukkaForSoukoNagoya", method = RequestMethod.POST)
	private JSONObject nyuushukkaForSoukoNagoya(HttpServletResponse response, HttpServletRequest request, String code,
			String in_common, String out_common, String user) {
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

			int in = 0;
			int out = 0;

			if (in_common != null && !"".equals(in_common)) {
				in = Integer.valueOf(in_common);
			}
			if (out_common != null && !"".equals(out_common)) {
				out = Integer.valueOf(out_common);
			}
			souko souko = new souko();
			souko.setCode(code);
			souko.setIn(in);
			souko.setOut(out);
			souko.setUpdate(new Date());
			souko.setUser(user);
			souko.setUuid(UUID.randomUUID().toString());

			soukoService.nyuushukkaForSoukoNagoyaByCode(souko);
			result result = new result();
			result.setState(1);
			result.setMsg("追加しました！");
			object.put("rows", result);
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		return object;
	}

	@ResponseBody
	@RequestMapping(value = "/uploadCsvACtion_souko", method = RequestMethod.POST)
	private JSONObject uploadCsvACtion_souko(@RequestParam(value = "file") MultipartFile file,
			HttpServletResponse response, HttpServletRequest request, String user) {
		DynamicDataSourceHolder.setDataSource("defultdataSource");
		JSONObject object = new JSONObject();
		result result = new result();

		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Cache-Control", "no-cache");

			String path = "";
			if (config.ISLOCAL) {
				dataSource = config.DATASOURCE_LOCAL_YUBIN;
			} else {
				dataSource = config.DATASOURCE_SERVER_YUBIN;
			}
			DynamicDataSourceHolder.setDataSource(dataSource);

			Date now = new Date();

			if (!"csv".equals(FileUtil.getFileType(file.getOriginalFilename()))) {
				result.setState(0);
				object.put("rows", result);
				return object;
			}

			InputStream is = file.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "Shift-JIS"));

			int num = 0;
			Integer code_index = null;
			Integer in_index = null;
			Integer out_index = null;
			Integer souko_index = null;

			List<souko> soukos = new ArrayList<souko>();
			List<zaikorireki> zos = new ArrayList<zaikorireki>();

			//			List<location> lo = locationService.getlocation("false", "true", "", "", "", "", "", "", "", "", "",
			//					"", "false", 0, 0);
			List<souko> souko_master = soukoService.getSouko("true", "true", "", "", "", "", "", "", "", "", 0,
					100000000, "false");
			List<String> codes = new ArrayList<String>();

			if (souko_master.size() > 0) {
				for (int i = 0; i < souko_master.size(); i++) {
					codes.add(souko_master.get(i).getCode());
				}
			}

			String uuid = UUID.randomUUID().toString();

			String line = null;
			while ((line = reader.readLine()) != null) {
				String item[] = line.split(",\\s*(?![^\"]*\"\\,)");// CSV格式文件为逗号分隔符文件，这里根据逗号切分
				if (num == 0) {
					if (!CommonUtil.isHave(item, "商品コード") || !CommonUtil.isHave(item, "入荷数")
							|| !CommonUtil.isHave(item, "出荷数") || !CommonUtil.isHave(item, "倉庫")) {
						result.setState(0);
						result.setMsg("正しいcsvファイルをアップロードしてください。");
						object.put("rows", result);
						return object;
					}
					for (int i = 0; i < item.length; i++) {
						if ("商品コード".equals(CsvUtil.getValue(item, i))) {
							code_index = i;
						} else if ("入荷数".equals(CsvUtil.getValue(item, i))) {
							in_index = i;
						} else if ("出荷数".equals(CsvUtil.getValue(item, i))) {
							out_index = i;
						} else if ("倉庫".equals(CsvUtil.getValue(item, i))) {
							souko_index = i;
						}
					}
				} else {
					String code_csv = CsvUtil.getValue(item, code_index).trim();
					String in_csv = CsvUtil.getValue(item, in_index).trim();
					String out_csv = CsvUtil.getValue(item, out_index).trim();
					int in = 0;
					int out = 0;
					String souko_csv = CsvUtil.getValue(item, souko_index).trim();

					if (!"".equals(code_csv) && !"".equals(souko_csv)) {
						if ("名古屋".equals(souko_csv) && codes.contains(code_csv)) {

							if (CommonUtil.isInteger(in_csv)) {
								in = Integer.valueOf(in_csv);
							}

							if (CommonUtil.isInteger(out_csv)) {
								out = Integer.valueOf(out_csv);
							}

							souko so = new souko();
							so.setCode(code_csv);
							so.setIn(in);
							so.setOut(out);
							so.setUuid(uuid);
							so.setUser(user);
							so.setUpdate(now);
							soukos.add(so);
						}
					}
				}
				num++;
			}

			if (soukos.size() > 0) {
				soukoService.nyuushukkaForSoukoNagoya(soukos);

				result.setState(1);
				result.setMsg(soukos.size() + "件を登録しました。");
			} else {
				result.setState(0);
				result.setMsg("登録するデータがないです。");
			}

			object.put("rows", result);
			return object;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
			result.setState(0);
			result.setMsg("インポート中にエラーがあります。");
			object.put("rows", result);
			return object;
		}
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

			List<zaikorireki> rireki = zaikorirekiService.getNagoyaRireki(codeSc, "tana", updateSc_s, updateSc_e,
					orderSC,
					list_currentPage, searchCount);
			int total = zaikorirekiService.getNagoyaRireki_total(codeSc, updateSc_s, updateSc_e, orderSC);
			object.put("rireki", rireki);
			object.put("total", total);
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		return object;
	}

	@ResponseBody
	@RequestMapping(value = "/getNagoyaRireki2", method = RequestMethod.POST)
	private JSONObject getNagoyaRireki2(HttpServletResponse response, HttpServletRequest request) {
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
					.parseInt(new String(request.getParameter("curr").getBytes("ISO-8859-1"), "UTF-8"));
			list_currentPage = (list_currentPage - 1) * searchCount;

			List<souko> souko = soukoService.getNagoyaRireki2(list_currentPage, searchCount);

			int total = soukoService.getNagoyaRireki_total2();
			object.put("rireki", souko);
			object.put("total", total);
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		return object;
	}

	@ResponseBody
	@RequestMapping(value = "/getNagoyaRireki2ByUUid", method = RequestMethod.POST)
	private JSONObject getNagoyaRireki2ByUUid(HttpServletResponse response, HttpServletRequest request, String uuid) {
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

			List<souko> souko_ri = soukoService.getNagoyaRireki2ByUUid(uuid);
			object.put("rireki", souko_ri);
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
									nagoyarireki.setType("tana");
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

					//					エクセル保存
					List<souko> soukos_all = soukoService.getSouko("false", "true", "コード昇順", "", "", "", "", "", "",
							"", 0, 999999999, "");

					if (soukos_all.size() > 0) {
						try {

							String place = "";
							if (config.ISLOCAL) {
								place = config.LOCATION_PLACE_LOCAL;
							} else {
								place = config.LOCATION_PLACE_SERVER;
							}

							FileOutputStream fos = new FileOutputStream(
									place + "xampp\\htdocs\\ordery\\logi\\location.csv");
							OutputStreamWriter osw = new OutputStreamWriter(fos, "SJIS");
							CSVFormat csvFormat = CSVFormat.DEFAULT.withHeader("商品コード", "商品名", "商品分類タグ", "代表商品コード",
									"ship-weight", "ロケーション", "ロケーション(名古屋)", "梱包サイズ", "特殊", "sw2");
							CSVPrinter csvPrinter = new CSVPrinter(osw, csvFormat);
							StringBuffer location_dzf = new StringBuffer();
							StringBuffer location_ngy = new StringBuffer();

							for (int k = 0; k < soukos_all.size(); k++) {
								location_dzf.setLength(0);
								location_ngy.setLength(0);

								if (soukos_all.get(k).getKaisou() == null || "".equals(soukos_all.get(k).getKaisou())) {
								} else {
									location_dzf.append(soukos_all.get(k).getKaisou());
								}

								if (soukos_all.get(k).getTana() == null || "".equals(soukos_all.get(k).getTana())) {
								} else {
									location_dzf.append(soukos_all.get(k).getTana());
								}

								if (soukos_all.get(k).getKaisou_nagoya() == null
										|| "".equals(soukos_all.get(k).getKaisou_nagoya())) {
								} else {
									location_ngy.append(soukos_all.get(k).getKaisou_nagoya());
								}

								if (soukos_all.get(k).getTana_nagoya() == null
										|| "".equals(soukos_all.get(k).getTana_nagoya())) {
								} else {
									location_ngy.append(soukos_all.get(k).getTana_nagoya());
								}

								csvPrinter.printRecord(soukos_all.get(k).getCode(), soukos_all.get(k).getName(),
										soukos_all.get(k).getTag(),
										soukos_all.get(k).getDcode(), soukos_all.get(k).getSw(),
										location_dzf.toString(),
										location_ngy.toString(),
										soukos_all.get(k).getKsize(), soukos_all.get(k).getSp(),
										soukos_all.get(k).getSw2());
							}

							csvPrinter.flush();
							csvPrinter.close();
						} catch (Exception e) {
							// TODO: handle exception
							System.out.println(e);
						}
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
