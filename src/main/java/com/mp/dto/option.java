package com.mp.dto;

import java.util.List;

public class option {
	private String value;
	private String label;
	private List<String> vaList;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public List<String> getVaList() {
		return vaList;
	}

	public void setVaList(List<String> vaList) {
		this.vaList = vaList;
	}

}
