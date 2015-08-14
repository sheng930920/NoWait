package com.nowait.app;

import java.io.File;

import cn.jpush.android.api.JPushInterface;

import com.example.nowait.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.StorageUtils;

import android.app.Application;
import android.os.Environment;

public class Myapplication extends Application {
	
	static Myapplication mApplication;
	
	public String code ="";
	
	public double lat;
	public double lon;
	public String city;
	
	 public static Myapplication getInstance() {
	        return mApplication;
	 }
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double d) {
		this.lat = d;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@SuppressWarnings("deprecation")
	public void onCreate() {
		super.onCreate();
		
		JPushInterface.setDebugMode(false); 	// ���ÿ�����־,����ʱ��ر���־
        JPushInterface.init(this);     		// ��ʼ�� JPush
		
		String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"NoWait/Cache";
		File cacheDir = StorageUtils.getOwnCacheDirectory(this,"NoWait/Cache");
		
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				.showImageForEmptyUri(R.drawable.empty_photo)//û��ͼƬ��Դʱ��Ĭ��ͼƬ 
				 .showImageOnLoading(R.drawable.empty_photo) //����ͼƬʱ��ͼƬ 
				.showImageOnFail(R.drawable.empty_photo)//����ʧ��ʱ��ͼƬ  
				.showStubImage(R.drawable.empty_photo)   // ����ͼƬ�����ڼ���ʾ��ͼƬ  
				.cacheInMemory(true)// �������ص�ͼƬ�Ƿ񻺴����ڴ��� 
				.cacheOnDisc(true)  // �������ص�ͼƬ�Ƿ񻺴���SD����  
				.build();
		
		ImageLoaderConfiguration config = new ImageLoaderConfiguration
				.Builder(getApplicationContext())
		        .threadPoolSize(4)//�̳߳��ڼ��ص�����   
				.defaultDisplayImageOptions(defaultOptions)
				.discCacheSize(50 * 1024 * 1024)//
				.discCacheFileCount(500)// ����500��ͼ
				.discCache(new UnlimitedDiscCache(cacheDir))//�Զ��建��·��   
				.writeDebugLogs()
				.build();
		ImageLoader.getInstance().init(config);
	}

}
