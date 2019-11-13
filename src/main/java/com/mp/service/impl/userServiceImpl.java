package com.mp.service.impl;

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



}
