package bananatechnologies.mortgagecalculator;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.maps.GoogleMap;

import java.util.ArrayList;
import java.util.List;


import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Sagar Mane
 * @version 1.0
 */

public class CalculationViewFragment extends android.support.v4.app.Fragment implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "Sagar_Logs";

    String loan_amount_text;
    String down_payment_text;
    String apr_text;

    PieChart chart;
    float values[]={100,100000};
    String names[]={"Principle","Interest"};

    private String title;
    private int page;

    Spinner prop_type;
    Spinner terms_of_loan;
    Spinner state;

    String type=null;
    String state_name=null;
    String terms=null;
    String principle="150";
    String interest1="21";

    static EditText address;
    EditText city;
    EditText zipcode;

    TextView loan_amount;
    TextView down_payment;
    TextView apr;
    TextView property_info_label;

    TextView prop_type_label;
    TextView address_label;
    TextView city_label;
    TextView state_label;
    TextView zipcode_label;

    TextView calculation_result;
    RelativeLayout more_options_layout;

    double Loan_amount;
    double Apr;
    double Down_payment;
    double No_of_years;
    double monthly_payment;

    boolean visibility_flag=false;

    Button save;
    Button new_calculation;

    Button more_options;
    Button less_options;

    static SeekBar loan_amount1;
    static SeekBar down_payment1;
    static SeekBar apr1;

    public static CalculationViewFragment newInstance(int page, String title) {

        CalculationViewFragment fragmentFirst = new CalculationViewFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
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
        final View rootview=inflater.inflate(R.layout.calculation_view_fragment,container,false);

        //Property type spinner
        prop_type=(Spinner) rootview.findViewById(R.id.property_type);
        state=(Spinner) rootview.findViewById(R.id.state);

        terms_of_loan=(Spinner)rootview.findViewById(R.id.Terms_of_loan);

        address=(EditText) rootview.findViewById(R.id.address);
        city=(EditText)rootview.findViewById(R.id.city);

        zipcode=(EditText)rootview.findViewById(R.id.zipcode);
        loan_amount=(TextView)rootview.findViewById(R.id.loan_amount);
        down_payment=(TextView)rootview.findViewById(R.id.down_payment);
        apr=(TextView)rootview.findViewById(R.id.apr);
        chart=(PieChart) rootview.findViewById(R.id.piechart);
        loan_amount1=(SeekBar)rootview.findViewById(R.id.loan_amount1);
        down_payment1=(SeekBar)rootview.findViewById(R.id.down_payment1);
        apr1=(SeekBar)rootview.findViewById(R.id.apr1);



       // property_info_label=(TextView) rootview.findViewById(R.id.property);
        prop_type_label=(TextView) rootview.findViewById(R.id.type_label);
        address_label=(TextView) rootview.findViewById(R.id.address_label);
        city_label=(TextView) rootview.findViewById(R.id.city_label);
        state_label=(TextView) rootview.findViewById(R.id.state_label);
        zipcode_label=(TextView) rootview.findViewById(R.id.zipcode_label);
        more_options_layout=(RelativeLayout) rootview.findViewById(R.id.more_options_layout);

        //setting invisible ui on creating fragment-------------------------------------------------
        prop_type_label.setVisibility(View.GONE);
        address_label.setVisibility(View.GONE);
        city_label.setVisibility(View.GONE);
        state_label.setVisibility(View.GONE);
        zipcode_label.setVisibility(View.GONE);
        prop_type.setVisibility(View.GONE);
        address.setVisibility(View.GONE);
        city.setVisibility(View.GONE);
        state.setVisibility(View.GONE);
        zipcode.setVisibility(View.GONE);

        more_options_layout.setVisibility(View.INVISIBLE);
        //------------------------------------------------------------------------------------------

        loan_amount1.setProgress(1000000);
        down_payment1.setProgress(190000);
        apr1.setProgress(4);
        loan_amount_text=Integer.toString(loan_amount1.getProgress());
        loan_amount.setText("$"+loan_amount_text);
        down_payment_text=Integer.toString(down_payment1.getProgress());
        down_payment.setText("$"+down_payment_text);
        apr_text=Integer.toString(apr1.getProgress());
        apr.setText(apr_text+"%");
        principle="1000000";
        values[0]=Float.parseFloat(principle);
        calculate_mortgage(1000000,4,"10");


        loan_amount1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress=0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                progress=progressValue;
                loan_amount_text=Integer.toString(loan_amount1.getProgress());
                loan_amount.setText("$"+loan_amount_text);
                calculate_mortgage(loan_amount1.getProgress(),apr1.getProgress(),terms);

                principle=loan_amount_text;
                chart.invalidate();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                loan_amount_text=Integer.toString(loan_amount1.getProgress());
                loan_amount.setText("$"+loan_amount_text);
                chart.invalidate();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        down_payment1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                down_payment_text=Integer.toString(down_payment1.getProgress());
                down_payment.setText("$"+down_payment_text);
                calculate_mortgage(loan_amount1.getProgress(),apr1.getProgress(),terms);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

                down_payment_text=Integer.toString(down_payment1.getProgress());
                down_payment.setText("$"+down_payment_text);
                chart.invalidate();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        apr1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                apr_text=Integer.toString(apr1.getProgress());
                apr.setText(apr_text+"%");
                calculate_mortgage(loan_amount1.getProgress(),apr1.getProgress(),terms);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                apr_text=Integer.toString(apr1.getProgress());
                apr.setText(apr_text+"%");
                chart.invalidate();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        set();
        setSpinners();


        try{
            Loan_amount=Double.parseDouble(loan_amount.getText().toString());
            Apr=Double.parseDouble(apr.getText().toString());
        }
        catch (Exception e){
            e.printStackTrace();
        }



        //all buttons

        save=(Button) rootview.findViewById(R.id.save_button);
       // reset=(Button) rootview.findViewById(R.id.reset_button);

        more_options=(Button) rootview.findViewById(R.id.more_options);
        less_options=(Button) rootview.findViewById(R.id.less_options);
        /*new_calculation=(Button) rootview.findViewById(R.id.new_button);

        new_calculation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"New calculation button clicked");
                loan_amount.setText("$"+0);
               // down_payment_text=Integer.toString(down_payment1.getProgress());
                down_payment.setText("$"+0);
                //apr_text=Integer.toString(apr1.getProgress());
                apr.setText(0+"%");
                loan_amount1.setProgress(0);
                down_payment1.setProgress(0);
                apr1.setProgress(0);

            }
        });
*/
        more_options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"Sagar check out this value"+visibility_flag);
                moreOptions();

            }
        });
        less_options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lessOptions();
            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(address.getText().toString().equals("")
                            || city.getText().toString().equals("")
                            || zipcode.getText().toString().equals("")
                            || loan_amount.getText().equals("")
                            || down_payment.getText().equals("")
                            || apr.getText().toString().equals("")
                            || apr.getText().equals(""))
                    {
                        Toast toast = Toast.makeText(getContext(), "Please input all fields", Toast.LENGTH_LONG);
                        toast.show();
                        return;
                    }
                    save_data(type,address.getText().toString(),city.getText().toString(),
                            loan_amount.getText().toString(),down_payment.getText().toString(),apr.getText().toString(),terms);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        return rootview;
    }

    public void calculate_mortgage(int loan_amount,int apr, String terms){
        Log.i(TAG,"Calculate pressed "+type);
        Log.i(TAG,"In calculate mortgage value of terms spinner"+terms);
        Loan_amount=1.0*loan_amount;
        Apr=1.0*apr;
        No_of_years = Double.parseDouble(terms);
        monthly_payment=calculateMortgage(Loan_amount,Apr,No_of_years);
        Log.i(TAG,"Result7777777777777777777777777777 = "+monthly_payment );
        set();
        chart.invalidate();

    }

    /**
     * Used to Save Property data. It also calculates latitude and longitude from the address string.
     * @param type  (Property type)
     * @param prop_address
     * @param city
     * @param loan_amount
     * @param apr
     * @throws JSONException
     */
    public void save_data(String type,String prop_address, String city, String loan_amount,String down_payment, String apr, String terms) throws JSONException, IOException {

        Geocoder coder=new Geocoder(getContext());
        List<Address> address;
        double lat=0,lng=0;
        try {
            address=coder.getFromLocationName(prop_address,5);
            if(address==null){
                Log.i(TAG,"Address is null");
            }
            Address location=address.get(0);
            lat=location.getLatitude();
            lng=location.getLongitude();
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
        Log.i(TAG,"Location found");
        Log.i(TAG,"Latitude= "+lat+"Longitude= "+lng);
        PropertyData newProperty=new PropertyData(type,prop_address,city,loan_amount,apr,lat,lng);
        try{
            Loan_amount=Double.parseDouble(loan_amount);
            Apr=Double.parseDouble(apr);
            Down_payment=Double.parseDouble(down_payment);
            No_of_years = Double.parseDouble(terms);

        }
        catch (Exception e){
            e.printStackTrace();
        }

        monthly_payment=calculateMortgage(Loan_amount,Apr,No_of_years);
        //Logs to check double values inserted.
        Log.i(TAG,"##################################double loan amount"+Loan_amount);
        Log.i(TAG,"##################################double apr"+Apr);
        Log.i(TAG,"##################################double down payment"+Down_payment);
        Log.i(TAG,"##################################double No of years"+No_of_years);
        Log.i(TAG,"##################################Monthly Payments"+monthly_payment);

        Log.i(TAG,"Save button pressed "+type +"And" +prop_address+"and " +city +" "+ loan_amount+ "and " +down_payment+ " and "+ apr+" and "+terms);


        //Insert Data into DataBase
        DBHelper db = new DBHelper(getActivity());
        db.onCreate();
       // String monthly_payment = "";
        String extra_field = "";
        db.insertMortgage(type,prop_address,city,Loan_amount,monthly_payment, extra_field,Apr,lat,lng);
        Toast toast = Toast.makeText(getContext(), "Your preferences saved", Toast.LENGTH_LONG);
        toast.show();
        ArrayList<PropertyData> temp = db.getAll();




    }
    public double calculateMortgage(double loan_amount,double rate,double terms){
        double monthly_payment=0;
        rate =(rate/100)/12;
        System.out.println(rate);
        double no_of_payments=12*terms;

        double temp=1+rate;
        double temp2=Math.pow(temp, no_of_payments);
        System.out.println(temp);
        System.out.println(temp2);

        monthly_payment=loan_amount*((temp2*rate)/(temp2-1));
        monthly_payment=Math.round(monthly_payment);
        System.out.println(monthly_payment);
        calculateInterest();
        return monthly_payment;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Spinner spinner = (Spinner) parent;

        if(spinner.getId() == R.id.property_type)
        {
            type=parent.getItemAtPosition(position).toString();
            Log.i(TAG,"Property Type"+parent.getItemAtPosition(position).toString());
        }
        else if(spinner.getId() == R.id.state)
        {
            state_name=parent.getItemAtPosition(position).toString();
            Log.i(TAG,"State selected"+parent.getItemAtPosition(position).toString());
        }
        else if(spinner.getId()==R.id.Terms_of_loan){

            terms=parent.getItemAtPosition(position).toString();
            Log.i(TAG,"terms selected"+parent.getItemAtPosition(position).toString());
            calculate_mortgage(loan_amount1.getProgress(),apr1.getProgress(),terms);
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * Function for Initializing Spinner inputs
     */
    public void setSpinners(){

        List<String> type = new ArrayList<String>();
        type.add("Townhouse");
        type.add("Apartment");
        type.add("Condo");

        ArrayAdapter<String> dataAdapter=new ArrayAdapter<String>(getActivity(),R.layout.support_simple_spinner_dropdown_item,type);
        prop_type.setAdapter(dataAdapter);
        prop_type.setOnItemSelectedListener(this);

        List<String> states=new ArrayList<>();
        states.add("Arizona");
        states.add("California");
        states.add("New York");
        states.add("New Jersey");
        states.add("Maharashtra");
        states.add("Washington");

        ArrayAdapter<String> state_adapter=new ArrayAdapter<String>(getActivity(),R.layout.support_simple_spinner_dropdown_item,states);
        state.setAdapter(state_adapter);
        state.setOnItemSelectedListener(this);


        List<String> list = new ArrayList<String>();
        list.add("10");
        list.add("15");
        list.add("30");

        ArrayAdapter<String> terms_adapter = new ArrayAdapter<String>(getActivity(),R.layout.support_simple_spinner_dropdown_item,list);
        terms_of_loan.setAdapter(terms_adapter);
        terms_of_loan.setOnItemSelectedListener(this);



    }

    /**
     * Function for Initializing Pie Chart
     */
    public void set() {

        List<PieEntry> entries=new ArrayList<>();

        values[0]=Float.parseFloat(principle);
        for(int i=0;i<values.length;i++){
            entries.add(new PieEntry(values[i],names[i]));

        }

        PieDataSet dataset=new PieDataSet(entries,"");
        dataset.setColors(ColorTemplate.JOYFUL_COLORS);
        PieData data=new PieData(dataset);
        chart.setHoleColor(Color.TRANSPARENT);

        chart.setData(data);
        chart.setCenterText("$"+monthly_payment);
        Log.i(TAG,"chart inside "+monthly_payment);
        chart.invalidate();
    }

    /**
     * Function for moreOptions Visibility
     */
    public void moreOptions(){

        prop_type_label.setVisibility(View.VISIBLE);
      //  property_info_label.setVisibility(View.VISIBLE);
        address_label.setVisibility(View.VISIBLE);
        city_label.setVisibility(View.VISIBLE);
        state_label.setVisibility(View.VISIBLE);
        zipcode_label.setVisibility(View.VISIBLE);
        prop_type.setVisibility(View.VISIBLE);
        address.setVisibility(View.VISIBLE);
        city.setVisibility(View.VISIBLE);
        state.setVisibility(View.VISIBLE);
        zipcode.setVisibility(View.VISIBLE);
        less_options.setVisibility(View.VISIBLE);
        save.setVisibility(View.VISIBLE);
        more_options_layout.setVisibility(View.VISIBLE);
        more_options.setVisibility(View.INVISIBLE);
    }

    /**
     * Function for lessOptions Visibility
     */
    public void lessOptions(){

        prop_type_label.setVisibility(View.GONE);
       // property_info_label.setVisibility(View.GONE);
        address_label.setVisibility(View.GONE);
        city_label.setVisibility(View.GONE);
        state_label.setVisibility(View.GONE);
        zipcode_label.setVisibility(View.GONE);
        prop_type.setVisibility(View.GONE);
        address.setVisibility(View.GONE);
        city.setVisibility(View.GONE);
        state.setVisibility(View.GONE);
        zipcode.setVisibility(View.INVISIBLE);
        less_options.setVisibility(View.GONE);
        save.setVisibility(View.GONE);
        more_options_layout.setVisibility(View.GONE);
        more_options.setVisibility(View.VISIBLE);
    }

    /**
     * Function for Calculating Interest
     */
    public void calculateInterest(){
        double interest=(No_of_years*Loan_amount*Apr)/100;
        Log.i(TAG,"Interest="+interest);
        interest1=Double.toString(interest);
        values[1]=Float.parseFloat(interest1);
    }


    public static void on_edit_call(){
        if(loan_amount1!=null){
            loan_amount1.setProgress(500000);
            down_payment1.setProgress(500000);
            apr1.setProgress(10);
            address.setText("101 E San Fernando");
            Log.i(TAG,"Values are not null yo yoooooooooooooooooooooooooooooooooooooo");

        }
                Log.i(TAG,"in on edit call yo"+loan_amount1);

    }
}
