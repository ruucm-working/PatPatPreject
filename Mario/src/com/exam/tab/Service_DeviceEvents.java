package com.exam.tab;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.exam.CoinBlockWidgetApp;
import com.exam.R;
import com.exam.helper.TaskTimer;
import com.exam.helper.TextPref;
import com.exam.view.CoinBlockView;

public class Service_DeviceEvents extends Service {
	
	public static  TaskTimer  taskTimer1;
	

	TextPref mPref;

	NotificationCompat.Builder const_builder;
	private static boolean isBatteryLow = false;

	int level;

	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);

		Log.d("Service_BatteryGauge", "onStartCommand");
		
		
		//penetrating timer value
		taskTimer1 = new TaskTimer();
	    Log.d("keeping_taskTimer", " new TaskTimer");
		
		

		IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_BATTERY_CHANGED);
		filter.addAction(Intent.ACTION_HEADSET_PLUG);
		
		registerReceiver(mBRBattery, filter);
		return START_STICKY;
	}

	public IBinder onBind(Intent arg0) {
		return null;
	}

	public void onCreate() {

		Log.d("Service_BatteryGauge", "onCreate");

		const_builder = new NotificationCompat.Builder(
				Service_DeviceEvents.this);
	}

	public void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mBRBattery);
	}

	BroadcastReceiver mBRBattery = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {

			String action = intent.getAction();
			
			Log.v("Service_BatteryGauge", " action : "+action);

			// Battery & HeadsetPlug
			if (Intent.ACTION_BATTERY_CHANGED.equals(action)) {
				
				Log.v("Service_BatteryGauge", " action :ACTION_BATTERY_CHANGED ");

				int plugType = intent.getIntExtra("plugged", 0);
				level = intent.getIntExtra("level", 0);
				int scale = intent.getIntExtra("scale", 100);
				int voltage = intent.getIntExtra("voltage", 0);
				String temper = Integer.toString(intent.getIntExtra(
						"temperature", 0));
				String tech = intent.getStringExtra("technology");
				int health = intent.getIntExtra("health",
						BatteryManager.BATTERY_HEALTH_UNKNOWN);
				String strPlug = null;

				if (plugType == 2)
					Toast.makeText(context, "plugType  " + plugType+" " + action,
							Toast.LENGTH_SHORT).show();

			} else if (Intent.ACTION_HEADSET_PLUG.equals(action)) {
				int id = CoinBlockView.mWidgetId;
				Log.v("Service_BatteryGauge", "Entering headset");

				if (intent.getIntExtra("state", -1) == 1) {
					Toast.makeText(context, "Headset connected",
							Toast.LENGTH_SHORT).show();
					((CoinBlockWidgetApp) context.getApplicationContext())
							.GetView(id).OnHeadsetConnected();
				}

				else {
					Toast.makeText(context, "Headset disconnected",
							Toast.LENGTH_SHORT).show();
				}
/*
				AppWidgetManager manager = AppWidgetManager
						.getInstance(context);
				this.onUpdate(context, manager,
						manager.getAppWidgetIds(new ComponentName(context,
								getClass())));*/
			}
			
			
			
			
			
			

			if (level < 15)
				isBatteryLow = true;
			else
				isBatteryLow = false;

			// String action = intent.getAction();

			Log.d("Service_BatteryGauge", "onReceive");

			Log.d("Service_BatteryGauge", "handleMessag2e 실행");
			NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

			try {
				manager.cancel(2); // 기존에 constNotification 있으면 삭제
			} catch (Exception e) {
				Log.d("Service_BatteryGauge", "Const에서 에러 났다");
			}

			Log.d("Service_BatteryGauge", "NotificationManager 실행");

			const_builder.setSmallIcon(R.drawable.samsung_sample);
			const_builder.setTicker("Device Energy-State Changed");
			const_builder.setContentTitle("Device Energy-State");

			// 알림창 진행중 으로 띄우기
			const_builder.setOngoing(true);
			// set progress
			const_builder.setProgress(100, level, false);

			if (level == -1)
				const_builder.setContentText("배터리 잔량을 확인 중입니다.");
			else
				const_builder.setContentText("배터리 잔량: " + level + "%");

			const_builder.setAutoCancel(false); // 알림바에서 자동 삭제

			// 알람 클릭시 MainActivity를 화면에 띄운다
			Intent nintent = new Intent(getApplicationContext(),
					IntroActivity.class);
			PendingIntent pIntent = PendingIntent.getActivity(
					getApplicationContext(), 0, nintent,
					Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
			const_builder.setContentIntent(pIntent);
			manager.notify(2, const_builder.build());

			// write battery level to pref
			try {
				mPref = new TextPref("mnt/sdcard/SsdamSsdam/textpref.pref");
			} catch (Exception e) {
				e.printStackTrace();
			}

			mPref.Ready();
			mPref.WriteInt("battery_level", level);
			mPref.CommitWrite();

		}
	};
};