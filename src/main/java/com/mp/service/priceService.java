package com.mp.service;

import java.util.List;

import com.mp.entity.ne_hikaku;
import com.mp.entity.price;

public interface priceService {
	void insert(List<price> price);

	void deleteByToday(String tenpo);

	void deleteByDataTime(String tenpo, String time);

	List<price> getPriceData(String date, String tenpo, int current, int pageCount);

	int getPriceData_total(String date, String tenpo);

	List<price> getDistinctCodeByDate(String start, String end);

	List<price> getDistinctCodeByDate_ne(String start, String end);

	price getHikakuDataByCodeAndDate(String tenpo, String code, String start, String end);

	List<ne_hikaku> getHikakuDataByDate(List<String> codes, String start, String end);
	List<ne_hikaku> getHikakuDataByDate_ne(String start, String end);
	String getDcodeByLocation(String code);


}
