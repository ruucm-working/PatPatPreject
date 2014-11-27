package com.jym.patpat.Activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.jym.patpat.R;

public class Setting_dialog extends Activity { 
	
	@Override
	public void finish() {
		super.finish();
		this.overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
	}


	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);     
		
		
		
		this.overridePendingTransition(R.anim.start_enter, R.anim.start_exit);
		
		setContentView(R.layout.setting_dialogpage);             
	


	} 


	public void mOnClick(View v){

		switch(v.getId()){    	

		case R.id.ok02:    
			
			Intent intent = new Intent(this, Activity_Intro.class);
			startActivity(intent); 

		

			//finish();

			break;

		case R.id.cancled02:
			/*
			setResult(RESULT_CANCELED);
			
			
			
			finish();*/
			
			DialogSimple();
			
			break;

		}
	}




	private void DialogSimple(){
		AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
		alt_bld.setMessage("걍해..").setCancelable(
				false).setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// Action for 'Yes' Button
						
						Intent intent = new Intent(Setting_dialog.this, Activity_Intro.class);        	
						startActivity(intent);
						
						finish();
					}
					
				
					
				}).setNegativeButton("No",
						new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// Action for 'NO' Button


						dialog.cancel();
					}
				});
		AlertDialog alert = alt_bld.create();
		// Title for AlertDialog
		alert.setTitle("반갑습니다");
		// Icon for AlertDialog
		alert.setIcon(R.drawable.icon);
		alert.show();
	}
	
	

	
	
}