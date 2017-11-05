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
import com.asgatech.sharjahmuseums.Models.HighLightEntity;
import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.Connection.ConstantUtils;
import com.asgatech.sharjahmuseums.Tools.Connection.URLS;
import com.asgatech.sharjahmuseums.Tools.GlideApp;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HighLightOverlappingFragment extends Fragment {
    String image;

    ArrayList<HighLightEntity> highlightList;
    private int mPosition;
    private String highlightColor;

    public static HighLightOverlappingFragment newInstance(String image, String mMuseumColor, List<HighLightEntity> highlightList, int position) {
        HighLightOverlappingFragment highLightOverlappingFragment = new HighLightOverlappingFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ConstantUtils.IMAGE_PATH, image);
        ArrayList<HighLightEntity> list = new ArrayList<>();
        list.addAll(highlightList);
        bundle.putString(ConstantUtils.HIGHLIGHT_COLOR, mMuseumColor);
        bundle.putParcelableArrayList(ConstantUtils.HIGHLIGHT_LIST, list);
        bundle.putInt(ConstantUtils.HIGHLIGHT_LIST_POSITION, position);
        highLightOverlappingFragment.setArguments(bundle);
        return highLightOverlappingFragment;
    }

    public HighLightOverlappingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        image = getArguments().getString(ConstantUtils.IMAGE_PATH);
        highlightColor = getArguments().getString(ConstantUtils.HIGHLIGHT_COLOR);
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

        Log.e(getString(R.string.tag_image), URLS.URL_BASE + image);
        coverImageView.setOnClickListener(view -> {
            Intent intent1 = new Intent(getActivity(), HighlightsDetailActivity.class);
            intent1.putExtra(ConstantUtils.HIGHLIGHT_LIST, highlightList);
            intent1.putExtra(ConstantUtils.HIGHLIGHT_LIST_POSITION, mPosition);
            intent1.putExtra(ConstantUtils.HIGHLIGHT_COLOR, highlightColor);
            startActivity(intent1);
        });

        return rootView;
    }


}
