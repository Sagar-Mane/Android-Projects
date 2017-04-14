package bananatechnologies.mortgagecalculator;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;


public class MapsActivity extends SupportMapFragment implements OnMapReadyCallback {


    private static final String LOGTAG = "MapFragment";
    private static GoogleMap mMap;
    ArrayList<PropertyData> temp;

    TextView address;
    TextView city;
    TextView loan_amount;
    TextView apr;
    TextView payment;
    TextView type;
    Button delete;
    Marker tobedeleted;
    Button edit;

    double lat_current;
    double lng_current;
    int i;
    DBHelper db;
    public MapsActivity() {
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getMapAsync(this);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Log.v(LOGTAG, "map is ready");

        infoWindow();
        DBHelper db = new DBHelper(getActivity());
        db.onCreate();
        String monthly_payment = "";
        String extra_field = "";
        ArrayList<PropertyData> temp = db.getAll();
        System.out.println(temp);
        try {
            retrieveAndAddCities(temp, 0);
        } catch (IOException e) {
            System.out.println("Cannot retrive cities" + e);
            return;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void retrieveAndAddCities(ArrayList<PropertyData> temp, int i) throws IOException, JSONException {
        HttpURLConnection conn = null;
        if (i == 1) {
            getMapAsync(this);
        }
        //String json = null;
        final StringBuilder json = new StringBuilder();

        try {
            createMarkersFromJson(temp, i);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    void createMarkersFromJson(ArrayList<PropertyData> temp, int flag) throws JSONException {
        String dialog_box;
        System.out.println(temp);

        if (flag == 1) {
            getMapAsync(this);
        }
        Log.i(LOGTAG, "inside creating marker windows");
        db = new DBHelper(getActivity());
        db.onCreate();
        final String monthly_payment = "";
        String extra_field = "";
        temp = db.getAll();

        for (i = 0; i < temp.size(); i++) {
            try {
                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(temp.get(i).getLatitude(), temp.get(i).getLongitude())));
                Log.i(LOGTAG, "inside creating marker windows" + i);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    void RefreshMap() {

    }

    public void infoWindow() {

        Log.i(LOGTAG, "inside creating marker windows");
         db = new DBHelper(getActivity());
        db.onCreate();
        final String monthly_payment = "";
        String extra_field = "";
        temp = db.getAll();

        for (i = 0; i < temp.size(); i++) {
            try {
                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(temp.get(i).getLatitude(), temp.get(i).getLongitude())));
                Log.i(LOGTAG, "inside creating marker windows"+i);
            } catch (Exception e) {
                e.printStackTrace();
            }


            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    LatLng x=marker.getPosition();
                    lat_current=x.latitude;
                    lng_current=x.longitude;
                    tobedeleted=marker;

                    Log.i(LOGTAG,"%%%%%%%%%%%%%%%%%%%%%%hiahahahahahaha Marker clicked");
                    AlertDialog.Builder markerInfo=new AlertDialog.Builder(getActivity());

                    View markerView=getLayoutInflater(null).inflate(R.layout.marker_click_info_dialogue_box,null);
                    markerInfo.setView(markerView).setCancelable(false);
                    AlertDialog marker_info=markerInfo.create();
                    marker_info.show();
                    marker_info.setCanceledOnTouchOutside(true);



                    TextView prop_type=(TextView) markerView.findViewById(R.id.prop_type_dialogue);
                    TextView addr=(TextView) markerView.findViewById(R.id.address_dialogue);
                    TextView city=(TextView) markerView.findViewById(R.id.city_dialogue);
                    TextView loan=(TextView) markerView.findViewById(R.id.loan_amount_dialogue);
                    TextView apr=(TextView) markerView.findViewById(R.id.apr_dialogue);
                    TextView monthly_payment=(TextView) markerView.findViewById(R.id.monthly_payment_dialogue);


                    temp = db.getAll();
                    LatLng latlng=marker.getPosition();
                    double lat=latlng.latitude;
                    final double lng=latlng.longitude;
                    for(PropertyData d : temp) {
                        if (d.getLatitude() == lat && d.getLongitude() == lng) {
                            Log.i(LOGTAG, "inside for loop" + i);
                            prop_type.setText("Property Type: " + d.getType());
                            addr.setText("Address: " + d.getAddress());
                            city.setText("City: " + d.getCity());
                            loan.setText("Loan Amount: $" + d.getLoan_amount());
                            apr.setText("Apr: " + d.getApr()+"%");
                            monthly_payment.setText("Monthly Payment: $" + Double.toString(d.getMonthly_payment()));

                        }
                    }


                    Button delete=(Button) markerView.findViewById(R.id.Delete_dialogue);
                    Button edit=(Button) markerView.findViewById(R.id.Edit_dialogue);



                    delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                Log.i(LOGTAG,"Delete clicked inside marker info");
                                DBHelper db = new DBHelper(getActivity());
                                db.onCreate();


                                db.deleteRecord(lat_current,lng_current);
                                ArrayList<PropertyData> temp = db.getAll();
                            try {
                                retrieveAndAddCities(temp, 1);
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            tobedeleted.remove();
                            Toast toast = Toast.makeText(getContext(), "Property Deleted From DB", Toast.LENGTH_LONG);
                            toast.show();

                        }
                    });

                    edit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                Log.i(LOGTAG,"Edit clicked inside marker info");
                            Intent abc=new Intent(getActivity(), MainActivity.class);

                            abc.putExtra("loan_amount",10);
                            abc.putExtra("down_payment",10);
                            abc.putExtra("apr",2);

                            startActivity (abc);




                        }
                    });
                    return true;
                }
            });

           /* mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(Marker marker) {
                    return null;
                }


                @Override
                public View getInfoContents(Marker marker) {
                    temp = db.getAll();
                    View v = getLayoutInflater(null).inflate(R.layout.marker_window, null);
                    type = (TextView) v.findViewById(R.id.prop_type_marker);
                    address = (TextView) v.findViewById(R.id.address_marker);
                    city = (TextView) v.findViewById(R.id.city_marker);
                    loan_amount = (TextView) v.findViewById(R.id.loan_amount_marker);
                    apr = (TextView) v.findViewById(R.id.apr_marker);
                    payment = (TextView) v.findViewById(R.id.monthly_payment_marker);
                    delete=(Button) v.findViewById(R.id.delete);
                    edit=(Button) v.findViewById(R.id.Edit);


                    LatLng latlng=marker.getPosition();
                    double lat=latlng.latitude;
                    double lng=latlng.longitude;

                    for(PropertyData d : temp){
                        if(d.getLatitude() == lat && d.getLongitude() == lng)
                        {
                            Log.i(LOGTAG, "inside for loop" + i);
                            type.setText(d.getType());
                            address.setText("Address: "+d.getAddress());
                            city.setText("City: "+d.getCity());
                            loan_amount.setText("Loan Amount: "+d.getLoan_amount());
                            apr.setText("Apr: "+d.getApr());
                            payment.setText("Monthly Payment: "+Double.toString(d.getMonthly_payment()));

                        }
                        //something here
                    }

                    return v;
                }
            });*/
        }
    }
    public void delete_marker(){
        View v= LayoutInflater.from(getActivity()).inflate(R.layout.marker_click_info_dialogue_box,null);

    }
    public static void refresh(){

    }



}
