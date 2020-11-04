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
		neDao.insert(ne);
	}

	public List<ne> getHomeDataByNe_everday(String tenpo, String time_s, String time_e) {
		return neDao.getHomeDataByNe_everday(tenpo, time_s, time_e);
	}

	public List<ne> getMeisaiDataByNe(String tenpo, String time_s, String time_e, String jyuchu_name, String denpyono,
			String haitatsukanryo_chk, String haitatsukanryo_s, String haitatsukanryo_e, int current, int pageCount) {
		return neDao.getMeisaiDataByNe(tenpo, time_s, time_e, jyuchu_name, denpyono, haitatsukanryo_chk,
				haitatsukanryo_s, haitatsukanryo_e, current, pageCount);
	}

	public int getMeisaiDataByNe_count(String tenpo, String time_s, String time_e, String jyuchu_name, String denpyono,
			String haitatsukanryo_chk, String haitatsukanryo_s, String haitatsukanryo_e) {
		return neDao.getMeisaiDataByNe_count(tenpo, time_s, time_e, jyuchu_name, denpyono, haitatsukanryo_chk,
				haitatsukanryo_s, haitatsukanryo_e);
	}

	public int getMeisaiDataByNe_goukei(String tenpo, String time_s, String time_e, String jyuchu_name, String denpyono,
			String haitatsukanryo_chk, String haitatsukanryo_s, String haitatsukanryo_e) {
		return neDao.getMeisaiDataByNe_goukei(tenpo, time_s, time_e, jyuchu_name, denpyono, haitatsukanryo_chk,
				haitatsukanryo_s, haitatsukanryo_e);
	}

	public void deleteMeisaiByIds(String[] ids) {
		neDao.deleteMeisaiByIds(ids);
	}

	public void UpdateTsuisekiData(List<ne> ne) {
		// TODO 自動生成されたメソッド・スタブ
		neDao.UpdateTsuisekiData(ne);
	}

}
