package com.nowait.activity;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import com.example.nowait.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nowait.adapter.ShopdetailAdapter;
import com.nowait.adapter.QueueChioceTabTypeAdapter;//
import com.nowait.app.Constant;
import com.nowait.app.HttpUtil;
import com.nowait.app.Myapplication;
import com.nowait.app.ShopDetail;

public class Shopdetail_activity extends Activity implements OnClickListener {

	private LinearLayout showall;
	private ImageView shopImage;
	private LinearLayout iswait;
	private LinearLayout queueLayout;
	private ShoptabListview tab;
	private TextView title;
	private TextView type;
	private TextView money;
	private TextView address;
	private TextView openTime;
	private TextView discount;
	private ImageView take_phone;
	private Button getNumber;
	private TextView distance;

	private ShopDetail shopdetail;
	private ShopdetailAdapter shopdetailadapter;
	private  QueueChioceTabTypeAdapter myadapter;
	private String id;
	Myapplication myapp;
	private String tabcode ="";
	private Gson gson;
	
	public static boolean isForeground = false;
	public static final String Message_Received = "com.nowait.tabnews";
	public static final String UpdateMessage_Received = "com.nowait.updatenews";
	private static final int MSG_SET_TAGS = 1002;
	private MessageReceiver mMessageReceiver;
	
	private Map<String,String> map;
	
	private int currentTableNumber;
	private String currentTableCode = "";
	private TextView tipnews;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.shop_detail);
		id = getIntent().getStringExtra("id");
		myapp = (Myapplication) getApplication();
		registerMessageReceiver();
		setTag();
		getServiceData();
		initview();
	}

	private void initview() {
		showall = (LinearLayout) findViewById(R.id.showall);
		shopImage = (ImageView) findViewById(R.id.shopaImage);
		iswait = (LinearLayout) findViewById(R.id.iswait);
		queueLayout = (LinearLayout) findViewById(R.id.queueLayout);
		tab = (ShoptabListview) findViewById(R.id.tab);
		title = (TextView) findViewById(R.id.title);
		type = (TextView) findViewById(R.id.type);
		money = (TextView) findViewById(R.id.money);
		address = (TextView) findViewById(R.id.address);
		openTime = (TextView) findViewById(R.id.openTime);
		discount = (TextView) findViewById(R.id.discount);
		take_phone = (ImageView) findViewById(R.id.take_phone);
		getNumber = (Button) findViewById(R.id.getNumber);
		tipnews = (TextView) findViewById(R.id.tipnews);
		distance = (TextView) findViewById(R.id.distance);
		take_phone.setOnClickListener(this);
		getNumber.setOnClickListener(this);

	}

	public void getServiceData() {
		//http://192.168.1.105/queue/detail?id=55c9ba2e51c80f64649ff0ac
		String urlString = "http://192.168.1.105/queue/detail?id="+ String.valueOf(id);
		System.out.println(urlString);
		HttpUtil.get(urlString, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				String jsoString = new String(arg2);
				String newjsoString = jsoString.replaceAll("-t", "T");
				System.out.println("新的json字符串是------>>" + newjsoString);
				playview(newjsoString);
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub
			}
		});

	}

	public void playview(String json) {
		String shopString = "";
		String extraString = "";
		try {
			JSONObject obj = new JSONObject(json);
			JSONObject shop  = obj.getJSONObject("data");
			shopString = shop.toString();
			JSONObject extra = obj.getJSONObject("extra");
			extraString = extra.toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		gson = new Gson();
		map = gson.fromJson(extraString, new TypeToken<Map<String, String>>() {}.getType()); 
		shopdetail = new ShopDetail();
		shopdetail = gson.fromJson(shopString, ShopDetail.class);
		ImageLoader.getInstance().displayImage("http://192.168.1.105"+shopdetail.imgSrc,shopImage);
		title.setText(shopdetail.title);
		type.setText(shopdetail.type);
		money.setText("￥"+shopdetail.money+"/人");
		address.setText(shopdetail.address);
		openTime.setText("  营业时间" + shopdetail.openTime);
		discount.setText(shopdetail.discount);
		 test();
		String distan = Constant.getDistance(shopdetail.location.lng,shopdetail.location.lat);
		 distance.setText(distan+"km");
		showall.setVisibility(View.VISIBLE);
		
	}

	public void test(){
		if (map != null && map.get("wait").equals("1")&& map.get("wait") != null) {
			shopdetailadapter = new ShopdetailAdapter(Shopdetail_activity.this,shopdetail.table,map);
			tab.setAdapter(shopdetailadapter);
			shopdetailadapter.notifyDataSetChanged();
			iswait.setVisibility(View.GONE);
			queueLayout.setVisibility(View.VISIBLE);
		}else{
			queueLayout.setVisibility(View.GONE);
		}
	}
	
	@Override
	public void onClick(View v) {
		int mID = v.getId();
		if(mID == R.id.take_phone){
			new Phone_Popupwindows(Shopdetail_activity.this, take_phone,shopdetail.tel);
		}else if(mID == R.id.getNumber){
			showchoiceTab();
		}		
	}

	private void showchoiceTab() {
		final AlertDialog dlg = new AlertDialog.Builder(this).create();
		dlg.show();
		 Window window = dlg.getWindow();
	   // 设置窗口的内容页面,shopchoosetab_popuwindows.xml文件中定义view内容
		 window.setContentView(R.layout.shopchoosetab_popuwindows);
		 ListView tab_listView = (ListView) window.findViewById(R.id.tab_listView);
		 Button commit = (Button) window.findViewById(R.id.commit);
		 Button reset = (Button) window.findViewById(R.id.reset);
		 myadapter = new QueueChioceTabTypeAdapter(Shopdetail_activity.this,shopdetail.table);
		 tab_listView.setAdapter(myadapter);
		 reset.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dlg.cancel();
			}
		});
		 commit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				tabcode = myapp.getCode();
				currentTableCode = myapp.getCode();
				myapp.setCode("");
				getNumber();
				dlg.cancel();
			}
		});
	}
	
	private void getNumber(){
		String urlString = "http://192.168.1.105/queue/get?id="+id+"&code="+tabcode;
		HttpUtil.get(urlString, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				String jsonString = new String(arg2);
				try {
					JSONObject obj = new JSONObject(jsonString);
					if(obj.get("type").equals("ok")){
						String num = obj.getString("num");
						currentTableNumber = Integer.parseInt(num);
						getNumber.setText(tabcode+num);
						//getNumber.setBackgroundColor(R.drawable.queue_detail_btn_number);
						getNumber.setBackgroundResource(R.drawable.queue_detail_btn_number);
						tipnews.setText("   您领取的号码是："+tabcode+num);
						getNumber.setClickable(false);
						tabcode = "";
					}else{
						String info = obj.getString("info");
						Toast.makeText(getApplicationContext(), info, 3000).show();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,Throwable arg3) {
			}
		});
	}
	
	
	
	@Override
	protected void onResume() {
		isForeground = true;
		super.onResume();
	}


	@Override
	protected void onPause() {
		isForeground = false;
		super.onPause();
	}
	
	
	public class MessageReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (Message_Received.equals(intent.getAction())) {
				String messge = intent.getStringExtra("message");
				map.clear();
				map = gson.fromJson(messge, new TypeToken<Map<String, String>>() {}.getType()); 
				test();
				
			}else if(UpdateMessage_Received.equals(intent.getAction())){
				String messge = intent.getStringExtra("message");
				try {
					JSONObject obj = new JSONObject(messge);
					String getdata = obj.getString("num");
					String getCode = Constant.filterAlphabet(getdata);
					String getNum = Constant.filterNumber(getdata);
					int number = Integer.parseInt(getNum);
					if(getCode.equals(currentTableCode)){
						if(number <= currentTableNumber ){
							tipnews.setText("  当前排号是："+getdata);
						}else{
							tipnews.setText("  您已经过号，请从新取号");
							getNumber.setText("立即取号");
							getNumber.setBackgroundResource(R.drawable.queue_detail_btn_number_default);
							getNumber.setClickable(true);
						}
					}else{
						return;
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	
	public void registerMessageReceiver() {
		mMessageReceiver = new MessageReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Message_Received);
		filter.addAction(UpdateMessage_Received);
		registerReceiver(mMessageReceiver, filter);
	}
	
	private void setTag(){
		String[] sArray = id.split(",");
		Set<String> tagSet = new LinkedHashSet<String>();
		for (String sTagItme : sArray) {
			if (!Constant.isValidTagAndAlias(sTagItme)) {
				Toast.makeText(getApplicationContext(),"格式不对", Toast.LENGTH_SHORT).show();
				return;
			}
			tagSet.add(sTagItme);
		}
		
		//调用JPush API设置Tag
		mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_TAGS, tagSet));
	}
	
	private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
            case MSG_SET_TAGS:
                JPushInterface.setAliasAndTags(getApplicationContext(), null, (Set<String>) msg.obj, mTagsCallback);
                break;
                
            default:
            }
        }
    };
    
    private final TagAliasCallback mTagsCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs ;
            switch (code) {
            case 0:
                logs = "Set tag and alias success";
                break;
                
            case 6002:
                logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                if (Constant.isConnected(getApplicationContext())) {
                	mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_TAGS, tags), 1000 * 5);
                } else {
                }
                break;
            
            default:
                logs = "Failed with errorCode = " + code;
            }
        }
        
    };
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
	

}
