package com.mp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mp.entity.ne;
import com.mp.entity.ne_goods;
import com.mp.entity.ne_hikaku;
import com.mp.entity.ne_meisai;

public interface neDao {
	void insert(List<ne> ne);

	List<ne> getHomeDataByNe_everday(@Param("tenpo") String tenpo, @Param("time_s") String time_s,
			@Param("time_e") String time_e);

	List<ne> getHomeTenpoinfoDataByNe_month(@Param("tenpo") String tenpo, @Param("start") String start,
			@Param("end") String end, @Param("current") int current, @Param("pageCount") int pageCount);

	int getHomeTenpoinfoCountDataByNe_month(@Param("tenpo") String tenpo, @Param("start") String start,
			@Param("end") String end);

	List<ne_meisai> getHomeTenpoinfo2DataByNe_month(@Param("tenpo") String tenpo, @Param("start") String start,
			@Param("end") String end, @Param("current") int current, @Param("pageCount") int pageCount);

	int getHomeTenpoinfo2CountDataByNe_month(@Param("tenpo") String tenpo, @Param("start") String start,
			@Param("end") String end);

	Integer getHomedata_goukei(@Param("tenpo") String tenpo, @Param("start") String start,
			@Param("end") String end);

	ne getHomedata_keisan(@Param("tenpo") String tenpo, @Param("start") String start,
			@Param("end") String end);

	List<ne> getMeisaiDataByNe(@Param("tenpo") String tenpo, @Param("time_s") String time_s,
			@Param("time_e") String time_e, @Param("jyuchu_name") String jyuchu_name,
			@Param("denpyono") String denpyono, @Param("haitatsukanryo_chk") String haitatsukanryo_chk,
			@Param("haitatsukanryo_chk2") String haitatsukanryo_chk2,
			@Param("haitatsukanryo_s") String haitatsukanryo_s, @Param("haitatsukanryo_e") String haitatsukanryo_e,
			@Param("kekka") String kekka, @Param("jyuchu_haisokaisya") String jyuchu_haisokaisya,
			@Param("current") int current, @Param("pageCount") int pageCount);

	int getMeisaiDataByNe_count(@Param("tenpo") String tenpo, @Param("time_s") String time_s,
			@Param("time_e") String time_e, @Param("jyuchu_name") String jyuchu_name,
			@Param("denpyono") String denpyono, @Param("haitatsukanryo_chk") String haitatsukanryo_chk,
			@Param("haitatsukanryo_chk2") String haitatsukanryo_chk2,
			@Param("haitatsukanryo_s") String haitatsukanryo_s, @Param("haitatsukanryo_e") String haitatsukanryo_e,
			@Param("kekka") String kekka, @Param("jyuchu_haisokaisya") String jyuchu_haisokaisya);

	int getMeisaiDataByNe_goukei(@Param("tenpo") String tenpo, @Param("time_s") String time_s,
			@Param("time_e") String time_e, @Param("jyuchu_name") String jyuchu_name,
			@Param("denpyono") String denpyono, @Param("haitatsukanryo_chk") String haitatsukanryo_chk,
			@Param("haitatsukanryo_chk2") String haitatsukanryo_chk2,
			@Param("haitatsukanryo_s") String haitatsukanryo_s, @Param("haitatsukanryo_e") String haitatsukanryo_e,
			@Param("kekka") String kekka, @Param("jyuchu_haisokaisya") String jyuchu_haisokaisya);

	void deleteMeisaiByIds(@Param("ids") String[] ids);

	void UpdateTsuisekiData(List<ne> ne);

	void deleteByDenpyono(@Param("ids") List<Integer> no);

	void insertNeMeisai(List<ne_meisai> nm);

	void insertGoods(List<ne_goods> ng);

	List<ne_hikaku> getHomeKakuTenpoinfoDataByNe(@Param("codes") List<String> codes, @Param("start") String start,
			@Param("end") String end);

	List<String> getHomeKakuTenpoinfoCodeDataByNe(@Param("start") String start,
			@Param("end") String end);
}
