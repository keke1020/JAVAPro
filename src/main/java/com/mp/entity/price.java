package com.mp.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class price {
	private int ID;
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Tokyo")
	private Date dataTime;
	private String tenpo_code;
	private String code;
	private String name;
	private String postage_set;
	private String postage; //発送方法
	private String price;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Tokyo")
	private Date import_date;
	private int import_userId;
	private String import_user;
	private String uuid;

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public Date getDataTime() {
		return dataTime;
	}

	public void setDataTime(Date dataTime) {
		this.dataTime = dataTime;
	}

	public String getTenpo_code() {
		return tenpo_code;
	}

	public void setTenpo_code(String tenpo_code) {
		this.tenpo_code = tenpo_code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPostage_set() {
		return postage_set;
	}

	public void setPostage_set(String postage_set) {
		this.postage_set = postage_set;
	}

	public String getPostage() {
		return postage;
	}

	public void setPostage(String postage) {
		this.postage = postage;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public Date getImport_date() {
		return import_date;
	}

	public void setImport_date(Date import_date) {
		this.import_date = import_date;
	}

	public int getImport_userId() {
		return import_userId;
	}

	public void setImport_userId(int import_userId) {
		this.import_userId = import_userId;
	}

	public String getImport_user() {
		return import_user;
	}

	public void setImport_user(String import_user) {
		this.import_user = import_user;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

}
