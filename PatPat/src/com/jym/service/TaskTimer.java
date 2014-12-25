package com.jym.service;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.jym.helper.TextPref;
import com.jym.patpat.PatpatView;
import com.jym.patpat.PatpatWidgetApp;
import com.jym.patpat.R;

/** AsyncTask<A, B, C> 
 *    - A : parameters' type of doInBackground
 *    - B : parameters' type of onProgressUpdate
 *    - C : parameters' type of onPostExecute */
public class TaskTimer extends AsyncTask<String, String, String> {


	public static boolean isCanceled = false;

	private static final String RESULT_SUCCESS   = "1";
	private static final String RESULT_FAIL      = "0";

	private static final int TEXT_COLOR_NORMAL   = 0xFF000000;
	private static final int TEXT_COLOR_FINISHED = 0xFFFF0000;

	private static long startTime = 0;

	public TextView timer = null;
	public static long time = 0;
	
	
	boolean init = false;
	boolean lv0_1;
	boolean lv0_2;
	boolean lv1;
	boolean lv2;
	boolean lv3_1;
	
	public static String INTENT_HIDDEN_FORMAT = "com.exam.view.INTENT_HIDDEN_FORMAT";
	public static String INTENT_OFTEN_FORMAT = "com.exam.view.INTENT_OFTEN_FORMAT";
	public String INTENT_EVOLVE_FORMAT = "com.exam.view.INTENT_EVOLVE_FORMAT";
	public String INTENT_INIT_FORMAT = "com.exam.view.INTENT_INIT_FORMAT";
	
	
	
	int CliCountInit ;
	int CliCount0_1 ;
	int CliCount0_2 ;
	int CliCount1 ;
	int CliCount2 ;
	
	//Making hidden Action
	public static int CliCount3_1_left = 0 ;
	public static int CliCount3_1_right = 0 ;
	public static int temp_Count = 0 ;
	public static int temp_Count2 = 0 ;
	

/*	public void setTextView1(int textViewId) {
		timer = (TextView)IntroActivity.getInstance()
				.findViewById(textViewId);
	}*/

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
 

		
		
		Log.d("fix_futuretask","doInBackground(1");


		while (time >= 0 && !isCanceled) {
			
			
			
		 try {
			Thread.sleep(1000);
			Log.v("fix_futuretask"," Thread.sleep(1");
		} catch (InterruptedException e) {
			Log.v("fix_futuretask","Thread.error : "+e);
			e.printStackTrace();
			
		} // one second sleep
			
		
		
		ePref.Ready();

		// State Variable
		init = ePref.ReadBoolean("initstate", false);
		lv0_1 = ePref.ReadBoolean("lv0_1state", false);
		lv0_2 = ePref.ReadBoolean("lv0_2state", false);
		lv1 = ePref.ReadBoolean("lv1state", false);
		lv2 = ePref.ReadBoolean("lv2state", false);
		lv3_1 = ePref.ReadBoolean("lv3_1state", false);
		
		
		Log.d("erro_writePref","write_StateVariable");
		

		// ClickCount Variable
		CliCount0_1 = ePref.ReadInt("clicount0_1", 0);
		CliCount0_2 = ePref.ReadInt("clicount0_2", 0);
		CliCount1 = ePref.ReadInt("clicount1", 0);
		CliCount2 = ePref.ReadInt("clicount2", 0);
		CliCount3_1_left = ePref.ReadInt("clicount3_1_left", 0);
		CliCount3_1_right = ePref.ReadInt("clicount3_1_right", 0);

		time = (System.currentTimeMillis() - startTime) / 1000;
		

		publishProgress(); // trigger onProgressUpdate()

	}

			return RESULT_SUCCESS;
	}
	
	
	/** this method is used by doInBackground
	 *  because it's called on the main thread (UI thread),
	 *  you can directly modify UI */
	@Override
	protected void onProgressUpdate(String... value) {
		// modify timer's text (remained time)
//		timer.setText("" + time);
		
		Log.d("fix_futuretask","onProgressUpdate");
		

//		while (time >= 0 && !isCanceled) {
			
		Log.v("fix_futuretask","time : "+time);
		Log.v("fix_futuretask","CliCount3_1_left : "+CliCount3_1_left);
		Log.v("fix_futuretask","CliCount3_1_right : "+CliCount3_1_right);
		
		//Making hidden Action
		if(temp_Count*temp_Count2 == 2){
			Log.v("add_hiddenAction","temp_Count == 3");
			updateHiddenIntent(PatpatWidgetApp.getApplication());
		}

		// init temp_Count for a second
		temp_Count = 0;
		temp_Count2 = 0;
		
		if (time >= 10 && time <= 12 && CliCount0_1 >= 3 && lv0_1) {
			lv0_1 = false;
			lv0_2 = true;
			ePref.WriteBoolean("lv0_1state", lv0_1);
			ePref.WriteBoolean("lv0_2state", lv0_2);
			ePref.CommitWrite();

			RemoteViews rviews = new RemoteViews(PatpatWidgetApp
					.getApplication().getPackageName(),
					R.layout.patpat_widget);
			updateEvolveIntent(rviews,
					PatpatWidgetApp.getApplication());

		}

		else if (time >= 20 && time <= 22 && CliCount1 >= 3 && lv1) {
			lv1 = false;
			lv2 = true;
			ePref.WriteBoolean("lv1state", lv1);
			ePref.WriteBoolean("lv2state", lv2);
			ePref.CommitWrite();

			RemoteViews rviews = new RemoteViews(PatpatWidgetApp
					.getApplication().getPackageName(),
					R.layout.patpat_widget);
			updateEvolveIntent(rviews,
					PatpatWidgetApp.getApplication());

		} else if (time >= 30 && time <= 32 && CliCount2 >= 3
				&& lv2) {
			lv2 = false;
			lv3_1 = true;
			ePref.WriteBoolean("lv2state", lv2);
			ePref.WriteBoolean("lv3_1state", lv3_1);
			ePref.CommitWrite();

			RemoteViews rviews = new RemoteViews(PatpatWidgetApp
					.getApplication().getPackageName(),
					R.layout.patpat_widget);
			updateEvolveIntent(rviews,
					PatpatWidgetApp.getApplication());

		} else if (time % 10 ==7){
			Log.d("keep_oftenintent","time : "+time); 
			Log.d("keep_oftenintent","PatpatWidgetApp.getApplication() : "+PatpatWidgetApp.getApplication()); 
			updateOftenIntent(PatpatWidgetApp.getApplication());
			
			 
		}

		else {
			
			tPref.Ready();

			tPref.WriteLong("time", time);
			tPref.CommitWrite();

		}
		

		

		}

		
		
//	}
	
	
	
	private static void updateHiddenIntent(Context context) {
		// TODO Auto-generated method stub
		int mWidgetId = PatpatView.mWidgetId;
		
		Log.d("updateHiddenIntent","mWidgetId : "+mWidgetId);
		
		
		Intent intent = new Intent(String.format(INTENT_HIDDEN_FORMAT, mWidgetId));
		
		
		intent.putExtra("widgetId", mWidgetId);
		context.sendBroadcast(intent);


		
		
	}
	
	
	
	private static void updateOftenIntent(Context context) {
		// TODO Auto-generated method stub
		int mWidgetId = PatpatView.mWidgetId;
		
		Log.d("keep_oftenintent","mWidgetId : "+mWidgetId);
		
		
		Intent intent = new Intent(String.format(INTENT_OFTEN_FORMAT, mWidgetId));
		
		
		intent.putExtra("widgetId2", mWidgetId);
		context.sendBroadcast(intent);


		
		
	}
	
	
	
	private  void updateEvolveIntent(RemoteViews rviews, Context context) {
		// TODO Auto-generated method stub				
		 
		
//		Log.d("CoinBlockView","state " + init+" "+lv0_1+" "+lv0_2+" "+lv1+" "+lv2 );
		
		int mWidgetId = PatpatView.mWidgetId;
//		
		Intent intent = new Intent(String.format(INTENT_INIT_FORMAT, mWidgetId));
		intent.putExtra("widgetId11", mWidgetId);		

		context.sendBroadcast(intent);

		Intent intent2 = new Intent(String.format(INTENT_EVOLVE_FORMAT, mWidgetId));
		intent2.putExtra("widgetId10", mWidgetId);				

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