package com.mp.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class plan_info {
	private int ID;
	private int state; // 0: 待ちうけ 1:未着手
	private int parentId;
	private int user_id;
	private String text1;
	private String text2;
	private String updater;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Tokyo")
	private Date updatetime;

	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Tokyo")
	private Date updatetime2;

	private String code;
	private String info;
	private boolean backFlag;
	private int backShowFlag;

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getText1() {
		return text1;
	}

	public void setText1(String text1) {
		this.text1 = text1;
	}

	public String getText2() {
		return text2;
	}

	public void setText2(String text2) {
		this.text2 = text2;
	}

	public String getUpdater() {
		return updater;
	}

	public void setUpdater(String updater) {
		this.updater = updater;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public Date getUpdatetime2() {
		return updatetime2;
	}

	public void setUpdatetime2(Date updatetime2) {
		this.updatetime2 = updatetime2;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public boolean isBackFlag() {
		return backFlag;
	}

	public void setBackFlag(boolean backFlag) {
		this.backFlag = backFlag;
	}

	public int getBackShowFlag() {
		return backShowFlag;
	}

	public void setBackShowFlag(int backShowFlag) {
		this.backShowFlag = backShowFlag;
	}




}
