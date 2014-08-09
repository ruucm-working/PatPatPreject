package com.exam.tab;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.exam.R;



public class Service_BatteryGauge extends Service {
	
	NotificationCompat.Builder const_builder;	
	private static boolean isBatteryLow = false;
	
	
	
	
	public int onStartCommand (Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		
		Log.d("battersv","onStartCommand");
		
		
		IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_BATTERY_CHANGED);
		registerReceiver(mBRBattery, filter);
		return START_STICKY;
	}

	public IBinder onBind(Intent arg0) {
		return null;
	}
	
	
	public void onCreate()
	{
		
		Log.d("battersv","onCreate");
		 
		const_builder = new NotificationCompat.Builder(Service_BatteryGauge.this);
	}
	
	public void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mBRBattery);
	}

	BroadcastReceiver mBRBattery = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			
			
			
			
		String action = intent.getAction();
			if (Intent.ACTION_BATTERY_CHANGED.equals(action)) {
			
			int plugType = intent.getIntExtra("plugged", 0);
			Log.d("Service_BatteryGauge","plugType  "+plugType);
			int level = intent.getIntExtra("level", 0);
			Log.d("Service_BatteryGauge","level  "+level);
			int scale = intent.getIntExtra("scale", 100);
			Log.d("Service_BatteryGauge","scale  "+scale);
			int voltage = intent.getIntExtra("voltage", 0);
			Log.d("Service_BatteryGauge","voltage  "+voltage);
			String temper = Integer.toString(intent.getIntExtra("temperature", 0));
			Log.d("Service_BatteryGauge","temper  "+temper);
			String tech = intent.getStringExtra("technology");
			Log.d("Service_BatteryGauge","tech  "+tech);
			int health = intent.getIntExtra("health", BatteryManager.BATTERY_HEALTH_UNKNOWN);
			Log.d("Service_BatteryGauge","health  "+health);
			String strPlug = null;
			 
			
			if (plugType == 2)			
			Toast.makeText(context, "plugType  "+ plugType, Toast.LENGTH_SHORT).show();
			
			
			}
			
			
			int bLevel = intent.getIntExtra("level", 0);
			//Log.v(TAG, "Battery level changed: " + bLevel);			
			
			
			
			Setting.nowBattery = bLevel;
			coinBlockIntroActivity.bLevel = bLevel;

			if(bLevel < 15)
				isBatteryLow = true;
			else
				isBatteryLow = false;
			
			
			
			//String action = intent.getAction();
			 
			
			Log.d("battersv","onReceive");
			 
			 
			Log.d("battersv","handleMessag2e 실행");
			NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
			
			try {
				manager.cancel(2);		// 기존에 constNotification 있으면 삭제
			} catch(Exception e) {
				Log.d("battersv", "Const에서 에러 났다");
			}

			Log.d("battersv","NotificationManager 실행");

			const_builder.setSmallIcon(R.drawable.samsung_sample);
			const_builder.setTicker("Device Energy-State Changed");
			const_builder.setContentTitle("Device Energy-State");
			
			//알림창 진행중 으로 띄우기
			const_builder.setOngoing(true);
			//set progress
			const_builder.setProgress (100, bLevel, false);
			
			if(Setting.nowBattery == -1)
				const_builder.setContentText("배터리 잔량을 확인 중입니다.");
			else	
				const_builder.setContentText("배터리 잔량: " + Setting.nowBattery + "%");

			const_builder.setAutoCancel(false); // 알림바에서 자동 삭제
			Log.d("battersv","const configuration complete");


			// 알람 클릭시 MainActivity를 화면에 띄운다
			Intent nintent = new Intent(getApplicationContext(),coinBlockIntroActivity.class);
			PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext()
					, 0
					, nintent 
					, Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
			const_builder.setContentIntent(pIntent);
			manager.notify(2, const_builder.build());
			
			
			
			/*
			
			if (action.equals(Intent.ACTION_BATTERY_CHANGED)) {
				
				
				
				int scale, level, ratio;
				scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 100);
				level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
				ratio = level * 100 / scale;
				
				
				Log.d("battersv","ratio ");

				RemoteViews remote = new RemoteViews(context.getPackageName(), 
						R.layout.batterygauge);
				remote.setTextViewText(R.id.gauge, "" + ratio + "%");
				
				Log.d("battersv","setTextViewText ");

				
				AppWidgetManager wm = AppWidgetManager.getInstance(
						Service_BatteryGauge.this);
				ComponentName widget = new ComponentName(context, BatteryGauge.class);
				wm.updateAppWidget(widget, remote);
				
				
				
				
			}
			*/
		}
	};
};
