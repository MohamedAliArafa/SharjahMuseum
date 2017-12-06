package com.asgatech.sharjahmuseums.Activities.Events;

import com.asgatech.sharjahmuseums.Models.EventModel;

import java.util.List;

/**
 * Created by mohamed.arafa on 10/26/2017.
 */
interface EventsParentContract {
    interface ModelView {
        void openList(List<EventModel> models);
        void setBundle(boolean bundle);
        void setDate(String date);
    }

    interface UserActions {

    }
}
