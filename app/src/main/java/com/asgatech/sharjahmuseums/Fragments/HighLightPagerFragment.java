package com.asgatech.sharjahmuseums.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.asgatech.sharjahmuseums.Models.MuseumsDetailsModel;
import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.Connection.ConstantUtils;
import com.asgatech.sharjahmuseums.Tools.Connection.URLS;
import com.asgatech.sharjahmuseums.Tools.GlideApp;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HighLightPagerFragment extends Fragment implements View.OnClickListener {

    String image;

    ArrayList<MuseumsDetailsModel.HightLightEntity> highlightList;

    public static HighLightPagerFragment newInstance(String image,ArrayList<MuseumsDetailsModel.HightLightEntity> highlightList) {
        HighLightPagerFragment highLightPagerFragment = new HighLightPagerFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ConstantUtils.IMAGE_PATH, image);
        bundle.putParcelableArrayList(ConstantUtils.HIGHLIGHT_LIST, highlightList);
        highLightPagerFragment.setArguments(bundle);
        return highLightPagerFragment;
    }

    public HighLightPagerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        image = getArguments().getString(ConstantUtils.IMAGE_PATH);
        highlightList=getArguments().getParcelableArrayList(ConstantUtils.HIGHLIGHT_LIST);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_high_light_pager, container, false);
        final ImageView coverImageView = (ImageView) rootView.findViewById(R.id.image_cover);
//        coverImageView.setImageDrawable(ContextCompat.getDrawable(getContext(), resourceId));
        GlideApp.with(getActivity())
                .load(URLS.URL_BASE +image).placeholder(R.drawable.no_image).into((coverImageView));

        return rootView;
    }

    @Override
    public void onClick(View view) {

    }
}
