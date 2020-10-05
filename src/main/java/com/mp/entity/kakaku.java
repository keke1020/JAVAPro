package com.mp.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class kakaku {
	private int ID;
	private boolean chk;
	private String code;
	private String name;
	private int genka;
	private String haiso;
	private String haiso_short;
	private int raku_price;
	private int raku_sale;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Tokyo")
	private Date raku_sale_limit;
	private int yahoo_price;
	private int yahoo_sale;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Tokyo")
	private Date yahoo_sale_limit;
	private String memo;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Tokyo")
	private Date update;
	private String tanto;
	private int sale_kagen;
	private int syobun_kagen;
	private int syobun_kagen_;
	private int haiso_ryo;
	private String h_tzaiko;// 在庫数
	private String h_yzaiko;// 予約在庫
	private boolean hasEda;// 枝番あり
	private String kagen1;// SALE下限
	private String kagen2_goukei;// 処分下限
	private int uploadFlag;

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public boolean isChk() {
		return chk;
	}

	public void setChk(boolean chk) {
		this.chk = chk;
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

	public int getGenka() {
		return genka;
	}

	public void setGenka(int genka) {
		this.genka = genka;
	}

	public String getHaiso() {
		return haiso;
	}

	public void setHaiso(String haiso) {
		this.haiso = haiso;
	}

	public String getHaiso_short() {
		return haiso_short;
	}

	public void setHaiso_short(String haiso_short) {
		this.haiso_short = haiso_short;
	}

	public int getRaku_price() {
		return raku_price;
	}

	public void setRaku_price(int raku_price) {
		this.raku_price = raku_price;
	}

	public int getRaku_sale() {
		return raku_sale;
	}

	public void setRaku_sale(int raku_sale) {
		this.raku_sale = raku_sale;
	}

	public Date getRaku_sale_limit() {
		return raku_sale_limit;
	}

	public void setRaku_sale_limit(Date raku_sale_limit) {
		this.raku_sale_limit = raku_sale_limit;
	}

	public int getYahoo_price() {
		return yahoo_price;
	}

	public void setYahoo_price(int yahoo_price) {
		this.yahoo_price = yahoo_price;
	}

	public int getYahoo_sale() {
		return yahoo_sale;
	}

	public void setYahoo_sale(int yahoo_sale) {
		this.yahoo_sale = yahoo_sale;
	}

	public Date getYahoo_sale_limit() {
		return yahoo_sale_limit;
	}

	public void setYahoo_sale_limit(Date yahoo_sale_limit) {
		this.yahoo_sale_limit = yahoo_sale_limit;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Date getUpdate() {
		return update;
	}

	public void setUpdate(Date update) {
		this.update = update;
	}

	public String getTanto() {
		return tanto;
	}

	public void setTanto(String tanto) {
		this.tanto = tanto;
	}

	public int getSale_kagen() {
		return sale_kagen;
	}

	public void setSale_kagen(int sale_kagen) {
		this.sale_kagen = sale_kagen;
	}

	public int getSyobun_kagen() {
		return syobun_kagen;
	}

	public void setSyobun_kagen(int syobun_kagen) {
		this.syobun_kagen = syobun_kagen;
	}

	public int getSyobun_kagen_() {
		return syobun_kagen_;
	}

	public void setSyobun_kagen_(int syobun_kagen_) {
		this.syobun_kagen_ = syobun_kagen_;
	}

	public int getHaiso_ryo() {
		return haiso_ryo;
	}

	public void setHaiso_ryo(int haiso_ryo) {
		this.haiso_ryo = haiso_ryo;
	}

	public String getH_tzaiko() {
		return h_tzaiko;
	}

	public void setH_tzaiko(String h_tzaiko) {
		this.h_tzaiko = h_tzaiko;
	}

	public String getH_yzaiko() {
		return h_yzaiko;
	}

	public void setH_yzaiko(String h_yzaiko) {
		this.h_yzaiko = h_yzaiko;
	}

	public boolean isHasEda() {
		return hasEda;
	}

	public void setHasEda(boolean hasEda) {
		this.hasEda = hasEda;
	}

	public String getKagen1() {
		return kagen1;
	}

	public void setKagen1(String kagen1) {
		this.kagen1 = kagen1;
	}

	public String getKagen2_goukei() {
		return kagen2_goukei;
	}

	public void setKagen2_goukei(String kagen2_goukei) {
		this.kagen2_goukei = kagen2_goukei;
	}

	public int getUploadFlag() {
		return uploadFlag;
	}

	public void setUploadFlag(int uploadFlag) {
		this.uploadFlag = uploadFlag;
	}

}
