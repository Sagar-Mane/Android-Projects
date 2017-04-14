package bananatechnologies.mortgagecalculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Pranjal on 3/16/2017.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final String TAG = "db";
    public static final String DATABASE_NAME = "Properties.db";
    public static final String CONTACTS_TABLE_NAME = "mortgage";

    public DBHelper(Context context) {

        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
    }

    public void onCreate() {
        Log.i(TAG,"db on create");
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(
                "create table IF NOT EXISTS Property " +
                        "(id integer primary key, type text, address text, city text, loan_amt text, monthly_payment text, mortgage number, apr number, Lat double, Lng double)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS mortgage");
        onCreate(db);
    }

    public boolean insertMortgage (String type, String address, String city, double loan_amt, double monthly_payment, String extra, double apr, double Lat, double Long) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("type", type);
        contentValues.put("address", address);
        contentValues.put("city", city);
        contentValues.put("loan_amt", loan_amt);
        contentValues.put("monthly_payment",monthly_payment);
        contentValues.put("mortgage", extra);
        contentValues.put("apr", apr);
        contentValues.put("Lat", Lat);
        contentValues.put("Lng", Long);
        db.insert("Property", null, contentValues);

        return true;
    }


    public PropertyData getData(double lat,double lng) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM Property WHERE", null );

        PropertyData p = new PropertyData();
        p.setType(res.getString(1));
        p.setAddress(res.getString(2));
        p.setCity(res.getString(3));
        p.setLoan_amount(res.getString(4));
        p.setMonthly_payment(res.getDouble(5));
        p.setApr(res.getString(7));
        p.setLatitude(res.getDouble(8));
        p.setLongitude(res.getDouble(9));

        return p;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, "Property");
        return numRows;
    }


    public ArrayList<PropertyData> getAll() {
        ArrayList<PropertyData> array_list = new ArrayList<PropertyData>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from Property", null );
        res.moveToFirst();

        HashMap<String, String> map;

        while(res.isAfterLast() == false){
            map  = new HashMap<String, String>();
            PropertyData p = new PropertyData();
            p.setType(res.getString(1));
            p.setAddress(res.getString(2));
            p.setCity(res.getString(3));
            p.setLoan_amount(res.getString(4));
            p.setMonthly_payment(res.getDouble(5));
            p.setApr(res.getString(7));
            p.setLatitude(res.getDouble(8));
            p.setLongitude(res.getDouble(9));
            array_list.add(p);
            res.moveToNext();
        }
        return array_list;
    }
    public void deleteRecord(double lat, double lng) {

        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor res =  db.rawQuery( "DELETE from property where ", null );
        db.execSQL("DELETE from property where Lat = "+lat+" and Lng = "+lng);

    }
}
