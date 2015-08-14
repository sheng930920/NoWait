package com.nowait.service;


import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.nowait.activity.HomeTab;
import com.nowait.app.Constant;
import com.nowait.app.Myapplication;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class LocationService extends Service {

	private LocationClient mLocationClient;
	private LocationClientOption option;
	public MyLocationListener mMyLocationListener;
	Myapplication myapp;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}


	@Override
	public void onStart(Intent intent, int startId) {
		myapp = (Myapplication) getApplicationContext();
		mLocationClient = new LocationClient(this.getApplicationContext());
		mMyLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mMyLocationListener);
		initLocation();
		mLocationClient.start();// 定位SDK
		// start之后会默认发起一次定位请求，开发者无须判断isstart并主动调用request
		mLocationClient.requestLocation();

	}

	private void initLocation() {
		option = new LocationClientOption();
		option.setOpenGps(true); // 是否打开GPS
		option.setLocationMode(LocationMode.Hight_Accuracy);// 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
		option.setCoorType("gcj02");// 可选，默认gcj02，设置返回的定位结果坐标系，bd09ll
		option.setScanSpan(2000);// 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
		option.setIsNeedAddress(true);// 可选，设置是否需要地址信息，默认不需要
		option.setOpenGps(true);// 可选，默认false,设置是否使用gps
		option.setIsNeedAddress(true);// 返回的定位结果包含地址信息
		option.setLocationNotify(true);// 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
		option.setIgnoreKillProcess(true);// 可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
		mLocationClient.setLocOption(option);
	}

	/**
	 * 实现实时位置回调监听
	 */
	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// Receive Location
			StringBuffer sb = new StringBuffer(256);
			sb.append("time : ");
			sb.append(location.getTime());
			sb.append("\n纬度 : ");
			sb.append(location.getLatitude());
			sb.append("\n经度 : ");
			sb.append(location.getLongitude());
			sb.append("\n城市 : ");
			sb.append(location.getCity());
			sb.append("\n详细地址 : ");
			sb.append(location.getAddrStr());
			//System.out.println("定位结果是------------->>\n" + sb.toString());
			 myapp.setCity(location.getCity());
			 Constant.mlat = location.getLatitude();
			 Constant.mlon = location.getLongitude();
			 senlocation(location.getCity());
			//Toast.makeText(getApplicationContext(), "纬度 : "+location.getLatitude()+"\n经度 : "+location.getLongitude(), 2500).show();
		}

	}
	public void senlocation(String location){
		Intent msgIntent = new Intent(HomeTab.Location_Message);
		msgIntent.putExtra("location", location);
		getApplicationContext().sendBroadcast(msgIntent);
	}

}
