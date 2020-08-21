package com.mp.service;

import com.mp.entity.file;

public interface fileService {
	void insert(file f);
	void updateParentId(String[] ids, int id);
}
