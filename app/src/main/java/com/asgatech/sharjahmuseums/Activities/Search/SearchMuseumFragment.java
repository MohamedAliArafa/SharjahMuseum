package com.asgatech.sharjahmuseums.Activities.Search;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import com.asgatech.sharjahmuseums.Adapters.MuseumCategoryAdapter;
import com.asgatech.sharjahmuseums.Adapters.SearchMuseumsAdapter;
import com.asgatech.sharjahmuseums.Models.MuseumCategoryResponse;
import com.asgatech.sharjahmuseums.Models.MuseumsDetailsModel;
import com.asgatech.sharjahmuseums.Models.Request.SearchPagingModel;
import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.Connection.ServerTool;
import com.asgatech.sharjahmuseums.Tools.CustomFonts.EditText;
import com.asgatech.sharjahmuseums.Tools.DialogTool.NoDataDialog;
import com.asgatech.sharjahmuseums.Tools.SharedTool.UserData;
import com.asgatech.sharjahmuseums.Tools.Utils;

import java.util.List;

import okhttp3.ResponseBody;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchMuseumFragment extends Fragment implements SearchContract.ModelView {
    EditText ToolbarSearchEditText;
    private SearchPresenter mPresenter;
    RecyclerView mMuseumsResultRecyclerView;
    private int catId = 0;

    public SearchMuseumFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_museum, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupView(view);
    }

    private void setupView(View view) {
        ToolbarSearchEditText = (EditText) view.findViewById(R.id.et_toolbar_search);
        mMuseumsResultRecyclerView = (RecyclerView) view.findViewById(R.id.museum_result_recycler);
        mMuseumsResultRecyclerView.setHasFixedSize(true);
        mMuseumsResultRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mPresenter = new SearchPresenter(getActivity().getSupportFragmentManager(), this);
        getALLMuseumCategory(UserData.getLocalization(getContext()));

        ToolbarSearchEditText.setOnEditorActionListener((v, actionId, event) -> {
            boolean handled = false;
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                search(catId, ToolbarSearchEditText.getText().toString());
                handled = true;
            }
            return handled;
        });

    }
    void getALLMuseumCategory(int language) {
        ServerTool.getALLMuseumCategory(getActivity(), language, new ServerTool.APICallBack<List<MuseumCategoryResponse>>() {
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
        MuseumCategoryAdapter adapter = new MuseumCategoryAdapter(getContext(), data, getFragmentManager(), new MuseumCategoryAdapter.onClickAdapter() {
            @Override
            public void onClickItem(int ID, String keyword) {

            }
        });
        mMuseumsResultRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    public void search(int catId, String keyword) {
        SearchPagingModel  pagingModel = new SearchPagingModel(1, 1000,
                UserData.getLocalization(getActivity()), catId, keyword);
        getResultMuseums(pagingModel);
    }
    private void getResultMuseums(SearchPagingModel pagingModel) {
        ServerTool.getMuseumWithSearch(getActivity(), pagingModel, new ServerTool.APICallBack<List<MuseumsDetailsModel>>() {
            @Override
            public void onSuccess(List<MuseumsDetailsModel> response) {
                if (Utils.validList(response)) {
                    SearchMuseumsAdapter museumsAdapter = new SearchMuseumsAdapter(getActivity(),
                            response, ((SearchContract.ModelView) getActivity()).getPresenter());
                    mMuseumsResultRecyclerView.setAdapter(museumsAdapter);
                } else {
                    new NoDataDialog().showDialog(getContext());
                }
            }

            @Override
            public void onFailed(int statusCode, ResponseBody responseBody) {
                new NoDataDialog().showDialog(getContext());
            }
        });
    }


    @Override
    public SearchContract.UserAction getPresenter() {
        if (mPresenter != null)
            return mPresenter;
        else
            return new SearchPresenter(getActivity().getSupportFragmentManager(), this);
    }
}
