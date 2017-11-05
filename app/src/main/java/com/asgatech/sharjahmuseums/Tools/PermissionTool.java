package com.asgatech.sharjahmuseums.Tools;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

/**
 * Created by halima.reda on 4/12/2016.
 */


public class PermissionTool {
    // to handel permissiom v6
    public static final String PERMISSION_WRITE_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    public static final String PERMISSION_READ_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
    public static final String PERMISSION_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    public static final String PERMISSION_location_COARSE = Manifest.permission.ACCESS_COARSE_LOCATION;
    public static final String PERMISSION_PHONE_CALL = Manifest.permission.CALL_PHONE;
    public static final String PERMISSION_CALENDER = Manifest.permission.WRITE_CALENDAR;

    public static boolean checkAllPermission(Activity context, String[] permissions) {
        boolean allPermissionGranted = true;
        for (int i = 0; i < permissions.length; i++) {
            if (!isPermissionGranted(context, permissions[i])) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(context, permissions[i])) {
                    Toast.makeText(context, "I need this permission:" + permissions[i], Toast.LENGTH_SHORT).show();
                }
                allPermissionGranted = false;
            }
        }
        if (!allPermissionGranted) {
            ActivityCompat.requestPermissions(context, permissions, 1);
            return false;
        } else {
            return true;
        }
    }

    public static boolean checkPermission(final Activity context, final String permission) {
        if (!isPermissionGranted(context, permission)) {
//            if (ActivityCompat.shouldShowRequestPermissionRationale(context, permission)) {
//                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
//                alertDialog.setTitle("Permission Request");
//                alertDialog.setMessage("Access Permission");
//                alertDialog.setPositiveButton("OK", (dialog, which) -> {
//                    Toast.makeText(context, "I need this permission", Toast.LENGTH_SHORT).show();
//                    ActivityCompat.requestPermissions(context, new String[]{permission}, 1);
//                    dialog.dismiss();
//                });
//                alertDialog.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
//                AlertDialog alert11 = alertDialog.create();
//                alert11.show();
////                 Show an expanation to the user *asynchronously* -- don't block
////                 this thread waiting for the user's response! After the user
////                 sees the explanation, try again to request the permission.
//
//            } else {
                ActivityCompat.requestPermissions(context, new String[]{permission}, 1);
//            }
            return false;
        } else {
            return true;
        }
    }

    private static boolean isPermissionGranted(Context context, String permission) {
        return ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }
}
