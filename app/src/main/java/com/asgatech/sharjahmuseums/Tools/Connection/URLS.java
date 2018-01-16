package com.asgatech.sharjahmuseums.Tools.Connection;

/**
 * Created by halima.reda on 12/6/2016.
 */
public class URLS {

//    public static final String URL_BASE = "http://108.179.204.213:8071/"; // online
//    public static final String URL_BASE = "http://23.236.154.106:8070/"; // online
    public static final String URL_BASE = "http://23.236.154.106:8075/"; // Testing


//    public static final String URL_BASE = "http://192.168.20.86:8070/"; // local

    public static final String TAG_ARABIC_String = "ar";
    public static final String TAG_ENGLISH_String = "en";

    public static final String URL_ALL_SLIDER = "api/Slider/GetAllSlider";
    public static final String URL_ALL_MUSEUMS = "api/Museum/GetALLMuseums";
    public static final String URL_ALL_SEARCH_MUSEUMS = "/api/Museum/GetALLMuseumsWithsearch";
    public static final String URL_MUSEUMS_DETAILS = "api/Museum/GetMuseumById";
    public static final String URL_GET_EVENTS = "api/Events/getAllEvents";
    public static final String URL_GET_EVENTS_CATS = "api/EventCategray/GetEventCategrayList";
    public static final String URL_GET_EVENTS_DETAILS = "api/Events/GetEventById";
    public static final String URL_GET_DEMO = "/api/Demo/GetAllDemo";
    public static final String URL_GET_ABOUT_US = "api/Settings/GetAboutUs";
    public static final String URL_SEND_FEEDBACK = "api/Settings/FeedBack";
    public static final String URL_GET_CONTACTS = "api/Settings/GetContactUs";
    public static final String URL_GET_EDUCATION_LIST = "api/Education/GetAllEducation";
    public static final String URL_GET_PLAN_YOUR_VISITS_LIST = "api/PlaneViste/GetPlaneVisteList";
    public static final String URL_VISITOR_REVIEWS = "api/Reviews/GetAllMuseumReviews";
    public static final String URL_ADD_REVIEW = "api/Reviews/AddReviews";
    public static final String URL_GET_NOTIFICATION_LIST = "api/Notfcation/NotfactionList";
    public static final String URL_INSERT_DEVICE_Token = "/api/Notfcation/InsertDevicetoken";
    public static final String URL_UPDATE_NOTIFICATION_STATE = "/api/Notfcation/UpdateSate";
    public static final String URL_GEOFENCING_LIST = "api/Geofinance/GetGeofinceList";
    public static final String URL_DATE_LIST = "api/Events/GetEventListByDateAndCatId";

    public static final String URL_GET_ALLMUSEUM_CATEGORY = "api/MuseumCategray/GetALLMuseumCategray";
//    api/MuseumCategray/GetALLMuseumCategray?lang=1

}
