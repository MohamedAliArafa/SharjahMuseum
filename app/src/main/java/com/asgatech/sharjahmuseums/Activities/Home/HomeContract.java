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

        void closeDrawer();

        void changeToolbarTitle(String title);

        void changeToolbarColor(String color);

        void restToolbarColor();

        void openFragmentFromChild(Fragment fragment, Bundle bundle);

        void hideLogo();

        void showLogo();

        HomeContract.UserAction getPresenter();

        void hideToolbar();

        void showToolbar();
    }

    interface UserAction {
        void openFragment(Fragment fragment, Bundle bundle);

        void openHome();
    }
}
