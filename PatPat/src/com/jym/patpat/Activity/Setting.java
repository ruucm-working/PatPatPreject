package com.jym.patpat.Activity;

import java.io.File;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jym.helper.TextPref;
import com.jym.patpat.R;

public class Setting extends Activity { 
	private static final String TAG = "Setting_TAG";
	private static final boolean DEVELOPER_MODE = true;

	
	
	
	
	//facebook profile
	String userFirstName ;
	String userLastName ;





	//Mesuring Time
	static long count = 0;
	static TextView time;	
	//public static long second = 60;

	//프레퍼런스 값들
	public static TextPref ePref;		
	public static TextPref initPref;	
	String stNum1;
	String stNum2;

	int spTag1;
	int spTag2;
	int spTag3;
	Boolean checked[] = new Boolean[20];





	//스피너 변수들
	ArrayAdapter<CharSequence> adspin1;
	ArrayAdapter<CharSequence> adspin2;
	ArrayAdapter<CharSequence> adspin3;
	boolean mInitSpinner;


	//for dialog

	boolean preinit;

	//static variables	
	public static boolean init = false;
	public static boolean lv0_1 = false;
	public static boolean lv0_2 = false;
	public static boolean lv1 = false;
	public static boolean lv2 = false;



	float CliSp0;



	
	
	@Override
	public void finish() {
		super.finish();
		this.overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
	}


	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	public void onCreate(Bundle savedInstanceState) {

		if (DEVELOPER_MODE) {
			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
			.detectDiskReads()
			.detectDiskWrites()
			.detectNetwork()
			.penaltyLog()
			.build());
		}		
		super.onCreate(savedInstanceState);     
		
		
		
		this.overridePendingTransition(R.anim.start_enter, R.anim.start_exit);
		
		setContentView(R.layout.settingpage);             
		Log.d(TAG, "setting view"); 

		//time = (TextView)findViewById(R.id.time);




		Log.d(TAG, "time01");
		Log.d("tag3", "time01");

		//프레퍼런스 읽어오기   
		


		try {
			ePref = new TextPref("mnt/sdcard/SsdamSsdam/entitypref.pref");
			initPref = new TextPref("mnt/sdcard/SsdamSsdam/bprofile.txt");

			Log.d("Setting", "initPref");

		} catch (Exception e) { 
			e.printStackTrace();
		}       


		ePref.Ready();
		initPref.Ready();



		TextView Num1;
		TextView Num2;
		Num1 = (TextView)findViewById(R.id.input01);		
		stNum1 = initPref.ReadString("stNum1","0");
		Num1.setText(stNum1);

		spTag1 = initPref.ReadInt("Tag1", 0);
		spTag2 = initPref.ReadInt("Tag2", 0);
		spTag3 = initPref.ReadInt("Tag3", 0); 






		//state's preference
		preinit = ePref.ReadBoolean("preinitstate", true);
		init = ePref.ReadBoolean("initstate", false);
		lv0_1 = ePref.ReadBoolean("lv0_1state", false);
		lv0_2 = ePref.ReadBoolean("lv0_2state", false);
		lv1 = ePref.ReadBoolean("lv1state", false);
		lv2 = ePref.ReadBoolean("lv2state", false);
		Log.d("Setting","init2"+init);



		checked[0] = initPref.ReadBoolean("checked0", false);
		checked[1] = initPref.ReadBoolean("checked1", false);
		checked[2] = initPref.ReadBoolean("checked2", false);
		checked[3] = initPref.ReadBoolean("checked3", false);
		checked[4] = initPref.ReadBoolean("checked4", false);
		checked[5] = initPref.ReadBoolean("checked5", false);
		checked[6] = initPref.ReadBoolean("checked6", false);
		checked[7] = initPref.ReadBoolean("checked7", false);
		checked[8] = initPref.ReadBoolean("checked8", false);
		checked[9] = initPref.ReadBoolean("checked9", false);
		checked[10] = initPref.ReadBoolean("checked10", false);
		checked[11] = initPref.ReadBoolean("checked11", false);
		checked[12] = initPref.ReadBoolean("checked12", false);
		checked[13] = initPref.ReadBoolean("checked13", false);


		userFirstName = initPref.ReadString("userFirstName", "");
		userLastName = initPref.ReadString("userLastName", "");


		ePref.EndReady();
		initPref.EndReady();


		Log.d("Setting", "init"+init);

		//체크박스 값에 따라 체크해주기

		if(checked[0]){
			CheckBox bo = (CheckBox)findViewById(R.id.kind1);			
			bo.setChecked(true);
		}
		if(checked[1]){
			CheckBox bo = (CheckBox)findViewById(R.id.kind2);
			bo.setChecked(true);
		}
		if(checked[2]){
			CheckBox bo = (CheckBox)findViewById(R.id.kind3);
			bo.setChecked(true);
		}
		if(checked[3]){
			CheckBox bo = (CheckBox)findViewById(R.id.kind4);
			bo.setChecked(true);
		}
		if(checked[4]){
			CheckBox bo = (CheckBox)findViewById(R.id.kind5);
			bo.setChecked(true);
		}
		if(checked[5]){
			CheckBox bo = (CheckBox)findViewById(R.id.kind6);
			bo.setChecked(true);
		}
		if(checked[6]){
			CheckBox bo = (CheckBox)findViewById(R.id.for1);
			bo.setChecked(true);
		}
		if(checked[7]){
			CheckBox bo = (CheckBox)findViewById(R.id.for2);
			bo.setChecked(true);
		}
		if(checked[8]){
			CheckBox bo = (CheckBox)findViewById(R.id.for3);
			bo.setChecked(true);
		}
		if(checked[9]){
			CheckBox bo = (CheckBox)findViewById(R.id.dry1);
			bo.setChecked(true);
		}
		if(checked[10]){
			CheckBox bo = (CheckBox)findViewById(R.id.dry2);
			bo.setChecked(true);
		}
		if(checked[11]){
			CheckBox bo = (CheckBox)findViewById(R.id.dry3);
			bo.setChecked(true);
		}
		if(checked[12]){
			CheckBox bo = (CheckBox)findViewById(R.id.dry4);
			bo.setChecked(true);
		}
		if(checked[13]){
			CheckBox bo = (CheckBox)findViewById(R.id.dry5);
			bo.setChecked(true);
		}

		Spinner spin1 = (Spinner)findViewById(R.id.myspinner1);
		spin1.setPrompt("안녕스피너");

		adspin1 = ArrayAdapter.createFromResource(this, R.array.nation, 
				android.R.layout.simple_spinner_item);
		adspin1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spin1.setAdapter(adspin1);


		Log.d("Setting", "setAdapter.EndReady();");

		spin1.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, 
					int position, long id) {
				//�ʱ�ȭ���� ���� ���ܽ�
				if (mInitSpinner == false) {
					mInitSpinner = true;
					return;
				}

				Toast.makeText(Setting.this,adspin1.getItem(position) + "를 선택했지 난.",
						Toast.LENGTH_SHORT).show();
				//프레퍼런스에 기록하기
				spTag1 = position;
			}
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
		//스피너 초기값지정
		spin1.setSelection(spTag1);

		Spinner spin2 = (Spinner)findViewById(R.id.myspinner2);
		spin2.setPrompt("안녕스피너");

		adspin2 = ArrayAdapter.createFromResource(this, R.array.vintage, 
				android.R.layout.simple_spinner_item);
		adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spin2.setAdapter(adspin2);

		spin2.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, 
					int position, long id) {
				//�ʱ�ȭ���� ���� ���ܽ�
				if (mInitSpinner == false) {
					mInitSpinner = true;
					return;
				}
				//프레퍼런스에 기록하기
				spTag2 = position;
			}
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});

		Log.d("Setting", "onNothingSelected.EndReady();");

		//스피너 초기값지정
		spin2.setSelection(spTag2);

		Spinner spin3 = (Spinner)findViewById(R.id.myspinner3);
		spin3.setPrompt("안녕스피너");

		adspin3 = ArrayAdapter.createFromResource(this, R.array.vintage, 
				android.R.layout.simple_spinner_item);
		adspin3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spin3.setAdapter(adspin3);

		Log.d("Setting", "spin3.setAdapter.EndReady();");

		spin3.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, 
					int position, long id) {
				//�ʱ�ȭ���� ���� ���ܽ�
				if (mInitSpinner == false) {
					mInitSpinner = true;
					return; 
				}
				//프레퍼런스에 기록하기
				spTag3 = position;
			}
			public void onNothingSelected(AdapterView<?> parent) {
			}



		});

		Log.d("Setting", "setOnItemSelectedListener.setAdapter.EndReady();");
		//스피너 초기값지정
		spin3.setSelection(spTag3);
		Log.d("Setting", "spin3.setAdapter.EndReady();");


	} 

	

	
	

	public static long getSecond(long milli){
		long secondValue = 0;
		secondValue = milli / 10; 
		return secondValue;
	}

	public static long getMinute(long milli){
		long secondValue = 0;
		secondValue = milli / 600;
		return secondValue;
	}


	public static float getMinuteperCount(int clickcount){

		float clisp = 0 ;

		if (count > 10){
			clisp = clickcount/count*600;
		}		


		return clisp;
	}

	public static float getSecondperCount(int clickcount){

		float clisp = 0 ;

		if (count > 10){
			clisp = clickcount/count*10;
		}		


		return clisp;
	}


	public void onPause() {
		super.onPause();
		/* 
		ePref.BulkWriteReady(1000);
		ePref.BulkWrite("Name", Name);
		ePref.BulkWrite("StNum", Integer.toString(StNum));
		ePref.CommitWrite();
		//*/
	}

	public void mOnClick(View v){
		Log.d(TAG,"monclick");

		switch(v.getId()){    	

		case R.id.ok:    
			
			/*
			Intent intent = new Intent();    		
			setResult(RESULT_OK,intent)	;
			Log.d(TAG,"setOK");


*/
			
			ePref.Ready();
			initPref.Ready();

			TextView txtname1 = (TextView)findViewById(R.id.input01);
			stNum1 = txtname1.getText().toString();

			initPref.WriteString("stNum1", stNum1);
			initPref.WriteString("stNum2", stNum2);

			initPref.WriteInt("Tag1", spTag1);
			initPref.WriteInt("Tag2", spTag2);
			initPref.WriteInt("Tag3", spTag3);


			initPref.WriteBoolean("checked0", checked[0]);
			initPref.WriteBoolean("checked1", checked[1]);
			initPref.WriteBoolean("checked2", checked[2]);
			initPref.WriteBoolean("checked3", checked[3]);
			initPref.WriteBoolean("checked4", checked[4]);
			initPref.WriteBoolean("checked5", checked[5]);
			initPref.WriteBoolean("checked6", checked[6]);
			initPref.WriteBoolean("checked7", checked[7]);
			initPref.WriteBoolean("checked8", checked[8]);
			initPref.WriteBoolean("checked9", checked[9]);
			initPref.WriteBoolean("checked10", checked[10]);
			initPref.WriteBoolean("checked11", checked[11]);
			initPref.WriteBoolean("checked12", checked[12]);
			initPref.WriteBoolean("checked13", checked[13]);


			ePref.WriteBoolean("lv0_1state", lv0_1);
			ePref.WriteBoolean("lv0_2state", lv0_2);
			ePref.WriteBoolean("lv1state", lv1);
			ePref.WriteBoolean("lv2state", lv2);



			ePref.CommitWrite();
			initPref.CommitWrite();

			if (preinit){
				//DialogSimple();
				
				
				
				Intent intent = new Intent(this, Setting_dialog.class);
				startActivity(intent); 
				
				preinit = false ;
				init = true;
				ePref.Ready();
				ePref.WriteBoolean("preinitstate", preinit);
				ePref.WriteBoolean("initstate", init);
				ePref.CommitWrite();
				
				
				

				
			}
			else 
				finish();



			//finish();

			break;

		case R.id.cancled:
			/*
			setResult(RESULT_CANCELED);
			
			
			
			finish();*/
			
			Intent intent2 = new Intent(this, Activity_Intro.class);        	
			startActivity(intent2);
			break;

		case R.id.reset3:
			Spinner spin2 = (Spinner)findViewById(R.id.myspinner2);
			spin2.setSelection(0);
			Spinner spin3 = (Spinner)findViewById(R.id.myspinner3);
			spin3.setSelection(0);
			break;



		case R.id.reset4:



			break;

		case R.id.kind1:
			checked[0] = !checked[0];
			break;
		case R.id.kind2:
			checked[1] = !checked[1];
			break;
		case R.id.kind3:
			checked[2] = !checked[2];
			break;
		case R.id.kind4:
			checked[3] = !checked[3];
			break;
		case R.id.kind5:
			checked[4] = !checked[4];
			break;
		case R.id.kind6:
			checked[5] = !checked[5];
			break;
		case R.id.for1:
			checked[6] = !checked[6];
			break;
		case R.id.for2:
			checked[7] = !checked[7];
			break;
		case R.id.for3:
			checked[8] = !checked[8];
			break;
		case R.id.dry1:
			checked[9] = !checked[9];
			break;
		case R.id.dry2:
			checked[10] = !checked[10];
			Log.d(TAG,"checked[10]!");
			break;
		case R.id.dry3:
			checked[11] = !checked[11];
			break;
		case R.id.dry4:
			checked[12] = !checked[12];
			break;
		case R.id.dry5:
			checked[13] = !checked[13];			
			break;
		}
	}




	
	
	

	
	

	

	
	
}