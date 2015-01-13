package com.jym.patpat;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.jym.helper.TextPref;
import com.jym.helper.XmlMapping;


public class PatpatWidgetProvider extends AppWidgetProvider {
	// Init pref files at application class
	static String parentPath = Environment.getExternalStorageDirectory()
										  .getAbsolutePath() + "SsdamSsdam";

	// service Switch for onetime run service
	public static TextPref mPref;
	boolean serviceSwitch;
	File saveDir;

	@Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        Log.d("atActivityRemoved","onDeleted");
        for (int x : appWidgetIds) {
                ((PatpatWidgetApp) context.getApplicationContext()).DeleteWidget(x);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        Log.d("refreshing_RemoteView","onUpdate");
        for (int i=0; i<appWidgetIds.length; i++)
        {
        	((PatpatWidgetApp) context.getApplicationContext()).UpdateWidget(appWidgetIds[i]);
        }
    }

	@Override
	public void onEnabled(Context context) {
		super.onEnabled(context);
		
		Log.d("serviceSwitch", "parentPath : "+parentPath);
		
		// Init pref files at application class
		saveDir = new File(parentPath); // dir : 생성하고자 하는 경로
		Log.d("serviceSwitch","new File");
		
		if (!saveDir.exists()) {
			Log.d("serviceSwitch","aveDir.exists()");
			saveDir.mkdirs();
		}
		
		Log.d("serviceSwitch","End File");
		
		try {
			mPref = new TextPref("mnt/sdcard/SsdamSsdam/textpref.pref");
		} catch (Exception e) {
			e.printStackTrace();
			Log.d("serviceSwitch","e : "+e);
		}   
		
		mPref.Ready();
		Log.d("serviceSwitch","mPref.Ready();");
		serviceSwitch = mPref.ReadBoolean("serviceSwitch", true);
		mPref.EndReady();

		Log.d("Stop_renotify","onEnabled");
		Log.d("Stop_renotify","serviceSwitch : "+serviceSwitch);
		
		
		if(serviceSwitch){
			Intent intent = new Intent("com.jym.service.IntentService_DeviceEvents");
			context.startService(intent);
			
			Intent intent3 = new Intent("com.jym.service.IntentService_TaskTimer");
			
			HashMap evolvePointMap = XmlMapping.fieldMapping("test1", context, "evolve");
			ArrayList<String> evolvePointArray = (ArrayList<String>) evolvePointMap.get("evolve_point");
			ArrayList<String> evolveTimerArray = (ArrayList<String>) evolvePointMap.get("evolve_timer");
			
			intent3.putStringArrayListExtra("evolvePoint", evolvePointArray);
			intent3.putStringArrayListExtra("evolveTimer", evolveTimerArray);
			
			context.startService(intent3);
			Toast.makeText(context, "startService", Toast.LENGTH_SHORT).show();
		
			mPref.Ready();
			mPref.WriteBoolean("serviceSwitch", false);
			mPref.CommitWrite();
		}
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);

		Log.d("fix_futuretask",
				"onReceive_intent.getAction() : " + intent.getAction());

		if (intent.getAction().startsWith("click.com")) {
			int id = intent.getIntExtra("widgetId", 0);
			((PatpatWidgetApp) context.getApplicationContext()).GetView(id)
					.OnClick();
		} else if (intent.getAction().startsWith("click_right.com")) {
			int id = intent.getIntExtra("widgetId", 0);
			((PatpatWidgetApp) context.getApplicationContext()).GetView(id)
					.OnClick_right();

		} else if (intent.getAction().startsWith("com.jym.view.INTENT_HIDDEN_FORMAT")) {
			int id = intent.getIntExtra("widgetId", 0);
			((PatpatWidgetApp) context.getApplicationContext()).GetView(id)
					.OnClick();
			((PatpatWidgetApp) context.getApplicationContext()).GetView(id)
			.OnClick();
			((PatpatWidgetApp) context.getApplicationContext()).GetView(id)
			.OnClick();
		} else if (intent.getAction().startsWith("com.jym.patpat.INTENT_EVOLVE_FORMAT")) {
			Log.d("bugfix", "진화명령 받았습니다!!");
			int id = intent.getIntExtra("widgetId", 0);
			((PatpatWidgetApp) context.getApplicationContext()).GetView(id).OnEvolve();
		}
	}
}