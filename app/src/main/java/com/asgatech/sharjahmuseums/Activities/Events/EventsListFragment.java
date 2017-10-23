package com.asgatech.sharjahmuseums.Activities.Events;


import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.asgatech.sharjahmuseums.Activities.Home.HomeActivity;
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
import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
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
    EditText mSearchEditText;

    @BindView(R.id.loading_layout)
    View loadingView;

//    private final StateMaintainer mStateMaintainer =
//    new StateMaintainer(this.getFragmentManager(), TAG);


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    EventsAdapter mAdapter;

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
        mAdapter = new EventsAdapter(getActivity(), Realm.getDefaultInstance().where(EventModel.class).findAll());
        recyclerView.setAdapter(mAdapter);
        mSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty())
                    setData(Realm.getDefaultInstance().where(EventModel.class).findAll());
                else
                    setData(Realm.getDefaultInstance().where(EventModel.class).contains("title", s.toString(), Case.INSENSITIVE).findAll());
            }
        });
        ((HomeActivity) getActivity()).changeToolbarTitle(getString(R.string.Events));
        assignControls();
        getEventCategories(UserData.getLocalization(getActivity()));
        return view;
    }


    private void getEvents(int catId, int pageNumber, int pageSize, int langauge) {

        ServerTool.getEvents(getActivity(), catId, pageNumber, pageSize, langauge, new ServerTool.APICallBack<RealmList<EventModel>>() {
            @Override
            public void onSuccess(RealmList<EventModel> response) {
                if (Utils.validList(response)) {
                    Realm.getDefaultInstance().beginTransaction();
                    Realm.getDefaultInstance().copyToRealmOrUpdate(response);
                    Realm.getDefaultInstance().commitTransaction();
                    if (catId == 0)
                        setData(Realm.getDefaultInstance().where(EventModel.class).findAll());
                    else
                        setData(Realm.getDefaultInstance().where(EventModel.class).equalTo("catId", catId).findAll());
                }
            }

            @Override
            public void onFailed(int statusCode, ResponseBody responseBody) {
                Toast.makeText(getContext(), "Error: " + responseBody.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getEventCategories(final int language) {

        ServerTool.getEventsCategories(getActivity(), language, new ServerTool.APICallBack<List<EventCategoryModel>>() {
            @Override
            public void onSuccess(List<EventCategoryModel> response) {
                if (Utils.validList(response)) {
                    if (isAdded())
                        setCategoryData(response);
                    if (response.size() > 0) {
                        if (isAdded())
                            getEvents(0, 1, 15, language);
                    }
                }
            }

            @Override
            public void onFailed(int statusCode, ResponseBody responseBody) {
            }
        });
    }

    private void setData(RealmResults<EventModel> response) {
        if (mAdapter != null)
            mAdapter.updateSet(response);
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
            itemView.setOnClickListener(view -> {
                mainFilterLayout.setVisibility(View.GONE);
                getEvents(Integer.parseInt(itemView.getTag().toString()), 1, 15, UserData.getLocalization(getActivity()));
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
        itemView.setOnClickListener(view -> {
            mainFilterLayout.setVisibility(View.GONE);
            getEvents(0, 1, 15, new UserData().getLocalization(getActivity()));
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
                ((HomeActivity) getActivity()).openFragmentFromChild(new EventCalenderFragment(), null);
                break;
        }
    }
}
