package com.example.inspire.gameoflife;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
/**
 * Created by Sagar Mane on 23-02-2017.
 */

public class Grid_Layout_Fragment extends Fragment{
    Grid_Layout live_grid;
    static int count=0;
    SavingInstances saved;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.grid_fragment,container,false);
        live_grid=(Grid_Layout)v.findViewById(R.id.grid);
        saved=SavingInstances.getInstance();
        saved.saveGridInstance(live_grid);

                /*if(count==0){
            System.out.println("###########################Making copy of grid#############################################################");
            live_grid=(Grid_Layout)v.findViewById(R.id.grid);
            saved=SavingInstances.getInstance();
            saved.saveGridInstance(live_grid);
        }
        else{
            saved=SavingInstances.getInstance();
            //saved.print_Instance();
            live_grid=saved.returnInstance();
            System.out.println("recreating fragments       "+live_grid);
        }*/



        count++;
        return v;
    }

    public void resetGrid(){

        //System.out.println("Reset grid function in grid layout fragment.java");
        live_grid.initializeGrid();

    }
    public void goToNextCombination(){
        //System.out.println("next combination function in grid layout fragment.java");
        live_grid.nextGeneration();

    }
    public Grid_Layout returnInstance(){
        return live_grid;
    }
}
