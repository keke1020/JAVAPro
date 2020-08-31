package com.mp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mp.dao.customDao;
import com.mp.entity.custom;
import com.mp.service.customService;

@Service
public class customServiceImpl implements customService {
	@Autowired
	private customDao customDao;

	public void insert(custom custom) {
		customDao.insert(custom);
	}

	public List<custom> getCustom() {
		return customDao.getCustom();
	}

	public custom getCustomById(String id) {
		return customDao.getCustomById(id);
	}

	public void deleteCustomById(String id) {
		// TODO 自動生成されたメソッド・スタブ
		customDao.deleteCustomById(id);
	}

	public void update(custom custom) {
		// TODO 自動生成されたメソッド・スタブ
		customDao.update(custom);
	}

}
