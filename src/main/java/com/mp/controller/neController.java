package com.mp.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mp.common.DynamicDataSourceHolder;
import com.mp.dto.option;
import com.mp.dto.result;
import com.mp.entity.config;
import com.mp.entity.file;
import com.mp.entity.ne;
import com.mp.service.commonService;
import com.mp.service.fileService;
import com.mp.service.neService;
import com.mp.util.CommonUtil;
import com.mp.util.FileUtil;

@Controller
public class neController {
	SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	SimpleDateFormat sf2 = new SimpleDateFormat("yyyy/MM/dd");
	SimpleDateFormat sf3 = new SimpleDateFormat("yyyy/MM/dd HH:mm");
	SimpleDateFormat sf4 = new SimpleDateFormat("yyyy-MM");
	SimpleDateFormat sf5 = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat sf6 = new SimpleDateFormat("yyyyMMdd");
	SimpleDateFormat sf7 = new SimpleDateFormat("yyyyMMddHHmmss");

	private static Pattern datePattern = Pattern.compile("^\\d{4}/\\d{1,2}/\\d{1,2}$");
	private static Pattern numPattern = Pattern.compile("^-?\\d+(\\.\\d*)?$");

	@Autowired
	private neService neService;

	@Autowired
	private fileService fileService;

	@Autowired
	private commonService commonService;

	@ResponseBody
	@RequestMapping(value = "/uploadCsv_ne", method = RequestMethod.POST)
	private JSONObject uploadCsv_ne(@RequestParam(value = "file") MultipartFile file, HttpServletResponse response,
			HttpServletRequest request, int loginuser_id) {
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
//			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "SHIFT_JIS"));
			Reader rd = new BufferedReader(new InputStreamReader(is, "SHIFT_JIS"));

			int num = 0;
			List<ne> ne_add = new ArrayList<ne>();
			Integer jyuchu_denpyo_no_index = null;
			Integer pic_siji_naiyou_index = null;
			Integer jyuchu_bi_index = null;
			Integer syuka_kakutei_bi_index = null;
			Integer nouhinsyo_insatusiji_bi_index = null;
			Integer nouhinsyo_insatuhakou_bi_index = null;
			Integer jyuchu_jyotai_kbn_index = null;
			Integer tenpo_denpyo_no_index = null;
			Integer tenpo_code_index = null;
			Integer jyuchu_tantou_code_index = null;
			Integer jyuchu_name_index = null;
			Integer syohin_kin_index = null;
			Integer zei_kin_index = null;
			Integer hasou_kin_index = null;
			Integer tesuryo_kin_index = null;
			Integer sonota_kin_index = null;
			Integer point_index = null;
			Integer goukei_kin_index = null;
			Integer hasou_name_index = null;
			Integer hasou_yubin_bangou_index = null;
			Integer hasou_jyusyo_index = null;
			Integer hasou_kbn_index = null;
			Integer siharai_kbn_index = null;
			Integer hasou_denpyo_no_index = null;
			Integer bikou_index = null;
			Integer jyuyou_index = null;
			Integer jyuyou_check_index = null;
			Integer jyuchu_tag_index = null;

			// データ本体を読み込みます
			while (true) {
				List<String> row = readCsv(rd);
				if (row == null) {
					break;
				} else {
//					System.out.println(row);
//					System.out.println("-----------------");

					if (num == 0) {
						Object[] item_ = row.toArray();
						String[] item = new String[item_.length];
						System.arraycopy(item_, 0, item, 0, item_.length);

						if (!CommonUtil.isHave(item, "伝票番号") || !CommonUtil.isHave(item, "ﾋﾟｯｷﾝｸﾞ指示内容")
								|| !CommonUtil.isHave(item, "受注日") || !CommonUtil.isHave(item, "出荷確定日")
								|| !CommonUtil.isHave(item, "納品書印刷指示日") || !CommonUtil.isHave(item, "納品書発行日")
								|| !CommonUtil.isHave(item, "状態") || !CommonUtil.isHave(item, "受注番号")
								|| !CommonUtil.isHave(item, "店舗") || !CommonUtil.isHave(item, "受注担当者")
								|| !CommonUtil.isHave(item, "購入者名") || !CommonUtil.isHave(item, "商品計")
								|| !CommonUtil.isHave(item, "税金") || !CommonUtil.isHave(item, "発送料")
								|| !CommonUtil.isHave(item, "手数料") || !CommonUtil.isHave(item, "他費用")
								|| !CommonUtil.isHave(item, "ポイント数") || !CommonUtil.isHave(item, "総合計")
								|| !CommonUtil.isHave(item, "送り先名") || !CommonUtil.isHave(item, "送り先〒")
								|| !CommonUtil.isHave(item, "送り先住所") || !CommonUtil.isHave(item, "発送方法")
								|| !CommonUtil.isHave(item, "支払方法") || !CommonUtil.isHave(item, "発送伝票番号")
								|| !CommonUtil.isHave(item, "備考") || !CommonUtil.isHave(item, "重要")
								|| !CommonUtil.isHave(item, "重要ﾁｪｯｸ者") || !CommonUtil.isHave(item, "受注分類タグ")) {
							result.setState(0);
							result.setMsg("正しいcsvファイルをアップロードしてください。");
							object.put("rows", result);
							return object;
						}
						for (int i = 0; i < item.length; i++) {
							if ("伝票番号".equals(getValue(item, i))) {
								jyuchu_denpyo_no_index = i;
							} else if ("ﾋﾟｯｷﾝｸﾞ指示内容".equals(getValue(item, i))) {
								pic_siji_naiyou_index = i;
							} else if ("受注日".equals(getValue(item, i))) {
								jyuchu_bi_index = i;
							} else if ("出荷確定日".equals(getValue(item, i))) {
								syuka_kakutei_bi_index = i;
							} else if ("納品書印刷指示日".equals(getValue(item, i))) {
								nouhinsyo_insatusiji_bi_index = i;
							} else if ("納品書発行日".equals(getValue(item, i))) {
								nouhinsyo_insatuhakou_bi_index = i;
							} else if ("状態".equals(getValue(item, i))) {
								jyuchu_jyotai_kbn_index = i;
							} else if ("受注番号".equals(getValue(item, i))) {
								tenpo_denpyo_no_index = i;
							} else if ("店舗".equals(getValue(item, i))) {
								tenpo_code_index = i;
							} else if ("受注担当者".equals(getValue(item, i))) {
								jyuchu_tantou_code_index = i;
							} else if ("購入者名".equals(getValue(item, i))) {
								jyuchu_name_index = i;
							} else if ("商品計".equals(getValue(item, i))) {
								syohin_kin_index = i;
							} else if ("税金".equals(getValue(item, i))) {
								zei_kin_index = i;
							} else if ("発送料".equals(getValue(item, i))) {
								hasou_kin_index = i;
							} else if ("手数料".equals(getValue(item, i))) {
								tesuryo_kin_index = i;
							} else if ("他費用".equals(getValue(item, i))) {
								sonota_kin_index = i;
							} else if ("ポイント数".equals(getValue(item, i))) {
								point_index = i;
							} else if ("総合計".equals(getValue(item, i))) {
								goukei_kin_index = i;
							} else if ("送り先名".equals(getValue(item, i))) {
								hasou_name_index = i;
							} else if ("送り先〒".equals(getValue(item, i))) {
								hasou_yubin_bangou_index = i;
							} else if ("送り先住所".equals(getValue(item, i))) {
								hasou_jyusyo_index = i;
							} else if ("発送方法".equals(getValue(item, i))) {
								hasou_kbn_index = i;
							} else if ("支払方法".equals(getValue(item, i))) {
								siharai_kbn_index = i;
							} else if ("発送伝票番号".equals(getValue(item, i))) {
								hasou_denpyo_no_index = i;
							} else if ("備考".equals(getValue(item, i))) {
								bikou_index = i;
							} else if ("重要".equals(getValue(item, i))) {
								jyuyou_index = i;
							} else if ("重要ﾁｪｯｸ者".equals(getValue(item, i))) {
								jyuyou_check_index = i;
							} else if ("受注分類タグ".equals(getValue(item, i))) {
								jyuchu_tag_index = i;
							}
						}
					} else {
						Object[] item_ = row.toArray();
						String[] item = new String[item_.length];
						System.arraycopy(item_, 0, item, 0, item_.length);

						ne ne = new ne();
//						if (num >= 62) {
//							System.out.println(123);
//						}

						ne.setImport_date(now);
						ne.setImport_user(loginuser_id);

						if ("".equals(getValue(item, jyuchu_denpyo_no_index))
								|| getValue(item, jyuchu_denpyo_no_index) == null) {
							result.setState(0);
							result.setMsg("伝票番号を確認してください。");
							object.put("rows", result);
							return object;
						}

						ne.setJyuchu_denpyo_no(Integer.valueOf(getValue(item, jyuchu_denpyo_no_index)));
						ne.setPic_siji_naiyou(getValue(item, pic_siji_naiyou_index));

						if ("".equals(getValue(item, jyuchu_bi_index)) || getValue(item, jyuchu_bi_index) == null) {
							ne.setJyuchu_bi(null);
						} else {
							ne.setJyuchu_bi(sf3.parse(getValue(item, jyuchu_bi_index)));
						}

						if ("".equals(getValue(item, syuka_kakutei_bi_index))
								|| getValue(item, syuka_kakutei_bi_index) == null) {
							ne.setSyuka_kakutei_bi(null);
						} else {
							ne.setSyuka_kakutei_bi(sf2.parse(getValue(item, syuka_kakutei_bi_index)));
						}

						if ("".equals(getValue(item, nouhinsyo_insatusiji_bi_index))
								|| getValue(item, nouhinsyo_insatusiji_bi_index) == null
								|| "0000/00/00".equals(getValue(item, nouhinsyo_insatusiji_bi_index))) {
							ne.setNouhinsyo_insatusiji_bi(null);
						} else {
							ne.setNouhinsyo_insatusiji_bi(sf2.parse(getValue(item, nouhinsyo_insatusiji_bi_index)));
						}

						if ("".equals(getValue(item, nouhinsyo_insatuhakou_bi_index))
								|| getValue(item, nouhinsyo_insatuhakou_bi_index) == null) {
							ne.setNouhinsyo_insatuhakou_bi(null);
						} else {
							ne.setNouhinsyo_insatuhakou_bi(sf3.parse(getValue(item, nouhinsyo_insatuhakou_bi_index)));
						}

						ne.setJyuchu_jyotai_kbn(getValue(item, jyuchu_jyotai_kbn_index));
						ne.setTenpo_denpyo_no(getValue(item, tenpo_denpyo_no_index));
						ne.setTenpo_code(getValue(item, tenpo_code_index));
						ne.setJyuchu_tantou_code(getValue(item, jyuchu_tantou_code_index));
						ne.setJyuchu_name(getValue(item, jyuchu_name_index));

						if ("".equals(getValue(item, syohin_kin_index)) || getValue(item, syohin_kin_index) == null) {
							ne.setSyohin_kin(0);
						} else {
							ne.setSyohin_kin(Integer.valueOf(getValue(item, syohin_kin_index).replace(",", "")));
						}

						if ("".equals(getValue(item, zei_kin_index)) || getValue(item, zei_kin_index) == null) {
							ne.setZei_kin(0);
						} else {
							ne.setZei_kin(Integer.valueOf(getValue(item, zei_kin_index).replace(",", "")));
						}

						if ("".equals(getValue(item, hasou_kin_index)) || getValue(item, hasou_kin_index) == null) {
							ne.setHasou_kin(0);
						} else {
							ne.setHasou_kin(Integer.valueOf(getValue(item, hasou_kin_index).replace(",", "")));
						}

						if ("".equals(getValue(item, tesuryo_kin_index)) || getValue(item, tesuryo_kin_index) == null) {
							ne.setTesuryo_kin(0);
						} else {
							ne.setTesuryo_kin(Integer.valueOf(getValue(item, tesuryo_kin_index).replace(",", "")));
						}

						if ("".equals(getValue(item, sonota_kin_index)) || getValue(item, sonota_kin_index) == null) {
							ne.setSonota_kin(0);
						} else {
							ne.setSonota_kin(Integer.valueOf(getValue(item, sonota_kin_index).replace(",", "")));
						}

						if ("".equals(getValue(item, point_index)) || getValue(item, point_index) == null) {
							ne.setPoint(0);
						} else {
							ne.setPoint(Integer.valueOf(getValue(item, point_index).replace(",", "")));
						}

						if ("".equals(getValue(item, goukei_kin_index)) || getValue(item, goukei_kin_index) == null) {
							ne.setGoukei_kin(0);
						} else {
							ne.setGoukei_kin(Integer.valueOf(getValue(item, goukei_kin_index).replace(",", "")));
						}

						ne.setHasou_name(getValue(item, hasou_name_index));
						ne.setHasou_yubin_bangou(getValue(item, hasou_yubin_bangou_index));
						ne.setHasou_jyusyo(getValue(item, hasou_jyusyo_index));
						ne.setHasou_kbn(getValue(item, hasou_kbn_index));
						ne.setSiharai_kbn(getValue(item, siharai_kbn_index));

						if ("".equals(getValue(item, hasou_denpyo_no_index))
								|| getValue(item, hasou_denpyo_no_index) == null) {
							ne.setHasou_denpyo_no("");
						} else {
							ne.setHasou_denpyo_no(getValue(item, hasou_denpyo_no_index));
						}

						ne.setBikou(getValue(item, bikou_index));
						ne.setJyuyou(getValue(item, jyuyou_index));
						ne.setJyuyou_check(getValue(item, jyuyou_check_index));
						ne.setJyuchu_tag(getValue(item, jyuchu_tag_index));

						ne_add.add(ne);
					}
					num++;
				}
			}

			if (ne_add.size() > 0) {
				neService.insert(ne_add);

				String date = sf6.format(now);
				String place_str = path + "\\wanfang_file_NE\\" + date;

				String originalFileName = file.getOriginalFilename();
				File place = new File(place_str);
				if (!place.exists()) {// 如果文件夹不存在
					place.mkdir();// 创建文件夹
				}

				String newfilename = sf7.format(now) + "_" + originalFileName;
				File file1 = new File(place, newfilename);
				file.transferTo(file1);

				file upfile = new file();
				upfile.setChangeName(originalFileName);
				upfile.setMemo("NEデータ");
				upfile.setName(newfilename);
				upfile.setPath(place + "\\" + newfilename);
				upfile.setType("ne");
				upfile.setUpdatetime(now);
				upfile.setUser_id(loginuser_id);
				fileService.insert(upfile);

				result.setState(1);
				result.setMsg(ne_add.size() + "件を登録しました。");
				object.put("rows", result);
			}
			return object;

//			String line = null;
//			while ((line = reader.readLine()) != null) {
//				String item[] = line.split(",\\s*(?![^\"]*\"\\,)");// CSV格式文件为逗号分隔符文件，这里根据逗号切分
//				if (num == 0) {
//					if (!CommonUtil.isHave(item, "伝票番号") || !CommonUtil.isHave(item, "ﾋﾟｯｷﾝｸﾞ指示内容")
//							|| !CommonUtil.isHave(item, "受注日") || !CommonUtil.isHave(item, "出荷確定日")
//							|| !CommonUtil.isHave(item, "納品書印刷指示日") || !CommonUtil.isHave(item, "納品書発行日")
//							|| !CommonUtil.isHave(item, "状態") || !CommonUtil.isHave(item, "受注番号")
//							|| !CommonUtil.isHave(item, "店舗") || !CommonUtil.isHave(item, "受注担当者")
//							|| !CommonUtil.isHave(item, "購入者名") || !CommonUtil.isHave(item, "商品計")
//							|| !CommonUtil.isHave(item, "税金") || !CommonUtil.isHave(item, "発送料")
//							|| !CommonUtil.isHave(item, "手数料") || !CommonUtil.isHave(item, "他費用")
//							|| !CommonUtil.isHave(item, "ポイント数") || !CommonUtil.isHave(item, "総合計")
//							|| !CommonUtil.isHave(item, "送り先名") || !CommonUtil.isHave(item, "送り先〒")
//							|| !CommonUtil.isHave(item, "送り先住所") || !CommonUtil.isHave(item, "発送方法")
//							|| !CommonUtil.isHave(item, "支払方法") || !CommonUtil.isHave(item, "発送伝票番号")
//							|| !CommonUtil.isHave(item, "備考") || !CommonUtil.isHave(item, "重要")
//							|| !CommonUtil.isHave(item, "重要ﾁｪｯｸ者") || !CommonUtil.isHave(item, "受注分類タグ")) {
//						result.setState(0);
//						result.setMsg("正しいcsvファイルをアップロードしてください。");
//						object.put("rows", result);
//						return object;
//					}
//					for (int i = 0; i < item.length; i++) {
//						if ("伝票番号".equals(getValue(item, i))) {
//							jyuchu_denpyo_no_index = i;
//						} else if ("ﾋﾟｯｷﾝｸﾞ指示内容".equals(getValue(item, i))) {
//							pic_siji_naiyou_index = i;
//						} else if ("受注日".equals(getValue(item, i))) {
//							jyuchu_bi_index = i;
//						} else if ("出荷確定日".equals(getValue(item, i))) {
//							syuka_kakutei_bi_index = i;
//						} else if ("納品書印刷指示日".equals(getValue(item, i))) {
//							nouhinsyo_insatusiji_bi_index = i;
//						} else if ("納品書発行日".equals(getValue(item, i))) {
//							nouhinsyo_insatuhakou_bi_index = i;
//						} else if ("状態".equals(getValue(item, i))) {
//							jyuchu_jyotai_kbn_index = i;
//						} else if ("受注番号".equals(getValue(item, i))) {
//							tenpo_denpyo_no_index = i;
//						} else if ("店舗".equals(getValue(item, i))) {
//							tenpo_code_index = i;
//						} else if ("受注担当者".equals(getValue(item, i))) {
//							jyuchu_tantou_code_index = i;
//						} else if ("購入者名".equals(getValue(item, i))) {
//							jyuchu_name_index = i;
//						} else if ("商品計".equals(getValue(item, i))) {
//							syohin_kin_index = i;
//						} else if ("税金".equals(getValue(item, i))) {
//							zei_kin_index = i;
//						} else if ("発送料".equals(getValue(item, i))) {
//							hasou_kin_index = i;
//						} else if ("手数料".equals(getValue(item, i))) {
//							tesuryo_kin_index = i;
//						} else if ("他費用".equals(getValue(item, i))) {
//							sonota_kin_index = i;
//						} else if ("ポイント数".equals(getValue(item, i))) {
//							point_index = i;
//						} else if ("総合計".equals(getValue(item, i))) {
//							goukei_kin_index = i;
//						} else if ("送り先名".equals(getValue(item, i))) {
//							hasou_name_index = i;
//						} else if ("送り先〒".equals(getValue(item, i))) {
//							hasou_yubin_bangou_index = i;
//						} else if ("送り先住所".equals(getValue(item, i))) {
//							hasou_jyusyo_index = i;
//						} else if ("発送方法".equals(getValue(item, i))) {
//							hasou_kbn_index = i;
//						} else if ("支払方法".equals(getValue(item, i))) {
//							siharai_kbn_index = i;
//						} else if ("発送伝票番号".equals(getValue(item, i))) {
//							hasou_denpyo_no_index = i;
//						} else if ("備考".equals(getValue(item, i))) {
//							bikou_index = i;
//						} else if ("重要".equals(getValue(item, i))) {
//							jyuyou_index = i;
//						} else if ("重要ﾁｪｯｸ者".equals(getValue(item, i))) {
//							jyuyou_check_index = i;
//						} else if ("受注分類タグ".equals(getValue(item, i))) {
//							jyuchu_tag_index = i;
//						}
//					}
//				} else {
//					ne ne = new ne();
//					if(num >= 62) {
//						System.out.println(123);
//					}
//					if ("".equals(getValue(item, jyuchu_denpyo_no_index))
//							|| getValue(item, jyuchu_denpyo_no_index) == null) {
//						result.setState(0);
//						result.setMsg("伝票番号を確認してください。");
//						object.put("rows", result);
//						return object;
//					}
//
//					ne.setJyuchu_denpyo_no(Integer.valueOf(getValue(item, jyuchu_denpyo_no_index)));
//					ne.setPic_siji_naiyou(getValue(item, pic_siji_naiyou_index));
//
//					if ("".equals(getValue(item, jyuchu_bi_index)) || getValue(item, jyuchu_bi_index) == null) {
//						ne.setJyuchu_bi(null);
//					} else {
//						ne.setJyuchu_bi(sf3.parse(getValue(item, jyuchu_bi_index)));
//					}
//
//					if ("".equals(getValue(item, syuka_kakutei_bi_index))
//							|| getValue(item, syuka_kakutei_bi_index) == null) {
//						ne.setSyuka_kakutei_bi(null);
//					} else {
//						ne.setSyuka_kakutei_bi(sf2.parse(getValue(item, syuka_kakutei_bi_index)));
//					}
//
//					if ("".equals(getValue(item, nouhinsyo_insatusiji_bi_index))
//							|| getValue(item, nouhinsyo_insatusiji_bi_index) == null) {
//						ne.setNouhinsyo_insatusiji_bi(null);
//					} else {
//						ne.setNouhinsyo_insatusiji_bi(sf2.parse(getValue(item, nouhinsyo_insatusiji_bi_index)));
//					}
//
//					if ("".equals(getValue(item, nouhinsyo_insatuhakou_bi_index))
//							|| getValue(item, nouhinsyo_insatuhakou_bi_index) == null) {
//						ne.setNouhinsyo_insatuhakou_bi(null);
//					} else {
//						ne.setNouhinsyo_insatuhakou_bi(sf3.parse(getValue(item, nouhinsyo_insatuhakou_bi_index)));
//					}
//
//					ne.setJyuchu_jyotai_kbn(getValue(item, jyuchu_jyotai_kbn_index));
//					ne.setTenpo_denpyo_no(getValue(item, tenpo_denpyo_no_index));
//					ne.setTenpo_code(getValue(item, tenpo_code_index));
//					ne.setJyuchu_tantou_code(getValue(item, jyuchu_tantou_code_index));
//					ne.setJyuchu_name(getValue(item, jyuchu_name_index));
//
//					if ("".equals(getValue(item, syohin_kin_index)) || getValue(item, syohin_kin_index) == null) {
//						ne.setSyohin_kin(0);
//					} else {
//						ne.setSyohin_kin(Integer.valueOf(getValue(item, syohin_kin_index).replace(",", "")));
//					}
//
//					if ("".equals(getValue(item, zei_kin_index)) || getValue(item, zei_kin_index) == null) {
//						ne.setZei_kin(0);
//					} else {
//						ne.setZei_kin(Integer.valueOf(getValue(item, zei_kin_index).replace(",", "")));
//					}
//
//					if ("".equals(getValue(item, hasou_kin_index)) || getValue(item, hasou_kin_index) == null) {
//						ne.setHasou_kin(0);
//					} else {
//						ne.setHasou_kin(Integer.valueOf(getValue(item, hasou_kin_index).replace(",", "")));
//					}
//
//					if ("".equals(getValue(item, tesuryo_kin_index)) || getValue(item, tesuryo_kin_index) == null) {
//						ne.setTesuryo_kin(0);
//					} else {
//						ne.setTesuryo_kin(Integer.valueOf(getValue(item, tesuryo_kin_index).replace(",", "")));
//					}
//
//					if ("".equals(getValue(item, sonota_kin_index)) || getValue(item, sonota_kin_index) == null) {
//						ne.setSonota_kin(0);
//					} else {
//						ne.setSonota_kin(Integer.valueOf(getValue(item, sonota_kin_index).replace(",", "")));
//					}
//
//					if ("".equals(getValue(item, point_index)) || getValue(item, point_index) == null) {
//						ne.setPoint(0);
//					} else {
//						ne.setPoint(Integer.valueOf(getValue(item, point_index).replace(",", "")));
//					}
//
//					if ("".equals(getValue(item, goukei_kin_index)) || getValue(item, goukei_kin_index) == null) {
//						ne.setGoukei_kin(0);
//					} else {
//						ne.setGoukei_kin(Integer.valueOf(getValue(item, goukei_kin_index).replace(",", "")));
//					}
//
//					ne.setHasou_name(getValue(item, hasou_name_index));
//					ne.setHasou_yubin_bangou(getValue(item, hasou_yubin_bangou_index));
//					ne.setHasou_jyusyo(getValue(item, hasou_jyusyo_index));
//					ne.setHasou_kbn(getValue(item, hasou_kbn_index));
//					ne.setSiharai_kbn(getValue(item, siharai_kbn_index));
//
//					if ("".equals(getValue(item, hasou_denpyo_no_index))
//							|| getValue(item, hasou_denpyo_no_index) == null) {
//						ne.setHasou_denpyo_no("");
//					} else {
//						ne.setHasou_denpyo_no(getValue(item, hasou_denpyo_no_index));
//					}
//
//					ne.setBikou(getValue(item, bikou_index));
//					ne.setJyuyou(getValue(item, jyuyou_index));
//					ne.setJyuyou_check(getValue(item, jyuyou_check_index));
//					ne.setJyuchu_tag(getValue(item, jyuchu_tag_index));
//
//					ne_add.add(ne);
//				}
//				num++;
//			}
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
	@RequestMapping(value = "/uploadCsvACtion_tuiseki", method = RequestMethod.POST)
	private JSONObject uploadCsvACtion_tuiseki(@RequestParam(value = "file") MultipartFile file,
			HttpServletResponse response, HttpServletRequest request, int loginuser_id) {
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
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "Shift-JIS"));
//			Reader rd = new BufferedReader(new InputStreamReader(is, "Shift-JIS"));

			int num = 0;
			List<ne> ne_up = new ArrayList<ne>();
			Integer haisoukaisya_index = null;
			Integer denpyono_index = null;
			Integer kekka_index = null;
			Integer sumi_index = null;
			Integer todokesaki_index = null;
			Integer tsuisekibango_index = null;
			Integer syukabi_index = null;
			Integer haitatsukanryo_index = null;
			Integer kosuu_index = null;
			Integer syosai_index = null;
			Integer syokaihannnou_index = null;

			String line = null;
			while ((line = reader.readLine()) != null) {
				String item[] = line.split(",\\s*(?![^\"]*\"\\,)");// CSV格式文件为逗号分隔符文件，这里根据逗号切分
				if (num == 0) {
					if (!CommonUtil.isHave(item, "配送会社") || !CommonUtil.isHave(item, "伝票番号")
							|| !CommonUtil.isHave(item, "結果") || !CommonUtil.isHave(item, "済")
							|| !CommonUtil.isHave(item, "届け先") || !CommonUtil.isHave(item, "追跡番号")
							|| !CommonUtil.isHave(item, "出荷日") || !CommonUtil.isHave(item, "配達完了日")
							|| !CommonUtil.isHave(item, "個数") || !CommonUtil.isHave(item, "詳細")
							|| !CommonUtil.isHave(item, "初回反応")) {
						result.setState(0);
						result.setMsg("正しいcsvファイルをアップロードしてください。");
						object.put("rows", result);
						return object;
					}
					for (int i = 0; i < item.length; i++) {
						if ("配送会社".equals(getValue(item, i))) {
							haisoukaisya_index = i;
						} else if ("伝票番号".equals(getValue(item, i))) {
							denpyono_index = i;
						} else if ("結果".equals(getValue(item, i))) {
							kekka_index = i;
						} else if ("済".equals(getValue(item, i))) {
							sumi_index = i;
						} else if ("届け先".equals(getValue(item, i))) {
							todokesaki_index = i;
						} else if ("追跡番号".equals(getValue(item, i))) {
							tsuisekibango_index = i;
						} else if ("出荷日".equals(getValue(item, i))) {
							syukabi_index = i;
						} else if ("配達完了日".equals(getValue(item, i))) {
							haitatsukanryo_index = i;
						} else if ("個数".equals(getValue(item, i))) {
							kosuu_index = i;
						} else if ("詳細".equals(getValue(item, i))) {
							syosai_index = i;
						} else if ("初回反応".equals(getValue(item, i))) {
							syokaihannnou_index = i;
						}
					}
				} else {
					String sumi_csv = getValue(item, sumi_index);
					String haisoukaisya_csv = getValue(item, haisoukaisya_index);
					String denpyono_csv = getValue(item, denpyono_index);
					String kekka_csv = getValue(item, kekka_index);
					String todokesaki_csv = getValue(item, todokesaki_index);
					String tsuisekibango_csv = getValue(item, tsuisekibango_index);
					String syukabi_csv = getValue(item, syukabi_index);
					String haitatsukanryo_csv = getValue(item, haitatsukanryo_index);
					String kosuu_csv = getValue(item, kosuu_index);
					String syosai_csv = getValue(item, syosai_index);
					String syokaihannnou_csv = getValue(item, syokaihannnou_index);

					if (!"".equals(haisoukaisya_csv) && haisoukaisya_csv != null && !"".equals(denpyono_csv)
							&& denpyono_csv != null && !"".equals(kekka_csv) && kekka_csv != null) {

						if (CommonUtil.isInteger(denpyono_csv) && CommonUtil.isInteger(tsuisekibango_csv)) {
							ne ne = new ne();
							ne.setTsuisekiImportUserId(loginuser_id);
							ne.setJyuchu_denpyo_no(Integer.parseInt(denpyono_csv));
							ne.setTsuisekiNo(tsuisekibango_csv);
							ne.setHaisoukaisya(haisoukaisya_csv);
							ne.setSumi(sumi_csv);
							ne.setTodokesaki(todokesaki_csv);
							ne.setKekka(kekka_csv);

							if (!"".equals(syukabi_csv) && syukabi_csv != null) {
//								if (syukabi_csv.length() == 9) {
//									Date date = new Date();
//									String year = syukabi_csv.substring(0, 4);
//									String month = syukabi_csv.substring(5, 7);
//									String day = syukabi_csv.substring(8, 9);
//
//									date.setYear(Integer.parseInt(year) - 1900);
//									date.setMonth(Integer.parseInt(month));
//									date.setDate(Integer.parseInt(day));
//									date.setHours(0);
//									date.setMinutes(0);
//									date.setSeconds(0);
//									ne.setSyukabi(date);
//								} else if (syukabi_csv.length() > 9 && syukabi_csv.length() < 12) {
//									Date date = new Date();
//									String year = syukabi_csv.substring(0, 4);
//									String month = syukabi_csv.substring(5, 7);
//									String day = syukabi_csv.substring(8, 10);
//									day = day.replace("日", "");
//
//									date.setYear(Integer.parseInt(year) - 1900);
//									date.setMonth(Integer.parseInt(month));
//									date.setDate(Integer.parseInt(day));
//									date.setHours(0);
//									date.setMinutes(0);
//									date.setSeconds(0);
//									ne.setSyukabi(date);
//								}
								if ("佐川急便(宅配便)".equals(haisoukaisya_csv)) {
									String year = CommonUtil.subString(syukabi_csv, "", "年");
									String month = CommonUtil.subString(syukabi_csv, "年", "月");
									String day = CommonUtil.subString(syukabi_csv, "月", "日");
									if (!"".equals(year) && !"".equals(month) && !"".equals(day)) {
										Date date = new Date();
										date.setYear(Integer.parseInt(year) - 1900);
										date.setMonth(Integer.parseInt(month));
										date.setDate(Integer.parseInt(day));
										date.setHours(0);
										date.setMinutes(0);
										date.setSeconds(0);
										ne.setSyukabi(date);
									}
								} else if ("ゆうパケット".equals(haisoukaisya_csv)) {
									String[] date_str = syukabi_csv.split("/");
									if (date_str.length == 3) {
										String year = date_str[0];
										String month = date_str[1];
										String day = date_str[2];

										if (!"".equals(year) && !"".equals(month) && !"".equals(day)) {
											Date date = new Date();
											date.setYear(Integer.parseInt(year) - 1900);
											date.setMonth(Integer.parseInt(month));
											date.setDate(Integer.parseInt(day));
											date.setHours(0);
											date.setMinutes(0);
											date.setSeconds(0);
											ne.setSyukabi(date);
										}
									}
								}

							}

							if (ne.getSyukabi() != null) {
								if (!"".equals(haitatsukanryo_csv) && haitatsukanryo_csv != null) {
									if ("佐川急便(宅配便)".equals(haisoukaisya_csv)) {
										String month = CommonUtil.subString(haitatsukanryo_csv, "", "月");
										String day = CommonUtil.subString(haitatsukanryo_csv, "月", "日");
										String hour = CommonUtil.subString(haitatsukanryo_csv, "日", "時");
										String minute = CommonUtil.subString(haitatsukanryo_csv, "時", "分");
										if (!"".equals(month) && !"".equals(day)) {
											Date date = new Date();
											date.setYear(ne.getSyukabi().getYear());
											date.setMonth(Integer.parseInt(month));
											date.setDate(Integer.parseInt(day));
											date.setHours(0);
											date.setMinutes(0);
											date.setSeconds(0);

											if (date.getTime() < ne.getSyukabi().getTime()) {
												date.setYear(ne.getSyukabi().getYear() + 1);
											}
											if (!"".equals(hour) && !"".equals(minute)) {
												date.setHours(Integer.parseInt(hour));
												date.setMinutes(Integer.parseInt(minute));
											}
											ne.setHaitatsukanryo(date);
										}
									} else if ("ゆうパケット".equals(haisoukaisya_csv)) {
										String[] date_str = haitatsukanryo_csv.split("/");
										if (date_str.length == 3) {
											String year = date_str[0];
											String month = date_str[1];

											String[] datetime = date_str[2].split(" ");

											if (datetime.length == 2) {
												String day = datetime[0];
												String[] datetime2 = datetime[1].split(":");

												if (datetime2.length == 2) {
													String hour = datetime2[0];
													String minute = datetime2[1];
													if (!"".equals(year) && !"".equals(month) && !"".equals(day)) {
														Date date = new Date();
														date.setYear(Integer.parseInt(year) - 1900);
														date.setMonth(Integer.parseInt(month));
														date.setDate(Integer.parseInt(day));
														date.setHours(0);
														date.setMinutes(0);
														date.setSeconds(0);

														if (date.getTime() < ne.getSyukabi().getTime()) {
															date.setYear(ne.getSyukabi().getYear() + 1);
														}
														date.setHours(Integer.parseInt(hour));
														date.setMinutes(Integer.parseInt(minute));
														ne.setHaitatsukanryo(date);
													}
												}
											}

										}
									}
								}
							}

							ne.setKosuu(kosuu_csv);
							ne.setSyosai(syosai_csv);
							ne.setTsuisekiImportDate(now);
							if (!"".equals(syokaihannnou_csv) && syokaihannnou_csv != null) {
								String[] date_str = syokaihannnou_csv.split("/");
								if (date_str.length == 3) {
									String year = date_str[0];
									String month = date_str[1];

									String[] datetime = date_str[2].split(" ");

									if (datetime.length == 2) {
										String day = datetime[0];
										String[] datetime2 = datetime[1].split(":");

										if (datetime2.length == 2) {
											String hour = datetime2[0];
											String minute = datetime2[1];
											if (!"".equals(year) && !"".equals(month) && !"".equals(day)
													&& !"".equals(hour) && !"".equals(minute)) {
												Date date = new Date();
												date.setYear(Integer.parseInt(year) - 1900);
												date.setMonth(Integer.parseInt(month));
												date.setDate(Integer.parseInt(day));
												date.setHours(Integer.parseInt(hour));
												date.setMinutes(Integer.parseInt(minute));
												ne.setSyokaihannnou(date);
											}
										}
									}

								}

							}
							ne_up.add(ne);
						}
					}
				}
				num++;
			}

			if (ne_up.size() > 0) {
				neService.UpdateTsuisekiData(ne_up);

				String date = sf6.format(now);
				String place_str = path + "\\wanfang_file_NE\\" + date;

				String originalFileName = file.getOriginalFilename();
				File place = new File(place_str);
				if (!place.exists()) {// 如果文件夹不存在
					place.mkdir();// 创建文件夹
				}

				String newfilename = sf7.format(now) + "_" + originalFileName;
				File file1 = new File(place, newfilename);
				file.transferTo(file1);

				file upfile = new file();
				upfile.setChangeName(originalFileName);
				upfile.setMemo("荷物追跡データ");
				upfile.setName(newfilename);
				upfile.setPath(place + "\\" + newfilename);
				upfile.setType("ne");
				upfile.setUpdatetime(now);
				upfile.setUser_id(loginuser_id);
				fileService.insert(upfile);

				result.setState(1);
				result.setMsg("登録しました。");
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
	@RequestMapping(value = "/getHomeDataByNe", method = RequestMethod.POST)
	private JSONObject getHomeDataByNe(HttpServletResponse response, HttpServletRequest request, String tenpo,
			String time) {
		DynamicDataSourceHolder.setDataSource("defultdataSource");
		JSONObject object = new JSONObject();
		try {
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Cache-Control", "no-cache");
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");

			Date date = sf4.parse(time);
			// https://www.oschina.net/code/snippet_2765_2063
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.set(Calendar.DAY_OF_MONTH, 1);
			// System.out.println(c.get(Calendar.DAY_OF_MONTH));
//	        System.out.println(c.getTime()); //输出本月一号的date
			long firstDayTime = c.getTime().getTime();

			Calendar c2 = Calendar.getInstance();
			c2.setTime(date);
			c2.set(Calendar.DAY_OF_MONTH, c2.getActualMaximum(Calendar.DAY_OF_MONTH));
			long lastDayTime = c2.getTime().getTime();

			c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) // 设置为本月日历中的第一天
					- (c.get(Calendar.DAY_OF_WEEK) - 1));
//			System.out.println(c.getTime());//输出本月日历显示的第一天的date
//			System.out.println(c.getTime().getMonth());
			Date beginDate = c.getTime();
//			String beginDay = sf5.format(beginDate);

			c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) + 6 * 7 - 1);// 获取日历上显示的最后一天
//	        System.out.println(c.getTime());//输出日历上显示的最后一天的date
			Date endDate = c.getTime();
//			String endDay = sf5.format(endDate);

//	        System.out.println(beginDay);
//	        System.out.println(endDay);

			Long beginTime = beginDate.getTime();// 转换时间戳
			Long endTime = endDate.getTime();
			Long oneDay = 1000 * 60 * 60 * 24l;// 一天的时长

			// 42个循环
			List<option> dataList = new ArrayList<option>();
//			int homedata_count = 0;
			int homedata_goukei = 0;
			while (beginTime <= endTime) {
//				System.out.println(sf5.format(beginTime));
				String time_s = sf5.format(beginTime);
				List<ne> ne = neService.getHomeDataByNe_everday(tenpo, time_s + " 00:00:00", time_s + " 23:59:59");

				option op = new option();
				if (ne.size() == 0) {
					op.setLabel("0");
//					vals.add("無し");
					op.setVaList(null);
				} else {
					op.setLabel(String.valueOf(ne.size()));
					List<String> vals = new ArrayList<String>();
					for (int i = 0; i < ne.size(); i++) {
						vals.add(ne.get(i).getJyuchu_name() + ": " + ne.get(i).getGoukei_kin());
						if (beginTime >= firstDayTime && beginTime <= lastDayTime) {
							homedata_goukei += ne.get(i).getGoukei_kin();
						}
					}
					op.setVaList(vals);
//					if(beginTime >= firstDayTime && beginTime <= lastDayTime) {
//						homedata_count += ne.size();
//					}
				}
				dataList.add(op);
				beginTime += oneDay;
			}
//			System.out.println(dataList);
//			List<option> homedata = commonService.getHomeDataByNe(tenpo,beginDay,endDay);

			object.put("dataList", dataList);
//			object.put("homedata_count", homedata_count);
			object.put("homedata_goukei", homedata_goukei);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return object;
	}

	@ResponseBody
	@RequestMapping(value = "/getMeisaiDataByNe", method = RequestMethod.POST)
	private JSONObject getMeisaiDataByNe(HttpServletResponse response, HttpServletRequest request, String tenpo,
			String time_s, String time_e, int currentPage, int pageCount, String jyuchu_name, String denpyono,
			String haitatsukanryo_s, String haitatsukanryo_e, String haitatsukanryo_chk) {
		DynamicDataSourceHolder.setDataSource("defultdataSource");
		JSONObject object = new JSONObject();
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Cache-Control", "no-cache");

			int current = (currentPage - 1) * pageCount;

			String time1 = "";
			if (!"".equals(time_s) && time_s != null && !"null".equals(time_s)) {
				time1 = time_s + " 00:00:00";
			}
			String time2 = "";
			if (!"".equals(time_e) && time_e != null && !"null".equals(time_e)) {
				time2 = time_e + " 23:59:59";
			}

			List<ne> dataList = neService.getMeisaiDataByNe(tenpo, time1, time2, jyuchu_name, denpyono,
					haitatsukanryo_chk, haitatsukanryo_s, haitatsukanryo_e, current, pageCount);

//			for (int i = 0; i < dataList.size(); i++) {
//				ne ne = dataList.get(i);
//				StringBuilder tsuisekiData_sb = new StringBuilder();
//				String syokaihannnou = "";
//				if(ne.getSyokaihannnou() != null) {
//					syokaihannnou = sf.format(ne.getSyokaihannnou());
//				}
//				String syukabi = "";
//				if(ne.getSyukabi() != null) {
//					syukabi =sf2.format(ne.getSyukabi());
//				}
//				if(!"".equals(ne.getKekka()) && ne.getKekka() != null) {
//					tsuisekiData_sb.append("荷物追跡: 初回反応日 -> ").append(syokaihannnou)
//					.append(" 状態-> ").append(ne.getSumi())
//					.append(" 結果 -> ").append(ne.getKekka())
//					.append(" 配送会社 -> ").append(ne.getHaisoukaisya())
//					.append(" 届け先 -> ").append(ne.getTodokesaki())
//					.append(" 出荷日 -> ").append(syukabi)
//					.append(" 配達完了日 -> ").append(ne.getHaitatsukanryo())
//					.append(" 個数 -> ").append(ne.getKosuu())
//					.append(" 導入日-> ").append(ne.getTsuisekiImportDate())
//					.append(" 導入者-> ").append(ne.getTsuisekiImportUser());
//				}
//				dataList.get(i).setTsuisekiData(tsuisekiData_sb.toString());
//			}

			int dataCount = neService.getMeisaiDataByNe_count(tenpo, time1, time2, jyuchu_name, denpyono,
					haitatsukanryo_chk, haitatsukanryo_s, haitatsukanryo_e);
			int datagoukei = 0;
			if (dataCount > 0) {
				datagoukei = neService.getMeisaiDataByNe_goukei(tenpo, time1, time2, jyuchu_name, denpyono,
						haitatsukanryo_chk, haitatsukanryo_s, haitatsukanryo_e);
			}
//			int datagoukei2 = neService.getMeisaiDataByNe_goukei2(tenpo, time1, time2, jyuchu_name, current, pageCount);
			int datagoukei2 = 0;

			if (dataList.size() > 0) {
				for (int i = 0; i < dataList.size(); i++) {
					datagoukei2 += dataList.get(i).getGoukei_kin();
				}
			}

			List<option> jyuchu_name_op = commonService.getJyuchuName_optionsByNe(tenpo);

			object.put("dataList", dataList);
			object.put("dataCount", dataCount);
			object.put("datagoukei", datagoukei);
			object.put("datagoukei2", datagoukei2);
			object.put("jyuchu_name_op", jyuchu_name_op);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		return object;
	}

	@ResponseBody
	@RequestMapping(value = "/deleteMeisai", method = RequestMethod.POST)
	private JSONObject deleteMeisai(HttpServletResponse response, HttpServletRequest request, String data) {
		DynamicDataSourceHolder.setDataSource("defultdataSource");
		JSONObject object = new JSONObject();
		result result = new result();
		try {
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Cache-Control", "no-cache");
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");

			String ids = "";
			if (data != "" && !"".equals(data)) {
				JSONArray json = (JSONArray) JSONArray.parse(data);
				if (json.size() > 0) {
					for (Object obj : json) {
						JSONObject jo = (JSONObject) obj;
						boolean chk = jo.getBoolean("chk");
						if (chk) {
							int id = jo.getInteger("jyuchu_denpyo_no");
							ids = ids + id + ",";
						}
					}
				}
				if (!"".equals(ids)) {
					String[] ids_arr = ids.split(",");
					neService.deleteMeisaiByIds(ids_arr);
					result.setState(0);
					result.setMsg(ids_arr.length + "件を削除しました。");
				}
			} else {
				result.setState(1);
			}

			object.put("rows", result);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return object;
	}

	@ResponseBody
	@RequestMapping(value = "/getUploadRirekiByNe", method = RequestMethod.POST)
	private JSONObject getUploadRirekiByNe(HttpServletResponse response, HttpServletRequest request) {
		DynamicDataSourceHolder.setDataSource("defultdataSource");
		JSONObject object = new JSONObject();
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Cache-Control", "no-cache");

			List<file> datalist = fileService.getUploadRirekiByNe();

			object.put("datalist", datalist);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		return object;
	}

	// CSVデータを1行読み込みます
	private List<String> readCsv(Reader r) throws IOException {
		int c = r.read();
		if (c == -1) {
			return null;
		}
		List<String> ret = new ArrayList<String>();
		StringBuffer sb = new StringBuffer();
		boolean q = false;
		boolean qe = false;
		boolean cr = false;
		while (c != -1) {
			if (c == '"') { // ダブルクオート["]の処理
				if (!q) {
					q = true;
				} else if (!qe) {
					qe = true;
				} else {
					// 値にダブルクオートを追加
					sb.append("\"");
					qe = false;
				}
			} else if (c == '\r') { // CRならばフラグを立てる
				cr = true;
			} else {
				if (qe) {
					q = false;
					qe = false;
				}
				if (!q && cr && c == '\n') { // CRLFならば行の区切り
					break;
				}
				cr = false;
				if (!q && c == ',') { // カンマ[,]ならば値の区切り
					ret.add(sb.toString());
					sb = new StringBuffer();
				} else if (c == '\n') { // LFならば値に改行[CRLF]を追加
					if (q) {
						sb.append("\r\n");
					}
				} else {
					// それ以外の文字を値に追加
					sb.append((char) c);
				}
			}
			c = r.read();
		}
		ret.add(sb.toString());
		return ret;
	}

	// 値を適切な型に変換して返します
	private Object parseValue(String v) {
		try {
			if (v != null) {
				if (datePattern.matcher(v).find()) {
					SimpleDateFormat df = new SimpleDateFormat("yyyy/M/d");
					return df.parse(v);
				} else if (numPattern.matcher(v).find()) {
					return new BigDecimal(v);
				}
			}
		} catch (Exception e) {
		}
		return v;
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
