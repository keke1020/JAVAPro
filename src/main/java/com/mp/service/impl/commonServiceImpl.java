package com.mp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mp.dao.commonDao;
import com.mp.dto.option;
import com.mp.entity.user;
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


}
