package com.nowait.adapter;

import java.util.List;

import android.R.color;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nowait.R;
import com.nowait.app.Area;

public class ArealistAdapter extends BaseAdapter {
	
	private Context mcontext;
	private List<Area> arealistdata;
	private int position = 0;
	
    public ArealistAdapter(Context context,List<Area> arealistdata){
    	this.mcontext = context;
    	this.arealistdata = arealistdata;
    }
	
	@Override
	public int getCount() {
		return arealistdata.size();
	}

	@Override
	public Object getItem(int position) {
		return arealistdata.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
            LayoutInflater mInflater = LayoutInflater.from(mcontext);
            convertView = mInflater.inflate(R.layout.arealist, null);
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.layout = (LinearLayout) convertView.findViewById(R.id.layout);
            convertView.setTag(viewHolder);
		}else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
		viewHolder.name.setText(arealistdata.get(position).name);
		if(position == getSelectItem()){
			viewHolder.layout.setBackgroundColor(color.white);
		}
		return convertView;
	}
	
	public void setSelectItem(int i) {
		position = i;
	}

	public int getSelectItem() {
		return position;
	}
	
	private static class ViewHolder {
		TextView name;
		LinearLayout layout;
	}

}
