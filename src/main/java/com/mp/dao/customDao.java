package com.mp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mp.entity.custom;

public interface customDao {
	void insert(custom custom);
	void update(custom custom);
	List<custom> getCustom();
	custom getCustomById(@Param("id") String id);
	void deleteCustomById(@Param("id") String id);
	void setView_count(@Param("id") String id);
}
