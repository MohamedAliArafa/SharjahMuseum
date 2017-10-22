package com.asgatech.sharjahmuseums.Tools.Connection;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;

import com.asgatech.sharjahmuseums.Models.AboutUsModel;
import com.asgatech.sharjahmuseums.Models.AddReviewRequest;
import com.asgatech.sharjahmuseums.Models.AllMuseumCategoryResponse;
import com.asgatech.sharjahmuseums.Models.AllSliderModel;
import com.asgatech.sharjahmuseums.Models.ContactUsModel;
import com.asgatech.sharjahmuseums.Models.DemoImage;
import com.asgatech.sharjahmuseums.Models.EducationListModel;
import com.asgatech.sharjahmuseums.Models.EventCategoryModel;
import com.asgatech.sharjahmuseums.Models.EventModel;
import com.asgatech.sharjahmuseums.Models.EventdetatailsResponceModel;
import com.asgatech.sharjahmuseums.Models.InsertDevicetokenRequestModel;
import com.asgatech.sharjahmuseums.Models.MuseumsDetailsModel;
import com.asgatech.sharjahmuseums.Models.NotificationListRequestModel;
import com.asgatech.sharjahmuseums.Models.NotificationListResponseModel;
import com.asgatech.sharjahmuseums.Models.PagingModel;
import com.asgatech.sharjahmuseums.Models.PlanYourVisitsModel;
import com.asgatech.sharjahmuseums.Models.ReviewVisitorsRequest;
import com.asgatech.sharjahmuseums.Models.ReviewVisitorsResponse;
import com.asgatech.sharjahmuseums.Models.SearchPagingModel;
import com.asgatech.sharjahmuseums.Models.UpdateRequestModel;
import com.asgatech.sharjahmuseums.Tools.LoadingTool.loadingDialog;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public class ServerTool {


    public interface ApiInterface {

        @GET(URLS.URL_ALL_SLIDER)
        Call<List<AllSliderModel>> getAllSlider(@Query("lang") int langauge);

        @POST(URLS.URL_ALL_MUSEUMS)
        Call<RealmList<MuseumsDetailsModel>> getAllMuseums(@Body PagingModel pagingModel);

        @POST(URLS.URL_ALL_SEARCH_MUSEUMS)
        Call<RealmList<MuseumsDetailsModel>> getMuseumWithSearch(@Body SearchPagingModel pagingModel);

        @GET(URLS.URL_MUSEUMS_DETAILS)
        Call<MuseumsDetailsModel> getMuseumsDetails(@Query("Musid") int MuseumsID, @Query("lang") int langauge);

        @POST(URLS.URL_GET_EVENTS)
        Call<List<EventModel>> getEvents(@Body JsonObject data);


        @GET(URLS.URL_GET_EVENTS_CATS)
        Call<List<EventCategoryModel>> getEventsCategories();


        @GET(URLS.URL_GET_EVENTS_DETAILS)
        Call<EventdetatailsResponceModel> getEventsDetails(@Query("eventid") int eventId, @Query("lang") int lang);

        @GET(URLS.URL_GET_DEMO)
        Call<List<DemoImage>> getDemo(@Query("lang") int lang);

        @GET(URLS.URL_GET_ABOUT_US)
        Call<AboutUsModel> getAbout(@Query("lang") int lang);


        @POST(URLS.URL_SEND_FEEDBACK)
        Call<Integer> sendFeedback(@Body JsonObject data);


        @GET(URLS.URL_GET_CONTACTS)
        Call<ContactUsModel> getContactData(@Query("lang") int lang);


        @GET(URLS.URL_GET_EDUCATION_LIST)
        Call<List<EducationListModel>> getEducationList(@Query("lang") int lang);

        @GET(URLS.URL_GET_PLAN_YOUR_VISITS_LIST)
        Call<PlanYourVisitsModel> getPlanVisits(@Query("lang") int lang);

        @POST(URLS.URL_VISITOR_REVIEWS)
        Call<List<ReviewVisitorsResponse>> getReviewList(@Body ReviewVisitorsRequest reviewVisitorsRequest);

        @POST(URLS.URL_ADD_REVIEW)
        Call<Integer> addReview(@Body AddReviewRequest addReviewRequest);

        @POST(URLS.URL_GET_NOTIFICATION_LIST)
        Call<List<NotificationListResponseModel>> getNotificationList(@Body NotificationListRequestModel notificationListRequestModel);

        @POST(URLS.URL_INSERT_DEVICE_Token)
        Call<Integer> insertDeviceToken(@Body InsertDevicetokenRequestModel insertDeviceToken);


        @POST(URLS.URL_UPDATE_NOTIFICATION_STATE)
        Call<Integer> UpdateNotificationList(@Body UpdateRequestModel updateRequestModel);

        @GET(URLS.URL_GET_ALLMUSEUM_CATEGORY)
        Call<RealmList<AllMuseumCategoryResponse>> getALLMuseumCategray(@Query("lang") int lang);

    }


    public static void getAllSlider(Context context, int langauge, final APICallBack apiCallBack) {
        final RetrofitTool retrofitTool = new RetrofitTool();
        Call call = retrofitTool.getAPIBuilder(URLS.URL_BASE).getAllSlider(langauge);
        makeRequest(context, call, apiCallBack, retrofitTool);
    }

    public static void getMuseumsDetails(Context context, int MuseumsID, int langauge, final APICallBack apiCallBack) {
        final RetrofitTool retrofitTool = new RetrofitTool();
        Call call = retrofitTool.getAPIBuilder(URLS.URL_BASE).getMuseumsDetails(MuseumsID, langauge);
        makeRequest(context, call, apiCallBack, retrofitTool);
    }

    public static void getAllMuseums(Context context, PagingModel pagingModel, final APICallBack apiCallBack) {
        Log.d("langaugeRequest", new Gson().toJson(pagingModel) + "");
        final RetrofitTool retrofitTool = new RetrofitTool();
        Call call = retrofitTool.getAPIBuilder(URLS.URL_BASE).getAllMuseums(pagingModel);
        makeRequest(context, call, apiCallBack, retrofitTool);
    }

    public static void getMuseumWithSearch(Context context, SearchPagingModel pagingModel, final APICallBack apiCallBack) {
        Log.d("langaugeRequest", new Gson().toJson(pagingModel) + "");
        final RetrofitTool retrofitTool = new RetrofitTool();
        Call call = retrofitTool.getAPIBuilder(URLS.URL_BASE).getMuseumWithSearch(pagingModel);
        RealmResults<MuseumsDetailsModel> model = Realm.getDefaultInstance().where(MuseumsDetailsModel.class).contains("Title", pagingModel.getKeyword()).findAll();
        if (model.isLoaded() && !model.isEmpty())
            apiCallBack.onSuccess(model);
        makeRequest(context, call, apiCallBack, retrofitTool);
    }

    public static void getEvents(Context context, int catId, int pageNumber, int pageSize, int langauge, final APICallBack apiCallBack) {
        final RetrofitTool retrofitTool = new RetrofitTool();
        JsonObject obj = new JsonObject();
        obj.addProperty("ID", catId);
        obj.addProperty("pagenumber", pageNumber);
        obj.addProperty("pagesize", pageSize);
        obj.addProperty("lang", langauge);
        Call call = retrofitTool.getAPIBuilder(URLS.URL_BASE).getEvents(obj);
        makeRequest(context, call, apiCallBack, retrofitTool);
    }

    public static void getEventsCategories(Context context, int langauge, final APICallBack apiCallBack) {
        final RetrofitTool retrofitTool = new RetrofitTool();
        JsonObject obj = new JsonObject();
        obj.addProperty("lang", langauge);
        Call call = retrofitTool.getAPIBuilder(URLS.URL_BASE).getEventsCategories();
        makeRequest(context, call, apiCallBack, retrofitTool);
    }

    public static void getEventsDetails(Context context, int eventId, int lang, final APICallBack apiCallBack) {
        final RetrofitTool retrofitTool = new RetrofitTool();
        JsonObject obj = new JsonObject();
        obj.addProperty("eventid", eventId);
        obj.addProperty("lang", lang);
        Call call = retrofitTool.getAPIBuilder(URLS.URL_BASE).getEventsDetails(eventId, lang);
        makeRequest(context, call, apiCallBack, retrofitTool);
    }

    public static void getDemo(Context context, int lang, final APICallBack apiCallBack) {
        final RetrofitTool retrofitTool = new RetrofitTool();
        JsonObject obj = new JsonObject();
        obj.addProperty("lang", lang);
        Call call = retrofitTool.getAPIBuilder(URLS.URL_BASE).getDemo(lang);
        makeRequest(context, call, apiCallBack, retrofitTool);
    }

    public static void getAbout(Context context, int lang, final APICallBack apiCallBack) {
        final RetrofitTool retrofitTool = new RetrofitTool();
        JsonObject obj = new JsonObject();
        obj.addProperty("lang", lang);
        Call call = retrofitTool.getAPIBuilder(URLS.URL_BASE).getAbout(lang);
        makeRequest(context, call, apiCallBack, retrofitTool);
    }

    public static void sendFeedback(Context context, String name, String email, String phone, String message, final APICallBack apiCallBack) {
        final RetrofitTool retrofitTool = new RetrofitTool();
        JsonObject obj = new JsonObject();
        obj.addProperty("Name", name);
        obj.addProperty("Email", email);
        obj.addProperty("Text", message);
        obj.addProperty("phone", phone);
        Call call = retrofitTool.getAPIBuilder(URLS.URL_BASE).sendFeedback(obj);
        makeRequest(context, call, apiCallBack, retrofitTool);
    }


    public static void getContactUs(Context context, int lang, final APICallBack apiCallBack) {
        final RetrofitTool retrofitTool = new RetrofitTool();
        Call call = retrofitTool.getAPIBuilder(URLS.URL_BASE).getContactData(lang);
        makeRequest(context, call, apiCallBack, retrofitTool);
    }

    public static void getEducationList(Context context, int lang, final APICallBack apiCallBack) {
        final RetrofitTool retrofitTool = new RetrofitTool();
        Call call = retrofitTool.getAPIBuilder(URLS.URL_BASE).getEducationList(lang);
        makeRequest(context, call, apiCallBack, retrofitTool);
    }

    public static void getPlanVisits(Context context, int lang, final APICallBack apiCallBack) {
        final RetrofitTool retrofitTool = new RetrofitTool();
        Call call = retrofitTool.getAPIBuilder(URLS.URL_BASE).getPlanVisits(lang);
        makeRequest(context, call, apiCallBack, retrofitTool);
    }

    public static void GetReviewList(Context context, ReviewVisitorsRequest reviewVisitorsRequest, final APICallBack apiCallBack) {
        //Show loading
        final Gson gson = new Gson();
        Log.d("getReviewListRequest", gson.toJson(reviewVisitorsRequest) + "");
        final RetrofitTool retrofitTool = new RetrofitTool();
        Call call = retrofitTool.getAPIBuilder(URLS.URL_BASE).getReviewList(reviewVisitorsRequest);
        makeRequest(context, call, apiCallBack, retrofitTool);
    }

    public static void AddReview(Context context, AddReviewRequest addReviewRequest, final APICallBack apiCallBack) {
        //Show loading
        final Gson gson = new Gson();
        Log.d("addReviewRequest", gson.toJson(addReviewRequest) + "");
        final RetrofitTool retrofitTool = new RetrofitTool();
        Call call = retrofitTool.getAPIBuilder(URLS.URL_BASE).addReview(addReviewRequest);
        makeRequest(context, call, apiCallBack, retrofitTool);
    }

    public static void GetNotificationList(Context context, NotificationListRequestModel notificationListRequestModel, final APICallBack apiCallBack) {
        //Show loading
        final Gson gson = new Gson();
        Log.d("getNotificationList", gson.toJson(notificationListRequestModel) + "");
        final RetrofitTool retrofitTool = new RetrofitTool();
        Call call = retrofitTool.getAPIBuilder(URLS.URL_BASE).getNotificationList(notificationListRequestModel);
        makeRequest(context, call, apiCallBack, retrofitTool);
    }

    public static void InsertDeviceToken(Context context, InsertDevicetokenRequestModel insertDevicetokenRequestModel, final APICallBack apiCallBack) {
        //Show loading
        final Gson gson = new Gson();
        Log.d("insertDeviceToken", gson.toJson(insertDevicetokenRequestModel) + "");
        final RetrofitTool retrofitTool = new RetrofitTool();
        Call call = retrofitTool.getAPIBuilder(URLS.URL_BASE).insertDeviceToken(insertDevicetokenRequestModel);
        makeRequest(context, call, apiCallBack, retrofitTool);
    }

    public static void UpdateNotificationList(Context context, UpdateRequestModel updateRequestModel, final APICallBack apiCallBack) {
        //Show loading
//        final Gson gson = new Gson();
//        Log.d("insertDeviceToken", gson.toJson(insertDevicetokenRequestModel) + "");
        final RetrofitTool retrofitTool = new RetrofitTool();
        Call call = retrofitTool.getAPIBuilder(URLS.URL_BASE).UpdateNotificationList(updateRequestModel);
        makeRequest(context, call, apiCallBack, retrofitTool);
    }

    public static void getALLMuseumCategory(Context context, int lang, final APICallBack apiCallBack) {
        final RetrofitTool retrofitTool = new RetrofitTool();
        Call call = retrofitTool.getAPIBuilder(URLS.URL_BASE).getALLMuseumCategray(lang);
        makeRequest(context, call, apiCallBack, retrofitTool);
    }

    private static <M> void makeRequest(final Context context, Call call, final APICallBack apiCallBack, final RetrofitTool retrofitTool) {
        final Dialog dialogsLoading = new loadingDialog().showDialog(context);
        call.enqueue(new RetrofitTool.APICallBack<M>() {
            @Override
            public void onSuccess(M response) {
                try {
                    apiCallBack.onSuccess(response);
                    if (response instanceof RealmObject) {
                        Realm.getDefaultInstance().beginTransaction();
                        Realm.getDefaultInstance().copyToRealmOrUpdate((RealmObject) response);
                        Realm.getDefaultInstance().commitTransaction();
                    }else if (response instanceof RealmList){
                        Realm.getDefaultInstance().beginTransaction();
                        Realm.getDefaultInstance().copyToRealmOrUpdate((RealmList) response);
                        Realm.getDefaultInstance().commitTransaction();
                    }
                    //Hide loading
                    if (dialogsLoading != null) {
                        dialogsLoading.dismiss();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (dialogsLoading != null) {
                        dialogsLoading.dismiss();
                    }
                }
            }

            @Override
            public void onFailed(int statusCode, ResponseBody responseBody) {
                //Hide loading
                try {
                    handleGeneralFailure(context, statusCode, responseBody, retrofitTool);
                    apiCallBack.onFailed(statusCode, responseBody);
                    if (dialogsLoading != null) {
                        dialogsLoading.dismiss();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private static void handleGeneralFailure(Context context, int statusCode, ResponseBody responseBody, RetrofitTool retrofitTool) {
        Retrofit retrofit = retrofitTool.getRetrofit(URLS.URL_BASE);
        Log.d("statusCode", statusCode + "");
    }

    public static interface APICallBack<T> {
        void onSuccess(T response);

        void onFailed(int statusCode, ResponseBody responseBody);

    }

}
