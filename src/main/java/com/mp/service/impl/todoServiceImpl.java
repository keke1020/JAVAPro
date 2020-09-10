package com.mp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mp.dao.todoDao;
import com.mp.entity.todo;
import com.mp.service.todoService;

@Service
public class todoServiceImpl implements todoService {
	@Autowired
	private todoDao todoDao;

	public List<todo> getTodoList(int current, int pagesize, String search_tanto, int end_flag, String search_condition) {
		// TODO 自動生成されたメソッド・スタブ
		return todoDao.getTodoList(current, pagesize, search_tanto, end_flag, search_condition);
	}

	public int getTotal(String search_tanto, int end_flag, String search_condition) {
		// TODO 自動生成されたメソッド・スタブ
		return todoDao.getTotal(search_tanto, end_flag, search_condition);
	}

	public String getCommentById(String id) {
		// TODO 自動生成されたメソッド・スタブ
		return todoDao.getCommentById(id);
	}

	public void updateComment(String id, String comment) {
		// TODO 自動生成されたメソッド・スタブ
		todoDao.updateComment(id, comment);
	}

	public todo getTodoById(String id) {
		// TODO 自動生成されたメソッド・スタブ
		return todoDao.getTodoById(id);
	}

	public void updateByType(todo todo) {
		// TODO 自動生成されたメソッド・スタブ
		todoDao.updateByType(todo);
	}

	public void insert(todo todo) {
		// TODO 自動生成されたメソッド・スタブ
		todoDao.insert(todo);
	}

	public void deleteById(String id) {
		// TODO 自動生成されたメソッド・スタブ
		todoDao.deleteById(id);
	}

	public void update(todo todo) {
		// TODO 自動生成されたメソッド・スタブ
		todoDao.update(todo);
	}

}
