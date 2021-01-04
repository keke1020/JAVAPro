package com.mp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mp.dao.locationDao;
import com.mp.entity.location;
import com.mp.service.locationService;

@Service
public class locationServiceImpl implements locationService {
	@Autowired
	private locationDao locationDao;

	public List<location> getlocation(String searchFlag, String commonFlag,String istana, String orderSC, String t_kbn, String singuZaiko,
			String codeSc, String containerSc, String kaisosc, String tanaSc, String kosuSC1, String kosuSC2,
			String limitFlag,int offset, int limit) {
		return locationDao.getlocation(searchFlag, commonFlag, istana, orderSC, t_kbn, singuZaiko, codeSc, containerSc, kaisosc,
				tanaSc, kosuSC1, kosuSC2,limitFlag, offset, limit);
	}

	public int getTotal(String searchFlag, String istana, String orderSC, String t_kbn, String singuZaiko,
			String codeSc, String containerSc, String kaisosc, String tanaSc, String kosuSC1, String kosuSC2) {
		return locationDao.getTotal(searchFlag, istana, orderSC, t_kbn, singuZaiko, codeSc, containerSc, kaisosc,
				tanaSc, kosuSC1, kosuSC2);
	}

	public int getChangeCount() {
		return locationDao.getChangeCount();
	}

	public void editlocation(location lo) {
		locationDao.editlocation(lo);
	}

	public int getLocationCountById(String id) {
		return locationDao.getLocationCountById(id);
	}

	public int getLocationCountByIds(String[] ids) {
		return locationDao.getLocationCountByIds(ids);
	}

	public int getLocationCountByCodes(String[] codes) {
		return locationDao.getLocationCountByCodes(codes);
	}

	public location getLocationByCode(String code) {
		return locationDao.getLocationByCode(code);
	}

	public List<location> getLocationById(String id) {
		return locationDao.getLocationById(id);
	}

	public List<location> getLocationByIds(String[] ids) {
		return locationDao.getLocationByIds(ids);
	}

	public void deleteLocationByIds(String[] ids) {
		locationDao.deleteLocationByIds(ids);
	}

	public void updateByCSV1(List<location> lo) {
		locationDao.updateByCSV1(lo);
	}

	public void updateByCSV2(List<location> lo) {
		locationDao.updateByCSV2(lo);
	}

	public void updateByCSV3(List<location> lo) {
		locationDao.updateByCSV3(lo);
	}

	public void deleteByCSV(List<location> lo) {
		locationDao.deleteByCSV(lo);
	}

	public void insertByCSV1(List<location> lo) {
		locationDao.insertByCSV1(lo);
	}

	public void insertByCSV2(List<location> lo) {
		locationDao.insertByCSV2(lo);
	}

	public void updateShinguByCode(location lo) {
		locationDao.updateShinguByCode(lo);
	}





}
