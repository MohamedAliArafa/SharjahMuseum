package com.asgatech.sharjahmuseums.Activities.Events;

import com.asgatech.sharjahmuseums.Models.EventCategoryModel;
import com.asgatech.sharjahmuseums.Models.EventModel;
import com.asgatech.sharjahmuseums.Models.NewResponse;

import java.util.List;

/**
 *
 * Created by mohamed.arafa on 10/26/2017.
 */
interface EventsContract {
    interface ModelView {
        void updateView(List<EventModel> models, List<EventCategoryModel> categoryModels);
        void updateViews(List<NewResponse> models, List<EventCategoryModel> categoryModels);
        void showList();
        void hideList();
    }

    interface UserActions {

    }
}
