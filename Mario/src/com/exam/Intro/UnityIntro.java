package com.exam.Intro;

import com.PatPat.Intro.UnityPlayerNativeActivity;
import com.exam.R;
import com.exam.helper.TextPref;
import com.exam.tab.coinBlockLoginActivity;
import com.unity3d.player.r;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

public class UnityIntro extends Activity {
	private static int introResult = 0;
	private UnityPlayerNativeActivity upna;
	private UpdateCheckThread ucThread;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		try {
			TextPref mPref = new TextPref("mnt/sdcard/SsdamSsdam/textpref.pref");
			setContentView(R.layout.invisiblelayout);
			Intent intent = new Intent(this, coinBlockLoginActivity.class);
			startActivity(intent);
		}
		// if file not exists (It means user run the application first time)
		catch (Exception e) {
			// TODO Auto-generated catch block
			Intent intent = new Intent(this, UnityPlayerNativeActivity.class);
			ucThread = new UpdateCheckThread();
			
			startActivity(intent);
			ucThread.start();
		}
	}
	
	public static int GetIntroResult() {
		return introResult;
	}

	private void _CallLoginActivity() {
		upna.finish();
		introResult = 1;
		
		Intent intent = new Intent(this, coinBlockLoginActivity.class);
		startActivity(intent);
		
		setContentView(R.layout.invisiblelayout);
	}
	
	private void _FinishActivity() {
		upna.finish();
		introResult = -1;
		finish();
	}

	class UpdateCheckThread extends Thread {
		private boolean isPlay = true;

		@Override
		public void run() {
			while(isPlay) {
				try {
					Thread.sleep(1000);
					int r = UnityPlayerNativeActivity.GetbResult();
					upna = UnityPlayerNativeActivity.GetInstance();
					
					if(r > 0){
						Log.v("Unity", "GetResult: True");
						isPlay = false;
						_CallLoginActivity();
					}
					else if(r < 0){
						Log.v("Unity", "GetResult: False");
						isPlay = false;
						_FinishActivity();
					}
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}