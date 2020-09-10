package com.mp.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mp.dto.option;

public class todo {
	private int ID;
	private String title;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Tokyo")
	private Date update;
	private String user;
	private String tanto;
	private String[] tanto_id;
	private String status;
	private int end;
	private String endFlag;
	private String files;
	private List<option> files_;
	private String comment;
	private String comment_input;
	private String[] comment_;
	private int comment_count;
	private String memo;
	private String rireki;
	private String[] rireki_;
	private boolean isInTantou;

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getUpdate() {
		return update;
	}

	public void setUpdate(Date update) {
		this.update = update;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getTanto() {
		return tanto;
	}

	public void setTanto(String tanto) {
		this.tanto = tanto;
	}

	public String[] getTanto_id() {
		return tanto_id;
	}

	public void setTanto_id(String[] tanto_id) {
		this.tanto_id = tanto_id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public String getEndFlag() {
		return endFlag;
	}

	public void setEndFlag(String endFlag) {
		this.endFlag = endFlag;
	}

	public String getFiles() {
		return files;
	}

	public void setFiles(String files) {
		this.files = files;
	}

	public List<option> getFiles_() {
		return files_;
	}

	public void setFiles_(List<option> files_) {
		this.files_ = files_;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getComment_input() {
		return comment_input;
	}

	public void setComment_input(String comment_input) {
		this.comment_input = comment_input;
	}

	public String[] getComment_() {
		return comment_;
	}

	public void setComment_(String[] comment_) {
		this.comment_ = comment_;
	}

	public int getComment_count() {
		return comment_count;
	}

	public void setComment_count(int comment_count) {
		this.comment_count = comment_count;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getRireki() {
		return rireki;
	}

	public void setRireki(String rireki) {
		this.rireki = rireki;
	}

	public String[] getRireki_() {
		return rireki_;
	}

	public void setRireki_(String[] rireki_) {
		this.rireki_ = rireki_;
	}

	public boolean isInTantou() {
		return isInTantou;
	}

	public void setInTantou(boolean isInTantou) {
		this.isInTantou = isInTantou;
	}

}
