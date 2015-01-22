package com.jym.service;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.jym.Broadcast.BroadcastDefinition;
import com.jym.helper.TextPref;
import com.jym.patpat.PatpatState;
import com.jym.patpat.PatpatView;
import com.jym.patpat.PatpatWidgetApp;
import com.jym.patpat.R;

/** AsyncTask<A, B, C> 
 *    - A : parameters' type of doInBackground
 *    - B : parameters' type of onProgressUpdate
 *    - C : parameters' type of onPostExecute */
public class TaskTimer extends AsyncTask<String, String, String> {
	public TextView timer = null;
	public static long time = 0;
	public static boolean isCanceled = false;

	private static final String RESULT_SUCCESS   = "1";
	private static final String RESULT_FAIL      = "0";

	private static final int TEXT_COLOR_NORMAL   = 0xFF000000;
	private static final int TEXT_COLOR_FINISHED = 0xFFFF0000;

	private static long startTime = 0;
	
	public static String level;
	public static int level_index = 0;

	ArrayList<String> evolveCountArray;
	ArrayList<String> evolveTimerArray;

	public static int clickCount;

	//Making hidden Action
	public static int CliCount_left = 0 ;
	public static int CliCount_right = 0 ;

	public TaskTimer(ArrayList<String> inputCountArray, ArrayList<String> inputTimerArray)
	{
		evolveCountArray = inputCountArray;
		evolveTimerArray = inputTimerArray;
		Log.d("bugfix", "진화 카운트 받아옴 : " + evolveCountArray.get(0));
		Log.d("bugfix", "진화 타이머 받아옴 : " + evolveTimerArray.get(0));

	}

	public void setTime(int time) {
		this.time = time; 
	}

	public void SetTimer(int n)
	{
		time += n;
	}

	public long GetTime()
	{
		return time;
	}

	@Override
	public void onCancelled()
	{
		Log.d("erro_writePref","onCancelled");

		tPref.Ready();
		//		startTime = tPref.ReadLong("startTime", 0);	
		Log.d("IntentService_TaskTimer","startTime "+startTime);

		//make new starttime while task timer is stopped
		startTime += time*1000;			
		tPref.WriteLong("startTime", startTime);	
		tPref.CommitWrite();

		Log.d("TaskTimer","newstartTime "+startTime);
	}

	public void onRestartset()
	{
		Log.d("erro_writePref","onRestartset");

		tPref.Ready();
		time = tPref.ReadLong("time", 0);

		startTime = System.currentTimeMillis() - time*1000;
		tPref.WriteLong("startTime", startTime);	
		tPref.CommitWrite();
	}

	public void StartSetting()
	{
		tPref.Ready();
		startTime = tPref.ReadLong("startTime", 0);	

		// It should work only one time
		if(startTime == 0){
			startTime = System.currentTimeMillis();		
			tPref.WriteLong("startTime", startTime);	
			tPref.CommitWrite();
		}
		Log.d("keep_oftenintent","StartSetting");
	}

	// Preference
	private TextPref tPref;	
	public static TextPref ePref;	

	@Override
	protected void onPreExecute() { 
		Log.d("erro_writePref","onPreExecute");

		try {
			tPref = new TextPref("mnt/sdcard/SsdamSsdam/timerpref.pref");
			ePref = new TextPref("mnt/sdcard/SsdamSsdam/entitypref.pref");
		} catch (Exception e) { 
			e.printStackTrace();
		}
		StartSetting();		

		//		timer.setTextColor(TEXT_COLOR_NORMAL);
		Log.d("keep_oftenintent","End - onPreExecute");
	}

	@Override
	protected String doInBackground(String... params) {
		Log.d("fix_futuretask","doInBackground");

		while (time >= 0 && !isCanceled) {		
			try {
				Thread.sleep(1000);
				Log.d("fix_futuretask"," Thread.sleep(1000)");
			} catch (InterruptedException e) {
				Log.d("fix_futuretask","Thread.error : "+e);
				e.printStackTrace();
			}

			ePref.Ready();
			level = ePref.ReadString("level", "box");
			clickCount = ePref.ReadInt("click_count", 0);
			ePref.EndReady();
			
			Log.w("textPref","Read_level : "+level);

			Log.i("seperated_ClickCount","clickcount_1_At_TaskTimer : "+PatpatState.clickcount);
			time = (System.currentTimeMillis() - startTime) / 1000;
			Log.d("fix_futuretask","End_doInBackground");
			publishProgress(); // trigger onProgressUpdate()
		}

		return RESULT_SUCCESS;
	}

	/** this method is used by doInBackground
	 *  because it's called on the main thread (UI thread),
	 *  you can directly modify UI */
	@Override
	protected void onProgressUpdate(String... value) {
		
		SetLevelIndex();
		int evolveCount = Integer.parseInt(evolveCountArray.get(level_index));
		int evolveTimer = Integer.parseInt(evolveTimerArray.get(level_index));

		if(/*time <= evolveTimer &&*/ clickCount >= evolveCount && level_index < evolveCountArray.size()-1) {
			Log.d("evolve_test", "진화조건 성립: " + level);
			clickCount = 0;
			time = 0;
			
			RemoteViews rviews = new RemoteViews(PatpatWidgetApp.getApplication().getPackageName(),
					R.layout.patpat_widget);
			updateEvolveIntent(rviews, PatpatWidgetApp.getApplication());
		} else if (time % 10 == 7){
			Log.d("fix_futuretask","onOften_at_time : "+time); 
			updateOftenIntent(PatpatWidgetApp.getApplication());

		} else {
			tPref.Ready();
			tPref.WriteLong("time", time);
			tPref.CommitWrite();
		}
	}

	private static void updateHiddenIntent(Context context) {
		// TODO Auto-generated method stub
		int mWidgetId = PatpatView.mWidgetId;

		Log.d("updateHiddenIntent","mWidgetId : "+mWidgetId);

		Intent intent = new Intent(String.format(BroadcastDefinition.INTENT_HIDDEN_FORMAT, mWidgetId));
		intent.putExtra("widgetId", mWidgetId);
		context.sendBroadcast(intent);
	}

	private static void updateOftenIntent(Context context) {
		// TODO Auto-generated method stub
		int mWidgetId = PatpatView.mWidgetId;

		Log.d("keep_oftenintent","mWidgetId : "+mWidgetId);

		Intent intent = new Intent(String.format(BroadcastDefinition.INTENT_OFTEN_FORMAT, mWidgetId));
		intent.putExtra("widgetId", mWidgetId);
		context.sendBroadcast(intent);		
	}

	private  void updateEvolveIntent(RemoteViews rviews, Context context) {
		// TODO Auto-generated method stub				
		int mWidgetId = PatpatView.mWidgetId;

		Log.d("evolve_test", "updateEvolveIntent");
		
		Log.w("evolve_test", "mWidgetId : "+mWidgetId);

		Intent intent = new Intent(String.format(BroadcastDefinition.INTENT_INIT_FORMAT, mWidgetId));
		intent.putExtra("widgetId", mWidgetId);

		context.sendBroadcast(intent);

		Intent intent2 = new Intent(String.format(BroadcastDefinition.INTENT_EVOLVE_FORMAT, mWidgetId));
		intent2.putExtra("widgetId", mWidgetId);

		context.sendBroadcast(intent2);
		
		PatpatView.Preload();

		Log.d("TaskTimer","updateEvolveIntent");
	}

	/** this method is executed right AFTER doInBackground()
	 *  on the main thread (UI thread) */
	@Override
	protected void onPostExecute(String result) {
		if(RESULT_SUCCESS.equals(result))
			Log.v("fix_futuretask","onPostExecute : result "+result);
	}
	
	public static void SetLevel() {
		
		Log.w("textPref","SetLevel_level : "+level);
		Log.w("textPref","SetLevel_level_index : "+level_index);
		
		TaskTimer.ePref.Ready();
		switch(level_index) {
		case 0:
			TaskTimer.ePref.WriteString("level", "box");
			break;
			
		case 1:
			TaskTimer.ePref.WriteString("level", "slime");
			break;
			
		case 2:
			TaskTimer.ePref.WriteString("level", "baby");
			break;
		}
		TaskTimer.ePref.CommitWrite();
	}
	
	public static void SetLevelIndex() {
		if(level.equals("box"))
			level_index = 0;
		else if(level.equals("slime"))
			level_index = 1;
		else if(level.equals("baby"))
			level_index = 2;
	}
}