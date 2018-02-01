package com.asgatech.sharjahmuseums.Activities.Events;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asgatech.sharjahmuseums.Adapters.EventsAdapter;
import com.asgatech.sharjahmuseums.Models.EventCategoryModel;
import com.asgatech.sharjahmuseums.Models.EventModel;
import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.DialogTool.NoDataDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventsListFragment extends Fragment implements EventsContract.ModelView {


    protected final String TAG = getClass().getSimpleName();

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    EventsAdapter mAdapter;

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
        mAdapter = new EventsAdapter(getActivity(), null);
        recyclerView.setAdapter(mAdapter);
        return view;
    }

    public void setData(List<EventModel> list) {
        if (list.isEmpty()) {
            new NoDataDialog().showDialog(getContext());
//            setData(Realm.getDefaultInstance()
//                    .where(EventModel.class)
//                    .findAll());
            ((EventsParentContract.ModelView) getParentFragment()).hideList();
        } else if (mAdapter != null) {
            mAdapter.updateSet(list);
            ((EventsParentContract.ModelView) getParentFragment()).showList();
        }
    }

    @Override
    public void updateView(List<EventModel> models, List<EventCategoryModel> categoryModels) {
        setData(models);
    }

    @Override
    public void showList() {
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideList() {
        recyclerView.setVisibility(View.GONE);
    }
}
