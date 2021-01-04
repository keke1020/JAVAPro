package com.mp.entity;

public class user {
	private String ID;
	private String realname;
	private String password;
	private int adminFlag;
	private int tenpoKanriId;
	private int plan_contr;
	private int plan_priv;
	private int todo_priv1;
	private int todo_priv2;
	private int ne_upload;
	private int ne_delete;
	private int dazaifu_wh_see;
	private int nagoya_wh_see;

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getAdminFlag() {
		return adminFlag;
	}

	public void setAdminFlag(int adminFlag) {
		this.adminFlag = adminFlag;
	}

	public int getTenpoKanriId() {
		return tenpoKanriId;
	}

	public void setTenpoKanriId(int tenpoKanriId) {
		this.tenpoKanriId = tenpoKanriId;
	}

	public int getPlan_contr() {
		return plan_contr;
	}

	public void setPlan_contr(int plan_contr) {
		this.plan_contr = plan_contr;
	}

	public int getPlan_priv() {
		return plan_priv;
	}

	public void setPlan_priv(int plan_priv) {
		this.plan_priv = plan_priv;
	}

	public int getTodo_priv1() {
		return todo_priv1;
	}

	public void setTodo_priv1(int todo_priv1) {
		this.todo_priv1 = todo_priv1;
	}

	public int getTodo_priv2() {
		return todo_priv2;
	}

	public void setTodo_priv2(int todo_priv2) {
		this.todo_priv2 = todo_priv2;
	}

	public int getNe_upload() {
		return ne_upload;
	}

	public void setNe_upload(int ne_upload) {
		this.ne_upload = ne_upload;
	}

	public int getNe_delete() {
		return ne_delete;
	}

	public void setNe_delete(int ne_delete) {
		this.ne_delete = ne_delete;
	}

	public int getDazaifu_wh_see() {
		return dazaifu_wh_see;
	}

	public void setDazaifu_wh_see(int dazaifu_wh_see) {
		this.dazaifu_wh_see = dazaifu_wh_see;
	}

	public int getNagoya_wh_see() {
		return nagoya_wh_see;
	}

	public void setNagoya_wh_see(int nagoya_wh_see) {
		this.nagoya_wh_see = nagoya_wh_see;
	}

}
