package com.exam.tab;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Contacts.People;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockListFragment;
import com.exam.R;

public class DeviceStatePage extends SherlockListFragment implements
		LoaderManager.LoaderCallbacks<Cursor> {

	/*@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.device_state, container, false);
		// View tv = v.findViewById(R.id.text03);
		// ((TextView) tv).setText("Hope is a good thing.");

		return v;
	}*/
	
	
	
	SimpleCursorAdapter mAdapter;
	
	
	
	String [] aDB = new String[] { "a","b","c","a","b","c","a","b","c" } ;
	
	@Override public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Give some text to display if there is no data.  In a real
        // application this would come from a resource.
//        setEmptyText("No phone numbers");

        // We have a menu item to show in action bar.
        setHasOptionsMenu(true);

        // Create an empty adapter we will use to display the loaded data.
        mAdapter = new SimpleCursorAdapter(getActivity(),
                android.R.layout.simple_list_item_1, null,
                aDB,
                new int[] { android.R.id.text1}, 0);
        setListAdapter(mAdapter);

        // Start out with a progress indicator.
        setListShown(true);

        // Prepare the loader.  Either re-connect with an existing one,
        // or start a new one.
//        getLoaderManager().initLoader(0, null, this);
        
        
        
        
        
        
        
    }

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		// TODO Auto-generated method stub
		return new CursorLoader(getActivity());
	}

	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Swap the new cursor in.  (The framework will take care of closing the
        // old cursor once we return.)
        mAdapter.swapCursor(data);

        // The list should now be shown.
        if (isResumed()) {
            setListShown(true);
        } else {
            setListShownNoAnimation(true);
        }
    }

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub

	}
}