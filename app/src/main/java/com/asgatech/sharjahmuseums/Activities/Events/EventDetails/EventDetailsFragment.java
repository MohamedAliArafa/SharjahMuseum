package com.asgatech.sharjahmuseums.Activities.Events.EventDetails;


import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asgatech.sharjahmuseums.Adapters.ViewPagerAdapter;
import com.asgatech.sharjahmuseums.Models.EventdetatailsResponceModel;
import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.Connection.ServerTool;
import com.asgatech.sharjahmuseums.Tools.SharedTool.UserData;
import com.asgatech.sharjahmuseums.Tools.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventDetailsFragment extends Fragment {
    @BindView(R.id.loading_layout)
    View loadingLayout;

    @BindView(R.id.event_title)
    TextView eventTitle;

//    @BindView(R.id.main_details_layout)
//    View mainLayout;

    @BindView(R.id.event_item_date_from)
    TextView mDateFromTextView;

    @BindView(R.id.event_item_date_to)
    TextView mDateToTextView;

    @BindView(R.id.event_place)
    TextView mPlaceTextView;

    @BindView(R.id.event_description)
    TextView mDescTextView;
    @BindView(R.id.download_text)
    TextView mDownloadTextView;

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @BindView(R.id.share)
    LinearLayout share;

    @BindView(R.id.call)
    LinearLayout caLL;

    @BindView(R.id.mail)
    LinearLayout email;

    @BindView(R.id.mark)
    LinearLayout mark;


    private TextView[] dots;
    @BindView(R.id.layoutDots)
    LinearLayout dotsLayout;

    int size = 0;


    public EventDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_event_details, container, false);
        ButterKnife.bind(this, view);
        int id = getArguments().getInt("eventId");
        mDownloadTextView.setPaintFlags(mDownloadTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
      //  mTextView.setText("This text will be underlined");
        getEventDetails(id , UserData.getLocalization(getActivity()));
        return view;
    }


    private void getEventDetails(int eventId, int lang) {


        ServerTool.getEventsDetails(getActivity(), eventId, lang, new ServerTool.APICallBack<EventdetatailsResponceModel>() {
            @Override
            public void onSuccess(final EventdetatailsResponceModel model) {
                if (Utils.validObject(model)) {

                    eventTitle.setText(model.getTitle());
                    mDateFromTextView.setText(model.getStartDate());
                    mDateToTextView.setText(model.getEndDate());
                    mDescTextView.setText(model.getDescrption());
                    mPlaceTextView.setText(model.getAdress());

                    viewPager.setAdapter(new ViewPagerAdapter(getActivity(), model.getEventImages()));
                    size = model.getEventImages().size();

                    addBottomDots(0);

                    viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

                    share.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            shareAction();
                        }
                    });

                    caLL.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            callAction(model.getPhone());
                        }
                    });

                    email.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            emailAction(model.getEmail());
                        }
                    });

                }

            }

            @Override
            public void onFailed(int statusCode, ResponseBody responseBody) {
            }
        });
    }




    private void shareAction() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "share our app..";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.app_name));
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));

    }


    private void callAction(String phone) {
        Intent sharingIntent = new Intent(Intent.ACTION_DIAL);
        sharingIntent.setData(Uri.parse("tel:"+phone));
        startActivity(sharingIntent);

        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }


    private void emailAction(String email) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.app_name));
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "message");
        sharingIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        startActivity(Intent.createChooser(sharingIntent, "Share via"));

    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[size];

        dotsLayout.removeAllViews();
        LinearLayout.LayoutParams params = new AppBarLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.rightMargin = 10;

        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(getActivity());
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(30);
            dots[i].setHeight(10);
            dots[i].setLayoutParams(params);

//            dots[i].setTextColor(getResources().getColor(R.drawable.indicator_background_inactive));
            dots[i].setBackground(getResources().getDrawable(R.drawable.indicator_background_inactive));
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
//            dots[currentPage].setTextColor(getResources().getDrawable(R.drawable.indicator_background_active));
            dots[currentPage].setBackground(getResources().getDrawable(R.drawable.indicator_background_active));
    }

    //	viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    };

}
