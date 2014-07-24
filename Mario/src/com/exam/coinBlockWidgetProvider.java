package com.exam;


import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbManager;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.exam.helper.SnowWiFiMonitor;

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
			Log.d("coinBlockWidgetProvider","onUpdate"+appWidgetIds);
		}
		Log.d("coinBlockWidgetProvider","onUpdate;");
		
		
		
		// Run service
		
		Intent nintent = new Intent(context, Service_Notify.class);
		context.startService(nintent);
		
		Intent intent = new Intent(context, Service_BatteryGauge.class);
		context.startService(intent);
		
		
		Log.d("coinBlockWidgetProvider","startService intent1  "+intent);
		
		Intent intent2 = new Intent(context, Service_SnowWiFiMonitor.class);
		context.startService(intent2);
		 
		
		Log.d("coinBlockWidgetProvider","startService intent2  "+intent2);
		
		
		/*
		//check wifi
		m_SnowWifiMonitor = new SnowWiFiMonitor(context);
        m_SnowWifiMonitor.setOnChangeNetworkStatusListener(SnowChangedListener);
        
        Log.i("coinBlockWidgetProvider", "m_SnowWifiMonitor ");
        
        //registerReceiver(m_SnowWifiMonitor, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        context.getApplicationContext().registerReceiver(m_SnowWifiMonitor, new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION));
		
        Log.i("coinBlockWidgetProvider", "registerReceiver ");
		
        
        */
		const_builder = new NotificationCompat.Builder(context);
		
	}
	
/*
	
	SnowWiFiMonitor.OnChangeNetworkStatusListener SnowChangedListener 
	= new SnowWiFiMonitor.OnChangeNetworkStatusListener()
{
	@Override
	public void OnChanged(Intent intent, Context context, int status) 
	{
		switch(status)
		{
		case SnowWiFiMonitor.WIFI_STATE_DISABLED:
			Log.i("coinBlockWidgetProvider", "[WifiMonitor] WIFI_STATE_DISABLED");
			break;
			
		case SnowWiFiMonitor.WIFI_STATE_DISABLING:
			Log.i("coinBlockWidgetProvider", "[WifiMonitor] WIFI_STATE_DISABLING");
			
			int id = intent.getIntExtra("widgetId31", 0);	
			((CoinBlockWidgetApp) context.getApplicationContext()).GetView(73).OnWifi();
			Log.d("coinBlockWidgetProvider", "OnWifi()  "+id+"  "+intent+"  "+context);
			
			
			
			break;
			
		case SnowWiFiMonitor.WIFI_STATE_ENABLED:
			Log.i("coinBlockWidgetProvider", "[WifiMonitor] WIFI_STATE_ENABLED");
			break; 
			 
		case SnowWiFiMonitor.WIFI_STATE_ENABLING:
			Log.i("coinBlockWidgetProvider", "[WifiMonitor] WIFI_STATE_ENABLING");
			break;
			
		case SnowWiFiMonitor.WIFI_STATE_UNKNOWN:
			Log.i("coinBlockWidgetProvider", "[WifiMonitor] WIFI_STATE_UNKNOWN");
			break;
			
		case SnowWiFiMonitor.NETWORK_STATE_CONNECTED:
			Log.i("coinBlockWidgetProvider", "[WifiMonitor] NETWORK_STATE_CONNECTED");
			break;
			
		case SnowWiFiMonitor.NETWORK_STATE_CONNECTING:
			Log.i("coinBlockWidgetProvider", "[WifiMonitor] NETWORK_STATE_CONNECTING");
			break;
			
		case SnowWiFiMonitor.NETWORK_STATE_DISCONNECTED:
			Log.i("coinBlockWidgetProvider", "[WifiMonitor] NETWORK_STATE_DISCONNECTED");
			
			
			break;
			
		case SnowWiFiMonitor.NETWORK_STATE_DISCONNECTING:
			Log.i("coinBlockWidgetProvider", "[WifiMonitor] NETWORK_STATE_DISCONNECTING");
			break;
			
		case SnowWiFiMonitor.NETWORK_STATE_SUSPENDED:
			Log.i("coinBlockWidgetProvider", "[WifiMonitor] NETWORK_STATE_SUSPENDED");
			break;
			
		case SnowWiFiMonitor.NETWORK_STATE_UNKNOWN:
			Log.i("coinBlockWidgetProvider", "[WifiMonitor] WIFI_STATE_DISABLED");
			break;
 	
		}
	}
};
	*/
	

	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);

		Log.d("coinBlockWidgetProvider","onReceive");

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

			Log.d("tag2","provider - onenvolve");
		}
		else if (intent.getAction().startsWith("com.exam.view.INTENT_INIT_FORMAT")){ 
			int id = intent.getIntExtra("widgetId11", 0);
			((CoinBlockWidgetApp) context.getApplicationContext()).GetView(id).OnInit();

			Log.d("tag2","provider - onenvolve");
		}

		// Custom Recevier
		// SMS
		else if (intent.getAction().startsWith("android.provider.Telephony.SMS_RECEIVED"))
		{
			Log.v("coinBlockWidgetProvider", "SMS Received");

			isSMSNotRead = true;
			Toast.makeText(context, "Get SMS", Toast.LENGTH_SHORT).show();
			
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
			if(Settings.System.getInt(context.getContentResolver(),Settings.System.AIRPLANE_MODE_ON, 0) == 1)
				isPlaneMode = true;
			else
				isPlaneMode = false;

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

		// Headset
		else if (intent.getAction().startsWith("android.intent.action.HEADSET_PLUG"))
		{
			Log.v("coinBlockWidgetProvider", "Entering headset");

			if(intent.getIntExtra("state", -1) == 1)
			{
				Toast.makeText(context, "Headset connected", Toast.LENGTH_SHORT).show();
				isHeadset = true;
			}	

			else
			{
				Toast.makeText(context, "Headset disconnected", Toast.LENGTH_SHORT).show();
				isHeadset = false;
			}

			AppWidgetManager manager = AppWidgetManager.getInstance(context);
			this.onUpdate(context, manager, manager.getAppWidgetIds(new ComponentName(context, getClass())));
		}

		// PC connected (not working)
		else if (intent.getAction().startsWith("android.hardware.usb.action.USB_ACCESSORY_ATTACHED"))
		{
			Log.v("coinBlockWidgetProvider", "USB Attached");
			isUsbAttached = true;
			Toast.makeText(context, "USB attached", Toast.LENGTH_SHORT).show();

			AppWidgetManager manager = AppWidgetManager.getInstance(context);
			this.onUpdate(context, manager, manager.getAppWidgetIds(new ComponentName(context, getClass())));
			
			int id = intent.getIntExtra("widgetId41", 0);
			
	//		((CoinBlockWidgetApp) context.getApplicationContext()).GetView(id).OnPowerConnected();
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
	}
}