package ji.ruucm.android.vertical_view.fragment;

import ji.ruucm.android.chat.R;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Demonstrates combining a TabHost with a ViewPager to implement a tab UI that
 * switches between tabs and also allows the user to perform horizontal flicks
 * to move between the tabs.
 */
public class Activity_ScheduleAdd extends FragmentActivity {

	// for Activity anim
	@Override
	public void finish() {
		super.finish();
		this.overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// for Activity anim
		this.overridePendingTransition(R.anim.start_enter, R.anim.start_exit);
		setContentView(R.layout.activity_scheduleadd);
		
/*		
		
		Dialog_DatePicker b = new Dialog_DatePicker();
		b.setOnDateSetListener(new OnDateSetListener(){
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
		        Calendar cal = Calendar.getInstance();
		        cal.set(year, monthOfYear, dayOfMonth);
				
				
				Log.d("Activity_ScheduleAdd","onDateSet ");
				TextView scheduledate = (TextView) findViewById(R.id.scheduledate);
				scheduledate.setText(year+"년 "+monthOfYear+"월 "+dayOfMonth+"일");
				Log.d("Activity_ScheduleAdd","setText ");

				
		}

		});
		*/
		
		
		

	}
	
	
	public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new Dialog_DatePicker();
        newFragment.show(getSupportFragmentManager(), "datePicker");
        ((Dialog_DatePicker) newFragment).setOnDateSetListener(new OnDateSetListener(){
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
		        /*Calendar cal = Calendar.getInstance();
		        cal.set(year, monthOfYear, dayOfMonth);*/
				
				
				Log.d("Activity_ScheduleAdd","onDateSet ");
				TextView scheduledate = (TextView) findViewById(R.id.scheduledate);
				scheduledate.setText(year+"년 "+(monthOfYear+1)+"월 "+dayOfMonth+"일");
				Log.d("Activity_ScheduleAdd","setText ");

				
		}

		});
    	
    }
	
	
	public void mOnClick(View v){

		switch(v.getId()){    	

		case R.id.ok:    
			
			EditText schedulename = (EditText) findViewById(R.id.schedulename);

			
			Intent intent = new Intent();    		
			intent.putExtra("schedulename", schedulename.getText().toString());
			setResult(RESULT_OK,intent)	;


			finish(); 
			
			
			
			
			

			break;

		case R.id.cancle:
			
			setResult(RESULT_CANCELED);
			
			finish();
			
			break;

		}
	}
	
	

	
}
