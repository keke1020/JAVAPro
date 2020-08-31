package com.mp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mp.entity.plan;
import com.mp.entity.plan_info;

public interface planDao {
	void insert(plan p);
	void insertInfo(plan_info pi);
	void update(plan p);
	void delete(plan p);
	List<plan> getPlan();
	plan getPlanById(@Param("id") String id);
	List<plan_info> getPlanStateDetailById(@Param("id") int id);
	plan_info getFinishPlanInfoByLastId(@Param("id") int id);
	List<plan> getPlanByState0(@Param("state") int state, @Param("offset") int currentPage, @Param("limit") int pageSize);
	List<plan> getPlanByState1(@Param("state") int state, @Param("offset") int currentPage, @Param("limit") int pageSize);
	List<plan> getPlanByState2(@Param("state") int state, @Param("offset") int currentPage, @Param("limit") int pageSize);
	List<plan> getPlanByState3(@Param("state") int state, @Param("offset") int currentPage, @Param("limit") int pageSize);
	List<plan> getPlanByState4(@Param("state") int state, @Param("offset") int currentPage, @Param("limit") int pageSize);
	int getPlanCountByState0();
	int getPlanCountByState1();
	int getPlanCountByState2();
	int getPlanCountByState3();
	int getPlanCountByState4();
}
