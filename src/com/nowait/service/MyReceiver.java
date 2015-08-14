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
            //����Registration Id : " + regId);
            //send the Registration Id to your server...
                        
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
        	//[MyReceiver] ���յ������������Զ�����Ϣ: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
        	
        	//senmessage(context, bundle);
        
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
           //[MyReceiver] ���յ�����������֪ͨ
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            //���յ�����������֪ͨ��ID: " + notifactionId
            senmessage(context, bundle);
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            //�û��������֪ͨ
            
        	//���Զ����Activity
        	/*Intent i = new Intent(context, TestActivity.class);
        	i.putExtras(bundle);
        	//i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        	i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
        	context.startActivity(i);*/
        	
        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            //�û��յ���RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //��������� JPushInterface.EXTRA_EXTRA �����ݴ�����룬������µ�Activity�� ��һ����ҳ��..
        	
        } else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
        	boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
        } else {
        }
	}
	
	public void senmessage(Context context, Bundle bundle){
		Intent msgIntent;
		String message = bundle.getString(JPushInterface.EXTRA_ALERT);
		String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
		System.out.println("���յ������ͣ�\nmessage:"+message+"\nextras:"+extras);
		
		if(message.equals("coolpoint")){
			 msgIntent = new Intent(Shopdetail_activity.Message_Received);
		}else{
			 msgIntent = new Intent(Shopdetail_activity.UpdateMessage_Received);
		}
		msgIntent.putExtra("message", extras);
		context.sendBroadcast(msgIntent);
	}
	

}
