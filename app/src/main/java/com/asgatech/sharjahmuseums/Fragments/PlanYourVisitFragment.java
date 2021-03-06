package com.asgatech.sharjahmuseums.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.asgatech.sharjahmuseums.Activities.Home.HomeActivity;
import com.asgatech.sharjahmuseums.Activities.OpenWebViewActivity;
import com.asgatech.sharjahmuseums.Adapters.PlanYourVisitAdapter;
import com.asgatech.sharjahmuseums.Models.PlanYourVisitsModel;
import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.Connection.ServerTool;
import com.asgatech.sharjahmuseums.Tools.CustomFonts.TextViewLight;
import com.asgatech.sharjahmuseums.Tools.SharedTool.UserData;
import com.asgatech.sharjahmuseums.Tools.Utils;

import okhttp3.ResponseBody;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlanYourVisitFragment extends Fragment {
    private RecyclerView recyclerView;
    private TextViewLight planDescriptionTextView, groupVisitsDescriptionTextView;
    private Button bookNowBtn;
    private String bookLink;
    private LinearLayoutManager layoutManager;
    private LinearLayout groupVisitsLinear;


    public PlanYourVisitFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_plan_your_visit, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        if (Utils.isNetworkAvailable(getActivity())) {
            getPlanVisits(UserData.getLocalization(getActivity()));
        }
        bookNowBtn.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), OpenWebViewActivity.class);
            Log.e("bookLink", bookLink);
            intent.putExtra("bookLink", bookLink);
            Log.e("bookLink" , bookLink);
            startActivity(intent);
        });

    }

    private void initView(View view) {
        ((HomeActivity) getActivity()).changeToolbarTitle(getString(R.string.plan_your_visit));
        groupVisitsLinear = view.findViewById(R.id.layout_group_visits);
        recyclerView = view.findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        planDescriptionTextView = view.findViewById(R.id.plan_txt);
        groupVisitsDescriptionTextView = view.findViewById(R.id.describtion_group_visits);
        groupVisitsDescriptionTextView.setMovementMethod(new ScrollingMovementMethod());
        bookNowBtn = view.findViewById(R.id.book_nw);


    }

    private void getPlanVisits(int lang) {
        ServerTool.getPlanVisits(getActivity(), lang, new ServerTool.APICallBack<PlanYourVisitsModel>() {
            @Override
            public void onSuccess(PlanYourVisitsModel response) {
                planDescriptionTextView.setText(response.getPlaneText());
                groupVisitsDescriptionTextView.setText(response.getGrouptext());
                bookLink = response.getGroupBookLink();
                Log.e("", response.getPlanVist().size() + "");
                recyclerView.setAdapter(new PlanYourVisitAdapter(getActivity(), response.getPlanVist(), recyclerView));
                groupVisitsLinear.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailed(int statusCode, ResponseBody responseBody) {

            }
        });
    }
}
