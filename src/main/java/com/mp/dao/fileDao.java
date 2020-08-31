package com.mp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mp.entity.file;

public interface fileDao {
	void insert(file f);
	void updateParentId(@Param("ids") String[] ids, @Param("parentid") int id);
	void clearParentId(@Param("type") String type, @Param("parentid") int id);
	List<file> getFilesByParentId(@Param("type") String type, @Param("id") String id);
	file getFileById(@Param("id") String id);
	List<file> getLastFinishFilesByParentId(@Param("id") int id);
}
