package com.nowait.app;

import java.util.List;

public class ShopDetail {

	public String imgSrc;
	public String title;
	public boolean wait;
	public List<Table> table;
	public Location location;
	public String type;
	public String money;
	public String address;
	public String openTime;
	public String discount;
	public String tel;
	public boolean storeBranch;
	public String user;

	public static class Table {
		public String name;
		public String code;
		public String desc;
		public String average;
	}

	public static class Location {
		public float lat;
		public float lng;
	}

}
