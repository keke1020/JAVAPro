package com.mp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mp.dao.planDao;
import com.mp.entity.plan;
import com.mp.service.planService;

@Service
public class planServiceImpl implements planService{
	@Autowired
	private planDao planDao;

	public void insert(plan p) {
		planDao.insert(p);
	}

	public List<plan> getPlan() {
		// TODO 自動生成されたメソッド・スタブ
		return planDao.getPlan();
	}





}
