package com.mp.service;

import java.util.List;
import java.util.Map;

import com.mp.dto.option;
import com.mp.entity.list1;

public interface listService {
	List<list1> getList1(int offset, int limit, String searchId, String searchtime_s, String searchtime_e,
			String searchcontain, String searchkeiban, String searchedaban, String search_arrival_japan,
			String search_arrival_soko, String radio_soko1, String radio_soko2, String radio_soko3);

	List<option> getArrival_japan();

	int getCountAll();

	List<list1> getList1ById(String id);

	void updateList1(list1 list1);
}
