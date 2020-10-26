package com.mp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mp.dao.tenpoDao;
import com.mp.entity.tenpo;
import com.mp.service.tenpoService;

@Service
public class tenpoServiceImpl implements tenpoService {
	@Autowired
	private tenpoDao tenpoDao;

	public List<tenpo> getTenpo() {
		// TODO 自動生成されたメソッド・スタブ
		return tenpoDao.getTenpo();
	}

}
