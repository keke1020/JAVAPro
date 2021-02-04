package com.mp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mp.dao.soukoDao;
import com.mp.entity.souko;
import com.mp.service.soukoService;

@Service
public class soukoServiceImpl implements soukoService {

	@Autowired
	private soukoDao soukoDao;

	@Override
	public List<souko> getSouko(String isNagoya, String istana, String orderSC, String t_kbn, String codeSc,
			String containerSc,
			String kaisosc, String tanaSc, String kosuSC1, String kosuSC2, int offset, int limit, String zaikoOnly) {
		// TODO 自動生成されたメソッド・スタブ
		return soukoDao.getSouko(isNagoya, istana, orderSC, t_kbn, codeSc, containerSc,
				kaisosc, tanaSc, kosuSC1, kosuSC2, offset, limit, zaikoOnly);
	}

	@Override
	public int getTotal(String isNagoya, String istana, String orderSC, String t_kbn, String codeSc, String containerSc,
			String kaisosc,
			String tanaSc, String kosuSC1, String kosuSC2, String zaikoOnly) {
		// TODO 自動生成されたメソッド・スタブ
		return soukoDao.getTotal(isNagoya, istana, orderSC, t_kbn,
				codeSc, containerSc, kaisosc, tanaSc, kosuSC1,
				kosuSC2, zaikoOnly);
	}

	@Override
	public void upate(int active, List<souko> souko) {
		// TODO 自動生成されたメソッド・スタブ
		soukoDao.upate(active, souko);
	}

	@Override
	public List<souko> getSoukoByCodes(List<String> codes) {
		// TODO 自動生成されたメソッド・スタブ
		return soukoDao.getSoukoByCodes(codes);
	}

	@Override
	public List<souko> getCodeDataBySoukoNagoya(String code, int currentPage, int searchCount) {
		// TODO 自動生成されたメソッド・スタブ
		return soukoDao.getCodeDataBySoukoNagoya(code, currentPage, searchCount);
	}

	@Override
	public void nyuushukkaForSoukoNagoyaByCode(souko so) {
		// TODO 自動生成されたメソッド・スタブ
		soukoDao.nyuushukkaForSoukoNagoyaByCode(so);
	}

	@Override
	public int getTotalBySoukoNagoya(String code) {
		// TODO 自動生成されたメソッド・スタブ
		return soukoDao.getTotalBySoukoNagoya(code);
	}

	@Override
	public void deleteNagoyaZaikoById(int id) {
		// TODO 自動生成されたメソッド・スタブ
		soukoDao.deleteNagoyaZaikoById(id);
	}

	@Override
	public void nyuushukkaForSoukoNagoya(List<souko> soukos) {
		// TODO 自動生成されたメソッド・スタブ
		soukoDao.nyuushukkaForSoukoNagoya(soukos);
	}

	@Override
	public List<souko> getNagoyaRireki2(int list_currentPage, int searchCount) {
		// TODO 自動生成されたメソッド・スタブ
		return soukoDao.getNagoyaRireki2(list_currentPage, searchCount);
	}

	@Override
	public int getNagoyaRireki_total2() {
		// TODO 自動生成されたメソッド・スタブ
		return soukoDao.getNagoyaRireki_total2();
	}

	@Override
	public List<souko> getNagoyaRireki2ByUUid(String uuid) {
		// TODO 自動生成されたメソッド・スタブ
		return soukoDao.getNagoyaRireki2ByUUid(uuid);
	}
}
