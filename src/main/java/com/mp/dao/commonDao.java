package com.mp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mp.dto.option;

public interface commonDao {
	List<option> getArrival_japan();
	List<option> getBBS_user();
	List<option> getUsers(@Param("place") String place);
	String getInfoByType(@Param("type") String type);
	void insertInfo(@Param("type") String type, @Param("info") String info);
	List<option> getTenpoByNe();
	List<option> getHomeDataByNe(@Param("tenpo") String tenpo,@Param("beginDay") String beginDay,@Param("endDay") String endDay);
	List<option> getJyuchuName_optionsByNe(@Param("tenpo") String tenpo);
	List<option> getJyuchuKekka_optionsByNe(@Param("tenpo") String tenpo);
	List<option> getAllShopCodeDataByNe(@Param("start") String start, @Param("end") String end);
}
