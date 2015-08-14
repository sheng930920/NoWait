package com.nowait.activity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.apache.http.Header;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nowait.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nowait.adapter.ArealistAdapter;
import com.nowait.adapter.ArealistdetailAdapter;
import com.nowait.adapter.ShopListAdapter;
import com.nowait.adapter.StatulistAdapter;
import com.nowait.app.Area;
import com.nowait.app.Constant;
import com.nowait.app.HttpUtil;
import com.nowait.app.Shop;
import com.nowait.app.Area.Tags;

public class QueueTab extends Fragment {

	private View view;
	private ListView ShopListView; // 商店列表listview
	private ListView arealist, arealist_detail; // 区域检索listview
	private LinearLayout arealist_layout;
	private ListView statulist; // 状态检索listview
	private LinearLayout tv_area; // 区域检索Layout
	private LinearLayout tv_statu; // 状态检索Layout
	private int statushow = 1;
	private int areashow = 1;
	private ArrayList<Shop> list;
	private ProgressDialog pDialog;
	
	private List<Area> arealistdata;
	private ArealistAdapter areaAdapter;
	private ArealistdetailAdapter areadetailAdapter;
	private TextView location_queue;
	
	private String area = "";
	private String tag = "";
	private int position;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.queue, container, false);
		list = new ArrayList<Shop>();
		arealistdata = new ArrayList<Area>();
		getAreaData("http://192.168.1.105/queue/list/area?city=广州市");
		return view;
	}

	private void getAreaData(String url) {
		HttpUtil.get(url, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				//pDialog.dismiss();
				String Result = new String(arg2);
				int start = Result.indexOf("[{");
				int stop = Result.lastIndexOf("}]");
				String json = Result.substring(start, stop + 2);
				arealistdata.clear();
				Gson gson = new Gson();
				java.lang.reflect.Type listType = new TypeToken<LinkedList<Area>>() {}.getType();
				LinkedList<Area> areas = gson.fromJson(json, listType);

				for (Iterator iterator = areas.iterator(); iterator.hasNext();) {
					Area area = (Area) iterator.next();
					arealistdata.add(area);
				}
				String url = "http://192.168.1.105/queue/list?start=0";
				getJSONFromServer(url);
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,Throwable arg3) {

			}
		});
	}

	public void getJSONFromServer(String url) {

		pDialog = new ProgressDialog(getActivity());
		pDialog.show();
		HttpUtil.get(url, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				pDialog.dismiss();
				String Result = new String(arg2);
				int start = Result.indexOf("[{");
				int stop = Result.lastIndexOf("}]");
				String json = Result.substring(start, stop + 2);
				System.out.println("json---->>" + json);
				DisplayJson(json);
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {

			}
		});
	}

	/**
	 * JSON解释
	 */
	public void DisplayJson(String jsonstring) {
		list.clear();
		Gson gson = new Gson();
		java.lang.reflect.Type listType = new TypeToken<LinkedList<Shop>>() {
		}.getType();
		LinkedList<Shop> shops = gson.fromJson(jsonstring, listType);

		for (Iterator iterator = shops.iterator(); iterator.hasNext();) {
			Shop shop = (Shop) iterator.next();
			list.add(shop);
		}
		init(view);
	}

	private void init(View view) {
		tv_area = (LinearLayout) view.findViewById(R.id.tv_area);
		tv_area.setOnClickListener(new MyOnclickListener());

		ShopListView = (ListView) view.findViewById(R.id.ShopListView);
		ShopListAdapter shopadapter = new ShopListAdapter(getActivity(), list);
		ShopListView.setAdapter(shopadapter);
		ShopListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				String id = list.get(arg2).id;
				Intent intent = new Intent(getActivity(),Shopdetail_activity.class);
				intent.putExtra("id", id);
				startActivity(intent);
			}
		});
		
		areaAdapter = new ArealistAdapter(getActivity(), arealistdata);
		arealist = (ListView) view.findViewById(R.id.arealist);
		arealist_detail = (ListView) view.findViewById(R.id.arealist_detail);
		arealist_layout = (LinearLayout) view.findViewById(R.id.arealist_layout);
		arealist.setAdapter(areaAdapter);
		areaAdapter.setSelectItem(0);
		initAdapter(arealistdata.get(0).tags);
		
		Onelistclick1 onelistclick1 = new Onelistclick1();
		arealist.setOnItemClickListener(onelistclick1);
		
		Onelistclick2 onelistclick2 = new Onelistclick2();
		arealist_detail.setOnItemClickListener(onelistclick2);
		
		tv_statu = (LinearLayout) view.findViewById(R.id.tv_statu);
		tv_statu.setOnClickListener(new MyOnclickListener());

		statulist = (ListView) view.findViewById(R.id.statulist);
		StatulistAdapter statuadapter = new StatulistAdapter(getActivity(),Constant.statu);
		statulist.setAdapter(statuadapter);
	}

	private class MyOnclickListener implements View.OnClickListener {
		public void onClick(View v) {
			int mID = v.getId();
			if (mID == R.id.tv_statu) {
				if (statushow == 1) {
					statulist.setVisibility(View.VISIBLE);
					statushow = 0;
				} else {
					statulist.setVisibility(View.GONE);
					statushow = 1;
				}
			}else if(mID == R.id.tv_area){
				if (areashow == 1) {
					arealist_layout.setVisibility(View.VISIBLE);
					areashow = 0;
				} else {
					arealist_layout.setVisibility(View.GONE);
					areashow = 1;
				}
			}
		}
	}
	
	private class Onelistclick1 implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			position = arg2;
			area = arealistdata.get(arg2).name;
			//Toast.makeText(getActivity(), area, 70000).show();
			initAdapter(arealistdata.get(arg2).tags);
			areaAdapter.setSelectItem(arg2);
			areaAdapter.notifyDataSetChanged();
		}
	}
	
	private class Onelistclick2 implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			tag = arealistdata.get(position).tags.get(arg2).name;
			//area = arealistdata.get(arg2).name;
			String url = Constant.Url+"/query/area?city=广州市"+"&area="+area+"&tag="+tag;
			//Toast.makeText(getActivity(), url, 70000).show();
			System.out.println("选择url------>>>"+url);
			getJSONFromServer(url);
			arealist_layout.setVisibility(View.GONE);
		}
	}
	
	private void initAdapter(List<Tags> tags){
		areadetailAdapter = new ArealistdetailAdapter(getActivity(), tags);
		arealist_detail.setAdapter(areadetailAdapter);
		areadetailAdapter.notifyDataSetChanged();
	}
	
	
	
	
	
	
	
	
	
	

}
