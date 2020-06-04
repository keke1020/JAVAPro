package com.mp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mp.dao.locationDao;
import com.mp.entity.location;
import com.mp.service.locationService;

@Service
public class locationServiceImpl implements locationService{
	@Autowired
	private locationDao locationDao;

	public List<location> getlocation() {
		return locationDao.getlocation();
	}

}
