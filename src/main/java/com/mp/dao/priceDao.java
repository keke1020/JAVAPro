package com.mp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mp.entity.ne_hikaku;
import com.mp.entity.price;

public interface priceDao {
	void insert(List<price> price);
	void deleteByToday(@Param("tenpo") String tenpo);
	void deleteByDataTime(@Param("tenpo") String tenpo, @Param("time") String time);
	List<price> getPriceData(@Param("date") String date, @Param("tenpo") String tenpo, @Param("current") int current, @Param("pageCount") int pageCount);
	int getPriceData_total(@Param("date") String date, @Param("tenpo") String tenpo);
	List<price> getDistinctCodeByDate(@Param("start") String start, @Param("end") String end);
	List<price> getDistinctCodeByDate_ne(@Param("start") String start, @Param("end") String end);
	price getHikakuDataByCodeAndDate(@Param("tenpo") String tenpo, @Param("code") String code, @Param("start") String start, @Param("end") String end);
	List<ne_hikaku> getHikakuDataByDate(@Param("codes") List<String> codes, @Param("start") String start, @Param("end") String end);
	List<ne_hikaku> getHikakuDataByDate_ne(@Param("start") String start, @Param("end") String end);
	String getDcodeByLocation(@Param("code") String code);
}
