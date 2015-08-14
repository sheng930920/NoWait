package com.nowait.app;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 静态变量的存放
 * */

public class Constant {
	
	public static double mlat;
	public static double mlon;

	// 排行榜中状态数据
	public static String[] statu = new String[] { "全部状态", "支持手机取号", "过号不作废",
			"支持手机点菜" };

	// ip地址
	public static String Url = "http://192.168.1.105";

	 // 保留小数点后1位
	static DecimalFormat df2 = new DecimalFormat("##0.0");
	
	private static final double EARTH_RADIUS = 6378137;

	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	// 通过经纬度计算两个点的之间的距离
	public static String getDistance(double lng1, double lat1) {
		
		double radLat1 = rad(lat1);
		double radLat2 = rad(mlat);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(mlon);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10000;
		double d = s/1000;
		String e =df2.format(d);
		return e;
	}


	// 校验Tag只能是数字,英文字母和中文
	public static boolean isValidTagAndAlias(String s) {
		Pattern p = Pattern.compile("^[\u4E00-\u9FA50-9a-zA-Z_-]{0,}$");
		Matcher m = p.matcher(s);
		return m.matches();
	}

	// 过滤出数字
	public static String filterNumber(String number) {
		number = number.replaceAll("[^(0-9)]", "");
		return number;
	}

	// 过滤出字母
	public static String filterAlphabet(String alph) {
		alph = alph.replaceAll("[^(A-Za-z)]", "");
		return alph;
	}

	// 过滤出字母、数字和中文
	public static String filter(String character) {
		character = character.replaceAll("[^(a-zA-Z0-9\\u4e00-\\u9fa5)]", "");
		return character;
	}

	public static boolean isConnected(Context context) {
		ConnectivityManager conn = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = conn.getActiveNetworkInfo();
		return (info != null && info.isConnected());
	}

}
