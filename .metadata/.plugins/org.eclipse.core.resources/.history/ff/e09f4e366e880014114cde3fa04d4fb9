package com.example;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity 
{

	ProgressDialog dialog;	
	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        new MyNewTask().execute("http://feeds.feedburner.com/karanbalkar");
        
        Log.d("MyAsyncTask","execute");
        
    }//end oncreate	
	
	private class MyNewTask extends AsyncTask<String,Integer,String> 
	{
	    @Override
	    protected void onPreExecute()
	    {
	    	
	    	 Log.d("MyAsyncTask","onPreExecute");
	    	
	    	dialog= new ProgressDialog(MainActivity.this);
	        dialog.setIndeterminate(true);
	        dialog.setIndeterminateDrawable(getResources().getDrawable(R.anim.progress_dialog_anim));
	        dialog.setCancelable(false);
	        dialog.setMessage("Downloading! Please wait...!");
	        dialog.show();
	                      	
	    }
	  
	    
	    @Override
	    protected String doInBackground(String... params)
	    {
	    	//don't interact with UI
	    	//do something in the background over here
	    	
	    	 Log.d("MyAsyncTask","doInBackground");
	    	
	    	String url=params[0];
	    	
	    	for (int i = 0; i <= 100; i += 5) 
	    	{
	    		     try{     
	    			    Thread.sleep(1000);
	    		        } catch (InterruptedException e) 
	    		        {
	    		          e.printStackTrace();
	    		        }
	    		     
	         }
	    	
 	    return "Done!";	       
	  }

	    @Override
	    protected void onPostExecute(String result) 
	    {
	    	
	    	 Log.d("MyAsyncTask","onPostExecute");
	    	super.onPostExecute(result);
	    	Log.i("result","" +result);
	    	Toast.makeText(getApplicationContext(),"Downloading Done...!",Toast.LENGTH_LONG).show();
	    	if(result!=null)
	    		dialog.dismiss();
	    }
	    
	} //end MyNewTask


    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
