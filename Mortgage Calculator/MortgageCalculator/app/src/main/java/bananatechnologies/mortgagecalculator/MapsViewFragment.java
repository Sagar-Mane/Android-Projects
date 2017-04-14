package bananatechnologies.mortgagecalculator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author Sagar Mane
 * @version 1.0
 */

public class MapsViewFragment extends android.support.v4.app.Fragment {

    private String title;
    private int page;

    public static MapsViewFragment newInstance(int page, String title) {

        MapsViewFragment fragmentSecond = new MapsViewFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentSecond.setArguments(args);
        return fragmentSecond;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.maps_view_fragment,container,false);
        return rootView;
    }
}
