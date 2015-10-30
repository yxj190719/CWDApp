package com.example.xyb5b.cwd;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Deer_Location_Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Deer_Location_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Deer_Location_Fragment extends Fragment implements View.OnClickListener, OnMapReadyCallback,GoogleMap.OnMapClickListener, AdapterView.OnItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private GoogleMap googleMap;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    private CWDDbHelper mDbHelper;
    private SQLiteDatabase mDb;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Deer_Location_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Deer_Location_Fragment newInstance(String param1, String param2) {
        Deer_Location_Fragment fragment = new Deer_Location_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Deer_Location_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.deer_location, container, false);
        Button previousButton = (Button) v.findViewById(R.id.previous_button);
        Button nextButton = (Button) v.findViewById(R.id.next_button);
        previousButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
        MapFragment mapFragment = (MapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        String[] counties=new String[]{"ADAIR",
                "ANDREW",
                "ATCHISON",
                "AUDRAIN",
                "BARRY",
                "BARTON",
                "BATES",
                "BENTON",
                "BOLLINGER",
                "BOONE",
                "BUCHANAN",
                "BUTLER",
                "CALDWELL",
                "CALLAWAY",
                "CAMDEN",
                "CAPE GIRARDEAU",
                "CARROLL",
                "CARTER",
                "CASS",
                "CEDAR",
                "CHARITON",
                "CHRISTIAN",
                "CLARK",
                "CLAY",
                "CLINTON",
                "COLE",
                "COOPER",
                "CRAWFORD",
                "DADE",
                "DALLAS",
                "DAVIESS",
                "DEKALB",
                "DENT",
                "DOUGLAS",
                "DUNKLIN",
                "FRANKLIN",
                "GASCONADE",
                "GENTRY",
                "GREENE",
                "GRUNDY",
                "HARRISON",
                "HENRY",
                "HICKORY",
                "HOLT",
                "HOWARD",
                "HOWELL",
                "IRON",
                "JACKSON",
                "JASPER",
                "JEFFERSON",
                "JOHNSON",
                "KNOX",
                "LACLEDE",
                "LAFAYETTE",
                "LAWRENCE",
                "LEWIS",
                "LINCOLN",
                "LINN",
                "LIVINGSTON",
                "MCDONALD",
                "MACON",
                "MADISON",
                "MARIES",
                "MARION",
                "MERCER",
                "MILLER",
                "MISSISSIPPI",
                "MONITEAU",
                "MONROE",
                "MONTGOMERY",
                "MORGAN",
                "NEW MADRID",
                "NEWTON",
                "NODAWAY",
                "OREGON",
                "OSAGE",
                "OZARK",
                "PEMISCOT",
                "PERRY",
                "PETTIS",
                "PHELPS",
                "PIKE",
                "PLATTE",
                "POLK",
                "PULASKI",
                "PUTNAM",
                "RALLS",
                "RANDOLPH",
                "RAY",
                "REYNOLDS",
                "RIPLEY",
                "ST CHARLES",
                "ST CLAIR",
                "STE GENEVIEVE",
                "ST FRANCOIS",
                "ST LOUIS",
                "SALINE",
                "SCHUYLER",
                "SCOTLAND",
                "SCOTT",
                "SHANNON",
                "SHELBY",
                "STODDARD",
                "STONE",
                "SULLIVAN",
                "TANEY",
                "TEXAS",
                "VERNON",
                "WARREN",
                "WASHINGTON",
                "WAYNE",
                "WEBSTER",
                "WORTH",
                "WRIGHT"};
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_dropdown_item_1line,counties);
        AutoCompleteTextView autoTextView=(AutoCompleteTextView) v.findViewById(R.id.harvest_county);
        autoTextView.setAdapter(adapter);
        autoTextView.setOnItemClickListener(this);
        mDbHelper=new CWDDbHelper(getActivity());
        mDb=mDbHelper.getWritableDatabase();
        return v;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onMapReady(GoogleMap myMap) {
        googleMap=myMap;
        LatLng como = new LatLng(38.946494, -92.3308389);
        googleMap.addMarker(new MarkerOptions().position(como).title("Marker in COMO"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(como, 14));
        googleMap.setOnMapClickListener(this);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        googleMap.clear();
        googleMap.addMarker(new MarkerOptions().position(latLng).title("Here is where you clicked"));
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        EditText Lat= (EditText) this.getView().findViewById(R.id.harvest_latitude);
        EditText Long= (EditText) this.getView().findViewById(R.id.harvest_longitude);
        Lat.setText(String.valueOf(latLng.latitude));
        Long.setText(String.valueOf(latLng.longitude));

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        /*String[] townships=new String[52];
        for (int i=0;i<52;i++){
            townships[i]=String.valueOf(i+16);
        }
        ArrayAdapter<String> townShipAdapter=new ArrayAdapter<String>(getActivity(),R.layout.my_spinner_layout,townships);
        Spinner townShipsSpinner=(Spinner) getView().findViewById(R.id.harvest_township);
        townShipsSpinner.setAdapter(townShipAdapter);*/
        String county=((TextView)view).getText().toString();
        Cursor TWPc=mDb.rawQuery("SELECT DISTINCT TWP FROM PLSS WHERE CNTY = ?", new String[]{county});
        Cursor RNGc=mDb.rawQuery("SELECT DISTINCT RNG FROM PLSS WHERE CNTY = ?", new String[]{county});
        Cursor SECc=mDb.rawQuery("SELECT DISTINCT SEC FROM PLSS WHERE CNTY = ? ORDER BY SEC", new String[]{county});
        //Cursor c=mDb.rawQuery("SELECT SEC FROM PLSS WHERE CNTY = ? and TWP = ? and RNG = ?",new String[] {"CLARK","T67N","R09W"});
        ArrayList<String> townships =new ArrayList<String>();
        ArrayList<String> ranges =new ArrayList<String>();
        ArrayList<String> sections =new ArrayList<String>();
        while(TWPc.moveToNext()){
            String temp=TWPc.getString(TWPc.getColumnIndexOrThrow("TWP"));
            townships.add(temp.substring(0,3));
        }
        while(RNGc.moveToNext()){
            String temp=RNGc.getString(RNGc.getColumnIndexOrThrow("RNG"));
            ranges.add(temp.substring(0,3));
        }
        while(SECc.moveToNext()){
            String temp=SECc.getString(SECc.getColumnIndexOrThrow("SEC"));
            sections.add(temp);
        }
        ArrayAdapter<String> townshipAdapter=new ArrayAdapter<String>(getActivity(),R.layout.my_spinner_layout,townships);
        ArrayAdapter<String> rangeAdapter=new ArrayAdapter<String>(getActivity(),R.layout.my_spinner_layout,ranges);
        ArrayAdapter<String> sectionAdapter=new ArrayAdapter<String>(getActivity(),R.layout.my_spinner_layout,sections);
        ((Spinner) getView().findViewById(R.id.harvest_township)).setAdapter(townshipAdapter);
        ((Spinner) getView().findViewById(R.id.harvest_range)).setAdapter(rangeAdapter);
        ((Spinner) getView().findViewById(R.id.harvest_section)).setAdapter(sectionAdapter);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onPreviousButtonClicked(int i);
        public void onNextButtonClicked(int i);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.previous_button:
                mListener.onPreviousButtonClicked(2);
                break;
            case R.id.next_button:
                mListener.onNextButtonClicked(2);
                break;
        }
    }

}
