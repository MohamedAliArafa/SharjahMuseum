package com.asgatech.sharjahmuseums.Activities.Events.EventDetails;

/**
 * Created by mohamed.arafa on 11/1/2017.
 */

public interface EventDetailsContract {
    interface ModelView {
        void updateUI();
    }
    interface UserAction {
        void getData();
        void DownloadFile();
    }
}
