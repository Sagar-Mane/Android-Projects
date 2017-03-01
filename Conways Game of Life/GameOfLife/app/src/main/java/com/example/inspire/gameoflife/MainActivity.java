package com.example.inspire.gameoflife;

import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements Buttons_Fragment.buttonClickListeners{

    Grid_Layout grid;
    Grid_Layout live_grid;
    SavingInstances saved;
    public void nextClicked(){

        //System.out.println("Main Activity next clicked method");
        Grid_Layout_Fragment g=(Grid_Layout_Fragment) getFragmentManager().findFragmentById(R.id.Grid_Fragment);
        g.goToNextCombination();
    }
    public void resetClicked(){

        //System.out.println("Main Activity reset clicked method");
        Grid_Layout_Fragment g=(Grid_Layout_Fragment) getFragmentManager().findFragmentById(R.id.Grid_Fragment);
        g.resetGrid();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
           super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^Reporting from main activity on create");
        grid=new Grid_Layout(this);
        //grid.initializeGrid(12,8);
        Grid_Layout_Fragment g=(Grid_Layout_Fragment) getFragmentManager().findFragmentById(R.id.Grid_Fragment);
        live_grid=g.returnInstance();
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^Reporting from main activity on create"+live_grid);

        /*//System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^Reporting from main activity on create");
        grid=new Grid_Layout(this);
        //grid.initializeGrid(12,8);
        Grid_Layout_Fragment g=(Grid_Layout_Fragment) getFragmentManager().findFragmentById(R.id.Grid_Fragment);
        live_grid=g.returnInstance();
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^Reporting from main activity on create"+live_grid);
        live_grid.printGrid();
        */

        //if(savedInstanceState==null){
            //live_grid.printGrid();
        /*}
        else
        {
            saved=SavingInstances.getInstance();
            live_grid=saved.returnInstance();
            System.out.println("%%%%%%%%%%%%%%%%"+live_grid);
            int arr[]= savedInstanceState.getIntArray("live_cells_indexes");
            System.out.println("Reporting from on Restore from on create");
            //live_grid.printGrid();
            System.out.println("Cells need to make live "+Arrays.toString(arr));
            live_grid.onStateRestore(arr);
        }*/


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        System.out.println("Orientation changed");
        ArrayList<Integer> live_cell_indexes=new ArrayList<>();
        //live_grid.printGrid();
        saved=SavingInstances.getInstance();
        saved.saveLiveGrid(live_grid.returnCurrentGrid());

        live_grid=saved.returnInstance();
        //System.out.println("Checking live grid not null"+live_grid);

        live_cell_indexes=live_grid.liveCell_Indexes();

        int arr[]=new int[live_cell_indexes.size()];
        for(int i=0;i<live_cell_indexes.size();i++){
            arr[i]=live_cell_indexes.get(i);
        }
        System.out.println("State changing live cells are : "+ Arrays.toString(arr));
        outState.putIntArray("live_cells_indexes",arr);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        int arr[]= savedInstanceState.getIntArray("live_cells_indexes");
        System.out.println("Reporting from on Restore");
        System.out.println("Cells need to make live "+Arrays.toString(arr));

        live_grid.onStateRestore(arr);

    }
}
