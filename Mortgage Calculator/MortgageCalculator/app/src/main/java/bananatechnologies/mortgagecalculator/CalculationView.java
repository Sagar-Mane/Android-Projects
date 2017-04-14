package bananatechnologies.mortgagecalculator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

/**
 * @author Sagar Mane
 * @version 1.0
 */

public class CalculationView extends FragmentActivity {
    CalculationViewPagerAdapter mCalculationViewPagerAdapter;
    ViewPager mViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCalculationViewPagerAdapter=new CalculationViewPagerAdapter(getSupportFragmentManager());
        mViewPager=(ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mCalculationViewPagerAdapter);

    }

}
