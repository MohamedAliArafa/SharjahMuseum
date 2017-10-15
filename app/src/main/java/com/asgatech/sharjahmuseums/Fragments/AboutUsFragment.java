package com.asgatech.sharjahmuseums.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.asgatech.sharjahmuseums.Models.AboutUsModel;
import com.asgatech.sharjahmuseums.Models.EventCategoryModel;
import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.Connection.ServerTool;
import com.asgatech.sharjahmuseums.Tools.Connection.URLS;
import com.asgatech.sharjahmuseums.Tools.Utils;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutUsFragment extends Fragment {

    @BindView(R.id.about_mainImage)
    ImageView mainImage;

    @BindView(R.id.about_memberImage)
    ImageView memberImage;

    @BindView(R.id.about_visionMain)
    TextView visionTv;

    @BindView(R.id.about_missionMain)
    TextView missionTv;

    @BindView(R.id.about_policiesMain)
    TextView policyTv;

    @BindView(R.id.about_memberWords)
    TextView memberWords;


    @BindView(R.id.about_memberName)
    TextView memberName;


    public AboutUsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about_us, container, false);
        ButterKnife.bind(this, view);

        getAboutUs(1);
        return view;
    }


    private void getAboutUs(int langauge) {

        ServerTool.getAbout(getActivity(), langauge, new ServerTool.APICallBack<AboutUsModel>() {
            @Override
            public void onSuccess(AboutUsModel response) {
                if (Utils.validObject(response)) {
                    Glide.with(getActivity()).load(URLS.URL_BASE + response.getImage()).placeholder(R.drawable.image_about_default).into(mainImage);
                    Glide.with(getActivity()).load(URLS.URL_BASE + response.getOfficialImage()).placeholder(R.drawable.image_m).into(memberImage);

                    Log.e("Image" , URLS.URL_BASE+response.getImage());
                    Log.e("Image" , URLS.URL_BASE+response.getOfficialImage());
                    memberWords.setText(response.getOfficalWord());
                    missionTv.setText(response.getMisson());
                    visionTv.setText(response.getVision());
                    policyTv.setText(response.getPolicies());
                    memberName.setText(response.getOfficalName());

                }
            }

            @Override
            public void onFailed(int statusCode, ResponseBody responseBody) {
            }
        });
    }
}
