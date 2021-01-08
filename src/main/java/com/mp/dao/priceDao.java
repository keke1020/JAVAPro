package com.mp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mp.entity.ne_hikaku;
import com.mp.entity.price;

public interface priceDao {
	void insert(List<price> price);

	void deleteByToday(@Param("tenpo") String tenpo);

	void deleteByDataTime(@Param("tenpo") String tenpo, @Param("time") String time);

	List<price> getPriceData(@Param("date") String date, @Param("tenpo") String tenpo, @Param("show") int show, @Param("current") int current,
			@Param("pageCount") int pageCount);

	int getPriceData_total(@Param("date") String date, @Param("tenpo") String tenpo, @Param("show") int show);

	List<price> getDistinctCodeByDate(@Param("start") String start, @Param("end") String end, @Param("show") int show);

	List<price> getDistinctCodeByDate_ne(@Param("start") String start, @Param("end") String end);

	price getHikakuDataByCodeAndDate(@Param("tenpo") String tenpo, @Param("code") String code,
			@Param("start") String start, @Param("end") String end);

	List<ne_hikaku> getHikakuDataByDate(@Param("kakaku1") String kakaku1,
			@Param("start") String start, @Param("end") String end);

	List<ne_hikaku> getHikakuDataByDate_ne(@Param("start") String start, @Param("end") String end);

	List<price> getDistinctCodeDataByDate(@Param("start") String start, @Param("end") String end, @Param("show") int show);

	String getDcodeByLocation(@Param("code") String code);

	void deleteByUuid(@Param("uuid") String uuid);

	void backByUuid(@Param("uuid") String uuid);

	int getCountByUuid(@Param("uuid") String uuid);
}
