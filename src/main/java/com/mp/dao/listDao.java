package com.mp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mp.entity.list1;

public interface listDao {
	List<list1> getList1(@Param("offset") int offset, @Param("limit") int limit);

}
