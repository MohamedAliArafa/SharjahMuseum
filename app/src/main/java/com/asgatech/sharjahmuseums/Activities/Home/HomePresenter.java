package com.asgatech.sharjahmuseums.Activities.Home;

import android.app.Activity;
import android.app.PendingIntent;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.asgatech.sharjahmuseums.Models.InnerLocationClass;
import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.Connection.ConstantUtils;
import com.asgatech.sharjahmuseums.Tools.DialogTool.NotificationDialog;
import com.asgatech.sharjahmuseums.Tools.Geofence.GeofenceAlarmReceiver;
import com.asgatech.sharjahmuseums.Tools.Geofence.GeofenceTransitionsIntentService;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;

import java.util.ArrayList;

/**
 * Created by mohamed.arafa on 10/16/2017.
 */

//public class HomePresenter implements HomeContract.UserAction, LifecycleObserver, OnCompleteListener<Void> {
public class HomePresenter implements HomeContract.UserAction, LifecycleObserver {

    private final FragmentManager mFragmentManager;
    private final HomeContract.ModelView mView;
    private final Activity context;

//    @Override
//    public void onComplete(@NonNull Task<Void> task) {
//        mPendingGeofenceTask = PendingGeofenceTask.NONE;
//        if (task.isSuccessful()) {
//            updateGeofencesAdded(!getGeofencesAdded());
//            int messageId = getGeofencesAdded() ? R.string.geofences_added :
//                    R.string.geofences_removed;
//            Log.w("GeoFence", context.getString(messageId));
////            Toast.makeText(context, context.getString(messageId), Toast.LENGTH_SHORT).show();
//        } else {
//            // Get the status code for the error and log it using a user-friendly message.
//            String errorMessage = GeofenceErrorMessages.getErrorString(context, task.getException());
//            Log.w("GeoFence", errorMessage);
//        }
//    }

//    private boolean getGeofencesAdded() {
//        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(
//                ConstantUtils.GEOFENCES_ADDED_KEY, false);
//    }
//
//    private void updateGeofencesAdded(boolean added) {
//        PreferenceManager.getDefaultSharedPreferences(context)
//                .edit()
//                .putBoolean(ConstantUtils.GEOFENCES_ADDED_KEY, added)
//                .apply();
//    }

    /**
     * Tracks whether the user requested to add or remove geofences, or to do neither.
     */
    private enum PendingGeofenceTask {
        ADD, REMOVE, NONE
    }

    /**
     * Provides access to the Geofencing API.
     */
    private GeofencingClient mGeofencingClient;
    /**
     * The list of geofences used in this sample.
     */
    private ArrayList<Geofence> mGeofenceList;
    InnerLocationClass innerLocationClass;
    /**
     * Used when requesting to add or remove geofences.
     */
    private PendingIntent mGeofencePendingIntent;
    private PendingGeofenceTask mPendingGeofenceTask = PendingGeofenceTask.NONE;

    public HomePresenter(HomeContract.ModelView view, Activity context, Lifecycle lifecycle, FragmentManager fragmentManager) {
        this.mFragmentManager = fragmentManager;
        this.mView = view;
        this.context = context;
        lifecycle.addObserver(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void create() {
        // Empty list for storing geofences.
        mGeofenceList = new ArrayList<>();
        // Initially set the PendingIntent used in addGeofences() and removeGeofences() to null.
        mGeofencePendingIntent = null;
        GeofenceAlarmReceiver alarm = new GeofenceAlarmReceiver();
        alarm.setAlarm(context);

        // Get the geofences used. Geofence data is hard coded in this sample.
//        populateGeoFenceList(UserData.getLocalization(context));
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void resume() {
        LocalBroadcastManager.getInstance(mView.getContext()).registerReceiver(mMessageReceiver,
                new IntentFilter("myFunction"));
        LocalBroadcastManager.getInstance(mView.getContext()).registerReceiver(gMessageReceiver,
                new IntentFilter("myGeofence"));
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void pause() {
        LocalBroadcastManager.getInstance(mView.getContext()).unregisterReceiver(mMessageReceiver);
        LocalBroadcastManager.getInstance(mView.getContext()).unregisterReceiver(gMessageReceiver);
    }

//    @OnLifecycleEvent(Lifecycle.Event.ON_START)
//    public void start() {
//        new LocationTool(context).getNewLocation(new LocationTool.LocationListener() {
//            @Override
//            public void gotLocation(Location location) {
//                Log.e("latttttt", location.getLatitude() + "");
//                Log.e("longgggggg", location.getLongitude() + "");
//                Toast.makeText(context, "New Location:\n" + location, Toast.LENGTH_LONG).show();
//            }
//        });
//    }


    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Extract data included in the Intent
            int id = intent.getIntExtra("id", 0);
            int type = intent.getIntExtra("type", 0);
            String title = intent.getStringExtra("title");
            String desc = intent.getStringExtra("text");
            Log.e("desc", desc);
            //alert data here
            new NotificationDialog().showDialog(mView.getContext(), id, type, title, desc);
        }
    };
    private BroadcastReceiver gMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context contextv, Intent intent) {
            // Extract data included in the Intent
            innerLocationClass = new InnerLocationClass();
            int id = intent.getIntExtra("id", 0);
            int type = intent.getIntExtra("type", 0);
            String desc = intent.getStringExtra("title");
            InnerLocationClass innerLocationClass = intent.getParcelableExtra("text");
            if (mView.getContext() != null) {
                new NotificationDialog().showDialogs(mView.getContext(), desc, innerLocationClass);
            }
        }
    };

    private GeofencingRequest getGeofencingRequest() {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();

        // The INITIAL_TRIGGER_ENTER flag indicates that geofencing service should trigger a
        // GEOFENCE_TRANSITION_ENTER notification when the geofence is added and if the device
        // is already inside that geofence.
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);

        // Add the geofences to be monitored by geofencing service.
        builder.addGeofences(mGeofenceList);

        // Return a GeofencingRequest.
        return builder.build();
    }


    private PendingIntent getGeoFencePendingIntent() {
        // Reuse the PendingIntent if we already have it.
        if (mGeofencePendingIntent != null) {
            return mGeofencePendingIntent;
        }
        Intent intent = new Intent(context, GeofenceTransitionsIntentService.class);
        // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when calling
        // addGeofences() and removeGeofences().
        return PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @Override
    public void openFragment(Fragment fragment, Bundle bundle) {
        Fragment fragmentCheck = mFragmentManager.findFragmentById(R.id.content_main);
        if (fragmentCheck.getClass() == fragment.getClass()) {
            mView.closeDrawer();
            return;
        }
        mView.showToolbar();
        mView.changeToolbarTitle("");
        mView.restToolbarColor();
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        transaction.replace(R.id.content_main, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
        mView.closeDrawer();
        mView.hideLogo();
    }

    @Override
    public void openFragment(Fragment fragment, Bundle bundle, boolean shouldAddToBackStack) {
        Fragment fragmentCheck = mFragmentManager.findFragmentById(R.id.content_main);
        if (fragmentCheck.getClass() == fragment.getClass()) {
            mView.closeDrawer();
            return;
        }
        mView.showToolbar();
        mView.changeToolbarTitle("");
        mView.restToolbarColor();
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        transaction.replace(R.id.content_main, fragment);
        if (shouldAddToBackStack)
            transaction.addToBackStack(null);
        transaction.commit();
        mView.closeDrawer();
        mView.hideLogo();
    }

    @Override
    public void openHome() {
        mView.showToolbar();
        mView.changeToolbarTitle("");
        mView.restToolbarColor();
        mFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        HomeFragment fragment = new HomeFragment();
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(R.id.content_main, fragment, ConstantUtils.HOMEPAGE_FRAGMENT_KEY);
        transaction.commit();
        mView.closeDrawer();
        mView.showLogo();
    }
}
