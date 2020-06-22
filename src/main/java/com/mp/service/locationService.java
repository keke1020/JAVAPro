package com.mp.service;

import java.util.Date;
import java.util.List;

import com.mp.entity.location;

public interface locationService {
	List<location> getlocation(String searchFlag, String istana, String orderSC, String t_kbn, String singuZaiko,
			String codeSc,String containerSc, String kaisosc, String tanaSc, String kosuSC1, String kosuSC2, String limitFlag, int offset, int limit);

	int getTotal(String searchFlag, String istana, String orderSC, String t_kbn, String singuZaiko, String containerSc,
			String codeSc,String kaisosc, String tanaSc, String kosuSC1, String kosuSC2);

	int getChangeCount();
	void editlocation(location lo);
	int getLocationCountById(String id);
	int getLocationCountByIds(String[] ids);
	List<location> getLocationById(String id);
	List<location> getLocationByIds(String[] ids);
	void deleteLocationByIds(String[] ids);
	void updateByCSV1(List<location> lo);
	void updateByCSV2(List<location> lo);
	void updateByCSV3(List<location> lo);
	void deleteByCSV(List<location> lo);
	void insertByCSV1(List<location> lo);
	void insertByCSV2(List<location> lo);
}
