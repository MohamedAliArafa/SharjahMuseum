package com.asgatech.sharjahmuseums.Activities.Events.EventDetails;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asgatech.sharjahmuseums.Activities.Home.HomeActivity;
import com.asgatech.sharjahmuseums.Activities.ViewLocationMapActivity;
import com.asgatech.sharjahmuseums.Adapters.ViewPagerAdapter;
import com.asgatech.sharjahmuseums.Models.EventDetailsResponseModel;
import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.Connection.ConstantUtils;
import com.asgatech.sharjahmuseums.Tools.Connection.ServerTool;
import com.asgatech.sharjahmuseums.Tools.Connection.URLS;
import com.asgatech.sharjahmuseums.Tools.CustomFonts.TextViewBold;
import com.asgatech.sharjahmuseums.Tools.Localization;
import com.asgatech.sharjahmuseums.Tools.PermissionTool;
import com.asgatech.sharjahmuseums.Tools.SharedTool.UserData;
import com.asgatech.sharjahmuseums.Tools.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.ResponseBody;

import static com.asgatech.sharjahmuseums.Tools.AndroidDialogTools.customToastView;

public class EventDetailsActivity extends AppCompatActivity {
    public ImageView toolbarHomeImageView;
    private ViewPager viewpager;
    private LinearLayout layoutDots;
    private TextView eventTitle;
    private TextView eventTime;
    private TextView eventItemDateFrom;
    private TextView eventItemDateTo;
    private TextView eventItemPlace;
    private TextView eventDescription;
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
    private String attachUrl, eventTitleToolbar, eventImage;
    long downloadReference;
    private String eventUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        setContentView(R.layout.activity_event_details);
        eventTitleToolbar = getIntent().getStringExtra("title");
        int id = getIntent().getIntExtra("id", 0);
        setToolBar();
        initView();
        getEventDetails(id, UserData.getLocalization(EventDetailsActivity.this));
    }

    public void setToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleMarginStart(-8);
        toolbarHomeImageView = findViewById(R.id.toolbar_home_image_view);
        ToolbarTitleTextView = findViewById(R.id.tv_toolbar_title);
        ToolbarTitleTextView.setText(eventTitleToolbar);
        toolbarHomeImageView.setVisibility(View.VISIBLE);
        toolbarHomeImageView.setOnClickListener(v -> {
            Intent in = new Intent(this, HomeActivity.class);
            in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(in);
        });
        if (getSupportActionBar() != null)
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void initView() {
        viewpager = findViewById(R.id.viewpager);
        layoutDots = findViewById(R.id.layoutDots);
        eventTitle = findViewById(R.id.event_title);
        eventItemDateFrom = findViewById(R.id.event_item_date_from);
        eventItemDateTo = findViewById(R.id.event_item_date_to);
        eventItemPlace = findViewById(R.id.event_item_place);
        eventDescription = findViewById(R.id.event_description);
        downloadText = findViewById(R.id.download_text);
        eventTime = findViewById(R.id.tv_event_item_time);
        share = findViewById(R.id.share);
        call = findViewById(R.id.call);
        mail = findViewById(R.id.mail);
        mark = findViewById(R.id.mark);

        downloadText.setOnClickListener(view -> {
            if (PermissionTool.checkPermission(this, PermissionTool.PERMISSION_WRITE_STORAGE)) {
                DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                Uri Download_Uri = Uri.parse(URLS.URL_BASE + attachUrl);
                DownloadManager.Request request = new DownloadManager.Request(Download_Uri);
                request.setTitle(eventTitleToolbar);
                request.setDescription(getString(R.string.title_downloading) + attachUrl.substring(attachUrl.lastIndexOf('/') + 1));
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,
                        attachUrl.substring(attachUrl.lastIndexOf('/') + 1));
                downloadReference = downloadManager.enqueue(request);
                IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
                registerReceiver(downloadReceiver, filter);
            }
        });

        dot = null;
        dots = null;
        downloadText.setPaintFlags(downloadText.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        eventItemPlace.setPaintFlags(downloadText.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    BroadcastReceiver downloadReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //check if the broadcast message is for our enqueued download
            long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            if (referenceId != -1)
                if (referenceId == downloadReference) {
                    customToastView(EventDetailsActivity.this, context.getResources().getString(R.string.toast_download_complete));
//                    Toast.makeText(EventDetailsActivity.this, R.string.toast_download_complete, Toast.LENGTH_LONG).show();
                }
        }
    };

    private void getEventDetails(int eventId, int lang) {
        ServerTool.getEventsDetails(EventDetailsActivity.this, eventId, lang, new ServerTool.APICallBack<EventDetailsResponseModel>() {
            @Override
            public void onSuccess(final EventDetailsResponseModel model) {
                if (Utils.validObject(model)) {
                    eventTitle.setText(model.getTitle());
                    eventItemDateFrom.setText(Utils.spliteDate(model.getStartDate()));
                    eventItemDateTo.setText(Utils.spliteDate(model.getEndDate()));
                    eventDescription.setText(model.getDescrption());
                    eventItemPlace.setText(model.getAdress());
                    String time = model.getStartTimeHours() + ":" + model.getStartTimeMin();
                    eventTime.setText(Utils.convertTo12Hour(time));
                    eventItemPlace.setOnClickListener(view -> {
                        Intent intent1 = new Intent(EventDetailsActivity.this,
                                ViewLocationMapActivity.class);
                        intent1.putExtra(ConstantUtils.EXTRA_MUSEUMS_lATITUDE, Double.parseDouble(model.getLat()));
                        intent1.putExtra(ConstantUtils.EXTRA_MUSEUMS_LONGTUDE, Double.parseDouble(model.getLong()));
                        startActivity(intent1);
                    });
                    attachUrl = model.getAttach();
                    eventUrl = model.getUrl();
                    eventImage = model.getEventImages().get(0).getImage();
                    viewpager.setAdapter(new ViewPagerAdapter(EventDetailsActivity.this, model.getEventImages()));
                    NUM_PAGES = model.getEventImages().size();

                    if (Utils.validList(model.getEventImages())) {
                        Log.e("NFF", NUM_PAGES + "");
                        addDots(NUM_PAGES);
                        viewpager.setAdapter(new ViewPagerAdapter(EventDetailsActivity.this, model.getEventImages()));
                        NUM_PAGES = model.getEventImages().size();
                        final Handler handler = new Handler();
                        final Runnable Update = () -> {
                            if (currentPage == NUM_PAGES) {
                                currentPage = 0;
                            }
                            viewpager.setCurrentItem(currentPage++, true);
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
                    share.setOnClickListener(view -> shareAction());

                    call.setOnClickListener(view -> callAction(model.getPhone()));

                    mail.setOnClickListener(view -> emailAction(model));

                    mark.setOnClickListener(view -> addToCalender(model));

                }

            }

            @Override
            public void onFailed(int statusCode, ResponseBody responseBody) {
            }
        });
    }

    private void shareAction() {
        Intent intentShare = new Intent(Intent.ACTION_SEND);
        intentShare.setType("text/plain");
        intentShare.putExtra(Intent.EXTRA_TEXT, eventUrl + "\n" +
                "\n" + getResources().getString(R.string.description) + ":" + eventDescription.getText().toString());
        Intent chooser = Intent.createChooser(intentShare, getResources().getString(R.string.title_share_via));
        chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(chooser);

    }

    private void callAction(String phone) {
        Intent sharingIntent = new Intent(Intent.ACTION_DIAL);
        sharingIntent.setData(Uri.parse("tel:" + phone));
        startActivity(sharingIntent);
        startActivity(Intent.createChooser(sharingIntent, getString(R.string.title_share_via)));
    }


    private void emailAction(EventDetailsResponseModel email) {
        Intent mailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", email.getEmail(), null));
        mailIntent.putExtra(Intent.EXTRA_SUBJECT, email.getTitle());
//        mailIntent.putExtra(Intent.EXTRA_TEXT, email.getDescrption());
        startActivity(Intent.createChooser(mailIntent, getString(R.string.title_share_via)));

    }

    public void addToCalender(EventDetailsResponseModel model) {
        if (PermissionTool.checkPermission(this, PermissionTool.PERMISSION_CALENDER)) {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
            Intent intent = new Intent(Intent.ACTION_EDIT);
            intent.setType("vnd.android.cursor.item/event");
            try {
                Date today = cal.getTime();
                Date date = sdf.parse(model.getEndDate());
                if (date.before(today)) {
                    customToastView(EventDetailsActivity.this, getResources().getString(R.string.toast_evet_expired));
//                    Toast.makeText(this, R.string.toast_evet_expired, Toast.LENGTH_SHORT).show();
                    return;
                }
                cal.setTime(date);
                intent.putExtra("beginTime", cal.getTimeInMillis());
                date = sdf.parse(model.getEndDate());
                cal.setTime(date);
                intent.putExtra("endTime", cal.getTimeInMillis());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            intent.putExtra("allDay", true);
            intent.putExtra("title", model.getTitle());
            startActivity(intent);
        }
    }

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
        Localization.setLanguage(EventDetailsActivity.this, UserData.getLocalization(EventDetailsActivity.this));
        super.onResume();
    }

}
