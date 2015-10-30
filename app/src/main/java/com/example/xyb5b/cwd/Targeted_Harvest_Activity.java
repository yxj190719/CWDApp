package com.example.xyb5b.cwd;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;


public class Targeted_Harvest_Activity extends ActionBarActivity implements Deer_Collector_Fragment.OnFragmentInteractionListener, Deer_Location_Fragment.OnFragmentInteractionListener,Targeted_Harvest_Fragment.OnFragmentInteractionListener,MenuFragment2.OnFragmentInteractionListener {

    private MenuFragment2 mf;
    private Deer_Collector_Fragment dcf;
    private Deer_Location_Fragment dlf;
    Targeted_Harvest_Fragment thf;
    private long mExitTime;
    private Toast toast=null;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_sheet);
        String button_tag = getIntent().getStringExtra(MainActivity.BUTTON_TAG);
        if (findViewById(R.id.menu_container) != null && findViewById(R.id.sheet_container) != null) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            mf = new MenuFragment2();
            dcf = new Deer_Collector_Fragment();
            dlf = new Deer_Location_Fragment();
            //Hunter_Harvested_Fragment hhf = new Hunter_Harvested_Fragment();
            thf = new Targeted_Harvest_Fragment();
            db = new CWDDbHelper(getApplication()).getWritableDatabase();
            transaction.add(R.id.menu_container, mf, "mf");
            transaction.add(R.id.sheet_container, dcf, "dcf");
            transaction.add(R.id.sheet_container, dlf, "dlf");
            transaction.add(R.id.sheet_container, thf, "thf");
            transaction.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_data_sheet, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_uploading) {
            new UploadingAsyncTask(this).execute();
        }

        return super.onOptionsItemSelected(item);
    }

    public void onPreviousButtonClicked(int i) {
        FragmentManager fg = getFragmentManager();
        FragmentTransaction transaction = fg.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ListView lv=(ListView)mf.getView().findViewById(R.id.menuList);
        for (int j = 0; j < lv.getCount(); j++) {
            lv.getChildAt(j).setBackgroundResource(android.R.color.transparent);
        }
        switch (i) {
            case 1:
                lv.getChildAt(0).setBackgroundResource(android.R.color.darker_gray);
                this.onBackPressed();
                break;
            case 2:
                transaction.show(dcf);
                transaction.hide(dlf);
                transaction.commit();
                lv.getChildAt(i-2).setBackgroundResource(android.R.color.darker_gray);
                break;
            /*case 3:
                transaction.show(dlf);
                transaction.hide(hhf);
                transaction.commit();
                break;*/
            case 4:
                transaction.show(dlf);
                transaction.hide(thf);
                transaction.commit();
                lv.getChildAt(i-3).setBackgroundResource(android.R.color.darker_gray);
                break;
        }

    }

    public void onNextButtonClicked(int i) {
        FragmentManager fg = getFragmentManager();
        FragmentTransaction transaction = fg.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ListView lv=(ListView)mf.getView().findViewById(R.id.menuList);
        setMenuBackgroundTransparent(lv);
        switch (i) {
            case 1:
                transaction.show(dlf);
                transaction.hide(dcf);
                transaction.commit();
                lv.getChildAt(i).setBackgroundResource(android.R.color.darker_gray);
                break;
            case 2:
                transaction.show(thf);
                transaction.hide(dlf);
                transaction.commit();
                lv.getChildAt(i).setBackgroundResource(android.R.color.darker_gray);
                break;
            /*case 3:
                transaction.show(thf);
                transaction.hide(hhf);
                transaction.commit();
                break;*/
            case 4:
                lv.getChildAt(i-2).setBackgroundResource(android.R.color.darker_gray);

                if(System.currentTimeMillis()-mExitTime>3000){
                    showToast("Click again to save the current sheet", Toast.LENGTH_SHORT);
                    //Toast.makeText(getApplicationContext(), "Click again to save the current sheet", Toast.LENGTH_SHORT).show();
                    mExitTime=System.currentTimeMillis();
                }else{
                    //starting store the data into local sqlite database
                    ContentValues values=new ContentValues();
                    values.put(CWDDbHelper.DataSheetUID, UUID.randomUUID().toString());
                    values.put(CWDDbHelper.CaseUID, UUID.randomUUID().toString());
                    try {
                        values.put(CWDDbHelper.collection_date, new SimpleDateFormat("yyyy-MM-dd").format(DateFormat.getDateInstance().parse(getIntent().getStringExtra(MainActivity.Date))));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    try {
                        values.put(CWDDbHelper.cwd_sampleNum, Integer.parseInt(((TextView) this.findViewById(R.id.cwd_sampleNum1)).getText().toString()));
                    }catch (Exception e){
                        transaction.show(dcf);
                        transaction.hide(thf);
                        transaction.commit();
                        setMenuBackgroundTransparent(lv);
                        lv.getChildAt(0).setBackgroundResource(android.R.color.darker_gray);
                        EditText problemET=(EditText) dcf.getView().findViewById(R.id.cwd_sampleNum);
                        problemET.requestFocus();
                        problemET.setBackgroundColor(Color.parseColor("#FF0000"));
                        showToast("Wrong CWD SAMPLE #!!", Toast.LENGTH_LONG);
                        //Toast.makeText(Road_Kill_Activity.this, "Wrong CWD Sample #!!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    values.put(CWDDbHelper.collection_method, getIntent().getStringExtra(MainActivity.BUTTON_TAG));
                    if(((RadioButton)dcf.getView().findViewById(R.id.male)).isChecked())
                        values.put(CWDDbHelper.deer_sex,"M");
                    if(((RadioButton)dcf.getView().findViewById(R.id.female)).isChecked())
                        values.put(CWDDbHelper.deer_sex,"F");
                    if(((RadioButton)dcf.getView().findViewById(R.id.fawn)).isChecked())
                        values.put(CWDDbHelper.deer_age,"Fawn");
                    if(((RadioButton)dcf.getView().findViewById(R.id.yearling)).isChecked())
                        values.put(CWDDbHelper.deer_age,"Yearling");
                    if(((RadioButton)dcf.getView().findViewById(R.id.adult)).isChecked())
                        values.put(CWDDbHelper.deer_age,"Adult");
                    if(((CheckBox)dcf.getView().findViewById(R.id.tissue_collected_lymphNodes)).isChecked())
                        values.put(CWDDbHelper.tissue_collected_lymphNodes,1);
                    else{
                        values.put(CWDDbHelper.tissue_collected_lymphNodes,0);
                    }
                    if(((CheckBox)dcf.getView().findViewById(R.id.tissue_collected_other)).isChecked())
                        values.put(CWDDbHelper.tissue_collected_other,((EditText)dcf.getView().findViewById(R.id.other)).getText().toString());
                    values.put(CWDDbHelper.collector_firstName,((EditText)dcf.getView().findViewById(R.id.collector_firstName)).getText().toString());
                    values.put(CWDDbHelper.collector_lastName,((EditText)dcf.getView().findViewById(R.id.collector_lastName)).getText().toString());
                    if(((RadioButton)dcf.getView().findViewById(R.id.mdc_staff)).isChecked())
                        values.put(CWDDbHelper.collector_MDCemployee,1);
                    if(((RadioButton)dcf.getView().findViewById(R.id.non_mdc_staff)).isChecked())
                        values.put(CWDDbHelper.collector_MDCemployee,0);
                    values.put(CWDDbHelper.collection_location,((EditText)dcf.getView().findViewById(R.id.collection_location)).getText().toString());
                    try{
                        if (((AutoCompleteTextView)dlf.getView().findViewById(R.id.harvest_county)).getText().toString().equals("")){
                            throw new Exception();
                        }
                        values.put(CWDDbHelper.harvest_county,((AutoCompleteTextView)dlf.getView().findViewById(R.id.harvest_county)).getText().toString());
                    }catch (Exception e){
                        transaction.show(dlf);
                        transaction.hide(thf);
                        transaction.commit();
                        setMenuBackgroundTransparent(lv);
                        lv.getChildAt(1).setBackgroundResource(android.R.color.darker_gray);
                        AutoCompleteTextView problemACTV=(AutoCompleteTextView) dlf.getView().findViewById(R.id.harvest_county);
                        problemACTV.requestFocus();
                        problemACTV.setBackgroundColor(Color.parseColor("#FF0000"));
                        showToast("Forget to choose a county?", Toast.LENGTH_LONG);
                        return;
                    }

                    values.put(CWDDbHelper.harvest_township,((Spinner)dlf.getView().findViewById(R.id.harvest_township)).getSelectedItem().toString()+"N");
                    try{
                        if(!(((RadioButton)dlf.getView().findViewById(R.id.east)).isChecked()) && !(((RadioButton)dlf.getView().findViewById(R.id.west)).isChecked()) )
                            throw new Exception();
                        if(((RadioButton)dlf.getView().findViewById(R.id.east)).isChecked())
                            values.put(CWDDbHelper.harvest_range,((Spinner)dlf.getView().findViewById(R.id.harvest_range)).getSelectedItem().toString()+"E");
                        if(((RadioButton)dlf.getView().findViewById(R.id.west)).isChecked())
                            values.put(CWDDbHelper.harvest_range,((Spinner)dlf.getView().findViewById(R.id.harvest_range)).getSelectedItem().toString()+"W");
                    }catch (Exception e){
                        transaction.show(dlf);
                        transaction.hide(thf);
                        transaction.commit();
                        setMenuBackgroundTransparent(lv);
                        lv.getChildAt(1).setBackgroundResource(android.R.color.darker_gray);
                        ((RadioGroup) dlf.getView().findViewById(R.id.range_radioGroup)).requestFocus();
                        ((RadioGroup) dlf.getView().findViewById(R.id.range_radioGroup)).setBackgroundColor(Color.parseColor("#FF0000"));
                        showToast("Please choose E or W for range", Toast.LENGTH_LONG);
                        return;
                    }

                    values.put(CWDDbHelper.harvest_section,((Spinner)dlf.getView().findViewById(R.id.harvest_section)).getSelectedItem().toString());
                    try{
                        values.put(CWDDbHelper.harvest_latitude, Double.parseDouble(((EditText) dlf.getView().findViewById(R.id.harvest_latitude)).getText().toString()));
                        values.put(CWDDbHelper.harvest_longitude, Float.parseFloat(((EditText) dlf.getView().findViewById(R.id.harvest_longitude)).getText().toString()));
                    }catch (Exception e){
                        transaction.show(dlf);
                        transaction.hide(thf);
                        transaction.commit();
                        setMenuBackgroundTransparent(lv);
                        lv.getChildAt(1).setBackgroundResource(android.R.color.darker_gray);
                        dlf.getView().findViewById(R.id.LatLong_LinearLayout).requestFocus();
                        dlf.getView().findViewById(R.id.LatLong_LinearLayout).setBackgroundColor(Color.parseColor("#FF0000"));
                        showToast("Click the map or Type 0 for Lat and Long", Toast.LENGTH_LONG);
                        return;
                    }

                    if(((RadioButton)dlf.getView().findViewById(R.id.accurate)).isChecked())
                        values.put(CWDDbHelper.accuracy,"1");
                    if(((RadioButton)dlf.getView().findViewById(R.id.not_accurate)).isChecked())
                        values.put(CWDDbHelper.accuracy,"0");
                    values.put(CWDDbHelper.comments, ((EditText) dlf.getView().findViewById(R.id.comments)).getText().toString());
                    values.put(CWDDbHelper.createTS, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(Calendar.getInstance().getTime()));

                    //targeted_harvest special
                    values.put(CWDDbHelper.cull_EarTagColor,((TextView)thf.getView().findViewById(R.id.cull_EarTagColor)).getText().toString());
                    values.put(CWDDbHelper.cull_EarTagNum,((TextView)thf.getView().findViewById(R.id.cull_EarTagNum)).getText().toString());
                    values.put(CWDDbHelper.cull_ParcelID,((TextView)thf.getView().findViewById(R.id.cull_ParcelID)).getText().toString());
                    if(((CheckBox)thf.getView().findViewById(R.id.cull_incisors)).isChecked()) {
                        values.put(CWDDbHelper.cull_incisors, 1);
                        values.put(CWDDbHelper.cull_incisors_envelopeNum,((TextView)thf.getView().findViewById(R.id.cull_incisors_envelopeNum)).getText().toString());
                    }else{
                        values.put(CWDDbHelper.cull_incisors, 0);
                    }
                    if(((CheckBox)thf.getView().findViewById(R.id.cull_tongue)).isChecked())
                        values.put(CWDDbHelper.cull_tongue, 1);
                    else
                        values.put(CWDDbHelper.cull_tongue, 0);
                    values.put(CWDDbHelper.cull_propertyOwner_firstName,((TextView)thf.getView().findViewById(R.id.cull_propertyOwner_firstName)).getText().toString());
                    values.put(CWDDbHelper.cull_propertyOwner_lastName,((TextView)thf.getView().findViewById(R.id.cull_propertyOwner_lastName)).getText().toString());
                    values.put(CWDDbHelper.cull_shooter_firstName,((TextView)thf.getView().findViewById(R.id.cull_shooter_firstName)).getText().toString());
                    values.put(CWDDbHelper.cull_shooter_lastName,((TextView)thf.getView().findViewById(R.id.cull_shooter_lastName)).getText().toString());
                    values.put(CWDDbHelper.cull_shooter_contact,((TextView)thf.getView().findViewById(R.id.cull_shooter_contact)).getText().toString());
                    if(((RadioButton)thf.getView().findViewById(R.id.mdc_staff)).isChecked())
                        values.put(CWDDbHelper.cull_shooter_MDCemployee, 1);
                    if(((RadioButton)thf.getView().findViewById(R.id.private_citizen)).isChecked())
                        values.put(CWDDbHelper.cull_shooter_MDCemployee, 0);



                    db.insert(CWDDbHelper.table_name, null, values);
                    /*Cursor cr=db.rawQuery("SELECT harvest_latitude FROM CWD_SHEET WHERE cwd_sampleNum = ?", new String[]{"145"});
                    cr.moveToFirst();
                    Double temp=cr.getDouble(cr.getColumnIndexOrThrow("harvest_latitude"));
                    Log.d("Latitude",""+temp);*/


                    showToast(((TextView) this.findViewById(R.id.cwd_sampleNum1)).getText() + " has been saved!", Toast.LENGTH_LONG);
                    db.close();
                    Intent intent = new Intent(this, Targeted_Harvest_Activity.class);
                    intent.putExtra(MainActivity.BUTTON_TAG, getIntent().getStringExtra(MainActivity.BUTTON_TAG));
                    intent.putExtra(MainActivity.Date, getIntent().getStringExtra(MainActivity.Date));
                    startActivity(intent);
                }

                break;
        }

    }

    @Override
    public void onSampleNumChanged(boolean hasFocus) {
        if(!hasFocus){
            String cwd_sample_number=((TextView)dcf.getView().findViewById(R.id.cwd_sampleNum)).getText().toString();
            ((TextView)dlf.getView().findViewById(R.id.cwd_sampleNum1)).setText(cwd_sample_number);
            ((TextView)thf.getView().findViewById(R.id.cwd_sampleNum3)).setText(cwd_sample_number);
        }
    }

    @Override
    public void ChangeSheetContent(int position) {
        FragmentManager fg = getFragmentManager();
        FragmentTransaction transaction = fg.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        switch (position){
            case 0:
                transaction.show(dcf);
                transaction.hide(dlf);
                transaction.hide(thf);
                transaction.commit();
                break;
            case 1:
                transaction.show(dlf);
                transaction.hide(dcf);
                transaction.hide(thf);
                transaction.commit();
                break;
            case 2:
                transaction.show(thf);
                transaction.hide(dlf);
                transaction.hide(dcf);
                transaction.commit();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        showToast("Press the upper left arrow to go back", Toast.LENGTH_SHORT);
        //Toast.makeText(this, "Press the upper left arrow to go back", Toast.LENGTH_SHORT).show();
    }

    protected void showToast(String msg, int length) {
        if (toast == null) {
            toast = Toast.makeText(this, msg, length);
        } else {
            toast.setText(msg);
            toast.setDuration(length);
        }
        toast.show();
    }

    private void setMenuBackgroundTransparent(ListView lv){
        for (int j = 0; j < lv.getCount(); j++) {
            lv.getChildAt(j).setBackgroundResource(android.R.color.transparent);
        }
    }

}
