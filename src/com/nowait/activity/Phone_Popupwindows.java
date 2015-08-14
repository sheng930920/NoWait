package com.nowait.activity;


import com.example.nowait.R;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

public class Phone_Popupwindows extends PopupWindow {
	public Phone_Popupwindows(final Context mContext, View parent ,final String phone) {

		View view = View.inflate(mContext, R.layout.shophone_popuwindows, null);
		view.startAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_ins));
		
		LinearLayout ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
		ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,R.anim.push_bottom_in_2));

		setWidth(LayoutParams.FILL_PARENT);
		setHeight(LayoutParams.FILL_PARENT);
		setBackgroundDrawable(new BitmapDrawable());
		setFocusable(true);
		setOutsideTouchable(true);
		setContentView(view);
		showAtLocation(parent, Gravity.BOTTOM, 0, 0);
		update();

		Button take_phone = (Button) view.findViewById(R.id.take_phone);
		take_phone.setText(phone);
		Button rest = (Button) view.findViewById(R.id.rest);
		take_phone.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent phoneIntent = new Intent("android.intent.action.CALL",Uri.parse("tel:" + phone));
				mContext.startActivity(phoneIntent);
				dismiss();
			}
		});
		
		rest.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				dismiss();
			}
		});

	}

}
