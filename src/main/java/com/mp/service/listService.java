package com.mp.service;

import java.util.List;

import com.mp.dto.option;
import com.mp.entity.list1;
import com.mp.entity.list3;

public interface listService {
	List<list1> getList1(String exportFlag,int offset, int limit, String searchId, String searchtime_s, String searchtime_e,
			String searchcontain_check, String searchcontain, String searchkeiban, String searchedaban,
			String search_arrival_japan, String search_arrival_soko, String radio_soko1, String radio_soko2,
			String radio_soko3);

	int getCountAll(String searchId, String searchtime_s, String searchtime_e, String searchcontain_check,
			String searchcontain, String searchkeiban, String searchedaban, String search_arrival_japan,
			String search_arrival_soko, String radio_soko1, String radio_soko2, String radio_soko3);

	List<option> getArrival_japan();

	List<list1> getList1ById(String id);

	void updateList1(list1 list1);
	void insertList1(list1 list1);
	void deleteList1(int id,int loginuser_id, String loginuser);

	void insertList3(list3 list3);
	List<list1> getList1_all();


}
