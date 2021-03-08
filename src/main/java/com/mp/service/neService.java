package com.mp.service;

import java.util.List;

import com.mp.entity.ne;
import com.mp.entity.ne_goods;
import com.mp.entity.ne_hikaku;
import com.mp.entity.ne_meisai;

public interface neService {
	void insert(List<ne> ne);

	List<ne> getHomeDataByNe_everday(String tenpo, String time_s, String time_e);

	List<ne> getHomeTenpoinfoDataByNe_month(String tenpo, String start, String end, int current, int pageCount);

	int getHomeTenpoinfoCountDataByNe_month(String tenpo, String start, String end);

	List<ne_meisai> getHomeTenpoinfo2DataByNe_month(String tenpo, String start, String end, int current, int pageCount);

	List<ne_hikaku> getHomeKakuTenpoinfoDataByNe(List<String> codes, String start, String end);
	List<ne> getHomeKakuTenpoinfoDataByNe2(List<String> codes, String start, String end);

	List<String> getHomeKakuTenpoinfoCodeDataByNe(List<String> codes, String start, String end);

	int getHomeTenpoinfo2CountDataByNe_month(String tenpo, String start, String end);

	Integer getHomedata_goukei(String tenpo, String start, String end);

	ne getHomedata_keisan(String tenpo, String start, String end);

	List<ne> getMeisaiDataByNe(String tenpo, String time_s, String time_e, String jyuchu_name, String denpyono,
			String haitatsukanryo_chk, String haitatsukanryo_chk2, String haitatsukanryo_s, String haitatsukanryo_e,
			String kekka, String jyuchu_haisokaisya, int current, int pageCount);

	int getMeisaiDataByNe_count(String tenpo, String time_s, String time_e, String jyuchu_name, String denpyono,
			String haitatsukanryo_chk, String haitatsukanryo_chk2, String haitatsukanryo_s, String haitatsukanryo_e,
			String kekka, String jyuchu_haisokaisya);

	int getMeisaiDataByNe_goukei(String tenpo, String time_s, String time_e, String jyuchu_name, String denpyono,
			String haitatsukanryo_chk, String haitatsukanryo_chk2, String haitatsukanryo_s, String haitatsukanryo_e,
			String kekka, String jyuchu_haisokaisya);

	void deleteMeisaiByIds(String[] ids);

	void UpdateTsuisekiData(List<ne> ne);

	void deleteByDenpyono(List<Integer> no);

	void insertNeMeisai(List<ne_meisai> nm);

	void insertGoods(List<ne_goods> ng);

	List<ne> getHomedata(String tenpo, String beginDateStr, String endDateStr);



}
