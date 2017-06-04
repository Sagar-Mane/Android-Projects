package com.example.inspire.myapplication_fragments_tutorials;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements First_Fragment.FragmentListeners{

    @Override
    public void showResults(String input){

        Second_Fragment s=(Second_Fragment) getFragmentManager().findFragmentById(R.id.fragment2);
        s.showResults(input);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

}
