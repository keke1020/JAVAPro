package com.mp.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class location {
	private int ID;
	private int router_index;
	private boolean chk;
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Tokyo")
	private Date update;
	private String code;
	private String name;
	private String tag;
	private String dcode;
	private String sw;
	private String sw2;
	private String kaisou;
	private String tana;
	private String ksize;
	private String sp;
	private String t_kbn;
	private String user;
	private int user_id;
	private String flag;
	private String moto;
	private String moto_old;
	private Integer zaiko;
	private Integer hikiate;
	private String yoyaku;
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Tokyo")
	private Date zaiko_update;
	private Integer sZaiko;
	private String sBikou;
	private String sTanto;
	private String img;

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public int getRouter_index() {
		return router_index;
	}

	public void setRouter_index(int router_index) {
		this.router_index = router_index;
	}

	public boolean isChk() {
		return chk;
	}

	public void setChk(boolean chk) {
		this.chk = chk;
	}

	public Date getUpdate() {
		return update;
	}

	public void setUpdate(Date update) {
		this.update = update;
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

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getDcode() {
		return dcode;
	}

	public void setDcode(String dcode) {
		this.dcode = dcode;
	}

	public String getSw() {
		return sw;
	}

	public void setSw(String sw) {
		this.sw = sw;
	}

	public String getSw2() {
		return sw2;
	}

	public void setSw2(String sw2) {
		this.sw2 = sw2;
	}

	public String getKaisou() {
		return kaisou;
	}

	public void setKaisou(String kaisou) {
		this.kaisou = kaisou;
	}

	public String getTana() {
		return tana;
	}

	public void setTana(String tana) {
		this.tana = tana;
	}

	public String getKsize() {
		return ksize;
	}

	public void setKsize(String ksize) {
		this.ksize = ksize;
	}

	public String getSp() {
		return sp;
	}

	public void setSp(String sp) {
		this.sp = sp;
	}

	public String getT_kbn() {
		return t_kbn;
	}

	public void setT_kbn(String t_kbn) {
		this.t_kbn = t_kbn;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getMoto() {
		return moto;
	}

	public void setMoto(String moto) {
		this.moto = moto;
	}

	public String getMoto_old() {
		return moto_old;
	}

	public void setMoto_old(String moto_old) {
		this.moto_old = moto_old;
	}

	public Integer getZaiko() {
		return zaiko;
	}

	public void setZaiko(Integer zaiko) {
		this.zaiko = zaiko;
	}

	public Integer getHikiate() {
		return hikiate;
	}

	public void setHikiate(Integer hikiate) {
		this.hikiate = hikiate;
	}

	public String getYoyaku() {
		return yoyaku;
	}

	public void setYoyaku(String yoyaku) {
		this.yoyaku = yoyaku;
	}

	public Date getZaiko_update() {
		return zaiko_update;
	}

	public void setZaiko_update(Date zaiko_update) {
		this.zaiko_update = zaiko_update;
	}

	public Integer getsZaiko() {
		return sZaiko;
	}

	public void setsZaiko(Integer sZaiko) {
		this.sZaiko = sZaiko;
	}

	public String getsBikou() {
		return sBikou;
	}

	public void setsBikou(String sBikou) {
		this.sBikou = sBikou;
	}

	public String getsTanto() {
		return sTanto;
	}

	public void setsTanto(String sTanto) {
		this.sTanto = sTanto;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

}
