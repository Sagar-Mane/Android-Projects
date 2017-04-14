package bananatechnologies.mortgagecalculator;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * @author Sagar Mane
 * @version 1.0
 * Don't modify this class.
 */

public class CalculationViewPagerAdapter extends FragmentPagerAdapter {

    private static int NUM_ITEMS = 2;
    public CalculationViewPagerAdapter(FragmentManager fm){
        super(fm);
    }
    @Override
    public Fragment getItem(int index) {
        switch (index) {
            case 0: // Fragment # 0 - This will show FirstFragment
                return CalculationViewFragment.newInstance(0, "Calculation");
            case 1: // Fragment # 0 - This will show FirstFragment different title
                return new MapsActivity();


            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        if(position==0)
            return "Calculation";
        else if(position==1)
            return "Maps";

        else
            return null;

//        return "Page " + position;
    }
}
