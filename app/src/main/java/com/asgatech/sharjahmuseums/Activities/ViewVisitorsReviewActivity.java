package com.asgatech.sharjahmuseums.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.asgatech.sharjahmuseums.Adapters.ViewVisitorsReviewAdapter;
import com.asgatech.sharjahmuseums.Models.MuseumsDetailsModel;
import com.asgatech.sharjahmuseums.Models.ReviewVisitorsRequest;
import com.asgatech.sharjahmuseums.Models.ReviewVisitorsResponse;
import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.CircleImageView;
import com.asgatech.sharjahmuseums.Tools.Connection.ConstantUtils;
import com.asgatech.sharjahmuseums.Tools.Connection.ServerTool;
import com.asgatech.sharjahmuseums.Tools.CustomFonts.TextViewBold;
import com.asgatech.sharjahmuseums.Tools.Localization;
import com.asgatech.sharjahmuseums.Tools.SharedTool.UserData;
import com.asgatech.sharjahmuseums.Tools.Utils;

import java.util.List;

import okhttp3.ResponseBody;

public class ViewVisitorsReviewActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerViewReviews;
    public ImageView toolbarHomeImageView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    ReviewVisitorsRequest reviewVisitorsRequest;
    private FloatingActionButton addReview;
   TextViewBold ErrorMessageTextView;
    private TextViewBold ToolbarTitleTextView;
    int museumsID;
    private String email,museumColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        setContentView(R.layout.activity_view_visitors_review);
        setToolBar();
        setUpView();
    }

    public void setToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbarHomeImageView = (ImageView) findViewById(R.id.toolbar_home_image_view);
        toolbarHomeImageView.setVisibility(View.VISIBLE);

        museumColor=getIntent().getStringExtra(ConstantUtils.MUSEUM_COLOR);
        Drawable background = toolbar.getBackground();
        if (museumColor != null) {
            Log.e("museumColor",museumColor);
            background.setColorFilter(Color.parseColor(museumColor), PorterDuff.Mode.SRC_IN);
        }

        ToolbarTitleTextView=findViewById(R.id.tv_toolbar_title);
        ToolbarTitleTextView.setText(getString(R.string.visitors_reviews));
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }

    private void setUpView() {
        recyclerViewReviews = (RecyclerView) findViewById(R.id.recyclerView_reviews);
        ErrorMessageTextView = (TextViewBold) findViewById(R.id.tv_error_message);
        recyclerViewReviews.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(ViewVisitorsReviewActivity.this);
        recyclerViewReviews.setLayoutManager(layoutManager);
        recyclerViewReviews.setItemAnimator(new DefaultItemAnimator());
        addReview = (FloatingActionButton) findViewById(R.id.iv_add_review);
        addReview.setOnClickListener(this);

        Drawable background = addReview.getBackground();
        if (museumColor != null) {
            Log.e("museumColor",museumColor);
            background.setColorFilter(Color.parseColor(museumColor), PorterDuff.Mode.SRC_IN);
        }

        if (getIntent().hasExtra(ConstantUtils.EXTRA_MUSEUMS_ID)) {
            museumsID = getIntent().getIntExtra((ConstantUtils.EXTRA_MUSEUMS_ID), 0);
            if (museumsID > 0) {
                reviewVisitorsRequest = new ReviewVisitorsRequest(museumsID, new UserData().getLocalization(this));
                GetReviewList(reviewVisitorsRequest);
            }
        }


    }


    private void GetReviewList(ReviewVisitorsRequest reviewVisitorsRequest) {
        ServerTool.GetReviewList(ViewVisitorsReviewActivity.this, reviewVisitorsRequest, new ServerTool.APICallBack<List<ReviewVisitorsResponse>>() {
            @Override
            public void onSuccess(List<ReviewVisitorsResponse> response) {
                if (response.size() != 0) {
                    setData(response);
                }else {
                    recyclerViewReviews.setVisibility(View.GONE);
                    ErrorMessageTextView.setVisibility(View.VISIBLE);

                }

            }

            @Override
            public void onFailed(int statusCode, ResponseBody responseBody) {

            }
        });
    }

    private void setData(final List<ReviewVisitorsResponse> data) {
        adapter = new ViewVisitorsReviewAdapter(this, data);
        recyclerViewReviews.setAdapter(adapter);

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
        if (getIntent().hasExtra(ConstantUtils.EXTRA_MUSEUMS_ID)) {
            museumsID = getIntent().getIntExtra((ConstantUtils.EXTRA_MUSEUMS_ID), 0);
            if (museumsID > 0) {
                reviewVisitorsRequest = new ReviewVisitorsRequest(museumsID, new UserData().getLocalization(this));
                GetReviewList(reviewVisitorsRequest);
            }
        }
        new Localization().setLanguage(ViewVisitorsReviewActivity.this, new UserData().getLocalization(ViewVisitorsReviewActivity.this));
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(ViewVisitorsReviewActivity.this, AddReviewActivity.class);
        intent.putExtra(ConstantUtils.EXTRA_MUSEUMS_ID, museumsID);
        intent.putExtra(ConstantUtils.MUSEUM_COLOR,museumColor);
        startActivity(intent);
    }
}
