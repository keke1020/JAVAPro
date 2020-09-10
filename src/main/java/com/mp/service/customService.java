package com.mp.service;

import java.util.List;

import com.mp.entity.custom;

public interface customService {
	void insert(custom custom);
	void update(custom custom);
	List<custom> getCustom();
	custom getCustomById(String id);
	void deleteCustomById(String id);
	void setView_count(String id);
}
