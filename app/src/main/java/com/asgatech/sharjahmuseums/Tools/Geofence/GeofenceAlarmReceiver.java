package com.asgatech.sharjahmuseums.Tools.Geofence;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

/**
 * Created by mohamed.arafa on 11/7/2017.
 */

public class GeofenceAlarmReceiver extends WakefulBroadcastReceiver {
    // The app's AlarmManager, which provides access to the system alarm services.
    public AlarmManager alarmMgr;
    // The pending intent that is triggered when the alarm fires.
    private PendingIntent alarmIntent;
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("onReceive", "onReceive");
        Intent service = new Intent(context, GeofenceTransitionsIntentService.class);
        startWakefulService(context, service);
    }
    public void setAlarm(Context context) {
        alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, GeofenceAlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        /// just
        alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                1800000, 1800000, alarmIntent);
//        alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP,
//                1800000, 1800000, alarmIntent);
        ComponentName receiver = new ComponentName(context, SampleBootReceiver.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }
    // END_INCLUDE(set_alarm)

    /**
     * Cancels the alarm.
     *
     * @param context
     */
    // BEGIN_INCLUDE(cancel_alarm)
    public void cancelAlarm( Context context) {
        // If the alarm has been set, cancel it.
        if (alarmMgr != null) {
            Log.d("if", "if");
            alarmMgr.cancel(alarmIntent);
            alarmIntent.cancel();
        }else {
            Log.d("else", "else");
        }

        // Disable {@code SampleBootReceiver} so that it doesn't automatically restart the
        // alarm when the device is rebooted.
        ComponentName receiver = new ComponentName(context, SampleBootReceiver.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }
    // END_INCLUDE(cancel_alarm)
}
