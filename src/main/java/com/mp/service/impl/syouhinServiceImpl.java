package com.mp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mp.dao.syouhinDao;
import com.mp.entity.syouhin;
import com.mp.service.syouhinService;

@Service
public class syouhinServiceImpl implements syouhinService {
	@Autowired
	private syouhinDao syouhinDao;

	public List<syouhin> getSyohinByCode(String code) {
		// TODO 自動生成されたメソッド・スタブ
		return syouhinDao.getSyohinByCode(code);
	}

	public void insertSyoh(syouhin syouhin) {
		syouhinDao.insertSyoh(syouhin);
	}



}
