package com.mp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mp.dao.listDao;
import com.mp.entity.list1;
import com.mp.service.listService;

@Service
public class listServiceImpl implements listService{
	@Autowired
	private listDao listDao;

	public List<list1> getList1(int offset, int limit) {
		return listDao.getList1(offset, limit);
	}




}
