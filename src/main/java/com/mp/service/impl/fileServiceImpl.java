package com.mp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mp.dao.fileDao;
import com.mp.entity.file;
import com.mp.service.fileService;
@Service
public class fileServiceImpl implements fileService{
	@Autowired
	private fileDao fileDao;

	public void insert(file f) {
		// TODO 自動生成されたメソッド・スタブ
		fileDao.insert(f);
	}

	public void updateParentId(String[] ids, int id) {
		// TODO 自動生成されたメソッド・スタブ
		fileDao.updateParentId(ids, id);
	}

}
