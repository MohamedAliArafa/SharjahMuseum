package com.asgatech.sharjahmuseums.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.asgatech.sharjahmuseums.Activities.Home.HomeActivity;
import com.asgatech.sharjahmuseums.R;

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
        ((HomeActivity)getActivity()).changeToolbarTitle(getString(R.string.near_by_facilities));
        musuemsIv = view.findViewById(R.id.musuems_iv);
        cafeIv = view.findViewById(R.id.cafe_iv);
        resturantIv = view.findViewById(R.id.resturant_iv);
        atmIv = view.findViewById(R.id.atm_iv);
        banksIv = view.findViewById(R.id.banks_iv);
        bookIv = view.findViewById(R.id.book_iv);
        parkingsIv = view.findViewById(R.id.parkings_iv);
        busIv = view.findViewById(R.id.bus_iv);
        airportIv = view.findViewById(R.id.airport_iv);
    }

}
