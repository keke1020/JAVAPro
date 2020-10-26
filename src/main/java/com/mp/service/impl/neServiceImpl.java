package com.mp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mp.dao.neDao;
import com.mp.entity.ne;
import com.mp.service.neService;

@Service
public class neServiceImpl implements neService {
	@Autowired
	private neDao neDao;

	public void insert(List<ne> ne) {
		// TODO 自動生成されたメソッド・スタブ
		neDao.insert(ne);
	}

	public List<ne> getHomeDataByNe_everday(String tenpo, String time_s, String time_e) {
		// TODO 自動生成されたメソッド・スタブ
		return neDao.getHomeDataByNe_everday(tenpo, time_s, time_e);
	}

	public List<ne> getMeisaiDataByNe(String tenpo, String time_s, String time_e, String jyuchu_name, int current, int pageCount) {
		// TODO 自動生成されたメソッド・スタブ
		return neDao.getMeisaiDataByNe(tenpo, time_s, time_e, jyuchu_name, current, pageCount);
	}

	public int getMeisaiDataByNe_count(String tenpo, String time_s, String time_e, String jyuchu_name) {
		// TODO 自動生成されたメソッド・スタブ
		return neDao.getMeisaiDataByNe_count(tenpo, time_s, time_e, jyuchu_name);
	}

	public int getMeisaiDataByNe_goukei(String tenpo, String time_s, String time_e, String jyuchu_name, int current,
			int pageCount) {
		// TODO 自動生成されたメソッド・スタブ
		return neDao.getMeisaiDataByNe_goukei(tenpo, time_s, time_e, jyuchu_name, current, pageCount);
	}

}
