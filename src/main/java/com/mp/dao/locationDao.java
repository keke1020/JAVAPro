package com.mp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mp.entity.location;

public interface locationDao {
	List<location> getlocation(@Param("orderSC") String orderSC, @Param("offset") int offset, @Param("limit") int limit);
	int getTotal();
}
