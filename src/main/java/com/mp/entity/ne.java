package com.mp.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ne {
	private boolean chk;
	private int count; //件数
	private int jyuchu_denpyo_no;
	private String pic_siji_naiyou;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Tokyo")
	private Date jyuchu_bi;
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Tokyo")
	private Date syuka_kakutei_bi;
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Tokyo")
	private Date nouhinsyo_insatusiji_bi;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Tokyo")
	private Date nouhinsyo_insatuhakou_bi;
	private String jyuchu_jyotai_kbn;
	private String tenpo_denpyo_no;
	private String tenpo_code;
	private String jyuchu_tantou_code;
	private String jyuchu_name;
	private int syohin_kin;
	private int zei_kin;
	private int hasou_kin;
	private int tesuryo_kin;
	private int sonota_kin;
	private int point;
	private int goukei_kin;
	private String hasou_name;
	private String hasou_yubin_bangou;
	private String hasou_jyusyo;
	private String hasou_kbn;
	private String siharai_kbn;
	private String hasou_denpyo_no;
	private String haisoukaisya;
	private String bikou;
	private String jyuyou;
	private String jyuyou_check;
	private String jyuchu_tag;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Tokyo")
	private Date import_date;
	private int import_user;
	private String import_username;

	private String tsuisekiData;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Tokyo")
	private Date tsuisekiImportDate;
	private String sumi;
	private String todokesaki;
	private String kekka;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Tokyo")
	private Date haitatsukanryo;
	private String kosuu;
	private String syosai;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Tokyo")
	private Date syokaihannnou;
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Tokyo")
	private Date syukabi;
	private int tsuisekiImportUserId;
	private String tsuisekiImportUser;
	private String tsuisekiNo;

	private String code;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getJyuchu_denpyo_no() {
		return jyuchu_denpyo_no;
	}

	public void setJyuchu_denpyo_no(int jyuchu_denpyo_no) {
		this.jyuchu_denpyo_no = jyuchu_denpyo_no;
	}

	public String getPic_siji_naiyou() {
		return pic_siji_naiyou;
	}

	public void setPic_siji_naiyou(String pic_siji_naiyou) {
		this.pic_siji_naiyou = pic_siji_naiyou;
	}

	public Date getJyuchu_bi() {
		return jyuchu_bi;
	}

	public void setJyuchu_bi(Date jyuchu_bi) {
		this.jyuchu_bi = jyuchu_bi;
	}

	public Date getSyuka_kakutei_bi() {
		return syuka_kakutei_bi;
	}

	public void setSyuka_kakutei_bi(Date syuka_kakutei_bi) {
		this.syuka_kakutei_bi = syuka_kakutei_bi;
	}

	public Date getNouhinsyo_insatusiji_bi() {
		return nouhinsyo_insatusiji_bi;
	}

	public void setNouhinsyo_insatusiji_bi(Date nouhinsyo_insatusiji_bi) {
		this.nouhinsyo_insatusiji_bi = nouhinsyo_insatusiji_bi;
	}

	public Date getNouhinsyo_insatuhakou_bi() {
		return nouhinsyo_insatuhakou_bi;
	}

	public void setNouhinsyo_insatuhakou_bi(Date nouhinsyo_insatuhakou_bi) {
		this.nouhinsyo_insatuhakou_bi = nouhinsyo_insatuhakou_bi;
	}

	public String getJyuchu_jyotai_kbn() {
		return jyuchu_jyotai_kbn;
	}

	public void setJyuchu_jyotai_kbn(String jyuchu_jyotai_kbn) {
		this.jyuchu_jyotai_kbn = jyuchu_jyotai_kbn;
	}

	public String getTenpo_denpyo_no() {
		return tenpo_denpyo_no;
	}

	public void setTenpo_denpyo_no(String tenpo_denpyo_no) {
		this.tenpo_denpyo_no = tenpo_denpyo_no;
	}

	public String getTenpo_code() {
		return tenpo_code;
	}

	public void setTenpo_code(String tenpo_code) {
		this.tenpo_code = tenpo_code;
	}

	public String getJyuchu_tantou_code() {
		return jyuchu_tantou_code;
	}

	public void setJyuchu_tantou_code(String jyuchu_tantou_code) {
		this.jyuchu_tantou_code = jyuchu_tantou_code;
	}

	public String getJyuchu_name() {
		return jyuchu_name;
	}

	public void setJyuchu_name(String jyuchu_name) {
		this.jyuchu_name = jyuchu_name;
	}

	public int getSyohin_kin() {
		return syohin_kin;
	}

	public void setSyohin_kin(int syohin_kin) {
		this.syohin_kin = syohin_kin;
	}

	public int getZei_kin() {
		return zei_kin;
	}

	public void setZei_kin(int zei_kin) {
		this.zei_kin = zei_kin;
	}

	public int getHasou_kin() {
		return hasou_kin;
	}

	public void setHasou_kin(int hasou_kin) {
		this.hasou_kin = hasou_kin;
	}

	public int getTesuryo_kin() {
		return tesuryo_kin;
	}

	public void setTesuryo_kin(int tesuryo_kin) {
		this.tesuryo_kin = tesuryo_kin;
	}

	public int getSonota_kin() {
		return sonota_kin;
	}

	public void setSonota_kin(int sonota_kin) {
		this.sonota_kin = sonota_kin;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public int getGoukei_kin() {
		return goukei_kin;
	}

	public void setGoukei_kin(int goukei_kin) {
		this.goukei_kin = goukei_kin;
	}

	public String getHasou_name() {
		return hasou_name;
	}

	public void setHasou_name(String hasou_name) {
		this.hasou_name = hasou_name;
	}

	public String getHasou_yubin_bangou() {
		return hasou_yubin_bangou;
	}

	public void setHasou_yubin_bangou(String hasou_yubin_bangou) {
		this.hasou_yubin_bangou = hasou_yubin_bangou;
	}

	public String getHasou_jyusyo() {
		return hasou_jyusyo;
	}

	public void setHasou_jyusyo(String hasou_jyusyo) {
		this.hasou_jyusyo = hasou_jyusyo;
	}

	public String getHasou_kbn() {
		return hasou_kbn;
	}

	public void setHasou_kbn(String hasou_kbn) {
		this.hasou_kbn = hasou_kbn;
	}

	public String getSiharai_kbn() {
		return siharai_kbn;
	}

	public void setSiharai_kbn(String siharai_kbn) {
		this.siharai_kbn = siharai_kbn;
	}

	public String getHasou_denpyo_no() {
		return hasou_denpyo_no;
	}

	public void setHasou_denpyo_no(String hasou_denpyo_no) {
		this.hasou_denpyo_no = hasou_denpyo_no;
	}

	public String getBikou() {
		return bikou;
	}

	public void setBikou(String bikou) {
		this.bikou = bikou;
	}

	public String getJyuyou() {
		return jyuyou;
	}

	public void setJyuyou(String jyuyou) {
		this.jyuyou = jyuyou;
	}

	public String getJyuyou_check() {
		return jyuyou_check;
	}

	public void setJyuyou_check(String jyuyou_check) {
		this.jyuyou_check = jyuyou_check;
	}

	public String getJyuchu_tag() {
		return jyuchu_tag;
	}

	public void setJyuchu_tag(String jyuchu_tag) {
		this.jyuchu_tag = jyuchu_tag;
	}

	public Date getImport_date() {
		return import_date;
	}

	public void setImport_date(Date import_date) {
		this.import_date = import_date;
	}

	public int getImport_user() {
		return import_user;
	}

	public void setImport_user(int import_user) {
		this.import_user = import_user;
	}

	public String getImport_username() {
		return import_username;
	}

	public void setImport_username(String import_username) {
		this.import_username = import_username;
	}

	public boolean isChk() {
		return chk;
	}

	public void setChk(boolean chk) {
		this.chk = chk;
	}

	public String getSumi() {
		return sumi;
	}

	public void setSumi(String sumi) {
		this.sumi = sumi;
	}

	public String getTodokesaki() {
		return todokesaki;
	}

	public void setTodokesaki(String todokesaki) {
		this.todokesaki = todokesaki;
	}

	public String getKekka() {
		return kekka;
	}

	public void setKekka(String kekka) {
		this.kekka = kekka;
	}

	public Date getHaitatsukanryo() {
		return haitatsukanryo;
	}

	public void setHaitatsukanryo(Date haitatsukanryo) {
		this.haitatsukanryo = haitatsukanryo;
	}

	public Date getSyukabi() {
		return syukabi;
	}

	public void setSyukabi(Date syukabi) {
		this.syukabi = syukabi;
	}

	public String getKosuu() {
		return kosuu;
	}

	public void setKosuu(String kosuu) {
		this.kosuu = kosuu;
	}

	public String getSyosai() {
		return syosai;
	}

	public void setSyosai(String syosai) {
		this.syosai = syosai;
	}

	public Date getSyokaihannnou() {
		return syokaihannnou;
	}

	public void setSyokaihannnou(Date syokaihannnou) {
		this.syokaihannnou = syokaihannnou;
	}

	public Date getTsuisekiImportDate() {
		return tsuisekiImportDate;
	}

	public void setTsuisekiImportDate(Date tsuisekiImportDate) {
		this.tsuisekiImportDate = tsuisekiImportDate;
	}

	public String getHaisoukaisya() {
		return haisoukaisya;
	}

	public void setHaisoukaisya(String haisoukaisya) {
		this.haisoukaisya = haisoukaisya;
	}

	public int getTsuisekiImportUserId() {
		return tsuisekiImportUserId;
	}

	public void setTsuisekiImportUserId(int tsuisekiImportUserId) {
		this.tsuisekiImportUserId = tsuisekiImportUserId;
	}

	public String getTsuisekiImportUser() {
		return tsuisekiImportUser;
	}

	public void setTsuisekiImportUser(String tsuisekiImportUser) {
		this.tsuisekiImportUser = tsuisekiImportUser;
	}

	public String getTsuisekiData() {
		return tsuisekiData;
	}

	public void setTsuisekiData(String tsuisekiData) {
		this.tsuisekiData = tsuisekiData;
	}

	public String getTsuisekiNo() {
		return tsuisekiNo;
	}

	public void setTsuisekiNo(String tsuisekiNo) {
		this.tsuisekiNo = tsuisekiNo;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}



}
