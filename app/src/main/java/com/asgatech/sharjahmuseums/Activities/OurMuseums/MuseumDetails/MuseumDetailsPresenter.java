package com.asgatech.sharjahmuseums.Activities.OurMuseums.MuseumDetails;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;

import com.asgatech.sharjahmuseums.Models.MuseumsDetailsModel;
import com.asgatech.sharjahmuseums.Tools.Connection.ServerTool;
import com.asgatech.sharjahmuseums.Tools.SharedTool.UserData;
import com.asgatech.sharjahmuseums.Tools.Utils;

import io.realm.Realm;
import okhttp3.ResponseBody;

/*
 * Created by mohamed.arafa on 10/24/2017.
 */

public class MuseumDetailsPresenter implements MuseumDetailsContract.UserAction, LifecycleObserver {

    private MuseumDetailsContract.ModelView mView;
    private Activity mContext;
    private int mMuseumId;
    private int mLanguage;

    MuseumDetailsPresenter(MuseumDetailsContract.ModelView mView, Activity mContext, Lifecycle lifecycle, int mMuseumId) {
        this.mView = mView;
        lifecycle.addObserver(this);
        this.mContext = mContext;
        this.mMuseumId = mMuseumId;
        mLanguage = UserData.getLocalization(mContext);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    @Override
    public void getDate() {
        MuseumsDetailsModel model = Realm.getDefaultInstance().where(MuseumsDetailsModel.class)
                .equalTo("Mus_ID", mMuseumId).findFirst();
        if (model != null)
            mView.updateUI(model);
        ServerTool.getMuseumsDetails(mContext, mMuseumId, mLanguage, new ServerTool.APICallBack<MuseumsDetailsModel>() {
            @Override
            public void onSuccess(MuseumsDetailsModel response) {
                if (Utils.validObject(response)) {
                    mView.updateUI(response);
                }
            }

            @Override
            public void onFailed(int statusCode, ResponseBody responseBody) {
            }
        });
    }
}
