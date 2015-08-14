package com.nowait.app;

public class Shop {

	public String imgSrc;
	public String title;
	public String type;
	public String money;
	public Location location;
	public int waitNum;
	public String id;

	public static class Location {
		public double lat;
		public double lng;
	}

	public String getImgSrc() {
		return imgSrc;
	}

	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public int getWaitNum() {
		return waitNum;
	}

	public void setWaitNum(int waitNum) {
		this.waitNum = waitNum;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
