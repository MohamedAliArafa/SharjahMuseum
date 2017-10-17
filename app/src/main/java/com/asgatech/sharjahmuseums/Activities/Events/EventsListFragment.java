package com.asgatech.sharjahmuseums.Activities.Events;


import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asgatech.sharjahmuseums.Activities.Home.HomeActivity;
import com.asgatech.sharjahmuseums.Adapters.AutoCompleteAdapter;
import com.asgatech.sharjahmuseums.Adapters.EventsAdapter;
import com.asgatech.sharjahmuseums.Models.EventCategoryModel;
import com.asgatech.sharjahmuseums.Models.EventModel;
import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.CircleImageView;
import com.asgatech.sharjahmuseums.Tools.Connection.ServerTool;
import com.asgatech.sharjahmuseums.Tools.SharedTool.UserData;
import com.asgatech.sharjahmuseums.Tools.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventsListFragment extends Fragment implements View.OnClickListener {


    protected final String TAG = getClass().getSimpleName();
    @BindView(R.id.filter_layout)
    LinearLayout filterLayout;

    @BindView(R.id.main_filter_layout)
    LinearLayout mainFilterLayout;

    @BindView(R.id.event_switch_to_calender)
    ImageView switchToCalender;

    @BindView(R.id.auto_complete_search_event)
    AutoCompleteTextView autoCompleteSearchView;

    @BindView(R.id.loading_layout)
    View loadingView;

//    private final StateMaintainer mStateMaintainer =
//            new StateMaintainer(this.getFragmentManager(), TAG);


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;


    @BindView(R.id.arrowIV)
    ImageView arrowImg;

    public EventsListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_events_list, container, false);
        ButterKnife.bind(this, view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ((HomeActivity) getActivity()).changeToolbarTitle(getString(R.string.Events));
        assignControls();
        getEventCategories(new UserData().getLocalization(getActivity()));
        return view;
    }


    private void getEvents(int catId, int pageNumber, int pageSize, int langauge) {

        ServerTool.getEvents(getActivity(), catId, pageNumber, pageSize, langauge, new ServerTool.APICallBack<List<EventModel>>() {
            @Override
            public void onSuccess(List<EventModel> response) {
                if (Utils.validList(response)) {
                    setData(response);
                }
            }

            @Override
            public void onFailed(int statusCode, ResponseBody responseBody) {
            }
        });
    }

    private void getEventCategories(final int langauge) {

        ServerTool.getEventsCategories(getActivity(), langauge, new ServerTool.APICallBack<List<EventCategoryModel>>() {
            @Override
            public void onSuccess(List<EventCategoryModel> response) {
                if (Utils.validList(response)) {
                    if (isAdded())
                        setCategoryData(response);
                    if (response.size() > 0) {
                        if (isAdded())
                            getEvents(0, 1, 15, langauge);
                    }
                }
            }

            @Override
            public void onFailed(int statusCode, ResponseBody responseBody) {
            }
        });
    }

    private void setData(List<EventModel> response) {
        EventsAdapter adapter = new EventsAdapter(getActivity(), response);
        recyclerView.setAdapter(adapter);
        autoCompleteSearchView.setThreshold(1);
        autoCompleteSearchView.setAdapter(new AutoCompleteAdapter(getActivity(), R.layout.custom_text_view, response));
    }

    private void setCategoryData(List<EventCategoryModel> list) {
        addReset();
        for (int i = 0; i < list.size(); i++) {
            final View itemView = LayoutInflater.from(getActivity())
                    .inflate(R.layout.filter_recycler_row, null);
            CircleImageView pallete = itemView.findViewById(R.id.pallete_for_filter_item);
            TextView name = itemView.findViewById(R.id.name_for_filter_item);
//            pallete.setSolidColor(list.get(i).getColor()); //throws exception becouse server data is dummy
            name.setText(list.get(i).getTitle().trim());
            itemView.setTag(list.get(i).getEventCatID());
            Drawable background = pallete.getBackground();
            if (list.get(i).getColor() != null) {
                Log.e("colorCode", list.get(i).getTitle() + ":" + list.get(i).getColor());
                background.setColorFilter(Color.parseColor(list.get(i).getColor()), PorterDuff.Mode.SRC_IN);
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mainFilterLayout.setVisibility(View.GONE);
                    getEvents(Integer.parseInt(itemView.getTag().toString()), 1, 15, new UserData().getLocalization(getActivity()));
                }
            });
            //otherwise throw exception java.lang.IllegalStateException: The specified
            // child already has a parent. You must call removeView() on the child's parent first.
            if (itemView.getParent() != null) {
                ((ViewGroup) itemView.getParent()).removeView(itemView);
            }
            mainFilterLayout.addView(itemView);
        }
    }

    private void addReset() {
        final View itemView = LayoutInflater.from(getActivity())
                .inflate(R.layout.filter_recycler_row, null);
        CircleImageView pallete = itemView.findViewById(R.id.pallete_for_filter_item);
        TextView name = itemView.findViewById(R.id.name_for_filter_item);
        name.setText("All");
        itemView.setTag("#000000");
        Drawable background = pallete.getBackground();
        Log.e("colorCode", "#000000");
        background.setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_IN);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainFilterLayout.setVisibility(View.GONE);
                getEvents(0, 1, 15, new UserData().getLocalization(getActivity()));
            }
        });
        //otherwise throw exception java.lang.IllegalStateException: The specified
        // child already has a parent. You must call removeView() on the child's parent first.
        if (itemView.getParent() != null) {
            ((ViewGroup) itemView.getParent()).removeView(itemView);
        }
        mainFilterLayout.addView(itemView);
    }

    private void assignControls() {
        filterLayout.setOnClickListener(this);
        switchToCalender.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.filter_layout:

                if (mainFilterLayout.getVisibility() == View.VISIBLE) {
                    mainFilterLayout.setVisibility(View.GONE);

                } else {
                    mainFilterLayout.setVisibility(View.VISIBLE);
                }
                if (arrowImg.getRotation() == 0) {
                    arrowImg.setRotation(180);
                } else {
                    arrowImg.setRotation(arrowImg.getRotation() + 180);
                }
                break;
            case R.id.event_switch_to_calender:
//                getActivity().getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.mainContainer , new EventCalenderFragment())
//                        .addToBackStack(null)
//                        .commit();

                ((HomeActivity) getActivity()).openFragmentFromChild(new EventCalenderFragment(), null);
                break;

//            case R.id.event_layout:
//                Toast.makeText(getActivity(), "Events", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.exhibition_layout:
//                Toast.makeText(getActivity(), "exhibition_layout", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.programmes_layout:
//                Toast.makeText(getActivity(), "programmes_layout", Toast.LENGTH_SHORT).show();
//                break;
        }
    }
}
