package com.exam.tab;
 
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.exam.R;
 
public class FragmentTab2 extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.profile, container, false);
        View tv = v.findViewById(R.id.text03);
        ((TextView) tv).setText("Hope is a good thing.");
        return v;
    }
}