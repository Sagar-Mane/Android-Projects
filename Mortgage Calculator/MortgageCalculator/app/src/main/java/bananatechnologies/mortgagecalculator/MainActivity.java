package bananatechnologies.mortgagecalculator;

import android.content.Intent;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;

import com.astuetz.PagerSlidingTabStrip;

/**
 * @author Sagar Mane
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {

    CalculationViewPagerAdapter adpter;
    PagerTabStrip header;
    SeekBar loan_amount1;
    SeekBar down_payment1;
    SeekBar apr1;

    private static final String TAG = "haha";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager vp=(ViewPager) findViewById(R.id.pager);
        adpter=new CalculationViewPagerAdapter(getSupportFragmentManager());
        vp.setAdapter(adpter);

        // Give the PagerSlidingTabStrip the ViewPager
        PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        // Attach the view pager to the tab strip
        tabsStrip.setViewPager(vp);

        Intent maps_info_window_clicks=getIntent();
        int loan_amount=maps_info_window_clicks.getIntExtra("loan_amount",0);
        int down_payment=maps_info_window_clicks.getIntExtra("down_payment",0);
        int apr=maps_info_window_clicks.getIntExtra("apr",0);


        loan_amount1=(SeekBar) findViewById(R.id.loan_amount1);
        down_payment1=(SeekBar)findViewById(R.id.down_payment1);
        apr1=(SeekBar)findViewById(R.id.apr1);

        Log.i(TAG,"value="+loan_amount1);
        Log.i(TAG,"loan_amount +"+loan_amount);
        Log.i(TAG,"down_payment +"+down_payment);
        Log.i(TAG,"apr +"+apr);
//        CalculationViewFragment.on_edit_call();
        /*loan_amount1.setProgress(loan_amount);
        down_payment1.setProgress(down_payment);
        apr1.setProgress(apr);*/


    }

}
