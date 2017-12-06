package com.asgatech.sharjahmuseums.Tools;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by mohamed.arafa  on 11/6/2016.
 */
public class LocationTool {

    //Last known location
    //<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
    //location updates
    //<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>

    private static final int MODE_LAST_KNOWN_LOCATION = 1;
    private static final int MODE_NEW_LOCATION = 2;
    public static final int REQUEST_CHECK_SETTINGS = 1267;

    private Context context;
    private GoogleApiClient mGoogleApiClient;
    private LocationListener locationListener;
    private int mode;

    public LocationTool(Context context) {
        this.context = context;
    }

    public void getLastKnownLocation(LocationListener locationListener) {
        mode = MODE_LAST_KNOWN_LOCATION;
        this.locationListener = locationListener;
        getGoogleAPIClient().connect();
    }

    public void getNewLocation(LocationListener locationListener) {
        mode = MODE_NEW_LOCATION;
        this.locationListener = locationListener;
        getGoogleAPIClient().connect();
    }

    private LocationRequest createLocationRequest() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return mLocationRequest;
    }

    private GoogleApiClient getGoogleAPIClient() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(context)
                    .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                        @Override
                        public void onConnected(@Nullable Bundle bundle) {
                            if (mode == MODE_LAST_KNOWN_LOCATION) {
                                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                                    return;
                                Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                                if (lastLocation != null) {
                                    locationListener.gotLocation(lastLocation);
                                    stop();
                                }
                            } else if (mode == MODE_NEW_LOCATION) {
                                getLocationUpdates();
                            }
                        }

                        @Override
                        public void onConnectionSuspended(int i) {
                        }
                    })
                    .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        }
                    })
                    .addApi(LocationServices.API)
                    .build();
        }
        return mGoogleApiClient;
    }

    private void getLocationUpdates() {
        final LocationRequest locationRequest = createLocationRequest();
        final LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        PendingResult<LocationSettingsResult> LocationSettingsResult = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        LocationSettingsResult.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates locationSettingsStates = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can
                        // initialize location requests here.
                        startLocationUpdates(locationRequest);
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied, but this can be fixed
                        // by showing the user a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            if (context instanceof Activity) {
                                status.startResolutionForResult((Activity) context, REQUEST_CHECK_SETTINGS);
                            }
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way
                        // to fix the settings so we won't show the dialog.
                        break;
                }
            }
        });
    }

    protected void startLocationUpdates(LocationRequest locationRequest) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            return;
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest, new com.google.android.gms.location.LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (location != null) {
                    locationListener.gotLocation(location);
                    stop();
                }
            }
        });
    }

    private void stop() {
        mGoogleApiClient.disconnect();
    }

    public interface LocationListener {
        void gotLocation(Location location);
    }

    public void getAddress(final double lat, final double lon, final AddressListener addressListener) {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                String errorMessage = null;
                List<Address> addresses = null;
                try {
                    addresses = geocoder.getFromLocation(lat, lon,
                            // In this sample, get just a single address.
                            1);
                } catch (IOException ioException) {
                    // Catch network or other I/O problems.
                    errorMessage = "service not available";
                    Log.e("getAddress", errorMessage, ioException);
                } catch (IllegalArgumentException illegalArgumentException) {
                    // Catch invalid latitude or longitude values.
                    errorMessage = "invalid lat long used";
                    Log.e("getAddress", errorMessage + ". " + "Latitude = " + lat + ", Longitude = " + lon, illegalArgumentException);
                }

                // Handle case where no address was found.
                if (addresses == null || addresses.size() == 0) {
                    if (errorMessage.isEmpty()) {
                        errorMessage = "string no address found";
                        Log.e("getAddress", errorMessage);
                    }
                    return null;
                } else {
                    Address address = addresses.get(0);
                    ArrayList<String> addressFragments = new ArrayList<String>();

                    // Fetch the address lines using getAddressLine,
                    // join them, and send them to the thread.
                    for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                        addressFragments.add(address.getAddressLine(i));
                    }
                    return TextUtils.join(System.getProperty("line.separator"), addressFragments);
                }
            }

            @Override
            protected void onPostExecute(String address) {
                addressListener.gotAddress(address);
            }
        }.execute();

    }

    public interface AddressListener {
        void gotAddress(String address);
    }
}
