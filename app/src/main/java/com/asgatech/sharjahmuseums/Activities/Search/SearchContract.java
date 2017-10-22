package com.asgatech.sharjahmuseums.Activities.Search;

/**
 * Created by mohamed.arafa on 10/22/2017.
 */
public interface SearchContract {
    interface ModelView {
        UserAction getPresenter();
    }

    interface UserAction {
        void search(int catID, String keyword);
    }
}
