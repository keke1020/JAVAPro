package com.mp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mp.dao.priceDao;
import com.mp.entity.ne_hikaku;
import com.mp.entity.price;
import com.mp.service.priceService;

@Service
public class priceServiceImpl implements priceService {
	@Autowired
	private priceDao priceDao;

	public void insert(List<price> price) {
		// TODO 自動生成されたメソッド・スタブ
		priceDao.insert(price);
	}

	public void deleteByToday(String tenpo) {
		// TODO 自動生成されたメソッド・スタブ
		priceDao.deleteByToday(tenpo);
	}

	@Override
	public void deleteByDataTime(String tenpo, String time) {
		// TODO 自動生成されたメソッド・スタブ
		priceDao.deleteByDataTime(tenpo, time);
	}

	public List<price> getPriceData(String date, String tenpo, int show, int current, int pageCount) {
		// TODO 自動生成されたメソッド・スタブ
		return priceDao.getPriceData(date, tenpo, show, current, pageCount);
	}

	public int getPriceData_total(String date, String tenpo, int show) {
		// TODO 自動生成されたメソッド・スタブ
		return priceDao.getPriceData_total(date, tenpo, show);
	}

	@Override
	public List<price> getDistinctCodeByDate(String start, String end,int show) {
		// TODO 自動生成されたメソッド・スタブ
		return priceDao.getDistinctCodeByDate(start, end,show);
	}

	@Override
	public List<price> getDistinctCodeByDate_ne(String start, String end) {
		// TODO 自動生成されたメソッド・スタブ
		return priceDao.getDistinctCodeByDate_ne(start, end);
	}

	@Override
	public price getHikakuDataByCodeAndDate(String tenpo, String code, String start, String end) {
		// TODO 自動生成されたメソッド・スタブ
		return priceDao.getHikakuDataByCodeAndDate(tenpo, code, start, end);
	}

	@Override
	public List<ne_hikaku> getHikakuDataByDate(String kakaku1, String start, String end) {
		// TODO 自動生成されたメソッド・スタブ
		return priceDao.getHikakuDataByDate(kakaku1, start, end);
	}

	@Override
	public List<ne_hikaku> getHikakuDataByDate_ne(String start, String end) {
		// TODO 自動生成されたメソッド・スタブ
		return priceDao.getHikakuDataByDate_ne(start, end);
	}

	@Override
	public String getDcodeByLocation(String code) {
		// TODO 自動生成されたメソッド・スタブ
		return priceDao.getDcodeByLocation(code);
	}

	@Override
	public List<price> getDistinctCodeDataByDate(String start, String end, int show) {
		// TODO 自動生成されたメソッド・スタブ
		return priceDao.getDistinctCodeDataByDate(start, end, show);
	}

	@Override
	public void deleteByUuid(String uuid) {
		// TODO 自動生成されたメソッド・スタブ
		priceDao.deleteByUuid(uuid);
	}

	@Override
	public int getCountByUuid(String uuid) {
		// TODO 自動生成されたメソッド・スタブ
		return priceDao.getCountByUuid(uuid);
	}

	@Override
	public void backByUuid(String uuid) {
		// TODO 自動生成されたメソッド・スタブ
		priceDao.backByUuid(uuid);
	}

}
