package com.nowait.activity;

import cn.jpush.android.api.JPushInterface;

import com.example.nowait.R;
import com.nowait.service.LocationService;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class MainActivity extends FragmentActivity implements OnClickListener {
	
	private LinearLayout tab1;
	private LinearLayout tab2;
	private LinearLayout tab3;
	private LinearLayout tab4;

	private ImageButton img1;
	private ImageButton img2;
	private ImageButton img3;
	private ImageButton img4;

	private Fragment mTab1;
	private Fragment mTab2;
	private Fragment mTab3;
	private Fragment mTab4;
	
	public static boolean isForeground = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		this.startService(new Intent(this, LocationService.class));
		JPushInterface.init(getApplicationContext());
		init();
		setSelet(1);
	}

	private void init() {
		tab1 = (LinearLayout) findViewById(R.id.tab1);
		tab2 = (LinearLayout) findViewById(R.id.tab2);
		tab3 = (LinearLayout) findViewById(R.id.tab3);
		tab4 = (LinearLayout) findViewById(R.id.tab4);

		img1 = (ImageButton) findViewById(R.id.img1);
		img2 = (ImageButton) findViewById(R.id.img2);
		img3 = (ImageButton) findViewById(R.id.img3);
		img4 = (ImageButton) findViewById(R.id.img4);

		tab1.setOnClickListener(this);
		tab2.setOnClickListener(this);
		tab3.setOnClickListener(this);
		tab4.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		resetImage();
		switch (v.getId()) {
		case R.id.tab1:
			setSelet(1);
			break;
		case R.id.tab2:
			setSelet(2);
			break;
		case R.id.tab3:
			setSelet(3);
			break;
		case R.id.tab4:
			setSelet(4);
			break;
		}
	}
	
	/**
	 * 把图片换成亮色，切换内容
	 */
	private void setSelet(int i) {

		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction();
	    hideFragment(transaction);

		switch (i) {
		case 1:
			if (mTab1 == null) {
				mTab1 = new HomeTab();
				transaction.add(R.id.content, mTab1);
			} else {
				transaction.show(mTab1);
			}
			img1.setImageResource(R.drawable.home_highlighted);
			break;
			
		case 2:
			if (mTab2 == null) {
				mTab2 = new QueueTab();
				transaction.add(R.id.content, mTab2);
			} else {
				transaction.show(mTab2);
			}
			img2.setImageResource(R.drawable.queue_highlighted);
			break;
			
		case 3:
			if (mTab3 == null) {
				mTab3 = new SquareTab();
				transaction.add(R.id.content, mTab3);
			} else {
				transaction.show(mTab3);
			}
			img3.setImageResource(R.drawable.square_highlihghted);
			break;
			
		case 4:
			if (mTab4 == null) {
				mTab4 = new MeTab();
				transaction.add(R.id.content, mTab4);
			} else {
				transaction.show(mTab4);
			}
			img4.setImageResource(R.drawable.me_highlighted);
			break;

		default:
			break;
		}
		transaction.commit();
	}
	private void hideFragment(FragmentTransaction transaction) {
		if (mTab1 != null) {
			transaction.hide(mTab1);
		}
		if (mTab2 != null) {
			transaction.hide(mTab2);
		}
		if (mTab3 != null) {
			transaction.hide(mTab3);
		}
		if (mTab4 != null) {
			transaction.hide(mTab4);
		}
	}	
	
	
	/**
	 * 重置图片，把所有图片换成暗色
	 * 
	 */
	private void resetImage() {
		img1.setImageResource(R.drawable.home_default);
		img2.setImageResource(R.drawable.queue_default);
		img3.setImageResource(R.drawable.square_default);
		img4.setImageResource(R.drawable.me_default);

	}
	
	@Override
	protected void onResume() {
		isForeground = true;
		JPushInterface.onResume(getApplicationContext());
		super.onResume();
	}


	@Override
	protected void onPause() {
		isForeground = false;
		JPushInterface.onPause(getApplicationContext());
		super.onPause();
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
