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
		
		JPushInterface.setDebugMode(false); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush
		
		String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"NoWait/Cache";
		File cacheDir = StorageUtils.getOwnCacheDirectory(this,"NoWait/Cache");
		
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				.showImageForEmptyUri(R.drawable.empty_photo)//没有图片资源时的默认图片 
				 .showImageOnLoading(R.drawable.empty_photo) //加载图片时的图片 
				.showImageOnFail(R.drawable.empty_photo)//加载失败时的图片  
				.showStubImage(R.drawable.empty_photo)   // 设置图片下载期间显示的图片  
				.cacheInMemory(true)// 设置下载的图片是否缓存在内存中 
				.cacheOnDisc(true)  // 设置下载的图片是否缓存在SD卡中  
				.build();
		
		ImageLoaderConfiguration config = new ImageLoaderConfiguration
				.Builder(getApplicationContext())
		        .threadPoolSize(4)//线程池内加载的数量   
				.defaultDisplayImageOptions(defaultOptions)
				.discCacheSize(50 * 1024 * 1024)//
				.discCacheFileCount(500)// 缓存500张图
				.discCache(new UnlimitedDiscCache(cacheDir))//自定义缓存路径   
				.writeDebugLogs()
				.build();
		ImageLoader.getInstance().init(config);
	}

}
