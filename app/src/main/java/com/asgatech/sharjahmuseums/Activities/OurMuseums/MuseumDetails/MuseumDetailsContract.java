package com.asgatech.sharjahmuseums.Activities.OurMuseums.MuseumDetails;

import com.asgatech.sharjahmuseums.Models.MuseumsDetailsModel;

/**
 * Created by mohamed.arafa on 10/24/2017.
 */

public interface MuseumDetailsContract {
    interface ModelView {
        void updateUI(MuseumsDetailsModel model);
    }

    interface UserAction {
        void getDate();
    }
}
