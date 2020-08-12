package com.mp.service;

import java.util.List;

import com.mp.entity.zaikorireki;

public interface zaikorirekiService {
	List<zaikorireki> getShiGuRirekiByView(String condition, int count);
	void insert(zaikorireki z);
	List<zaikorireki> getRirekiByView(String condition, int count);
}
