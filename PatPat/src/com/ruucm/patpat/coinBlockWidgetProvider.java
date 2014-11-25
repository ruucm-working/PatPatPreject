package com.ruucm.patpat;

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


public class coinBlockWidgetProvider extends AppWidgetProvider {
	
	
	@Override
    public void onDeleted(Context context, int[] appWidgetIds) {
            super.onDeleted(context, appWidgetIds);
            for (int x : appWidgetIds) {
                    ((PatpatWidgetApp) context.getApplicationContext()).DeleteWidget(x);
            }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
            super.onUpdate(context, appWidgetManager, appWidgetIds);

            for (int i=0; i<appWidgetIds.length; i++)
            {
                    ((PatpatWidgetApp) context.getApplicationContext()).UpdateWidget(appWidgetIds[i]);
            }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
            super.onReceive(context, intent);
            if (intent.getAction().startsWith("com.gueei")) {
                    int id = intent.getIntExtra("widgetId", 0);
                    ((PatpatWidgetApp) context.getApplicationContext()).GetView(id).OnClick();
            }
    }
}