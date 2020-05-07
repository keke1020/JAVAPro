package com.mp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.mp.dto.option;
import com.mp.entity.list1;

public interface listDao {
	List<list1> getList1(@Param("offset") int offset, @Param("limit") int limit, @Param("searchId") String searchId,
			@Param("searchtime_s") String searchtime_s, @Param("searchtime_e") String searchtime_e,
			@Param("searchcontain_check") String searchcontain_check, @Param("searchcontain") String searchcontain,
			@Param("searchkeiban") String searchkeiban, @Param("searchedaban") String searchedaban,
			@Param("search_arrival_japan") String search_arrival_japan,
			@Param("search_arrival_soko") String search_arrival_soko, @Param("radio_soko1") String radio_soko1,
			@Param("radio_soko2") String radio_soko2, @Param("radio_soko3") String radio_soko3);

	int getCountAll(@Param("searchId") String searchId, @Param("searchtime_s") String searchtime_s,
			@Param("searchtime_e") String searchtime_e, @Param("searchcontain_check") String searchcontain_check,
			@Param("searchcontain") String searchcontain, @Param("searchkeiban") String searchkeiban,
			@Param("searchedaban") String searchedaban, @Param("search_arrival_japan") String search_arrival_japan,
			@Param("search_arrival_soko") String search_arrival_soko, @Param("radio_soko1") String radio_soko1,
			@Param("radio_soko2") String radio_soko2, @Param("radio_soko3") String radio_soko3);

	List<option> getArrival_japan();

	List<list1> getList1ById(@Param("searchId") String id);

	void updateList1(list1 list1);
}
