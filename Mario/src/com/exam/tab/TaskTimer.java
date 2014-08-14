package com.exam.tab;

import com.exam.CoinBlockWidgetApp;
import com.exam.R;
import com.exam.TextPref;
import com.exam.coinBlockWidgetProvider;
import com.exam.view.*;

import android.content.Context;
import android.content.Intent;
import android.os.*;
import android.util.*;
import android.widget.*;

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

	TextView timer = null;
	private static long time = 0;
	
	
	boolean init = false;
	boolean lv0_1;
	boolean lv0_2;
	boolean lv1;
	boolean lv2;
	boolean lv3_1;
	
	public String INTENT_EVOLVE_FORMAT = "com.exam.view.INTENT_EVOLVE_FORMAT";
	public String INTENT_INIT_FORMAT = "com.exam.view.INTENT_INIT_FORMAT";
	
	
	
	int CliCountInit ;
	int CliCount0_1 ;
	int CliCount0_2 ;
	int CliCount1 ;
	int CliCount2 ;
	

	public void setTextView1(int textViewId) {
		timer = (TextView)coinBlockIntroActivity.getInstance()
				.findViewById(textViewId);
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

	public void StartSetting()
	{
		
		mPref.Ready();
		startTime = mPref.ReadLong("startTime", 0);	

		// It should work only one time
		if(startTime == 0){
			startTime = System.currentTimeMillis();		
			mPref.WriteLong("startTime", startTime);	
			mPref.CommitWrite();
		}
		
		Log.d("TaskTimer","startTime1 "+ Long.toString(startTime));
		
		
		Log.d("TaskTimer","CommitWrite ");

	}

	// Preference
	public static TextPref mPref;	

	/** this method is executed right BEFORE doInBackground()
	 *  on the main thread (UI thread) */
	@Override
	protected void onPreExecute() { 
//		Log.d("TaskTimer", "onPreExecute");
	
		

		try {
			mPref = new TextPref("mnt/sdcard/SsdamSsdam/textpref.pref");
			//fbPref = new TextPref("mnt/sdcard/SsdamSsdam/facebookprofile.txt");
		} catch (Exception e) { 
			e.printStackTrace();
		}      

		
		StartSetting();
//		mPref.Ready();
//		startTime = mPref.ReadInt("startTime", 0);	
//		mPref.EndReady();

		
		timer.setTextColor(TEXT_COLOR_NORMAL);
	

		

	}

	/** this method is executed BETWEEN onPreExecute() and onPostExecute()
	 *  on another thread (that's why it's called asynchronous) */
	// you should do network tasks here, not in main thread (abandoned)
	// you're not allowed to modify UI
	@Override
	protected String doInBackground(String... params) {
 
		Log.d("TaskTimer", "doInBackground");

		while(time >= 0 && !isCanceled) { 
			try {
				Thread.sleep(1000);		// one second sleep
				// time++;              // decrement time
				


				mPref.Ready(); 
				

//				Log.d("TaskTimer", "CommitWrite"+Long.toString(time));


				
				
				
				//State Variable
				init = mPref.ReadBoolean("initstate", false);	
				lv0_1 = mPref.ReadBoolean("lv0_1state", false);
				lv0_2 = mPref.ReadBoolean("lv0_2state", false);
				lv1 = mPref.ReadBoolean("lv1state", false);
				lv2 = mPref.ReadBoolean("lv2state", false);
				lv3_1 = mPref.ReadBoolean("lv3_1state", false);
				
				
				//ClickCount Variable
				CliCount0_1 = mPref.ReadInt("clicount0_1", 0);
				CliCount0_2 = mPref.ReadInt("clicount0_2", 0);
				CliCount1 = mPref.ReadInt("clicount1", 0);
				CliCount2 = mPref.ReadInt("clicount2", 0);
//				CliCount3 = mPref.ReadInt("clicount2", 0);

				
				

				
				time = (System.currentTimeMillis() - startTime) / 1000;

				Log.d("TaskTimer","time "+ Long.toString(time));
				Log.d("TaskTimer","startTime "+ Long.toString(startTime));
				
				
//				timer.setText("" + time);
//				timer.setTextColor(TEXT_COLOR_NORMAL);
				
					
				if (time >= 10 && time <= 12 && CliCount0_1 >= 3 && lv0_1){
					lv0_1 = false;
					lv0_2 = true;
					mPref.WriteBoolean("lv0_1state", lv0_1);	
					mPref.WriteBoolean("lv0_2state", lv0_2);
					mPref.CommitWrite();
					
					RemoteViews rviews = new RemoteViews(CoinBlockWidgetApp.getApplication().getPackageName(), R.layout.coin_block_widget);
					updateEvolveIntent(rviews, CoinBlockWidgetApp.getApplication());

					
				}
//				else if( CliCount0_2 >= 3  && lv0_2){
//					lv0_2 = false;
//					lv1 = true;
//					mPref.WriteBoolean("lv0_2state", lv0_2);	
//					mPref.WriteBoolean("lv1state", lv1);
//					mPref.CommitWrite();
//					
//					RemoteViews rviews = new RemoteViews(CoinBlockWidgetApp.getApplication().getPackageName(), R.layout.coin_block_widget);
//					updateEvolveIntent(rviews, CoinBlockWidgetApp.getApplication());
//				}
				else if (time >= 20 && time <= 22 && CliCount1 >= 3 && lv1){
					lv1 = false;
					lv2 = true;
					mPref.WriteBoolean("lv1state", lv1);	
					mPref.WriteBoolean("lv2state", lv2);
					mPref.CommitWrite();
					
					RemoteViews rviews = new RemoteViews(CoinBlockWidgetApp.getApplication().getPackageName(), R.layout.coin_block_widget);
					updateEvolveIntent(rviews, CoinBlockWidgetApp.getApplication());
					
				}
				else if (time >= 30 && time <= 32 && CliCount2 >=3 && lv2){
					lv2 = false;
					lv3_1 = true;
					mPref.WriteBoolean("lv2state", lv2);	
					mPref.WriteBoolean("lv3_1state", lv3_1);
					mPref.CommitWrite();
					
					RemoteViews rviews = new RemoteViews(CoinBlockWidgetApp.getApplication().getPackageName(), R.layout.coin_block_widget);
					updateEvolveIntent(rviews, CoinBlockWidgetApp.getApplication());
					
				}
				
				else{
				
				mPref.WriteLong("time", time);			
				mPref.CommitWrite();
				
				}
				
				
				publishProgress();          // trigger onProgressUpdate()


				/*
                //for CoinBlockView updateEvolveIntent                
                if(((CoinBlockWidgetApp) context.getApplicationContext()) != null) {    
                	Log.v("tag8","for CoinBlock"+time);
                	CoinBlockView.second = (long)time;                	
                }

				 */
				
				
				
				

			} catch(InterruptedException e) {
				Log.i("GUN", Log.getStackTraceString(e));
				return RESULT_FAIL;
			}
		}
		return RESULT_SUCCESS;
	}
	
	private  void updateEvolveIntent(RemoteViews rviews, Context context) {
		// TODO Auto-generated method stub				
		 
		
//		Log.d("CoinBlockView","state " + init+" "+lv0_1+" "+lv0_2+" "+lv1+" "+lv2 );
		
		int mWidgetId = CoinBlockView.mWidgetId;
//		
		Intent intent = new Intent(String.format(INTENT_INIT_FORMAT, mWidgetId));
		intent.putExtra("widgetId11", mWidgetId);		

		context.sendBroadcast(intent);

		Intent intent2 = new Intent(String.format(INTENT_EVOLVE_FORMAT, mWidgetId));
		intent2.putExtra("widgetId10", mWidgetId);				

		context.sendBroadcast(intent2);

		Log.d("TaskTimer","updateEvolveIntent");
	}

	/** this method is used by doInBackground
	 *  because it's called on the main thread (UI thread),
	 *  you can directly modify UI */
	@Override
	protected void onProgressUpdate(String... value) {
		// modify timer's text (remained time)
		timer.setText("" + time);

//		Log.d("TaskTimer", "onProgressUpdate"+Long.toString(time));

		// play beep sound
		//MediaPlayer.create(coinBlockIntroActivity.getInstance(), R.raw.beep).start();
	}

	/** this method is executed right AFTER doInBackground()
	 *  on the main thread (UI thread) */
	@Override
	protected void onPostExecute(String result) {
		if(RESULT_SUCCESS.equals(result))
			timer.setTextColor(TEXT_COLOR_FINISHED);

		Log.d("TaskTimer", "onPostExecute"+Long.toString(time));
	}
}