package com.mp.service;

import java.util.List;

import com.mp.entity.user;

public interface userService {
	user login(String password);
	List<user> getUsersConfig(boolean limitFlag, int current, int pagesize);
	List<user> getUsersConfigByIds(String[] ids);
	void setUserAuthority(String key, String[] ids);
	user getUsersConfigById(String id);
	void updateUserConfig(user user);
	int getTotal();
	List<user> getUsersConfigByRealnames(String[] realnames);
}
