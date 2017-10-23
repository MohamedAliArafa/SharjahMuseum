package com.asgatech.sharjahmuseums.Activities.OurMuseums.MuseumDetails;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.asgatech.sharjahmuseums.Activities.OurMuseums.MuseumDetails.HighlightDetails.HighlightsDetailActivity;
import com.asgatech.sharjahmuseums.Models.HightLightEntity;
import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.Connection.ConstantUtils;
import com.asgatech.sharjahmuseums.Tools.Connection.URLS;
import com.asgatech.sharjahmuseums.Tools.GlideApp;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HighLightOverlapingFragment extends Fragment {
    String image;

    ArrayList<HightLightEntity> highlightList;
    private int mPosition;

    public static HighLightOverlapingFragment newInstance(String image, List<HightLightEntity> highlightList, int position) {
        HighLightOverlapingFragment highLightOverlapingFragment = new HighLightOverlapingFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ConstantUtils.IMAGE_PATH, image);
        ArrayList<HightLightEntity> list = new ArrayList<>();
        list.addAll(highlightList);
        bundle.putParcelableArrayList(ConstantUtils.HIGHLIGHT_LIST, list);
        bundle.putInt(ConstantUtils.HIGHLIGHT_LIST_POSITION, position);
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
        highlightList = getArguments().getParcelableArrayList(ConstantUtils.HIGHLIGHT_LIST);
        mPosition = getArguments().getInt(ConstantUtils.HIGHLIGHT_LIST_POSITION, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_high_light_overlaping, container, false);
        ImageView coverImageView = rootView.findViewById(R.id.image_cover);
        GlideApp.with(getActivity()).load(URLS.URL_BASE + image).placeholder(R.drawable.no_image).into(coverImageView);

//        GlideApp.with(this)
//                .load(URLS.URL_BASE +image).transform(new RoundedCornersTransformation(getActivity(), 20, 0, "#e40d62", 50))
//                .into(coverImageView);

        Log.e("imaaaaaag", URLS.URL_BASE + image);
        coverImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getActivity(), HighlightsDetailActivity.class);
                intent1.putExtra(ConstantUtils.HIGHLIGHT_LIST, highlightList);
                intent1.putExtra(ConstantUtils.HIGHLIGHT_LIST_POSITION, mPosition);
                startActivity(intent1);
            }
        });

        return rootView;
    }


}
