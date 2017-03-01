package com.example.inspire.gameoflife;

/**
 * Created by Sagar Mane on 24-02-2017.
 */

public class SavingInstances {

    private static SavingInstances saved=new SavingInstances();
    static Grid_Layout Grid_copy;
    boolean live_Grid[][]=new boolean[12][12];

    private SavingInstances(){}

    public static SavingInstances getInstance(){
        return saved;
    }

    public  void saveGridInstance(Grid_Layout copy){
        Grid_copy=copy;
    }

    public Grid_Layout returnInstance(){
        return Grid_copy;
    }
    public void print_Instance(){
        System.out.println("Printing"+Grid_copy);
    }
    public void saveLiveGrid(boolean[][] copy){
        live_Grid=copy;
        System.out.println("Saving Grid");
        Grid_Layout temp=returnInstance();
        temp.printGrid(copy);
    }
    public boolean[][] returnLiveGrid(){
        return live_Grid;
    }
}
