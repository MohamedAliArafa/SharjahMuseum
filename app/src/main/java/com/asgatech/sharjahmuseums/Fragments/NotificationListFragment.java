package com.asgatech.sharjahmuseums.Fragments;

import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asgatech.sharjahmuseums.Activities.HomeActivity;
import com.asgatech.sharjahmuseums.Adapters.NotificationListAdapter;
import com.asgatech.sharjahmuseums.Adapters.ViewVisitorsReviewAdapter;
import com.asgatech.sharjahmuseums.Models.NotificationListRequestModel;
import com.asgatech.sharjahmuseums.Models.NotificationListResponseModel;
import com.asgatech.sharjahmuseums.Models.ReviewVisitorsResponse;
import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.Connection.ServerTool;
import com.asgatech.sharjahmuseums.Tools.CustomFonts.TextViewBold;
import com.asgatech.sharjahmuseums.Tools.SharedTool.UserData;

import java.util.List;

import okhttp3.ResponseBody;

/**
 * Created by Esraa on 10/5/2017.
 */

public class NotificationListFragment extends Fragment {

    private RecyclerView notificationRecycler;
    private TextViewBold notifiListErrorMessage;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView.Adapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notifications_list, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerView(view);
        ((HomeActivity)getActivity()).ToolbarTitleTextView.setText(getString(R.string.notifications));
        String androidID = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
//        AddDeviceModel addDeviceModel = new AddDeviceModel(androidID, FirebaseInstanceId.getInstance().getToken());

        NotificationListRequestModel notificationListRequestModel=new NotificationListRequestModel(new UserData().getLocalization(getActivity()),androidID);

        GetNotificationList(notificationListRequestModel);

    }

    private void initRecyclerView(View view) {
        notificationRecycler = (RecyclerView) view.findViewById(R.id.notification_recycler);
        notifiListErrorMessage = (TextViewBold) view.findViewById(R.id.notifi_list_error_message);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        notificationRecycler.setLayoutManager(linearLayoutManager);
        notificationRecycler.setItemAnimator(new DefaultItemAnimator());
        notificationRecycler.setNestedScrollingEnabled(false);
        notificationRecycler.setHasFixedSize(true);
        linearLayoutManager.scrollToPosition(0);

    }

    public void GetNotificationList(NotificationListRequestModel notificationListRequestModel) {
        ServerTool.GetNotificationList(getActivity(), notificationListRequestModel, new ServerTool.APICallBack<List<NotificationListResponseModel>>() {
            @Override
            public void onSuccess(List<NotificationListResponseModel> response) {
                if (response.size()!=0){
                    setData(response);
                }else {
                    notificationRecycler.setVisibility(View.GONE);
                    notifiListErrorMessage.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailed(int statusCode, ResponseBody responseBody) {

            }

        });
    }

    private void setData(final List<NotificationListResponseModel> data) {
        adapter = new NotificationListAdapter(getActivity(),data);
        notificationRecycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onResume() {
        super.onResume();
        String androidID = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
//        AddDeviceModel addDeviceModel = new AddDeviceModel(androidID, FirebaseInstanceId.getInstance().getToken());
        NotificationListRequestModel notificationListRequestModel=new NotificationListRequestModel(new UserData().getLocalization(getActivity()),androidID);
        GetNotificationList(notificationListRequestModel);
    }
}