package com.mp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mp.entity.ne;

public interface neDao {
	void insert(List<ne> ne);

	List<ne> getHomeDataByNe_everday(@Param("tenpo") String tenpo, @Param("time_s") String time_s,
			@Param("time_e") String time_e);

	List<ne> getMeisaiDataByNe(@Param("tenpo") String tenpo, @Param("time_s") String time_s,
			@Param("time_e") String time_e, @Param("jyuchu_name") String jyuchu_name,
			@Param("denpyono") String denpyono, @Param("haitatsukanryo_chk") String haitatsukanryo_chk,
			@Param("haitatsukanryo_s") String haitatsukanryo_s, @Param("haitatsukanryo_e") String haitatsukanryo_e,
			@Param("current") int current, @Param("pageCount") int pageCount);

	int getMeisaiDataByNe_count(@Param("tenpo") String tenpo, @Param("time_s") String time_s,
			@Param("time_e") String time_e, @Param("jyuchu_name") String jyuchu_name,
			@Param("denpyono") String denpyono, @Param("haitatsukanryo_chk") String haitatsukanryo_chk,
			@Param("haitatsukanryo_s") String haitatsukanryo_s, @Param("haitatsukanryo_e") String haitatsukanryo_e);

	int getMeisaiDataByNe_goukei(@Param("tenpo") String tenpo, @Param("time_s") String time_s,
			@Param("time_e") String time_e, @Param("jyuchu_name") String jyuchu_name,
			@Param("denpyono") String denpyono, @Param("haitatsukanryo_chk") String haitatsukanryo_chk,
			@Param("haitatsukanryo_s") String haitatsukanryo_s, @Param("haitatsukanryo_e") String haitatsukanryo_e);

	void deleteMeisaiByIds(@Param("ids") String[] ids);

	void UpdateTsuisekiData(List<ne> ne);
}
