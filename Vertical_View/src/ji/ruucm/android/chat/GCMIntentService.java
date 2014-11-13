package ji.ruucm.android.chat;

import ji.ruucm.android.vertical_view.fragment.Frag03;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;

public class GCMIntentService extends GCMBaseIntentService {
	
	
	
	Handler handler;
	
	public static String[] StringAll = null ;
	

	private static final String TAG = "GCMIntentService";
	
	private Controller aController = null;
	
    public GCMIntentService() {
    	// Call extended class Constructor GCMBaseIntentService
        super(Config.GOOGLE_SENDER_ID);
        
        
        handler = new Handler() {
 		  public void handleMessage(Message msg) {
 			  Frag03.receiveMessage();  //필자가 원했던 UI 업데이트 작업
 		  }
 		}; 
        
    }

    /**
     * Method called on device registered
     **/
    @Override
    protected void onRegistered(Context context, String registrationId) {
    	
    	
    	//Get Global Controller Class object (see application tag in AndroidManifest.xml)
    	if(aController == null)
           aController = (Controller) getApplicationContext();
    	
    	Log.i(TAG, "---------- onRegistered -------------");
        Log.i(TAG, "Device registered: regId = " + registrationId);
        aController.displayRegistrationMessageOnScreen(context, "Your device registred with GCM");
        Log.d("NAME", MainActivity2.name);
        
        aController.register(context, MainActivity2.name, MainActivity2.email, registrationId,MainActivity2.imei);
        
        DBAdapter.addDeviceData(MainActivity2.name, MainActivity2.email, registrationId, MainActivity2.imei);
    
        
        
    }

    /**
     * Method called on device unregistred
     * */
    @Override
    protected void onUnregistered(Context context, String registrationId) {
    	if(aController == null)
            aController = (Controller) getApplicationContext();
    	Log.i(TAG, "---------- onUnregistered -------------");
        Log.i(TAG, "Device unregistered");
        aController.displayRegistrationMessageOnScreen(context, getString(R.string.gcm_unregistered));
        aController.unregister(context, registrationId,MainActivity2.imei);
    }

    /**
     * Method called on Receiving a new message from GCM server
     * */
    @Override
    protected void onMessage(Context context, Intent intent) {
    	
    	if(aController == null)
            aController = (Controller) getApplicationContext();
    	
    	Log.i(TAG, "---------- onMessage -------------");
        String message = intent.getExtras().getString("message");
        
        Log.i("GCM","message : "+message);
        
        
		StringAll = message.split("\\^");

		String title = "";
		String imei  = "";
		
		int StringLength = StringAll.length;
		if (StringLength > 0) {

			title   = StringAll[0];
			imei    = StringAll[1];
			message = StringAll[2];
		}
		
		 // Call broadcast defined on ShowMessage.java to show message on ShowMessage.java screen
         aController.displayMessageOnScreen(context, title,message,imei);
         
         // Store new message data in sqlite database
         UserData userdata = new UserData(1,imei,title,message);
         DBAdapter.addUserData(userdata);
         
        // generate notification to notify user
        generateNotification(context, title,message,imei);
        
//        Frag03 av = new Frag03();
        
        
        
//        Frag03.receiveMessage();
        
        /*
        new Thread() {
        	   public void run() {
//        	    setClipList();  // 어떤 리스트를 세팅한다. = 쓰레드에서 하는 주 작업
        	    Message msg = handler.obtainMessage();
        	    handler.sendMessage(msg);
        	   }
        	  }.start();
        	  */
        Message msg = handler.obtainMessage();
        handler.sendMessage(msg);
        	  
    }

    /**
     * Method called on receiving a deleted message
     * */
    @Override
    protected void onDeletedMessages(Context context, int total) {
    	
    	if(aController == null)
            aController = (Controller) getApplicationContext();
    	
    	Log.i(TAG, "---------- onDeletedMessages -------------");
        String message = getString(R.string.gcm_deleted, total);
        
        String title = "DELETED";
        // aController.displayMessageOnScreen(context, message);
        
        // generate notification to notify user
        generateNotification(context,title, message,"");
    } 

    /**
     * Method called on Error
     * */
    @Override
    public void onError(Context context, String errorId) {
    	
    	if(aController == null)
            aController = (Controller) getApplicationContext();
    	
    	Log.i(TAG, "---------- onError -------------");
        Log.i(TAG, "Received error: " + errorId);
        
        aController.displayRegistrationMessageOnScreen(context, getString(R.string.gcm_error, errorId));
    }

    @Override
    protected boolean onRecoverableError(Context context, String errorId) {
    	
    	if(aController == null)
            aController = (Controller) getApplicationContext();
    	
    	Log.i(TAG, "---------- onRecoverableError -------------");
    	
        // log message
        Log.i(TAG, "Received recoverable error: " + errorId);
        aController.displayRegistrationMessageOnScreen(context, getString(R.string.gcm_recoverable_error,
                errorId));
        return super.onRecoverableError(context, errorId);
    }

    
    /**
     * Create a notification to inform the user that server has sent a message.
     */
    private static void generateNotification(Context context,String title, String message, String imei) {
        int icon = R.drawable.app_icon;
        long when = System.currentTimeMillis();
        
		NotificationManager notificationManager = (NotificationManager)
        context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification(icon, message, when);

        Intent notificationIntent = new Intent(context, ShowMessage.class);
        // set intent so it does not start a new activity
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_SINGLE_TOP);
        
        notificationIntent.putExtra("name", title);
        notificationIntent.putExtra("message", message);
        notificationIntent.putExtra("imei", imei);
		
        PendingIntent intent =
                PendingIntent.getActivity(context, 0, notificationIntent, 0);
        notification.setLatestEventInfo(context, title, message, intent);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        
        // Play default notification sound
        notification.defaults |= Notification.DEFAULT_SOUND;
        
        //notification.sound = Uri.parse("android.resource://" + context.getPackageName() + "your_sound_file_name.mp3");
        
        // Vibrate if vibrate is enabled
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notificationManager.notify(0, notification);   
        
        
    }

}
