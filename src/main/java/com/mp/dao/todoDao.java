package com.mp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mp.entity.todo;

public interface todoDao {
	List<todo> getTodoList(@Param("offset") int current,  @Param("limit") int pagesize, @Param("search_tanto") String search_tanto, @Param("end_flag") int end_flag, @Param("search_condition") String search_condition);
	int getTotal(@Param("search_tanto") String search_tanto, @Param("end_flag") int end_flag, @Param("search_condition") String search_condition);
	String getCommentById(@Param("id") String id);
	void updateComment(@Param("id") String id, @Param("comment") String comment);
	todo getTodoById(@Param("id") String id);
	void updateByType(todo todo);
	void update(todo todo);
	void insert(todo todo);
	void deleteById(@Param("id") String id);
}
