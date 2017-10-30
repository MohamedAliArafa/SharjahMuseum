package com.asgatech.sharjahmuseums.Activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.Connection.ConstantUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class ViewLocationMapActivity extends FragmentActivity {
    private static final int MAP_ZOOM_OUT = 10;
    private static final int MAP_ZOOM_TO = 12;
    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private Marker sourceMarker;
    double longtude, latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_location_map);
        latitude = getIntent().getDoubleExtra(ConstantUtils.EXTRA_MUSEUMS_lATITUDE, 0);
        longtude = getIntent().getDoubleExtra(ConstantUtils.EXTRA_MUSEUMS_LONGTUDE, 0);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.googleMap);
        setupMap(latitude, longtude);
//        setupMap(30.1,31.2);


    }

    private void setupMap(final double latitude, final double longitude) {
        mapFragment.getMapAsync(googleMap -> {
            mMap = googleMap;
            googleMap.getUiSettings().setMyLocationButtonEnabled(false);
            if (latitude > 0 && longitude > 0) {
                Log.d("latitudeMap", latitude + "");
                Log.d("longitudeMap", longitude + "");
                MarkerOptions optionFirstLocation = new MarkerOptions();
                optionFirstLocation.position(new LatLng(latitude, longitude));
                optionFirstLocation.icon(BitmapDescriptorFactory.defaultMarker());
                sourceMarker = googleMap.addMarker(optionFirstLocation);
                initCamera(latitude, longitude);
                mMap.getUiSettings().setScrollGesturesEnabled(true);

            }
        });
    }

    private void initCamera(double lat, double lng) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,
                lng), MAP_ZOOM_OUT));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(MAP_ZOOM_TO));
    }
}
