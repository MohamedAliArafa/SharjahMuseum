package com.asgatech.sharjahmuseums.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asgatech.sharjahmuseums.Activities.Home.HomeActivity;
import com.asgatech.sharjahmuseums.Adapters.EducationAdapter;
import com.asgatech.sharjahmuseums.Models.EducationListModel;
import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.Connection.ServerTool;
import com.asgatech.sharjahmuseums.Tools.SharedTool.UserData;
import com.asgatech.sharjahmuseums.Tools.Utils;

import java.util.List;

import okhttp3.ResponseBody;

/**
 * A simple {@link Fragment} subclass.
 */
public class EducationListFragment extends Fragment {


    RecyclerView recyclerView;


    public EducationListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_education_list, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
//        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        ((HomeActivity) getActivity()).changeToolbarTitle(getString(R.string.education));
        if (Utils.isNetworkAvailable(getActivity())) {
            getEducationList(UserData.getLocalization(getActivity()));
        }
        return view;
    }


    private void getEducationList(int lang) {
        ServerTool.getEducationList(getActivity(), lang, new ServerTool.APICallBack<List<EducationListModel>>() {
            @Override
            public void onSuccess(List<EducationListModel> response) {
                if (Utils.validList(response)) {

                    recyclerView.setAdapter(new EducationAdapter(getActivity(), response, recyclerView));
                }
            }

            @Override
            public void onFailed(int statusCode, ResponseBody responseBody) {
            }
        });
    }
}
