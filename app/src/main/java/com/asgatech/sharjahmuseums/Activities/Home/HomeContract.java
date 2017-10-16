package com.asgatech.sharjahmuseums.Activities.Home;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by mohamed.arafa on 10/16/2017.
 */

public interface HomeContract {
    interface ModelView {
        void initView();

        void setupNavigationDrawer();

        void setupToolbar();

        void closeDrawer();

        void showLoading();

        void hideLoading();

        void changeToolbarTitle(String title);

        void openFragmentFromChild(Fragment fragment, Bundle bundle);

        void hideLogo();

        void showLogo();
    }

    interface UserAction {
        void openFragment(Fragment fragment, Bundle bundle);
    }
}
