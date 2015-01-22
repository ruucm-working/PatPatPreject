package com.jym.service;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

public class IntentService_TaskTimer extends IntentService {

	public static TaskTimer taskTimer2;
	private static final String TAG = IntentService_TaskTimer.class.getSimpleName();

	public static final String INPUT_TEXT="INPUT_TEXT";
	public static final String OUTPUT_TEXT="OUTPUT_TEXT";

	public IntentService_TaskTimer() {
		super(IntentService_TaskTimer.class.getSimpleName());
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		//foregrounding Service
		startForeground(1, new Notification());

		if(intent.getStringArrayListExtra("evolvePoint") == null){
			Log.d("bugfix", "진화 조건 비어있어");
		}

		if(intent.getStringArrayListExtra("evolveTimer") == null){
			Log.d("bugfix", "타임 조건 비어있어");
		}

		taskTimer2 = new TaskTimer(intent.getStringArrayListExtra("evolvePoint"), intent.getStringArrayListExtra("evolveTimer"));
		taskTimer2.execute();
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		registerRestartAlarm();
	}

	@Override
	protected void onHandleIntent(Intent intent) {}

	void registerRestartAlarm() {
		Log.d("persist wake","IntentService_TaskTimer _ registerRestartAlarm");
		Intent intent = new Intent( IntentService_TaskTimer.this, IntentService_TaskTimer.class );
		PendingIntent sender = PendingIntent.getService( IntentService_TaskTimer.this, 0, intent, 0 );
		long firstTime = SystemClock.elapsedRealtime();
		firstTime += 10*1000; // 10초 후에 알람이벤트 발생
		AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
		am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, firstTime, 10*1000, sender);
	}

	void unregisterRestartAlarm() {
		Intent intent = new Intent(IntentService_TaskTimer.this, IntentService_TaskTimer.class);
		PendingIntent sender = PendingIntent.getService( IntentService_TaskTimer.this, 0, intent, 0 );
		AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
		am.cancel(sender);
	}
}