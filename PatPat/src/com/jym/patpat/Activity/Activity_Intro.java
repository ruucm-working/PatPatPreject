package com.jym.patpat.Activity;


import android.app.Activity;
import android.os.Bundle;

import com.jym.patpat.R;

public class Activity_Intro extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coin_block_widget);
       /* Button btnGetWallpaper = (Button)this.findViewById(R.id.btnGetWallpaper);
        btnGetWallpaper.setOnClickListener(new OnClickListener(){
                        public void onClick(View v) {
                                Uri uri = Uri.parse("http://andytsui.wordpress.com/tag/wallpaper/");
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intent);
                        }});*/
    }
}