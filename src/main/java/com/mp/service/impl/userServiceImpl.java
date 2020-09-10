package com.mp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mp.dao.userDao;
import com.mp.entity.user;
import com.mp.service.userService;

@Service
public class userServiceImpl implements userService {
	@Autowired
	private userDao userDao;

	public user login(String password) {
		return userDao.login(password);
	}

	public List<user> getUsersConfig(boolean limitFlag, int current, int pagesize) {
		// TODO 自動生成されたメソッド・スタブ
		return userDao.getUsersConfig(limitFlag, current, pagesize);
	}

	public void setUserAuthority(String key, String[] ids) {
		// TODO 自動生成されたメソッド・スタブ
		userDao.setUserAuthority(key, ids);
	}

	public user getUsersConfigById(String id) {
		// TODO 自動生成されたメソッド・スタブ
		return userDao.getUsersConfigById(id);
	}

	public void updateUserConfig(user user) {
		// TODO 自動生成されたメソッド・スタブ
		userDao.updateUserConfig(user);
	}

	public int getTotal() {
		// TODO 自動生成されたメソッド・スタブ
		return userDao.getTotal();
	}

	public List<user> getUsersConfigByIds(String[] ids) {
		// TODO 自動生成されたメソッド・スタブ
		return userDao.getUsersConfigByIds(ids);
	}

	public List<user> getUsersConfigByRealnames(String[] realnames) {
		// TODO 自動生成されたメソッド・スタブ
		return userDao.getUsersConfigByRealnames(realnames);
	}



}
