package com.mp.service;

import java.util.List;

import com.mp.entity.file;

public interface fileService {
	void insert(file f);
	void updateParentId(String[] ids, int id);
	void clearParentId(String type, int id);
	List<file> getFilesByParentId(String type, String id);
	file getFileById(String id);
	List<file> getLastFinishFilesByParentId(int id);
	List<file> getUploadRirekiByNe();
}
