package com.mp.dao;

import org.apache.ibatis.annotations.Param;

import com.mp.entity.file;

public interface fileDao {
	void insert(file f);
	void updateParentId(@Param("ids") String[] ids, @Param("id") int id);
}
