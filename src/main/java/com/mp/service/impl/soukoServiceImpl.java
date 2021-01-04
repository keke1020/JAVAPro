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

}
