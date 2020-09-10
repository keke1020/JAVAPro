package com.mp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mp.dao.planDao;
import com.mp.entity.plan;
import com.mp.entity.plan_info;
import com.mp.service.planService;

@Service
public class planServiceImpl implements planService {
	@Autowired
	private planDao planDao;

	public void insert(plan p) {
		planDao.insert(p);
	}

	public void insertInfo(plan_info pi) {
		// TODO 自動生成されたメソッド・スタブ
		planDao.insertInfo(pi);
	}

	public List<plan> getPlan() {
		// TODO 自動生成されたメソッド・スタブ
		return planDao.getPlan();
	}

	public plan getPlanById(String id) {
		return planDao.getPlanById(id);
	}

	public void update(plan p) {
		planDao.update(p);
	}

	public List<plan_info> getPlanStateDetailById(int id) {
		// TODO 自動生成されたメソッド・スタブ
		return planDao.getPlanStateDetailById(id);
	}

	public plan_info getFinishPlanInfoByLastId(int id) {
		// TODO 自動生成されたメソッド・スタブ
		return planDao.getFinishPlanInfoByLastId(id);
	}

	public void delete(plan p) {
		// TODO 自動生成されたメソッド・スタブ
		planDao.delete(p);
	}

	public List<plan> getPlanByState0(int state, int currentPage, int pageSize) {
		// TODO 自動生成されたメソッド・スタブ
		return planDao.getPlanByState0(state, currentPage, pageSize);
	}

	public List<plan> getPlanByState1(int state, int currentPage, int pageSize) {
		// TODO 自動生成されたメソッド・スタブ
		return planDao.getPlanByState1(state, currentPage, pageSize);
	}

	public List<plan> getPlanByState2(int state, int currentPage, int pageSize) {
		// TODO 自動生成されたメソッド・スタブ
		return planDao.getPlanByState2(state, currentPage, pageSize);
	}

	public List<plan> getPlanByState3(int state, int currentPage, int pageSize) {
		// TODO 自動生成されたメソッド・スタブ
		return planDao.getPlanByState3(state, currentPage, pageSize);
	}

	public List<plan> getPlanByState4(int state, int currentPage, int pageSize) {
		// TODO 自動生成されたメソッド・スタブ
		return planDao.getPlanByState4(state, currentPage, pageSize);
	}

	public int getPlanCountByState0() {
		// TODO 自動生成されたメソッド・スタブ
		return planDao.getPlanCountByState0();
	}

	public int getPlanCountByState1() {
		// TODO 自動生成されたメソッド・スタブ
		return planDao.getPlanCountByState1();
	}

	public int getPlanCountByState2() {
		// TODO 自動生成されたメソッド・スタブ
		return planDao.getPlanCountByState2();
	}

	public int getPlanCountByState3() {
		// TODO 自動生成されたメソッド・スタブ
		return planDao.getPlanCountByState3();
	}

	public int getPlanCountByState4() {
		// TODO 自動生成されたメソッド・スタブ
		return planDao.getPlanCountByState4();
	}


}
