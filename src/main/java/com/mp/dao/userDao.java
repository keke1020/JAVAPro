package com.mp.dao;

import org.apache.ibatis.annotations.Param;

import com.mp.entity.user;

public interface userDao {
	user login(@Param("password") String password);
}
