package com.exam.tab;

import android.os.Bundle;

public class TabNavigationCollapsed extends coinBlockIntroActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //The following two options trigger the collapsing of the main action bar view.
        //See the parent activity for the rest of the implementation
        
        /*
        requestWindowFeature(Window.FEATURE_PROGRESS);
        
        int progress = (Window.PROGRESS_END - Window.PROGRESS_START) / 100 * 130;
        setSupportProgress(progress);
        
        */
        
        getSupportActionBar().setDisplayShowHomeEnabled(false); 
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
}
