package com.mp.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
import com.mp.entity.ne_goods;
import com.mp.entity.ne_hikaku;
import com.mp.entity.ne_meisai;
import com.mp.service.commonService;
import com.mp.service.fileService;
import com.mp.service.neService;
import com.mp.util.CommonUtil;
import com.mp.util.CsvUtil;
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
	SimpleDateFormat sf8 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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
				result.setMsg("csv??????????????????????????????????????????????????????");
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

			// ????????????????????????????????????
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

						if (!CommonUtil.isHave(item, "????????????") || !CommonUtil.isHave(item, "?????????????????????")
								|| !CommonUtil.isHave(item, "?????????") || !CommonUtil.isHave(item, "???????????????")
								|| !CommonUtil.isHave(item, "????????????????????????") || !CommonUtil.isHave(item, "??????????????????")
								|| !CommonUtil.isHave(item, "????????????") || !CommonUtil.isHave(item, "????????????")
								|| !CommonUtil.isHave(item, "???????????????") || !CommonUtil.isHave(item, "??????????????????")
								|| !CommonUtil.isHave(item, "????????????") || !CommonUtil.isHave(item, "?????????")
								|| !CommonUtil.isHave(item, "??????") || !CommonUtil.isHave(item, "?????????")
								|| !CommonUtil.isHave(item, "?????????") || !CommonUtil.isHave(item, "?????????")
								|| !CommonUtil.isHave(item, "???????????????") || !CommonUtil.isHave(item, "?????????")
								|| !CommonUtil.isHave(item, "????????????") || !CommonUtil.isHave(item, "?????????????????????")
								|| !CommonUtil.isHave(item, "???????????????1") || !CommonUtil.isHave(item, "????????????")
								|| !CommonUtil.isHave(item, "????????????") || !CommonUtil.isHave(item, "??????????????????")
								|| !CommonUtil.isHave(item, "??????") || !CommonUtil.isHave(item, "???????????????????????????")
								|| !CommonUtil.isHave(item, "??????????????????????????????") || !CommonUtil.isHave(item, "??????????????????")) {
							result.setState(0);
							result.setMsg("?????????csv??????????????????????????????????????????????????????");
							object.put("rows", result);
							return object;
						}
						for (int i = 0; i < item.length; i++) {
							if ("????????????".equals(CsvUtil.getValue(item, i))) {
								jyuchu_denpyo_no_index = i;
							} else if ("?????????????????????".equals(CsvUtil.getValue(item, i))) {
								pic_siji_naiyou_index = i;
							} else if ("?????????".equals(CsvUtil.getValue(item, i))) {
								jyuchu_bi_index = i;
							} else if ("???????????????".equals(CsvUtil.getValue(item, i))) {
								syuka_kakutei_bi_index = i;
							} else if ("????????????????????????".equals(CsvUtil.getValue(item, i))) {
								nouhinsyo_insatusiji_bi_index = i;
							} else if ("??????????????????".equals(CsvUtil.getValue(item, i))) {
								nouhinsyo_insatuhakou_bi_index = i;
							} else if ("????????????".equals(CsvUtil.getValue(item, i))) {
								jyuchu_jyotai_kbn_index = i;
							} else if ("????????????".equals(CsvUtil.getValue(item, i))) {
								tenpo_denpyo_no_index = i;
							} else if ("???????????????".equals(CsvUtil.getValue(item, i))) {
								tenpo_code_index = i;
							} else if ("??????????????????".equals(CsvUtil.getValue(item, i))) {
								jyuchu_tantou_code_index = i;
							} else if ("????????????".equals(CsvUtil.getValue(item, i))) {
								jyuchu_name_index = i;
							} else if ("?????????".equals(CsvUtil.getValue(item, i))) {
								syohin_kin_index = i;
							} else if ("??????".equals(CsvUtil.getValue(item, i))) {
								zei_kin_index = i;
							} else if ("?????????".equals(CsvUtil.getValue(item, i))) {
								hasou_kin_index = i;
							} else if ("?????????".equals(CsvUtil.getValue(item, i))) {
								tesuryo_kin_index = i;
							} else if ("?????????".equals(CsvUtil.getValue(item, i))) {
								sonota_kin_index = i;
							} else if ("???????????????".equals(CsvUtil.getValue(item, i))) {
								point_index = i;
							} else if ("?????????".equals(CsvUtil.getValue(item, i))) {
								goukei_kin_index = i;
							} else if ("????????????".equals(CsvUtil.getValue(item, i))) {
								hasou_name_index = i;
							} else if ("?????????????????????".equals(CsvUtil.getValue(item, i))) {
								hasou_yubin_bangou_index = i;
							} else if ("???????????????1".equals(CsvUtil.getValue(item, i))) {
								hasou_jyusyo_index = i;
							} else if ("????????????".equals(CsvUtil.getValue(item, i))) {
								hasou_kbn_index = i;
							} else if ("????????????".equals(CsvUtil.getValue(item, i))) {
								siharai_kbn_index = i;
							} else if ("??????????????????".equals(CsvUtil.getValue(item, i))) {
								hasou_denpyo_no_index = i;
							} else if ("??????".equals(CsvUtil.getValue(item, i))) {
								bikou_index = i;
							} else if ("???????????????????????????".equals(CsvUtil.getValue(item, i))) {
								jyuyou_index = i;
							} else if ("??????????????????????????????".equals(CsvUtil.getValue(item, i))) {
								jyuyou_check_index = i;
							} else if ("??????????????????".equals(CsvUtil.getValue(item, i))) {
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

						if ("".equals(CsvUtil.getValue(item, jyuchu_denpyo_no_index))
								|| CsvUtil.getValue(item, jyuchu_denpyo_no_index) == null) {
							result.setState(0);
							result.setMsg("??????????????????????????????????????????");
							object.put("rows", result);
							return object;
						}

						ne.setJyuchu_denpyo_no(Integer.valueOf(CsvUtil.getValue(item, jyuchu_denpyo_no_index)));
						ne.setPic_siji_naiyou(CsvUtil.getValue(item, pic_siji_naiyou_index));

						if ("".equals(CsvUtil.getValue(item, jyuchu_bi_index))
								|| CsvUtil.getValue(item, jyuchu_bi_index) == null) {
							ne.setJyuchu_bi(null);
						} else {
							ne.setJyuchu_bi(sf8.parse(CsvUtil.getValue(item, jyuchu_bi_index)));
						}

						if ("".equals(CsvUtil.getValue(item, syuka_kakutei_bi_index))
								|| CsvUtil.getValue(item, syuka_kakutei_bi_index) == null) {
							ne.setSyuka_kakutei_bi(null);
						} else {
							ne.setSyuka_kakutei_bi(sf8.parse(CsvUtil.getValue(item, syuka_kakutei_bi_index)));
						}

						if ("".equals(CsvUtil.getValue(item, nouhinsyo_insatusiji_bi_index))
								|| CsvUtil.getValue(item, nouhinsyo_insatusiji_bi_index) == null
								|| "0000/00/00".equals(CsvUtil.getValue(item, nouhinsyo_insatusiji_bi_index))) {
							ne.setNouhinsyo_insatusiji_bi(null);
						} else {
							ne.setNouhinsyo_insatusiji_bi(
									sf8.parse(CsvUtil.getValue(item, nouhinsyo_insatusiji_bi_index)));
						}

						if ("".equals(CsvUtil.getValue(item, nouhinsyo_insatuhakou_bi_index))
								|| CsvUtil.getValue(item, nouhinsyo_insatuhakou_bi_index) == null) {
							ne.setNouhinsyo_insatuhakou_bi(null);
						} else {
							ne.setNouhinsyo_insatuhakou_bi(
									sf8.parse(CsvUtil.getValue(item, nouhinsyo_insatuhakou_bi_index)));
						}

						ne.setJyuchu_jyotai_kbn(CsvUtil.getValue(item, jyuchu_jyotai_kbn_index));
						ne.setTenpo_denpyo_no(CsvUtil.getValue(item, tenpo_denpyo_no_index));

						String tenpo_code = CsvUtil.getValue(item, tenpo_code_index);
						String tenpo = "";
						if (!"".equals(tenpo_code)) {
							if ("20".equals(tenpo_code)) {
								tenpo = "???????????????";
							} else if ("6".equals(tenpo_code)) {
								tenpo = "?????????";
							} else if ("3".equals(tenpo_code)) {
								tenpo = "???";
							} else if ("19".equals(tenpo_code)) {
								tenpo = "?????????Y";
							} else if ("2".equals(tenpo_code)) {
								tenpo = "FK";
							} else if ("8".equals(tenpo_code)) {
								tenpo = "Qoo10";
							} else if ("23".equals(tenpo_code)) {
								tenpo = "?????????KT";
							} else if ("22".equals(tenpo_code)) {
								tenpo = "Y??????KT";
							} else if ("12".equals(tenpo_code)) {
								tenpo = "????????????";
							} else if ("11".equals(tenpo_code)) {
								tenpo = "????????????";
							} else if ("17".equals(tenpo_code)) {
								tenpo = "???????????????";
							} else if ("1".equals(tenpo_code)) {
								tenpo = "????????????";
							} else if ("24".equals(tenpo_code)) {
								tenpo = "AZ?????????";
							} else if ("21".equals(tenpo_code)) {
								tenpo = "????????????";
							} else if ("4".equals(tenpo_code)) {
								tenpo = "?????????";
							} else if ("15".equals(tenpo_code)) {
								tenpo = "?????????";
							} else if ("13".equals(tenpo_code)) {
								tenpo = "????????????";
							} else if ("14".equals(tenpo_code)) {
								tenpo = "?????????????????????";
							} else if ("16".equals(tenpo_code)) {
								tenpo = "Amazon?????????";
							} else if ("18".equals(tenpo_code)) {
								tenpo = "??????????????????";
							} else if ("25".equals(tenpo_code)) {
								tenpo = "Selecting";
							} else if ("26".equals(tenpo_code)) {
								tenpo = "????????????";
							} else if ("27".equals(tenpo_code)) {
								tenpo = "?????????";
							}

							if (!"".equals(tenpo)) {
								ne.setTenpo_code(tenpo);
							}
						}

						ne.setJyuchu_tantou_code(CsvUtil.getValue(item, jyuchu_tantou_code_index));
						ne.setJyuchu_name(CsvUtil.getValue(item, jyuchu_name_index));

						if ("".equals(CsvUtil.getValue(item, syohin_kin_index))
								|| CsvUtil.getValue(item, syohin_kin_index) == null) {
							ne.setSyohin_kin(0);
						} else {
							ne.setSyohin_kin(
									Integer.valueOf(CsvUtil.getValue(item, syohin_kin_index).replace(".00", "")));
						}

						if ("".equals(CsvUtil.getValue(item, zei_kin_index))
								|| CsvUtil.getValue(item, zei_kin_index) == null) {
							ne.setZei_kin(0);
						} else {
							ne.setZei_kin(Integer.valueOf(CsvUtil.getValue(item, zei_kin_index).replace(".00", "")));
						}

						if ("".equals(CsvUtil.getValue(item, hasou_kin_index))
								|| CsvUtil.getValue(item, hasou_kin_index) == null) {
							ne.setHasou_kin(0);
						} else {
							ne.setHasou_kin(
									Integer.valueOf(CsvUtil.getValue(item, hasou_kin_index).replace(".00", "")));
						}

						if ("".equals(CsvUtil.getValue(item, tesuryo_kin_index))
								|| CsvUtil.getValue(item, tesuryo_kin_index) == null) {
							ne.setTesuryo_kin(0);
						} else {
							ne.setTesuryo_kin(
									Integer.valueOf(CsvUtil.getValue(item, tesuryo_kin_index).replace(".00", "")));
						}

						if ("".equals(CsvUtil.getValue(item, sonota_kin_index))
								|| CsvUtil.getValue(item, sonota_kin_index) == null) {
							ne.setSonota_kin(0);
						} else {
							ne.setSonota_kin(
									Integer.valueOf(CsvUtil.getValue(item, sonota_kin_index).replace(".00", "")));
						}

						if ("".equals(CsvUtil.getValue(item, point_index))
								|| CsvUtil.getValue(item, point_index) == null) {
							ne.setPoint(0);
						} else {
							ne.setPoint(Integer.valueOf(CsvUtil.getValue(item, point_index).replace(".00", "")));
						}

						if ("".equals(CsvUtil.getValue(item, goukei_kin_index))
								|| CsvUtil.getValue(item, goukei_kin_index) == null) {
							ne.setGoukei_kin(0);
						} else {
							ne.setGoukei_kin(
									Integer.valueOf(CsvUtil.getValue(item, goukei_kin_index).replace(".00", "")));
						}

						ne.setHasou_name(CsvUtil.getValue(item, hasou_name_index));
						ne.setHasou_yubin_bangou(CsvUtil.getValue(item, hasou_yubin_bangou_index));
						ne.setHasou_jyusyo(CsvUtil.getValue(item, hasou_jyusyo_index));
						ne.setHasou_kbn(CsvUtil.getValue(item, hasou_kbn_index));
						ne.setSiharai_kbn(CsvUtil.getValue(item, siharai_kbn_index));

						if ("".equals(CsvUtil.getValue(item, hasou_denpyo_no_index))
								|| CsvUtil.getValue(item, hasou_denpyo_no_index) == null) {
							ne.setHasou_denpyo_no("");
						} else {
							ne.setHasou_denpyo_no(CsvUtil.getValue(item, hasou_denpyo_no_index));
						}

						ne.setBikou(CsvUtil.getValue(item, bikou_index));
						ne.setJyuyou(CsvUtil.getValue(item, jyuyou_index));
						ne.setJyuyou_check(CsvUtil.getValue(item, jyuyou_check_index));
						ne.setJyuchu_tag(CsvUtil.getValue(item, jyuchu_tag_index));

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
				if (!place.exists()) {// ????????????????????????
					place.mkdir();// ???????????????
				}

				String newfilename = sf7.format(now) + "_" + originalFileName;
				File file1 = new File(place, newfilename);
				file.transferTo(file1);

				file upfile = new file();
				upfile.setChangeName(originalFileName);
				upfile.setMemo("NE?????????");
				upfile.setName(newfilename);
				upfile.setPath(place + "\\" + newfilename);
				upfile.setType("ne");
				upfile.setUpdatetime(now);
				upfile.setUser_id(loginuser_id);
				fileService.insert(upfile);

				result.setState(1);
				result.setMsg(ne_add.size() + "???????????????????????????");
				object.put("rows", result);
			} else {
				result.setState(1);
				result.setMsg("0???????????????????????????");
				object.put("rows", result);
			}
			return object;

			//			String line = null;
			//			while ((line = reader.readLine()) != null) {
			//				String item[] = line.split(",\\s*(?![^\"]*\"\\,)");// CSV???????????????????????????????????????????????????????????????
			//				if (num == 0) {
			//					if (!CommonUtil.isHave(item, "????????????") || !CommonUtil.isHave(item, "?????????????????????????????????")
			//							|| !CommonUtil.isHave(item, "?????????") || !CommonUtil.isHave(item, "???????????????")
			//							|| !CommonUtil.isHave(item, "????????????????????????") || !CommonUtil.isHave(item, "??????????????????")
			//							|| !CommonUtil.isHave(item, "??????") || !CommonUtil.isHave(item, "????????????")
			//							|| !CommonUtil.isHave(item, "??????") || !CommonUtil.isHave(item, "???????????????")
			//							|| !CommonUtil.isHave(item, "????????????") || !CommonUtil.isHave(item, "?????????")
			//							|| !CommonUtil.isHave(item, "??????") || !CommonUtil.isHave(item, "?????????")
			//							|| !CommonUtil.isHave(item, "?????????") || !CommonUtil.isHave(item, "?????????")
			//							|| !CommonUtil.isHave(item, "???????????????") || !CommonUtil.isHave(item, "?????????")
			//							|| !CommonUtil.isHave(item, "????????????") || !CommonUtil.isHave(item, "????????????")
			//							|| !CommonUtil.isHave(item, "???????????????") || !CommonUtil.isHave(item, "????????????")
			//							|| !CommonUtil.isHave(item, "????????????") || !CommonUtil.isHave(item, "??????????????????")
			//							|| !CommonUtil.isHave(item, "??????") || !CommonUtil.isHave(item, "??????")
			//							|| !CommonUtil.isHave(item, "?????????????????????") || !CommonUtil.isHave(item, "??????????????????")) {
			//						result.setState(0);
			//						result.setMsg("?????????csv??????????????????????????????????????????????????????");
			//						object.put("rows", result);
			//						return object;
			//					}
			//					for (int i = 0; i < item.length; i++) {
			//						if ("????????????".equals(CsvUtil.getValue(item, i))) {
			//							jyuchu_denpyo_no_index = i;
			//						} else if ("?????????????????????????????????".equals(CsvUtil.getValue(item, i))) {
			//							pic_siji_naiyou_index = i;
			//						} else if ("?????????".equals(CsvUtil.getValue(item, i))) {
			//							jyuchu_bi_index = i;
			//						} else if ("???????????????".equals(CsvUtil.getValue(item, i))) {
			//							syuka_kakutei_bi_index = i;
			//						} else if ("????????????????????????".equals(CsvUtil.getValue(item, i))) {
			//							nouhinsyo_insatusiji_bi_index = i;
			//						} else if ("??????????????????".equals(CsvUtil.getValue(item, i))) {
			//							nouhinsyo_insatuhakou_bi_index = i;
			//						} else if ("??????".equals(CsvUtil.getValue(item, i))) {
			//							jyuchu_jyotai_kbn_index = i;
			//						} else if ("????????????".equals(CsvUtil.getValue(item, i))) {
			//							tenpo_denpyo_no_index = i;
			//						} else if ("??????".equals(CsvUtil.getValue(item, i))) {
			//							tenpo_code_index = i;
			//						} else if ("???????????????".equals(CsvUtil.getValue(item, i))) {
			//							jyuchu_tantou_code_index = i;
			//						} else if ("????????????".equals(CsvUtil.getValue(item, i))) {
			//							jyuchu_name_index = i;
			//						} else if ("?????????".equals(CsvUtil.getValue(item, i))) {
			//							syohin_kin_index = i;
			//						} else if ("??????".equals(CsvUtil.getValue(item, i))) {
			//							zei_kin_index = i;
			//						} else if ("?????????".equals(CsvUtil.getValue(item, i))) {
			//							hasou_kin_index = i;
			//						} else if ("?????????".equals(CsvUtil.getValue(item, i))) {
			//							tesuryo_kin_index = i;
			//						} else if ("?????????".equals(CsvUtil.getValue(item, i))) {
			//							sonota_kin_index = i;
			//						} else if ("???????????????".equals(CsvUtil.getValue(item, i))) {
			//							point_index = i;
			//						} else if ("?????????".equals(CsvUtil.getValue(item, i))) {
			//							goukei_kin_index = i;
			//						} else if ("????????????".equals(CsvUtil.getValue(item, i))) {
			//							hasou_name_index = i;
			//						} else if ("????????????".equals(CsvUtil.getValue(item, i))) {
			//							hasou_yubin_bangou_index = i;
			//						} else if ("???????????????".equals(CsvUtil.getValue(item, i))) {
			//							hasou_jyusyo_index = i;
			//						} else if ("????????????".equals(CsvUtil.getValue(item, i))) {
			//							hasou_kbn_index = i;
			//						} else if ("????????????".equals(CsvUtil.getValue(item, i))) {
			//							siharai_kbn_index = i;
			//						} else if ("??????????????????".equals(CsvUtil.getValue(item, i))) {
			//							hasou_denpyo_no_index = i;
			//						} else if ("??????".equals(CsvUtil.getValue(item, i))) {
			//							bikou_index = i;
			//						} else if ("??????".equals(CsvUtil.getValue(item, i))) {
			//							jyuyou_index = i;
			//						} else if ("?????????????????????".equals(CsvUtil.getValue(item, i))) {
			//							jyuyou_check_index = i;
			//						} else if ("??????????????????".equals(CsvUtil.getValue(item, i))) {
			//							jyuchu_tag_index = i;
			//						}
			//					}
			//				} else {
			//					ne ne = new ne();
			//					if(num >= 62) {
			//						System.out.println(123);
			//					}
			//					if ("".equals(CsvUtil.getValue(item, jyuchu_denpyo_no_index))
			//							|| CsvUtil.getValue(item, jyuchu_denpyo_no_index) == null) {
			//						result.setState(0);
			//						result.setMsg("??????????????????????????????????????????");
			//						object.put("rows", result);
			//						return object;
			//					}
			//
			//					ne.setJyuchu_denpyo_no(Integer.valueOf(CsvUtil.getValue(item, jyuchu_denpyo_no_index)));
			//					ne.setPic_siji_naiyou(CsvUtil.getValue(item, pic_siji_naiyou_index));
			//
			//					if ("".equals(CsvUtil.getValue(item, jyuchu_bi_index)) || CsvUtil.getValue(item, jyuchu_bi_index) == null) {
			//						ne.setJyuchu_bi(null);
			//					} else {
			//						ne.setJyuchu_bi(sf3.parse(CsvUtil.getValue(item, jyuchu_bi_index)));
			//					}
			//
			//					if ("".equals(CsvUtil.getValue(item, syuka_kakutei_bi_index))
			//							|| CsvUtil.getValue(item, syuka_kakutei_bi_index) == null) {
			//						ne.setSyuka_kakutei_bi(null);
			//					} else {
			//						ne.setSyuka_kakutei_bi(sf2.parse(CsvUtil.getValue(item, syuka_kakutei_bi_index)));
			//					}
			//
			//					if ("".equals(CsvUtil.getValue(item, nouhinsyo_insatusiji_bi_index))
			//							|| CsvUtil.getValue(item, nouhinsyo_insatusiji_bi_index) == null) {
			//						ne.setNouhinsyo_insatusiji_bi(null);
			//					} else {
			//						ne.setNouhinsyo_insatusiji_bi(sf2.parse(CsvUtil.getValue(item, nouhinsyo_insatusiji_bi_index)));
			//					}
			//
			//					if ("".equals(CsvUtil.getValue(item, nouhinsyo_insatuhakou_bi_index))
			//							|| CsvUtil.getValue(item, nouhinsyo_insatuhakou_bi_index) == null) {
			//						ne.setNouhinsyo_insatuhakou_bi(null);
			//					} else {
			//						ne.setNouhinsyo_insatuhakou_bi(sf3.parse(CsvUtil.getValue(item, nouhinsyo_insatuhakou_bi_index)));
			//					}
			//
			//					ne.setJyuchu_jyotai_kbn(CsvUtil.getValue(item, jyuchu_jyotai_kbn_index));
			//					ne.setTenpo_denpyo_no(CsvUtil.getValue(item, tenpo_denpyo_no_index));
			//					ne.setTenpo_code(CsvUtil.getValue(item, tenpo_code_index));
			//					ne.setJyuchu_tantou_code(CsvUtil.getValue(item, jyuchu_tantou_code_index));
			//					ne.setJyuchu_name(CsvUtil.getValue(item, jyuchu_name_index));
			//
			//					if ("".equals(CsvUtil.getValue(item, syohin_kin_index)) || CsvUtil.getValue(item, syohin_kin_index) == null) {
			//						ne.setSyohin_kin(0);
			//					} else {
			//						ne.setSyohin_kin(Integer.valueOf(CsvUtil.getValue(item, syohin_kin_index).replace(",", "")));
			//					}
			//
			//					if ("".equals(CsvUtil.getValue(item, zei_kin_index)) || CsvUtil.getValue(item, zei_kin_index) == null) {
			//						ne.setZei_kin(0);
			//					} else {
			//						ne.setZei_kin(Integer.valueOf(CsvUtil.getValue(item, zei_kin_index).replace(",", "")));
			//					}
			//
			//					if ("".equals(CsvUtil.getValue(item, hasou_kin_index)) || CsvUtil.getValue(item, hasou_kin_index) == null) {
			//						ne.setHasou_kin(0);
			//					} else {
			//						ne.setHasou_kin(Integer.valueOf(CsvUtil.getValue(item, hasou_kin_index).replace(",", "")));
			//					}
			//
			//					if ("".equals(CsvUtil.getValue(item, tesuryo_kin_index)) || CsvUtil.getValue(item, tesuryo_kin_index) == null) {
			//						ne.setTesuryo_kin(0);
			//					} else {
			//						ne.setTesuryo_kin(Integer.valueOf(CsvUtil.getValue(item, tesuryo_kin_index).replace(",", "")));
			//					}
			//
			//					if ("".equals(CsvUtil.getValue(item, sonota_kin_index)) || CsvUtil.getValue(item, sonota_kin_index) == null) {
			//						ne.setSonota_kin(0);
			//					} else {
			//						ne.setSonota_kin(Integer.valueOf(CsvUtil.getValue(item, sonota_kin_index).replace(",", "")));
			//					}
			//
			//					if ("".equals(CsvUtil.getValue(item, point_index)) || CsvUtil.getValue(item, point_index) == null) {
			//						ne.setPoint(0);
			//					} else {
			//						ne.setPoint(Integer.valueOf(CsvUtil.getValue(item, point_index).replace(",", "")));
			//					}
			//
			//					if ("".equals(CsvUtil.getValue(item, goukei_kin_index)) || CsvUtil.getValue(item, goukei_kin_index) == null) {
			//						ne.setGoukei_kin(0);
			//					} else {
			//						ne.setGoukei_kin(Integer.valueOf(CsvUtil.getValue(item, goukei_kin_index).replace(",", "")));
			//					}
			//
			//					ne.setHasou_name(CsvUtil.getValue(item, hasou_name_index));
			//					ne.setHasou_yubin_bangou(CsvUtil.getValue(item, hasou_yubin_bangou_index));
			//					ne.setHasou_jyusyo(CsvUtil.getValue(item, hasou_jyusyo_index));
			//					ne.setHasou_kbn(CsvUtil.getValue(item, hasou_kbn_index));
			//					ne.setSiharai_kbn(CsvUtil.getValue(item, siharai_kbn_index));
			//
			//					if ("".equals(CsvUtil.getValue(item, hasou_denpyo_no_index))
			//							|| CsvUtil.getValue(item, hasou_denpyo_no_index) == null) {
			//						ne.setHasou_denpyo_no("");
			//					} else {
			//						ne.setHasou_denpyo_no(CsvUtil.getValue(item, hasou_denpyo_no_index));
			//					}
			//
			//					ne.setBikou(CsvUtil.getValue(item, bikou_index));
			//					ne.setJyuyou(CsvUtil.getValue(item, jyuyou_index));
			//					ne.setJyuyou_check(CsvUtil.getValue(item, jyuyou_check_index));
			//					ne.setJyuchu_tag(CsvUtil.getValue(item, jyuchu_tag_index));
			//
			//					ne_add.add(ne);
			//				}
			//				num++;
			//			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
			result.setState(0);
			result.setMsg("????????????????????????????????????????????????");
			object.put("rows", result);
			return object;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/uploadCsv_ne_meisai", method = RequestMethod.POST)
	private JSONObject uploadCsv_ne_meisai(@RequestParam(value = "file") MultipartFile file,
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
				result.setMsg("csv??????????????????????????????????????????????????????");
				object.put("rows", result);
				return object;
			}

			InputStream is = file.getInputStream();

			List<ne_meisai> ne_meisai_add = new ArrayList<ne_meisai>();
			List<Integer> denpyo_arr = new ArrayList<Integer>();

			Integer denpyono_index = null;
			Integer code_index = null;
			Integer name_index = null;
			Integer meisaiGyo_index = null;
			Integer jyucyu_count_index = null;
			Integer hikiate_count_index = null;
			Integer kingaku_index = null;
			Integer genka_index = null;

			int num = 0;
			Reader rd = new BufferedReader(new InputStreamReader(is, "SHIFT_JIS"));
			while (true) {
				List<String> row = CsvUtil.readCsv(rd);
				if (row == null) {
					break;
				} else {
					if (num == 0) {
						Object[] item_ = row.toArray();
						String[] item = new String[item_.length];
						System.arraycopy(item_, 0, item, 0, item_.length);

						if (!CommonUtil.isHave(item, "????????????") || !CommonUtil.isHave(item, "??????????????????")
								|| !CommonUtil.isHave(item, "?????????") || !CommonUtil.isHave(item, "?????????")
								|| !CommonUtil.isHave(item, "?????????") || !CommonUtil.isHave(item, "?????????")
								|| !CommonUtil.isHave(item, "??????") || !CommonUtil.isHave(item, "??????")) {
							result.setState(0);
							result.setMsg("?????????csv??????????????????????????????????????????????????????");
							object.put("rows", result);
							return object;
						}
						for (int i = 0; i < item.length; i++) {
							if ("????????????".equals(CsvUtil.getValue(item, i))) {
								denpyono_index = i;
							} else if ("??????????????????".equals(CsvUtil.getValue(item, i))) {
								code_index = i;
							} else if ("?????????".equals(CsvUtil.getValue(item, i))) {
								name_index = i;
							} else if ("?????????".equals(CsvUtil.getValue(item, i))) {
								meisaiGyo_index = i;
							} else if ("?????????".equals(CsvUtil.getValue(item, i))) {
								jyucyu_count_index = i;
							} else if ("?????????".equals(CsvUtil.getValue(item, i))) {
								hikiate_count_index = i;
							} else if ("??????".equals(CsvUtil.getValue(item, i))) {
								kingaku_index = i;
							} else if ("??????".equals(CsvUtil.getValue(item, i))) {
								genka_index = i;
							}
						}
					} else {
						Object[] item_ = row.toArray();
						String[] item = new String[item_.length];
						System.arraycopy(item_, 0, item, 0, item_.length);

						ne_meisai nm = new ne_meisai();

						int denpyono = Integer.valueOf(CsvUtil.getValue(item, denpyono_index));
						String code = CsvUtil.getValue(item, code_index);
						String name = CsvUtil.getValue(item, name_index);
						int meisaigyo = Integer.valueOf(CsvUtil.getValue(item, meisaiGyo_index));
						int jyucyu_count = Integer.valueOf(CsvUtil.getValue(item, jyucyu_count_index));
						int hikiate_count = Integer.valueOf(CsvUtil.getValue(item, hikiate_count_index));
						int kingaku = Integer.valueOf(CsvUtil.getValue(item, kingaku_index));
						int gennka = Integer.valueOf(CsvUtil.getValue(item, genka_index));

						nm.setJyuchu_denpyo_no(denpyono);
						nm.setCode(code);
						nm.setName(name);
						nm.setMeisaigyo(meisaigyo);
						nm.setJyucyu_count(jyucyu_count);
						nm.setHikiate_count(hikiate_count);
						nm.setKingaku(kingaku);
						nm.setGennka(gennka);

						denpyo_arr.add(denpyono);
						ne_meisai_add.add(nm);
					}
					num++;
				}
			}

			if (ne_meisai_add.size() > 0) {
				neService.deleteByDenpyono(denpyo_arr);
				neService.insertNeMeisai(ne_meisai_add);

				String date = sf6.format(now);
				String place_str = path + "\\wanfang_file_NE\\" + date;

				String originalFileName = file.getOriginalFilename();
				File place = new File(place_str);
				if (!place.exists()) {// ????????????????????????
					place.mkdir();// ???????????????
				}

				String newfilename = sf7.format(now) + "_" + originalFileName;
				File file1 = new File(place, newfilename);
				file.transferTo(file1);

				file upfile = new file();
				upfile.setChangeName(originalFileName);
				upfile.setMemo("NE???????????????");
				upfile.setName(newfilename);
				upfile.setPath(place + "\\" + newfilename);
				upfile.setType("ne");
				upfile.setUpdatetime(now);
				upfile.setUser_id(loginuser_id);
				fileService.insert(upfile);

				result.setState(1);
				result.setMsg(ne_meisai_add.size() + "???????????????????????????");
				object.put("rows", result);
			} else {
				result.setState(1);
				result.setMsg("0???????????????????????????");
				object.put("rows", result);
			}

			return object;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
			result.setState(0);
			result.setMsg("????????????????????????????????????????????????");
			object.put("rows", result);
			return object;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/uploadCsvACtion_good", method = RequestMethod.POST)
	private JSONObject uploadCsvACtion_good(@RequestParam(value = "file") MultipartFile file,
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
				result.setMsg("csv??????????????????????????????????????????????????????");
				object.put("rows", result);
				return object;
			}

			InputStream is = file.getInputStream();

			List<ne_goods> ne_goods_add = new ArrayList<ne_goods>();

			Integer code_index = null;
			Integer dhcode_index = null;
			Integer name_index = null;
			Integer baika_index = null;
			Integer genka_index = null;
			Integer teika_index = null;
			Integer tag_index = null;

			int num = 0;
			Reader rd = new BufferedReader(new InputStreamReader(is, "SHIFT_JIS"));
			while (true) {
				List<String> row = CsvUtil.readCsv(rd);
				if (row == null) {
					break;
				} else {
					if (num == 0) {
						Object[] item_ = row.toArray();
						String[] item = new String[item_.length];
						System.arraycopy(item_, 0, item, 0, item_.length);

						if (!CommonUtil.isHave(item, "???????????????") || !CommonUtil.isHave(item, "?????????????????????")
								|| !CommonUtil.isHave(item, "?????????") || !CommonUtil.isHave(item, "??????")
								|| !CommonUtil.isHave(item, "??????") || !CommonUtil.isHave(item, "??????")
								|| !CommonUtil.isHave(item, "??????????????????")) {
							result.setState(0);
							result.setMsg("?????????csv??????????????????????????????????????????????????????");
							object.put("rows", result);
							return object;
						}
						for (int i = 0; i < item.length; i++) {
							if ("???????????????".equals(CsvUtil.getValue(item, i))) {
								code_index = i;
							} else if ("?????????????????????".equals(CsvUtil.getValue(item, i))) {
								dhcode_index = i;
							} else if ("?????????".equals(CsvUtil.getValue(item, i))) {
								name_index = i;
							} else if ("??????".equals(CsvUtil.getValue(item, i))) {
								baika_index = i;
							} else if ("??????".equals(CsvUtil.getValue(item, i))) {
								genka_index = i;
							} else if ("??????".equals(CsvUtil.getValue(item, i))) {
								teika_index = i;
							} else if ("??????????????????".equals(CsvUtil.getValue(item, i))) {
								tag_index = i;
							}
						}
					} else {
						Object[] item_ = row.toArray();
						String[] item = new String[item_.length];
						System.arraycopy(item_, 0, item, 0, item_.length);

						ne_goods ng = new ne_goods();

						String code = CsvUtil.getValue(item, code_index);
						String dhcode = CsvUtil.getValue(item, dhcode_index);
						String name = CsvUtil.getValue(item, name_index);
						String baika = CsvUtil.getValue(item, baika_index);
						String genka = CsvUtil.getValue(item, genka_index);
						String teika = CsvUtil.getValue(item, teika_index);
						String tag = CsvUtil.getValue(item, tag_index);

						ng.setCode(code);
						ng.setDhcode(dhcode);
						ng.setName(name);
						ng.setBaika(baika);
						ng.setGenka(genka);
						ng.setTeika(teika);
						ng.setTag(tag);

						ne_goods_add.add(ng);
					}
					num++;
				}
			}

			if (ne_goods_add.size() > 0) {
				neService.insertGoods(ne_goods_add);

				String date = sf6.format(now);
				String place_str = path + "\\wanfang_file_NE\\" + date;

				String originalFileName = file.getOriginalFilename();
				File place = new File(place_str);
				if (!place.exists()) {// ????????????????????????
					place.mkdir();// ???????????????
				}

				String newfilename = sf7.format(now) + "_" + originalFileName;
				File file1 = new File(place, newfilename);
				file.transferTo(file1);

				file upfile = new file();
				upfile.setChangeName(originalFileName);
				upfile.setMemo("NE???????????????");
				upfile.setName(newfilename);
				upfile.setPath(place + "\\" + newfilename);
				upfile.setType("ne");
				upfile.setUpdatetime(now);
				upfile.setUser_id(loginuser_id);
				fileService.insert(upfile);

				result.setState(1);
				result.setMsg(ne_goods_add.size() + "???????????????????????????");
				object.put("rows", result);
			} else {
				result.setState(1);
				result.setMsg("0???????????????????????????");
				object.put("rows", result);
			}

			return object;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
			result.setState(0);
			result.setMsg("????????????????????????????????????????????????");
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
				result.setMsg("csv??????????????????????????????????????????????????????");
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
				String item[] = line.split(",\\s*(?![^\"]*\"\\,)");// CSV???????????????????????????????????????????????????????????????
				if (num == 0) {
					if (!CommonUtil.isHave(item, "????????????") || !CommonUtil.isHave(item, "????????????")
							|| !CommonUtil.isHave(item, "??????") || !CommonUtil.isHave(item, "???")
							|| !CommonUtil.isHave(item, "?????????") || !CommonUtil.isHave(item, "????????????")
							|| !CommonUtil.isHave(item, "?????????") || !CommonUtil.isHave(item, "???????????????")
							|| !CommonUtil.isHave(item, "??????") || !CommonUtil.isHave(item, "??????")
							|| !CommonUtil.isHave(item, "????????????")) {
						result.setState(0);
						result.setMsg("?????????csv??????????????????????????????????????????????????????");
						object.put("rows", result);
						return object;
					}
					for (int i = 0; i < item.length; i++) {
						if ("????????????".equals(CsvUtil.getValue(item, i))) {
							haisoukaisya_index = i;
						} else if ("????????????".equals(CsvUtil.getValue(item, i))) {
							denpyono_index = i;
						} else if ("??????".equals(CsvUtil.getValue(item, i))) {
							kekka_index = i;
						} else if ("???".equals(CsvUtil.getValue(item, i))) {
							sumi_index = i;
						} else if ("?????????".equals(CsvUtil.getValue(item, i))) {
							todokesaki_index = i;
						} else if ("????????????".equals(CsvUtil.getValue(item, i))) {
							tsuisekibango_index = i;
						} else if ("?????????".equals(CsvUtil.getValue(item, i))) {
							syukabi_index = i;
						} else if ("???????????????".equals(CsvUtil.getValue(item, i))) {
							haitatsukanryo_index = i;
						} else if ("??????".equals(CsvUtil.getValue(item, i))) {
							kosuu_index = i;
						} else if ("??????".equals(CsvUtil.getValue(item, i))) {
							syosai_index = i;
						} else if ("????????????".equals(CsvUtil.getValue(item, i))) {
							syokaihannnou_index = i;
						}
					}
				} else {
					String kekka_csv = CsvUtil.getValue(item, kekka_index).trim();

					if ("????????????".equals(kekka_csv) || "????????????".equals(kekka_csv)) {

					} else {
						String sumi_csv = CsvUtil.getValue(item, sumi_index).trim();
						String haisoukaisya_csv = CsvUtil.getValue(item, haisoukaisya_index).trim();
						String denpyono_csv = CsvUtil.getValue(item, denpyono_index).trim();

						String todokesaki_csv = CsvUtil.getValue(item, todokesaki_index).trim();
						String tsuisekibango_csv = CsvUtil.getValue(item, tsuisekibango_index).trim();
						String syukabi_csv = CsvUtil.getValue(item, syukabi_index).trim();
						String haitatsukanryo_csv = CsvUtil.getValue(item, haitatsukanryo_index).trim();
						String kosuu_csv = CsvUtil.getValue(item, kosuu_index).trim();
						String syosai_csv = CsvUtil.getValue(item, syosai_index).trim();
						String syokaihannnou_csv = CsvUtil.getValue(item, syokaihannnou_index).trim();

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
									//									day = day.replace("???", "");
									//
									//									date.setYear(Integer.parseInt(year) - 1900);
									//									date.setMonth(Integer.parseInt(month));
									//									date.setDate(Integer.parseInt(day));
									//									date.setHours(0);
									//									date.setMinutes(0);
									//									date.setSeconds(0);
									//									ne.setSyukabi(date);
									//								}
									if ("????????????(?????????)".equals(haisoukaisya_csv)) {
										String year = CommonUtil.subString(syukabi_csv, "", "???");
										String month = CommonUtil.subString(syukabi_csv, "???", "???");
										String day = CommonUtil.subString(syukabi_csv, "???", "???");
										if (!"".equals(year) && !"".equals(month) && !"".equals(day)) {
											Date date = new Date();
											date.setYear(Integer.parseInt(year) - 1900);
											date.setMonth(Integer.parseInt(month) - 1);
											date.setDate(Integer.parseInt(day));
											date.setHours(0);
											date.setMinutes(0);
											date.setSeconds(0);
											ne.setSyukabi(date);
										}
									} else if ("??????????????????".equals(haisoukaisya_csv)) {
										String[] date_str = syukabi_csv.split("/");
										if (date_str.length == 3) {
											String year = date_str[0];
											String month = date_str[1];
											//											String day = date_str[2];
											String[] datetime = date_str[2].split(" ");

											//											if (!"".equals(year) && !"".equals(month) && !"".equals(day)) {
											//												Date date = new Date();
											//												date.setYear(Integer.parseInt(year) - 1900);
											//												date.setMonth(Integer.parseInt(month) - 1);
											//												date.setDate(Integer.parseInt(day));
											//												date.setHours(0);
											//												date.setMinutes(0);
											//												date.setSeconds(0);
											//												ne.setSyukabi(date);
											//											}
											if (datetime.length == 2) {
												String day = datetime[0];
												String[] datetime2 = datetime[1].split(":");

												if (datetime2.length == 2) {
													String hour = datetime2[0];
													String minute = datetime2[1];
													if (!"".equals(year) && !"".equals(month) && !"".equals(day)) {
														Date date = new Date();
														date.setYear(Integer.parseInt(year) - 1900);
														date.setMonth(Integer.parseInt(month) - 1);
														date.setDate(Integer.parseInt(day));

														date.setHours(Integer.parseInt(hour));
														date.setMinutes(Integer.parseInt(minute));
														ne.setHaitatsukanryo(date);
													}
												}
											}

										}
									}

								}

								if (ne.getSyukabi() != null) {
									if (!"".equals(haitatsukanryo_csv) && haitatsukanryo_csv != null) {
										if ("????????????(?????????)".equals(haisoukaisya_csv)) {
											String month = CommonUtil.subString(haitatsukanryo_csv, "", "???");
											String day = CommonUtil.subString(haitatsukanryo_csv, "???", "???");
											String hour = CommonUtil.subString(haitatsukanryo_csv, "???", "???");
											String minute = CommonUtil.subString(haitatsukanryo_csv, "???", "???");
											if (!"".equals(month) && !"".equals(day)) {
												Date date = new Date();
												date.setYear(ne.getSyukabi().getYear());
												date.setMonth(Integer.parseInt(month) - 1);
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
										} else if ("??????????????????".equals(haisoukaisya_csv)) {
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
															date.setMonth(Integer.parseInt(month) - 1);
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
													date.setMonth(Integer.parseInt(month) - 1);
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
				}
				num++;
			}

			if (ne_up.size() > 0) {
				neService.UpdateTsuisekiData(ne_up);

				String date = sf6.format(now);
				String place_str = path + "\\wanfang_file_NE\\" + date;

				String originalFileName = file.getOriginalFilename();
				File place = new File(place_str);
				if (!place.exists()) {// ????????????????????????
					place.mkdir();// ???????????????
				}

				String newfilename = sf7.format(now) + "_" + originalFileName;
				File file1 = new File(place, newfilename);
				file.transferTo(file1);

				file upfile = new file();
				upfile.setChangeName(originalFileName);
				upfile.setMemo("?????????????????????");
				upfile.setName(newfilename);
				upfile.setPath(place + "\\" + newfilename);
				upfile.setType("ne");
				upfile.setUpdatetime(now);
				upfile.setUser_id(loginuser_id);
				fileService.insert(upfile);

				result.setState(1);
				result.setMsg("?????????????????????");
			}

			object.put("rows", result);
			return object;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
			result.setState(0);
			result.setMsg("????????????????????????????????????????????????");
			object.put("rows", result);
			return object;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/getHomeDataByNe", method = RequestMethod.POST)
	private JSONObject getHomeDataByNe(HttpServletResponse response, HttpServletRequest request, String tenpo,
			String time, int tenpoinfo_currentPage, int tenpoinfo_pageCount, int tenpoinfo_currentPage2,
			int tenpoinfo_pageCount2) {
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
			//	        System.out.println(c.getTime()); //?????????????????????date
			long firstDayTime = c.getTime().getTime();
			Date firstDay = date;

			Calendar c2 = Calendar.getInstance();
			c2.setTime(date);
			c2.set(Calendar.DAY_OF_MONTH, c2.getActualMaximum(Calendar.DAY_OF_MONTH));
			long lastDayTime = c2.getTime().getTime();
			Date lastDayDate = c2.getTime();

			c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) // ????????????????????????????????????
					- (c.get(Calendar.DAY_OF_WEEK) - 1));
			//			System.out.println(c.getTime());//???????????????????????????????????????date
			//			System.out.println(c.getTime().getMonth());
			Date beginDate = c.getTime();
			//			String beginDay = sf5.format(beginDate);

			c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) + 6 * 7 - 1);// ????????????????????????????????????
			//	        System.out.println(c.getTime());//???????????????????????????????????????date
			Date endDate = c.getTime();
			//			String endDay = sf5.format(endDate);

			//	        System.out.println(beginDay);
			//	        System.out.println(endDay);

			Long beginTime = beginDate.getTime();// ???????????????
			Long endTime = endDate.getTime();
			Long oneDay = 1000 * 60 * 60 * 24l;// ???????????????

			// 42?????????
			List<option> dataList = new ArrayList<option>();
			//			int homedata_count = 0;

			while (beginTime <= endTime) {
				//				System.out.println(sf5.format(beginTime));
				String time_s = sf5.format(beginTime);
				List<ne> ne = neService.getHomeDataByNe_everday(tenpo, time_s + " 00:00:00", time_s + " 23:59:59");

				option op = new option();
				if (ne.size() == 0) {
					op.setLabel("0");
					//					vals.add("??????");
					op.setVaList(null);
				} else {
					op.setLabel(String.valueOf(ne.size()));
					List<String> vals = new ArrayList<String>();
					for (int i = 0; i < ne.size(); i++) {
						vals.add(ne.get(i).getJyuchu_name() + ": " + ne.get(i).getGoukei_kin());
						//						if (beginTime >= firstDayTime && beginTime <= lastDayTime) {
						//							homedata_goukei += ne.get(i).getGoukei_kin();
						//						}
						//						if (ne.get(i).getJyuchu_bi().getTime() >= firstDayTime
						//								&& ne.get(i).getJyuchu_bi().getTime() <= lastDayTime) {
						//						if (ne.get(i).getJyuchu_bi().before(firstDay)
						//								&& ne.get(i).getJyuchu_bi().after(lastDayDate)) {
						//							homedata_goukei += ne.get(i).getGoukei_kin();
						//						}
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

			//date lastDayDate

			//??????
			//			Integer homedata_goukei = 0;
			ne homedata_keisan = new ne();

			String starttimestr = "";
			String endtimestr = "";
			List<ne> ne_tenpoinfo = new ArrayList<ne>();
			List<ne_meisai> ne_tenpoinfo2 = new ArrayList<ne_meisai>();
			int ne_tenpoinfo_total = 0;
			int ne_tenpoinfo_total2 = 0;

			if (date != null && lastDayDate != null) {
				starttimestr = sf5.format(date) + " 00:00:00";
				endtimestr = sf5.format(lastDayDate) + " 23:59:59";

				//				homedata_goukei = neService.getHomedata_goukei(tenpo, starttimestr, endtimestr);
				//				if(homedata_goukei == null) {
				//					homedata_goukei = 0;
				//				}
				homedata_keisan = neService.getHomedata_keisan(tenpo, starttimestr, endtimestr);

				int current = (tenpoinfo_currentPage - 1) * tenpoinfo_pageCount;
				ne_tenpoinfo = neService.getHomeTenpoinfoDataByNe_month(tenpo, starttimestr, endtimestr, current,
						tenpoinfo_pageCount);

				if (ne_tenpoinfo.size() > 0) {
					ne_tenpoinfo_total = neService.getHomeTenpoinfoCountDataByNe_month(tenpo, starttimestr, endtimestr);
				}

				int current2 = (tenpoinfo_currentPage2 - 1) * tenpoinfo_pageCount2;
				ne_tenpoinfo2 = neService.getHomeTenpoinfo2DataByNe_month(tenpo, starttimestr, endtimestr, current2,
						tenpoinfo_pageCount2);
				if (ne_tenpoinfo2.size() > 0) {
					ne_tenpoinfo_total2 = neService.getHomeTenpoinfo2CountDataByNe_month(tenpo, starttimestr,
							endtimestr);
				}
			}
			object.put("ne_tenpoinfo", ne_tenpoinfo);
			object.put("ne_tenpoinfo_total", ne_tenpoinfo_total);
			object.put("ne_tenpoinfo2", ne_tenpoinfo2);
			object.put("ne_tenpoinfo_total2", ne_tenpoinfo_total2);
			object.put("ne_tenpoinfo_s", starttimestr);
			object.put("ne_tenpoinfo_e", endtimestr);
			object.put("homedata_keisan", homedata_keisan);

			object.put("dataList", dataList);
			//			object.put("homedata_count", homedata_count);
			//			object.put("homedata_goukei", homedata_goukei);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		return object;
	}

	@ResponseBody
	@RequestMapping(value = "/getHomeTenpoinfoDataByNe_info", method = RequestMethod.POST)
	private JSONObject getHomeTenpoinfoDataByNe_info(HttpServletResponse response, HttpServletRequest request,
			String tenpo,
			String starttimestr, String endtimestr, int tenpoinfo_currentPage, int tenpoinfo_pageCount) {
		DynamicDataSourceHolder.setDataSource("defultdataSource");
		JSONObject object = new JSONObject();
		try {
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Cache-Control", "no-cache");
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");

			int ne_tenpoinfo_total = 0;
			starttimestr = starttimestr + " 00:00:00";
			endtimestr = endtimestr + " 23:59:59";
			int current = (tenpoinfo_currentPage - 1) * tenpoinfo_pageCount;
			List<ne> ne_tenpoinfo = neService.getHomeTenpoinfoDataByNe_month(tenpo, starttimestr, endtimestr, current,
					tenpoinfo_pageCount);

			if (ne_tenpoinfo.size() > 0) {
				ne_tenpoinfo_total = ne_tenpoinfo_total = neService.getHomeTenpoinfoCountDataByNe_month(tenpo,
						starttimestr, endtimestr);
			}

			ne homedata_keisan = neService.getHomedata_keisan(tenpo, starttimestr, endtimestr);

			object.put("ne_tenpoinfo", ne_tenpoinfo);
			object.put("ne_tenpoinfo_total", ne_tenpoinfo_total);
			object.put("homedata_keisan", homedata_keisan);
			object.put("ne_tenpoinfo_s", starttimestr);
			object.put("ne_tenpoinfo_e", endtimestr);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return object;
	}

	@ResponseBody
	@RequestMapping(value = "/getHomeTenpoinfo2DataByNe_info", method = RequestMethod.POST)
	private JSONObject getHomeTenpoinfo2DataByNe_info(HttpServletResponse response, HttpServletRequest request,
			String tenpo,
			String starttimestr, String endtimestr, int tenpoinfo_currentPage2, int tenpoinfo_pageCount2) {
		DynamicDataSourceHolder.setDataSource("defultdataSource");
		JSONObject object = new JSONObject();
		try {
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Cache-Control", "no-cache");
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");

			int ne_tenpoinfo_total2 = 0;
			starttimestr = starttimestr + " 00:00:00";
			endtimestr = endtimestr + " 23:59:59";
			List<ne_meisai> ne_tenpoinfo2 = new ArrayList<ne_meisai>();

			int current2 = (tenpoinfo_currentPage2 - 1) * tenpoinfo_pageCount2;
			ne_tenpoinfo2 = neService.getHomeTenpoinfo2DataByNe_month(tenpo, starttimestr, endtimestr, current2,
					tenpoinfo_pageCount2);
			if (ne_tenpoinfo2.size() > 0) {
				ne_tenpoinfo_total2 = neService.getHomeTenpoinfo2CountDataByNe_month(tenpo, starttimestr, endtimestr);
			}

			object.put("ne_tenpoinfo2", ne_tenpoinfo2);
			object.put("ne_tenpoinfo_total2", ne_tenpoinfo_total2);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return object;
	}

	@ResponseBody
	@RequestMapping(value = "/getMeisaiDataByNe", method = RequestMethod.POST)
	private JSONObject getMeisaiDataByNe(HttpServletResponse response, HttpServletRequest request, String tenpo,
			String time_s, String time_e, int currentPage, int pageCount, String jyuchu_name, String denpyono,
			String haitatsukanryo_s, String haitatsukanryo_e, String haitatsukanryo_chk, String haitatsukanryo_chk2,
			String kekka_s, String jyuchu_haisokaisya_s) {
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
					haitatsukanryo_chk, haitatsukanryo_chk2, haitatsukanryo_s, haitatsukanryo_e, kekka_s,
					jyuchu_haisokaisya_s, current,
					pageCount);

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
			//					tsuisekiData_sb.append("????????????: ??????????????? -> ").append(syokaihannnou)
			//					.append(" ??????-> ").append(ne.getSumi())
			//					.append(" ?????? -> ").append(ne.getKekka())
			//					.append(" ???????????? -> ").append(ne.getHaisoukaisya())
			//					.append(" ????????? -> ").append(ne.getTodokesaki())
			//					.append(" ????????? -> ").append(syukabi)
			//					.append(" ??????????????? -> ").append(ne.getHaitatsukanryo())
			//					.append(" ?????? -> ").append(ne.getKosuu())
			//					.append(" ?????????-> ").append(ne.getTsuisekiImportDate())
			//					.append(" ?????????-> ").append(ne.getTsuisekiImportUser());
			//				}
			//				dataList.get(i).setTsuisekiData(tsuisekiData_sb.toString());
			//			}

			int dataCount = neService.getMeisaiDataByNe_count(tenpo, time1, time2, jyuchu_name, denpyono,
					haitatsukanryo_chk, haitatsukanryo_chk2, haitatsukanryo_s, haitatsukanryo_e, kekka_s,
					jyuchu_haisokaisya_s);
			int datagoukei = 0;
			if (dataCount > 0) {
				datagoukei = neService.getMeisaiDataByNe_goukei(tenpo, time1, time2, jyuchu_name, denpyono,
						haitatsukanryo_chk, haitatsukanryo_chk2, haitatsukanryo_s, haitatsukanryo_e, kekka_s,
						jyuchu_haisokaisya_s);
			}
			//			int datagoukei2 = neService.getMeisaiDataByNe_goukei2(tenpo, time1, time2, jyuchu_name, current, pageCount);
			int datagoukei2 = 0;

			if (dataList.size() > 0) {
				for (int i = 0; i < dataList.size(); i++) {
					datagoukei2 += dataList.get(i).getGoukei_kin();
				}
			}

			List<option> jyuchu_name_op = commonService.getJyuchuName_optionsByNe(tenpo);
			List<option> kekka_op = commonService.getJyuchuKekka_optionsByNe(tenpo);
			option kekka_op_ = new option();
			kekka_op_.setLabel("???????????????");
			kekka_op_.setValue("???????????????");
			kekka_op.add(kekka_op_);

			object.put("dataList", dataList);
			object.put("dataCount", dataCount);
			object.put("datagoukei", datagoukei);
			object.put("datagoukei2", datagoukei2);
			object.put("jyuchu_name_op", jyuchu_name_op);
			object.put("kekka_op", kekka_op);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		return object;
	}

	@ResponseBody
	@RequestMapping(value = "/getMeisaiDataByNe_dl", method = RequestMethod.POST)
	private JSONObject getMeisaiDataByNe_dl(HttpServletResponse response, HttpServletRequest request, String tenpo,
			String time_s, String time_e, int currentPage, int pageCount, String jyuchu_name, String denpyono,
			String haitatsukanryo_s, String haitatsukanryo_e, String haitatsukanryo_chk, String haitatsukanryo_chk2,
			String kekka_s, String jyuchu_haisokaisya_s) {
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
					haitatsukanryo_chk,
					haitatsukanryo_chk2, haitatsukanryo_s, haitatsukanryo_e, kekka_s, jyuchu_haisokaisya_s, current,
					pageCount);

			object.put("dataList", dataList);
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
					result.setMsg(ids_arr.length + "???????????????????????????");
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

	@ResponseBody
	@RequestMapping(value = "/getAllShopDataByNe", method = RequestMethod.POST)
	private JSONObject getAllShopDataByNe(HttpServletResponse response, HttpServletRequest request,
			String starttimestr, String endtimestr, String codes_s) {
		DynamicDataSourceHolder.setDataSource("defultdataSource");
		JSONObject object = new JSONObject();
		try {
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Cache-Control", "no-cache");
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");

			starttimestr = starttimestr + " 00:00:00";
			endtimestr = endtimestr + " 23:59:59";

			//			List<ne_hikaku> ne_hikaku = new ArrayList<ne_hikaku>();

			JSONArray json = (JSONArray) JSONArray.parse(codes_s);
			List<String> codes = new ArrayList<String>();
			if (json.size() > 0) {
				for (Object obj : json) {
					codes.add(String.valueOf(obj));
				}
			}

			//			ne_hikaku = neService.getHomeKakuTenpoinfoDataByNe(codes, starttimestr, endtimestr);

			//			if (ne_hikaku.size() > 0) {
			//				for (int i = 0; i < ne_hikaku.size(); i++) {
			//					int sum = 0;
			//					if (ne_hikaku.get(i).getValue1() != null) {
			//						if (!"".equals(ne_hikaku.get(i).getValue1().trim())) {
			//							sum += Integer.parseInt(ne_hikaku.get(i).getValue1());
			//						}
			//					}
			//					if (ne_hikaku.get(i).getValue2() != null) {
			//						if (!"".equals(ne_hikaku.get(i).getValue2().trim())) {
			//							sum += Integer.parseInt(ne_hikaku.get(i).getValue2());
			//						}
			//					}
			//					if (ne_hikaku.get(i).getValue3() != null) {
			//						if (!"".equals(ne_hikaku.get(i).getValue3().trim())) {
			//							sum += Integer.parseInt(ne_hikaku.get(i).getValue3());
			//						}
			//					}
			//					if (ne_hikaku.get(i).getValue4() != null) {
			//						if (!"".equals(ne_hikaku.get(i).getValue4().trim())) {
			//							sum += Integer.parseInt(ne_hikaku.get(i).getValue4());
			//						}
			//					}
			//					if (ne_hikaku.get(i).getValue5() != null) {
			//						if (!"".equals(ne_hikaku.get(i).getValue5().trim())) {
			//							sum += Integer.parseInt(ne_hikaku.get(i).getValue5());
			//						}
			//					}
			//					if (ne_hikaku.get(i).getValue6() != null) {
			//						if (!"".equals(ne_hikaku.get(i).getValue6().trim())) {
			//							sum += Integer.parseInt(ne_hikaku.get(i).getValue6());
			//						}
			//					}
			//					if (ne_hikaku.get(i).getValue7() != null) {
			//						if (!"".equals(ne_hikaku.get(i).getValue7().trim())) {
			//							sum += Integer.parseInt(ne_hikaku.get(i).getValue7());
			//						}
			//					}
			//					if (ne_hikaku.get(i).getValue8() != null) {
			//						if (!"".equals(ne_hikaku.get(i).getValue8().trim())) {
			//							sum += Integer.parseInt(ne_hikaku.get(i).getValue8());
			//						}
			//					}
			//					if (ne_hikaku.get(i).getValue9() != null) {
			//						if (!"".equals(ne_hikaku.get(i).getValue9().trim())) {
			//							sum += Integer.parseInt(ne_hikaku.get(i).getValue9());
			//						}
			//					}
			//					if (ne_hikaku.get(i).getValue10() != null) {
			//						if (!"".equals(ne_hikaku.get(i).getValue10().trim())) {
			//							sum += Integer.parseInt(ne_hikaku.get(i).getValue10());
			//						}
			//					}
			//					if (ne_hikaku.get(i).getValue11() != null) {
			//						if (!"".equals(ne_hikaku.get(i).getValue11().trim())) {
			//							sum += Integer.parseInt(ne_hikaku.get(i).getValue11());
			//						}
			//					}
			//					if (ne_hikaku.get(i).getValue12() != null) {
			//						if (!"".equals(ne_hikaku.get(i).getValue12().trim())) {
			//							sum += Integer.parseInt(ne_hikaku.get(i).getValue12());
			//						}
			//					}
			//					if (ne_hikaku.get(i).getValue13() != null) {
			//						if (!"".equals(ne_hikaku.get(i).getValue13().trim())) {
			//							sum += Integer.parseInt(ne_hikaku.get(i).getValue13());
			//						}
			//					}
			//					if (ne_hikaku.get(i).getValue14() != null) {
			//						if (!"".equals(ne_hikaku.get(i).getValue14().trim())) {
			//							sum += Integer.parseInt(ne_hikaku.get(i).getValue14());
			//						}
			//					}
			//					if (ne_hikaku.get(i).getValue15() != null) {
			//						if (!"".equals(ne_hikaku.get(i).getValue15().trim())) {
			//							sum += Integer.parseInt(ne_hikaku.get(i).getValue15());
			//						}
			//					}
			//
			//					if (sum > 0) {
			//						ne_hikaku.get(i).setValue999(String.valueOf(sum));
			//					}
			//				}
			//			}
			List<ne> nm = new ArrayList<ne>();
			List<ne_hikaku> ne_hikaku = new ArrayList<ne_hikaku>();
			List<String> codes_result = neService.getHomeKakuTenpoinfoCodeDataByNe(codes, starttimestr, endtimestr);
			if (codes_result.size() > 0) {
				nm = neService.getHomeKakuTenpoinfoDataByNe2(codes, starttimestr, endtimestr);

				if (nm.size() > 0) {
					int value1 = 0;
					int value2 = 0;
					int value3 = 0;
					int value4 = 0;
					int value5 = 0;
					int value6 = 0;
					int value7 = 0;
					int value8 = 0;
					int value9 = 0;
					int value10 = 0;
					int value11 = 0;
					int value12 = 0;
					int value13 = 0;
					int value14 = 0;
					int value15 = 0;
					int value16 = 0;
					int value999 = 0;

					for (int i = 0; i < codes_result.size(); i++) {
						String code = codes_result.get(i);
						if (code != null && !"".equals(code)) {
							ne_hikaku nh = new ne_hikaku();
							value1 = 0;
							value2 = 0;
							value3 = 0;
							value4 = 0;
							value5 = 0;
							value6 = 0;
							value7 = 0;
							value8 = 0;
							value9 = 0;
							value10 = 0;
							value11 = 0;
							value12 = 0;
							value13 = 0;
							value14 = 0;
							value15 = 0;
							value16 = 0;
							value999 = 0;

							nh.setCode(code);
							for (int j = 0; j < nm.size(); j++) {
								String tenpcode = nm.get(j).getTenpo_code();
								if (nm.get(j).getCode().equals(code)) {
									if ("???????????????".equals(tenpcode)) {
										value1++;
									} else if ("?????????".equals(tenpcode)) {
										value2++;
									} else if ("???".equals(tenpcode)) {
										value3++;
									} else if ("?????????Y".equals(tenpcode)) {
										value4++;
									} else if ("FK".equals(tenpcode)) {
										value5++;
									} else if ("?????????KT".equals(tenpcode)) {
										value6++;
									} else if ("Y??????KT".equals(tenpcode)) {
										value7++;
									} else if ("????????????".equals(tenpcode)) {
										value8++;
									} else if ("????????????".equals(tenpcode)) {
										value9++;
									} else if ("???????????????".equals(tenpcode)) {
										value10++;
									} else if ("????????????".equals(tenpcode)) {
										value11++;
									} else if ("AZ?????????".equals(tenpcode)) {
										value12++;
									} else if ("????????????".equals(tenpcode)) {
										value13++;
									} else if ("?????????".equals(tenpcode)) {
										value14++;
									} else if ("?????????".equals(tenpcode)) {
										value15++;
									} else if ("Qoo10".equals(tenpcode)) {
										value16++;
									}
								}
							}
							nh.setValue1(String.valueOf(value1));
							nh.setValue2(String.valueOf(value2));
							nh.setValue3(String.valueOf(value3));
							nh.setValue4(String.valueOf(value4));
							nh.setValue5(String.valueOf(value5));
							nh.setValue6(String.valueOf(value6));
							nh.setValue7(String.valueOf(value7));
							nh.setValue8(String.valueOf(value8));
							nh.setValue9(String.valueOf(value9));
							nh.setValue10(String.valueOf(value10));
							nh.setValue11(String.valueOf(value11));
							nh.setValue12(String.valueOf(value12));
							nh.setValue13(String.valueOf(value13));
							nh.setValue14(String.valueOf(value14));
							nh.setValue15(String.valueOf(value15));
							nh.setValue16(String.valueOf(value16));

							value999 = value1 + value2 + value3+ value4+ value5+ value6+ value7+ value8+ value9+ value10+ value11+ value12+ value13+ value14+ value15;
							nh.setValue999(String.valueOf(value999));
							ne_hikaku.add(nh);
						}
					}
				}
			}

			object.put("ne_hikaku", ne_hikaku);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		return object;
	}

	@ResponseBody
	@RequestMapping(value = "/getAllShopCodeDataByNe", method = RequestMethod.POST)
	private JSONObject getAllShopCodeDataByNe(HttpServletResponse response, HttpServletRequest request,
			String starttimestr, String endtimestr) {
		DynamicDataSourceHolder.setDataSource("defultdataSource");
		JSONObject object = new JSONObject();
		try {
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Cache-Control", "no-cache");
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");

			starttimestr = starttimestr + " 00:00:00";
			endtimestr = endtimestr + " 23:59:59";

			List<option> option = commonService.getAllShopCodeDataByNe(starttimestr, endtimestr);
			object.put("allshop_codes_op", option);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		return object;
	}

}
