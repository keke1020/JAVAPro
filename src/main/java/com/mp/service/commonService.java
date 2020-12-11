package com.mp.service;

import java.util.List;

import com.mp.dto.option;

public interface commonService {
	List<option> getArrival_japan();
	List<option> getBBS_user();
	List<option> getUsers(String place);
	String getInfoByType(String type);
	void insertInfo(String type, String info);
	List<option> getTenpoByNe();
	List<option> getHomeDataByNe(String tenpo,String beginDay,String endDay);
	List<option> getJyuchuName_optionsByNe(String tenpo);
	List<option> getJyuchuKekka_optionsByNe(String tenpo);
	List<option> getAllShopCodeDataByNe(String start, String end);
}
