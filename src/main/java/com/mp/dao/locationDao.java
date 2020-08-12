package com.mp.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mp.entity.location;

public interface locationDao {
	List<location> getlocation(@Param("searchFlag") String searchFlag, @Param("commonFlag") String commonFlag,@Param("istana") String istana,
			@Param("orderSC") String orderSC, @Param("t_kbn") String t_kbn, @Param("singuZaiko") String singuZaiko,
			@Param("codeSc") String codeSc, @Param("containerSc") String containerSc, @Param("kaisosc") String kaisosc,
			@Param("tanaSc") String tanaSc, @Param("kosuSC1") String kosuSC1, @Param("kosuSC2") String kosuSC2,
			@Param("limitFlag") String limitFlag,@Param("offset") int offset, @Param("limit") int limit);

	int getTotal(@Param("searchFlag") String searchFlag, @Param("istana") String istana,
			@Param("orderSC") String orderSC, @Param("t_kbn") String t_kbn, @Param("singuZaiko") String singuZaiko,
			@Param("codeSc") String codeSc, @Param("containerSc") String containerSc, @Param("kaisosc") String kaisosc,
			@Param("tanaSc") String tanaSc, @Param("kosuSC1") String kosuSC1, @Param("kosuSC2") String kosuSC2);

	int getChangeCount();

	void editlocation(location lo);
	int getLocationCountById(@Param("id") String id);
	int getLocationCountByIds(@Param("ids") String[] ids);
	int getLocationCountByCodes(@Param("codes") String[] codes);
	location getLocationByCode(@Param("code") String code);
	List<location> getLocationById(@Param("id") String id);
	List<location> getLocationByIds(@Param("ids") String[] ids);
	void deleteLocationByIds(@Param("ids") String[] ids);
	void updateByCSV1(@Param("list") List<location> lo);
	void updateByCSV2(@Param("list") List<location> lo);
	void updateByCSV3(@Param("list") List<location> lo);
	void deleteByCSV(@Param("list") List<location> lo);
	void insertByCSV1(@Param("list") List<location> lo);
	void insertByCSV2(@Param("list") List<location> lo);
	void updateShinguByCode(location lo);

}
