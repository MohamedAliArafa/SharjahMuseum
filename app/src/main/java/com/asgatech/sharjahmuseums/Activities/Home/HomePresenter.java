package com.asgatech.sharjahmuseums.Activities.Home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.asgatech.sharjahmuseums.R;

/**
 * Created by mohamed.arafa on 10/16/2017.
 */

public class HomePresenter implements HomeContract.UserAction {

    private final FragmentManager mFragmentManager;
    private final HomeContract.ModelView mView;

    public HomePresenter(HomeContract.ModelView view, FragmentManager fragmentManager) {
        this.mFragmentManager = fragmentManager;
        this.mView = view;
    }

    @Override
    public void openFragment(Fragment fragment, Bundle bundle) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        transaction.replace(R.id.content_main, fragment);
        transaction.commit();
        mView.closeDrawer();
        mView.hideLogo();
    }
}
