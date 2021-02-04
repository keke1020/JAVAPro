package com.mp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mp.entity.zaikorireki;

public interface zaikorirekiDao {
	List<zaikorireki> getShiGuRirekiByView(@Param("condition") String condition, @Param("count") int count);

	void insert(zaikorireki z);

	List<zaikorireki> getRirekiByView(@Param("condition") String condition, @Param("count") int count);

	void insertAllNagoya(@Param("zaikorireki") List<zaikorireki> z);

	List<zaikorireki> getNagoyaRireki(@Param("code") String codeSc, @Param("type") String type, @Param("update_s") String updateSc_s,
			@Param("update_e") String updateSc_e, @Param("order") String orderSC,
			@Param("offset") int list_currentPage, @Param("limit") int searchCount);

	int getNagoyaRireki_total(@Param("code") String codeSc, @Param("update_s") String updateSc_s,
			@Param("update_e") String updateSc_e, @Param("order") String orderSC);

}
