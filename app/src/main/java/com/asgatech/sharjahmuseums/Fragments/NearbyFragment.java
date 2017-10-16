package com.asgatech.sharjahmuseums.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.asgatech.sharjahmuseums.Activities.HomeActivity;
import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.CustomFonts.TextViewLight;

/**
 * A simple {@link Fragment} subclass.
 */
public class NearbyFragment extends Fragment {

    private ImageView musuemsIv;
    private ImageView cafeIv;
    private ImageView resturantIv;
    private ImageView atmIv;
    private ImageView banksIv;
    private ImageView bookIv;
    private ImageView parkingsIv;
    private ImageView busIv;
    private ImageView airportIv;

    public NearbyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nearby, container, false);
    }





    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);

    }
    private void initView(View view){
        ((HomeActivity)getActivity()).ToolbarTitleTextView.setText(getString(R.string.near_by_facilities));
        musuemsIv = (ImageView) view.findViewById(R.id.musuems_iv);
        cafeIv = (ImageView) view.findViewById(R.id.cafe_iv);
        resturantIv = (ImageView) view.findViewById(R.id.resturant_iv);
        atmIv = (ImageView) view.findViewById(R.id.atm_iv);
        banksIv = (ImageView) view.findViewById(R.id.banks_iv);
        bookIv = (ImageView) view.findViewById(R.id.book_iv);
        parkingsIv = (ImageView) view.findViewById(R.id.parkings_iv);
        busIv = (ImageView) view.findViewById(R.id.bus_iv);
        airportIv = (ImageView) view.findViewById(R.id.airport_iv);
    }

}
