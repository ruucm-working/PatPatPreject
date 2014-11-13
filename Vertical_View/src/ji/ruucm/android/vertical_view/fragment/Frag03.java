package ji.ruucm.android.vertical_view.fragment;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import ji.ruucm.android.chat.Config;
import ji.ruucm.android.chat.GCMIntentService;
import ji.ruucm.android.chat.MainActivity;
import ji.ruucm.android.chat.R;
import ji.ruucm.android.margin_animation.AnimatingRelativeLayout;
import ji.ruucm.android.margin_animation.MediaAssets;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.viewpagerindicator.PageIndicator;

public class Frag03 extends Fragment /* implements OnClickListener */{

	
	
	// gcm UI elements
		String serverURL;
		String message;
		String imei = "";
		String sendfrom ="";
		String name ="";
		
		
		EditText txtMessage; 
		TextView sendTo;
		
		// Register button
		Button btnSend;
	    
//		Controller aController = null;
	
	
	// for activity anim
	public static AnimatingRelativeLayout a;
	public static ImageView plusbutton;

	AnimatingRelativeLayout a2;

	ImageView emoticon_preview;

	// for adding Emoticon function on chat
	TestFragmentAdapter mAdapter;
	ViewPager mPager;
	PageIndicator mIndicator;

	static Frag03 instance;

	static EditText msg;

	// chat variable
	// 리스트뷰를 구성하는 리스트뷰와 어댑터 변수
	public static ExamAdapter m_adapter = null;
	public static ListView m_list = null;

	// 시간및 날짜 출력시 사용할 포맷 객체 변수
	private SimpleDateFormat m_date_format = null;
	public static SimpleDateFormat m_time_format = null;

	// public static TextPref fbPref;

	static boolean init;
	public static boolean lv0_1;
	static boolean lv0_2;
	public static boolean lv1;
	public static boolean lv2;
	public static boolean lv3_1;

	static View v;

	public static Frag03 getInstance() {
		return instance;
	}
	
	
	
	
	public static void receiveMessage () {
		
		
		
		Log.d("Frag03","GCMIntentService.StringAll "+GCMIntentService.StringAll);
		
		
		
		if (m_adapter != null && GCMIntentService.StringAll!= null){
			
			Chat_ExamData data = null;
			
			String title   = GCMIntentService.StringAll[0];
			String imei    = GCMIntentService.StringAll[1];
			String message = GCMIntentService.StringAll[2];
			

			Log.d("Frag03","StringAll: "+message);
			
			Log.d("Frag03","m_adapter1: "+m_adapter);
			
			
			
		/*	if (message == "2130837549"){
				data = new Chat_ExamData((byte) 3,"imei : "+imei+ "\nmessage : "+
						message, m_time_format
								.format(new Date()), 0);
			}*/
			
			
//			if (data)
		
			
			data = new Chat_ExamData((byte) 0,/*"imei : "+imei+ "\nmessage : "+*/
					message, m_time_format
							.format(new Date()), 0);
			
			
			Log.d("Frag03","data3 : "+data.data3);
			Log.d("Frag03","m_adapter: "+m_adapter);
		// 어댑터에 데이터를 추가한다.
		m_adapter.add(data);
		
		// 추가된 영역으로 스크롤을 이동시킨다.
		m_list.smoothScrollToPosition(m_adapter.getCount() - 1);
		
		
		msg.setText("");
		
		}
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		instance = this;

		v = inflater.inflate(R.layout.frag03, container, false);


		// ExamData 객체를 관리하는 ArrayList 객체를 생성한다.
		ArrayList<Chat_ExamData> data_list = new ArrayList<Chat_ExamData>();
		// 사용자 정의 어댑터 객체를 생성한다.
		m_adapter = new ExamAdapter(data_list);

		// 리스트를 얻어서 어댑터를 설정한다.
		m_list = (ListView)v. findViewById(R.id.lvMessageList);
		m_list.setAdapter(m_adapter);

		// 한국 기준의 시간으로 날짜의 출력 형태를 정의하여 객체를 생성한다.
		m_date_format = new SimpleDateFormat("yyyy/MM/dd", Locale.KOREA);
		m_time_format = new SimpleDateFormat("HH:mm:ss", Locale.KOREA);

		/*AsyncTask02 sendAutomessage = new AsyncTask02();
		// // sendAutomessage.execute("");
		StartAsyncTaskInParallel(sendAutomessage);*/

		
		Log.d("Frag03","m_adapter1 : "+m_adapter.getClass());
		
//		receiveMessage();
	
		
		// 이모티콘 추가버튼에 리스너를 등록한다.
		ImageView btn1 = (ImageView) v.findViewById(R.id.btn1);
		a = (AnimatingRelativeLayout) v.findViewById(R.id.below_relative);
		a.hide();
		plusbutton = (ImageView) v.findViewById(R.id.btn1);

		btn1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// toggleMenu();

				if (!a.isVisible()) {

					a.show();
					plusbutton
							.setImageResource(R.drawable.ic_emoticon_selected);

				} else {
					a.hide(false);
					plusbutton.setImageResource(R.drawable.ic_emoticon);

				}

			}
		});

		// send버튼에 리스너를 등록한다.
		Button btn2 = (Button) v.findViewById(R.id.btn2);
		msg = (EditText) v.findViewById(R.id.message01);
		//send to nexus
//		Intent i = MainActivity.i;
		
		
	/*	name = "designer";
	 //gx
		imei = "353472060743915";
		
		*/
		
		
/*		name = "develop";
		//nexus
		imei = "359618041295763";
		*/
		
		
		
		name = "gagag";
		 //galaxy1
		imei = "352905051641094";
		
		
		
	/*	name = "gagag2";
		 //galaxy2
		imei = "352260053702746";
		
		*/
		
		sendfrom = /*i.getStringExtra("sendfrom");*/MainActivity.deviceIMEI;
		
		

		btn2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				
				
				
			/*
						
				// Get data from EditText 
				message = msg.getText().toString(); 
				 
 				// WebServer Request URL
		        serverURL = Config.YOUR_SERVER_URL+"sendpush.php";
		        
		        // Use AsyncTask execute Method To Prevent ANR Problem
//		        new LongOperation().execute(serverURL,imei,message,sendfrom); 
		        
		        LongOperation sendtomessage = new LongOperation();
				StartAsyncTaskInParallel2(sendtomessage);
		        
				
				*/
				
				
				
				
				
				Chat_ExamData data = null;
				
				

				if (a2.isVisible() && msg.getText().toString().equals("")) {
					
					
					sendtoEmoOtherDevice();
					
					
					
					int emo = (Integer) emoticon_preview.getTag();

					data = new Chat_ExamData((byte) 3,
							msg.getText().toString(), m_time_format
									.format(new Date()), emo);


					// send with sound (feat.park)

					if (emo == R.drawable.emo_dontlike) {

						final MediaPlayer snd = MediaAssets.getInstance()
								.getSoundPlayer(R.raw.dontlike);
						snd.seekTo(0);
						snd.setOnSeekCompleteListener(new OnSeekCompleteListener() {
							public void onSeekComplete(MediaPlayer mp) {
								snd.start();
							}
						});
					} else if (emo == R.drawable.emo_hate) {
						final MediaPlayer snd = MediaAssets.getInstance()
								.getSoundPlayer(R.raw.hate);
						snd.seekTo(0);
						snd.setOnSeekCompleteListener(new OnSeekCompleteListener() {
							public void onSeekComplete(MediaPlayer mp) {
								snd.start();
							}
						});

					} else if (emo == R.drawable.emo_please) {
						final MediaPlayer snd = MediaAssets.getInstance()
								.getSoundPlayer(R.raw.please);
						snd.seekTo(0);
						snd.setOnSeekCompleteListener(new OnSeekCompleteListener() {
							public void onSeekComplete(MediaPlayer mp) {
								snd.start();
							}
						});

					}

					// 어댑터에 데이터를 추가한다.
					m_adapter.add(data);
					// 추가된 영역으로 스크롤을 이동시킨다.
					m_list.smoothScrollToPosition(m_adapter.getCount() - 1);

					msg.setText("");

					a.hide(false);
					a2.hide(false);
					plusbutton.setImageResource(R.drawable.ic_emoticon);

				} else if (a2.isVisible() && msg.getText() != null) {
					
					
					
					sendtoOtherDevice();
					
					
					
					
					int emo = (Integer) emoticon_preview.getTag();
					
					
					// add data including emoticon
					data = new Chat_ExamData((byte) 2,
							msg.getText().toString(), m_time_format
									.format(new Date()),
									emo);

					if (emo == R.drawable.emo_dontlike) {

						final MediaPlayer snd = MediaAssets.getInstance()
								.getSoundPlayer(R.raw.dontlike);
						snd.seekTo(0);
						snd.setOnSeekCompleteListener(new OnSeekCompleteListener() {
							public void onSeekComplete(MediaPlayer mp) {
								snd.start();
							}
						});
					} else if (emo == R.drawable.emo_hate) {
						final MediaPlayer snd = MediaAssets.getInstance()
								.getSoundPlayer(R.raw.hate);
						snd.seekTo(0);
						snd.setOnSeekCompleteListener(new OnSeekCompleteListener() {
							public void onSeekComplete(MediaPlayer mp) {
								snd.start();
							}
						});

					} else if (emo == R.drawable.emo_please) {
						final MediaPlayer snd = MediaAssets.getInstance()
								.getSoundPlayer(R.raw.please);
						snd.seekTo(0);
						snd.setOnSeekCompleteListener(new OnSeekCompleteListener() {
							public void onSeekComplete(MediaPlayer mp) {
								snd.start();
							}
						});

					}

					// 어댑터에 데이터를 추가한다.
					m_adapter.add(data);
					// 추가된 영역으로 스크롤을 이동시킨다.
					m_list.smoothScrollToPosition(m_adapter.getCount() - 1);

					msg.setText("");

					a.hide(false);
					a2.hide(false);
					plusbutton.setImageResource(R.drawable.ic_emoticon);

				}

				else if (!msg.getText().toString().equals("")) {
					
					
					sendtoOtherDevice();
					
					
					// 첫번째 버튼이 눌리면 상대방이 이야기하는 듯한 데이터를 구성한다.
					data = new Chat_ExamData((byte) 1,
							msg.getText().toString(), m_time_format
									.format(new Date()), 0);

					// 어댑터에 데이터를 추가한다.
					m_adapter.add(data);
					// 추가된 영역으로 스크롤을 이동시킨다.
					m_list.smoothScrollToPosition(m_adapter.getCount() - 1);

					msg.setText("");

				}

			}
		});

		// add Listener to Emoticons

		emoticon_preview = (ImageView) v.findViewById(R.id.emoticon_preview);
		a2 = (AnimatingRelativeLayout) v.findViewById(R.id.below_relative02);
		a2.hide();

		ImageView emo_dontlike = (ImageView) v.findViewById(R.id.emo_dontlike);
		emo_dontlike.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// toggleMenu();

				
				emoticon_preview.setImageResource(R.drawable.emo_dontlike);
				emoticon_preview.setTag(R.drawable.emo_dontlike);

				a2.show(false);

			}
		});

		ImageView emo_hate = (ImageView) v.findViewById(R.id.emo_hate);
		emo_hate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// toggleMenu();

				emoticon_preview.setImageResource(R.drawable.emo_hate);
				emoticon_preview.setTag(R.drawable.emo_hate);
				a2.show(false);

			}
		});

		ImageView emo_please = (ImageView) v.findViewById(R.id.emo_please);
		emo_please.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// toggleMenu();

				emoticon_preview.setImageResource(R.drawable.emo_please);
				emoticon_preview.setTag(R.drawable.emo_please);
				a2.show(false);

			}
		});

		// add Listener blurred emoticon layout

		ImageView cancle = (ImageView) v.findViewById(R.id.emo_cancle);

		cancle.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// toggleMenu();

				a2.hide(false);

			}
		});


		
		
		
		/*	
			
			//Get Global Controller Class object (see application tag in AndroidManifest.xml)
					aController = (Controller) getActivity();
					
					// Check if Internet Connection present
					if (!aController.isConnectingToInternet()) {
						
						// Internet Connection is not present
						aController.showAlertDialog(getActivity(),
								"Internet Connection Error",
								"Please connect to working Internet connection", false);
						
						// stop executing code by return
						return;
					}
	*/
					// Getting name, email from intent
					/*Intent i = MainActivity.i;*/
					
					/*name = i.getStringExtra("name");
					imei = i.getStringExtra("imei");
					sendfrom = i.getStringExtra("sendfrom");*/
					
					
//					txtMessage = (EditText) v.findViewById(R.id.txtMessage);
//					sendTo     = (TextView) v.findViewById(R.id.sendTo);
//					btnSend    = (Button) v.findViewById(R.id.btnSend);
					
//					sendTo.setText("Send To : "+name);
					
					Toast.makeText(getActivity(), "Send To : "+name,
							Toast.LENGTH_LONG).show();
			/*		
					// Click event on Register button
					btn2.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View arg0) {  
							// Get data from EditText 
							message = msg.getText().toString(); 
							 
							// WebServer Request URL
					        serverURL = Config.YOUR_SERVER_URL+"sendpush.php";
					        
					        
					        // Use AsyncTask execute Method To Prevent ANR Problem
					        new LongOperation().execute(serverURL,imei,message,sendfrom); 
					        
					        txtMessage.setText("");
						}
					});
			*/
			
		
		
		
		
		
		
		
		
		
		
		return v;

	}
	
	void sendtoEmoOtherDevice(){
		
		message = Integer.toString( (Integer) emoticon_preview.getTag());
		
		
		
		
		// WebServer Request URL
        serverURL = Config.YOUR_SERVER_URL+"sendpush.php";
        
        // Use AsyncTask execute Method To Prevent ANR Problem
//        new LongOperation().execute(serverURL,imei,message,sendfrom); 
        
        LongOperation sendtomessage = new LongOperation();
		StartAsyncTaskInParallel2(sendtomessage);
		
	}
	
	void sendtoOtherDevice() {
		// Get data from EditText 
		message = msg.getText().toString(); 
		 
			// WebServer Request URL
        serverURL = Config.YOUR_SERVER_URL+"sendpush.php";
        
        // Use AsyncTask execute Method To Prevent ANR Problem
//        new LongOperation().execute(serverURL,imei,message,sendfrom); 
        
        LongOperation sendtomessage = new LongOperation();
		StartAsyncTaskInParallel2(sendtomessage);
        
	}
	
	

	// BaseAdapter 를 상속하여 어댑터 클래스를 재정의한다.
	public class ExamAdapter extends BaseAdapter {
		private LayoutInflater m_inflater = null;
		// Chat_ExamData 객체를 관리하는 ArrayList
		private ArrayList<Chat_ExamData> m_data_list;

		public ExamAdapter(ArrayList<Chat_ExamData> items) {
			m_data_list = items;
			// 인플레이터를 얻는다.
			m_inflater = (LayoutInflater) getActivity().getSystemService(
					Context.LAYOUT_INFLATER_SERVICE);
		}

		// ArrayList 에 Chat_ExamData 객체를 추가하는 메서드
		public void add(Chat_ExamData parm_data) {
			m_data_list.add(parm_data);
			// 데이터가 변화됨을 알려준다.
			notifyDataSetChanged();
		}

		// 어댑터에서 참조하는 ArrayList 가 가진 데이터의 개수를 반환하는 함수
		@Override
		public int getCount() {
			return m_data_list.size();
		}

		// 인자로 넘어온 값에 해당하는 데이터를 반환하는 함수
		@Override
		public Chat_ExamData getItem(int position) {
			return m_data_list.get(position);
		}

		// 인자로 넘어온 값에 해당하는 행 ID 를 반환하는 메서드
		@Override
		public long getItemId(int position) {
			return position;
		}

		// 인자로 넘어온 값에 해당하는 뷰의 타입을 반환하는 메서드
		@Override
		public int getItemViewType(int position) {
			return m_data_list.get(position).type;
		}

		// getView 메서드로 생성될 수 있는 뷰의 수를 반환하는 메서드
		@Override
		public int getViewTypeCount() {
			return 4;
		}

		// 각 항목에 출력될 뷰를 구성하여 반환하는 메서드
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = null;
			// 해당 항목의 뷰 타입을 얻는다.
			int type = getItemViewType(position);

			// convertView 뷰는 어댑터가 현재 가지고 있는 해당 항목의 뷰객체이다.
			// null 이 넘어오는 경우에만 새로 생성하고, 그렇지않은 경우에는 그대로 사용한다.
			if (convertView == null) {
				// 타입에 따라 각기 다른 XML 리소스로 뷰를 생성한다.
				switch (type) {
				case 0:
					view = m_inflater.inflate(R.layout.chat_list_item1, null);
					break;
				case 1:
					view = m_inflater.inflate(R.layout.chat_list_item2, null);
					break;
				case 2:
					view = m_inflater.inflate(R.layout.chat_list_item3, null);
					break;
				case 3:
					view = m_inflater.inflate(R.layout.chat_list_item4, null);
					break;
					/*case 4:
					view = m_inflater.inflate(R.layout.chat_receive_list_item1, null);
					if (emo == R.drawable.emo_dontlike) {

						final MediaPlayer snd = MediaAssets.getInstance()
								.getSoundPlayer(R.raw.dontlike);
						snd.seekTo(0);
						snd.setOnSeekCompleteListener(new OnSeekCompleteListener() {
							public void onSeekComplete(MediaPlayer mp) {
								snd.start();
							}
						});}*/
				/*case 5:
					view = m_inflater.inflate(R.layout.chat_receive_list_item2, null);
					break;*/

				}
			} else {
				view = convertView;
			}

			// 요청하는 항목에 해당하는 데이터 객체를 얻는다.
			Chat_ExamData data = m_data_list.get(position);

			// 데이터가 존재하는 경우
			if (data != null) {
				// 뷰 타입에 따라 값을 설정한다.
				if (type == 0) {
					TextView user_tv = null, msg_tv = null, date_tv = null;
					user_tv = (TextView) view.findViewById(R.id.user_view1);
					msg_tv = (TextView) view.findViewById(R.id.message_view1);
					date_tv = (TextView) view.findViewById(R.id.date_view1);

					// 이름, 메세지, 시간정보를 텍스트뷰에 설정한다.
					user_tv.setText(R.string.m_user_name);
					msg_tv.setText(data.data1);
					date_tv.setText(data.data2);
				} else if (type == 1) {
					TextView msg_tv = null, date_tv = null;
					msg_tv = (TextView) view.findViewById(R.id.message_view2);
					date_tv = (TextView) view.findViewById(R.id.date_view2);
					// 메세지, 시간정보를 텍스트뷰에 설정한다.
					msg_tv.setText(data.data1);
					date_tv.setText(data.data2);
				} else if (type == 2) {

					ImageView emo_tv = null;
					TextView msg_tv = null, date_tv = null;
					emo_tv = (ImageView) view.findViewById(R.id.img_view3);
					msg_tv = (TextView) view.findViewById(R.id.message_view3);
					date_tv = (TextView) view.findViewById(R.id.date_view3);
					// 메세지, 시간정보를 텍스트뷰에 설정한다.

					emo_tv.setImageResource(data.data3);
					msg_tv.setText(data.data1);
					date_tv.setText(data.data2);
				}

				else if (type == 3) {

					ImageView emo_tv = null;
					TextView date_tv = null;
					emo_tv = (ImageView) view.findViewById(R.id.img_view4);
					date_tv = (TextView) view.findViewById(R.id.date_view4);
					// 메세지, 시간정보를 텍스트뷰에 설정한다.

					emo_tv.setImageResource(data.data3);
					date_tv.setText(data.data2);
				}
		/*		else if (type == 4) {

					ImageView emo_tv = null;
					TextView date_tv = null;
					emo_tv = (ImageView) view.findViewById(R.id.receive_emo01);
					// 메세지, 시간정보를 텍스트뷰에 설정한다.

					emo_tv.setImageResource(data.data3);
				}
				else if (type == 5) {

					ImageView emo_tv = null;
					TextView date_tv = null;
					emo_tv = (ImageView) view.findViewById(R.id.receive_emo02);
					date_tv = (TextView) view.findViewById(R.id.receive_message_view2);
					// 메세지, 시간정보를 텍스트뷰에 설정한다.

					emo_tv.setImageResource(data.data3);
					date_tv.setText(data.data2);
				}*/
			}
			// 뷰를 반환한다.
			return view;
		}
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void StartAsyncTaskInParallel(AsyncTask02 task) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
			task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		else
			task.execute();
	}
	
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void StartAsyncTaskInParallel2(LongOperation task) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
			task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,serverURL,imei,message,sendfrom);
		else
			task.execute(serverURL,imei,message,sendfrom); 
	}
	

	public class LongOperation extends AsyncTask<String, Void, String> {

		// Required initialization

		// private final HttpClient Client = new DefaultHttpClient();
		// private Controller aController = null;
		private String Error = null;
		private ProgressDialog Dialog = new ProgressDialog(getActivity());
		String data = "";
		int sizeData = 0;

		protected void onPreExecute() {
			// NOTE: You can call UI Element here.

			// Start Progress Dialog (Message)

			Dialog.setMessage("Please wait..");
			Dialog.show();

		}

		// Call after onPreExecute method
		protected String doInBackground(String... params) {

			/************ Make Post Call To Web Server ***********/
			BufferedReader reader = null;
			String Content = "";
			// Send data
			try {

				// Defined URL where to send data
				URL url = new URL(params[0]);

				// Set Request parameter
				if (!params[1].equals(""))
					data += "&" + URLEncoder.encode("data1", "UTF-8") + "="
							+ params[1].toString();
				if (!params[2].equals(""))
					data += "&" + URLEncoder.encode("data2", "UTF-8") + "="
							+ params[2].toString();
				if (!params[3].equals(""))
					data += "&" + URLEncoder.encode("data3", "UTF-8") + "="
							+ params[3].toString();

				// Send POST data request

				URLConnection conn = url.openConnection();
				conn.setDoOutput(true);
				OutputStreamWriter wr = new OutputStreamWriter(
						conn.getOutputStream());
				wr.write(data);
				wr.flush();

				// Get the server response

				reader = new BufferedReader(new InputStreamReader(
						conn.getInputStream()));
				StringBuilder sb = new StringBuilder();
				String line = null;

				// Read Server Response
				while ((line = reader.readLine()) != null) {
					// Append server response in string
					sb.append(line + "\n");
				}

				// Append Server Response To Content String
				Content = sb.toString();
			} catch (Exception ex) {
				Error = ex.getMessage();
			} finally {
				try {

					reader.close();
				}

				catch (Exception ex) {
				}
			}

			/*****************************************************/
			return Content;
		}

		protected void onPostExecute(String Result) {
			// NOTE: You can call UI Element here.

			// Close progress dialog
			Dialog.dismiss();

			if (Error != null) {
				Toast.makeText(getActivity(), "Error: " + Error,
						Toast.LENGTH_LONG).show();

			} else {

				// Show Response Json On Screen (activity)
				Toast.makeText(getActivity(), "Message sent." + Result,
						Toast.LENGTH_LONG).show();

			}
		}

	}

}
