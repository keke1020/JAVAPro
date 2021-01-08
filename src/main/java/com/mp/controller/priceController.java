package com.mp.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
import com.mp.entity.config;
import com.mp.entity.file;
import com.mp.entity.ne_hikaku;
import com.mp.entity.price;
import com.mp.service.fileService;
import com.mp.service.priceService;
import com.mp.util.CommonUtil;
import com.mp.util.CsvUtil;
import com.mp.util.FileUtil;
import com.opencsv.CSVParser;
import com.opencsv.CSVReader;

@Controller
public class priceController {
	SimpleDateFormat sf5 = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat sf6 = new SimpleDateFormat("yyyyMMdd");
	SimpleDateFormat sf7 = new SimpleDateFormat("yyyyMMddHHmmss");

	@Autowired
	private fileService fileService;

	@Autowired
	private priceService priceService;

	@ResponseBody
	@RequestMapping(value = "/uploadCsv_price", method = RequestMethod.POST)
	private JSONObject uploadCsv_price(@RequestParam(value = "file") MultipartFile file, HttpServletResponse response,
			HttpServletRequest request, int loginuser_id, String tenpo, String uploadtime) {

		JSONObject object = new JSONObject();
		result result = new result();

		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Cache-Control", "no-cache");

			if ("".equals(uploadtime) || uploadtime == null) {
				result.setState(0);
				result.setMsg("日付を選択してください。");
				object.put("rows", result);
				return object;
			}
			Date dataTime = sf5.parse(uploadtime);

			String path = "";
			if (config.ISLOCAL) {
				path = config.NE_UPLOADFILE_PLACE_LOCAL;
			} else {
				path = config.NE_UPLOADFILE_PLACE_SERVER;
			}

			Date now = new Date();

			if (!"csv".equals(FileUtil.getFileType(file.getOriginalFilename()))) {
				result.setState(0);
				result.setMsg("csvファイルをアップロードしてください。");
				object.put("rows", result);
				return object;
			}

			InputStream is = file.getInputStream();

			List<price> price_add = new ArrayList<price>();
			Integer code_index = null;
			Integer name_index = null;
			Integer postage_set_index = null;
			Integer price_index = null;
			String uuid = UUID.randomUUID().toString();

			// データ本体を読み込みます
			if ("あかね楽天".equals(tenpo) || "アリス楽天".equals(tenpo) || "暁".equals(tenpo)) {
				//			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "SHIFT_JIS"));
				int num = 0;
				Reader rd = new BufferedReader(new InputStreamReader(is, "SHIFT_JIS"));
				while (true) {
					List<String> row = CsvUtil.readCsv(rd);
					if (row == null) {
						break;
					} else {
						//					System.out.println(row);
						//					System.out.println("-----------------");
						if (num == 0) {
							Object[] item_ = row.toArray();
							String[] item = new String[item_.length];
							System.arraycopy(item_, 0, item, 0, item_.length);

							if ("あかね楽天".equals(tenpo) || "アリス楽天".equals(tenpo) || "暁".equals(tenpo)) {
								if (!CommonUtil.isHave(item, "商品番号") || !CommonUtil.isHave(item, "商品名")
										|| !CommonUtil.isHave(item, "配送方法セット管理番号")
										|| !CommonUtil.isHave(item, "販売価格")) {
									result.setState(0);
									result.setMsg("正しいcsvファイルをアップロードしてください。");
									object.put("rows", result);
									return object;
								}
								for (int i = 0; i < item.length; i++) {
									if ("商品番号".equals(CsvUtil.getValue(item, i))) {
										code_index = i;
									} else if ("商品名".equals(CsvUtil.getValue(item, i))) {
										name_index = i;
									} else if ("配送方法セット管理番号".equals(CsvUtil.getValue(item, i))) {
										postage_set_index = i;
									} else if ("販売価格".equals(CsvUtil.getValue(item, i))) {
										price_index = i;
									}
								}
							}
						} else {
							Object[] item_ = row.toArray();
							String[] item = new String[item_.length];
							System.arraycopy(item_, 0, item, 0, item_.length);

							price pr = new price();

							String code = CsvUtil.getValue(item, code_index);
							String name = CsvUtil.getValue(item, name_index);
							String price = CsvUtil.getValue(item, price_index);
							String postage_set = CsvUtil.getValue(item, postage_set_index);

							pr.setDataTime(dataTime);
							pr.setName(name);
							pr.setImport_date(now);
							pr.setImport_userId(loginuser_id);
							pr.setTenpo_code(tenpo);
							pr.setCode(code);
							pr.setPrice(price);
							pr.setPostage_set(postage_set);
							pr.setUuid(uuid);

							price_add.add(pr);
						}
						num++;
					}
				}
			} else if ("あかねYahoo".equals(tenpo) || "FKstyle".equals(tenpo) || "KT雑貨Yahoo".equals(tenpo)
					|| "Lucky9".equals(tenpo)) {
				int num = 0;
				String line = null;
				//				BufferedReader reader = new BufferedReader(new InputStreamReader(is, "Shift-JIS"));
				InputStreamReader read = new InputStreamReader(is, "Shift-JIS");
				BufferedReader bufferedReader = new BufferedReader(read);

				CSVReader csvReader = new CSVReader(new InputStreamReader(is, "Shift-JIS"), CSVParser.DEFAULT_SEPARATOR,
						CSVParser.DEFAULT_QUOTE_CHARACTER, CSVParser.DEFAULT_ESCAPE_CHARACTER, 0);

				String[] item;
				while ((item = csvReader.readNext()) != null) {

					if (num == 0) {
						if (!CommonUtil.isHave(item, "code") || !CommonUtil.isHave(item, "name")
								|| !CommonUtil.isHave(item, "postage-set") || !CommonUtil.isHave(item, "price")) {
							result.setState(0);
							result.setMsg("正しいcsvファイルをアップロードしてください。");
							object.put("rows", result);
							return object;
						}
						for (int i = 0; i < item.length; i++) {
							if ("code".equals(CsvUtil.getValue(item, i))) {
								code_index = i;
							} else if ("name".equals(CsvUtil.getValue(item, i))) {
								name_index = i;
							} else if ("postage-set".equals(CsvUtil.getValue(item, i))) {
								postage_set_index = i;
							} else if ("price".equals(CsvUtil.getValue(item, i))) {
								price_index = i;
							}
						}
					} else {
						price pr = new price();

						String code = CsvUtil.getValue(item, code_index);
						String name = CsvUtil.getValue(item, name_index);
						String price = CsvUtil.getValue(item, price_index);
						String postage_set = CsvUtil.getValue(item, postage_set_index);

						pr.setDataTime(dataTime);
						pr.setName(name);
						pr.setImport_date(now);
						pr.setImport_userId(loginuser_id);
						pr.setTenpo_code(tenpo);
						pr.setCode(code);
						pr.setPrice(price);
						pr.setPostage_set(postage_set);
						pr.setUuid(uuid);

						price_add.add(pr);
					}
					num++;
				}
			} else if ("問屋よかろうもん".equals(tenpo)) {
				int num = 0;
				String line = null;
				//				BufferedReader reader = new BufferedReader(new InputStreamReader(is, "Shift-JIS"));
				InputStreamReader read = new InputStreamReader(is, "Shift-JIS");
				BufferedReader bufferedReader = new BufferedReader(read);

				CSVReader csvReader = new CSVReader(new InputStreamReader(is, "Shift-JIS"), CSVParser.DEFAULT_SEPARATOR,
						CSVParser.DEFAULT_QUOTE_CHARACTER, CSVParser.DEFAULT_ESCAPE_CHARACTER, 0);

				String[] item;
				while ((item = csvReader.readNext()) != null) {

					if (num == 0) {
						if (!CommonUtil.isHave(item, "独自商品コード") || !CommonUtil.isHave(item, "商品名")
								|| !CommonUtil.isHave(item, "販売価格") || !CommonUtil.isHave(item, "送料個別設定")) {
							result.setState(0);
							result.setMsg("正しいcsvファイルをアップロードしてください。");
							object.put("rows", result);
							return object;
						}
						for (int i = 0; i < item.length; i++) {
							if ("独自商品コード".equals(CsvUtil.getValue(item, i))) {
								code_index = i;
							} else if ("商品名".equals(CsvUtil.getValue(item, i))) {
								name_index = i;
							} else if ("送料個別設定".equals(CsvUtil.getValue(item, i))) {
								postage_set_index = i;
							} else if ("販売価格".equals(CsvUtil.getValue(item, i))) {
								price_index = i;
							}
						}
					} else {
						price pr = new price();

						String code = CsvUtil.getValue(item, code_index);
						String name = CsvUtil.getValue(item, name_index);
						String price = CsvUtil.getValue(item, price_index);
						String postage_set = CsvUtil.getValue(item, postage_set_index);

						pr.setDataTime(dataTime);
						pr.setName(name);
						pr.setImport_date(now);
						pr.setImport_userId(loginuser_id);
						pr.setTenpo_code(tenpo);
						pr.setCode(code);
						pr.setPrice(price);
						pr.setPostage_set(postage_set);
						pr.setUuid(uuid);

						price_add.add(pr);
					}
					num++;
				}
			} else if ("KuraNavi".equals(tenpo)) {
				int num = 0;
				String line = null;
				//				BufferedReader reader = new BufferedReader(new InputStreamReader(is, "Shift-JIS"));
				InputStreamReader read = new InputStreamReader(is, "Shift-JIS");
				BufferedReader bufferedReader = new BufferedReader(read);

				CSVReader csvReader = new CSVReader(new InputStreamReader(is, "Shift-JIS"), CSVParser.DEFAULT_SEPARATOR,
						CSVParser.DEFAULT_QUOTE_CHARACTER, CSVParser.DEFAULT_ESCAPE_CHARACTER, 0);

				String[] item;
				while ((item = csvReader.readNext()) != null) {

					if (num == 0) {
						if (!CommonUtil.isHave(item, "itemName") || !CommonUtil.isHave(item, "itemCode")
								|| !CommonUtil.isHave(item, "itemPrice")
								|| !CommonUtil.isHave(item, "deliveryMethodId1")) {
							result.setState(0);
							result.setMsg("正しいcsvファイルをアップロードしてください。");
							object.put("rows", result);
							return object;
						}
						for (int i = 0; i < item.length; i++) {
							if ("itemCode".equals(CsvUtil.getValue(item, i))) {
								code_index = i;
							} else if ("itemName".equals(CsvUtil.getValue(item, i))) {
								name_index = i;
							} else if ("deliveryMethodId1".equals(CsvUtil.getValue(item, i))) {
								postage_set_index = i;
							} else if ("itemPrice".equals(CsvUtil.getValue(item, i))) {
								price_index = i;
							}
						}
					} else {
						price pr = new price();

						String code = CsvUtil.getValue(item, code_index);
						String name = CsvUtil.getValue(item, name_index);
						String price = CsvUtil.getValue(item, price_index);
						String postage_set = CsvUtil.getValue(item, postage_set_index);

						pr.setDataTime(dataTime);
						pr.setName(name);
						pr.setImport_date(now);
						pr.setImport_userId(loginuser_id);
						pr.setTenpo_code(tenpo);
						pr.setCode(code);
						pr.setPrice(price);
						pr.setPostage_set(postage_set);
						pr.setUuid(uuid);

						price_add.add(pr);
					}
					num++;
				}
			} else if ("Amazon 雑貨の国のアリス".equals(tenpo) || "Amazon ヒューフリット".equals(tenpo)
					|| "Amazon 通販のトココ".equals(tenpo)
					|| "Amazon サラダ".equals(tenpo)) {
				int num = 0;
				String line = null;
				//				BufferedReader reader = new BufferedReader(new InputStreamReader(is, "Shift-JIS"));
				InputStreamReader read = new InputStreamReader(is, "Shift-JIS");
				BufferedReader bufferedReader = new BufferedReader(read);

				CSVReader csvReader = new CSVReader(new InputStreamReader(is, "Shift-JIS"), CSVParser.DEFAULT_SEPARATOR,
						CSVParser.DEFAULT_QUOTE_CHARACTER, CSVParser.DEFAULT_ESCAPE_CHARACTER, 0);

				String[] item;
				List<String> dcode_arr = new ArrayList<String>();
				String dataSource = "";
				if (config.ISLOCAL) {
					dataSource = config.DATASOURCE_LOCAL_YUBIN;
				} else {
					dataSource = config.DATASOURCE_SERVER_YUBIN;
				}

				DynamicDataSourceHolder.setDataSource(dataSource);
				while ((item = csvReader.readNext()) != null) {

					if (num == 0) {
						if (!CommonUtil.isHave(item, "商品名") || !CommonUtil.isHave(item, "出品者SKU")
								|| !CommonUtil.isHave(item, "価格")
								|| !CommonUtil.isHave(item, "merchant-shipping-group")) {
							result.setState(0);
							result.setMsg("正しいcsvファイルをアップロードしてください。");
							object.put("rows", result);
							return object;
						}
						for (int i = 0; i < item.length; i++) {
							if ("出品者SKU".equals(CsvUtil.getValue(item, i))) {
								code_index = i;
							} else if ("商品名".equals(CsvUtil.getValue(item, i))) {
								name_index = i;
							} else if ("merchant-shipping-group".equals(CsvUtil.getValue(item, i))) {
								postage_set_index = i;
							} else if ("価格".equals(CsvUtil.getValue(item, i))) {
								price_index = i;
							}
						}
					} else {
						price pr = new price();
						String code = CsvUtil.getValue(item, code_index);
						String dcode = priceService.getDcodeByLocation(code);
						if (dcode == null || "".equals(dcode)) {

						} else {
							code = dcode;
						}

						if (!dcode_arr.contains(code)) {
							dcode_arr.add(code);

							String name = CsvUtil.getValue(item, name_index);
							String price = CsvUtil.getValue(item, price_index);
							String postage_set = CsvUtil.getValue(item, postage_set_index);

							pr.setDataTime(dataTime);
							pr.setName(name);
							pr.setImport_date(now);
							pr.setImport_userId(loginuser_id);
							pr.setTenpo_code(tenpo);
							pr.setCode(code);
							pr.setPrice(price);
							pr.setPostage_set(postage_set);
							pr.setUuid(uuid);

							price_add.add(pr);
						}
					}
					num++;
				}
			}

			if (price_add.size() > 0) {
				DynamicDataSourceHolder.setDataSource("defultdataSource");

				//				priceService.deleteByToday(tenpo);
				priceService.deleteByDataTime(tenpo, uploadtime);

				priceService.insert(price_add);
				String date = sf6.format(now);
				String place_str = path + "\\wanfang_file_Price\\" + date;

				String originalFileName = file.getOriginalFilename();
				File place = new File(place_str);
				if (!place.exists()) {
					place.mkdir();
				}

				String newfilename = sf7.format(now) + "_" + originalFileName;
				File file1 = new File(place, newfilename);
				file.transferTo(file1);

				file upfile = new file();
				upfile.setChangeName(originalFileName);
				upfile.setMemo(uploadtime + "_" + tenpo);
				upfile.setName(newfilename);
				upfile.setPath(place + "\\" + newfilename);
				upfile.setType("price");
				upfile.setUpdatetime(now);
				upfile.setUser_id(loginuser_id);
				upfile.setParentId(uuid);
				fileService.insert(upfile);

				result.setState(1);
				result.setMsg(price_add.size() + "件を登録しました。");
				object.put("rows", result);
			} else {
				result.setState(1);
				result.setMsg("0件を登録しました。");
				object.put("rows", result);
			}

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
	@RequestMapping(value = "/deletePriceDataByFileId", method = RequestMethod.POST)
	private JSONObject deletePriceDataByFileId(HttpServletResponse response,
			HttpServletRequest request, String id) {

		JSONObject object = new JSONObject();
		result result = new result();

		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Cache-Control", "no-cache");

			file file = fileService.getFileById(id);
			if (file != null) {
				if (file.getParentId() != null && !"".equals(file.getParentId())) {
					String parentId = file.getParentId();
					int count = priceService.getCountByUuid(parentId);
					if (count > 0) {
						priceService.deleteByUuid(parentId);
						fileService.changeDataStateById(id, "削除済み");
						result.setState(1);
						result.setMsg(count + "件を削除しました。");
					}
				}
			}
			object.put("rows", result);
			return object;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
			return object;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/backPriceDataByFileId", method = RequestMethod.POST)
	private JSONObject backPriceDataByFileId(HttpServletResponse response,
			HttpServletRequest request, String id) {

		JSONObject object = new JSONObject();
		result result = new result();

		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Cache-Control", "no-cache");

			file file = fileService.getFileById(id);
			if (file != null) {
				if (file.getParentId() != null && !"".equals(file.getParentId())) {
					String parentId = file.getParentId();
					int count = priceService.getCountByUuid(parentId);
					if (count > 0) {
						priceService.backByUuid(parentId);
						fileService.changeDataStateById(id, "");
						result.setState(1);
						result.setMsg(count + "件を削除しました。");
					}
				}
			}
			object.put("rows", result);
			return object;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
			return object;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/getHikakuData", method = RequestMethod.POST)
	private JSONObject getHikakuData(HttpServletResponse response,
			HttpServletRequest request, String start, String end, String kakaku1, int pageCount, int currentPage) {
		DynamicDataSourceHolder.setDataSource("defultdataSource");
		JSONObject object = new JSONObject();
		result result = new result();

		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Cache-Control", "no-cache");

			List<ne_hikaku> price_hikaku = new ArrayList<ne_hikaku>();
			//			price_hikaku = priceService.getHikakuDataByDate(kakaku1, start, end);

			List<price> codes = priceService.getDistinctCodeByDate(start, end, 1);

			List<price> pr_all = priceService.getDistinctCodeDataByDate(start, end, 1);

			int Price_Hikaku = 0;
			int Price_Rashop = 0;
			int Price_Ralice = 0;
			int Price_Akatsuki = 0;
			int Price_Yashop = 0;
			int Price_FK = 0;
			int Price_KT = 0;
			int Price_L9 = 0;
			int Price_Tonya = 0;
			int Price_KN = 0;
			int Price_AmzAlice = 0;
			int Price_AmzFT = 0;
			int Price_AmzTKK = 0;
			int Price_AmzSD = 0;

			//			long Time_Hikaku = 0;
			long Time_Rashop = 0;
			long Time_Ralice = 0;
			long Time_Akatsuki = 0;
			long Time_Yashop = 0;
			long Time_FK = 0;
			long Time_KT = 0;
			long Time_L9 = 0;
			long Time_Tonya = 0;
			long Time_KN = 0;
			long Time_AmzAlice = 0;
			long Time_AmzFT = 0;
			long Time_AmzTKK = 0;
			long Time_AmzSD = 0;

			boolean isHava_Rashop = false;
			boolean isHava_Ralice = false;
			boolean isHava_Akatsuki = false;
			boolean isHava_Yashop = false;
			boolean isHava_FK = false;
			boolean isHava_KT = false;
			boolean isHava_L9 = false;
			boolean isHava_Tonya = false;
			boolean isHava_KN = false;
			boolean isHava_AmzAlice = false;
			boolean isHava_AmzFT = false;
			boolean isHava_AmzTKK = false;
			boolean isHava_AmzSD = false;

			String zyun_hidukechikai = "日付が近い順";
			String zyun_yasui = "価格が安い順";
			String zyun_takai = "価格が高い順";

			if (codes.size() > 0 && pr_all.size() > 0) {
				for (int i = 0; i < codes.size(); i++) {
					String code = codes.get(i).getCode().trim();
					if (code != null && !"".equals(code)) {
						ne_hikaku ne_hikaku = new ne_hikaku();
						ne_hikaku.setCode(code);

						Price_Hikaku = 0;

						Price_Rashop = 0;
						Price_Ralice = 0;
						Price_Akatsuki = 0;
						Price_Yashop = 0;
						Price_FK = 0;
						Price_KT = 0;
						Price_L9 = 0;
						Price_Tonya = 0;
						Price_KN = 0;
						Price_AmzAlice = 0;
						Price_AmzFT = 0;
						Price_AmzTKK = 0;
						Price_AmzSD = 0;

						Time_Rashop = 0;
						Time_Ralice = 0;
						Time_Akatsuki = 0;
						Time_Yashop = 0;
						Time_FK = 0;
						Time_KT = 0;
						Time_L9 = 0;
						Time_Tonya = 0;
						Time_KN = 0;
						Time_AmzAlice = 0;
						Time_AmzFT = 0;
						Time_AmzTKK = 0;
						Time_AmzSD = 0;

						isHava_Rashop = false;
						isHava_Ralice = false;
						isHava_Akatsuki = false;
						isHava_Yashop = false;
						isHava_FK = false;
						isHava_KT = false;
						isHava_L9 = false;
						isHava_Tonya = false;
						isHava_KN = false;
						isHava_AmzAlice = false;
						isHava_AmzFT = false;
						isHava_AmzTKK = false;
						isHava_AmzSD = false;

						//kakaku1 日付が近い順 価格が安い順 価格が高い順
						for (int j = 0; j < pr_all.size(); j++) {
							if (pr_all.get(j).getCode() != null) {
								if (pr_all.get(j).getCode().equals(code)) {
									if (pr_all.get(j).getPrice() != null) {
										if (!"".equals(pr_all.get(j).getPrice())
												&& CommonUtil.isInteger(pr_all.get(j).getPrice())) {
											Price_Hikaku = Integer.parseInt(pr_all.get(j).getPrice());
											if ("あかね楽天".equals(pr_all.get(j).getTenpo_code())) {
												if (zyun_hidukechikai.equals(kakaku1)) {
													if (Time_Rashop == 0) {
														Time_Rashop = pr_all.get(j).getDataTime().getTime();
														Price_Rashop = Price_Hikaku;
													} else {
														if (pr_all.get(j).getDataTime().getTime() > Time_Rashop) {
															Time_Rashop = pr_all.get(j).getDataTime().getTime();
															Price_Rashop = Price_Hikaku;
														}
													}
												} else if (zyun_yasui.equals(kakaku1)) {
													if (Price_Rashop == 0) {
														Price_Rashop = Price_Hikaku;
													} else {
														if (Price_Hikaku < Price_Rashop) {
															Price_Rashop = Price_Hikaku;
														}
													}
												} else if (zyun_takai.equals(kakaku1)) {
													if (Price_Hikaku > Price_Rashop) {
														Price_Rashop = Price_Hikaku;
													}
												}
												isHava_Rashop = true;
											} else if ("アリス楽天".equals(pr_all.get(j).getTenpo_code())) {
												if (zyun_hidukechikai.equals(kakaku1)) {
													if (Time_Ralice == 0) {
														Time_Ralice = pr_all.get(j).getDataTime().getTime();
														Price_Ralice = Price_Hikaku;
													} else {
														if (pr_all.get(j).getDataTime().getTime() > Time_Ralice) {
															Time_Ralice = pr_all.get(j).getDataTime().getTime();
															Price_Ralice = Price_Hikaku;
														}
													}
												} else if (zyun_yasui.equals(kakaku1)) {
													if (Price_Ralice == 0) {
														Price_Ralice = Price_Hikaku;
													} else {
														if (Price_Hikaku < Price_Ralice) {
															Price_Ralice = Price_Hikaku;
														}
													}
												} else if (zyun_takai.equals(kakaku1)) {
													if (Price_Hikaku > Price_Ralice) {
														Price_Ralice = Price_Hikaku;
													}
												}
												isHava_Ralice = true;
											} else if ("暁".equals(pr_all.get(j).getTenpo_code())) {
												if (zyun_hidukechikai.equals(kakaku1)) {
													if (Time_Akatsuki == 0) {
														Time_Akatsuki = pr_all.get(j).getDataTime().getTime();
														Price_Akatsuki = Price_Hikaku;
													} else {
														if (pr_all.get(j).getDataTime().getTime() > Time_Akatsuki) {
															Time_Akatsuki = pr_all.get(j).getDataTime().getTime();
															Price_Akatsuki = Price_Hikaku;
														}
													}
												} else if (zyun_yasui.equals(kakaku1)) {
													if (Price_Akatsuki == 0) {
														Price_Akatsuki = Price_Hikaku;
													} else {
														if (Price_Hikaku < Price_Akatsuki) {
															Price_Akatsuki = Price_Hikaku;
														}
													}
												} else if (zyun_takai.equals(kakaku1)) {
													if (Price_Hikaku > Price_Akatsuki) {
														Price_Akatsuki = Price_Hikaku;
													}
												}
												isHava_Akatsuki = true;
											} else if ("あかねYahoo".equals(pr_all.get(j).getTenpo_code())) {
												if (zyun_hidukechikai.equals(kakaku1)) {
													if (Time_Yashop == 0) {
														Time_Yashop = pr_all.get(j).getDataTime().getTime();
														Price_Yashop = Price_Hikaku;
													} else {
														if (pr_all.get(j).getDataTime().getTime() > Time_Yashop) {
															Time_Yashop = pr_all.get(j).getDataTime().getTime();
															Price_Yashop = Price_Hikaku;
														}
													}
												} else if (zyun_yasui.equals(kakaku1)) {
													if (Price_Yashop == 0) {
														Price_Yashop = Price_Hikaku;
													} else {
														if (Price_Hikaku < Price_Yashop) {
															Price_Yashop = Price_Hikaku;
														}
													}
												} else if (zyun_takai.equals(kakaku1)) {
													if (Price_Hikaku > Price_Yashop) {
														Price_Yashop = Price_Hikaku;
													}
												}
												isHava_Yashop = true;
											} else if ("FKstyle".equals(pr_all.get(j).getTenpo_code())) {
												if (zyun_hidukechikai.equals(kakaku1)) {
													if (Time_FK == 0) {
														Time_FK = pr_all.get(j).getDataTime().getTime();
														Price_FK = Price_Hikaku;
													} else {
														if (pr_all.get(j).getDataTime().getTime() > Time_FK) {
															Time_FK = pr_all.get(j).getDataTime().getTime();
															Price_FK = Price_Hikaku;
														}
													}
												} else if (zyun_yasui.equals(kakaku1)) {
													if (Price_FK == 0) {
														Price_FK = Price_Hikaku;
													} else {
														if (Price_Hikaku < Price_FK) {
															Price_FK = Price_Hikaku;
														}
													}
												} else if (zyun_takai.equals(kakaku1)) {
													if (Price_Hikaku > Price_FK) {
														Price_FK = Price_Hikaku;
													}
												}
												isHava_FK = true;
											} else if ("KT雑貨Yahoo".equals(pr_all.get(j).getTenpo_code())) {
												if (zyun_hidukechikai.equals(kakaku1)) {
													if (Time_KT == 0) {
														Time_KT = pr_all.get(j).getDataTime().getTime();
														Price_KT = Price_Hikaku;
													} else {
														if (pr_all.get(j).getDataTime().getTime() > Time_KT) {
															Time_KT = pr_all.get(j).getDataTime().getTime();
															Price_KT = Price_Hikaku;
														}
													}
												} else if (zyun_yasui.equals(kakaku1)) {
													if (Price_KT == 0) {
														Price_KT = Price_Hikaku;
													} else {
														if (Price_Hikaku < Price_KT) {
															Price_KT = Price_Hikaku;
														}
													}
												} else if (zyun_takai.equals(kakaku1)) {
													if (Price_Hikaku > Price_KT) {
														Price_KT = Price_Hikaku;
													}
												}
												isHava_KT = true;
											} else if ("Lucky9".equals(pr_all.get(j).getTenpo_code())) {
												if (zyun_hidukechikai.equals(kakaku1)) {
													if (Time_L9 == 0) {
														Time_L9 = pr_all.get(j).getDataTime().getTime();
														Price_L9 = Price_Hikaku;
													} else {
														if (pr_all.get(j).getDataTime().getTime() > Time_L9) {
															Time_L9 = pr_all.get(j).getDataTime().getTime();
															Price_L9 = Price_Hikaku;
														}
													}
												} else if (zyun_yasui.equals(kakaku1)) {
													if (Price_L9 == 0) {
														Price_L9 = Price_Hikaku;
													} else {
														if (Price_Hikaku < Price_L9) {
															Price_L9 = Price_Hikaku;
														}
													}
												} else if (zyun_takai.equals(kakaku1)) {
													if (Price_Hikaku > Price_L9) {
														Price_L9 = Price_Hikaku;
													}
												}
												isHava_L9 = true;
											} else if ("問屋よかろうもん".equals(pr_all.get(j).getTenpo_code())) {
												if (zyun_hidukechikai.equals(kakaku1)) {
													if (Time_Tonya == 0) {
														Time_Tonya = pr_all.get(j).getDataTime().getTime();
														Price_Tonya = Price_Hikaku;
													} else {
														if (pr_all.get(j).getDataTime().getTime() > Time_Tonya) {
															Time_Tonya = pr_all.get(j).getDataTime().getTime();
															Price_Tonya = Price_Hikaku;
														}
													}
												} else if (zyun_yasui.equals(kakaku1)) {
													if (Price_Tonya == 0) {
														Price_Tonya = Price_Hikaku;
													} else {
														if (Price_Hikaku < Price_Tonya) {
															Price_Tonya = Price_Hikaku;
														}
													}
												} else if (zyun_takai.equals(kakaku1)) {
													if (Price_Hikaku > Price_Tonya) {
														Price_Tonya = Price_Hikaku;
													}
												}
												isHava_Tonya = true;
											} else if ("KuraNavi".equals(pr_all.get(j).getTenpo_code())) {
												if (zyun_hidukechikai.equals(kakaku1)) {
													if (Time_KN == 0) {
														Time_KN = pr_all.get(j).getDataTime().getTime();
														Price_KN = Price_Hikaku;
													} else {
														if (pr_all.get(j).getDataTime().getTime() > Time_KN) {
															Time_KN = pr_all.get(j).getDataTime().getTime();
															Price_KN = Price_Hikaku;
														}
													}
												} else if (zyun_yasui.equals(kakaku1)) {
													if (Price_KN == 0) {
														Price_KN = Price_Hikaku;
													} else {
														if (Price_Hikaku < Price_KN) {
															Price_KN = Price_Hikaku;
														}
													}
												} else if (zyun_takai.equals(kakaku1)) {
													if (Price_Hikaku > Price_KN) {
														Price_KN = Price_Hikaku;
													}
												}
												isHava_KN = true;
											} else if ("Amazon 雑貨の国のアリス".equals(pr_all.get(j).getTenpo_code())) {
												if (zyun_hidukechikai.equals(kakaku1)) {
													if (Time_AmzAlice == 0) {
														Time_AmzAlice = pr_all.get(j).getDataTime().getTime();
														Price_AmzAlice = Price_Hikaku;
													} else {
														if (pr_all.get(j).getDataTime().getTime() > Time_AmzAlice) {
															Time_AmzAlice = pr_all.get(j).getDataTime().getTime();
															Price_AmzAlice = Price_Hikaku;
														}
													}
												} else if (zyun_yasui.equals(kakaku1)) {
													if (Price_AmzAlice == 0) {
														Price_AmzAlice = Price_Hikaku;
													} else {
														if (Price_Hikaku < Price_AmzAlice) {
															Price_AmzAlice = Price_Hikaku;
														}
													}
												} else if (zyun_takai.equals(kakaku1)) {
													if (Price_Hikaku > Price_AmzAlice) {
														Price_AmzAlice = Price_Hikaku;
													}
												}
												isHava_AmzAlice = true;
											} else if ("Amazon ヒューフリット".equals(pr_all.get(j).getTenpo_code())) {
												if (zyun_hidukechikai.equals(kakaku1)) {
													if (Time_AmzFT == 0) {
														Time_AmzFT = pr_all.get(j).getDataTime().getTime();
														Price_AmzFT = Price_Hikaku;
													} else {
														if (pr_all.get(j).getDataTime().getTime() > Time_AmzFT) {
															Time_AmzFT = pr_all.get(j).getDataTime().getTime();
															Price_AmzFT = Price_Hikaku;
														}
													}
												} else if (zyun_yasui.equals(kakaku1)) {
													if (Price_AmzFT == 0) {
														Price_AmzFT = Price_Hikaku;
													} else {
														if (Price_Hikaku < Price_AmzFT) {
															Price_AmzFT = Price_Hikaku;
														}
													}
												} else if (zyun_takai.equals(kakaku1)) {
													if (Price_Hikaku > Price_AmzFT) {
														Price_AmzFT = Price_Hikaku;
													}
												}
												isHava_AmzFT = true;
											} else if ("Amazon 通販のトココ".equals(pr_all.get(j).getTenpo_code())) {
												if (zyun_hidukechikai.equals(kakaku1)) {
													if (Time_AmzTKK == 0) {
														Time_AmzTKK = pr_all.get(j).getDataTime().getTime();
														Price_AmzTKK = Price_Hikaku;
													} else {
														if (pr_all.get(j).getDataTime().getTime() > Time_Rashop) {
															Time_AmzTKK = pr_all.get(j).getDataTime().getTime();
															Price_AmzTKK = Price_Hikaku;
														}
													}
												} else if (zyun_yasui.equals(kakaku1)) {
													if (Price_AmzTKK == 0) {
														Price_AmzTKK = Price_Hikaku;
													} else {
														if (Price_Hikaku < Price_AmzTKK) {
															Price_AmzTKK = Price_Hikaku;
														}
													}
												} else if (zyun_takai.equals(kakaku1)) {
													if (Price_Hikaku > Price_AmzTKK) {
														Price_AmzTKK = Price_Hikaku;
													}
												}
												isHava_AmzTKK = true;
											} else if ("Amazon サラダ".equals(pr_all.get(j).getTenpo_code())) {
												if (zyun_hidukechikai.equals(kakaku1)) {
													if (Time_AmzSD == 0) {
														Time_AmzSD = pr_all.get(j).getDataTime().getTime();
														Price_AmzSD = Price_Hikaku;
													} else {
														if (pr_all.get(j).getDataTime().getTime() > Time_AmzSD) {
															Time_AmzSD = pr_all.get(j).getDataTime().getTime();
															Price_AmzSD = Price_Hikaku;
														}
													}
												} else if (zyun_yasui.equals(kakaku1)) {
													if (Price_AmzSD == 0) {
														Price_AmzSD = Price_Hikaku;
													} else {
														if (Price_Hikaku < Price_AmzSD) {
															Price_AmzSD = Price_Hikaku;
														}
													}
												} else if (zyun_takai.equals(kakaku1)) {
													if (Price_Hikaku > Price_AmzSD) {
														Price_AmzSD = Price_Hikaku;
													}
												}
												isHava_AmzSD = true;
											}
										}
									}
								}
							}
						}

						if (isHava_Rashop) {
							ne_hikaku.setValue1(String.valueOf(Price_Rashop));
						}

						if (isHava_Ralice) {
							ne_hikaku.setValue2(String.valueOf(Price_Ralice));
						}

						if (isHava_Akatsuki) {
							ne_hikaku.setValue3(String.valueOf(Price_Akatsuki));
						}

						if (isHava_Yashop) {
							ne_hikaku.setValue4(String.valueOf(Price_Yashop));
						}

						if (isHava_FK) {
							ne_hikaku.setValue5(String.valueOf(Price_FK));
						}

						if (isHava_KT) {
							ne_hikaku.setValue6(String.valueOf(Price_KT));
						}

						if (isHava_L9) {
							ne_hikaku.setValue7(String.valueOf(Price_L9));
						}

						if (isHava_Tonya) {
							ne_hikaku.setValue8(String.valueOf(Price_Tonya));
						}

						if (isHava_KN) {
							ne_hikaku.setValue9(String.valueOf(Price_KN));
						}

						if (isHava_AmzAlice) {
							ne_hikaku.setValue10(String.valueOf(Price_AmzAlice));
						}

						if (isHava_AmzFT) {
							ne_hikaku.setValue11(String.valueOf(Price_AmzFT));
						}

						if (isHava_AmzTKK) {
							ne_hikaku.setValue12(String.valueOf(Price_AmzTKK));
						}

						if (isHava_AmzSD) {
							ne_hikaku.setValue13(String.valueOf(Price_AmzSD));
						}

						price_hikaku.add(ne_hikaku);
					}
				}
			}

			int current = (currentPage - 1) * pageCount;
			int total = price_hikaku.size();

			List<ne_hikaku> price_hikaku_rs = new ArrayList<ne_hikaku>();

			if (total > 0) {
				int index_last = current + pageCount;
				if (index_last > total) {
					index_last = total;
				}
				price_hikaku_rs = price_hikaku.subList(current, index_last);
			}

			object.put("hikaku", price_hikaku_rs);
			object.put("total", total);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		return object;
	}

	@ResponseBody
	@RequestMapping(value = "/getPriceData", method = RequestMethod.POST)
	private JSONObject getPriceData(HttpServletResponse response, HttpServletRequest request, String date_s,
			String tenpo,
			int currentPage, int pageCount) {
		DynamicDataSourceHolder.setDataSource("defultdataSource");
		JSONObject object = new JSONObject();
		try {
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Cache-Control", "no-cache");
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");

			int current = (currentPage - 1) * pageCount;
			List<price> dataList = priceService.getPriceData(date_s, tenpo, 1, current, pageCount);

			for (int i = 0; i < dataList.size(); i++) {
				String tenpo_ = dataList.get(i).getTenpo_code();
				if ("あかね楽天".equals(tenpo_) || "アリス楽天".equals(tenpo_)) {
					if ("1".equals(dataList.get(i).getPostage_set())) {
						dataList.get(i).setPostage("宅配便");
					} else if ("2".equals(dataList.get(i).getPostage_set())) {
						dataList.get(i).setPostage("メール");
					} else if ("4".equals(dataList.get(i).getPostage_set())) {
						dataList.get(i).setPostage("定形外");
					} else if ("1".equals(dataList.get(i).getPostage_set())) {
						dataList.get(i).setPostage("デフォルト");
					}
				} else if ("暁".equals(tenpo_)) {
					if ("2".equals(dataList.get(i).getPostage_set()) || "6".equals(dataList.get(i).getPostage_set())) {
						dataList.get(i).setPostage("宅配便");
					} else if ("4".equals(dataList.get(i).getPostage_set())
							|| "7".equals(dataList.get(i).getPostage_set())) {
						dataList.get(i).setPostage("メール");
					} else if ("5".equals(dataList.get(i).getPostage_set())
							|| "8".equals(dataList.get(i).getPostage_set())) {
						dataList.get(i).setPostage("定形外");
					} else if ("3".equals(dataList.get(i).getPostage_set())) {
						dataList.get(i).setPostage("大型宅配便");
					} else if ("1".equals(dataList.get(i).getPostage_set())) {
						dataList.get(i).setPostage("デフォルト");
					}
				} else if ("あかねYahoo".equals(tenpo_) || "Lucky9".equals(tenpo_)) {
					if ("1".equals(dataList.get(i).getPostage_set())) {
						dataList.get(i).setPostage("デフォルト");
					} else if ("2".equals(dataList.get(i).getPostage_set())) {
						dataList.get(i).setPostage("宅配便");
					} else if ("3".equals(dataList.get(i).getPostage_set())) {
						dataList.get(i).setPostage("メール");
					} else if ("4".equals(dataList.get(i).getPostage_set())) {
						dataList.get(i).setPostage("定形外");
					} else if ("5".equals(dataList.get(i).getPostage_set())) {
						dataList.get(i).setPostage("大型宅配便");
					}
				} else if ("FKstyle".equals(tenpo_)) {
					if ("1".equals(dataList.get(i).getPostage_set())) {
						dataList.get(i).setPostage("デフォルト");
					} else if ("2".equals(dataList.get(i).getPostage_set())
							|| "6".equals(dataList.get(i).getPostage_set())) {
						dataList.get(i).setPostage("宅配便");
					} else if ("3".equals(dataList.get(i).getPostage_set())) {
						dataList.get(i).setPostage("メール");
					} else if ("4".equals(dataList.get(i).getPostage_set())) {
						dataList.get(i).setPostage("定形外");
					} else if ("5".equals(dataList.get(i).getPostage_set())
							|| "7".equals(dataList.get(i).getPostage_set())) {
						dataList.get(i).setPostage("大型宅配便");
					}
				} else if ("KT雑貨Yahoo".equals(tenpo_)) {
					if ("1".equals(dataList.get(i).getPostage_set())) {
						dataList.get(i).setPostage("デフォルト");
					} else if ("2".equals(dataList.get(i).getPostage_set())
							|| "6".equals(dataList.get(i).getPostage_set())) {
						dataList.get(i).setPostage("宅配便");
					} else if ("3".equals(dataList.get(i).getPostage_set())
							|| "7".equals(dataList.get(i).getPostage_set())) {
						dataList.get(i).setPostage("メール");
					} else if ("4".equals(dataList.get(i).getPostage_set())
							|| "8".equals(dataList.get(i).getPostage_set())) {
						dataList.get(i).setPostage("定形外");
					} else if ("5".equals(dataList.get(i).getPostage_set())
							|| "9".equals(dataList.get(i).getPostage_set())) {
						dataList.get(i).setPostage("大型宅配便");
					}
				} else if ("問屋よかろうもん".equals(tenpo_)) {
					if ("20170407121342".equals(dataList.get(i).getPostage_set())) {
						dataList.get(i).setPostage("宅配便");
					} else if ("20170407121422".equals(dataList.get(i).getPostage_set())) {
						dataList.get(i).setPostage("メール");
					} else if ("20180402101403".equals(dataList.get(i).getPostage_set())) {
						dataList.get(i).setPostage("定形外");
					} else if ("20170407121633".equals(dataList.get(i).getPostage_set())
							|| "20200916153725".equals(dataList.get(i).getPostage_set())) {
						dataList.get(i).setPostage("大型宅配便");
					}
				} else if ("KuraNavi".equals(tenpo_)) {
					if ("T".equals(dataList.get(i).getPostage_set())) {
						dataList.get(i).setPostage("宅配便");
					} else if ("M".equals(dataList.get(i).getPostage_set())) {
						dataList.get(i).setPostage("メール");
					} else if ("K".equals(dataList.get(i).getPostage_set())) {
						dataList.get(i).setPostage("定形外");
					} else if ("O".equals(dataList.get(i).getPostage_set())) {
						dataList.get(i).setPostage("大型宅配便");
					}
				} else if ("Amazon 雑貨の国のアリス".equals(tenpo_) || "Amazon ヒューフリット".equals(tenpo_)
						|| "Amazon 通販のトココ".equals(tenpo_) || "Amazon サラダ".equals(tenpo_)) {
					if ("通常宅配便".equals(dataList.get(i).getPostage_set())) {
						dataList.get(i).setPostage("宅配便");
					} else if ("メール便".equals(dataList.get(i).getPostage_set())) {
						dataList.get(i).setPostage("メール");
					} else if ("大型商品".equals(dataList.get(i).getPostage_set())
							|| "特大商品".equals(dataList.get(i).getPostage_set())) {
						dataList.get(i).setPostage("大型宅配便");
					} else if ("プライム対象".equals(dataList.get(i).getPostage_set())) {
						dataList.get(i).setPostage("プライム");
					}
				}
			}

			int total = priceService.getPriceData_total(date_s, tenpo, 1);

			object.put("dataList", dataList);
			object.put("total", total);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return object;
	}

	@ResponseBody
	@RequestMapping(value = "/getUploadRirekiByPrice", method = RequestMethod.POST)
	private JSONObject getUploadRirekiByPrice(HttpServletResponse response, HttpServletRequest request) {
		DynamicDataSourceHolder.setDataSource("defultdataSource");
		JSONObject object = new JSONObject();
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Cache-Control", "no-cache");

			List<file> datalist = fileService.getUploadRirekiByType("price");

			object.put("datalist", datalist);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		return object;
	}

}
