package com.mp.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.mp.entity.location;
import com.mp.entity.plan;
import com.mp.entity.plan_info;
import com.mp.service.planService;
import com.mp.service.userService;
import com.mp.service.commonService;
import com.mp.service.fileService;

@Controller
public class planController {
	@Autowired
	private planService planService;

	@Autowired
	private fileService fileService;

	@Autowired
	private userService userService;

	SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
	SimpleDateFormat sf2 = new SimpleDateFormat("yyyyMMdd");
	SimpleDateFormat sf3 = new SimpleDateFormat("yyyy-MM-dd");

	@ResponseBody
	@RequestMapping(value = "/uploadplanBegin", method = RequestMethod.POST)
	private JSONObject uploadplanBegin(@RequestParam(value = "file") MultipartFile file, HttpServletResponse response,
			HttpServletRequest request, String type) {
		DynamicDataSourceHolder.setDataSource("defultdataSource");
		JSONObject object = new JSONObject();

		String path = "";
		if(config.ISLOCAL) {
			path = config.TODO_FILE_PLACE_LOCAL + "wanfang_file";
		} else {
			path = config.TODO_FILE_PLACE_SERVER + "wanfang_file";
		}

		try {
//			File filePath = new File(path);
			String originalFileName = file.getOriginalFilename();
//			System.out.println("原始文件名称：" + originalFileName);
//			String name = originalFileName.substring(0, originalFileName.lastIndexOf("."));
			Date now = new Date();
			String date = sf2.format(now);
			int loginuser_id = Integer
					.parseInt(new String(request.getParameter("loginuser_id").getBytes("ISO-8859-1"), "UTF-8"));

			String place_str = path + "\\" + date;
			File place = new File(place_str);
			if (!place.exists()) {// 如果文件夹不存在
				place.mkdir();// 创建文件夹
			}

			File file1 = new File(place, originalFileName);
			String newfilename = sf.format(now) + "_" + originalFileName;
			file.transferTo(file1);
			File file2 = new File(place + "\\" + newfilename);
			file1.renameTo(file2);
//			File file_moto = new File(file.get);
//			FileUtils.copyFile(file_moto,place);

//	        String type = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
//	        StringBuffer sb = new StringBuffer();
//			sb.append(date).append("_").append(name).append(".").append(type);

//	        System.out.println("新文件名称：" + sb.toString());

			file upfile = new file();
			upfile.setChangeName(originalFileName);
			upfile.setName(newfilename);
			upfile.setPath(path + "\\" + date + "\\"+ newfilename);
			upfile.setType(type);
			upfile.setUpdatetime(now);
			upfile.setUser_id(loginuser_id);
			fileService.insert(upfile);

			file upfile_re = new file();
			upfile_re.setID(upfile.getID());
			upfile_re.setName(upfile.getChangeName());

			object.put("file", upfile_re);
			object.put("success", true);
			object.put("message", "アプロードしました!");
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Cache-Control", "no-cache");
		} catch (Exception e) {
			// TODO: handle exception
		}
		return object;
	}

	@ResponseBody
	@RequestMapping(value = "/setUserPlan_contr", method = RequestMethod.POST)
	private JSONObject setUserPlan_contr(HttpServletResponse response,
			HttpServletRequest request, String id) {
		DynamicDataSourceHolder.setDataSource("defultdataSource");
		JSONObject object = new JSONObject();

		try {
			String[] ids = id.split(",");
			if (ids.length > 0) {
				userService.setUserAuthority("plan_priv", ids);
			}

			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Cache-Control", "no-cache");
		} catch (Exception e) {
			// TODO: handle exception
		}
		return object;
	}

	@ResponseBody
	@RequestMapping(value = "/getPlan", method = RequestMethod.POST)
	private JSONObject getPlan(HttpServletResponse response, HttpServletRequest request, int loginuser_id,
			int currentPage0, int currentPage1, int currentPage2, int currentPage3,int currentPage4,
			int pagesize0,int pagesize1, int pagesize2, int pagesize3, int pagesize4) {
		JSONObject object = new JSONObject();

		int current0 = (currentPage0 - 1) * pagesize0;
		int current1 = (currentPage1 - 1) * pagesize1;
		int current2 = (currentPage2 - 1) * pagesize2;
		int current3 = (currentPage3 - 1) * pagesize3;
		int current4 = (currentPage4 - 1) * pagesize4;

		try {
			request.setCharacterEncoding("utf-8");
			DynamicDataSourceHolder.setDataSource("defultdataSource");
//			List<plan> plans = planService.getPlan();

			List<plan> plan_state0 = planService.getPlanByState0(0, current0 ,pagesize0);
			List<plan> plan_state1 = planService.getPlanByState1(1, current1 ,pagesize1);
			List<plan> plan_state2 = planService.getPlanByState2(2, current2 ,pagesize2);
			List<plan> plan_state3 = planService.getPlanByState3(3, current3 ,pagesize3);
			List<plan> plan_state4 = planService.getPlanByState4(4, current4 ,pagesize4);

			//0:新規 1:受け 2:分配 3:着手 4:完成
			int plan_state1_count = 0;
			int plan_state2_count = 0;
			int plan_state3_count = 0;
			int plan_state4_count = 0;
//			if(plans.size() > 0) {
//				for (int i = 0; i < plans.size(); i++) {
//					if(plans.get(i).getState() == 0) {
//						plan_state0.add(plans.get(i));
//					} else if (plans.get(i).getState() == 1) {
//						plan_state1.add(plans.get(i));
//						if(plans.get(i).getTantou_id() == loginuser_id) {
//							plan_state1_count++;
//						}
//					} else if (plans.get(i).getState() == 2) {
//						plan_state2.add(plans.get(i));
//						if(plans.get(i).getTantou_id() == loginuser_id) {
//							plan_state2_count++;
//						}
//					} else if (plans.get(i).getState() == 3) {
//						plan_state3.add(plans.get(i));
//						if(plans.get(i).getTantou_id() == loginuser_id) {
//							plan_state3_count++;
//						}
//					} else if (plans.get(i).getState() == 4 || plans.get(i).getState() == 5) {
//						plan_state4.add(plans.get(i));
//						if(plans.get(i).getTantou_id() == loginuser_id) {
//							plan_state4_count++;
//						}
//					}
//				}
//			}

			if(plan_state1.size() > 0) {
				for (int i = 0; i < plan_state1.size(); i++) {
					if(plan_state1.get(i).getTantou_id() == loginuser_id) {
						plan_state1_count++;
					}
				}
			}

			if(plan_state2.size() > 0) {
				for (int i = 0; i < plan_state2.size(); i++) {
					if(plan_state2.get(i).getTantou_id() == loginuser_id) {
						plan_state2_count++;
					}
				}
			}

			if(plan_state3.size() > 0) {
				for (int i = 0; i < plan_state3.size(); i++) {
					if(plan_state3.get(i).getTantou_id() == loginuser_id) {
						plan_state3_count++;
					}
				}
			}

			if(plan_state4.size() > 0) {
				for (int i = 0; i < plan_state4.size(); i++) {
					if(plan_state4.get(i).getTantou_id() == loginuser_id) {
						plan_state4_count++;
					}
				}
			}

			int plan_state0_count_all = planService.getPlanCountByState0();
			int plan_state1_count_all = planService.getPlanCountByState1();
			int plan_state2_count_all = planService.getPlanCountByState2();
			int plan_state3_count_all = planService.getPlanCountByState3();
			int plan_state4_count_all = planService.getPlanCountByState4();

			object.put("plan_state0", plan_state0);
			object.put("plan_state1", plan_state1);
			object.put("plan_state2", plan_state2);
			object.put("plan_state3", plan_state3);
			object.put("plan_state4", plan_state4);
			object.put("plan_state0_count", plan_state0.size());
			object.put("plan_state1_count", plan_state1_count);
			object.put("plan_state2_count", plan_state2_count);
			object.put("plan_state3_count", plan_state3_count);
			object.put("plan_state4_count", plan_state4_count);
			object.put("plan_state0_count_all", plan_state0_count_all);
			object.put("plan_state1_count_all", plan_state1_count_all);
			object.put("plan_state2_count_all", plan_state2_count_all);
			object.put("plan_state3_count_all", plan_state3_count_all);
			object.put("plan_state4_count_all", plan_state4_count_all);
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return object;
	}

	@ResponseBody
	@RequestMapping(value = "/getPlanStateDetail", method = RequestMethod.POST)
	private JSONObject getPlanStateDetail(HttpServletResponse response, HttpServletRequest request, int planId) {
		DynamicDataSourceHolder.setDataSource("defultdataSource");
		JSONObject object = new JSONObject();
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");

			List<plan_info> plan_info = planService.getPlanStateDetailById(planId);

			if(plan_info.size() > 0) {
				int state_old = 0;
				for (int i = 0; i < plan_info.size(); i++) {
//					if(plan_info.get(i).getUpdatetime() != null) {
//						plan_info.get(i).setUpdatetime2(sf3.format(plan_info.get(i).getUpdatetime()));
//					}

					//1: 戻る場合
					if(plan_info.get(i).getState() < state_old) {
						plan_info.get(i).setBackShowFlag(1);
						if(plan_info.get(i).getState() == 0) {
							plan_info.get(i).setText1(plan_info.get(i).getUpdater() + "は企画を「新規」に戻りました");
						} else if (plan_info.get(i).getState() == 1) {
							plan_info.get(i).setText1("企画を「受け待ち」に戻りました.. 担当者: " + plan_info.get(i).getUpdater());
						} else if (plan_info.get(i).getState() == 2) {
							plan_info.get(i).setText1(plan_info.get(i).getUpdater() + "は企画を「未着手」に戻りました。");
						} else if (plan_info.get(i).getState() == 3) {
							plan_info.get(i).setText1(plan_info.get(i).getUpdater() + "は企画を「作成中」に戻りました。");
						}
					} else {
						if(plan_info.get(i).getState() == 0) {
							plan_info.get(i).setText1(plan_info.get(i).getUpdater() + "は企画を新規しました。");
						} else if (plan_info.get(i).getState() == 1) {
							plan_info.get(i).setText1(plan_info.get(i).getUpdater() + "は企画を受けました。");
						} else if (plan_info.get(i).getState() == 2) {
							plan_info.get(i).setText1("企画を" + plan_info.get(i).getUpdater() + "に分配されました。");
						} else if (plan_info.get(i).getState() == 3) {
							plan_info.get(i).setText1(plan_info.get(i).getUpdater() + "は企画を着手しました。");
						} else if (plan_info.get(i).getState() == 4) {
							plan_info.get(i).setText1(plan_info.get(i).getUpdater() + "は完成しました。");
						} else if (plan_info.get(i).getState() == 5) {
							plan_info.get(i).setText1(plan_info.get(i).getUpdater() + "は直しました。");
						}
						plan_info.get(i).setBackShowFlag(0);
					}

					state_old = plan_info.get(i).getState();
				}
			}

			object.put("rows", plan_info);
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Cache-Control", "no-cache");
		} catch (Exception e) {
			// TODO: handle exception
		}
		return object;
	}

	@ResponseBody
	@RequestMapping(value = "/getPlanById", method = RequestMethod.POST)
	private JSONObject getPlanById(HttpServletResponse response, HttpServletRequest request,
			String id) {
		JSONObject object = new JSONObject();

		try {
			request.setCharacterEncoding("utf-8");
			DynamicDataSourceHolder.setDataSource("defultdataSource");
			plan plan = planService.getPlanById(id);

			List<file> files = fileService.getFilesByParentId("1",id);
			object.put("files", files);
			object.put("rows", plan);
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return object;
	}

	@ResponseBody
	@RequestMapping(value = "/insertPlan", method = RequestMethod.POST)
	private JSONObject insertPlan(HttpServletResponse response, HttpServletRequest request, String title, String link,
			String info, String file) {
		DynamicDataSourceHolder.setDataSource("defultdataSource");
		JSONObject object = new JSONObject();
		result result = new result();
		try {
			request.setCharacterEncoding("utf-8");

			Date now = new Date();
			int loginuser_id = Integer
					.parseInt(new String(request.getParameter("loginuser_id").getBytes("ISO-8859-1"), "UTF-8"));

			plan plan = new plan();
			plan.setInfo(info);
			plan.setLink(link);
			plan.setTitle(title);
			plan.setUser_id(loginuser_id);
			plan.setCreatetime(now);
			plan.setUpdatetime(now);
			plan.setShowFlag(1);
			planService.insert(plan);

			plan_info pi = new plan_info();
			pi.setParentId(plan.getID());
			pi.setState(0);
			pi.setUpdatetime(now);
			pi.setUser_id(loginuser_id);
			planService.insertInfo(pi);

			String ids = "";
			if (file != "" && !"".equals(file)) {
				JSONArray json = (JSONArray) JSONArray.parse(file);
				if(json.size() >0) {
					for (Object obj : json) {
						JSONObject jo = (JSONObject)obj;
						int id = jo.getInteger("id");
						ids = ids + id + ",";
					}
				}
				if(!"".equals(ids)) {
					String[] ids_arr = ids.split(",");
					fileService.updateParentId(ids_arr,plan.getID());
				}
			}

			result.setState(0);
			object.put("rows", result);
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Cache-Control", "no-cache");
		} catch (Exception e) {
			// TODO: handle exception
		}
		return object;
	}

	@ResponseBody
	@RequestMapping(value = "/updatePlan", method = RequestMethod.POST)
	private JSONObject updatePlan(HttpServletResponse response, HttpServletRequest request, String id,String title, String link,
			String info, String file) {
		DynamicDataSourceHolder.setDataSource("defultdataSource");
		JSONObject object = new JSONObject();
		result result = new result();
		try {
			request.setCharacterEncoding("utf-8");

			Date now = new Date();
			int loginuser_id = Integer
					.parseInt(new String(request.getParameter("loginuser_id").getBytes("ISO-8859-1"), "UTF-8"));

			plan plan = new plan();
			plan.setID(Integer.parseInt(id));
			plan.setInfo(info);
			plan.setLink(link);
			plan.setTitle(title);
			plan.setUser_id(loginuser_id);
			plan.setUpdatetime(now);
			planService.update(plan);

			int plan_id = plan.getID();

			fileService.clearParentId("1", plan_id);
			String file_ids = "";
			if (file != "" && !"".equals(file)) {
				JSONArray json = (JSONArray) JSONArray.parse(file);
				if(json.size() >0) {
					for (Object obj : json) {
						JSONObject jo = (JSONObject)obj;
						int file_id = jo.getInteger("id");
						file_ids = file_ids + file_id + ",";
					}
				}
				if(!"".equals(file_ids)) {
					String[] ids_arr = file_ids.split(",");
					fileService.updateParentId(ids_arr,plan_id);
				}
			}

			result.setState(0);
			object.put("rows", result);
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Cache-Control", "no-cache");
		} catch (Exception e) {
			// TODO: handle exception
		}
		return object;
	}

	@ResponseBody
	@RequestMapping(value = "/changePlanState", method = RequestMethod.POST)
	private JSONObject changePlanState(HttpServletResponse response, HttpServletRequest request, int planId, int state, int tantou_id,
			String info, String code, String file, boolean backFlag) {
		DynamicDataSourceHolder.setDataSource("defultdataSource");
		JSONObject object = new JSONObject();
		result result = new result();
		try {
			request.setCharacterEncoding("utf-8");

			Date now = new Date();
			int loginuser_id = Integer
					.parseInt(new String(request.getParameter("loginuser_id").getBytes("ISO-8859-1"), "UTF-8"));

			plan_info pi = new plan_info();
			pi.setParentId(planId);
			pi.setState(state);
			pi.setUpdatetime(now);
			pi.setBackFlag(backFlag);

			//削除
			if(state == 999) {
				plan pl = new plan();
				pl.setID(planId);
				pl.setUpdatetime(now);
				planService.delete(pl);
			} else {
				if(state == 2) {
					pi.setUser_id(tantou_id);
				} else if (state == 4 || state == 5) {
					pi.setCode(code);
					pi.setInfo(info);
					pi.setUser_id(loginuser_id);
				} else {
					pi.setUser_id(loginuser_id);
				}

				planService.insertInfo(pi);

				if (state == 4 || state == 5) {
					String ids = "";
					if (file != "" && !"".equals(file)) {
						JSONArray json = (JSONArray) JSONArray.parse(file);
						if(json.size() >0) {
							for (Object obj : json) {
								JSONObject jo = (JSONObject)obj;
								int id = jo.getInteger("id");
								ids = ids + id + ",";
							}
						}
						if(!"".equals(ids)) {
							String[] ids_arr = ids.split(",");
							fileService.updateParentId(ids_arr,pi.getID());
						}
					}
				}
			}

			object.put("rows", result);
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Cache-Control", "no-cache");
		} catch (Exception e) {
			// TODO: handle exception
		}
		return object;
	}

	@ResponseBody
	@RequestMapping(value = "/getFinishPlanInfoByLastId", method = RequestMethod.POST)
	private JSONObject getFinishPlanInfoByLastId(HttpServletResponse response, HttpServletRequest request, int id) {
		DynamicDataSourceHolder.setDataSource("defultdataSource");
		JSONObject object = new JSONObject();
		try {
			request.setCharacterEncoding("utf-8");

			plan_info pi = planService.getFinishPlanInfoByLastId(id);
			//file->type->2:finish
			List<file> files = fileService.getLastFinishFilesByParentId(pi.getID());

			object.put("pi", pi);
			object.put("files", files);
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Cache-Control", "no-cache");
		} catch (Exception e) {
			// TODO: handle exception
		}
		return object;
	}



}
