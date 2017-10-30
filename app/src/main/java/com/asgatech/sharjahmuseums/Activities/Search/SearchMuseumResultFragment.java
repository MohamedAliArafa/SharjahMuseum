package com.asgatech.sharjahmuseums.Activities.Search;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asgatech.sharjahmuseums.Adapters.SearchMuseumsAdapter;
import com.asgatech.sharjahmuseums.Models.MuseumsDetailsModel;
import com.asgatech.sharjahmuseums.Models.Request.SearchPagingModel;
import com.asgatech.sharjahmuseums.R;
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
public class SearchMuseumResultFragment extends Fragment {

    private SearchPagingModel pagingModel;

    public SearchMuseumResultFragment() {
        // Required empty public constructor
    }

    static int mCatID;
    static String mKeyword;
    @BindView(R.id.museum_result_recycler)
    RecyclerView mMuseumsResultRecyclerView;

    public static SearchMuseumResultFragment newInstance(int catId, String keyword) {
        mCatID = catId;
        mKeyword = keyword;
        return new SearchMuseumResultFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((SearchMuseumActivity) getActivity()).setCurrentId(mCatID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_museum_result, container, false);
        ButterKnife.bind(this, view);
        mMuseumsResultRecyclerView.setHasFixedSize(true);
        mMuseumsResultRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        pagingModel = new SearchPagingModel(1, 1000,
                UserData.getLocalization(getActivity()), mCatID, mKeyword);
        getResultMuseums(pagingModel);
        return view;
    }

    private void getResultMuseums(SearchPagingModel pagingModel) {
        ServerTool.getMuseumWithSearch(getActivity(), pagingModel, new ServerTool.APICallBack<List<MuseumsDetailsModel>>() {
            @Override
            public void onSuccess(List<MuseumsDetailsModel> response) {
                if (Utils.validList(response)) {
                    SearchMuseumsAdapter museumsAdapter = new SearchMuseumsAdapter(getActivity(),
                            response, ((SearchContract.ModelView) getActivity()).getPresenter());
                    mMuseumsResultRecyclerView.setAdapter(museumsAdapter);
                }
            }

            @Override
            public void onFailed(int statusCode, ResponseBody responseBody) {
            }
        });
    }

}
