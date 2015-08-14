package com.nowait.app;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import android.app.ProgressDialog;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ServerTask {

	public static ArrayList<Shop> list = new ArrayList<Shop>();

	
/*	public void getJSONFromServer(String url) {

		HttpUtils http = new HttpUtils();
		http.send(HttpRequest.HttpMethod.GET, url,
				new RequestCallBack<String>() {

					@Override
					public void onStart() {
						pDialog = new ProgressDialog(getActivity());
						pDialog.show();
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						pDialog.dismiss();
						String Result = responseInfo.result;
						int start = Result.indexOf("[{");
						int stop = Result.lastIndexOf("}]");
						String json = Result.substring(start, stop + 2);
						System.out.println("json---->>" + json);
						DisplayJson(json);
					}

					@Override
					public void onFailure(HttpException error, String msg) {

					}
				});
	}*/
	/**
	 * json½âÎö
	 * 
	 * @param jsonstring
	 * @return
	 */
	public static ArrayList<Shop> DisplayJson(String jsonstring) {
		list.clear();
		Gson gson = new Gson();
		java.lang.reflect.Type listType = new TypeToken<LinkedList<Shop>>() {
		}.getType();
		LinkedList<Shop> shops = gson.fromJson(jsonstring, listType);

		for (Iterator iterator = shops.iterator(); iterator.hasNext();) {
			Shop shop = (Shop) iterator.next();
			list.add(shop);
		}
		return list;
	}

}
