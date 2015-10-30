package com.example.xyb5b.cwd;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by xyb5b on 10/20/2015.
 */
public class UploadingAsyncTask extends AsyncTask<Void,Void,Void> {

    private ProgressDialog pd;
    private Context mContext;
    private JSONArray ja=new JSONArray();
    private JSONObject output;
    private CWDDbHelper mDbHelper;
    private SQLiteDatabase mDb;

    public UploadingAsyncTask(Context appContext){
        mContext=appContext;
    }

    public String ModifyString(String myString){
        return myString==null?"":myString;

    }

    @Override
    protected void onPreExecute() {
        pd=ProgressDialog.show(mContext, "Please wait...", "Uploading is in process...", true);
    }

    @Override
    protected Void doInBackground(Void... params) {
        mDbHelper=new CWDDbHelper(mContext);
        mDb=mDbHelper.getReadableDatabase();
        Cursor cursor = mDb.rawQuery("select * from "+CWDDbHelper.table_name,null);
        while (cursor.moveToNext()) {
            JSONObject jo=new JSONObject();
            try {
                jo.put(CWDDbHelper.DataSheetUID,ModifyString(cursor.getString(cursor.getColumnIndexOrThrow(CWDDbHelper.DataSheetUID))));
                jo.put(CWDDbHelper.CaseUID,ModifyString(cursor.getString(cursor.getColumnIndexOrThrow(CWDDbHelper.CaseUID))));
                jo.put(CWDDbHelper.collection_date,ModifyString(cursor.getString(cursor.getColumnIndexOrThrow(CWDDbHelper.collection_date))));
                jo.put(CWDDbHelper.cwd_sampleNum,cursor.getInt(cursor.getColumnIndexOrThrow(CWDDbHelper.cwd_sampleNum)));
                jo.put(CWDDbHelper.collection_method,ModifyString(cursor.getString(cursor.getColumnIndexOrThrow(CWDDbHelper.collection_method))));
                jo.put(CWDDbHelper.harvest_date,ModifyString(cursor.getString(cursor.getColumnIndexOrThrow(CWDDbHelper.harvest_date))));
                jo.put(CWDDbHelper.deer_sex,ModifyString(cursor.getString(cursor.getColumnIndexOrThrow(CWDDbHelper.deer_sex))));
                jo.put(CWDDbHelper.deer_age,ModifyString(cursor.getString(cursor.getColumnIndexOrThrow(CWDDbHelper.deer_age))));
                jo.put(CWDDbHelper.tissue_collected_lymphNodes,cursor.getInt(cursor.getColumnIndexOrThrow(CWDDbHelper.tissue_collected_lymphNodes)));
                jo.put(CWDDbHelper.tissue_collected_other,ModifyString(cursor.getString(cursor.getColumnIndexOrThrow(CWDDbHelper.tissue_collected_other))));
                jo.put(CWDDbHelper.collector_firstName,ModifyString(cursor.getString(cursor.getColumnIndexOrThrow(CWDDbHelper.collector_firstName))));
                jo.put(CWDDbHelper.collector_lastName,ModifyString(cursor.getString(cursor.getColumnIndexOrThrow(CWDDbHelper.collector_lastName))));
                jo.put(CWDDbHelper.collector_MDCemployee,cursor.getInt(cursor.getColumnIndexOrThrow(CWDDbHelper.collector_MDCemployee)));
                jo.put(CWDDbHelper.collection_location,ModifyString(cursor.getString(cursor.getColumnIndexOrThrow(CWDDbHelper.collection_location))));
                jo.put(CWDDbHelper.harvest_county,ModifyString(cursor.getString(cursor.getColumnIndexOrThrow(CWDDbHelper.harvest_county))));
                jo.put(CWDDbHelper.harvest_township,ModifyString(cursor.getString(cursor.getColumnIndexOrThrow(CWDDbHelper.harvest_township))));
                jo.put(CWDDbHelper.harvest_range,ModifyString(cursor.getString(cursor.getColumnIndexOrThrow(CWDDbHelper.harvest_range))));
                jo.put(CWDDbHelper.harvest_section,ModifyString(cursor.getString(cursor.getColumnIndexOrThrow(CWDDbHelper.harvest_section))));
                jo.put(CWDDbHelper.harvest_latitude,cursor.getDouble(cursor.getColumnIndexOrThrow(CWDDbHelper.harvest_latitude)));
                jo.put(CWDDbHelper.harvest_longitude,cursor.getDouble(cursor.getColumnIndexOrThrow(CWDDbHelper.harvest_longitude)));
                jo.put(CWDDbHelper.accuracy,ModifyString(cursor.getString(cursor.getColumnIndexOrThrow(CWDDbHelper.accuracy))));
                jo.put(CWDDbHelper.hunter_firstName,ModifyString(cursor.getString(cursor.getColumnIndexOrThrow(CWDDbHelper.hunter_firstName))));
                jo.put(CWDDbHelper.hunter_lastName,ModifyString(cursor.getString(cursor.getColumnIndexOrThrow(CWDDbHelper.hunter_lastName))));
                jo.put(CWDDbHelper.hunter_contact,ModifyString(cursor.getString(cursor.getColumnIndexOrThrow(CWDDbHelper.hunter_contact))));
                if(String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(CWDDbHelper.hunter_conservationID))).equals("0")){
                    jo.put(CWDDbHelper.hunter_conservationID,JSONObject.NULL);
                }else {
                    jo.put(CWDDbHelper.hunter_conservationID,cursor.getInt(cursor.getColumnIndexOrThrow(CWDDbHelper.hunter_conservationID)));
                }
                jo.put(CWDDbHelper.hunter_TelecheckNum,ModifyString(cursor.getString(cursor.getColumnIndexOrThrow(CWDDbHelper.hunter_TelecheckNum))));
                jo.put(CWDDbHelper.cwd_mgmtSeal,ModifyString(cursor.getString(cursor.getColumnIndexOrThrow(CWDDbHelper.cwd_mgmtSeal))));
                jo.put(CWDDbHelper.cwd_mgmtSealNum,ModifyString(cursor.getString(cursor.getColumnIndexOrThrow(CWDDbHelper.cwd_mgmtSealNum))));
                jo.put(CWDDbHelper.cull_EarTagColor,ModifyString(cursor.getString(cursor.getColumnIndexOrThrow(CWDDbHelper.cull_EarTagColor))));
                jo.put(CWDDbHelper.cull_EarTagNum,ModifyString(cursor.getString(cursor.getColumnIndexOrThrow(CWDDbHelper.cull_EarTagNum))));
                jo.put(CWDDbHelper.cull_ParcelID,ModifyString(cursor.getString(cursor.getColumnIndexOrThrow(CWDDbHelper.cull_ParcelID))));
                if(String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(CWDDbHelper.cull_incisors))).equals("0")){
                    jo.put(CWDDbHelper.cull_incisors,JSONObject.NULL);
                }else {
                    jo.put(CWDDbHelper.cull_incisors,cursor.getInt(cursor.getColumnIndexOrThrow(CWDDbHelper.cull_incisors)));
                }
                jo.put(CWDDbHelper.cull_incisors_envelopeNum,ModifyString(cursor.getString(cursor.getColumnIndexOrThrow(CWDDbHelper.cull_incisors_envelopeNum))));
                if(String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(CWDDbHelper.cull_tongue))).equals("0")){
                    jo.put(CWDDbHelper.cull_tongue,JSONObject.NULL);
                }else {
                    jo.put(CWDDbHelper.cull_tongue,cursor.getInt(cursor.getColumnIndexOrThrow(CWDDbHelper.cull_tongue)));
                }
                jo.put(CWDDbHelper.cull_propertyOwner_firstName,ModifyString(cursor.getString(cursor.getColumnIndexOrThrow(CWDDbHelper.cull_propertyOwner_firstName))));
                jo.put(CWDDbHelper.cull_propertyOwner_lastName,ModifyString(cursor.getString(cursor.getColumnIndexOrThrow(CWDDbHelper.cull_propertyOwner_lastName))));
                jo.put(CWDDbHelper.cull_shooter_firstName,ModifyString(cursor.getString(cursor.getColumnIndexOrThrow(CWDDbHelper.cull_shooter_firstName))));
                jo.put(CWDDbHelper.cull_shooter_lastName, ModifyString(cursor.getString(cursor.getColumnIndexOrThrow(CWDDbHelper.cull_shooter_lastName))));
                jo.put(CWDDbHelper.cull_shooter_contact,ModifyString(cursor.getString(cursor.getColumnIndexOrThrow(CWDDbHelper.cull_shooter_contact))));
                if(String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(CWDDbHelper.cull_shooter_MDCemployee))).equals("0")){
                    jo.put(CWDDbHelper.cull_shooter_MDCemployee,JSONObject.NULL);
                }else {
                    jo.put(CWDDbHelper.cull_shooter_MDCemployee,cursor.getInt(cursor.getColumnIndexOrThrow(CWDDbHelper.cull_shooter_MDCemployee)));
                }
                jo.put(CWDDbHelper.comments,ModifyString(cursor.getString(cursor.getColumnIndexOrThrow(CWDDbHelper.comments))));
                if(String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(CWDDbHelper.uploaded))).equals("0")){
                    jo.put(CWDDbHelper.uploaded,JSONObject.NULL);
                }else {
                    jo.put(CWDDbHelper.uploaded,cursor.getInt(cursor.getColumnIndexOrThrow(CWDDbHelper.uploaded)));
                }
                jo.put(CWDDbHelper.createTS,ModifyString(cursor.getString(cursor.getColumnIndexOrThrow(CWDDbHelper.createTS))));
                jo.put(CWDDbHelper.updateTS,ModifyString(cursor.getString(cursor.getColumnIndexOrThrow(CWDDbHelper.updateTS))));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ja.put(jo);

        }


       /* cursor.moveToNext();
        JSONObject jo=new JSONObject();
        try {
            jo.put(CWDDbHelper.cull_EarTagColor, ModifyString(cursor.getString(cursor.getColumnIndexOrThrow(CWDDbHelper.cull_EarTagColor))));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ja.put(jo);*/

        output = new JSONObject();
        try {
            output.put("cwdSurveillance_Recs", ja);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String url="http://mdc7test.mdc.mo.gov/webservices/cwdsurveillanceproxywebapi/api/CWDSurveillance/GetCWDSurveillanceData";
        //String url="http://dslsrv8.cs.missouri.edu/~xyb5b/Phptest/WriteSome.php";
        HttpURLConnection connection=null;
        try{
            connection=(HttpURLConnection)new URL(url).openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            //connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            //connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //connection.connect();
            BufferedWriter printout=new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(),"UTF-8"));
            printout.write(output.toString());
            //Log.d("HAHA",output.toString());
            //DataOutputStream printout=new DataOutputStream(connection.getOutputStream());
            //printout.writeBytes(URLEncoder.encode(params[0],"UTF-8"));
            printout.flush();
            printout.close();
            connection.getResponseCode();
        }catch (Exception e){
            e.printStackTrace();
        }

        mDb.close();

        return null;
    }


    @Override
    protected void onPostExecute(Void aVoid) {
        pd.dismiss();
    }
}
