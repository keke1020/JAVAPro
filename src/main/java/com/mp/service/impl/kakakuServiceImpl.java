package com.mp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mp.dao.kakakuDao;
import com.mp.entity.kakaku;
import com.mp.service.kakakuService;
@Service
public class kakakuServiceImpl implements kakakuService {
	@Autowired
	private kakakuDao kakakuDao;

	public List<kakaku> getKakaku(int offset, int limit, String sort) {
		// TODO 自動生成されたメソッド・スタブ
		return kakakuDao.getKakaku(offset, limit, sort);
	}

	public int getTotal() {
		// TODO 自動生成されたメソッド・スタブ
		return kakakuDao.getTotal();
	}

}
