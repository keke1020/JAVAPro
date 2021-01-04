package com.mp.service;

import java.util.List;

import com.mp.entity.zaikorireki;

public interface zaikorirekiService {
	List<zaikorireki> getShiGuRirekiByView(String condition, int count);

	void insert(zaikorireki z);

	List<zaikorireki> getRirekiByView(String condition, int count);

	void insertAllNagoya(List<zaikorireki> z);

	List<zaikorireki> getNagoyaRireki(String codeSc, String updateSc_s, String updateSc_e, String orderSC,
			int list_currentPage, int searchCount);

	int getNagoyaRireki_total(String codeSc, String updateSc_s, String updateSc_e, String orderSC);

}
