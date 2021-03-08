package com.mp.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
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
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mp.common.DynamicDataSourceHolder;
import com.mp.dto.RakutenYahooApiDate;
import com.mp.dto.result;
import com.mp.entity.config;
import com.mp.entity.kakaku;
import com.mp.service.kakakuService;
import com.mp.util.CommonUtil;

@Controller
public class kakakuController {
	@Autowired
	private kakakuService kakakuService;

	@ResponseBody
	@RequestMapping(value = "/getKakaku", method = RequestMethod.POST)
	private JSONObject getKakaku(HttpServletResponse response, HttpServletRequest request, String sort, int currentPage,
			int pagesize, String s_code, String t_price1, String t_price2, String s_price1, String s_price2,
			String s_limit1, String s_limit2, String s_update1, String s_update2, boolean t_price_ch1,
			boolean t_price_ch2, boolean s_price_ch1, boolean s_price_ch2, boolean s_limit_ch1, boolean s_limit_ch2) {
		DynamicDataSourceHolder.setDataSource("jrt_dataSource");
		JSONObject object = new JSONObject();


		String place = "";
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");

			if (config.ISLOCAL) {
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

			if (updateFlag) {
				Path path_masterDB = Paths.get(masterDB_Path);
				Files.copy(path_master, path_masterDB, StandardCopyOption.REPLACE_EXISTING);
			}

			// CSV
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
				if (num == 0) {
					if (!CommonUtil.isHave(item, "商品コード") || !CommonUtil.isHave(item, "在庫数")
							|| !CommonUtil.isHave(item, "予約在庫")) {
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
//					if(codeStr != null && !"".equals(codeStr)) {
//						String[] code_s = codeStr.split("-");
//						k.setCode(code_s[0]);
//					}
					k.setCode(codeStr);

					k.setH_tzaiko(getValue(item, h_tzaiko_No));
					k.setH_yzaiko(getValue(item, h_yzaiko_No));
					kakakus_match.add(k);
				}
				num++;
			}
			reader.close();

			// DB
			String[] s_code_ = new String[] {};
			if (s_code != null && !"".equals(s_code)) {
				s_code_ = s_code.split(",");
			}

			int list_currentPage = (currentPage - 1) * pagesize;
			List<kakaku> kakakus = kakakuService.getKakaku(list_currentPage, pagesize, sort, s_code_, t_price1,
					t_price2, s_price1, s_price2, s_limit1, s_limit2, s_update1, s_update2, t_price_ch1, t_price_ch2,
					s_price_ch1, s_price_ch2, s_limit_ch1, s_limit_ch2);
			int total = kakakuService.getTotal();

			for (int i = 0; i < kakakus.size(); i++) {
				kakakus.get(i).setChk(false);
				int haiso_ryo = 0;

				if (kakakus.get(i).getHaiso().contains("大型")) {
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

				double sale_kagen_ = (kakakus.get(i).getGenka() + haiso_ryo) * 1.4;
				int sale_kagen = (int) sale_kagen_;
				kakakus.get(i).setSale_kagen(sale_kagen);

				int syobun_kagen_ = (int) (kakakus.get(i).getGenka() * 1.25);
				kakakus.get(i).setSyobun_kagen_(syobun_kagen_);
				int syobun_kagen = syobun_kagen_ + haiso_ryo;
				kakakus.get(i).setSyobun_kagen(syobun_kagen);

				int h_tzaiko = 0;
				int h_yzaiko = 0;
				kakakus.get(i).setHasEda(false);
				for (int j = 0; j < kakakus_match.size(); j++) {
					if (kakakus.get(i).getCode().equals(kakakus_match.get(j).getCode())) {
						h_tzaiko = h_tzaiko + Integer.parseInt(kakakus_match.get(j).getH_tzaiko());
						h_yzaiko = h_yzaiko + Integer.parseInt(kakakus_match.get(j).getH_yzaiko());
						kakakus.get(i).setHasEda(true);
						continue;
					}
				}
				kakakus.get(i).setH_tzaiko(String.valueOf(h_tzaiko));
				kakakus.get(i).setH_yzaiko(String.valueOf(h_yzaiko));

				// SALE下限
				double kagen1 = Math.floor((kakakus.get(i).getGenka() + haiso_ryo) * 1.4);
				kakakus.get(i).setKagen1(String.valueOf(new Double(kagen1).intValue()) + "円");

				// 処分下限
				double kagen2_goukei = kakakus.get(i).getGenka() * 1.25;
				kakakus.get(i).setKagen2_goukei(String.valueOf(new Double(kagen2_goukei + haiso_ryo).intValue()) + "円("
						+ String.valueOf(new Double(kagen2_goukei).intValue()) + "+" + String.valueOf(haiso_ryo) + ")");

//				String[] code_s = kakakus.get(i).getCode().split("-");
//				if(code_s.length > 1) {
//					kakakus.get(i).setHasEda(1);
//				} else {
//					kakakus.get(i).setHasEda(0);
//				}
			}

			object.put("kakakus", kakakus);
			object.put("total", total);
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Cache-Control", "no-cache");
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return object;
	}

	@ResponseBody
	@RequestMapping(value = "/updateKakaku", method = RequestMethod.POST)
	private JSONObject updateKakaku(HttpServletResponse response, HttpServletRequest request,
			@RequestBody String params, String loginuser) {
		JSONObject object = new JSONObject();
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			DynamicDataSourceHolder.setDataSource("jrt_dataSource");

			JSONObject j1 = JSONObject.parseObject(params);
			List<kakaku> kakakus = JSONObject.parseArray(j1.getJSONArray("params").toJSONString(), kakaku.class);
			Date now = new Date();

			for (int i = 0; i < kakakus.size(); i++) {
				kakakus.get(i).setUpdate(now);
				kakakus.get(i).setTanto(loginuser);
				kakakuService.updateKakaku(kakakus.get(i));
			}

//			object.put("rows", lo);
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Cache-Control", "no-cache");
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return object;
	}

	@ResponseBody
	@RequestMapping(value = "/loadApiContent", method = RequestMethod.POST)
	private JSONObject loadApiContent(HttpServletResponse response, HttpServletRequest request, String code) {
		JSONObject object = new JSONObject();
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Cache-Control", "no-cache");

			// Rakuten
			String applicationId = "1068941964236537601";
			List<RakutenYahooApiDate> rakutenyahoo_data = new ArrayList<RakutenYahooApiDate>();
			String[] shopcodes_rakuten = { "ashop", "patri", "alice-zk" };

			for (int i = 0; i < shopcodes_rakuten.length; i++) {
				String url = "https://app.rakuten.co.jp/services/api/IchibaItem/Search/20170706?applicationId="
						+ applicationId + "&keyword=" + code + "&shopCode=" + shopcodes_rakuten[i];
				String json_str = loadApiJson(url);
				if (json_str != null && !"".equals(json_str)) {
					JSONObject jsonObject = JSONObject.parseObject(json_str);
					for (Map.Entry entry : jsonObject.entrySet()) {
						if ("Items".equals(entry.getKey())) {
							JSONArray jsonArray = (JSONArray) entry.getValue();
							if (jsonArray != null) {
								for (int k = 0; k < jsonArray.size(); k++) {
									if (jsonArray.getJSONObject(k).get("Item") != null) {
										JSONObject jsonObject2 = JSONObject
												.parseObject(jsonArray.getJSONObject(k).get("Item").toString());

										RakutenYahooApiDate ra = new RakutenYahooApiDate();
										for (Map.Entry entry2 : jsonObject2.entrySet()) {
//											System.out.println(entry2.getKey() + ":" + entry2.getValue());
//											System.out.println("----------");
											if ("itemName".equals(entry2.getKey())) {
												ra.setItemName(entry2.getValue().toString());
											} else if ("itemUrl".equals(entry2.getKey())) {
												ra.setItemUrl(entry2.getValue().toString());
											} else if ("mediumImageUrls".equals(entry2.getKey())) {
												if (!"".equals(entry2.getValue().toString())) {
													JSONArray mediumImageUrls_js = (JSONArray) entry2.getValue();
													String[] mediumImageUrls_st = new String[mediumImageUrls_js.size()];
													for (int j = 0; j < mediumImageUrls_js.size(); j++) {
														mediumImageUrls_st[j] = mediumImageUrls_js.getJSONObject(j)
																.get("imageUrl").toString();
													}
													ra.setMediumImageUrls(mediumImageUrls_st);
												}
											} else if ("itemPrice".equals(entry2.getKey())) {
												ra.setItemPrice(entry2.getValue().toString());
											} else if ("shopName".equals(entry2.getKey())) {
												ra.setShopName(entry2.getValue().toString());
											}
										}
										rakutenyahoo_data.add(ra);
									}
								}
							}
						}
					}
				}
			}

			// Yahoo
			String appid = "dj00aiZpPTVYcmQ3b3BjaENlbSZzPWNvbnN1bWVyc2VjcmV0Jng9MGI-";
			String[] shopcodes_yahoo = { "fkstyle", "lucky9", "akaneashop", "kt-zkshop" };
			for (int i = 0; i < shopcodes_yahoo.length; i++) {
				String url = "http://shopping.yahooapis.jp/ShoppingWebService/V3/itemSearch?appid=" + appid + "&query="
						+ code + "&seller_id=" + shopcodes_yahoo[i];
				String json_str = loadApiJson(url);

				if (json_str != null && !"".equals(json_str)) {
					JSONObject jsonObject = JSONObject.parseObject(json_str);
					for (Map.Entry entry : jsonObject.entrySet()) {
						if ("hits".equals(entry.getKey())) {
							JSONArray jsonArray = (JSONArray) entry.getValue();
							if (jsonArray != null) {
								System.out.println(entry.getKey() + ":" + entry.getValue());
								System.out.println("----------");
								for (int k = 0; k < jsonArray.size(); k++) {
									JSONObject js = jsonArray.getJSONObject(k);
									RakutenYahooApiDate ra = new RakutenYahooApiDate();
									for (Map.Entry entry2 : js.entrySet()) {
//											System.out.println(entry2.getKey() + ":" + entry2.getValue());
//											System.out.println("----------");
										if ("name".equals(entry2.getKey())) {
											ra.setItemName(entry2.getValue().toString());
										} else if ("url".equals(entry2.getKey())) {
											ra.setItemUrl(entry2.getValue().toString());
										} else if ("price".equals(entry2.getKey())) {
											ra.setItemPrice(entry2.getValue().toString());
										} else if ("image".equals(entry2.getKey())) {
											JSONObject js_image = js.parseObject(entry2.getValue().toString());
											if (js_image != null) {
												List<String> medium_ = new ArrayList<String>();
												for (Map.Entry entry3 : js_image.entrySet()) {
													if ("medium".equals(entry3.getKey())) {
														System.out.println(entry3.getKey() + ":" + entry3.getValue());
														medium_.add(entry3.getValue().toString());
													}
												}

												if (medium_.size() > 0) {
													String[] mediumImageUrls_st = new String[medium_.size()];
													for (int j = 0; j < medium_.size(); j++) {
														mediumImageUrls_st[j] = medium_.get(j).toString();
													}
													ra.setMediumImageUrls(mediumImageUrls_st);
												}
											}
										} else if ("seller".equals(entry2.getKey())) {
											JSONObject js_seller = js.parseObject(entry2.getValue().toString());
											if (js_seller != null) {
												for (Map.Entry entry3 : js_seller.entrySet()) {
													if ("name".equals(entry3.getKey())) {
														ra.setShopName(entry3.getValue().toString());
													}
												}
											}
										}
									}
									rakutenyahoo_data.add(ra);
								}
							}
						}
					}
				}
			}
			System.out.println(rakutenyahoo_data);
			object.put("rakutenyahoo_data", rakutenyahoo_data);
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return object;
	}

	@ResponseBody
	@RequestMapping(value = "/getKakakuRireki", method = RequestMethod.POST)
	private JSONObject getKakakuRireki(HttpServletResponse response, HttpServletRequest request, String code) {
		JSONObject object = new JSONObject();
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			DynamicDataSourceHolder.setDataSource("jrt_dataSource");

			List<kakaku> kakakurireki = kakakuService.getKakakuRireki(code);

			object.put("rows", kakakurireki);
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Cache-Control", "no-cache");
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return object;
	}

	@ResponseBody
	@RequestMapping(value = "/uploadKakaku", method = RequestMethod.POST)
	private JSONObject uploadKakaku(@RequestParam(value = "file") MultipartFile file, HttpServletResponse response,
			HttpServletRequest request, String upload_type) {
		DynamicDataSourceHolder.setDataSource("jrt_dataSource");
		JSONObject object = new JSONObject();
		result result = new result();

		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Cache-Control", "no-cache");

			List<kakaku> checkCodeArray = kakakuService.getAllCodes();
			List<String> checkCodesList = new ArrayList<String>();
			List<String> checkStrArray = new ArrayList<String>();
			if (checkCodeArray.size() > 0) {
				if ("「項目全て」登録編集".equals(upload_type)) {
					for (int i = 0; i < checkCodeArray.size(); i++) {
						StringBuilder sb = new StringBuilder();
						sb.append(checkCodeArray.get(i).getCode().toLowerCase())
								.append(checkCodeArray.get(i).getGenka()).append(checkCodeArray.get(i).getHaiso())
								.append(checkCodeArray.get(i).getRaku_price())
								.append(checkCodeArray.get(i).getRaku_sale())
								.append(checkCodeArray.get(i).getRaku_sale_limit())
								.append(checkCodeArray.get(i).getYahoo_price())
								.append(checkCodeArray.get(i).getYahoo_sale())
								.append(checkCodeArray.get(i).getYahoo_sale_limit())
								.append(checkCodeArray.get(i).getMemo());
						checkStrArray.add(sb.toString());
						checkCodesList.add(checkCodeArray.get(i).getCode().toLowerCase());
					}
				} else if ("ネクストエンジンcsv登録".equals(upload_type)) {
					for (int i = 0; i < checkCodeArray.size(); i++) {
						StringBuilder sb = new StringBuilder();
						String haiso = "";
						if (checkCodeArray.get(i).getHaiso().contains("宅配便")) {
							haiso = "宅";
						} else if (checkCodeArray.get(i).getHaiso().contains("メール便")) {
							haiso = "メ";
						} else if (checkCodeArray.get(i).getHaiso().contains("定形外")) {
							haiso = "定外";
						}
						sb.append(checkCodeArray.get(i).getCode().toLowerCase())
								.append(checkCodeArray.get(i).getName().substring(0, 3))
								.append(checkCodeArray.get(i).getGenka()).append(haiso);
						checkStrArray.add(sb.toString());
						checkCodesList.add(checkCodeArray.get(i).getCode().toLowerCase());
					}
				}

			}

//			InputStream is = file.getInputStream();
//			InputStreamReader isr = new InputStreamReader(is);
//			String encode = isr.getEncoding();
//			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//			if ("SHIFT_JIS".equals(encode) || "MS932".equals(encode)) {
//				reader = new BufferedReader(new InputStreamReader(is, "SHIFT_JIS"));
//			}
//			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "SHIFT_JIS"));
//			Workbook wb = PoiUtil.getWorkbok(file.getInputStream(), file);
//
//			// 读取第一个工作页sheet
//			Sheet sheet = wb.getSheetAt(0);
//
//			//第一行为标题
//		      for (Row row : sheet) {
//		    	  for (Cell cell : row) {
//		    		  System.out.println(cell.getStringCellValue());
//		    	  }
//		      }

			String line = null;
			int num = 0;
			Integer codeNo = null;
			Integer nameNo = null;
			Integer genkaNo = null;
			Integer haisoNo = null;
			Integer r1No = null;
			Integer r2No = null;
			Integer r3No = null;
			Integer y1No = null;
			Integer y2No = null;
			Integer y3No = null;
			Integer memoNo = null;
			Integer tantoNo = null;
			Integer daihyoNo = null;

			List<kakaku> kakakus_in = new ArrayList<kakaku>();
			List<kakaku> kakakus_up = new ArrayList<kakaku>();

//			while ((line = reader.readLine()) != null) {
//				String item[] = line.split(",");
//
//				if (num == 0) {
//					if ("ネクストエンジンcsv登録".equals(upload_type)) {
//						if (!CommonUtil.isHave(item, "") || !CommonUtil.isHave(item, "商品コード")
//								|| !CommonUtil.isHave(item, "商品名") || !CommonUtil.isHave(item, "原価")
//								|| !CommonUtil.isHave(item, "JANコード") || !CommonUtil.isHave(item, "代表商品コード")) {
//							result.setState(0);
//							result.setMsg("ファイルの種類が異なるため更新できません。");
//							object.put("result", result);
//							return object;
//						}
//						for (int i = 0; i < item.length; i++) {
//							if ("商品コード".equals(getValue(item, i))) {
//								codeNo = i;
//							} else if ("商品名".equals(getValue(item, i))) {
//								nameNo = i;
//							} else if ("原価".equals(getValue(item, i))) {
//								genkaNo = i;
//							} else if ("JANコード".equals(getValue(item, i))) {
//								haisoNo = i;
//							} else if ("代表商品コード".equals(getValue(item, i))) {
//								daihyoNo = i;
//							}
//						}
//					} else if ("「項目全て」登録編集".equals(upload_type)) {
//						if (!CommonUtil.isHave(item, "コード") || !CommonUtil.isHave(item, "原価")
//								|| !CommonUtil.isHave(item, "配送") || !CommonUtil.isHave(item, "R_通常")
//								|| !CommonUtil.isHave(item, "R_SALE") || !CommonUtil.isHave(item, "R_期限")
//								|| !CommonUtil.isHave(item, "Y_通常") || !CommonUtil.isHave(item, "Y_SALE")
//								|| !CommonUtil.isHave(item, "Y_期限") || !CommonUtil.isHave(item, "備考")
//								|| !CommonUtil.isHave(item, "更新者")) {
//							result.setState(0);
//							result.setMsg("ファイルの種類が異なるため更新できません。");
//							object.put("result", result);
//							return object;
//						}
//						for (int i = 0; i < item.length; i++) {
//							if ("コード".equals(getValue(item, i))) {
//								codeNo = i;
//							} else if ("原価".equals(getValue(item, i))) {
//								genkaNo = i;
//							} else if ("配送".equals(getValue(item, i))) {
//								haisoNo = i;
//							} else if ("R_通常".equals(getValue(item, i))) {
//								r1No = i;
//							} else if ("R_SALE".equals(getValue(item, i))) {
//								r2No = i;
//							} else if ("R_期限".equals(getValue(item, i))) {
//								r3No = i;
//							} else if ("Y_通常".equals(getValue(item, i))) {
//								y1No = i;
//							} else if ("Y_SALE".equals(getValue(item, i))) {
//								y2No = i;
//							} else if ("Y_期限".equals(getValue(item, i))) {
//								y3No = i;
//							} else if ("備考".equals(getValue(item, i))) {
//								memoNo = i;
//							} else if ("更新者".equals(getValue(item, i))) {
//								tantoNo = i;
//							}
//						}
//					}
//				} else {
//					String dCode = "";
//
//					boolean isNull_daihyo = false;
//					if (daihyoNo == null) {
//						isNull_daihyo = true;
//					} else {
//						if (getValue(item, daihyoNo) == null || "".equals(getValue(item, daihyoNo))) {
//							isNull_daihyo = true;
//						}
//					}
//
//					if ("「項目全て」登録編集".equals(upload_type)) {
//						dCode = getValue(item, codeNo).toLowerCase();
//					} else {
//						if (isNull_daihyo) {
//							dCode = getValue(item, codeNo).toLowerCase();
//						} else {
//							dCode = getValue(item, daihyoNo).toLowerCase();
//						}
//					}
//
//					kakaku kakaku_up = new kakaku();
//					kakaku kakaku_in = new kakaku();
//
//					int genka = 0;
//					String haiso = "";
//					int raku_price = 0;
//					int raku_sale = 0;
//					Date raku_sale_limit = null;
//					int yahoo_price = 0;
//					int yahoo_sale = 0;
//					Date yahoo_sale_limit = null;
//					String memo = "";
//					String tanto = "";
//
//					String name = "";
//
//					if ("「項目全て」登録編集".equals(upload_type)) {
//						genka = Integer.parseInt(getValue(item, genkaNo));
//						haiso = getValue(item, haisoNo);
//						raku_price = Integer.parseInt(getValue(item, r1No));
//						raku_sale = Integer.parseInt(getValue(item, r2No));
//
//						if (getValue(item, r3No) != null && !"".equals(getValue(item, r3No))) {
//							SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
//							raku_sale_limit = format.parse(getValue(item, r3No));
//						}
//
//						yahoo_price = Integer.parseInt(getValue(item, y1No));
//						yahoo_sale = Integer.parseInt(getValue(item, y2No));
//
//						if (getValue(item, y3No) != null && !"".equals(getValue(item, y3No))) {
//							SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
//							yahoo_sale_limit = format.parse(getValue(item, y3No));
//						}
//
//						memo = getValue(item, memoNo);
//						tanto = getValue(item, tantoNo);
//					} else {
//						genka = Integer.parseInt(getValue(item, genkaNo));
//						name = getValue(item, nameNo);
//						haiso = getValue(item, haisoNo);
//					}
//
//					int insert_count = 0;
//					int update_count = 0;
//					// type 0:「項目全て」登録編集 1:ネクストエンジンcsv登録
//					if (checkCodesList.contains(dCode)) {// コードが登録済みか？
//						if ("「項目全て」登録編集".equals(upload_type)) {
//							kakaku_up.setUploadFlag(0);
//							kakaku_up.setCode(dCode);
//							kakaku_up.setGenka(genka);
//							kakaku_up.setHaiso(haiso);
//							kakaku_up.setRaku_price(raku_price);
//							kakaku_up.setRaku_sale(raku_sale);
//							kakaku_up.setRaku_sale_limit(raku_sale_limit);
//							kakaku_up.setYahoo_price(yahoo_price);
//							kakaku_up.setYahoo_sale(yahoo_sale);
//							kakaku_up.setYahoo_sale_limit(yahoo_sale_limit);
//							kakaku_up.setMemo(memo);
//							kakaku_up.setTanto(tanto);
//						} else if ("ネクストエンジンcsv登録".equals(upload_type)) {
//							kakaku_up.setUploadFlag(1);
//							kakaku_up.setCode(dCode);
//							kakaku_up.setName(name);
//							kakaku_up.setGenka(genka);
//							kakaku_up.setHaiso(haiso);
//						}
//						kakakus_up.add(kakaku_up);
//						update_count++;
//					} else {
//						// コードの登録が無ければ登録
//						if ("「項目全て」登録編集".equals(upload_type)) {
//							kakaku_in.setUploadFlag(0);
//							kakaku_in.setCode(dCode);
//							kakaku_in.setGenka(genka);
//							kakaku_in.setHaiso(haiso);
//							kakaku_in.setRaku_price(raku_price);
//							kakaku_in.setRaku_sale(raku_sale);
//							kakaku_in.setRaku_sale_limit(raku_sale_limit);
//							kakaku_in.setYahoo_price(yahoo_price);
//							kakaku_in.setYahoo_sale(yahoo_sale);
//							kakaku_in.setYahoo_sale_limit(yahoo_sale_limit);
//							kakaku_in.setMemo(memo);
//							kakaku_in.setTanto(tanto);
//						} else if ("ネクストエンジンcsv登録".equals(upload_type)) {
//							kakaku_in.setUploadFlag(1);
//							kakaku_in.setCode(dCode);
//							kakaku_in.setName(name);
//							kakaku_in.setGenka(genka);
//							kakaku_in.setHaiso(haiso);
//						}
//						kakakus_in.add(kakaku_in);
//						insert_count++;
//					}
//				}
//				num++;
//			}
//			reader.close();

			if (kakakus_up.size() > 0) {

			}

			if (kakakus_in.size() > 0) {

			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
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

	public static String loadApiJson(String url) {
		StringBuilder json = new StringBuilder();
		try {
			URL urlObject = new URL(url);
			URLConnection uc = urlObject.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream(), "UTF-8"));
			String inputLine = null;
			while ((inputLine = in.readLine()) != null) {
				json.append(inputLine);
			}
			in.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json.toString();
	}
}
