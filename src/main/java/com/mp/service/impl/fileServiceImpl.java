package com.mp.service.impl;

import java.util.List;

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

	public void clearParentId(String type, int id) {
		// TODO 自動生成されたメソッド・スタブ
		fileDao.clearParentId(type, id);
	}

	public List<file> getFilesByParentId(String type, String id) {
		// TODO 自動生成されたメソッド・スタブ
		return fileDao.getFilesByParentId(type,id);
	}

	public file getFileById(String id) {
		// TODO 自動生成されたメソッド・スタブ
		return fileDao.getFileById(id);
	}

	public List<file> getLastFinishFilesByParentId(int id) {
		// TODO 自動生成されたメソッド・スタブ
		return fileDao.getLastFinishFilesByParentId(id);
	}

	public List<file> getUploadRirekiByNe() {
		// TODO 自動生成されたメソッド・スタブ
		return fileDao.getUploadRirekiByNe();
	}



}
