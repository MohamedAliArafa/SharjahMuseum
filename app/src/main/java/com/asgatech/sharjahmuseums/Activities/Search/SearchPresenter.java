package com.asgatech.sharjahmuseums.Activities.Search;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.asgatech.sharjahmuseums.R;

/**
 * Created by mohamed.arafa on 10/22/2017.
 */

public class SearchPresenter implements SearchContract.UserAction {

    private final FragmentManager mFragmentManager;
    private final SearchContract.ModelView mView;

    public SearchPresenter(FragmentManager mFragmentManager, SearchContract.ModelView mView) {
        this.mFragmentManager = mFragmentManager;
        this.mView = mView;
    }

    @Override
    public void search(int catID, String keyword) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, SearchMuseumResultFragment.newInstance(catID, keyword));
        transaction.commit();
    }
}
