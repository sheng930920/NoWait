package com.nowait.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.nowait.R;
import com.nowait.app.ShopDetail.Table;

public class ShopdetailAdapter extends BaseAdapter {
	
	private Context mcontext;
	private List<Table> table;
	private Map<String,String> map;
	
	public ShopdetailAdapter(Context context,List<Table> table,Map<String,String> map){
		this.mcontext = context;
		this.table = table;
		this.map = map;
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
	public View getView(int position, View convertView, ViewGroup arg2) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
            LayoutInflater mInflater = LayoutInflater.from(mcontext);
            convertView = mInflater.inflate(R.layout.shoptabitem, null);
            viewHolder.tabLayout = (LinearLayout) convertView.findViewById(R.id.tabLayout);
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.desc = (TextView) convertView.findViewById(R.id.desc);
            viewHolder.waitnum = (TextView) convertView.findViewById(R.id.waitnum);
            viewHolder.average = (TextView) convertView.findViewById(R.id.average);
            
            convertView.setTag(viewHolder);
            
		}else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
		
		 viewHolder.name.setText(table.get(position).name);
         viewHolder.desc.setText(table.get(position).desc);
         viewHolder.average.setText(table.get(position).average);
         String tag = table.get(position).code;
         if( map.get(tag) != null){
         	 viewHolder.waitnum.setText(map.get(tag));
         	int average = Integer.parseInt(map.get(tag))*Integer.parseInt(table.get(position).average);
         	viewHolder.average.setText(String.valueOf(average));
         }else{
         	viewHolder.waitnum.setText("0");
         	viewHolder.average.setText("0"); 
         }
         if(position%2 == 0){
         	viewHolder.tabLayout.setBackgroundResource(R.drawable.queue_detail_tab2);
         }else{
         	viewHolder.tabLayout.setBackgroundResource(R.drawable.queue_detail_tab1);
         }
		return convertView;
	}
	
	private static class ViewHolder {
		LinearLayout tabLayout;
		TextView name;
		TextView desc;
		TextView waitnum;
		TextView average;
	}

}
