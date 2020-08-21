package com.mp.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class syouhin {
	private int ID;
	private int updater_id;
	private String updater;

	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Tokyo")
	private Date updatetime;

	private String code;
	private String sub_code;
	private String img;
	private String item_name;
	private double unit_ch;
	private double rate;
	private double kg;
	private String material;
	private String material_ch;
	private double height;
	private double width;
	private double depth;
	private double doru;

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public int getUpdater_id() {
		return updater_id;
	}

	public void setUpdater_id(int updater_id) {
		this.updater_id = updater_id;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSub_code() {
		return sub_code;
	}

	public void setSub_code(String sub_code) {
		this.sub_code = sub_code;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getItem_name() {
		return item_name;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	public double getUnit_ch() {
		return unit_ch;
	}

	public void setUnit_ch(double unit_ch) {
		this.unit_ch = unit_ch;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public double getKg() {
		return kg;
	}

	public void setKg(double kg) {
		this.kg = kg;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getMaterial_ch() {
		return material_ch;
	}

	public void setMaterial_ch(String material_ch) {
		this.material_ch = material_ch;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getDepth() {
		return depth;
	}

	public void setDepth(double depth) {
		this.depth = depth;
	}

	public double getDoru() {
		return doru;
	}

	public void setDoru(double doru) {
		this.doru = doru;
	}
}
