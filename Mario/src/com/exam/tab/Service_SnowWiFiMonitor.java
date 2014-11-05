package com.exam.tab;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.util.Log;

import com.exam.helper.SnowWiFiMonitor;
import com.exam.helper.SnowWiFiMonitor.OnChangeNetworkStatusListener;
import com.exam.view.CoinBlockView;

public class Service_SnowWiFiMonitor extends Service {
	
	//NotificationCompat.Builder const_builder;	
	//private static boolean isBatteryLow = false;
	
	
	
	
	public int onStartCommand (Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		
		Log.d("Service_SnowWiFiMonitor","onStartCommand");
		
		
		//IntentFilter filter = new IntentFilter();
		//filter.addAction(Intent.ACTION_CALL);
		
		
		mSnMonitor = new SnowWiFiMonitor(this);
		mSnMonitor.setOnChangeNetworkStatusListener(SnowChangedListener);
		
		registerReceiver(mSnMonitor, new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION));
		
		
		Log.d("Service_SnowWiFiMonitor","registerReceiver"+mSnMonitor);
		
		return START_STICKY;
		
		
		
	}
	
	

	public IBinder onBind(Intent arg0) {
		return null;
	}
	
	 
	public void onCreate()
	{
		
		
		//mSnMonitor = new SnowWiFiMonitor(this);
		
		Log.d("Service_SnowWiFiMonitor","onCreate");
		
		
		
		//const_builder = new NotificationCompat.Builder(Service_SnowWiFiMonitor.this);
		
		
		
	}
	
	public void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mSnMonitor);
	}
	
	
	//BroadcastReceiver mSnMonitor ;
	
	
	SnowWiFiMonitor.OnChangeNetworkStatusListener SnowChangedListener 
	= new SnowWiFiMonitor.OnChangeNetworkStatusListener()
{
	

	@Override
	public void OnChanged(Intent intent, Context context, int status) {
		switch(status)
		{
		case SnowWiFiMonitor.WIFI_STATE_DISABLED:
			Log.i("Service_SnowWiFiMonitor", "[WifiMonitor] WIFI_STATE_DISABLED");
			break;
			
		case SnowWiFiMonitor.WIFI_STATE_DISABLING:
			Log.i("Service_SnowWiFiMonitor", "[WifiMonitor] WIFI_STATE_DISABLING");
			break;
			
		case SnowWiFiMonitor.WIFI_STATE_ENABLED:
			Log.i("Service_SnowWiFiMonitor", "[WifiMonitor] WIFI_STATE_ENABLED");
			break;
			
		case SnowWiFiMonitor.WIFI_STATE_ENABLING:
			Log.i("Service_SnowWiFiMonitor", "[WifiMonitor] WIFI_STATE_ENABLING");
			break;
			
		case SnowWiFiMonitor.WIFI_STATE_UNKNOWN:
			Log.i("Service_SnowWiFiMonitor", "[WifiMonitor] WIFI_STATE_UNKNOWN");
			break;
			
		case SnowWiFiMonitor.NETWORK_STATE_CONNECTED:
			Log.i("Service_SnowWiFiMonitor", "[WifiMonitor] NETWORK_STATE_CONNECTED");
			break;
			
		case SnowWiFiMonitor.NETWORK_STATE_CONNECTING:
			Log.i("Service_SnowWiFiMonitor", "[WifiMonitor] NETWORK_STATE_CONNECTING");
			break;
			
		case SnowWiFiMonitor.NETWORK_STATE_DISCONNECTED:
			Log.i("Service_SnowWiFiMonitor", "[WifiMonitor] NETWORK_STATE_DISCONNECTED");
			break;
			
		case SnowWiFiMonitor.NETWORK_STATE_DISCONNECTING:
			Log.i("Service_SnowWiFiMonitor", "[WifiMonitor] NETWORK_STATE_DISCONNECTING");
			break;
			
		case SnowWiFiMonitor.NETWORK_STATE_SUSPENDED:
			Log.i("Service_SnowWiFiMonitor", "[WifiMonitor] NETWORK_STATE_SUSPENDED");
			break;
			
		case SnowWiFiMonitor.NETWORK_STATE_UNKNOWN:
			Log.i("Service_SnowWiFiMonitor", "[WifiMonitor] WIFI_STATE_DISABLED");
			break;
 	
		}		
	}
};
	
	
SnowWiFiMonitor mSnMonitor ;



	
	/*

		SnowWiFiMonitor mSnMonitor = new SnowWiFiMonitor(this) {
		
		
		
		public final static int WIFI_STATE_DISABLED 		= 0x00;
		public final static int WIFI_STATE_DISABLING	 	= WIFI_STATE_DISABLED 		+ 1;
		public final static int WIFI_STATE_ENABLED 			= WIFI_STATE_DISABLING 		+ 1;
		public final static int WIFI_STATE_ENABLING 		= WIFI_STATE_ENABLED 		+ 1;
		public final static int WIFI_STATE_UNKNOWN 			= WIFI_STATE_ENABLING 		+ 1;
		public final static int NETWORK_STATE_CONNECTED 	= WIFI_STATE_UNKNOWN 		+ 1;
		public final static int NETWORK_STATE_CONNECTING 	= NETWORK_STATE_CONNECTED 	+ 1;
		public final static int NETWORK_STATE_DISCONNECTED 	= NETWORK_STATE_CONNECTING 	+ 1;
		public final static int NETWORK_STATE_DISCONNECTING = NETWORK_STATE_DISCONNECTED + 1;
		public final static int NETWORK_STATE_SUSPENDED 	= NETWORK_STATE_DISCONNECTING + 1;
		public final static int NETWORK_STATE_UNKNOWN 		= NETWORK_STATE_SUSPENDED 	+ 1;
		
		
		
		private WifiManager 		m_WifiManager = null;
		private ConnectivityManager m_ConnManager = null;
		private OnChangeNetworkStatusListener m_OnChangeNetworkStatusListener = null;
		
	
		
		public void setOnChangeNetworkStatusListener(OnChangeNetworkStatusListener listener)
		{
			m_OnChangeNetworkStatusListener = listener;
		}
		
		
		@Override
		public void onReceive(Context context, Intent intent)
		{
			if (m_OnChangeNetworkStatusListener == null)
			{
				return;
			}
			
			String strAction = intent.getAction();
			
			if (strAction.equals(WifiManager.WIFI_STATE_CHANGED_ACTION))
			{
				switch(m_WifiManager.getWifiState())
				{
				case WifiManager.WIFI_STATE_DISABLED:
					m_OnChangeNetworkStatusListener.OnChanged(intent, context, WIFI_STATE_DISABLED);
					
					int id = CoinBlockView.mWidgetId;
					Log.d("SnowWiFiMonitor","id  "+id+"  "+ context);
					
					((CoinBlockWidgetApp) context.getApplicationContext()).GetView(id).OnWifi();
					
					Log.d("SnowWiFiMonitor","CoinBlockWidgetApp  "+id+"  "+ context);
					
					
					break;
					
				case WifiManager.WIFI_STATE_DISABLING:
					m_OnChangeNetworkStatusListener.OnChanged(intent, context, WIFI_STATE_DISABLING);
					break;
					
				case WifiManager.WIFI_STATE_ENABLED:
					m_OnChangeNetworkStatusListener.OnChanged(intent, context, WIFI_STATE_ENABLED);
					break;
					
				case WifiManager.WIFI_STATE_ENABLING:
					m_OnChangeNetworkStatusListener.OnChanged(intent, context, WIFI_STATE_ENABLING);
					break;
					
				case WifiManager.WIFI_STATE_UNKNOWN:
					m_OnChangeNetworkStatusListener.OnChanged(intent, context, WIFI_STATE_UNKNOWN);
					break;
				}
			}
			else if (strAction.equals(WifiManager.NETWORK_STATE_CHANGED_ACTION))
			{
				NetworkInfo networkInfo = m_ConnManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
				if ( (networkInfo != null) && (networkInfo.isAvailable() == true) )
				{
					if (networkInfo.getState() == NetworkInfo.State.CONNECTED)
					{
						m_OnChangeNetworkStatusListener.OnChanged(intent, context, NETWORK_STATE_CONNECTED);
					}
					else if (networkInfo.getState() == NetworkInfo.State.CONNECTING)
					{
						m_OnChangeNetworkStatusListener.OnChanged(intent, context, NETWORK_STATE_CONNECTING);
					}
					else if (networkInfo.getState() == NetworkInfo.State.DISCONNECTED)
					{
						m_OnChangeNetworkStatusListener.OnChanged(intent, context, NETWORK_STATE_DISCONNECTED);
					}
					else if (networkInfo.getState() == NetworkInfo.State.DISCONNECTING)
					{
						m_OnChangeNetworkStatusListener.OnChanged(intent, context, NETWORK_STATE_DISCONNECTING);
					}
					else if (networkInfo.getState() == NetworkInfo.State.SUSPENDED)
					{
						m_OnChangeNetworkStatusListener.OnChanged(intent, context, NETWORK_STATE_SUSPENDED);
					}
					else if (networkInfo.getState() == NetworkInfo.State.UNKNOWN)
					{
						m_OnChangeNetworkStatusListener.OnChanged(intent, context, NETWORK_STATE_UNKNOWN);
					}
				}
			}
		}
	};
	*/
	
	
	
}



