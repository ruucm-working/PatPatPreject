package com.jym.helper;

import java.util.List;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

import com.jym.service.IntentService_DeviceEvents;
import com.jym.service.IntentService_TaskTimer;
 
/**
 * User: huhwook
 * Date: 2014. 2. 4.
 * Time: 오후 3:09
 */
public class ServiceMonitor {
 
    private static ServiceMonitor instance;
    private AlarmManager am;
    private Intent intent;
    private PendingIntent sender;
    private long interval = 5000;
 
    private ServiceMonitor() {}
    public static synchronized ServiceMonitor getInstance() {
        if (instance == null) {
            instance = new ServiceMonitor();
        }
        
        Log.d("ServiceMonitor","getInstance");
        return instance;
    }
 
    public static class MonitorBR extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
        	
        	Log.d("ServicceMonitor","intent : "+intent);
        	
            if (isRunningService(context, IntentService_TaskTimer.class) == false) {
                context.startService(new Intent(context, IntentService_TaskTimer.class));
                
                Log.d("ServiceMonitor","startService(new Intent(context, IntentService_TaskTimer");
            }
            
            if (isRunningService(context, IntentService_DeviceEvents.class) == false) {
                context.startService(new Intent(context, IntentService_DeviceEvents.class));
                Log.d("ServiceMonitor","startService(new Intent(context, IntentService_DeviceEvents");
            }
        }
    }
 
    public void setInterval(long interval) {
        this.interval = interval;
    }
 
    public void startMonitoring(Context context) {
    	 Log.d("ServiceMonitor","void startMonitoring");
        am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        intent = new Intent(context, MonitorBR.class);
        sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        am.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), interval, sender);
    }
 
    public void stopMonitoring(Context context) {
        am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        intent = new Intent(context, MonitorBR.class);
        sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        am.cancel(sender);
        am = null;
        sender = null;
    }
 
    public boolean isMonitoring() {
//        return (AppCounterService.mThread == null || AppCounterService.mThread.isAlive() == false) ? false : true;
    
    	return true;
    }
 
    private static boolean isRunningService(Context context, Class<?> cls) {
        boolean isRunning = false;
 
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> info = activityManager.getRunningServices(Integer.MAX_VALUE);
 
        if (info != null) {
            for(ActivityManager.RunningServiceInfo serviceInfo : info) {
                ComponentName compName = serviceInfo.service;
                String className = compName.getClassName();
 
                if(className.equals(cls.getName())) {
                    isRunning = true;
                    break;
                }
            }
        }
        return isRunning;
    }
}
