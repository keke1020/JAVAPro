package com.mp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mp.dao.listDao;
import com.mp.dto.option;
import com.mp.entity.list1;
import com.mp.entity.list3;
import com.mp.service.listService;
import com.mp.util.MyBatisSqlUtils;

@Service
public class listServiceImpl implements listService {
	@Autowired
	private listDao listDao;

	public List<list1> getList1(String exportFlag, int offset, int limit, String searchId, String searchtime_s, String searchtime_e,
			String searchcontain_check, String searchcontain, String searchkeiban, String searchedaban,
			String search_arrival_japan, String search_arrival_soko, String radio_soko0, String radio_soko1, String radio_soko2,
			String radio_soko3) {
		return listDao.getList1(exportFlag, offset, limit, searchId, searchtime_s, searchtime_e, searchcontain_check, searchcontain,
				searchkeiban, searchedaban, search_arrival_japan, search_arrival_soko, radio_soko0, radio_soko1, radio_soko2,
				radio_soko3);
	}

	public int getCountAll(String searchId, String searchtime_s, String searchtime_e, String searchcontain_check,
			String searchcontain, String searchkeiban, String searchedaban, String search_arrival_japan,
			String search_arrival_soko, String radio_soko0, String radio_soko1, String radio_soko2, String radio_soko3) {
		return listDao.getCountAll(searchId, searchtime_s, searchtime_e, searchcontain_check, searchcontain,
				searchkeiban, searchedaban, search_arrival_japan, search_arrival_soko, radio_soko0, radio_soko1, radio_soko2,
				radio_soko3);
	}

	public List<list1> getList1ById(String id) {
		return listDao.getList1ById(id);
	}

	public void updateList1(list1 list1) {
		listDao.updateList1(list1);
	}

	public void insertList1(list1 list1) {
		listDao.insertList1(list1);
	}

	public void deleteList1(int id, int loginuser_id, String loginuser) {
		listDao.deleteList1(id,loginuser_id,loginuser);
	}

	public void insertList3(list3 list3) {
		listDao.insertList3(list3);
	}

	public List<list1> getList1_all() {
		return listDao.getList1_all();
	}

}
