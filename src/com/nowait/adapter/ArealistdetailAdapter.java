package com.nowait.adapter;

import java.util.List;
import com.example.nowait.R;
import com.nowait.app.Area;
import com.nowait.app.Area.Tags;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ArealistdetailAdapter extends BaseAdapter {
	private Context mcontext;
	private List<Tags> tags;
	private int position = 0;
	
	public ArealistdetailAdapter(Context context,List<Tags> tags){
		this.mcontext = context;
		this.tags = tags;
	}

	@Override
	public int getCount() {
		return tags.size();
	}

	@Override
	public Object getItem(int position) {
		return tags.get(position);
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
            convertView = mInflater.inflate(R.layout.arealistdetail, null);
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(viewHolder);
		}else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
		viewHolder.name.setText(tags.get(position).name);
		return convertView;
	}
	
	public void setSelectItem(int i) {
		position = i;
	}
	
	private static class ViewHolder {
		TextView name;
	}

}
