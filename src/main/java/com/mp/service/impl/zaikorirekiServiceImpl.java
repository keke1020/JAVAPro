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

	@Override
	public void insertAllNagoya(List<zaikorireki> z) {
		// TODO 自動生成されたメソッド・スタブ
		zaikorirekiDao.insertAllNagoya(z);
	}

	@Override
	public List<zaikorireki> getNagoyaRireki(String codeSc, String type,String updateSc_s, String updateSc_e, String orderSC,
			int list_currentPage, int searchCount) {
		// TODO 自動生成されたメソッド・スタブ
		return zaikorirekiDao.getNagoyaRireki(codeSc, type,updateSc_s, updateSc_e, orderSC,
				list_currentPage, searchCount);
	}

	@Override
	public int getNagoyaRireki_total(String codeSc, String updateSc_s, String updateSc_e, String orderSC) {
		// TODO 自動生成されたメソッド・スタブ
		return zaikorirekiDao.getNagoyaRireki_total(codeSc, updateSc_s, updateSc_e, orderSC);
	}



}
