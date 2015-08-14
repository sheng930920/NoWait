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
		mLocationClient.start();// ��λSDK
		// start֮���Ĭ�Ϸ���һ�ζ�λ���󣬿����������ж�isstart����������request
		mLocationClient.requestLocation();

	}

	private void initLocation() {
		option = new LocationClientOption();
		option.setOpenGps(true); // �Ƿ��GPS
		option.setLocationMode(LocationMode.Hight_Accuracy);// ��ѡ��Ĭ�ϸ߾��ȣ����ö�λģʽ���߾��ȣ��͹��ģ����豸
		option.setCoorType("gcj02");// ��ѡ��Ĭ��gcj02�����÷��صĶ�λ�������ϵ��bd09ll
		option.setScanSpan(2000);// ��ѡ��Ĭ��0��������λһ�Σ����÷���λ����ļ����Ҫ���ڵ���1000ms������Ч��
		option.setIsNeedAddress(true);// ��ѡ�������Ƿ���Ҫ��ַ��Ϣ��Ĭ�ϲ���Ҫ
		option.setOpenGps(true);// ��ѡ��Ĭ��false,�����Ƿ�ʹ��gps
		option.setIsNeedAddress(true);// ���صĶ�λ���������ַ��Ϣ
		option.setLocationNotify(true);// ��ѡ��Ĭ��false�������Ƿ�gps��Чʱ����1S1��Ƶ�����GPS���
		option.setIgnoreKillProcess(true);// ��ѡ��Ĭ��true����λSDK�ڲ���һ��SERVICE�����ŵ��˶������̣������Ƿ���stop��ʱ��ɱ��������̣�Ĭ�ϲ�ɱ��
		mLocationClient.setLocOption(option);
	}

	/**
	 * ʵ��ʵʱλ�ûص�����
	 */
	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// Receive Location
			StringBuffer sb = new StringBuffer(256);
			sb.append("time : ");
			sb.append(location.getTime());
			sb.append("\nγ�� : ");
			sb.append(location.getLatitude());
			sb.append("\n���� : ");
			sb.append(location.getLongitude());
			sb.append("\n���� : ");
			sb.append(location.getCity());
			sb.append("\n��ϸ��ַ : ");
			sb.append(location.getAddrStr());
			//System.out.println("��λ�����------------->>\n" + sb.toString());
			 myapp.setCity(location.getCity());
			 Constant.mlat = location.getLatitude();
			 Constant.mlon = location.getLongitude();
			 senlocation(location.getCity());
			//Toast.makeText(getApplicationContext(), "γ�� : "+location.getLatitude()+"\n���� : "+location.getLongitude(), 2500).show();
		}

	}
	public void senlocation(String location){
		Intent msgIntent = new Intent(HomeTab.Location_Message);
		msgIntent.putExtra("location", location);
		getApplicationContext().sendBroadcast(msgIntent);
	}

}
