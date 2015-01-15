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

	/*
>>>>>>> origin/notifiAndXmlUpdate
	boolean init = false;
	boolean lv0_1;
	boolean lv0_2;
	boolean lv1;
	boolean lv2;
	boolean lv3_1;
<<<<<<< HEAD

	public static String INTENT_HIDDEN_FORMAT = "com.exam.view.INTENT_HIDDEN_FORMAT";
	public static String INTENT_OFTEN_FORMAT = "com.exam.view.INTENT_OFTEN_FORMAT";
	public String INTENT_EVOLVE_FORMAT = "com.exam.view.INTENT_EVOLVE_FORMAT";
	public String INTENT_INIT_FORMAT = "com.exam.view.INTENT_INIT_FORMAT";

=======
	 */

	int level;

	ArrayList<String> evolveCountArray;
	ArrayList<String> evolveTimerArray;

	public static int clickCount;

	/*
>>>>>>> origin/notifiAndXmlUpdate
	int CliCountInit ;
	int CliCount0_1 ;
	int CliCount0_2 ;
	int CliCount1 ;
	int CliCount2 ;
<<<<<<< HEAD

=======
	 */

	//Making hidden Action
	public static int CliCount3_1_left = 0 ;
	public static int CliCount3_1_right = 0 ;
	/*	public static int temp_Count = 0 ;
	public static int temp_Count2 = 0 ;
	 */

	/*	public void setTextView1(int textViewId) {
		timer = (TextView)IntroActivity.getInstance()
				.findViewById(textViewId);
	}*/

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
			level = ePref.ReadInt("level", 0);
			clickCount = ePref.ReadInt("click_count", 0);
			ePref.EndReady();

			Log.i("seperated_ClickCount","clickcount_3_1_At_TaskTimer : "+PatpatState.clickcount_3_1);
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

		int evolveCount = Integer.parseInt(evolveCountArray.get(level));
		int evolveTimer = Integer.parseInt(evolveTimerArray.get(level));

		if(time <= evolveTimer && clickCount >= evolveCount && level < evolveCountArray.size()-1) {
			Log.d("bugfix", "진화조건 성립");
			level++;
			ePref.Ready();
			ePref.WriteInt("level", level);
			ePref.CommitWrite();

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

		Log.d("bugfix", "헛! 진화!!!");

		Intent intent = new Intent(String.format(BroadcastDefinition.INTENT_INIT_FORMAT, mWidgetId));
		intent.putExtra("widgetId", mWidgetId);

		context.sendBroadcast(intent);

		Intent intent2 = new Intent(String.format(BroadcastDefinition.INTENT_EVOLVE_FORMAT, mWidgetId));
		intent2.putExtra("widgetId", mWidgetId);

		context.sendBroadcast(intent2);

		Log.d("TaskTimer","updateEvolveIntent");
	}

	/** this method is executed right AFTER doInBackground()
	 *  on the main thread (UI thread) */
	@Override
	protected void onPostExecute(String result) {
		if(RESULT_SUCCESS.equals(result))
			Log.v("fix_futuretask","onPostExecute : result "+result);
	}
}