package com.mp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mp.entity.bbs;

public interface bbsDao {
	List<bbs> getBBS(@Param("bbs_update_s") String bbs_update_s, @Param("bbs_update_e") String bbs_update_e, @Param("bbs_keyword") String bbs_keyword, @Param("bbs_user") String bbs_user, @Param("offset") int offset, @Param("limit") int limit);
//	void insertBBS(@Param("loginuser_id") int loginuser_id,@Param("username") String usr, @Param("message") String message, @Param("update") String date);
	void insertBBS(bbs bbs);
	void deleteBBS(@Param("ID") int id);
	int getTotal(@Param("bbs_update_s") String bbs_update_s, @Param("bbs_update_e") String bbs_update_e, @Param("bbs_keyword") String bbs_keyword, @Param("bbs_user") String bbs_user);
}
