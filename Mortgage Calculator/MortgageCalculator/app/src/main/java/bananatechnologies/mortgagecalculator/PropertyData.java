package bananatechnologies.mortgagecalculator;

import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.database.sqlite.SQLiteQuery;
import android.util.Log;

import org.json.JSONException;

import static android.content.Context.MODE_PRIVATE;

/**
 * @author Sagar Mane.
 */

public class PropertyData {

    SQLiteDatabase myDB= null;
    String TableName = "myTable";
    String Data="";

    private String Type;
    private String Address;
    private String City;
    private String Loan_amount;
    private String Apr;
    private double Monthly_payment;
    private double Latitude;

    private double Longitude;

    private static final String TAG = "Property";

    public PropertyData(){}

    //parameterized constructor
    public PropertyData(String type,String address, String city, String loan_amount,
                        String apr, double latitude, double longitude){
        this.Type=type;
        this.Address=address;
        this.City=city;
        this.Loan_amount=loan_amount;
        this.Apr=apr;
        this.Monthly_payment=0;
        this.Latitude=latitude;
        this.Longitude=longitude;
    }

    public String getType() {
        return Type;
    }

    public String getAddress() {
        return Address;
    }

    public String getCity() {
        return City;
    }

    public String getLoan_amount() {
        return Loan_amount;
    }

    public String getApr() {
        return Apr;
    }

    public double getMonthly_payment() {
        return Monthly_payment;
    }

    public double getLatitude() {
        return Latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setType(String type) {
        Type = type;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public void setCity(String city) {
        City = city;
    }

    public void setLoan_amount(String loan_amount) {
        Loan_amount = loan_amount;
    }

    public void setApr(String apr) {
        Apr = apr;
    }

    public void setMonthly_payment(double monthly_payment) {
        Monthly_payment = monthly_payment;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }
    //call JsonCreator method toJSON to convert current prop object into json.
    public void toJSON() throws JSONException {
            Log.i(TAG,"INside properties");
            String json=JsonCreator.toJSON(this);
            //JsonCreator.printJSON(json);


    }
}
