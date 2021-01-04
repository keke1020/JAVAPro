package com.mp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mp.entity.souko;

public interface soukoDao {
	List<souko> getSouko(@Param("isNagoya") String isNagoya, @Param("istana") String istana,
			@Param("orderSC") String orderSC, @Param("t_kbn") String t_kbn,
			@Param("codeSc") String codeSc, @Param("containerSc") String containerSc, @Param("kaisosc") String kaisosc,
			@Param("tanaSc") String tanaSc, @Param("kosuSC1") String kosuSC1, @Param("kosuSC2") String kosuSC2,
			@Param("offset") int offset, @Param("limit") int limit, @Param("zaikoOnly") String zaikoOnly);

	int getTotal(@Param("isNagoya") String isNagoya, @Param("istana") String istana,
			@Param("orderSC") String orderSC, @Param("t_kbn") String t_kbn,
			@Param("codeSc") String codeSc, @Param("containerSc") String containerSc, @Param("kaisosc") String kaisosc,
			@Param("tanaSc") String tanaSc, @Param("kosuSC1") String kosuSC1, @Param("kosuSC2") String kosuSC2,
			@Param("zaikoOnly") String zaikoOnly);

	void upate(@Param("active") int active, @Param("souko") List<souko> souko);
	List<souko> getSoukoByCodes(@Param("codes") List<String> codes);

}
