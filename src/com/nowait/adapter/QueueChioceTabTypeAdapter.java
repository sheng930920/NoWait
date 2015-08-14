package com.nowait.adapter;

import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.TextView;
import com.example.nowait.R;
import com.nowait.app.Myapplication;
import com.nowait.app.ShopDetail.Table;
import com.nowait.app.SingleView;

public class QueueChioceTabTypeAdapter extends BaseAdapter {

	private List<Table> table;
	private Context mcontext;
	// 用于记录每个RadioButton的状态，并保证只可选一个
	HashMap<String, Boolean> states = new HashMap<String, Boolean>();
	
	Myapplication myapplication;

	public QueueChioceTabTypeAdapter(Context context, List<Table> table) {
		this.mcontext = context;
		this.table = table;
		myapplication = (Myapplication) mcontext.getApplicationContext();
	}

	@Override
	public int getCount() {
		return table.size();
	}

	@Override
	public Object getItem(int arg0) {
		return table.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(final int arg0, View convertView, ViewGroup arg2) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			LayoutInflater mInflater = LayoutInflater.from(mcontext);
			convertView = mInflater.inflate(R.layout.queue_choice_tab_type, null);
			viewHolder.tab_person_number = (TextView) convertView.findViewById(R.id.tab_person_number);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		viewHolder.tab_person_number.setText(table.get(arg0).desc);
		final RadioButton radio =  (RadioButton) convertView.findViewById(R.id.radiobtn);
		viewHolder.radiobtn = radio;
		viewHolder.radiobtn.setText(table.get(arg0).name);
		viewHolder.radiobtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				myapplication.setCode(table.get(arg0).code);
				
				for (String key : states.keySet()) {
			          states.put(key, false);
			        }
			        states.put(String.valueOf(arg0), radio.isChecked());
			        notifyDataSetChanged();
			}
		});
		boolean res = false;
	    if (states.get(String.valueOf(arg0)) == null|| states.get(String.valueOf(arg0)) == false) {
	      res = false;
	     // states.put(String.valueOf(arg0), false);
	    } else{
	      res = true;
	    }
	    viewHolder.radiobtn.setChecked(res);
		return convertView;
	}

	private static class ViewHolder {
		RadioButton radiobtn;
		TextView tab_person_number;
	}

}
