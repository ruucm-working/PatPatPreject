package com.exam.tab;

import java.io.File;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.exam.R;
import com.exam.helper.TextPref;
import com.facebook.LoggingBehavior;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.Settings;
import com.facebook.model.GraphUser;

public class coinBlockLoginActivity extends Activity
{
	//facebook	
	private Session.StatusCallback statusCallback = new SessionStatusCallback();
	private Button buttonLoginLogout;

	//facebook login profile
	String userId ;
	String userFirstName ;
	String userLastName ;

	//프레퍼런스 
	public static TextPref mPref;	
	public static TextPref bPref;	

	boolean setDialogOn ;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		Log.d("coinBlockLoginActivity","onCreate.");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		init();
		dataInit();
		facebookInit(savedInstanceState);

		//프레퍼런스 읽어오기   
		File saveDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "SsdamSsdam"); // dir : 생성하고자 하는 경로
		if(!saveDir.exists())
		{
			// Make textpref
			saveDir.mkdirs();
		}

		try {
			mPref = new TextPref("mnt/sdcard/SsdamSsdam/textpref.pref");
			bPref = new TextPref("mnt/sdcard/SsdamSsdam/bprofile.txt");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		mPref.Ready();
		bPref.Ready();

		Log.d("coinBlockLoginActivity","bPref.Ready();.");

		setDialogOn = mPref.ReadBoolean("setdialogon", true);

		userId = bPref.ReadString("userId", "");
		userFirstName = bPref.ReadString("userFirstName", "");
		userLastName = bPref.ReadString("userLastName", "");

		//Write device Profile(bprofile)
		bPref.WriteString("MANUFACTURER", Build.MANUFACTURER);
		bPref.WriteString("MODEL", Build.MODEL);
		bPref.WriteString("PRODUCT", Build.PRODUCT);

		TelephonyManager telephony=(TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		bPref.WriteString("NetworkCountryIso", telephony.getNetworkCountryIso());
		bPref.WriteString("SimCountryIso", telephony.getSimCountryIso());

		String[] array = Build.FINGERPRINT.split("/");
		bPref.WriteString("DeviceVersion", array[2]);

		bPref.CommitWrite();
		mPref.EndReady();
		bPref.EndReady();
	} 

	//Facebook Login

	private void init() {
		buttonLoginLogout = (Button)findViewById(R.id.buttonLoginLogout1);
	}

	@SuppressLint("NewApi")
	private void dataInit() {
		//ActionBar Init
		//getActionBar().setDisplayShowHomeEnabled(false);
		//getActionBar().setTitle(R.string.board_detail_activity_title);
	}

	private void facebookInit(Bundle savedInstanceState) {
		Settings.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);

		Session session = Session.getActiveSession();
		if (session == null) {
			if (savedInstanceState != null) {
				session = Session.restoreSession(this, null, statusCallback, savedInstanceState);
			}
			if (session == null) {
				session = new Session(this);
			}
			Session.setActiveSession(session);
			if (session.getState().equals(SessionState.CREATED_TOKEN_LOADED)) {
				session.openForRead(new Session.OpenRequest(this).setCallback(statusCallback));
			}
		} 

		updateView();
	}

	@Override
	public void onStart() {
		super.onStart();
		Session.getActiveSession().addCallback(statusCallback);
	}

	@Override
	public void onStop() {
		super.onStop();
		Session.getActiveSession().removeCallback(statusCallback);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Session session = Session.getActiveSession();
		Session.saveSession(session, outState);
	}

	private void updateView() {
		Session session = Session.getActiveSession();
		if (session.isOpened()) {
			buttonLoginLogout.setText("로그아웃");
			buttonLoginLogout.setOnClickListener(new OnClickListener() {
				public void onClick(View view) { onClickLogout(); }
			});
			Log.d("tag02","userFirstName"+ userFirstName );
		} else {
			buttonLoginLogout.setText("로그인");
			buttonLoginLogout.setOnClickListener(new OnClickListener() {
				public void onClick(View view) { onClickLogin(); }
			});
		}
	}

	private void onClickLogin() {
		Session session = Session.getActiveSession();
		if (!session.isOpened() && !session.isClosed()) {
			session.openForRead(new Session.OpenRequest(this).setCallback(statusCallback));
		} else {
			Session.openActiveSession(this, true, statusCallback);
		}
	}

	private void onClickLogout() {
		Session session = Session.getActiveSession();
		if (!session.isClosed()) {
			session.closeAndClearTokenInformation();
		}
	}

	private void toIntro() {
		if(setDialogOn){					
			Intent intent = new Intent(this, Setting.class);	 
			startActivity(intent);

			//for one-time showing dialog										
			setDialogOn = false;		        	
			mPref.Ready();
			mPref.WriteBoolean("setdialogon", setDialogOn);
			mPref.CommitWrite();
		} else {
			Intent intent = new Intent(this, IntroActivity.class);
			startActivity(intent);		        	
		}
	}

	private class SessionStatusCallback implements Session.StatusCallback {
		@Override
		public void call(Session session, SessionState state, Exception exception) {
			updateView();    
			getFaceBookMe(session);
		}
	}

	private void getFaceBookMe(Session session){
		if(session.isOpened()){
			Request.newMeRequest(session, new Request.GraphUserCallback() {
				@Override
				public void onCompleted(GraphUser user, Response response) {
					response.getError();

					userId = user.getId() ;
					userFirstName = user.getFirstName() ;
					userLastName = user.getLastName() ;

					Log.d("coinBlockLoginActivity","userFirstName2"+userFirstName);

					bPref.Ready();

					bPref.WriteString("userId", userId);
					bPref.WriteString("userFirstName", userFirstName);
					bPref.WriteString("userLastName", userLastName);

					Log.d("coinBlockLoginActivity", "WriteString;");

					bPref.CommitWrite();

					toIntro();
					finish();
				}
<<<<<<< HEAD
			}).executeAsync();
		}
	}
=======
				
	        }
	        
	 
	        private class SessionStatusCallback implements Session.StatusCallback {
	            @Override
	            public void call(Session session, SessionState state, Exception exception) {
	                updateView();    
	                getFaceBookMe(session);
	            }
	        }
	        
	        
	        private void getFaceBookMe(Session session){
	        	 
	            if(session.isOpened()){
	                Request.newMeRequest(session, new Request.GraphUserCallback() {
	     
	                    @Override
	                    public void onCompleted(GraphUser user, Response response) {
	                        response.getError();
	                        
	                        /*
	                        System.err.println(" getId  :  " + user.getId());
	                        System.err.println(" getFirstName  :  " + user.getFirstName());
	                        System.err.println(" getLastName  :  " + user.getLastName());
	                        System.err.println(" getMiddleName  :  " + user.getMiddleName());
	                        System.err.println(" getBirthday  :  " + user.getBirthday());
	                        //System.err.println(" getLink  :  " + user.getLink());
	                        //System.err.println(" getName  :  " + user.getName());
	                        //System.err.println(" getUsername :  " + user.getUsername());
	                        //System.err.println(" getLocation :  " + user.getLocation());
	                        //System.err.println("getRawResponse  :  " + response.getRawResponse());
	                        */
	                         
	                        /* 
	                        Log.d("tag01"," getId  :  " + user.getId() );
	                        Log.d("tag01"," getFirstName  :  " + user.getFirstName() );
	                        Log.d("tag01"," getLastName  :  " + user.getLastName() );
	                        Log.d("tag01"," getMiddleName  :  " + user.getMiddleName() );
	                        Log.d("tag01"," getBirthday  :  " + user.getBirthday() );
	                        */
	                         
	                        
	                        userId = user.getId() ;
	                        userFirstName = user.getFirstName() ;
	                        userLastName = user.getLastName() ;
	                        
	                        Log.d("coinBlockLoginActivity","userFirstName2"+userFirstName);
	                        
	                        
	                        bPref.Ready();
	                        
	                        bPref.WriteString("userId", userId);
	            			bPref.WriteString("userFirstName", userFirstName);
	            			bPref.WriteString("userLastName", userLastName);
	                  		
	                    
	            			Log.d("coinBlockLoginActivity", "WriteString;");
	                    
	            			
	            			bPref.CommitWrite();
	            			
	            			toIntro();
	            			finish();
	            			
	            			

	            			
	            			
	                        
	                        
	                    }
	                }).executeAsync();
	            }
	        }

			
		
		
	
	
	
	
>>>>>>> master
}