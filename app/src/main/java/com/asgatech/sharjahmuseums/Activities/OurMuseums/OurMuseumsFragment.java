package com.asgatech.sharjahmuseums.Activities.OurMuseums;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.asgatech.sharjahmuseums.Activities.Home.HomeActivity;
import com.asgatech.sharjahmuseums.Activities.Home.HomeContract;
import com.asgatech.sharjahmuseums.Adapters.AllMuseumsAdapter;
import com.asgatech.sharjahmuseums.Adapters.MuseumCategoryAdapter;
import com.asgatech.sharjahmuseums.Adapters.SearchMuseumsAdapter;
import com.asgatech.sharjahmuseums.Models.MuseumCategoryResponse;
import com.asgatech.sharjahmuseums.Models.MuseumsDetailsModel;
import com.asgatech.sharjahmuseums.Models.Request.PagingModel;
import com.asgatech.sharjahmuseums.Models.Request.SearchPagingModel;
import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.Connection.ServerTool;
import com.asgatech.sharjahmuseums.Tools.SharedTool.UserData;
import com.asgatech.sharjahmuseums.Tools.Utils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;

import static com.asgatech.sharjahmuseums.Tools.AndroidDialogTools.customToastView;

/**
 * A simple {@link Fragment} subclass.
 */
public class OurMuseumsFragment extends Fragment implements OnClickListener {
    private RecyclerView ourMuseumsRecyclerView, serachRecycler;
    private PagingModel pagingModel;
    //    LinearLayout searchIv;
    RecyclerView.LayoutManager mLayoutManager;
    private ImageView close, searchIv;
    LinearLayout frameSearch;
    private HomeContract.UserAction presenter;
    private EditText ToolbarSearchEditText;
    List<MuseumsDetailsModel> museumsDetailsModels;

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
        ourMuseumsRecyclerView = view.findViewById(R.id.our_museums_recycler_view);
        searchIv = view.findViewById(R.id.search_iv);
        close = view.findViewById(R.id.close);
        searchIv.setOnClickListener(this);

        close.setOnClickListener(this);
        frameSearch = view.findViewById(R.id.frame_search);
        serachRecycler = view.findViewById(R.id.serach_recycler);
        ToolbarSearchEditText = view.findViewById(R.id.et_toolbar_search);
//        searchMuseumAutoCompleteTextView.setOnClickListener(this);
        ToolbarSearchEditText.setFocusable(false);
        ToolbarSearchEditText.setFocusableInTouchMode(false);
        ToolbarSearchEditText.setOnClickListener(this);
        ((HomeActivity) getActivity()).changeToolbarTitle(getString(R.string.our_museums));
//        ((HomeActivity) getActivity()).mToolbar.setTitle(getString(R.string.our_museums));
        ourMuseumsRecyclerView.setHasFixedSize(true);
        serachRecycler.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        RecyclerView.LayoutManager nLayoutManager = new LinearLayoutManager(getActivity());
        ourMuseumsRecyclerView.setLayoutManager(mLayoutManager);
        serachRecycler.setLayoutManager(nLayoutManager);
        pagingModel = new PagingModel(1, 1000, UserData.getLocalization(getActivity()));
        getAllMuseums(pagingModel);
        ToolbarSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() == 0) {
                    ToolbarSearchEditText.setFocusable(false);
                    ToolbarSearchEditText.setFocusableInTouchMode(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (Utils.validList(museumsDetailsModels)) {
                    List<MuseumsDetailsModel> innerList = new ArrayList<>();
                    for (int i = 0; i < museumsDetailsModels.size(); i++) {
                        String word = museumsDetailsModels.get(i).getTitle().toLowerCase();
                        String wordText = ToolbarSearchEditText.getText().toString().toLowerCase();
                        if (word.contains(wordText)) {
                            innerList.add(museumsDetailsModels.get(i));
                        }
                    }
                    if (Utils.validList(innerList)) {
                        AllMuseumsAdapter museumsAdapter = new AllMuseumsAdapter(getActivity(), innerList, ((HomeContract.ModelView) getActivity()).getPresenter());
                        frameSearch.setVisibility(View.GONE);
                        ourMuseumsRecyclerView.setAdapter(museumsAdapter);
                    } else {
                        AllMuseumsAdapter museumsAdapter = new AllMuseumsAdapter(getActivity(), innerList, ((HomeContract.ModelView) getActivity()).getPresenter());
                        ourMuseumsRecyclerView.setAdapter(museumsAdapter);
                        frameSearch.setVisibility(View.GONE);
                        customToastView(getActivity(), getActivity().getString(R.string.no_museums));
                    }
                }
            }


        });
    }

    private void getAllMuseums(PagingModel pagingModel) {
        ServerTool.getAllMuseums(getActivity(), pagingModel, new ServerTool.APICallBack<List<MuseumsDetailsModel>>() {
            @Override
            public void onSuccess(List<MuseumsDetailsModel> response) {
                if (Utils.validList(response)) {
                    museumsDetailsModels = new ArrayList<>();
                    museumsDetailsModels = response;
                    AllMuseumsAdapter museumsAdapter = new AllMuseumsAdapter(getActivity(), response, ((HomeContract.ModelView) getActivity()).getPresenter());
                    ourMuseumsRecyclerView.setAdapter(museumsAdapter);
                }
            }

            @Override
            public void onFailed(int statusCode, ResponseBody responseBody) {
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.et_toolbar_search:
            case R.id.search_iv:
                if (!ToolbarSearchEditText.isFocusable()) {
                    getSearchResult();
                    ToolbarSearchEditText.setFocusable(true);
                    ToolbarSearchEditText.setFocusableInTouchMode(true);
                }
//                else {
//
//                    if (Utils.validList(museumsDetailsModels)) {
//                        List<MuseumsDetailsModel> innerList = new ArrayList<>();
//                        for (int i = 0; i < museumsDetailsModels.size(); i++) {
//                            if (museumsDetailsModels.get(i).getTitle().contains(ToolbarSearchEditText.getText().toString())) {
//                                innerList.add(museumsDetailsModels.get(i));
//                            }
//                        }
//                        if (Utils.validList(innerList)) {
//                            AllMuseumsAdapter museumsAdapter = new AllMuseumsAdapter(getActivity(), innerList, ((HomeContract.ModelView) getActivity()).getPresenter());
//                            ToolbarSearchEditText.setFocusable(false);
//                            ToolbarSearchEditText.setFocusableInTouchMode(false);
//                            frameSearch.setVisibility(View.GONE);
//                            ourMuseumsRecyclerView.setAdapter(museumsAdapter);
//                        }
//                    }

                //List<MuseumsDetailsModel>
//                    ((EventsContract.ModelView) pagerAdapter
//                            .getItem(mEventViewPager.getCurrentItem()))
//                            .updateView(Realm.getDefaultInstance().where(MuseumsDetailsModel.class)
//                                    .contains("title", ToolbarSearchEditText.getText().toString().toString(), Case.INSENSITIVE)
//                                    .findAll(), null);

//                    SearchPagingModel pagingModel = new SearchPagingModel(1, 1000,
//                            UserData.getLocalization(getActivity()), 0, ToolbarSearchEditText.getText().toString());
//                    getResultMuseums(pagingModel);
//                }
                break;
            case R.id.close:
                Utils.hideKeypad(getActivity());
                ToolbarSearchEditText.setText("");
                ToolbarSearchEditText.setFocusable(false);
                ToolbarSearchEditText.setFocusableInTouchMode(false);
                frameSearch.setVisibility(View.GONE);
                break;


        }
//        Utils.hideKeypad(getActivity());
//        getSearchResult();

//        ((HomeActivity)getActivity()).openFragment(SearchMuseumFragment.class , null);
//        startActivity(new Intent(getActivity(), SearchMuseumActivity.class));
    }

    private void getSearchResult() {
        getALLMuseumCategory(UserData.getLocalization(getContext()));
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
                SearchPagingModel pagingModel = new SearchPagingModel(1, 1000,
                        UserData.getLocalization(getActivity()), ID, keyword);
                getResultMuseums(pagingModel);
            }
        });
//        ourMuseumsRecyclerView.setNestedScrollingEnabled(false);
//        ourMuseumsRecyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        frameSearch.setVisibility(View.VISIBLE);
//        ourMuseumsRecyclerView.setVisibility(View.GONE);
        serachRecycler.setAdapter(adapter);
    }

    private void getResultMuseums(SearchPagingModel pagingModel) {
        ServerTool.getMuseumWithSearch(getActivity(), pagingModel, new ServerTool.APICallBack<List<MuseumsDetailsModel>>() {
            @Override
            public void onSuccess(List<MuseumsDetailsModel> response) {
                if (Utils.validList(response)) {
                    SearchMuseumsAdapter museumsAdapter = new SearchMuseumsAdapter(getActivity(),
                            response);
                    serachRecycler.setAdapter(museumsAdapter);
                    ToolbarSearchEditText.setFocusable(false);
                    ToolbarSearchEditText.setFocusableInTouchMode(false);
                } else {
//                    new NoDataDialog().showDialog(getContext());
                }
            }

            @Override
            public void onFailed(int statusCode, ResponseBody responseBody) {
//                new NoDataDialog().showDialog(getContext());
            }
        });
    }
}
