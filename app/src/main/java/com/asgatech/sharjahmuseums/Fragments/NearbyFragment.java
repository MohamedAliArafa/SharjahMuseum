package com.asgatech.sharjahmuseums.Fragments;


import android.content.Intent;
import android.net.Uri;
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
        musuemsIv.setOnClickListener(v -> {
            // Search for restaurants nearby
            Uri gmmIntentUri = Uri.parse("geo:0,0?q=museums");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        });
        cafeIv = view.findViewById(R.id.cafe_iv);
        cafeIv.setOnClickListener(v -> {
            // Search for restaurants nearby
            Uri gmmIntentUri = Uri.parse("geo:0,0?q=cafe");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        });
        resturantIv = view.findViewById(R.id.resturant_iv);
        resturantIv.setOnClickListener(v -> {
            // Search for restaurants nearby
            Uri gmmIntentUri = Uri.parse("geo:0,0?q=restaurants");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        });
        atmIv = view.findViewById(R.id.atm_iv);
        atmIv.setOnClickListener(v -> {
            // Search for restaurants nearby
            Uri gmmIntentUri = Uri.parse("geo:0,0?q=atm");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        });
        banksIv = view.findViewById(R.id.banks_iv);
        banksIv.setOnClickListener(v -> {
            // Search for restaurants nearby
            Uri gmmIntentUri = Uri.parse("geo:0,0?q=banks");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        });
        bookIv = view.findViewById(R.id.book_iv);
        bookIv.setOnClickListener(v -> {
            // Search for restaurants nearby
            Uri gmmIntentUri = Uri.parse("geo:0,0?q=book stores");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        });
        parkingsIv = view.findViewById(R.id.parkings_iv);
        parkingsIv.setOnClickListener(v -> {
            // Search for restaurants nearby
            Uri gmmIntentUri = Uri.parse("geo:0,0?q=parking");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        });
        busIv = view.findViewById(R.id.bus_iv);
        busIv.setOnClickListener(v -> {
            // Search for restaurants nearby
            Uri gmmIntentUri = Uri.parse("geo:0,0?q=bus");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        });
        airportIv = view.findViewById(R.id.airport_iv);
        airportIv.setOnClickListener(v -> {
            // Search for restaurants nearby
            Uri gmmIntentUri = Uri.parse("geo:0,0?q=airport");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        });
    }

}
