package ji.ruucm.android.chat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import ji.ruucm.android.vertical_view.fragment.Frag01;
import ji.ruucm.android.vertical_view.fragment.Frag02;
import ji.ruucm.android.vertical_view.fragment.Frag03;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.viewpagerindicator.IconPagerAdapter;
import com.viewpagerindicator.TabPageIndicator;

/**
 * Demonstrates combining a TabHost with a ViewPager to implement a tab UI that
 * switches between tabs and also allows the user to perform horizontal flicks
 * to move between the tabs.
 */
public class MainActivity extends FragmentActivity {
	
	
	
	//두번눌러 종료 구현
	private Handler mHandler;
	private boolean mFlag = false;
	
	

	//get register inform
	Controller aController = null;
	public static String uIMEI;
	public static String uName;
	public static String deviceIMEI = "";
	
	
	
	
	
	public static Intent i;
	
	
	
	
	ViewPager mViewPager;

	private static MainActivity instance;

	private static final int[] ICONS = new int[] {
			R.drawable.perm_group_hometab, R.drawable.perm_group_scheduletab,
			R.drawable.perm_group_talktab

	};

	public static Context getInstance() {
		// TODO Auto-generated method stub

		return instance;
	}
	
	
	
	
	
	
	
//	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void StartAsyncTaskInParallel(LongOperation task, String serverURL,String r,String a,String b) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
			task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,serverURL,"rrr","","");
		else
			task.execute(serverURL,"rrr","","");
	}
	
	

	// Create a broadcast receiver to get message and show on screen 
	private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			
			String newMessage = intent.getExtras().getString(Config.EXTRA_MESSAGE);
			
			// Waking up mobile if it is sleeping
			aController.acquireWakeLock(getApplicationContext());
			
			// Display message on the screen
//			lblMessage.append(newMessage + "\n");			
			
			Toast.makeText(getApplicationContext(), 
					"Got Message: " + newMessage, 
					Toast.LENGTH_LONG).show();
			
			// Releasing wake lock
			aController.releaseWakeLock();
		}
	};
	
	
/*	@Override
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
*/
	
	
	
	
	// Back Key Event  

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub

	/*	switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			
			
			if (Frag03.a.isVisible()){
				Frag03.a.hide(false);
				Frag03.plusbutton.setImageResource(R.drawable.ic_emoticon);
			}
			
			
			
			break;
		}*/
		
		
		
		 if (keyCode == KeyEvent.KEYCODE_BACK) {
		        if(!mFlag) {
		        	if (Frag03.a.isVisible()){
						Frag03.a.hide(false);
						Frag03.plusbutton.setImageResource(R.drawable.ic_emoticon);
					}
		            Toast.makeText(MainActivity.this, "'뒤로'버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
		            mFlag = true;
		            mHandler.sendEmptyMessageDelayed(0, 2000);
		            return false;
		        } else {
		            finish();
		        }
		    }
		    return super.onKeyDown(keyCode, event);
		
		
	}

	// for Activity anim
	@Override
	public void finish() {
		super.finish();
		this.overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// for getInstance method
		instance = this;
		
		
		/*
		//Receiving Broadcast
		registerReceiver(mHandleMessageReceiver, new IntentFilter(
				Config.DISPLAY_REGISTRATION_MESSAGE_ACTION));
*/
		// for Activity anim
		this.overridePendingTransition(R.anim.start_enter, R.anim.start_exit);

		final boolean customTitleSupported = requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);

		setContentView(R.layout.main);

		
		

		
		if (customTitleSupported) {
			getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
					R.layout.mytitle);
		}
		
		
		
		//두번눌러 종료
		
		// BACK Handler -> onCreate 안에 넣어주세요!
		mHandler = new Handler() {
		    @Override
		    public void handleMessage(Message msg) {
		        if(msg.what == 0) {
		            mFlag = false;
		        }
		    }
		};
		
		
		
		
		Log.d("MainActivity","aController : "+i);
	
		
		//Get Global Controller Class object (see application tag in AndroidManifest.xml)
				aController = (Controller) getApplicationContext();
				
				/*// Check if Internet Connection present
				if (!aController.isConnectingToInternet()) {
					
					// Internet Connection is not present
					aController.showAlertDialog(MainActivity.this,
							"Internet Connection Error",
							"Please connect to working Internet connection", false);
					
					// stop executing code by return
					return;
				}*/

				
				Log.d("MainActivity","aController : "+aController);
				
				// WebServer Request URL to get All registered devices
		        String serverURL = Config.YOUR_SERVER_URL+"userdata.php";
		        
		        // Use AsyncTask execute Method To Prevent ANR Problem
		        LongOperation serverRequest = new LongOperation(); 
		        
		        Log.d("MainActivity","serverRequest : ");
		        
//		        StartAsyncTaskInParallel(serverRequest, serverURL,"rrr","","");
//		        serverRequest.execute(serverURL,"rrr","","");
				
				
				Log.d("MainActivity","StartAsyncTaskInParallel : "+i);
				
				if(Config.SECOND_SIMULATOR){
					
					//Make it true in CONFIG if you want to open second simutor
					// for testing actually we are using IMEI number to save a unique device
					
					deviceIMEI = "000000000000001";
				}	
				else
				{
				  // GET IMEI NUMBER      
					
					Log.d("MainActivity","deviceIMEI : "+i);
				 TelephonyManager tManager = (TelephonyManager) getBaseContext()
				    .getSystemService(Context.TELEPHONY_SERVICE);
				  deviceIMEI = tManager.getDeviceId(); 
				  
				  
				  Log.d("MainActivity","deviceIMEI : "+deviceIMEI);
				}
				
				
				Log.d("MainActivity","aController : "+aController);
				
				
				/*uIMEI = aController.getUserData(0).getIMEI();"359618041295763";
				uName = aController.getUserData(0).getName();"nexus";
				
				*/
				
				Log.d("MainActivity","uName : "+i);
		
		
		
		
		
		

		/*
		 * final TextView myTitleText = (TextView) findViewById(R.id.myTitle);
		 * if (myTitleText != null) { //
		 * myTitleText.setText("========= NEW TITLE =========="); //
		 * myTitleText.setBackgroundColor(Color.GREEN); }
		 */

		ViewPager pager = (ViewPager) findViewById(R.id.pager);
 
		FragmentPagerAdapter adapter = new GoogleMusicAdapter(
				getSupportFragmentManager(), pager);

		// pager.setPageMargin( -30);

		// for tabindicator
		TabPageIndicator indicator = (TabPageIndicator) findViewById(R.id.indicator);
		indicator.setViewPager(pager);

		// viewpager가 아닌 indicator에 리스너를 등록해야함 
		indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageScrollStateChanged(int position) {
				// TODO Auto-generated method stub
				Log.d("CopyOfMainActivity",
						"onPageScrollStateChanged position : " + position);

			}

			@Override
			public void onPageScrolled(int position, float arg1, int arg2) {
				// TODO Auto-generated method stub
				Log.d("CopyOfMainActivity", "onPageScrolled position : "
						+ position); 

			}

			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
				Log.d("CopyOfMainActivity", "onPageSelected position : "
						+ position);
				final TextView myTitleText = (TextView) findViewById(R.id.myTitle);
				/*
				 * if (myTitleText != null) { myTitleText.setText(""); //
				 * myTitleText.setBackgroundColor(Color.GREEN); }
				 */

				switch (position) {
				case 0:
					myTitleText.setText("패밀리 위치 알림");
					break;
				case 1:
					myTitleText.setText("패밀리 일정");
					break;
				case 2:
					myTitleText.setText("패밀리 채팅");
					break;

				}

			}
		});

	}

	class GoogleMusicAdapter extends FragmentPagerAdapter implements
			IconPagerAdapter, ViewPager.OnPageChangeListener {
		public GoogleMusicAdapter(FragmentManager fm, ViewPager pager) {
			super(fm);
			mViewPager = pager;
			mViewPager.setAdapter(this);
		}

		@Override
		public Fragment getItem(int position) {
			// return TestFragment.newInstance(CONTENT[position %
			// CONTENT.length]);

			Fragment a = null;

			switch (position) {
			case 0:
				a = new Frag01();
				break;
			case 1:
				a = new Frag02();
				break;
			case 2:
				a = new Frag03();
				break;

			}

			return a;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			// return CONTENT[position % CONTENT.length].toUpperCase();
			return null;
		}

		@Override
		public int getIconResId(int index) {
			return ICONS[index];
		}

		@Override
		public int getCount() {
			return ICONS.length;
		}

		@Override
		public void onPageScrollStateChanged(int position) {
			// TODO Auto-generated method stub
			Log.d("CopyOfMainActivity", "onPageScrollStateChanged position : "
					+ position);
		}

		@Override
		public void onPageScrolled(int position, float arg1, int arg2) {
			// TODO Auto-generated method stub
			Log.d("CopyOfMainActivity", "onPageScrolled position : " + position);

		}

		@Override
		public void onPageSelected(int position) {
			// TODO Auto-generated method stub
			Log.d("CopyOfMainActivity", "onPageSelected position : " + position);

		}
	}
	
	
	
	// Class with extends AsyncTask class
			public class LongOperation  extends AsyncTask<String, Void, String> {
			         
			    	// Required initialization
			    	
			        //private final HttpClient Client = new DefaultHttpClient();
			       // private Controller aController = null;
			        private String Error = null;
			        private ProgressDialog Dialog = new ProgressDialog(MainActivity.this); 
			        String data =""; 
			        int sizeData = 0;  
			        
			        
			        protected void onPreExecute() {
			            // NOTE: You can call UI Element here.
			             
			            //Start Progress Dialog (Message)
			           
			            Dialog.setMessage("Getting registered users ..");
			            Dialog.show();
			            
			        }
			 
			        // Call after onPreExecute method
			        protected String doInBackground(String... params) {
			        	
			        	/************ Make Post Call To Web Server ***********/
			        	BufferedReader reader=null;
			        	String Content = "";
				             // Send data 
				            try{
				            	
				            	// Defined URL  where to send data
					               URL url = new URL(params[0]);
				            	
					            // Set Request parameter
					            if(!params[1].equals(""))
				               	   data +="&" + URLEncoder.encode("data", "UTF-8") + "="+params[1].toString();
					            if(!params[2].equals(""))
					               	   data +="&" + URLEncoder.encode("data2", "UTF-8") + "="+params[2].toString();	
					            if(!params[3].equals(""))
					               	   data +="&" + URLEncoder.encode("data3", "UTF-8") + "="+params[3].toString();
				              Log.i("GCM",data);
					            
					          // Send POST data request
				   
				              URLConnection conn = url.openConnection(); 
				              conn.setDoOutput(true); 
				              OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream()); 
				              wr.write( data ); 
				              wr.flush(); 
				          
				              // Get the server response 
				               
				              reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				              StringBuilder sb = new StringBuilder();
				              String line = null;
				            
					            // Read Server Response
					            while((line = reader.readLine()) != null)
					                {
					                       // Append server response in string
					                       sb.append(line + "\n");
					                }
				                
				                // Append Server Response To Content String 
				               Content = sb.toString();
				            }
				            catch(Exception ex)
				            {
				            	Error = ex.getMessage();
				            }
				            finally
				            {
				                try
				                {
				     
				                    reader.close();
				                }
				   
				                catch(Exception ex) {}
				            }
			        	
			            /*****************************************************/
			            return Content;
			        }
			         
			        protected void onPostExecute(String Content) {
			            // NOTE: You can call UI Element here.
			             
			            // Close progress dialog
			            Dialog.dismiss();
			            
			            if (Error != null) {
			                 
			                 
			            } else {
			              
			            	// Show Response Json On Screen (activity)
			            	
			             /****************** Start Parse Response JSON Data *************/
			            	aController.clearUserData();
			            	
			            	JSONObject jsonResponse;
			                      
			                try {
			                      
			                     /****** Creates a new JSONObject with name/value mappings from the JSON string. ********/
			                     jsonResponse = new JSONObject(Content);
			                      
			                     /***** Returns the value mapped by name if it exists and is a JSONArray. ***/
			                     /*******  Returns null otherwise.  *******/
			                     JSONArray jsonMainNode = jsonResponse.optJSONArray("Android");
			                      
			                     /*********** Process each JSON Node ************/
			  
			                     int lengthJsonArr = jsonMainNode.length();  
			  
			                     for(int i=0; i < lengthJsonArr; i++) 
			                     {
			                         /****** Get Object for each JSON node.***********/
			                         JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
			                          
			                         /******* Fetch node values **********/
			                         String IMEI       = jsonChildNode.optString("imei").toString();
			                         String Name       = jsonChildNode.optString("name").toString();
			                          
			                         Log.i("GCM","---"+Name);
			                         
			                         UserData userdata = new UserData();
			                         userdata.setIMEI(IMEI);
			                         userdata.setName(Name);
			                        
			                       //Add user data to controller class UserDataArr arraylist
			                         aController.setUserData(userdata);
			                         
			                    }
			                     
			                 /****************** End Parse Response JSON Data *************/
			                     
			                  //Add user data to controller class UserDataArr arraylist
//			                  gridView.setAdapter(new CustomGridAdapter(getBaseContext(), aController));
			                    
			                      
			                 } catch (JSONException e) {
			          
			                     e.printStackTrace();
			                 }
			  
			                 
			             }
			        }
			         
			    }

}
