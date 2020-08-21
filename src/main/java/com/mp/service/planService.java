package com.mp.service;

import java.util.List;

import com.mp.entity.plan;

public interface planService {
	void insert(plan p);
	List<plan> getPlan();
}
