package com.mp.service;

import java.util.List;

import com.mp.entity.todo;

public interface todoService {
	List<todo> getTodoList(int current, int pagesize, String search_tanto, int end_flag, String search_condition);
	int getTotal(String search_tanto, int end_flag, String search_condition);
	String getCommentById(String id);
	void update(todo todo);
	void updateByType(todo todo);
	void updateComment(String id, String comment);
	todo getTodoById(String id);
	void insert(todo todo);
	void deleteById(String id);
}
