package com.exam;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbManager;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.exam.helper.SnowWiFiMonitor;
import com.exam.tab.Service_DeviceEvents;
import com.exam.tab.Service_Notify;
import com.exam.tab.Service_SnowWiFiMonitor;
import com.exam.view.CoinBlockView;


public class coinBlockWidgetProvider extends AppWidgetProvider {
	
	private SnowWiFiMonitor m_SnowWifiMonitor = null;
	

	public static final String TAG = "block";
	public static final String TAG2 = "anim";

	// phone status variables
	private static boolean isDown = false;
	private static boolean isSMSNotRead = false;
	private static boolean isBatteryLow = false;
	private static boolean isHeadset = false;
	private static boolean isPlaneMode = false;
	private static boolean isWifiConnected = false;
	private static boolean isBluetoothActivated = false;
	private static boolean isPowerConnected = false;
	private static boolean isUsbAttached = false;
	private static boolean isThreadCreated = false;
	private int nowBattery;
	private static boolean isAdditionalListenerCreated = false;
	
	private static long wifi_cool = 0;
	
	NotificationCompat.Builder const_builder;
	
	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		super.onDeleted(context, appWidgetIds);
		for (int x : appWidgetIds) {
			((CoinBlockWidgetApp) context.getApplicationContext()).DeleteWidget(x);
		}
		
		//context.getApplicationContext().unregisterReceiver(m_SnowWifiMonitor);
		Log.i("coinBlockWidgetProvider", "unregisterReceiver ");
		
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		
		
		
		//coinBlockIntroActivity.UpdateIntroView();
		
		
		if(!isAdditionalListenerCreated)
		{
			context.getApplicationContext().registerReceiver(this, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
			context.getApplicationContext().registerReceiver(this, new IntentFilter(Intent.ACTION_HEADSET_PLUG));
			context.getApplicationContext().registerReceiver(this, new IntentFilter(UsbManager.ACTION_USB_ACCESSORY_ATTACHED));
			context.getApplicationContext().registerReceiver(this, new IntentFilter(UsbManager.ACTION_USB_ACCESSORY_DETACHED));
			
			isAdditionalListenerCreated = true;
		}

		for (int i=0; i<appWidgetIds.length; i++)
		{
			((CoinBlockWidgetApp) context.getApplicationContext()).UpdateWidget(appWidgetIds[i]);
			Log.d("coinBlockWidgetProvider","onUpdate"+appWidgetIds[i]);
		}
		Log.d("coinBlockWidgetProvider","onUpdate;");
		
		
		
		// Run service
		
		
		
		Intent nintent = new Intent(context, Service_Notify.class);
		context.startService(nintent);
		
		Intent intent = new Intent(context, Service_DeviceEvents.class);
		context.startService(intent);
		
		
		Log.d("coinBlockWidgetProvider","startService intent1  "+intent);
		
		Intent intent2 = new Intent(context, Service_SnowWiFiMonitor.class);
		context.startService(intent2);
		 
		Intent intent3 = new Intent("com.exam.tab.TaskTimer");
		context.startService(intent3);
		
		Log.d("keep_oftenintent","startService");
		
		
		const_builder = new NotificationCompat.Builder(context);
		
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);

		Log.d("stop_unknownOverlapping","onReceive");
		
		Log.d("stop_unknownOverlapping","intent : "+intent.getAction());

		if (intent.getAction().startsWith("com.gueei")) {
			
			int id = intent.getIntExtra("widgetId", 0);
			((CoinBlockWidgetApp) context.getApplicationContext()).GetView(id).OnClick();

		}
		else if (intent.getAction().startsWith("com.exam.view.INTENT_OFTEN_FORMAT")){
			int id = intent.getIntExtra("widgetId2", 0);
			((CoinBlockWidgetApp) context.getApplicationContext()).GetView(id).OnOften();         

		}
		else if (intent.getAction().startsWith("com.exam.view.INTENT_EVOLVE_FORMAT")){ 
			int id = intent.getIntExtra("widgetId10", 0);
			((CoinBlockWidgetApp) context.getApplicationContext()).GetView(id).OnEvolve();

			Log.i("coinBlockWidgetProvider","provider - onenvolve");
		}
		else if (intent.getAction().startsWith("com.exam.view.INTENT_INIT_FORMAT")){ 
			int id = intent.getIntExtra("widgetId11", 0);
			((CoinBlockWidgetApp) context.getApplicationContext()).GetView(id).OnInit();

			Log.i("coinBlockWidgetProvider","provider - init");
		}

		// Custom Recevier
		// SMS
		else if (intent.getAction().startsWith("android.provider.Telephony.SMS_RECEIVED"))
		{
			int id = CoinBlockView.mWidgetId;
			Log.v("coinBlockWidgetProvider", "SMS Received");

			isSMSNotRead = true;
			Toast.makeText(context, "Get SMS", Toast.LENGTH_SHORT).show();
			
			((CoinBlockWidgetApp) context.getApplicationContext()).GetView(id).OnSMS();
			
			AppWidgetManager manager = AppWidgetManager.getInstance(context);
			this.onUpdate(context, manager, manager.getAppWidgetIds(new ComponentName(context, getClass())));
		}

		// Low battery
		/*
		else if (intent.getAction().startsWith("android.intent.action.BATTERY_CHANGED"))
		{
			int bLevel = intent.getIntExtra("level", 0);
			Log.v("coinBlockWidgetProvider", "Battery level changed: " + bLevel);
			
			
			
			
			Setting.nowBattery = bLevel;

			if(bLevel < 20)
				isBatteryLow = true;
			else
				isBatteryLow = false;
			
			
			
			
			//Toast.makeText(context, "Battery Changed", Toast.LENGTH_SHORT).show();

			AppWidgetManager manager = AppWidgetManager.getInstance(context);
			this.onUpdate(context, manager, manager.getAppWidgetIds(new ComponentName(context, getClass())));
		}
		*/

		// WiFi
		else if (intent.getAction().startsWith("android.net.wifi.STATE_CHANGE"))
		{/*
			Log.v("coinBlockWidgetProvider", "currentTime: " + Long.toString(System.currentTimeMillis()));
			Log.v("coinBlockWidgetProvider","wifi_cool: " + wifi_cool);
			
			if(System.currentTimeMillis() - wifi_cool >= 5000)
			{
				wifi_cool = System.currentTimeMillis();
				Log.v("coinBlockWidgetProvider", "Wifi Connect state changed");
				NetworkInfo netInfo = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
				isWifiConnected = netInfo.isConnected();
				Toast.makeText(context, "Wifi status changed", Toast.LENGTH_SHORT).show();
	
				int id = intent.getIntExtra("widgetId31", 0);
				
				AppWidgetManager manager = AppWidgetManager.getInstance(context);
				this.onUpdate(context, manager, manager.getAppWidgetIds(new ComponentName(context, getClass())));
				
				((CoinBlockWidgetApp) context.getApplicationContext()).GetView(id).OnWifi();
			}
			else
				Log.v("coinBlockWidgetProvider","Cooltime break");*/
		}

		// Plane mode
		else if (intent.getAction().startsWith("android.intent.action.AIRPLANE_MODE"))
		{
			int id = CoinBlockView.mWidgetId;
			
			if(Settings.System.getInt(context.getContentResolver(),Settings.System.AIRPLANE_MODE_ON, 0) == 1) {
				isPlaneMode = true;
				((CoinBlockWidgetApp) context.getApplicationContext()).GetView(id).OnPlaneModeOn();
			} else {
				isPlaneMode = false;
				((CoinBlockWidgetApp) context.getApplicationContext()).GetView(id).OnPlaneModeOff();
			}

			//Toast.makeText(context, "Plane mode status changed", Toast.LENGTH_SHORT).show();
			Log.v("coinBlockWidgetProvider", "isPlaneMode: " + isPlaneMode);

			AppWidgetManager manager = AppWidgetManager.getInstance(context);
			this.onUpdate(context, manager, manager.getAppWidgetIds(new ComponentName(context, getClass())));
		}

		// Bluetooth
		else if (intent.getAction().startsWith("android.bluetooth.adapter.action.STATE_CHANGED"))
		{
			Log.v("coinBlockWidgetProvider", "Bluetooth");
			BluetoothAdapter mBTAdapter = BluetoothAdapter.getDefaultAdapter();
			isBluetoothActivated = mBTAdapter.isEnabled();
			//Toast.makeText(context, "Bluetooth status changed", Toast.LENGTH_SHORT).show();

			AppWidgetManager manager = AppWidgetManager.getInstance(context);
			this.onUpdate(context, manager, manager.getAppWidgetIds(new ComponentName(context, getClass())));
		}

		// Power connected
		else if (intent.getAction().startsWith("android.intent.action.ACTION_POWER_CONNECTED"))
		{
			Log.v("coinBlockWidgetProvider", "Power Connected");
			isPowerConnected = true;
			//Toast.makeText(context, "Power connected", Toast.LENGTH_SHORT).show();

			AppWidgetManager manager = AppWidgetManager.getInstance(context);
			this.onUpdate(context, manager, manager.getAppWidgetIds(new ComponentName(context, getClass())));
			
			int id = intent.getIntExtra("widgetId32", 0);
			
			((CoinBlockWidgetApp) context.getApplicationContext()).GetView(id).OnPowerConnected();
		}

		// Power disconnected
		else if (intent.getAction().startsWith("android.intent.action.ACTION_POWER_DISCONNECTED"))
		{
			Log.v("coinBlockWidgetProvider", "Power Disconnected");
			isPowerConnected = false;
			//Toast.makeText(context, "Power disconnected", Toast.LENGTH_SHORT).show();

			AppWidgetManager manager = AppWidgetManager.getInstance(context);
			this.onUpdate(context, manager, manager.getAppWidgetIds(new ComponentName(context, getClass())));
		}

	/*	// Headset
		else if (intent.getAction().startsWith("android.intent.action.HEADSET_PLUG"))
		{
			int id = CoinBlockView.mWidgetId;
			Log.v("coinBlockWidgetProvider", "Entering headset");

			if(intent.getIntExtra("state", -1) == 1)
			{
				Toast.makeText(context, "Headset connected", Toast.LENGTH_SHORT).show();
				isHeadset = true;
				((CoinBlockWidgetApp) context.getApplicationContext()).GetView(id).OnHeadsetConnected();
			}	

			else
			{
				Toast.makeText(context, "Headset disconnected", Toast.LENGTH_SHORT).show();
				isHeadset = false;
			}

			AppWidgetManager manager = AppWidgetManager.getInstance(context);
			this.onUpdate(context, manager, manager.getAppWidgetIds(new ComponentName(context, getClass())));
		}*/
		
		/*
		// PC connected (not working)
		else if (intent.getAction().startsWith("android.hardware.usb.action.USB_ACCESSORY_ATTACHED"))
		{
			int id = CoinBlockView.mWidgetId;
			
			Log.v("coinBlockWidgetProvider", "USB Attached");
			isUsbAttached = true;
			Toast.makeText(context, "USB attached", Toast.LENGTH_SHORT).show();

			AppWidgetManager manager = AppWidgetManager.getInstance(context);
			this.onUpdate(context, manager, manager.getAppWidgetIds(new ComponentName(context, getClass())));
			
			((CoinBlockWidgetApp) context.getApplicationContext()).GetView(id).OnPowerConnected();
		}

		// PC disconnected (I don't sure it working or not)
		else if (intent.getAction().startsWith("android.hardware.usb.action.USB_ACCESSORY_DETACHED"))
		{
			Log.v("coinBlockWidgetProvider", "USB Detached");
			isUsbAttached = false;
			Toast.makeText(context, "USB detached", Toast.LENGTH_SHORT).show();

			AppWidgetManager manager = AppWidgetManager.getInstance(context);
			this.onUpdate(context, manager, manager.getAppWidgetIds(new ComponentName(context, getClass())));
		}
		*/
	}
}