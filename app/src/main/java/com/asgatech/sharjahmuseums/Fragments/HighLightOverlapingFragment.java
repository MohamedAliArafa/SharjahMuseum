package com.asgatech.sharjahmuseums.Fragments;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.asgatech.sharjahmuseums.Activities.HightLightDetailActivity;
import com.asgatech.sharjahmuseums.Activities.MuseumsDetailsActivity;
import com.asgatech.sharjahmuseums.Activities.ViewLocationMapActivity;
import com.asgatech.sharjahmuseums.Models.MuseumsDetailsModel;
import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.Connection.ConstantUtils;
import com.asgatech.sharjahmuseums.Tools.Connection.URLS;
import com.asgatech.sharjahmuseums.Tools.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HighLightOverlapingFragment extends Fragment {
    String image;

    ArrayList<MuseumsDetailsModel.HightLightEntity> highlightList;

    public static HighLightOverlapingFragment newInstance(String image,ArrayList<MuseumsDetailsModel.HightLightEntity> highlightList) {
        HighLightOverlapingFragment highLightOverlapingFragment = new HighLightOverlapingFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ConstantUtils.IMAGE_PATH, image);
        bundle.putParcelableArrayList(ConstantUtils.HIGHLIGHT_LIST, highlightList);
        highLightOverlapingFragment.setArguments(bundle);
        return highLightOverlapingFragment;
    }

    public HighLightOverlapingFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_high_light_overlaping, container, false);
        final ImageView coverImageView = (ImageView) rootView.findViewById(R.id.image_cover);
//        coverImageView.setImageDrawable(ContextCompat.getDrawable(getContext(), resourceId));
        Glide.with(getActivity())
                .load(URLS.URL_BASE +image).
                asBitmap().override(400, 400).placeholder(R.drawable.no_image).into(new BitmapImageViewTarget(coverImageView){
            @Override
            protected void setResource(Bitmap resource) {
                if (getActivity()!=null){
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(getActivity().getResources(), resource);
                    circularBitmapDrawable.setCornerRadius(20);

                    coverImageView.setImageDrawable(circularBitmapDrawable);
                }}
        });

        coverImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getActivity(),"hshshsh"+highlightList.size(), Toast.LENGTH_LONG).show();
                Intent intent1 = new Intent(getActivity(), HightLightDetailActivity.class);
                intent1.putExtra(ConstantUtils.HIGHLIGHT_LIST, highlightList);
                startActivity(intent1);

            }
        });

        return rootView;
    }

}
