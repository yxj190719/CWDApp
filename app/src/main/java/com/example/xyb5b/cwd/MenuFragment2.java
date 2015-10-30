package com.example.xyb5b.cwd;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MenuFragment2.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MenuFragment2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuFragment2 extends Fragment implements AdapterView.OnItemClickListener {

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        for (int i = 0; i < parent.getCount(); i++) {
            parent.getChildAt(i).setBackgroundResource(android.R.color.transparent);
        }
        view.setBackgroundResource(android.R.color.darker_gray);
        mListener.ChangeSheetContent(position);
    }

    private ListView lv;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    //hhm represents hunter_harvest menu
    //rkm represents road_kill menu
    //sdm represents sick_deer menu
    //thm represents target_harvested menu
    private String[] hhm = new String[]{"DEER & COLLECTOR INFORMATION", "DEER LOCATION", "HUNTER HARVESTED"};
    private String[] rkm = new String[]{"DEER & COLLECTOR INFORMATION", "DEER LOCATION"};
    private String[] sdm = new String[]{"DEER & COLLECTOR INFORMATION", "DEER LOCATION"};
    private String[] thm = new String[]{"DEER & COLLECTOR INFORMATION", "DEER LOCATION", "TARGETED HARVEST"};
    private String[] menu_values;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MenuFragment2.
     */
    // TODO: Rename and change types and number of parameters
    public static MenuFragment2 newInstance(String param1, String param2) {
        MenuFragment2 fragment = new MenuFragment2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public MenuFragment2() {
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
        View view = inflater.inflate(R.layout.fragment_menu_fragment2, container, false);
        String buttonTag = getActivity().getIntent().getStringExtra(MainActivity.BUTTON_TAG);
        switch (buttonTag) {
            case "hunter_harvested":
                menu_values = hhm;
                break;
            case "road_kill":
                menu_values = rkm;
                break;
            case "sick_deer":
                menu_values = sdm;
                break;
            case "targeted_harvest":
                menu_values = thm;
                break;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, menu_values);
        lv = (ListView) view.findViewById(R.id.menuList);
        /*lv.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,menu_values){
            public View getView(int position, View convertView, ViewGroup parent)
            {
                final View renderer = super.getView(position, convertView, parent);
                if (position == 0)
                {
                    renderer.setBackgroundResource(android.R.color.darker_gray);
                }
                return renderer;
            }
        });*/
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
        lv.post(new Runnable() {
            @Override
            public void run() {
                lv.getChildAt(0).setBackgroundResource(android.R.color.darker_gray);
            }
        });
        /*String todayDate = DateFormat.getDateInstance().format(new Date());
        TextView t = (TextView) view.findViewById(R.id.showPickedDate);
        t.setText(todayDate);*/
        // Inflate the layout for this fragment
        TextView t = (TextView) view.findViewById(R.id.collection_date);
        t.setText(getActivity().getIntent().getStringExtra(MainActivity.Date));
        return view;
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


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void ChangeSheetContent(int position);
    }



}
