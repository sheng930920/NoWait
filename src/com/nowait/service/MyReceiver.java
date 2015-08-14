package com.nowait.service;

import org.json.JSONException;
import org.json.JSONObject;
import com.google.gson.JsonObject;
import com.nowait.activity.Shopdetail_activity;
import cn.jpush.android.api.JPushInterface;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            //接收Registration Id : " + regId);
            //send the Registration Id to your server...
                        
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
        	//[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
        	
        	//senmessage(context, bundle);
        
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
           //[MyReceiver] 接收到推送下来的通知
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            //接收到推送下来的通知的ID: " + notifactionId
            senmessage(context, bundle);
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            //用户点击打开了通知
            
        	//打开自定义的Activity
        	/*Intent i = new Intent(context, TestActivity.class);
        	i.putExtras(bundle);
        	//i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        	i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
        	context.startActivity(i);*/
        	
        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            //用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..
        	
        } else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
        	boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
        } else {
        }
	}
	
	public void senmessage(Context context, Bundle bundle){
		Intent msgIntent;
		String message = bundle.getString(JPushInterface.EXTRA_ALERT);
		String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
		System.out.println("接收到的推送：\nmessage:"+message+"\nextras:"+extras);
		
		if(message.equals("coolpoint")){
			 msgIntent = new Intent(Shopdetail_activity.Message_Received);
		}else{
			 msgIntent = new Intent(Shopdetail_activity.UpdateMessage_Received);
		}
		msgIntent.putExtra("message", extras);
		context.sendBroadcast(msgIntent);
	}
	

}
