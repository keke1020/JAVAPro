package com.mp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mp.dao.zaikorirekiDao;
import com.mp.entity.zaikorireki;
import com.mp.service.zaikorirekiService;

@Service
public class zaikorirekiServiceImpl implements zaikorirekiService {
	@Autowired
	private zaikorirekiDao zaikorirekiDao;

	public List<zaikorireki> getShiGuRirekiByView(String condition, int count) {
		return zaikorirekiDao.getShiGuRirekiByView(condition, count);
	}

	public void insert(zaikorireki z) {
		zaikorirekiDao.insert(z);
	}

	public List<zaikorireki> getRirekiByView(String condition, int count) {
		return zaikorirekiDao.getRirekiByView(condition, count);
	}

}
