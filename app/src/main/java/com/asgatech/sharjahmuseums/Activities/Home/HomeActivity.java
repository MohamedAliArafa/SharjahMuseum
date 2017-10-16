package com.asgatech.sharjahmuseums.Activities.Home;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.asgatech.sharjahmuseums.Adapters.NavigationDrawerAdapter;
import com.asgatech.sharjahmuseums.Fragments.HomeFragment;
import com.asgatech.sharjahmuseums.Models.InsertDevicetokenRequestModel;
import com.asgatech.sharjahmuseums.Models.NavigationDrawerItem;
import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.Connection.ServerTool;
import com.asgatech.sharjahmuseums.Tools.CustomFonts.TextViewBold;
import com.asgatech.sharjahmuseums.Tools.SharedTool.UserData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;

public class HomeActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, HomeContract.ModelView {

    private ActionBarDrawerToggle toogleButtonActionBarDrawerToggle;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_logo_image_view)
    ImageView mToolbarLogoImageView;
    @BindView(R.id.toolbar_home_image_view)
    ImageView mToolbarHomeImageView;
    @BindView(R.id.tv_toolbar_title)
    TextViewBold mToolbarTitleTextView;
    @BindView(R.id.drawer_recycler_view)
    RecyclerView mDrawerRecyclerView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mNavigationViewDrawerLayout;

    UserData mUserData;
    private HomePresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        mPresenter = new HomePresenter(this, getSupportFragmentManager());
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        initView();
        setupNavigationDrawer();
    }

    public void insertDeviceToken() {
        String androidID = Settings.Secure.getString(HomeActivity.this.getContentResolver(), Settings.Secure.ANDROID_ID);

//        InsertDevicetokenRequestModel insertDevicetokenRequestModel=new InsertDevicetokenRequestModel(androidID, FirebaseInstanceId.getInstance().getToken());
        InsertDevicetokenRequestModel insertDevicetokenRequestModel = new InsertDevicetokenRequestModel(androidID, "");
        InsertDeviceToken(insertDevicetokenRequestModel);
    }

    private void InsertDeviceToken(InsertDevicetokenRequestModel insertDevicetokenRequestModel) {
        ServerTool.InsertDeviceToken(this, insertDevicetokenRequestModel, new ServerTool.APICallBack<Integer>() {
            @Override
            public void onSuccess(Integer response) {
                if (response == 1) {
                    Log.e("InsertDeviceToken", "Success");
                    mUserData.saveUserStateOfInsertToken(HomeActivity.this, true, mUserData.TAG_INSERT_TOKEN);

                } else {
                    Log.e("InsertDeviceToken", "failure to insert token");
//                    Toast.makeText(HomeActivity.this, "failure to insert token", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailed(int statusCode, ResponseBody responseBody) {

            }
        });

    }

    public List<NavigationDrawerItem> getData() {
        List<NavigationDrawerItem> list = new ArrayList<>();
        list.add(new NavigationDrawerItem(getString(R.string.app_name), NavigationDrawerItem.HEADER_VIEW_TYPE));
        list.add(new NavigationDrawerItem(getString(R.string.home), NavigationDrawerItem.MENU_TYPE));
        list.add(new NavigationDrawerItem(getString(R.string.our_museums), NavigationDrawerItem.MENU_TYPE));
        list.add(new NavigationDrawerItem(getString(R.string.plan_your_visit), NavigationDrawerItem.MENU_TYPE));
        list.add(new NavigationDrawerItem(getString(R.string.Events), NavigationDrawerItem.MENU_TYPE));
        list.add(new NavigationDrawerItem(getString(R.string.near_by_facilities), NavigationDrawerItem.MENU_TYPE));
        list.add(new NavigationDrawerItem(getString(R.string.education), NavigationDrawerItem.MENU_TYPE));
        list.add(new NavigationDrawerItem(getString(R.string.about_us), NavigationDrawerItem.MENU_TYPE));
        list.add(new NavigationDrawerItem(getString(R.string.contact_us), NavigationDrawerItem.MENU_TYPE));
        list.add(new NavigationDrawerItem(getString(R.string.notifications), NavigationDrawerItem.MENU_TYPE));
        list.add(new NavigationDrawerItem(getString(R.string.setting), NavigationDrawerItem.MENU_TYPE));
        list.add(new NavigationDrawerItem(getString(R.string.demo), NavigationDrawerItem.MENU_TYPE));
        list.add(new NavigationDrawerItem(getString(R.string.app_name), NavigationDrawerItem.FOOTER_VIEW_TYPE));
        return list;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_home_image_view:
                mPresenter.openFragment(new HomeFragment(), null);
                mToolbarTitleTextView.setText(" ");
                break;
        }
    }


    @Override
    public void onBackPressed() {

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content_main);
        if (!(fragment instanceof HomeFragment)) {
            mPresenter.openFragment(new HomeFragment(), null);
            mToolbarTitleTextView.setText("");
            mToolbarHomeImageView.setVisibility(View.GONE);
            mToolbarLogoImageView.setVisibility(View.VISIBLE);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public void initView() {
        mUserData = new UserData();
        mDrawerRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mDrawerRecyclerView.setLayoutManager(mLayoutManager);
        NavigationDrawerAdapter adapter = new NavigationDrawerAdapter(getApplicationContext(), this, mPresenter, getData());
        mDrawerRecyclerView.setAdapter(adapter);
        mToolbarHomeImageView.setVisibility(View.GONE);
        mToolbarLogoImageView.setVisibility(View.VISIBLE);
        mPresenter.openFragment(new HomeFragment(), null);
        if (!mUserData.getUserStateOfInsertToken(this)) {
            insertDeviceToken();
        }
    }

    @Override
    public void setupNavigationDrawer() {
        setSupportActionBar(mToolbar);
        mToolbarHomeImageView.setOnClickListener(this);
        toogleButtonActionBarDrawerToggle = new ActionBarDrawerToggle(this, mNavigationViewDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toogleButtonActionBarDrawerToggle.setDrawerIndicatorEnabled(false);
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_side_menu, this.getTheme());
        toogleButtonActionBarDrawerToggle.setHomeAsUpIndicator(drawable);
        toogleButtonActionBarDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mNavigationViewDrawerLayout.isDrawerVisible(GravityCompat.START)) {
                    mNavigationViewDrawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    mNavigationViewDrawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });
    }

    @Override
    public void setupToolbar() {

    }

    @Override
    public void closeDrawer() {
        mNavigationViewDrawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void changeToolbarTitle(String title) {
        mToolbarTitleTextView.setText(title);
    }

    @Override
    public void openFragmentFromChild(Fragment fragment, Bundle bundle) {
        mPresenter.openFragment(fragment, bundle);
    }

    @Override
    public void hideLogo() {
        mToolbarHomeImageView.setVisibility(View.VISIBLE);
        mToolbarLogoImageView.setVisibility(View.GONE);
    }

    @Override
    public void showLogo() {
        mToolbarHomeImageView.setVisibility(View.GONE);
        mToolbarLogoImageView.setVisibility(View.VISIBLE);
    }
}
