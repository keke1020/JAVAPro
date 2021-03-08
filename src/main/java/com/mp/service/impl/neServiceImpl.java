package com.mp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mp.dao.neDao;
import com.mp.entity.ne;
import com.mp.entity.ne_goods;
import com.mp.entity.ne_hikaku;
import com.mp.entity.ne_meisai;
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
			String haitatsukanryo_chk, String haitatsukanryo_chk2, String haitatsukanryo_s, String haitatsukanryo_e,
			String kekka, String jyuchu_haisokaisya,
			int current, int pageCount) {
		return neDao.getMeisaiDataByNe(tenpo, time_s, time_e, jyuchu_name, denpyono, haitatsukanryo_chk,
				haitatsukanryo_chk2,
				haitatsukanryo_s, haitatsukanryo_e, kekka, jyuchu_haisokaisya, current, pageCount);
	}

	public int getMeisaiDataByNe_count(String tenpo, String time_s, String time_e, String jyuchu_name, String denpyono,
			String haitatsukanryo_chk, String haitatsukanryo_chk2, String haitatsukanryo_s, String haitatsukanryo_e,
			String kekka, String jyuchu_haisokaisya) {
		return neDao.getMeisaiDataByNe_count(tenpo, time_s, time_e, jyuchu_name, denpyono, haitatsukanryo_chk,
				haitatsukanryo_chk2,
				haitatsukanryo_s, haitatsukanryo_e, kekka, jyuchu_haisokaisya);
	}

	public int getMeisaiDataByNe_goukei(String tenpo, String time_s, String time_e, String jyuchu_name, String denpyono,
			String haitatsukanryo_chk, String haitatsukanryo_chk2, String haitatsukanryo_s, String haitatsukanryo_e,
			String kekka, String jyuchu_haisokaisya) {
		return neDao.getMeisaiDataByNe_goukei(tenpo, time_s, time_e, jyuchu_name, denpyono, haitatsukanryo_chk,
				haitatsukanryo_chk2,
				haitatsukanryo_s, haitatsukanryo_e, kekka, jyuchu_haisokaisya);
	}

	@Override
	public Integer getHomedata_goukei(String tenpo, String start, String end) {
		// TODO 自動生成されたメソッド・スタブ
		return neDao.getHomedata_goukei(tenpo, start, end);
	}

	@Override
	public ne getHomedata_keisan(String tenpo, String start, String end) {
		// TODO 自動生成されたメソッド・スタブ
		return neDao.getHomedata_keisan(tenpo, start, end);
	}

	public void deleteMeisaiByIds(String[] ids) {
		neDao.deleteMeisaiByIds(ids);
	}

	public void UpdateTsuisekiData(List<ne> ne) {
		// TODO 自動生成されたメソッド・スタブ
		neDao.UpdateTsuisekiData(ne);
	}

	@Override
	public List<ne> getHomeTenpoinfoDataByNe_month(String tenpo, String start, String end, int current, int pageCount) {
		// TODO 自動生成されたメソッド・スタブ
		return neDao.getHomeTenpoinfoDataByNe_month(tenpo, start, end, current, pageCount);
	}

	@Override
	public int getHomeTenpoinfoCountDataByNe_month(String tenpo, String start, String end) {
		// TODO 自動生成されたメソッド・スタブ
		return neDao.getHomeTenpoinfoCountDataByNe_month(tenpo, start, end);
	}

	@Override
	public void deleteByDenpyono(List<Integer> no) {
		// TODO 自動生成されたメソッド・スタブ
		neDao.deleteByDenpyono(no);
	}

	@Override
	public void insertNeMeisai(List<ne_meisai> nm) {
		// TODO 自動生成されたメソッド・スタブ
		neDao.insertNeMeisai(nm);
	}

	@Override
	public void insertGoods(List<ne_goods> ng) {
		// TODO 自動生成されたメソッド・スタブ
		neDao.insertGoods(ng);
	}

	@Override
	public List<ne_meisai> getHomeTenpoinfo2DataByNe_month(String tenpo, String start, String end, int current, int pageCount) {
		// TODO 自動生成されたメソッド・スタブ
		return neDao.getHomeTenpoinfo2DataByNe_month(tenpo, start, end, current, pageCount);
	}

	@Override
	public int getHomeTenpoinfo2CountDataByNe_month(String tenpo, String start, String end) {
		// TODO 自動生成されたメソッド・スタブ
		return neDao.getHomeTenpoinfo2CountDataByNe_month(tenpo, start, end);
	}

	@Override
	public List<ne_hikaku> getHomeKakuTenpoinfoDataByNe(List<String> codes, String start, String end) {
		// TODO 自動生成されたメソッド・スタブ
		return neDao.getHomeKakuTenpoinfoDataByNe(codes, start, end);
	}

	@Override
	public List<String> getHomeKakuTenpoinfoCodeDataByNe(List<String> codes,String start, String end) {
		// TODO 自動生成されたメソッド・スタブ
		return neDao.getHomeKakuTenpoinfoCodeDataByNe(codes, start, end);
	}

	@Override
	public List<ne> getHomeKakuTenpoinfoDataByNe2(List<String> codes, String start, String end) {
		// TODO 自動生成されたメソッド・スタブ
		return neDao.getHomeKakuTenpoinfoDataByNe2(codes, start, end);
	}

	@Override
	public List<ne> getHomedata(String tenpo, String beginDateStr, String endDateStr) {
		// TODO 自動生成されたメソッド・スタブ
		return neDao.getHomedata(tenpo, beginDateStr, endDateStr);
	}





}
