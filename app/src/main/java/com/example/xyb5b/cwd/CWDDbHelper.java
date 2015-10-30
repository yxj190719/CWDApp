package com.example.xyb5b.cwd;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by xyb5b on 9/8/2015.
 */
public class CWDDbHelper extends SQLiteOpenHelper{
    static final int dbVersion=1;
    static final String dbName = "/sdcard/MDC/CWD.db";
    static final String table_name = "CWD_Sheet";
    static final String DataSheetUID = "DataSheetUID";
    static final String CaseUID = "CaseUID";
    static final String collection_date = "collection_date";   //
    static final String cwd_sampleNum = "cwd_sampleNum";       //
    static final String collection_method = "collection_method";
    static final String harvest_date = "harvest_date"; //
    static final String deer_sex = "deer_sex"; //
    static final String deer_age = "deer_age"; //
    static final String tissue_collected_lymphNodes = "tissue_collected_lymphNodes"; //rplns
    static final String tissue_collected_other = "tissue_collected_other"; //
    static final String collector_firstName = "collector_firstName";//
    static final String collector_lastName = "collector_lastName";//
    static final String collector_MDCemployee = "collector_MDCemployee";
    static final String collection_location = "collection_location"; //
    static final String harvest_county = "harvest_county"; //
    static final String harvest_township = "harvest_township";//
    static final String harvest_range = "harvest_range";//
    static final String harvest_section = "harvest_section";//
    static final String harvest_latitude = "harvest_latitude"; //
    static final String harvest_longitude = "harvest_longitude"; //
    static final String accuracy = "accuracy"; //
    static final String hunter_firstName = "hunter_firstName";//
    static final String hunter_lastName = "hunter_lastName";//
    static final String hunter_contact = "hunter_contact";//
    static final String hunter_conservationID = "hunter_conservationID";//
    static final String hunter_TelecheckNum = "hunter_TelecheckNum";//
    static final String cwd_mgmtSeal = "cwd_mgmtSeal"; //
    static final String cwd_mgmtSealNum = "cwd_mgmtSealNum"; //
    static final String cull_EarTagColor = "cull_EarTagColor";//
    static final String cull_EarTagNum = "cull_EarTagNum";//
    static final String cull_ParcelID = "cull_ParcelID"; //
    static final String cull_incisors = "cull_incisors"; //
    static final String cull_incisors_envelopeNum = "cull_incisors_envelopeNum"; //
    static final String cull_tongue = "cull_tongue"; //
    static final String cull_propertyOwner_firstName = "cull_propertyOwner_firstName";//
    static final String cull_propertyOwner_lastName = "cull_propertyOwner_lastName"; //
    static final String cull_shooter_firstName = "cull_shooter_firstName";//
    static final String cull_shooter_lastName = "cull_shooter_lastName";//
    static final String cull_shooter_contact = "cull_shooter_contact";//
    static final String cull_shooter_MDCemployee = "cull_shooter_MDCemployee"; //
    static final String comments = "comments"; //
    static final String uploaded = "uploaded";
    static final String createTS="createTS";
    static final String updateTS="updateTS";

    private static final String SQL_CREATE_ENTRIES="CREATE TABLE IF NOT EXISTS "
            + table_name
            + "("
            + DataSheetUID
            + " TEXT PRIMARY KEY,"
            + CaseUID
            + " TEXT,"
            + collection_date
            + " TEXT,"
            + cwd_sampleNum
            + " INTEGER,"
            + collection_method
            + " TEXT,"
            + harvest_date
            + " TEXT,"
            + deer_sex
            + " TEXT,"
            + deer_age
            + " TEXT,"
            + tissue_collected_lymphNodes
            + " INTEGER,"
            + tissue_collected_other
            + " TEXT,"
            + collector_firstName
            + " TEXT,"
            + collector_lastName
            + " TEXT,"
            + collector_MDCemployee
            + " INTEGER,"
            + collection_location
            + " TEXT,"
            + harvest_county
            + " TEXT,"
            + harvest_township
            + " TEXT,"
            + harvest_range
            + " TEXT,"
            + harvest_section
            + " TEXT,"
            + harvest_latitude
            + " REAL,"
            + harvest_longitude
            + " REAL,"
            + accuracy
            + " TEXT,"
            + hunter_firstName
            + " TEXT,"
            + hunter_lastName
            + " TEXT,"
            + hunter_contact
            + " TEXT,"
            + hunter_conservationID
            + " INTEGER,"
            + hunter_TelecheckNum
            + " TEXT,"
            + cwd_mgmtSeal
            + " TEXT,"
            + cwd_mgmtSealNum
            + " TEXT,"
            + cull_EarTagColor
            + " TEXT,"
            + cull_EarTagNum
            + " TEXT,"
            + cull_ParcelID
            + " TEXT,"
            + cull_incisors
            + " INTEGER,"
            + cull_incisors_envelopeNum
            + " TEXT,"
            + cull_tongue
            + " INTEGER,"
            + cull_propertyOwner_firstName
            + " TEXT,"
            + cull_propertyOwner_lastName
            + " TEXT,"
            + cull_shooter_firstName
            + " TEXT,"
            + cull_shooter_lastName
            + " TEXT,"
            + cull_shooter_contact
            + " TEXT,"
            + cull_shooter_MDCemployee
            + " INTEGER,"
            + comments
            + " TEXT,"
            + uploaded
            + " INTEGER,"  //0 for false and 1 for true
            + createTS
            + " TEXT,"
            + updateTS
            + " TEXT)";

    private static final String SQL_CREATE_PLSS_TABLE="CREATE TABLE IF NOT EXISTS PLSS ( CNTY TEXT, TWP TEXT, RNG TEXT, SEC INTEGER, PRIMARY KEY (CNTY,TWP,RNG,SEC))";


    private Context AppContext;

    public CWDDbHelper(Context context) {
        super(context, dbName, null, dbVersion);
        AppContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
        db.execSQL(SQL_CREATE_PLSS_TABLE);
        InputStream ins=AppContext.getResources().openRawResource(R.raw.plss);
        BufferedReader br=new BufferedReader(new InputStreamReader(ins));
        int i=0;
        String row;
        try {
            while ((row=br.readLine())!=null){
                i++;
                String[] temp=row.split("\\t");
                ContentValues values=new ContentValues();
                values.put("CNTY",temp[0]);
                values.put("TWP",temp[1]);
                values.put("RNG",temp[2]);
                values.put("SEC", temp[3]);
                db.insert("PLSS",null,values);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
