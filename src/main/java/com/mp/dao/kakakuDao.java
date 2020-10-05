package com.mp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mp.entity.kakaku;

public interface kakakuDao {
	List<kakaku> getKakaku(@Param("offset") int offset, @Param("limit") int limit, @Param("sort") String sort,
			@Param("s_code") String[] s_code, @Param("t_price1") String t_price1, @Param("t_price2") String t_price2,
			@Param("s_price1") String s_price1, @Param("s_price2") String s_price2, @Param("s_limit1") String s_limit1,
			@Param("s_limit2") String s_limit2, @Param("s_update1") String s_update1,
			@Param("s_update2") String s_update2, @Param("t_price_ch1") Boolean t_price_ch1,
			@Param("t_price_ch2") Boolean t_price_ch2, @Param("s_price_ch1") Boolean s_price_ch1,
			@Param("s_price_ch2") Boolean s_price_ch2, @Param("s_limit_ch1") Boolean s_limit_ch1,
			@Param("s_limit_ch2") Boolean s_limit_ch2);

	int getTotal();
	void updateKakaku(kakaku k);
	List<kakaku> getKakakuRireki(@Param("code") String code);
	List<kakaku> getAllCodes();
}
