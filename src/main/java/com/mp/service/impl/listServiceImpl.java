package com.mp.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mp.dao.listDao;
import com.mp.dto.option;
import com.mp.entity.list1;
import com.mp.service.listService;

@Service
public class listServiceImpl implements listService {
	@Autowired
	private listDao listDao;

	public List<list1> getList1(int offset, int limit, String searchId, String searchtime_s, String searchtime_e,
			String searchcontain, String searchkeiban, String searchedaban, String search_arrival_japan,
			String search_arrival_soko, String radio_soko1, String radio_soko2, String radio_soko3) {
		return listDao.getList1(offset, limit, searchId, searchtime_s, searchtime_e, searchcontain, searchkeiban,
				searchedaban, search_arrival_japan, search_arrival_soko, radio_soko1, radio_soko2, radio_soko3);
	}

	public List<option> getArrival_japan() {
		return listDao.getArrival_japan();
	}

	public int getCountAll() {
		return listDao.getCountAll();
	}

	public List<list1> getList1ById(String id) {
		return listDao.getList1ById(id);
	}

	public void updateList1(list1 list1) {
		listDao.updateList1(list1);
	}

}
