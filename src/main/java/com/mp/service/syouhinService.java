package com.mp.service;

import java.util.List;

import com.mp.entity.syouhin;

public interface syouhinService {
	List<syouhin> getSyohinByCode(String code);
	void insertSyoh(syouhin syouhin);
}
