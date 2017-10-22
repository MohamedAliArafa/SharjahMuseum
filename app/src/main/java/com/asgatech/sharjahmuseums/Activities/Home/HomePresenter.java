package com.asgatech.sharjahmuseums.Activities.Home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.Connection.ConstantUtils;

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
