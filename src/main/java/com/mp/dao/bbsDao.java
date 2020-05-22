package com.mp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mp.entity.bbs;

public interface bbsDao {
	List<bbs> getBBS(@Param("offset") int offset, @Param("limit") int limit);
//	void insertBBS(@Param("loginuser_id") int loginuser_id,@Param("username") String usr, @Param("message") String message, @Param("update") String date);
	void insertBBS(bbs bbs);
	void deleteBBS(@Param("ID") int id);
	int getTotal();
}
