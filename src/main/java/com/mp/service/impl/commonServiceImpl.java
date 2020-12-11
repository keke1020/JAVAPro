package com.mp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mp.dao.commonDao;
import com.mp.dto.option;
import com.mp.service.commonService;

@Service
public class commonServiceImpl implements commonService{
	@Autowired
	private commonDao commonDao;

	public List<option> getArrival_japan() {
		return commonDao.getArrival_japan();
	}

	public List<option> getBBS_user() {
		return commonDao.getBBS_user();
	}

	public List<option> getUsers(String place) {
		return commonDao.getUsers(place);
	}

	public String getInfoByType(String type) {
		// TODO 自動生成されたメソッド・スタブ
		return commonDao.getInfoByType(type);
	}

	public void insertInfo(String type, String info) {
		// TODO 自動生成されたメソッド・スタブ
		commonDao.insertInfo(type,info);
	}

	public List<option> getTenpoByNe() {
		// TODO 自動生成されたメソッド・スタブ
		return commonDao.getTenpoByNe();
	}

	public List<option> getHomeDataByNe(String tenpo,String beginDay,String endDay) {
		// TODO 自動生成されたメソッド・スタブ
		return commonDao.getHomeDataByNe(tenpo,beginDay,endDay);
	}

	public List<option> getJyuchuName_optionsByNe(String tenpo) {
		// TODO 自動生成されたメソッド・スタブ
		return commonDao.getJyuchuName_optionsByNe(tenpo);
	}

	@Override
	public List<option> getJyuchuKekka_optionsByNe(String tenpo) {
		// TODO 自動生成されたメソッド・スタブ
		return commonDao.getJyuchuKekka_optionsByNe(tenpo);
	}

	@Override
	public List<option> getAllShopCodeDataByNe(String start, String end) {
		// TODO 自動生成されたメソッド・スタブ
		return commonDao.getAllShopCodeDataByNe(start,end);
	}



}
