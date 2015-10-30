package com.example.xyb5b.cwd;

import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;


public class MainActivity extends ActionBarActivity implements CalendarFragment.OnFragmentInteractionListener{
    public final static String BUTTON_TAG="Button_Tag";
    public final static String Date="Date";
    public final static  Calendar c = Calendar.getInstance();
    private CWDDbHelper mDbHelper;
    private SQLiteDatabase mDb;


    class DatabaseInitialization extends AsyncTask<Void, Void, Void> {
        private ProgressDialog pd;
        @Override
        protected void onPreExecute() {
            pd=ProgressDialog.show(MainActivity.this,"Please wait...","Database is initializing...",true);
        }

        @Override
        protected Void doInBackground(Void... params) {
            mDbHelper=new CWDDbHelper(getApplication());
            mDb=mDbHelper.getWritableDatabase();
            mDb.close();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            pd.dismiss();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String todayDate = DateFormat.getDateInstance().format(c.getTime());
        TextView t=(TextView)findViewById(R.id.showDate);
        t.setText(todayDate);
        new DatabaseInitialization().execute();
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

    public void goToNextActivity(View view){
        TextView t=(TextView)findViewById(R.id.showDate);
        switch (view.getId()) {
            case R.id.hunter_harvested:
                Intent intent1 = new Intent(this, Hunter_Harvested_Activity.class);
                intent1.putExtra(BUTTON_TAG, "hunter_harvested");
                intent1.putExtra(Date,t.getText());
                startActivity(intent1);
                break;
            case R.id.road_kill:
                Intent intent2 = new Intent(this, Road_Kill_Activity.class);
                intent2.putExtra(BUTTON_TAG, "road_kill");
                intent2.putExtra(Date,t.getText());
                startActivity(intent2);
                break;
            case R.id.sick_deer:
                Intent intent3 = new Intent(this, Sick_Deer_Activity.class);
                intent3.putExtra(BUTTON_TAG, "sick_deer");
                intent3.putExtra(Date,t.getText());
                startActivity(intent3);
                break;
            case R.id.targeted_harvest:
                Intent intent4 = new Intent(this, Targeted_Harvest_Activity.class);
                intent4.putExtra(BUTTON_TAG, "targeted_harvest");
                intent4.putExtra(Date,t.getText());
                startActivity(intent4);
                break;
        }
    }

    public void showCalendar(View v){
        DialogFragment calendarDialog=new CalendarFragment();
        calendarDialog.show(getFragmentManager(),"datePicker");
    }

    @Override
    public void onCalendarTimeSet(int year, int month, int day) {
        c.set(year,month,day);
        TextView t=(TextView)findViewById(R.id.showDate);
        String PickedDate = DateFormat.getDateInstance().format(c.getTime());
        t.setText(PickedDate);
    }
}
