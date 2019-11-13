package com.mp.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class list1 {
	private int ID;
	private String lockuser;
	private Date locktime;
	private String updater;
	private Date updatetime;
	private String code;
	private String sub_code;
	private String img;
	private String item_name;
	private int order_count;
	private int inspect_count;
	private Date departure;
	private Date arrival;
	private double unit_ch;
	private double total_ch;
	private int unit_jp;
	private int total_jp;
	private double rate;
	private int fba_stock;
	private int ne_stock;
	private String container;
	private String box_num;
	private int box_count;
	private double kg;
	private double one_m3;
	private double all_m3;
	private String material;
	private String material_ch;
	private double height;
	private double width;
	private double depth;

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getLockuser() {
		return lockuser;
	}

	public void setLockuser(String lockuser) {
		this.lockuser = lockuser;
	}

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "Asia/Tokyo")
	public Date getLocktime() {
		return locktime;
	}

	public void setLocktime(Date locktime) {
		this.locktime = locktime;
	}

	public String getUpdater() {
		return updater;
	}

	public void setUpdater(String updater) {
		this.updater = updater;
	}

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "Asia/Tokyo")
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

	public int getOrder_count() {
		return order_count;
	}

	public void setOrder_count(int order_count) {
		this.order_count = order_count;
	}

	public int getInspect_count() {
		return inspect_count;
	}

	public void setInspect_count(int inspect_count) {
		this.inspect_count = inspect_count;
	}

	@JsonFormat(pattern="yyyy-MM-dd", timezone = "Asia/Tokyo")
	public Date getDeparture() {
		return departure;
	}

	public void setDeparture(Date departure) {
		this.departure = departure;
	}

	@JsonFormat(pattern="yyyy-MM-dd", timezone = "Asia/Tokyo")
	public Date getArrival() {
		return arrival;
	}

	public void setArrival(Date arrival) {
		this.arrival = arrival;
	}

	public double getUnit_ch() {
		return unit_ch;
	}

	public void setUnit_ch(double unit_ch) {
		this.unit_ch = unit_ch;
	}

	public double getTotal_ch() {
		return total_ch;
	}

	public void setTotal_ch(double total_ch) {
		this.total_ch = total_ch;
	}

	public int getUnit_jp() {
		return unit_jp;
	}

	public void setUnit_jp(int unit_jp) {
		this.unit_jp = unit_jp;
	}

	public int getTotal_jp() {
		return total_jp;
	}

	public void setTotal_jp(int total_jp) {
		this.total_jp = total_jp;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public int getFba_stock() {
		return fba_stock;
	}

	public void setFba_stock(int fba_stock) {
		this.fba_stock = fba_stock;
	}

	public int getNe_stock() {
		return ne_stock;
	}

	public void setNe_stock(int ne_stock) {
		this.ne_stock = ne_stock;
	}

	public String getContainer() {
		return container;
	}

	public void setContainer(String container) {
		this.container = container;
	}

	public String getBox_num() {
		return box_num;
	}

	public void setBox_num(String box_num) {
		this.box_num = box_num;
	}

	public int getBox_count() {
		return box_count;
	}

	public void setBox_count(int box_count) {
		this.box_count = box_count;
	}

	public double getKg() {
		return kg;
	}

	public void setKg(double kg) {
		this.kg = kg;
	}

	public double getOne_m3() {
		return one_m3;
	}

	public void setOne_m3(double one_m3) {
		this.one_m3 = one_m3;
	}

	public double getAll_m3() {
		return all_m3;
	}

	public void setAll_m3(double all_m3) {
		this.all_m3 = all_m3;
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

}
