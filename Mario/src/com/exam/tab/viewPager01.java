package com.exam.tab;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.exam.R;

public class viewPager01 extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
        	View v = inflater.inflate(R.layout.device_condition, container, false);
            //View tv = v.findViewById(R.id.text02);
            //((TextView) tv).setText("coolkim.tistory.com");
            return v; 
        }
    }