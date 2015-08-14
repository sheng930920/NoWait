package com.nowait.adapter;

import java.util.ArrayList;

import com.example.nowait.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nowait.app.Constant;
import com.nowait.app.Shop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ShopListAdapter extends BaseAdapter {
	
	private ArrayList<Shop> list;
	private Context mcontext;
	
	public ShopListAdapter(Context context,ArrayList<Shop> list){
		this.mcontext = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
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
            convertView = mInflater.inflate(R.layout.queue_listview_item, null);
            viewHolder.shop_image = (ImageView) convertView.findViewById(R.id.shop_image);
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.type = (TextView) convertView.findViewById(R.id.type);
            viewHolder.money = (TextView) convertView.findViewById(R.id.money);
            viewHolder.waitNum = (TextView) convertView.findViewById(R.id.waitNum);
            viewHolder.location = (TextView) convertView.findViewById(R.id.location);
            viewHolder.type_image = (ImageView) convertView.findViewById(R.id.type_image);
            
            ImageLoader.getInstance().displayImage("http://192.168.1.105"+list.get(position).imgSrc, viewHolder.shop_image);
            viewHolder.title.setText(list.get(position).title);
            viewHolder.type.setText(list.get(position).type);
            viewHolder.money.setText("£¤"+list.get(position).money+"/ÈË");
            viewHolder.waitNum.setText(String.valueOf(list.get(position).waitNum));
            String diatance = Constant.getDistance(list.get(position).location.lng, list.get(position).location.lat);
            viewHolder.location.setText(diatance+"km");
            if(list.get(position).waitNum > 0){
            	viewHolder.type_image.setImageResource(R.drawable.queue_icon_queue);
            }else{
            	viewHolder.type_image.setImageResource(R.drawable.ic_dinnerware);
            }
            convertView.setTag(viewHolder);
		}else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
		return convertView;
	}
	
	private static class ViewHolder {
		ImageView shop_image;
		TextView title;
		TextView type;
		TextView money;
		TextView location;
		ImageView type_image;
		TextView waitNum;
	}

}
