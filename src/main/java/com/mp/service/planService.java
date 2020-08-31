package com.mp.service;

import java.util.List;

import com.mp.entity.plan;
import com.mp.entity.plan_info;

public interface planService {
	void insert(plan p);
	void insertInfo(plan_info pi);
	void update(plan p);
	void delete(plan p);
	List<plan> getPlan();
	plan getPlanById(String id);
	List<plan_info> getPlanStateDetailById(int id);
	plan_info getFinishPlanInfoByLastId(int id);
	List<plan> getPlanByState0(int state, int currentPage, int pageSize);
	List<plan> getPlanByState1(int state, int currentPage, int pageSize);
	List<plan> getPlanByState2(int state, int currentPage, int pageSize);
	List<plan> getPlanByState3(int state, int currentPage, int pageSize);
	List<plan> getPlanByState4(int state, int currentPage, int pageSize);
	int getPlanCountByState0();
	int getPlanCountByState1();
	int getPlanCountByState2();
	int getPlanCountByState3();
	int getPlanCountByState4();
}
