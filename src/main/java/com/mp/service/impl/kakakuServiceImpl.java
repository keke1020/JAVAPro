package com.mp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mp.dao.kakakuDao;
import com.mp.entity.kakaku;
import com.mp.service.kakakuService;
@Service
public class kakakuServiceImpl implements kakakuService {
	@Autowired
	private kakakuDao kakakuDao;

	public List<kakaku> getKakaku(int offset, int limit, String sort, String[] s_code, String t_price1, String t_price2,
			String s_price1, String s_price2, String s_limit1, String s_limit2, String s_update1, String s_update2,
			Boolean t_price_ch1, Boolean t_price_ch2, Boolean s_price_ch1, Boolean s_price_ch2, Boolean s_limit_ch1,
			Boolean s_limit_ch2) {
		// TODO 自動生成されたメソッド・スタブ
		return kakakuDao.getKakaku(offset, limit, sort, s_code, t_price1,
				t_price2, s_price1, s_price2, s_limit1, s_limit2, s_update1, s_update2, t_price_ch1, t_price_ch2,
				s_price_ch1, s_price_ch2, s_limit_ch1, s_limit_ch2);
	}

	public int getTotal() {
		// TODO 自動生成されたメソッド・スタブ
		return kakakuDao.getTotal();
	}

	public void updateKakaku(kakaku k) {
		// TODO 自動生成されたメソッド・スタブ
		kakakuDao.updateKakaku(k);
	}

	public List<kakaku> getKakakuRireki(String code) {
		// TODO 自動生成されたメソッド・スタブ
		return kakakuDao.getKakakuRireki(code);
	}

	public List<kakaku> getAllCodes() {
		// TODO 自動生成されたメソッド・スタブ
		return kakakuDao.getAllCodes();
	}

}
