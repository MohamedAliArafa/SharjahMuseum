package com.asgatech.sharjahmuseums.Activities.Home;

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

import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.Connection.ConstantUtils;
import com.asgatech.sharjahmuseums.Tools.DialogTool.NotificationDialog;

/**
 * Created by mohamed.arafa on 10/16/2017.
 */

public class HomePresenter implements HomeContract.UserAction, LifecycleObserver {

    private final FragmentManager mFragmentManager;
    private final HomeContract.ModelView mView;

    public HomePresenter(HomeContract.ModelView view, Lifecycle lifecycle, FragmentManager fragmentManager) {
        this.mFragmentManager = fragmentManager;
        this.mView = view;
        lifecycle.addObserver(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void resume() {
        LocalBroadcastManager.getInstance(mView.getContext()).registerReceiver(mMessageReceiver,
                new IntentFilter("myFunction"));
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void pause() {
        LocalBroadcastManager.getInstance(mView.getContext()).unregisterReceiver(mMessageReceiver);
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Extract data included in the Intent
            int id = intent.getIntExtra("id", 0);
            int type = intent.getIntExtra("type", 0);
            String title = intent.getStringExtra("title");
            String desc = intent.getStringExtra("text");
            //alert data here
            new NotificationDialog().showDialog(mView.getContext(), id, type, title, desc);
        }
    };


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
