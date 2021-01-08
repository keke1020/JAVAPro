package com.mp.service;

import java.util.List;

import com.mp.entity.ne_hikaku;
import com.mp.entity.price;

public interface priceService {
	void insert(List<price> price);

	void deleteByToday(String tenpo);

	void deleteByDataTime(String tenpo, String time);

	List<price> getPriceData(String date, String tenpo, int show, int current, int pageCount);

	int getPriceData_total(String date, String tenpo, int show);

	List<price> getDistinctCodeByDate(String start, String end, int show);

	List<price> getDistinctCodeDataByDate(String start, String end, int show);

	List<price> getDistinctCodeByDate_ne(String start, String end);

	price getHikakuDataByCodeAndDate(String tenpo, String code, String start, String end);

	List<ne_hikaku> getHikakuDataByDate(String kakaku1,
			String start, String end);

	List<ne_hikaku> getHikakuDataByDate_ne(String start, String end);

	String getDcodeByLocation(String code);

	void deleteByUuid(String uuid);

	void backByUuid(String uuid);

	int getCountByUuid(String uuid);

}
