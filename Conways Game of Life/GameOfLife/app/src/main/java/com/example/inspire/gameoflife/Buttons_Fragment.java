package com.example.inspire.gameoflife;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Sagar Mane on 23-02-2017.
 */

public class Buttons_Fragment extends Fragment{

    Button next;
    Button reset;

    //For interaction between grid fragment and buttons fragment.
    public interface buttonClickListeners{
        public void nextClicked();
        public void resetClicked();
    }
    buttonClickListeners command;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            command = (buttonClickListeners) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + e);
        }
    }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.buttons_fragment,container, false);

            next=(Button) v.findViewById(R.id.next_button);
            reset=(Button) v.findViewById(R.id.reset_button);

            //Listener for next button
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    nextClicked();
                }
            });

            //Listener for reset button
            reset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder reset_alert=new AlertDialog.Builder(getActivity());
                    reset_alert.setMessage("Do you want to reset the grid?").setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    resetClicked();

                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert=reset_alert.create();
                    alert.setTitle("Reset");
                    alert.show();

                }
            });

        return v;
    }
    public void nextClicked(){
        //System.out.println("BBBBBBBBBBB## Reporting from buttons fragment java ****Next clicked***");
        command.nextClicked();
    }
    public void resetClicked(){
        //System.out.println("BBBBBBBBBBB## Reporting from buttons fragment java *****Reset clicked****");
        command.resetClicked();
    }

}
