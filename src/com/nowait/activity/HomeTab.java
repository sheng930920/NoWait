package com.nowait.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nowait.R;
import com.nowait.adapter.AdvAdapter;
import com.nowait.app.Constant;
import com.nowait.app.Myapplication;

public class HomeTab extends Fragment {

	private ImageView[] imageViews = null;
	private ImageView imageView = null;
	private ViewPager advPager = null;
	private AtomicInteger what = new AtomicInteger(0);
	private boolean isContinue = true;
	private View mview;
	private ListView listview;
	private TextView location;
	ViewGroup group;
	Myapplication myapp;
	private LocationReceiver locationReceiver;
	public static final String Location_Message = "com.nowait.location";
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		myapp = (Myapplication) getActivity().getApplicationContext();
		mview = inflater.inflate(R.layout.home, container, false);
		advPager = (ViewPager) mview.findViewById(R.id.adv_pager);
		group = (ViewGroup) mview.findViewById(R.id.viewGroup);
		listview = (ListView) mview.findViewById(R.id.listView);
		location = (TextView) mview.findViewById(R.id.location);
		registerLocationReceiver();
		init();
		return mview;
	}
	public void registerLocationReceiver() {
		locationReceiver = new LocationReceiver();
		IntentFilter filter = new IntentFilter();
		
		filter.addAction(Location_Message);
		getActivity().registerReceiver(locationReceiver, filter);
	}
	
	public class LocationReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (Location_Message.equals(intent.getAction())) {
				String Location = intent.getStringExtra("location");
				location.setText(Location);
				myapp.setLat(intent.getDoubleExtra("lat", 0.0));
				myapp.setLon(intent.getDoubleExtra("lon", 0.0));
			}
		}
		
	}

	public void init() {
		// 这里存放的是四张广告背景
		List<View> advPics = new ArrayList<View>();
		ImageView img1 = new ImageView(getActivity());
		img1.setBackgroundResource(R.drawable.a1);
		advPics.add(img1);
		ImageView img2 = new ImageView(getActivity());
		img2.setBackgroundResource(R.drawable.a2);
		advPics.add(img2);
		ImageView img3 = new ImageView(getActivity());
		img3.setBackgroundResource(R.drawable.a3);
		advPics.add(img3);
		ImageView img4 = new ImageView(getActivity());
		img4.setBackgroundResource(R.drawable.a4);
		advPics.add(img4);

		// 对imageviews进行填充
		imageViews = new ImageView[advPics.size()];
		// 小图标
		for (int i = 0; i < advPics.size(); i++) {
			imageView = new ImageView(getActivity());
			imageView.setLayoutParams(new LayoutParams(20, 20));
			imageView.setPadding(5, 5, 5, 5);
			imageViews[i] = imageView;
			if (i == 0) {
				imageViews[i].setBackgroundResource(R.drawable.b2);
			} else {
				imageViews[i].setBackgroundResource(R.drawable.b1);
			}
			group.addView(imageViews[i]);
		}

		advPager.setAdapter(new AdvAdapter(advPics));
		advPager.setOnPageChangeListener(new GuidePageChangeListener());
		advPager.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
				case MotionEvent.ACTION_MOVE:
					isContinue = false;
					break;
				case MotionEvent.ACTION_UP:
					isContinue = true;
					break;
				default:
					isContinue = true;
					break;
				}
				return false;
			}
		});
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					if (isContinue) {
						viewHandler.sendEmptyMessage(what.get());
						whatOption();
					}
				}
			}
		}).start();
	}

	private void whatOption() {
		what.incrementAndGet();
		if (what.get() > imageViews.length - 1) {
			what.getAndAdd(-4);
		}
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {

		}
	}

	private  Handler viewHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			advPager.setCurrentItem(msg.what);
			super.handleMessage(msg);
		}
	};

	private  class GuidePageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageScrollStateChanged(int arg0) {
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageSelected(int arg0) {
			what.getAndSet(arg0);
			for (int i = 0; i < imageViews.length; i++) {
				imageViews[arg0].setBackgroundResource(R.drawable.b2);
				if (arg0 != i) {
					imageViews[i].setBackgroundResource(R.drawable.b1);
				}
			}
		}
	}

}
