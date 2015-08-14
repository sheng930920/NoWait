package com.nowait.app;

import com.example.nowait.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Checkable;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

public class SingleView extends LinearLayout implements Checkable {
	private TextView tab_person_number;
	private RadioButton radiobtn;

	public SingleView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
	}

	public SingleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public SingleView(Context context) {
		super(context);
		initView(context);
	}

	private void initView(Context context) {
		// Ìî³ä²¼¾Ö
		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.queue_choice_tab_type, this, true);
		tab_person_number = (TextView) v.findViewById(R.id.tab_person_number);
		radiobtn = (RadioButton) v.findViewById(R.id.radiobtn);
	}

	@Override
	public void setChecked(boolean checked) {
		radiobtn.setChecked(checked);

	}

	@Override
	public boolean isChecked() {
		return radiobtn.isChecked();
	}

	@Override
	public void toggle() {
		radiobtn.toggle();
	}

	public void setTitle(String text) {
		tab_person_number.setText(text);
	}
	
	public void setName(String text) {
		radiobtn.setText(text);
	}

}
