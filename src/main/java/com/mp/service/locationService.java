package com.mp.service;

import java.util.List;

import com.mp.entity.location;

public interface locationService {
	List<location> getlocation(String orderSC, int offset, int limit);
	int getTotal();
}
