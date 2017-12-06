package com.asgatech.sharjahmuseums.Tools.FCM;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.asgatech.sharjahmuseums.Activities.Events.EventDetails.EventDetailsActivity;
import com.asgatech.sharjahmuseums.Activities.NotificationDetailActivity;
import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.Localization;
import com.asgatech.sharjahmuseums.Tools.SharedTool.UserData;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/*
 * Created by mohamed.arafa on 10/25/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());


            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
                scheduleJob();
            } else {
                // Handle message within 10 seconds
                handleNow();
            }

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            Log.d(TAG, UserData.getNotificationState(this) + "");
            if (UserData.getNotificationState(this)) {
                sendNotification(remoteMessage);
                Intent intent = new Intent("myFunction");
                // add data
                if (Localization.getCurrentLanguageID(getApplicationContext()) == Localization.ARABIC_VALUE) {
                    intent.putExtra("title", remoteMessage.getData().get("title"));
                    intent.putExtra("text", remoteMessage.getData().get("Message"));
                } else {
                    intent.putExtra("title", remoteMessage.getData().get("titleen"));
                    intent.putExtra("text", remoteMessage.getData().get("Messageen"));
                }
                intent.putExtra("image", remoteMessage.getData().get("img"));
                intent.putExtra("id", Integer.parseInt(remoteMessage.getData().get("DesID")));
                intent.putExtra("type", Integer.parseInt(remoteMessage.getData().get("type")));
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
            }
        }
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    // [END receive_message]

    /**
     * Schedule a job using FirebaseJobDispatcher.
     */
    private void scheduleJob() {
        // [START dispatch_job]
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));
        Job myJob = dispatcher.newJobBuilder()
                .setService(MyJobService.class)
                .setTag("my-job-tag")
                .build();
        dispatcher.schedule(myJob);
        // [END dispatch_job]
    }

    /**
     * Handle time allotted to BroadcastReceivers.
     */
    private void handleNow() {
        Log.d(TAG, "Short lived task is done.");
    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param notification FCM message body received.
     */
    private void sendNotification(RemoteMessage notification) {
        Intent intent;
        if (notification.getData().get("type").equals("1"))
            intent = new Intent(this, NotificationDetailActivity.class);
        else {
            intent = new Intent(this, EventDetailsActivity.class);

        }
        if (Localization.getCurrentLanguageID(getApplicationContext()) == Localization.ARABIC_VALUE) {
            intent.putExtra("title", notification.getData().get("title"));
            intent.putExtra("text", notification.getData().get("Message"));
        } else {
            intent.putExtra("title", notification.getData().get("titleen"));
            intent.putExtra("text", notification.getData().get("Messageen"));
        }
        intent.putExtra("image", notification.getData().get("img"));
        intent.putExtra("id", Integer.parseInt(notification.getData().get("DesID")));
        intent.putExtra("type", Integer.parseInt(notification.getData().get("type")));
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.ic_logo_notification)
                        .setColor(getResources().getColor(R.color.GreenLight))
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);
        if (Localization.getCurrentLanguageID(getApplicationContext()) == Localization.ARABIC_VALUE) {
            notificationBuilder.setContentTitle(notification.getData().get("title")).setContentText(notification.getData().get("Message"));
        } else {
            notificationBuilder.setContentTitle(notification.getData().get("titleen")).setContentText(notification.getData().get("Messageen"));
        }

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}