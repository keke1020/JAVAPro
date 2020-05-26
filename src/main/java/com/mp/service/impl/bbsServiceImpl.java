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

	public List<bbs> getBBS(String bbs_update_s, String bbs_update_e, String bbs_keyword, String bbs_user, int offset, int limit) {
		return bbsDao.getBBS(bbs_update_s, bbs_update_e, bbs_keyword, bbs_user, offset, limit);
	}

//	public void insertBBS(int loginuser_id, String username, String message, String date) {
//		bbsDao.insertBBS(loginuser_id,username,message,date);
//	}

	public void insertBBS(bbs bbs) {
		bbsDao.insertBBS(bbs);
	}

	public void deleteBBS(int id) {
		bbsDao.deleteBBS(id);
	}

	public int getTotal(String bbs_update_s, String bbs_update_e, String bbs_keyword, String bbs_user) {
		return bbsDao.getTotal(bbs_update_s, bbs_update_e, bbs_keyword, bbs_user);
	}



}
