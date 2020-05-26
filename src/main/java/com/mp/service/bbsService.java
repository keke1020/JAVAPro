package com.mp.service;

import java.util.List;

import com.mp.entity.bbs;

public interface bbsService {
	List<bbs> getBBS(String bbs_update_s, String bbs_update_e, String bbs_keyword, String bbs_user, int offset, int limit);
//	void insertBBS(int loginuser_id, String username, String message, String date);
	void insertBBS(bbs bbs);
	void deleteBBS(int id);
	int getTotal(String bbs_update_s, String bbs_update_e, String bbs_keyword, String bbs_user);
}
