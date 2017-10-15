package com.asgatech.sharjahmuseums.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.Connection.URLS;
import com.bumptech.glide.Glide;

/**
 * A simple {@link Fragment} subclass.
 */
public class HighLightOverlapingFragment extends Fragment {
    String image;
    static final String IMAGE_PATH = "IMAGE_PATH";


    public static HighLightOverlapingFragment newInstance(String image) {
        HighLightOverlapingFragment highLightOverlapingFragment = new HighLightOverlapingFragment();
        Bundle bundle = new Bundle();
        bundle.putString(IMAGE_PATH, image);
        highLightOverlapingFragment.setArguments(bundle);
        return highLightOverlapingFragment;
    }

    public HighLightOverlapingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        image = getArguments().getString(IMAGE_PATH);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_high_light_overlaping, container, false);
        ImageView coverImageView = (ImageView) rootView.findViewById(R.id.image_cover);
//        coverImageView.setImageDrawable(ContextCompat.getDrawable(getContext(), resourceId));

        Glide.with(getActivity()).load(URLS.URL_BASE +image ).placeholder(R.drawable.no_image).into(coverImageView);

        return rootView;
    }

}
