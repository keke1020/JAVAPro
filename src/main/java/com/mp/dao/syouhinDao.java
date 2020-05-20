package com.mp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mp.entity.syouhin;

public interface syouhinDao {
	List<syouhin> getSyohinByCode(@Param("code") String code);
	void insertSyoh(syouhin syouhin);
}
