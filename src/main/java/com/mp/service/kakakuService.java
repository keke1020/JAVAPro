package com.mp.service;

import java.util.List;

import com.mp.entity.kakaku;

public interface kakakuService {
	List<kakaku> getKakaku(int offset, int limit, String sort);
	int getTotal();
}
