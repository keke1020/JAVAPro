package com.mp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mp.entity.user;

public interface userDao {
	user login(@Param("password") String password);
	List<user> getUsersConfig(@Param("limitFlag") boolean limitFlag, @Param("offset") int current, @Param("limit") int pagesize);
	List<user> getUsersConfigByIds(@Param("ids") String[] ids);
	void setUserAuthority(@Param("key") String key, @Param("ids") String[] ids);
	user getUsersConfigById(@Param("id") String id);
	void updateUserConfig(user user);
	int getTotal();
	List<user> getUsersConfigByRealnames(@Param("realnames") String[] realnames);
}
