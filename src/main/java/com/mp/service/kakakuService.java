package com.mp.service;

import java.util.List;

import com.mp.entity.kakaku;

public interface kakakuService {
	List<kakaku> getKakaku(int offset, int limit, String sort, String[] s_code, String t_price1, String t_price2,
			String s_price1, String s_price2, String s_limit1, String s_limit2, String s_update1, String s_update2,
			Boolean t_price_ch1, Boolean t_price_ch2, Boolean s_price_ch1, Boolean s_price_ch2, Boolean s_limit_ch1,
			Boolean s_limit_ch2);
	void updateKakaku(kakaku k);
	int getTotal();
	List<kakaku> getKakakuRireki(String code);
	List<kakaku> getAllCodes();
}
