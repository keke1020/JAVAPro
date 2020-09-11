package com.mp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mp.entity.kakaku;

public interface kakakuDao {
	List<kakaku> getKakaku(@Param("offset") int offset, @Param("limit") int limit, @Param("sort") String sort);
	int getTotal();
}
