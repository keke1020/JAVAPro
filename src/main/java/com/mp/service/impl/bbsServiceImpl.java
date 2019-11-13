package com.mp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mp.dao.bbsDao;
import com.mp.entity.bbs;
import com.mp.service.bbsService;

@Service
public class bbsServiceImpl implements bbsService{
	@Autowired
	private bbsDao bbsDao;

	public List<bbs> getBBS(int offset, int limit) {
		return bbsDao.getBBS(offset, limit);
	}

	public void insertBBS(int id, String username, String message, String date) {
		bbsDao.insertBBS(0,username,message,date);
	}

	public void deleteBBS(int id) {
		bbsDao.deleteBBS(id);
	}



}
