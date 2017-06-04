package com.example.inspire.myapplication_fragments_tutorials;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class First_Fragment extends Fragment{
    private EditText input;
    private Button enter;

    public interface FragmentListeners{
        public void showResults(String input);
    }

    FragmentListeners command;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            command=(FragmentListeners)getActivity();
        }
        catch (ClassCastException e){
            throw new ClassCastException(context.toString() +e);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.first_fragment,container,false);
        input=(EditText) v.findViewById(R.id.input);
        enter=(Button) v.findViewById(R.id.enter);

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_results(v);
            }
        });
        return v;
    }
    public void show_results(View v)
    {
        command.showResults(input.getText().toString());
    }
}
