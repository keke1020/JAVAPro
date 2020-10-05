package com.mp.dto;

public class RakutenYahooApiDate {
	private String itemName;
	private String itemUrl;
	private String[] mediumImageUrls;
	private String itemPrice;
	private String shopName;

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemUrl() {
		return itemUrl;
	}

	public void setItemUrl(String itemUrl) {
		this.itemUrl = itemUrl;
	}

	public String[] getMediumImageUrls() {
		return mediumImageUrls;
	}

	public void setMediumImageUrls(String[] mediumImageUrls) {
		this.mediumImageUrls = mediumImageUrls;
	}

	public String getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(String itemPrice) {
		this.itemPrice = itemPrice;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

}
