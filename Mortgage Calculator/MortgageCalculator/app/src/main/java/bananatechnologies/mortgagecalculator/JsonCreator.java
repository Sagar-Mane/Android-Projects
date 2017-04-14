package bananatechnologies.mortgagecalculator;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Sagar Mane.
 * version 1.0
 */

/**
 * Used for creating and handling JSON data of individual properties.
 */
public class JsonCreator {

    private static final String TAG = "JSON";
    //Method to convert PropertyData object into JSON

    /**
     * Method to convert property data into JSON
     * @param current_property
     * @return string representation of json
     */
    public static String toJSON(PropertyData current_property){
        Log.i(TAG,"INside writing json");
        try {

            JSONObject new_property=new JSONObject();
            new_property.put("Type",current_property.getType());
            new_property.put("Address",current_property.getAddress());
            new_property.put("City",current_property.getCity());
            new_property.put("Loan_amount",current_property.getLoan_amount());
            new_property.put("Apr",current_property.getApr());
            new_property.put("Monthly_payment",current_property.getMonthly_payment());
            new_property.put("Latitude",current_property.getLatitude());
            new_property.put("Longitude",current_property.getLongitude());

            return new_property.toString();
        }
        catch (JSONException ex){
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Fucntion to check json data .
     * @param json String
     * @throws JSONException
     */
    public static void printJSON(String json) throws JSONException {
        Log.i(TAG,"inside print json function json creator");
        JSONArray json_array=new JSONArray(json);
        JSONObject jsonObj = json_array.getJSONObject(0);
        Log.i(TAG,"Property Type "+jsonObj.getString("type"));
    }

}
