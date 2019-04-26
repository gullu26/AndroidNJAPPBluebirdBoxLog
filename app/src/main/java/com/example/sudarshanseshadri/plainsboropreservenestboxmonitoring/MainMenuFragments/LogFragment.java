//package com.example.sudarshanseshadri.plainsborolayoutthreetabs;
//
//import android.content.Context;
//import android.net.Uri;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//
///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link LogFragment.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link LogFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
//public class LogFragment extends Fragment {
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    private OnFragmentInteractionListener mListener;
//
//    public LogFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment LogFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static LogFragment newInstance(String param1, String param2) {
//        LogFragment fragment = new LogFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_log, container, false);
//    }
//
//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
//
//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
//}

package com.example.sudarshanseshadri.plainsboropreservenestboxmonitoring.MainMenuFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sudarshanseshadri.plainsboropreservenestboxmonitoring.MyObjects.AllUserLogData;
import com.example.sudarshanseshadri.plainsboropreservenestboxmonitoring.R;
import com.example.sudarshanseshadri.plainsboropreservenestboxmonitoring.RecyclerViewAdapters.RecyclerViewAdapterMainNav;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class LogFragment extends Fragment {

    private static final String TAG = "LogFragment";

    View rootView;
    SharedPreferences mPrefs;
    AllUserLogData allUserLogData;


    public LogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_log, container, false);


        mPrefs=getActivity().getSharedPreferences("PlainsboroPrefs", Context.MODE_PRIVATE);

        setLogEntries();
        initRecyclerView();
        return rootView;


    }

    private void setLogEntries(){
        Log.d(TAG, "setting images and text");


        Gson gson = new Gson();
        String json = mPrefs.getString("allUserLogData", null);
        allUserLogData = gson.fromJson(json, AllUserLogData.class);

        if (allUserLogData==null)
        {
            allUserLogData=new AllUserLogData();
        }



    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerview.");
        RecyclerView recyclerView = rootView.findViewById(R.id.id_recyclerView_completeLogList);
        RecyclerViewAdapterMainNav adapter = new RecyclerViewAdapterMainNav( allUserLogData, getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }


    public String getDefaultDate(){

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("MMMM dd, YYYY");
        String strDate = mdformat.format(calendar.getTime());
        String dayNum=strDate.split(" ")[1];
        if (dayNum.startsWith("0"))
        {
            dayNum= dayNum.substring(1) ;
        }
        String formatStrDate=strDate.split(" ")[0]+ " " + dayNum +" "+strDate.split(" ")[2];
        return formatStrDate;

    }
    private int getNumberOfBoxes()
    {
        return allUserLogData.getCompleteLogEntryArrayList().size();
    }



}

