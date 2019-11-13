package com.mp.service;

import java.util.List;

import com.mp.entity.bbs;

public interface bbsService {
	List<bbs> getBBS(int offset, int limit);
	void insertBBS(int id,String username, String message, String date);
	void deleteBBS(int id);
}
