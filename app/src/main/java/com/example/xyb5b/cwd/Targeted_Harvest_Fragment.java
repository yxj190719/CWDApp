package com.example.xyb5b.cwd;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Targeted_Harvest_Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Targeted_Harvest_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Targeted_Harvest_Fragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Targeted_Harvest_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Targeted_Harvest_Fragment newInstance(String param1, String param2) {
        Targeted_Harvest_Fragment fragment = new Targeted_Harvest_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Targeted_Harvest_Fragment() {
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
        View v=inflater.inflate(R.layout.targeted_harvest, container, false);
        Button previousButton= (Button)v.findViewById(R.id.previous_button);
        Button nextButton=(Button)v.findViewById(R.id.next_button);
        previousButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
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
        public void onPreviousButtonClicked(int i);
        public void onNextButtonClicked(int i);
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.previous_button:
                mListener.onPreviousButtonClicked(4);
                break;
            case R.id.next_button:
                mListener.onNextButtonClicked(4);
                break;
        }
    }

}
