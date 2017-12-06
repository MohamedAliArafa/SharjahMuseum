package com.asgatech.sharjahmuseums.Tools.Geofence;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.asgatech.sharjahmuseums.Activities.Home.HomeActivity;
import com.asgatech.sharjahmuseums.Models.GeoReference;
import com.asgatech.sharjahmuseums.Models.InnerLocationClass;
import com.asgatech.sharjahmuseums.Models.InnerLocationModel;
import com.asgatech.sharjahmuseums.Models.geoFenceResponseModel;
import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.Connection.ConstantUtils;
import com.asgatech.sharjahmuseums.Tools.Connection.ServerTool;
import com.asgatech.sharjahmuseums.Tools.DataBaseHandler.DBHelper;
import com.asgatech.sharjahmuseums.Tools.PermissionTool;
import com.asgatech.sharjahmuseums.Tools.SharedTool.UserData;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingEvent;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;

/**
 * Created by mohamed.arafa on 11/5/2017.
 */

public class GeofenceTransitionsIntentService extends IntentService implements OnCompleteListener<Void> {
    private static final String TAG = "GeofenceTransitionsIS";
    /**
     * Provides access to the Geofencing API.
     */
    private InnerLocationClass innerLocationClass;
    private GeofencingClient mGeofencingClient;
    private geoFenceResponseModel gResponse = new geoFenceResponseModel();
    /**
     * The list of geofences used in this sample.
     */
    private ArrayList<Geofence> mGeofenceList;
    /**
     * Used when requesting to add or remove geofences.
     */
    private PendingIntent mGeofencePendingIntent;
    private PendingGeofenceTask mPendingGeofenceTask = PendingGeofenceTask.NONE;

    /**
     * This constructor is required, and calls the super IntentService(String)
     * constructor with the name for a worker thread.
     */
    public GeofenceTransitionsIntentService() {
        // Use the TAG to name the worker thread.
        super(TAG);
    }

    /**
     * Handles incoming intents.
     *
     * @param intent sent by Location Services. This Intent is provided to Location
     *               Services (inside a PendingIntent) when addGeofences() is called.
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        mGeofenceList = new ArrayList<>();
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent.hasError()) {
            String errorMessage = GeofenceErrorMessages.getErrorString(this,
                    geofencingEvent.getErrorCode());
            Log.e(TAG, errorMessage);
            return;
        }

        // Get the transition type.
        int geofenceTransition = geofencingEvent.getGeofenceTransition();

        // Test that the reported transition was of interest.
        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER ||
                geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {

            // Get the geofences that were triggered. A single event can trigger multiple geofences.
            List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();

            List<InnerLocationModel> geofenceTransitionDetails = getGeofenceTransitionDetails(geofenceTransition,
                    triggeringGeofences);

            // Send notification and log the transition details.
            String geofenceTransitionString = getTransitionString(geofenceTransition);
            sendNotification(geofenceTransitionDetails, geofenceTransitionString);
            Intent intentt = new Intent("myGeofence");
            InnerLocationClass innerLocationClass = new InnerLocationClass(geofenceTransitionDetails);
            intentt.putExtra("title", geofenceTransitionString +
                    UserData.getGeoDistance(getApplicationContext())
                    + "\n" + getString(R.string.geofence_transition_entered2) + "\n" +
                    geofenceTransitionDetails.get(0).getTitle() + " " + getString(R.string.geofence_transition_entered3));
            intentt.putExtra("text", innerLocationClass);
//            intentt.putExtra("text", "");
            LocalBroadcastManager.getInstance(this).sendBroadcast(intentt);
        } else {
            populateGeoFenceList(UserData.getLocalization(getApplicationContext()));
            // Log the error.
            Log.e(TAG, getString(R.string.geofence_transition_invalid_type, geofenceTransition));
        }
        GeofenceAlarmReceiver.completeWakefulIntent(intent);
    }

    /**
     * Gets transition details and returns them as a formatted string.
     *
     * @param geofenceTransition  The ID of the geofence transition.
     * @param triggeringGeofences The geofence(s) triggered.
     * @return The transition details formatted as String.
     */
    private List<InnerLocationModel> getGeofenceTransitionDetails(int geofenceTransition, List<Geofence> triggeringGeofences) {

        String geofenceTransitionString = getTransitionString(geofenceTransition);
        List<InnerLocationModel> locationModels = new ArrayList<>();
        InnerLocationModel locationModel = new InnerLocationModel();
        // Get the Ids of each geofence that was triggered.
        ArrayList<String> triggeringGeofencesIdsList = new ArrayList<>();
        for (Geofence geofence : triggeringGeofences) {
            triggeringGeofencesIdsList.add(geofence.getRequestId());
            locationModel.setTitle(geofence.getRequestId());
            locationModels.add(locationModel);
        }
//        String triggeringGeofencesIdsString = TextUtils.join(",\n ", triggeringGeofencesIdsList);
//        List<String> triggeringGeofencesIdsStrings = new ArrayList<>();
//
//        triggeringGeofencesIdsStrings.add(TextUtils.join(",\n", triggeringGeofencesIdsList));

//        return geofenceTransitionString + ": " + triggeringGeofencesIdsString;
        return locationModels;
    }


    /**
     * Posts a notification in the notification bar when a transition is detected.
     * If the user clicks the notification, control goes to the MainActivity.
     */
    private void sendNotification(List<InnerLocationModel> notificationDetails, String geofenceTransitionString) {
        // Create an explicit content Intent that starts the main Activity.
        Intent notificationIntent = new Intent(getApplicationContext(), HomeActivity.class);

        // Construct a task stack.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        // Add the main Activity to the task stack as the parent.
        stackBuilder.addParentStack(HomeActivity.class);

        // Push the content Intent onto the stack.
        stackBuilder.addNextIntent(notificationIntent);

        // Get a PendingIntent containing the entire back stack.
//        PendingIntent notificationPendingIntent =
//                stackBuilder.getPendingIntent(0,  notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, notificationIntent,
                PendingIntent.FLAG_ONE_SHOT);

        // Get a notification builder that's compatible with platform versions >= 4
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        // Define the notification settings.
        builder.setSmallIcon(R.drawable.ic_logo_notification)
                // In a real app, you may want to use a library like Volley
                // to decode the Bitmap.
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                        R.drawable.ic_logo_notification))
                .setColor(Color.RED)
                .setContentTitle(geofenceTransitionString)
                .setContentText(notificationDetails.get(0).getTitle())
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(notificationDetails.get(0).getTitle()))
                .setContentIntent(pendingIntent);

        // Dismiss notification once the user touches it.
        builder.setAutoCancel(true);

        // Get an instance of the Notification manager
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Issue the notification
        mNotificationManager.notify(0, builder.build());
    }

    /**
     * Maps geofence transition types to their human-readable equivalents.
     *
     * @param transitionType A transition type constant defined in Geofence
     * @return A String indicating the type of transition
     */
    private String getTransitionString(int transitionType) {
        switch (transitionType) {
            case Geofence.GEOFENCE_TRANSITION_ENTER:
                return getString(R.string.geofence_transition_entered1);
            case Geofence.GEOFENCE_TRANSITION_EXIT:
                return getString(R.string.geofence_transition_exited);
            default:
                return getString(R.string.unknown_geofence_transition);
        }
    }

    private void populateGeoFenceList(int lang) {
        ServerTool.getGeoFencingList(lang, new ServerTool.APICallBack<geoFenceResponseModel>() {
            @Override
            public void onSuccess(geoFenceResponseModel response) {
                gResponse.setGeoReferenceList(response.getGeoReferenceList());
                for (GeoReference entry : response.getGeoReferenceList()) {
                    mGeofenceList.add(new Geofence.Builder()
                            .setRequestId(entry.getTitle())
                            .setCircularRegion(
                                    Double.parseDouble(entry.getLat()),
                                    Double.parseDouble(entry.getLong()),
                                    response.getDistance()
                            )
                            .setExpirationDuration(ConstantUtils.GEOFENCE_EXPIRATION_IN_MILLISECONDS)
                            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
                                    Geofence.GEOFENCE_TRANSITION_EXIT)
                            .build());
                }
                mGeofencingClient = LocationServices.getGeofencingClient(getApplicationContext());
                UserData.saveGeoDistance(getApplicationContext(), response.getDistance());
                List<InnerLocationModel> locationModels = new ArrayList<>();
                DBHelper db = new DBHelper(getApplicationContext());
                db.DeleteFromSql(getApplicationContext());
                for (int i = 0; i < response.getGeoReferenceList().size(); i++) {
                    InnerLocationModel locationModel = new InnerLocationModel();
                    locationModel.setTitle(response.getGeoReferenceList().get(i).getTitle());
                    locationModel.setLang(Double.parseDouble(response.getGeoReferenceList().get(i).getLong()));
                    locationModel.setLat(Double.parseDouble(response.getGeoReferenceList().get(i).getLat()));
                    locationModels.add(locationModel);
                }

                db.insert_Date_SystemCalenderId_DB(getApplicationContext(), locationModels);
                db.close();
                addGeoFencesHandler();
            }

            @Override
            public void onFailed(int statusCode, ResponseBody responseBody) {

            }
        });
    }

    public void addGeoFencesHandler() {
        if (!PermissionTool.checkPermission(getApplicationContext(), PermissionTool.PERMISSION_LOCATION)) {
            return;
        }
        mGeofencingClient.addGeofences(getGeofencingRequest(), getGeoFencePendingIntent())
                .addOnCompleteListener(this);
    }

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
        Intent intent = new Intent(this, GeofenceTransitionsIntentService.class);
        // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when calling
        // addGeofences() and removeGeofences().
        return PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @Override
    public void onComplete(@NonNull Task<Void> task) {
        mPendingGeofenceTask = PendingGeofenceTask.NONE;
        if (task.isSuccessful()) {
            updateGeofencesAdded(!getGeofencesAdded());
            int messageId = getGeofencesAdded() ? R.string.geofences_added :
                    R.string.geofences_removed;
            Log.w("GeoFence", this.getString(messageId));
//            Toast.makeText(context, context.getString(messageId), Toast.LENGTH_SHORT).show();
        } else {
            // Get the status code for the error and log it using a user-friendly message.
            String errorMessage = GeofenceErrorMessages.getErrorString(this, task.getException());
            Log.w("GeoFence", errorMessage);
        }
    }

    private boolean getGeofencesAdded() {
        return PreferenceManager.getDefaultSharedPreferences(this).getBoolean(
                ConstantUtils.GEOFENCES_ADDED_KEY, false);
    }

    private void updateGeofencesAdded(boolean added) {
        PreferenceManager.getDefaultSharedPreferences(this)
                .edit()
                .putBoolean(ConstantUtils.GEOFENCES_ADDED_KEY, added)
                .apply();
    }

    private enum PendingGeofenceTask {
        ADD, REMOVE, NONE
    }

}

