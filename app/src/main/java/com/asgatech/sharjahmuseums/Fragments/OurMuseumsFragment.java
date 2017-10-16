package com.asgatech.sharjahmuseums.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asgatech.sharjahmuseums.Adapters.AllMuseumsAdapter;
import com.asgatech.sharjahmuseums.Models.ALLMuseumsModel;
import com.asgatech.sharjahmuseums.Models.PagingModel;
import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.Connection.ServerTool;
import com.asgatech.sharjahmuseums.Tools.SharedTool.UserData;
import com.asgatech.sharjahmuseums.Tools.Utils;

import java.util.List;

import okhttp3.ResponseBody;

/**
 * A simple {@link Fragment} subclass.
 */
public class OurMuseumsFragment extends Fragment {
    private RecyclerView ourMuseumsRecyclerView;
    private PagingModel pagingModel;

    public OurMuseumsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_our_museums, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViews(view);
    }

    private void setupViews(View view) {
        ourMuseumsRecyclerView = (RecyclerView) view.findViewById(R.id.our_museums_recycler_view);
        ourMuseumsRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        ourMuseumsRecyclerView.setLayoutManager(mLayoutManager);
        pagingModel = new PagingModel(1, 1000, new UserData().getLocalization(getActivity()));
        getAllMuseums(pagingModel);
    }

    private void getAllMuseums(PagingModel pagingModel) {
        ServerTool.getAllMuseums(getActivity(), pagingModel, new ServerTool.APICallBack<List<ALLMuseumsModel>>() {
            @Override
            public void onSuccess(List<ALLMuseumsModel> response) {
                if (Utils.validList(response)) {
                    AllMuseumsAdapter museumsAdapter = new AllMuseumsAdapter(getActivity(), response);
                    ourMuseumsRecyclerView.setAdapter(museumsAdapter);
                }

            }

            @Override
            public void onFailed(int statusCode, ResponseBody responseBody) {
            }
        });
    }

}
