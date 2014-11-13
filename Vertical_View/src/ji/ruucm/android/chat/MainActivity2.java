package ji.ruucm.android.chat;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;

public class MainActivity2 extends Activity {
	// label to display gcm messages
	TextView lblMessage;
	Controller aController;
	
	// Asyntask
	AsyncTask<Void, Void, Void> mRegisterTask;
	
	public static String name;
	public static String email;
	public static String imei;

	@Override
	public void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
		
		Log.d("MainActivity2","onCreate");
		
		setContentView(R.layout.activity_main2);
		
		// Get Global Controller Class object 
		// (see application tag in AndroidManifest.xml)
		aController = (Controller) getApplicationContext();
		
		Log.d("MainActivity2","aController");
		// Check if Internet present
		if (!aController.isConnectingToInternet()) {
			
			// Internet Connection is not present
			aController.showAlertDialog(MainActivity2.this,
					"Internet Connection Error",
					"Please connect to Internet connection", false);
			// stop executing code by return
			
			
			
			
			return;
		}
		
		
		
		
		Log.d("MainActivity2","return");
		String deviceIMEI = "";
		if(Config.SECOND_SIMULATOR){
			
			//Make it true in CONFIG if you want to open second simutor
			// for testing actually we are using IMEI number to save a unique device
			
			deviceIMEI = "000000000000001";
		}	
		else
		{
		  // GET IMEI NUMBER      
		 TelephonyManager tManager = (TelephonyManager) getBaseContext()
		    .getSystemService(Context.TELEPHONY_SERVICE);
		  deviceIMEI = tManager.getDeviceId(); 
		}
		 
		 // Getting name, email from intent
		Intent i = getIntent();
		
		Log.d("MainActivity2","getIntent");
		
		name = i.getStringExtra("name");
		email = i.getStringExtra("email");		
		imei  = deviceIMEI;
		// Make sure the device has the proper dependencies.
		GCMRegistrar.checkDevice(this);

		// Make sure the manifest permissions was properly set 
		GCMRegistrar.checkManifest(this);

		Log.d("MainActivity2","checkManifest");
		
		
		lblMessage = (TextView) findViewById(R.id.lblMessage); 
		
		
		Log.d("MainActivity2","lblMessage");
		
		// Register custom Broadcast receiver to show messages on activity
		registerReceiver(mHandleMessageReceiver, new IntentFilter(
				Config.DISPLAY_REGISTRATION_MESSAGE_ACTION));
		
		
		Log.d("MainActivity2","registerReceiver");

		
		// Get GCM registration id
		final String regId = GCMRegistrar.getRegistrationId(this);

		
		
		Log.d("MainActivity2","regId : "+regId);
		
		
		// Check if regid already presents
		if (regId.equals("")) {
			
			 Log.i("GCM K", "--- Regid = ''"+regId);
			// Register with GCM			
			 
			 
			 Log.d("MainActivity2","Register with GCM : "+regId);
			 
			GCMRegistrar.register(this, Config.GOOGLE_SENDER_ID);
			
			Log.d("MainActivity2","regregistered : "+regId);
			
			
			
		} else {
			
			// Device is already registered on GCM Server
			if (GCMRegistrar.isRegisteredOnServer(this)) {
				
				final Context context = this;
				// Skips registration.				
				Toast.makeText(getApplicationContext(), 
						"Already registered with GCM Server", 
						Toast.LENGTH_LONG).show();
				Log.i("GCM K", "Already registered with GCM Server");
				
				//GCMRegistrar.unregister(context);
				
			} else {
				
				Log.i("GCM K", "-- gO for registration--");
				
				// Try to register again, but not in the UI thread.
				// It's also necessary to cancel the thread onDestroy(),
				// hence the use of AsyncTask instead of a raw thread.
				final Context context = this; 
				
				mRegisterTask = new AsyncTask<Void, Void, Void>() {

					@Override
					protected Void doInBackground(Void... params) {
						
						// Register on our server
						// On server creates a new user
						aController.register(context, name, email, regId,imei); 
						
						return null;
					}

					@Override
					protected void onPostExecute(Void result) {
						mRegisterTask = null;
						
						finish();
					}

				};
				
				// execute AsyncTask
				mRegisterTask.execute(null, null, null);
			}
		}
	}		

	// Create a broadcast receiver to get message and show on screen 
	private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			
			Log.d("MainActivity2","onReceive : "+intent);
			
			String newMessage = intent.getExtras().getString(Config.EXTRA_MESSAGE);
			
			// Waking up mobile if it is sleeping
			aController.acquireWakeLock(getApplicationContext());
			
			// Display message on the screen
			lblMessage.append(newMessage + "\n");			
			
			Toast.makeText(getApplicationContext(), 
					"Got Message: " + newMessage, 
					Toast.LENGTH_LONG).show();
			
			// Releasing wake lock
			aController.releaseWakeLock();
		}
	};
	
	@Override
	protected void onDestroy() {
		// Cancel AsyncTask
		if (mRegisterTask != null) {
			mRegisterTask.cancel(true);
		}
		try {
			// Unregister Broadcast Receiver
			unregisterReceiver(mHandleMessageReceiver);
			
			//Clear internal resources.
			GCMRegistrar.onDestroy(this);
			
		} catch (Exception e) {
			Log.e("UnRegister Receiver Error", "> " + e.getMessage());
		}
		super.onDestroy();
	}

}
