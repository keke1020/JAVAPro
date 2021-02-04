package com.mp.service;

import java.util.List;

import com.mp.entity.souko;

public interface soukoService {
	List<souko> getSouko(String isNagoya, String istana, String orderSC, String t_kbn,
			String codeSc, String containerSc, String kaisosc, String tanaSc, String kosuSC1,
			String kosuSC2, int offset, int limit, String zaikoOnly);

	int getTotal(String isNagoya,String istana, String orderSC, String t_kbn,
			String codeSc, String containerSc, String kaisosc, String tanaSc, String kosuSC1,
			String kosuSC2, String zaikoOnly);

	void upate(int active, List<souko> souko);
	List<souko> getSoukoByCodes(List<String> codes);

	List<souko> getCodeDataBySoukoNagoya(String code, int currentPage, int searchCount);

	void nyuushukkaForSoukoNagoyaByCode(souko so);

	int getTotalBySoukoNagoya(String code);

	void deleteNagoyaZaikoById(int id);

	void nyuushukkaForSoukoNagoya(List<souko> soukos);

	List<souko> getNagoyaRireki2(int list_currentPage, int searchCount);

	int getNagoyaRireki_total2();

	List<souko> getNagoyaRireki2ByUUid(String uuid);
}
