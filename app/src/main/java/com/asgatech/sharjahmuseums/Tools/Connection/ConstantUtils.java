package com.asgatech.sharjahmuseums.Tools.Connection;

import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;

/**
 * Created by halima.reda on 9/12/2017.
 */

public class ConstantUtils {
    public static  final String EXTRA_MUSEUMS_ID = "museums_id";
    public static  final String EXTRA_MUSEUMS_LONGTUDE = "longtude_musem";
    public static  final String EXTRA_MUSEUMS_lATITUDE = "latitude_musem";
    public static final String HIGHLIGHT_LIST = "highLightList";
    public static final String HIGHLIGHT_URL = "url";
    public static final String HIGHLIGHT_LIST_POSITION = "highLightList_position";
    public static final String HIGHLIGHT_COLOR = "highLightList_color";
    public static final String IMAGE_PATH = "IMAGE_PATH";
    public static final String MUSEUM_COLOR = "color";
    public static final String MUSEUM_TITLE = "museumTitle";
    public static final String MUSEUM_IMAGE = "museumImage";


    public static final String GLIDE_TIMEOUT = "com.bumptech.glide.load.model.stream.HttpGlideUrlLoader.Timeout";

    public static String HOMEPAGE_FRAGMENT_KEY = "home_page_fragment_key";

    private static final String PACKAGE_NAME = "com.asgatech.sharjahmuseums.Tools.GeoFence";
    public static final String GEOFENCES_ADDED_KEY = PACKAGE_NAME + ".GEOFENCES_ADDED_KEY";
    /**
     * Used to set an expiration time for a geofence. After this amount of time Location Services
     * stops tracking the geofence.
     */
    private static final long GEOFENCE_EXPIRATION_IN_HOURS = 12;
    /**
     * For this sample, geofences expire after twelve hours.
     */
    public static final long GEOFENCE_EXPIRATION_IN_MILLISECONDS =
            GEOFENCE_EXPIRATION_IN_HOURS * 60 * 60 * 1000;
    public static final float GEOFENCE_RADIUS_IN_METERS = 1609; // 1 mile, 1.6 km
    /**
     * Map for storing information about airports in the San Francisco bay area.
     */
    public static final HashMap<String, LatLng> BAY_AREA_LANDMARKS = new HashMap<>();

    static {
        // San Francisco International Airport.
        BAY_AREA_LANDMARKS.put("SFO", new LatLng(30.7908467, 31.0139363));

        // Googleplex.
        BAY_AREA_LANDMARKS.put("GOOGLE", new LatLng(30.7909757,31.0101383));
    }
}
