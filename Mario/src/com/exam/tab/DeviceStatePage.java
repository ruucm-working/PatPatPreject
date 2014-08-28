package com.exam.tab;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

import com.actionbarsherlock.app.SherlockListFragment;
import com.exam.R;
import com.exam.TextPref;

public class DeviceStatePage extends SherlockListFragment {


	
	public static TextPref bPref;
	
	
	/** An array of items to display */
    String device_state_list[] = new String[]{
            "MANUFACTURER",
            "MODEL",
            "PRODUCT",
            "NetworkCountryIso",
            "SimCountryIso"
    };
    
    String device_states[] ;
    
    int android_images[] = new int[]{
            R.drawable.jb,
            R.drawable.ics,
            R.drawable.honeycomb,
            R.drawable.gingerbread,
            R.drawable.froyo
    };
	
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    		Bundle savedInstanceState) {
    	     	
		try {
			bPref = new TextPref("mnt/sdcard/SsdamSsdam/bprofile.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}

		bPref.Ready();
		
		Log.d("DeviceStatePage",".Ready();");
		
		
		device_states = new String[] { bPref.ReadString("MANUFACTURER", ""),
				bPref.ReadString("MODEL", ""), bPref.ReadString("PRODUCT", ""),
				bPref.ReadString("NetworkCountryIso", ""),
				bPref.ReadString("SimCountryIso", "")

		};

		bPref.EndReady();
		
		Log.d("DeviceStatePage","EndReady();();");
		
		
        List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();

        for(int i=0;i<5;i++){
                HashMap<String, String> hm = new HashMap<String,String>();
            hm.put("txt01", device_state_list[i]);
            hm.put("txt02", device_states[i]);
            hm.put("img", Integer.toString(android_images[i]  ) );
            aList.add(hm);
        }

        
        Log.d("DeviceStatePage","EndReady();();");
        
        
        // Keys used in Hashmap
        String[] from = { "img", "txt01", "txt02"};

        // Ids of views in listview_layout
        int[] to = { R.id.img, R.id.txt01, R.id.txt02};
        
        
        Log.d("DeviceStatePage","int[] to =");

        // Instantiating an adapter to store each items
        // R.layout.listview_layout defines the layout of each item
        SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(), aList, R.layout.device_state, from, to);        
        
        Log.d("DeviceStatePage","SimpleAdapter adapt");
        
        // Setting the adapter to the listView
        setListAdapter(adapter);      
        
        Log.d("DeviceStatePage","setListAdapter");
        
        return super.onCreateView(inflater, container, savedInstanceState);

    }
	
	
}