package com.mp.dao;

import java.util.List;

import com.mp.entity.plan;

public interface planDao {
	void insert(plan p);
	List<plan> getPlan();
}
