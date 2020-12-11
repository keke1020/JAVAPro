package com.mp.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import com.mp.entity.todo;
import com.mp.entity.user;
import com.mp.service.commonService;
import com.mp.service.todoService;
import com.mp.service.userService;
import com.mp.util.DeleteFileUtil;

@Controller
public class todoController {
	@Autowired
	private todoService todoService;

	@Autowired
	private commonService commonService;

	@Autowired
	private userService userService;

	SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	@ResponseBody
	@RequestMapping(value = "/loadTodo", method = RequestMethod.POST)
	private JSONObject setUserPlan_contr(HttpServletResponse response, HttpServletRequest request, int currentPage,
			int pagesize, String loginuser, String search_tanto, int end_flag, String search_condition) {
		DynamicDataSourceHolder.setDataSource("defultdataSource");
		JSONObject object = new JSONObject();

		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			String info = commonService.getInfoByType("todo");
			String[] info_ = info.split("\n");
			List<user> users = userService.getUsersConfig(false, 0, 0);
//			List<option> op_todo_priv1 = commonService.getUsers("todo_priv1");
//			List<option> op_todo_priv2 = commonService.getUsers("todo_priv2");
			List<option> user_op = commonService.getUsers(null);
			int current = (currentPage - 1) * pagesize;
			List<todo> todoList = todoService.getTodoList(current, pagesize, search_tanto, end_flag, search_condition);
			boolean isInTantou = false;
			if (todoList.size() > 0) {
				for (int i = 0; i < todoList.size(); i++) {
					isInTantou = false;
					if (!"".equals(todoList.get(i).getTanto()) && todoList.get(i).getTanto() != null) {
						if (todoList.get(i).getTanto().indexOf(",") > 0) {
							String[] tanto_ = todoList.get(i).getTanto().split(",");
							if (ArrayUtils.contains(tanto_, loginuser)) {
								isInTantou = true;
							}
						} else {
							if (loginuser.equals(todoList.get(i).getTanto())) {
								isInTantou = true;
							}
						}
					}
					todoList.get(i).setInTantou(isInTantou);

					if (!"".equals(todoList.get(i).getComment()) && todoList.get(i).getComment() != null) {
						if (todoList.get(i).getComment().indexOf(",") > 0) {
							String[] comment_ = todoList.get(i).getComment().split(",");
							todoList.get(i).setComment_count(comment_.length);
							for (int j = 0; j < comment_.length; j++) {
								if (comment_[j].contains("|=|")) {
									comment_[j] = comment_[j].replace("|=|", " >> ");
								}
							}
							todoList.get(i).setComment_(comment_);
						} else {
							todoList.get(i).setComment_count(1);
						}

					} else {
						todoList.get(i).setComment_count(0);
					}

//					if (!"".equals(todoList.get(i).getMemo()) && todoList.get(i).getMemo() != null) {
//						if(todoList.get(i).getMemo().indexOf("\\r\\n") > 0) {
//							String[] memo_list = todoList.get(i).getMemo().split("\r\n");
//							todoList.get(i).setMemo_list(memo_list);
//						}
//					}

					if (!"".equals(todoList.get(i).getRireki()) && todoList.get(i).getRireki() != null) {
						if (todoList.get(i).getRireki().contains("<br>")) {
							String[] rireki = todoList.get(i).getRireki().split("<br>");
							todoList.get(i).setRireki_(rireki);
						}
					}

					if (!"".equals(todoList.get(i).getFiles()) && todoList.get(i).getFiles() != null) {
						String[] files_ = todoList.get(i).getFiles().split(",");
						List<option> options = new ArrayList<option>();
						for (int j = 0; j < files_.length; j++) {
							String file_sp = files_[j];
//							System.out.println(file_sp);
							if (file_sp.contains("|")) {
								String[] op_ = file_sp.split("\\|");
								option op = new option();
								op.setLabel(op_[0]);
								op.setValue(op_[1]);
								options.add(op);
							}
						}
						todoList.get(i).setFiles_(options);
					}
				}
			}
			int total = todoService.getTotal(search_tanto, end_flag, search_condition);

			object.put("info", info);
			object.put("info_", info_);
			object.put("users", users);
//			object.put("op_todo_priv1", op_todo_priv1);
//			object.put("op_todo_priv2", op_todo_priv2);
			object.put("user_op", user_op);
			object.put("todoList", todoList);
			object.put("total", total);
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Cache-Control", "no-cache");
		} catch (Exception e) {
			// TODO: handle exception
		}
		return object;
	}

	@ResponseBody
	@RequestMapping(value = "/getTodoInfoById", method = RequestMethod.POST)
	private JSONObject getTodoInfoById(HttpServletResponse response, HttpServletRequest request, String id) {
		DynamicDataSourceHolder.setDataSource("defultdataSource");
		JSONObject object = new JSONObject();
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");

			todo todo = todoService.getTodoById(id);

			List<file> files = new ArrayList<file>();
			if (!"".equals(todo.getFiles()) && todo.getFiles() != null) {
				String[] files_ = todo.getFiles().split(",");
				if (files_.length > 0) {
					for (int j = 0; j < files_.length; j++) {
						if (files_[j].contains("|")) {
							String[] op_ = files_[j].split("\\|");
							file f = new file();
							f.setName(op_[0]);
							f.setChangeName(op_[1]);
							f.setValue(op_[0] + "|" + op_[1]);
							files.add(f);
						}
					}
				}
			}
			object.put("files", files);

			if (!"".equals(todo.getTanto()) && todo.getTanto() != null) {
				String tanto_[] = todo.getTanto().split(",");
				List<user> tantos = userService.getUsersConfigByRealnames(tanto_);
				if (tantos.size() > 0) {
					List<String> tantoid_ = new ArrayList<String>();
					for (int i = 0; i < tantos.size(); i++) {
						tantoid_.add(tantos.get(i).getID());
					}
					String[] tantoids_ = new String[tantoid_.size()];
					tantoid_.toArray(tantoids_);
					todo.setTanto_id(tantoids_);
				}
			}

			object.put("todo", todo);

			List<user> users = userService.getUsersConfig(false, 0, 0);
			object.put("users", users);

			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Cache-Control", "no-cache");
		} catch (Exception e) {
			// TODO: handle exception
		}
		return object;
	}

	@ResponseBody
	@RequestMapping(value = "/insertTodo", method = RequestMethod.POST)
	private JSONObject insertTodo(HttpServletResponse response, HttpServletRequest request, String title,
			String loginuser, String status, String file, String memo, int endFlag, String tanto) {
		DynamicDataSourceHolder.setDataSource("defultdataSource");
		JSONObject object = new JSONObject();
		result result = new result();
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");

			Date now = new Date();

			String file_insert = "";
			if (file != "" && !"".equals(file)) {
				JSONArray json = (JSONArray) JSONArray.parse(file);
				if (json.size() > 0) {
					for (Object obj : json) {
						JSONObject jo = (JSONObject) obj;
						String value = jo.getString("value");
						file_insert = file_insert + value + ",";
					}
				}

			}

			String tanto_insert = "";
			if (tanto != "" && !"".equals(tanto)) {
				String[] tanto_ = tanto.split(",");
				List<user> tantos = userService.getUsersConfigByIds(tanto_);
				if (tantos != null && tantos.size() > 0) {
					for (int i = 0; i < tantos.size(); i++) {
						if (!"".equals(tantos.get(i).getRealname()) && tantos.get(i).getRealname() != null) {
							tanto_insert = tanto_insert + tantos.get(i).getRealname() + ",";
						}
					}
				}
			}
			if (!"".equals(tanto_insert)) {
				tanto_insert = tanto_insert.substring(0, tanto_insert.length() - 1);
			}

			todo todo = new todo();
			todo.setUpdate(now);
			todo.setTitle(title);
			todo.setMemo(memo);
			todo.setUser(loginuser);
			todo.setTanto(tanto_insert);
			todo.setStatus(status);
			todo.setEnd(endFlag);
			todo.setFiles(file_insert);
			todoService.insert(todo);

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
	@RequestMapping(value = "/editTodo", method = RequestMethod.POST)
	private JSONObject editTodo(HttpServletResponse response, HttpServletRequest request, String id, String title,
			String loginuser, String status, String file, String memo, String endFlag, String tanto) {
		DynamicDataSourceHolder.setDataSource("defultdataSource");
		JSONObject object = new JSONObject();
		result result = new result();
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");

			String file_insert = "";
			if (file != "" && !"".equals(file)) {
				JSONArray json = (JSONArray) JSONArray.parse(file);
				if (json.size() > 0) {
					for (Object obj : json) {
						JSONObject jo = (JSONObject) obj;
						String value = jo.getString("value");
						file_insert = file_insert + value + ",";
					}
				}

			}

			String tanto_insert = "";
			if (tanto != "" && !"".equals(tanto)) {
				String[] tanto_ = tanto.split(",");
				List<user> tantos = userService.getUsersConfigByIds(tanto_);
				if (tantos != null && tantos.size() > 0) {
					for (int i = 0; i < tantos.size(); i++) {
						if (!"".equals(tantos.get(i).getRealname()) && tantos.get(i).getRealname() != null) {
							tanto_insert = tanto_insert + tantos.get(i).getRealname() + ",";
						}
					}
				}
			}
			if (!"".equals(tanto_insert)) {
				tanto_insert = tanto_insert.substring(0, tanto_insert.length() - 1);
			}

			todo todo = new todo();
			todo.setID(Integer.parseInt(id));
			todo.setTitle(title);
			todo.setMemo(memo);
			todo.setUser(loginuser);
			todo.setTanto(tanto_insert);
			todo.setStatus(status);
			if("完了".equals(endFlag)) {
				todo.setEnd(0);
			} else if ("未完了".equals(endFlag)) {
				todo.setEnd(1);
			}

			todo.setFiles(file_insert);
			todoService.update(todo);

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
	@RequestMapping(value = "/deleteTodo", method = RequestMethod.POST)
	private JSONObject deleteTodo(HttpServletResponse response, HttpServletRequest request, String id) {
		DynamicDataSourceHolder.setDataSource("defultdataSource");
		JSONObject object = new JSONObject();
		result result = new result();
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			StringBuilder msg = new StringBuilder();

			String path = "";
			if (config.ISLOCAL) {
				path = config.TODO_FILE_PLACE_LOCAL;
			} else {
				path = config.TODO_FILE_PLACE_SERVER;
			}

			todo todo = todoService.getTodoById(id);
			String[] filePath = todo.getFiles().split(",");
			if (filePath.length > 0) {
				for (int i = 0; i < filePath.length; i++) {
					if (filePath[i].contains("|")) {
						String[] filePath_ = filePath[i].split("\\|");
						DeleteFileUtil.deleteFile(path + filePath_[1]);
						msg.append(filePath_[0]).append(",");
					}
				}
			}

			if (msg.length() > 0 && !"null".equals(msg.toString()) && !"".equals(msg.toString())) {
				msg.deleteCharAt(msg.length() - 1);
				msg.append("を削除しました。");
			}
			todoService.deleteById(id);

			result.setState(0);
			result.setMsg(msg.toString());
			object.put("rows", result);
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Cache-Control", "no-cache");
		} catch (Exception e) {
			// TODO: handle exception
		}
		return object;
	}

	@ResponseBody
	@RequestMapping(value = "/deleteTodoOneFile", method = RequestMethod.POST)
	private JSONObject deleteTodoOneFile(HttpServletResponse response, HttpServletRequest request, String file_Str) {
		JSONObject object = new JSONObject();
		result result = new result();
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			StringBuilder msg = new StringBuilder();

			String path = "";
			if (config.ISLOCAL) {
				path = config.TODO_FILE_PLACE_LOCAL;
			} else {
				path = config.TODO_FILE_PLACE_SERVER;
			}

			if (!"".equals(file_Str) && file_Str != null && file_Str.contains("|")) {
				String[] file_ = file_Str.split("\\|");
				if (!"".equals(file_[1])) {
					if (DeleteFileUtil.deleteFile(path + file_[1])) {
						msg.append(file_[0]).append("を削除しました。");
						result.setState(0);
						result.setMsg(msg.toString());
					}
				}
			} else {
				result.setState(1);
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
	@RequestMapping(value = "/uploadTodoFile", method = RequestMethod.POST)
	private JSONObject uploadTodoFile(@RequestParam(value = "file") MultipartFile file, HttpServletResponse response,
			HttpServletRequest request) {
		DynamicDataSourceHolder.setDataSource("defultdataSource");
		JSONObject object = new JSONObject();

		try {
			String path = "";
			if (config.ISLOCAL) {
				path = config.TODO_FILE_PLACE_LOCAL;
			} else {
				path = config.TODO_FILE_PLACE_SERVER;
			}

			String originalFileName = file.getOriginalFilename();
			String date = String.valueOf(new Date().getTime());
			String suffix = originalFileName.substring(file.getOriginalFilename().lastIndexOf(".") + 1);

			String newFileName = date + "." + suffix;
			File file1 = new File(path, newFileName);
			file.transferTo(file1);

			object.put("originalFileName", originalFileName);
			object.put("newFileName", newFileName);

			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Cache-Control", "no-cache");
		} catch (Exception e) {
			// TODO: handle exception
		}
		return object;
	}

	@ResponseBody
	@RequestMapping(value = "/uploadTodoConfig", method = RequestMethod.POST)
	private JSONObject uploadTodoConfig(HttpServletResponse response, HttpServletRequest request, String type,
			String info, String ids1, String ids2) {
		DynamicDataSourceHolder.setDataSource("defultdataSource");
		JSONObject object = new JSONObject();

		try {
			response.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			commonService.insertInfo(type, info);

			String[] ids1_ = ids1.split(",");
			if (ids1_.length > 0) {
				userService.setUserAuthority("todo_priv1", ids1_);
			}

			String[] ids2_ = ids2.split(",");
			if (ids2_.length > 0) {
				userService.setUserAuthority("todo_priv2", ids2_);
			}

			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Cache-Control", "no-cache");
		} catch (Exception e) {
			// TODO: handle exception
		}
		return object;
	}

	@ResponseBody
	@RequestMapping(value = "/updateTodoByType", method = RequestMethod.POST)
	private JSONObject updateTodoByType(HttpServletResponse response, HttpServletRequest request, String id, String type,
			String comment_, String loginuser) {
		DynamicDataSourceHolder.setDataSource("defultdataSource");
		JSONObject object = new JSONObject();

		try {
			response.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			Date now = new Date();

			todo todo = todoService.getTodoById(id);

			if (todo != null) {
				if ("comment".equals(type)) {
					String comment = todo.getComment();
					comment = comment + loginuser + "|=|" + comment_ + ",";
					todo.setComment(comment);
					todoService.updateByType(todo);
				} else if ("delcomment".equals(type)) {
					String comment = todo.getComment();
					String del = comment_.replace(" >> ", "|=|");
					comment = comment.replace(del + ",", "");
					todo.setComment(comment);
					todoService.updateByType(todo);
				} else if ("delTantou".equals(type)) {
					String ddtanto = todo.getTanto();
					String tanto = ddtanto.replace(loginuser + ",", "");

					String ddrireki = todo.getRireki();
					String rireki = ddrireki + "<br>[完了]" + loginuser + " " + sf.format(now);

					todo.setTanto(tanto);
					todo.setRireki(rireki);
					todoService.updateByType(todo);
				}
			}

			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Cache-Control", "no-cache");
		} catch (Exception e) {
			// TODO: handle exception
		}
		return object;
	}

	@ResponseBody
	@RequestMapping(value = "/dlTodoFile", method = RequestMethod.POST)
	private ResponseEntity<byte[]> dlTodoFile(HttpServletResponse response, HttpServletRequest request, String name,
			String value) throws IOException {
		DynamicDataSourceHolder.setDataSource("defultdataSource");
		HttpHeaders headers = new HttpHeaders();
		String path = "";
		try {
			if (config.ISLOCAL) {
				path = config.TODO_FILE_PLACE_LOCAL + value;
			} else {
				path = config.TODO_FILE_PLACE_SERVER + value;
			}
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			headers.setContentDispositionFormData("attachment", path.substring(path.lastIndexOf("/") + 1));
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Cache-Control", "no-cache");
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(new File(path)), headers, HttpStatus.CREATED);
	}
}
