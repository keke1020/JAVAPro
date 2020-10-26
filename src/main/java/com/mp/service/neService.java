package com.mp.service;

import java.util.List;

import com.mp.entity.ne;

public interface neService {
	void insert(List<ne> ne);
	List<ne> getHomeDataByNe_everday(String tenpo, String time_s,String time_e);
	List<ne> getMeisaiDataByNe(String tenpo, String time_s, String time_e, String jyuchu_name, int current, int pageCount);
	int getMeisaiDataByNe_count(String tenpo, String time_s, String time_e, String jyuchu_name);
	int getMeisaiDataByNe_goukei(String tenpo, String time_s, String time_e, String jyuchu_name, int current, int pageCount);
}
