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
import com.mp.entity.price;
import com.mp.entity.ne_hikaku;
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

				DynamicDataSourceHolder.setDataSource("jrt_dataSource_server");
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
	@RequestMapping(value = "/getHikakuData", method = RequestMethod.POST)
	private JSONObject getHikakuData(HttpServletResponse response,
			HttpServletRequest request, String start, String end, int pageCount, int currentPage) {
		DynamicDataSourceHolder.setDataSource("defultdataSource");
		JSONObject object = new JSONObject();
		result result = new result();

		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Cache-Control", "no-cache");

			//			List<price> pr = priceService.getDistinctCodeByDate(start, end);

			//			String tenpo_arr[] = new String[] { "あかね楽天", "アリス楽天", "暁", "あかねYahoo", "FKstyle", "KT雑貨Yahoo", "Lucky9",
			//					"問屋よかろうもん", "KuraNavi", "Amazon 雑貨の国のアリス", "Amazon ヒューフリット", "Amazon 通販のトココ", "Amazon サラダ" };
			//
			//			if (pr.size() > 0) {
			//				for (int i = 0; i < pr.size(); i++) {
			//					price_hikaku ph = new price_hikaku();
			//					String code = pr.get(i).getCode();
			//
			//					ph.setCode(code);
			//
			//					for (int j = 0; j < tenpo_arr.length; j++) {
			//						price pi = priceService.getHikakuDataByCodeAndDate(tenpo_arr[j], code, start, end);
			//						String price = "";
			//						if (pi != null) {
			//							price = pi.getPrice();
			//						}
			//						if (j == 0) {
			//							ph.setValue1(price);
			//						} else if (j == 1) {
			//							ph.setValue2(price);
			//						} else if (j == 2) {
			//							ph.setValue3(price);
			//						} else if (j == 3) {
			//							ph.setValue4(price);
			//						} else if (j == 4) {
			//							ph.setValue5(price);
			//						} else if (j == 5) {
			//							ph.setValue6(price);
			//						} else if (j == 6) {
			//							ph.setValue7(price);
			//						} else if (j == 7) {
			//							ph.setValue8(price);
			//						} else if (j == 8) {
			//							ph.setValue9(price);
			//						} else if (j == 9) {
			//							ph.setValue10(price);
			//						} else if (j == 10) {
			//							ph.setValue11(price);
			//						} else if (j == 11) {
			//							ph.setValue12(price);
			//						} else if (j == 12) {
			//							ph.setValue13(price);
			//						}
			//					}
			//					hikaku.add(ph);
			//				}
			//			}

			//			List<String> codes = new ArrayList<String>();
			//			if (pr.size() > 0) {
			//				for (int i = 0; i < pr.size(); i++) {
			//					if(!"".equals(pr.get(i).getCode()) && pr.get(i).getCode() != null) {
			//						codes.add(pr.get(i).getCode());
			//					}
			//
			//				}
			//			}
			List<ne_hikaku> price_hikaku = new ArrayList<ne_hikaku>();

			List<String> codes = new ArrayList<String>();
			List<price> pr = priceService.getDistinctCodeByDate(start, end);
			int total = pr.size();
			int current = (currentPage - 1) * pageCount;

			if (pr.size() > 0) {
				for (int i = current; i < current + pageCount; i++) {
					if (!"".equals(pr.get(i).getCode()) && pr.get(i).getCode() != null) {
						codes.add(pr.get(i).getCode());
					}
				}
			}

			if (codes.size() > 0) {
				price_hikaku = priceService.getHikakuDataByDate(codes, start, end);
				//				if(price_hikaku.size() > 0) {
				//					for (int i = 0; i < price_hikaku.size(); i++) {
				//						if(price_hikaku.get(i).getValue1() == null || "".equals(price_hikaku.get(i).getValue1())) {
				//							price_hikaku.get(i).setValue1("×");
				//						} else if (price_hikaku.get(i).getValue2() == null || "".equals(price_hikaku.get(i).getValue2())) {
				//							price_hikaku.get(i).setValue2("×");
				//						} else if (price_hikaku.get(i).getValue3() == null || "".equals(price_hikaku.get(i).getValue3())) {
				//							price_hikaku.get(i).setValue3("×");
				//						} else if (price_hikaku.get(i).getValue4() == null || "".equals(price_hikaku.get(i).getValue4())) {
				//							price_hikaku.get(i).setValue4("×");
				//						} else if (price_hikaku.get(i).getValue5() == null || "".equals(price_hikaku.get(i).getValue5())) {
				//							price_hikaku.get(i).setValue5("×");
				//						} else if (price_hikaku.get(i).getValue6() == null || "".equals(price_hikaku.get(i).getValue6())) {
				//							price_hikaku.get(i).setValue6("×");
				//						} else if (price_hikaku.get(i).getValue7() == null || "".equals(price_hikaku.get(i).getValue7())) {
				//							price_hikaku.get(i).setValue7("×");
				//						} else if (price_hikaku.get(i).getValue8() == null || "".equals(price_hikaku.get(i).getValue8())) {
				//							price_hikaku.get(i).setValue8("×");
				//						} else if (price_hikaku.get(i).getValue9() == null || "".equals(price_hikaku.get(i).getValue9())) {
				//							price_hikaku.get(i).setValue9("×");
				//						} else if (price_hikaku.get(i).getValue10() == null || "".equals(price_hikaku.get(i).getValue10())) {
				//							price_hikaku.get(i).setValue10("×");
				//						} else if (price_hikaku.get(i).getValue11() == null || "".equals(price_hikaku.get(i).getValue11())) {
				//							price_hikaku.get(i).setValue11("×");
				//						} else if (price_hikaku.get(i).getValue12() == null || "".equals(price_hikaku.get(i).getValue12())) {
				//							price_hikaku.get(i).setValue12("×");
				//						} else if (price_hikaku.get(i).getValue13() == null || "".equals(price_hikaku.get(i).getValue13())) {
				//							price_hikaku.get(i).setValue13("×");
				//						}
				//					}
				//				}
			}

			object.put("hikaku", price_hikaku);
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
			List<price> dataList = priceService.getPriceData(date_s, tenpo, current, pageCount);

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

			int total = priceService.getPriceData_total(date_s, tenpo);

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
