package com.asgatech.sharjahmuseums.Activities.Search;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asgatech.sharjahmuseums.Adapters.MuseumCategoryAdapter;
import com.asgatech.sharjahmuseums.Models.MuseumCategoryResponse;
import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.Connection.ServerTool;
import com.asgatech.sharjahmuseums.Tools.SharedTool.UserData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchMuseumCategoryFragment extends Fragment {

    @BindView(R.id.rv_category)
    RecyclerView mRecycleCategory;

    public SearchMuseumCategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_museum_category, container, false);
        ButterKnife.bind(this, view);
        setUpView();
        return view;
    }

    void setUpView() {
        mRecycleCategory.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycleCategory.setItemAnimator(new DefaultItemAnimator());
        mRecycleCategory.setNestedScrollingEnabled(false);
        mRecycleCategory.setHasFixedSize(true);
        getALLMuseumCategory(UserData.getLocalization(getContext()));
    }

    void getALLMuseumCategory(int language) {
        ServerTool.getALLMuseumCategory(getContext(), language, new ServerTool.APICallBack<List<MuseumCategoryResponse>>() {
            @Override
            public void onSuccess(List<MuseumCategoryResponse> response) {
                if (response.size() != 0) {
                    setData(response);
                }
            }

            @Override
            public void onFailed(int statusCode, ResponseBody responseBody) {

            }
        });
    }

    private void setData(final List<MuseumCategoryResponse> data) {
        MuseumCategoryAdapter adapter = new MuseumCategoryAdapter(getContext(), data);
        mRecycleCategory.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

}
