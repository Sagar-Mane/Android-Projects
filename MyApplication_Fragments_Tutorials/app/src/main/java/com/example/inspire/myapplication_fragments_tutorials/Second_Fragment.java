package com.example.inspire.myapplication_fragments_tutorials;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Second_Fragment extends Fragment{

    TextView results;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.second_fragment,container,false);
        results=(TextView) v.findViewById(R.id.results);
        return v;
    }
    public void showResults(String output){
        results.setText(output);
    }
}
