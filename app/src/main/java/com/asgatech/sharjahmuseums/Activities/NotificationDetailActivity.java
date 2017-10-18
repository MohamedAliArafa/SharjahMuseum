package com.asgatech.sharjahmuseums.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.asgatech.sharjahmuseums.Activities.Home.HomeActivity;
import com.asgatech.sharjahmuseums.Models.UpdateRequestModel;
import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.CircleImageView;
import com.asgatech.sharjahmuseums.Tools.Connection.ServerTool;
import com.asgatech.sharjahmuseums.Tools.CustomFonts.TextViewBold;
import com.asgatech.sharjahmuseums.Tools.CustomFonts.TextViewLight;
import com.asgatech.sharjahmuseums.Tools.Localization;
import com.asgatech.sharjahmuseums.Tools.SharedTool.UserData;
import com.asgatech.sharjahmuseums.Tools.Utils;

import okhttp3.ResponseBody;

public class NotificationDetailActivity extends AppCompatActivity implements View.OnClickListener{
    public ImageView toolbarHomeImageView;
    private CircleImageView ivMainimage;
    private TextViewBold tvTitle,ToolbarTitleTextView;
    private TextViewLight tvDescription;

    String title,text,image;
    int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        setContentView(R.layout.activity_notification_detail);
        setToolBar();
        setUpView();
    }

    public void setToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbarHomeImageView = (ImageView) findViewById(R.id.toolbar_home_image_view);
        ToolbarTitleTextView=findViewById(R.id.tv_toolbar_title);
        toolbarHomeImageView.setVisibility(View.VISIBLE);
        toolbarHomeImageView.setOnClickListener(this);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }


void setUpView(){
    ivMainimage = (CircleImageView) findViewById(R.id.iv_mainimage);
    tvTitle = (TextViewBold) findViewById(R.id.tv_title);
    tvDescription = (TextViewLight) findViewById(R.id.tv_description);

    if (getIntent().hasExtra("title")){
        title=getIntent().getStringExtra("title");
    }
    if (getIntent().hasExtra("text")){
        text=getIntent().getStringExtra("text");
    }
    if (getIntent().hasExtra("image")){
        image= getIntent().getStringExtra("image");
    }
    if (getIntent().hasExtra("id")){
        id= getIntent().getIntExtra("id",0);
    }
    tvDescription.setText(text);
    tvTitle.setText(title);
//    Glide.with(this).load(URLS.URL_BASE + image).placeholder(R.drawable.image_m).into(ivMainimage);
    UpdateRequestModel updateRequestModel= new UpdateRequestModel(id);
    UpdateNotifiList(updateRequestModel);

}
void UpdateNotifiList(UpdateRequestModel updateRequestModel){
    ServerTool.UpdateNotifiList(NotificationDetailActivity.this, updateRequestModel, new ServerTool.APICallBack<Integer>() {
        @Override
        public void onSuccess(Integer response) {
            if (response==1){
             //   Toast.makeText(NotificationDetailActivity.this, "SUCCESS", Toast.LENGTH_SHORT).show();
            }else {
              // Toast.makeText(NotificationDetailActivity.this, "fail", Toast.LENGTH_SHORT).show();

            }
        }

        @Override
        public void onFailed(int statusCode, ResponseBody responseBody) {

        }
    });
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
        new Localization().setLanguage(NotificationDetailActivity.this, new UserData().getLocalization(NotificationDetailActivity.this));
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.toolbar_home_image_view:
                Intent in = new Intent(this, HomeActivity.class);
                in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(in);
                break;
        }
    }
}
