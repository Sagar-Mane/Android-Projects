package com.example.inspire.gameoflife;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Sagar Mane on 20-02-2017.
 */

public class Grid_Layout extends View{
    int rows=12;
    int columns=12;
    float width;
    float height;
    static boolean orientation_changed=false;
    ArrayList<Integer> live_cells=new ArrayList<>();
    ArrayList<Integer> dead_cells=new ArrayList<>();
    int row,column;

    int cell_width;
    int cell_height;

    boolean Grid[][]=new boolean[rows][columns];
    boolean Grid_Copy[][]=new boolean[rows][columns];
    public Grid_Layout(Context context, AttributeSet attrs) {

        super(context, attrs);
        setWillNotDraw(false);
    }

    public Grid_Layout(Context context) {
        super(context);
    }


    public void initializeGrid(){
        //System.out.println("@@@@@@@@@@@@@@@@@@@@@@@Printing grid before resetting");
        //printGrid();
        //System.out.println("Reporting from GridLayout.java resetting grid");
        for(int i=0;i<rows;i++){
            for(int j=0;j<columns;j++){
                Grid[i][j]=false;
            }
        }
        //System.out.println("@@@@@@@@@@@@@@@@@@@@@@@Printing grid before invalidate call");
        //printGrid();
        invalidate();
        //System.out.println("@@@@@@@@@@@@@@@@@@@@@@@Printing grid after invalidate call");
        //printGrid();

    }
    public void nextGeneration(){
        System.out.println("Generating next generation of the grid");
        changeGridCells_Rules();
        nextGenerationRedraw();
    }
    @Override
    protected void onDraw(Canvas canvas) {

        printGrid(Grid);

        System.out.println("OnDraw called Orientation flag="+orientation_changed);
        //sprintGrid();
        super.onDraw(canvas);
        Rect grid=new Rect();
        grid.set(0,0,canvas.getWidth(),canvas.getHeight());
        Paint grey=new Paint();
        grey.setColor(Color.GRAY);
        grey.setStyle(Paint.Style.FILL);

        Paint red=new Paint();
        red.setColor(Color.RED);

        Paint black=new Paint();
        black.setColor(Color.BLACK);

        DrawLines(canvas,rows,columns);
        for(int i=0;i<rows;i++)
        {
            for(int j=0;j<columns;j++){
                try {
                    if(Grid[i][j]){
                            //*System.out.println("Drawing clicked cell "+i+"&"+j);
                            //System.out.println(cell_height);
                            //System.out.println(cell_width);
                            //canvas.drawRect((j*cell_width)+2,(i*cell_height)+2,((j+1)*cell_width)-1,((i+1)*cell_height)-1,red);
                            canvas.drawCircle((((j*cell_width)+2)+(cell_width/2)),(((i*cell_height)+2)+(cell_height/2)),(cell_height/2)-13,red);
                        }

                }
                catch (NullPointerException e){
                    //System.out.println("-------------Exception occured----------"+e);
                }

            }
        }
        //changeGridCells_Rules();                  //************************Important*********************************
        //surroundingLiveCells(row-1,column-1,typeOfCell(row-1,column-1));

        //surroundingDeadCells(row,column,typeOfCell(row-1,column-1));

    }
    public void printGrid(boolean g[][]){
        for(int i=0;i<rows;i++)
        {
            for(int j=0;j<columns;j++){
                System.out.print(g[i][j]+" ");
            }
            System.out.println("");
        }
    }
    public boolean isChecked(int row,int column)
    {
        boolean flag=false;
        for(int i=0;i<rows;i++)
        {
            for(int j=0;j<columns;j++){
                if(Grid[i][j])
                {
                    flag=true;
                }
            }
        }
        return flag;
    }

    public void DrawLines(Canvas canvas,int rows, int columns){

        width=canvas.getWidth();
        height=canvas.getHeight();

        cell_width=(int) (width/columns);
        cell_height=(int)(height/rows);

        Paint black=new Paint();
        black.setColor(Color.BLACK);
        black.setStyle(Paint.Style.FILL_AND_STROKE);
        //Drawing Basic Grid.
        int start_x=0;
        int start_y=0;
        //Drawing columns.
        /*for(int i=0;i<=columns;i++){
            System.out.println("Drawing column "+i);
            canvas.drawLine(start_x,0,start_x,canvas.getHeight(),black);
            start_x=start_x+canvas.getWidth()/columns;
        }*/

        //test

        for (int i=0;i <=12; i++) {
            canvas.drawLine(i * cell_width, 0, i * cell_width, height, black);
        }
        //test
        //Drawing rows.
        //test
        for (int i = 0; i < 13; i++) {
            canvas.drawLine(0, i * cell_height, width, i * cell_height, black);

        }
        //test

        /*for(int i=0;i<=rows;i++){
            System.out.println("Drawing row "+i);
            canvas.drawLine(0,start_y,canvas.getWidth(),start_y,black);

            start_y=start_y+canvas.getHeight()/rows;
        }*/


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(event.getAction()==MotionEvent.ACTION_DOWN){

            float x=event.getX();
            float y=event.getY();

            row=(int) ((y/(height/rows))+1);        //touched cell row number.
            column=(int)((x/(width/columns))+1);    //touched cell column number.
            //System.out.println("Row number "+row+"Column number" +column);
            try

            {
                if (Grid[row - 1][column - 1]) {
                    Grid[row - 1][column - 1] = false;
                } else
                    Grid[row - 1][column - 1] = true;
                //typeOfCell(row,column);
            }
            catch (NullPointerException e){
                System.out.println("---------Exception occured--------"+e);
            }
            Grid_Copy=Grid;
            invalidate(); //redraw canvas
        }

        return super.onTouchEvent(event);
    }

    public void changeGridCells_Rules(){
        //First rule
        // Any live cell with fewer than two live neighbours dies, as if caused by underpopulation.

        ArrayList<Integer> live_cells_indexes=new ArrayList<>();
        ArrayList<Integer> dead_cells_indexes=new ArrayList<>();

        System.out.println("Welcome to Rules section");
        //printGrid();
        for(int i=0;i<rows;i++){
            for(int j=0;j<columns;j++){

                try{

                    if(Grid[i][j]){
                        //Live cell
                        //check neighbours less than two dies
                        // check neighbours equal or more than two lives
                        //check surrounding cells
                        //for checking surrounding cells 3 situations
                        // 1. Any normal cell = 8 neighbours
                        // 2. side cells      = 5 neighbours
                        // 3. corner cells    = 3 neighbours
                        int neighbour=typeOfCell(i,j);
                        System.out.println("row= "+i+"column="+j+"cell type="+neighbour);
                        if(neighbour==8){
                            //normal cell;
                            // get number of live surrounding cells
                            //System.out.println("Normal cell ");

                            System.out.println("###Surrounding live cells="+surroundingLiveCells(i,j,neighbour));
                            System.out.println("###Surrounding dead cells="+(8-surroundingLiveCells(i,j,neighbour)));
                        if(surroundingLiveCells(i,j,neighbour)<2){                          //Under population Rule
                            //Grid[i][j]=false;
                            dead_cells_indexes.add(i);
                            dead_cells_indexes.add(j);
                            //invalidate();
                        }
                        else if(surroundingLiveCells(i,j,neighbour)>3){                     //Over population Rule
                            //Grid[i][j]=false;
                            dead_cells_indexes.add(i);
                            dead_cells_indexes.add(j);
                            //invalidate();
                        }

                        }
                        else if(neighbour==5){
                            //side cell
                            //get number of live surrounding cells
                            //System.out.println("###########Side cell");
                            System.out.println("###Surrounding live cells="+surroundingLiveCells(i,j,neighbour));
                            System.out.println("###Surrounding dead cells="+(5-surroundingLiveCells(i,j,neighbour)));
                        //surroundingLiveCells(i,j,neighbour);

                        if(surroundingLiveCells(i,j,neighbour)<2) {                         //Under population Rule
                            //Grid[i][j] = false;
                            dead_cells_indexes.add(i);
                            dead_cells_indexes.add(j);
                            //invalidate();
                        }
                        else if(surroundingLiveCells(i,j,neighbour)>3) {                    //Over population Rule
                            //Grid[i][j] = false;
                            dead_cells_indexes.add(i);
                            dead_cells_indexes.add(j);
                            //invalidate();
                        }

                        }
                        else if(neighbour==3){
                            //corner cell;
                            //get number of live surrounding cells
                            //System.out.println("Corner cell");
                            System.out.println("###Surrounding live cells="+surroundingLiveCells(i,j,neighbour));
                            System.out.println("###Surrounding dead cells="+(3-surroundingLiveCells(i,j,neighbour)));
                        //surroundingLiveCells(i,j,neighbour);

                        if(surroundingLiveCells(i,j,neighbour)<2) {                             //Under population Rule
                            //Grid[i][j] = false;
                            dead_cells_indexes.add(i);
                            dead_cells_indexes.add(j);
                            //invalidate();
                        }
                        else if(surroundingLiveCells(i,j,neighbour)>3) {                        //Over population Rule
                            //Grid[i][j] = false;
                            dead_cells_indexes.add(i);
                            dead_cells_indexes.add(j);
                            //invalidate();
                        }
                        }
                    }
                    else{
                        int neighbour=typeOfCell(i,j);
                        if(neighbour==8){
                            //normal dead cell
                            if(surroundingLiveCells(i,j,neighbour)==3){                          //Survival Rule
                                //reproduction
                                live_cells_indexes.add(i);
                                live_cells_indexes.add(j);
                                //Grid[i][j]=true;

                            }
                        }
                        else if(neighbour==5){
                            //side dead cell
                            if(surroundingLiveCells(i,j,neighbour)==3){
                                //reproduction
                                live_cells_indexes.add(i);
                                live_cells_indexes.add(j);
                                //Grid[i][j]=true;

                            }
                        }
                        else if(neighbour==3){
                            //corner dead cell
                            if(surroundingLiveCells(i,j,neighbour)==3){
                                //reproduction
                                live_cells_indexes.add(i);
                                live_cells_indexes.add(j);
                                //Grid[i][j]=true;
                            }
                        }
                    }


                }
                catch (NullPointerException e){
                    System.out.println("------------Exception occured---------" +e);
                }



            }
        }
        /*System.out.println("live cells array size"+live_cells_indexes);
        System.out.println("dead cells array size"+dead_cells_indexes);
        System.out.println("***********End of this generation printing indexes of cells which need to be changed to dead");
        */

        //Now this is where grid will be moved to next generation

        live_cells=live_cells_indexes;
        dead_cells=dead_cells_indexes;

        /*for(int i=0;i<live_cells_indexes.size()-1;i=i+2){
            //System.out.println("*******making live ##row i="+live_cells_indexes.get(i)+"Column j="+live_cells_indexes.get(i+1));
            Grid[live_cells_indexes.get(i)][live_cells_indexes.get(i+1)]=true;
        }
        for(int i=0;i<dead_cells_indexes.size()-1;i=i+2){
            //System.out.println("*******making dead ##row i="+dead_cells_indexes.get(i)+"Column j="+dead_cells_indexes.get(i+1));
            Grid[dead_cells_indexes.get(i)][dead_cells_indexes.get(i+1)]=false;

        }
        invalidate();*//*
*/

    }
    public void nextGenerationRedraw(){
        System.out.println("Next generation redraw");
        System.out.println(live_cells);
        System.out.println(dead_cells);
        //printGrid(Grid_Copy);
        for(int i=0;i<live_cells.size()-1;i=i+2){
            //System.out.println("*******making live ##row i="+live_cells_indexes.get(i)+"Column j="+live_cells_indexes.get(i+1));
            //Grid[live_cells.get(i)][live_cells.get(i+1)]=true;
            if(live_cells!=null)
            {
                Grid[live_cells.get(i)][live_cells.get(i+1)]=true;
            }
        }
        for(int i=0;i<dead_cells.size()-1;i=i+2){
            //System.out.println("*******making dead ##row i="+dead_cells_indexes.get(i)+"Column j="+dead_cells_indexes.get(i+1));
            //Grid[dead_cells.get(i)][dead_cells.get(i+1)]=false;
            if(dead_cells!=null){
                System.out.println("*******making dead ##row i="+dead_cells.get(i)+"Column j="+dead_cells.get(i+1));
                Grid[dead_cells.get(i)][dead_cells.get(i+1)]=false;
            }
        }
        System.out.println("Calling invalidate");
        invalidate();
    }
    public int typeOfCell(int row,int column){

        //row=row-1;
        //column=column-1;
        //System.out.println("Identifying cell type row="+row+"Column="+column );
        if(row>0&&column>0&&row<rows-1&&column<columns-1){

            //Normal cell 8 neighbours
            //System.out.println("Normal cell with 8 neighbours");
            return 8;
        }
        else if((row==0&&column==0)||(row==rows-1&&column==0)||(column==columns-1&&row==0)||(row==rows-1&&column==columns-1)){
            //Side cells
            //System.out.println("Corner cells with 3 neighbours");
            return 3;
        }
        else if((column==0&&row>0&&row<rows-1)||(row==0&&column>0&&column<columns-1)||(row==rows-1&&column>0&&column<columns)||(column==columns-1)&&row>0&&row<rows-1){
            //corner cells
            //System.out.println("Side cell with 5 neighbours");
            return 5;
        }
        return 0;
    }
    public int surroundingLiveCells(int row, int column, int type){
        //Return number of live cells surrounding this cell
        int count=0;
        //System.out.println("Current cell row= "+row+"column= "+column);
        int live_cells=0;
        switch (type){
            case 8:
                for(int i=row-1;i<row+2;i++){
                    for(int j=column-1;j<column+2;j++){
                        count++;
                        //System.out.println("Navigating around surrounding cells "+count);
                        if(i==row&&j==column)
                            continue;
                        if(Grid[i][j]){
                            //System.out.println("********************lIVE CELL row= "+i+"column= "+j);
                            live_cells++;
                        }

                    }
                }
                //System.out.println("Normal cell - Surrounding live cells ="+live_cells);
                break;
            case 5:
                if(column==0){

                    //leftmost column
                    for(int i=row-1;i<row+2;i++){
                        for(int j=0;j<2;j++){
                            //System.out.println("Navigating around surrounding cells on leftmost column row="+i+"column="+j);
                            if(i==row&&j==column)
                                continue;
                            if(Grid[i][j]){
                                //System.out.println("*********lIVE CELL row= "+i+"column= "+j);
                                live_cells++;
                            }
                        }
                    }
                    //System.out.println("Side cell - Surrounding live cells ="+live_cells);


                }
                else if(row==0){
                    //topmost row
                    //System.out.println("Topmost Row");
                    for(int i=row;i<2;i++){
                        for(int j=column-1;j<column+2;j++){
                            if(i==row&&j==column)
                                continue;
                            if(Grid[i][j]){
                                live_cells++;
                            }
                        }
                    }
                    //System.out.println("Number of live cells topmost row"+live_cells);
                    //System.out.println("Side cell - Surrounding live cells ="+live_cells);


                }
                else if(row==rows-1){
                    //bottommost row
                    //System.out.println("##########Bottommost Row side cells");
                    for(int i=row-1;i<row+1;i++){

                        for(int j=column-1;j<column+2;j++){
                            //System.out.println("Navigating in bottom most row "+i+" "+j);
                            if(i==row&&j==column)
                                continue;
                            if(Grid[i][j]){
                                live_cells++;
                            }

                        }
                    }
                    //System.out.println("Number of live cells bottommost row"+live_cells);
                    //System.out.println("Side cell - Surrounding live cells ="+live_cells);

                }
                else if(column==columns-1){
                    //rightmost column
                    //System.out.println("righmost Column");
                    for(int i=row-1;i<row+2;i++){
                        for(int j=column-1;j<column+1;j++){
                            if(i==row&&j==column)
                                continue;
                            if(Grid[i][j]){
                                live_cells++;
                            }
                        }
                    }
                    //System.out.println("Number of live cells rightmost column"+live_cells);
                   // System.out.println("Side cell - Surrounding live cells ="+live_cells);
                }

                break;
            case 3:
                if(row==0&&column==0){
                    //left top
                    for(int i=0;i<2;i++){
                        for(int j=0;j<2;j++){
                            if(i==row&&j==column)
                                continue;
                            if(Grid[i][j]){
                                live_cells++;
                            }
                        }
                    }
                }
                else if(row==0&&column==columns-1){
                    //right top
                    for(int i=0;i<2;i++)
                    {
                        for(int j=column-2;j<columns;j++){
                            if(i==row&&j==column)
                                continue;
                            if(Grid[i][j]){
                                live_cells++;
                            }
                        }
                    }

                }
                else if(row==rows-1&&column==columns-1){
                    //right bottom
                    for(int i=row-2;i<rows;i++){
                        for(int j=column-2;j<columns;j++){
                            if(i==row&&j==column)
                                continue;
                            if(Grid[i][j]){
                                live_cells++;
                            }
                        }
                    }

                }
                else if(row==rows-1&&column==0){
                    //left bottom
                    for(int i=row-2;i<rows;i++){
                        for(int j=0;j<2;j++){
                            if(i==row&&j==column)
                                continue;
                            if(Grid[i][j]){
                                live_cells++;
                            }
                        }
                    }


                }
                //System.out.println("Corner Cell - Surrounding live cells ="+live_cells);

                break;
        }

        return live_cells;
    }



    public ArrayList<Integer> liveCell_Indexes() {
        System.out.println("Reporting from grid_layout livecellindexes");
        SavingInstances saved=SavingInstances.getInstance();
        Grid_Copy=saved.returnLiveGrid();
        ArrayList<Integer> live_Cells_Indexes=new ArrayList<>();

        for(int i=0;i<rows;i++){
            for(int j=0;j<columns;j++){
                if(Grid_Copy[i][j]){
                    System.out.println("live "+i+"&"+j);
                    live_Cells_Indexes.add(i);
                    live_Cells_Indexes.add(j);
                }
            }
        }
        return live_Cells_Indexes;

}
    //Orientation change restoring
    public void onStateRestore(int arr[]){
        SavingInstances saved=SavingInstances.getInstance();
        Grid_Copy=saved.returnLiveGrid();
        orientation_changed=true;
        System.out.println("Reporting from onStateRestore"+Arrays.toString(arr));


        printGrid(Grid_Copy);
        for(int i=0;i<arr.length-1;i=i+2){
            System.out.println("for"+i+"&"+i+1);
            Grid_Copy[arr[i]][arr[i+1]]=true;
        }
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@calling invalidate");
        //printGrid();
        invalidate();


    }
    public boolean[][] returnCurrentGrid(){
        return Grid;
    }



}
