package com.mp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mp.entity.zaikorireki;

public interface zaikorirekiDao {
	List<zaikorireki> getShiGuRirekiByView(@Param("condition") String condition, @Param("count") int count);
	void insert(zaikorireki z);
	List<zaikorireki> getRirekiByView(@Param("condition") String condition, @Param("count") int count);
}
