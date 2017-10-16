package com.asgatech.sharjahmuseums.Activities;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asgatech.sharjahmuseums.Adapters.ViewPagerAdapter;
import com.asgatech.sharjahmuseums.Models.EventdetatailsResponceModel;
import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.Connection.ServerTool;
import com.asgatech.sharjahmuseums.Tools.CustomFonts.TextViewBold;
import com.asgatech.sharjahmuseums.Tools.Localization;
import com.asgatech.sharjahmuseums.Tools.SharedTool.UserData;
import com.asgatech.sharjahmuseums.Tools.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.ResponseBody;

public class EventDetailsActivity extends AppCompatActivity {
    public ImageView toolbarHomeImageView;
    private ViewPager viewpager;
    private LinearLayout layoutDots;
    private TextView eventTitle;
    private LinearLayout eventItemDateLayout;
    private ImageView eventItemDateIndicator;
    private TextView eventItemDateFrom;
    private TextView eventItemDateTo;
    private TextView tvEventItemTime;
    private TextView eventItemPlace;
    private TextView eventDescription;
    private ImageView downloadIndicator;
    private TextView downloadText;
    private LinearLayout share;
    private LinearLayout call;
    private LinearLayout mail;
    private LinearLayout mark;
    private TextViewBold ToolbarTitleTextView;
    int NUM_PAGES = 0;
    private int currentPage = 0;
    Timer timer;
    private List<ImageView> dots;
    private ImageView dot;
private String attachUrl,eventTitleToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        setContentView(R.layout.activity_event_details);
        eventTitleToolbar=getIntent().getStringExtra("eventTitle");
        int id = getIntent().getIntExtra("eventId", 0);
        setToolBar();
        initView();
        getEventDetails(id, new UserData().getLocalization(EventDetailsActivity.this));

    }

    public void setToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbarHomeImageView = findViewById(R.id.toolbar_home_image_view);
        ToolbarTitleTextView=findViewById(R.id.tv_toolbar_title);
        ToolbarTitleTextView.setText(eventTitleToolbar);
        toolbarHomeImageView.setVisibility(View.VISIBLE);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void initView() {
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        layoutDots = (LinearLayout) findViewById(R.id.layoutDots);
        eventTitle = (TextView) findViewById(R.id.event_title);
        eventItemDateLayout = (LinearLayout) findViewById(R.id.event_item_date_layout);
        eventItemDateIndicator = (ImageView) findViewById(R.id.event_item_date_indicator);
        eventItemDateFrom = (TextView) findViewById(R.id.event_item_date_from);
        eventItemDateTo = (TextView) findViewById(R.id.event_item_date_to);
        tvEventItemTime = (TextView) findViewById(R.id.tv_event_item_time);
        eventItemPlace = (TextView) findViewById(R.id.event_item_place);
        eventDescription = (TextView) findViewById(R.id.event_description);
//        eventDescription.setMovementMethod(new ScrollingMovementMethod());
        downloadIndicator = (ImageView) findViewById(R.id.download_indicator);
        downloadText = (TextView) findViewById(R.id.download_text);
        share = (LinearLayout) findViewById(R.id.share);
        call = (LinearLayout) findViewById(R.id.call);
        mail = (LinearLayout) findViewById(R.id.mail);
        mark = (LinearLayout) findViewById(R.id.mark);

        downloadText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(EventDetailsActivity.this, OpenWebViewActivity.class);
                Log.e("attachUrl",attachUrl);
                intent.putExtra("attachUrl",attachUrl);
                startActivity(intent);
            }
        });
        downloadIndicator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(EventDetailsActivity.this, OpenWebViewActivity.class);
                Log.e("bookLink",attachUrl);
                intent.putExtra("bookLink",attachUrl);
                startActivity(intent);
            }
        });

        dot = null;
        dots = null;
        downloadText.setPaintFlags(downloadText.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    private void getEventDetails(int eventId, int lang) {


        ServerTool.getEventsDetails(EventDetailsActivity.this, eventId, lang, new ServerTool.APICallBack<EventdetatailsResponceModel>() {
            @Override
            public void onSuccess(final EventdetatailsResponceModel model) {
                if (Utils.validObject(model)) {

                    eventTitle.setText(model.getTitle());
                    eventItemDateFrom.setText(Utils.spliteDate(model.getStartDate()));
                    eventItemDateTo.setText(Utils.spliteDate(model.getEndDate()));

                    eventDescription.setText(model.getDescrption());
                    eventItemPlace.setText(model.getAdress());
                    attachUrl=model.getAttach();
                    viewpager.setAdapter(new ViewPagerAdapter(EventDetailsActivity.this, model.getEventImages()));
                    NUM_PAGES = model.getEventImages().size();

//                    addDots(NUM_PAGES);

                    if (Utils.validList(model.getEventImages())) {
                        Log.e("NFF", NUM_PAGES + "");
                        addDots(NUM_PAGES);
                        viewpager.setAdapter(new ViewPagerAdapter(EventDetailsActivity.this, model.getEventImages()));
                        NUM_PAGES = model.getEventImages().size();
                        final Handler handler = new Handler();
                        final Runnable Update = new Runnable() {
                            public void run() {
                                if (currentPage == NUM_PAGES) {
                                    currentPage = 0;
                                }
                                viewpager.setCurrentItem(currentPage++, true);
                            }
                        };
                        timer = new Timer();
                        timer.schedule(new TimerTask() {

                            @Override
                            public void run() {
                                handler.post(Update);
                            }
                        }, 500, 2000);
                        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                            @Override
                            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                            }

                            @Override
                            public void onPageSelected(int position) {
                                selectDot(position, model.getEventImages().size());
                            }

                            @Override
                            public void onPageScrollStateChanged(int state) {
                            }
                        });
                    }
                    share.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            shareAction();
                        }
                    });

                    call.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            callAction(model.getPhone());
                        }
                    });

                    mail.setOnClickListener(new View.OnClickListener() {
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
        sharingIntent.setData(Uri.parse("tel:" + phone));
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


//    @SuppressLint("NewApi")
//    private void addBottomDots(int currentPage) {
//        dots = new TextView[NUM_PAGES];
//
//        layoutDots.removeAllViews();
//        LinearLayout.LayoutParams params = new AppBarLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        params.rightMargin = 10;
//
//        for (int i = 0; i < dots.length; i++) {
//            dots[i] = new TextView(EventDetailsActivity.this);
//            dots[i].setText(Html.fromHtml("&#8226;"));
//            dots[i].setTextSize(30);
//            dots[i].setHeight(10);
//            dots[i].setLayoutParams(params);
//
//            dots[i].setBackground(getResources().getDrawable(R.drawable.indicator_background_inactive));
//            layoutDots.addView(dots[i]);
//        }
//
//        if (dots.length > 0)
//            dots[currentPage].setBackground(getResources().getDrawable(R.drawable.indicator_background_active));
//    }

    public void addDots(int size) {
        dots = new ArrayList<>();
        if (size != 0) {
            for (int i = 0; i < size; i++) {
                if (dot == null) {
                    dot = new ImageView(this);
                    dot.setImageDrawable(getResources().getDrawable(R.drawable.dot_slider_active));
                } else {
                    dot = new ImageView(this);
                    dot.setImageDrawable(getResources().getDrawable(R.drawable.dot_slider_active));
                }

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(4, 2, 4, 2);
                layoutDots.addView(dot, params);
                layoutDots.getChildCount();
                dots.add(dot);
            }

        }

    }

    public void selectDot(int idx, int size) {
        try {
            Resources res = getResources();
            if (size != 0) {
                for (int i = 0; i < size; i++) {
                    int drawableId = (i == idx) ? (R.drawable.dot_slider_active) : (R.drawable.dot_slider_unactive);
                    Drawable drawable = res.getDrawable(drawableId);
                    dots.get(i).setImageDrawable(drawable);
                }
            }
        } catch (Exception e) {
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Utils.hideKeypad(this);
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    protected void onResume() {
        new Localization().setLanguage(EventDetailsActivity.this, new UserData().getLocalization(EventDetailsActivity.this));
        super.onResume();
    }

}
