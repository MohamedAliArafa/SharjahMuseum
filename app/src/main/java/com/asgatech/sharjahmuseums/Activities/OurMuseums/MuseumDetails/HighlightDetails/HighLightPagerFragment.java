package com.asgatech.sharjahmuseums.Activities.OurMuseums.MuseumDetails.HighlightDetails;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.asgatech.sharjahmuseums.Models.HighLightEntity;
import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.Connection.ConstantUtils;
import com.asgatech.sharjahmuseums.Tools.Connection.URLS;
import com.asgatech.sharjahmuseums.Tools.GlideApp;
import com.bumptech.glide.load.Option;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

/**
 * A simple {@link Fragment} subclass.
 */
public class HighLightPagerFragment extends Fragment implements View.OnClickListener {

    String image;

    HighLightEntity highlight;

    public static HighLightPagerFragment newInstance(String image, HighLightEntity highlight) {
        HighLightPagerFragment highLightPagerFragment = new HighLightPagerFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ConstantUtils.IMAGE_PATH, image);
        bundle.putParcelable(ConstantUtils.HIGHLIGHT_LIST, highlight);
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
        highlight = getArguments().getParcelable(ConstantUtils.HIGHLIGHT_LIST);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_high_light_pager, container, false);
        ImageView coverImageView = rootView.findViewById(R.id.image_cover);
        TextView titleTextView = rootView.findViewById(R.id.tv_title);
        TextView descTextView = rootView.findViewById(R.id.tv_text_desc);
        titleTextView.setText(highlight.getTitle());
        descTextView.setText(highlight.getText());
        GlideApp.with(getActivity())
                .load(URLS.URL_BASE + image).placeholder(R.drawable.no_image)
                .apply(RequestOptions.option(Option.memory(ConstantUtils.GLIDE_TIMEOUT), 0))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into((coverImageView));

        return rootView;
    }

    @Override
    public void onClick(View view) {

    }
}
