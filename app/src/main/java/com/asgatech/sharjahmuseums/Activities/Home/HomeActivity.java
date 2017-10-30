package com.asgatech.sharjahmuseums.Activities.Home;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
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
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.asgatech.sharjahmuseums.Adapters.NavigationDrawerAdapter;
import com.asgatech.sharjahmuseums.Models.NavigationDrawerItem;
import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.CustomFonts.TextViewBold;
import com.asgatech.sharjahmuseums.Tools.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
                mPresenter.openHome();
                mToolbarTitleTextView.setText("");
                break;
        }
    }


    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            if (getSupportFragmentManager().popBackStackImmediate()) {
                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content_main);
                if ((fragment instanceof HomeFragment)) {
                    mToolbarTitleTextView.setText("");
                    showLogo();
                }else {
                    hideLogo();
                }
            }
            showToolbar();
            restToolbarColor();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void initView() {
        mDrawerRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mDrawerRecyclerView.setLayoutManager(mLayoutManager);
        NavigationDrawerAdapter adapter = new NavigationDrawerAdapter(getApplicationContext(), this, mPresenter, getData());
        mDrawerRecyclerView.setAdapter(adapter);
        showLogo();
        mPresenter.openHome();
    }

    @Override
    public void setupNavigationDrawer() {
        setSupportActionBar(mToolbar);
        mToolbarHomeImageView.setOnClickListener(this);
        toogleButtonActionBarDrawerToggle = new ActionBarDrawerToggle(this, mNavigationViewDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toogleButtonActionBarDrawerToggle.setDrawerIndicatorEnabled(false);
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_side_menu, this.getTheme());
        toogleButtonActionBarDrawerToggle.setHomeAsUpIndicator(drawable);
        toogleButtonActionBarDrawerToggle.setToolbarNavigationClickListener(v -> {
            if (mNavigationViewDrawerLayout.isDrawerVisible(GravityCompat.START)) {
                mNavigationViewDrawerLayout.closeDrawer(GravityCompat.START);
            } else {
                mNavigationViewDrawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    @Override
    public void closeDrawer() {
        mNavigationViewDrawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    public void changeToolbarTitle(String title) {
        mToolbarTitleTextView.setText(title);
    }

    @Override
    public void changeToolbarColor(String color) {
        if (color != null) {
            mToolbar.setBackgroundColor(Color.parseColor(color));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(Utils.getDarkColor(Color.parseColor(color)));
            }
        }
    }

    @Override
    public void restToolbarColor() {
        mToolbar.setBackgroundColor(getResources().getColor(R.color.GreenLight));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Utils.getDarkColor(getResources().getColor(R.color.GreenLight)));
        }
    }

    @Override
    public void openFragmentFromChild(Fragment fragment, Bundle bundle, boolean shouldAddToBackStack) {
        mPresenter.openFragment(fragment, bundle, shouldAddToBackStack);
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

    @Override
    public HomeContract.UserAction getPresenter() {
        if (mPresenter != null)
            return mPresenter;
        return null;
    }

    @Override
    public void hideToolbar() {
        mToolbar.setVisibility(View.GONE);
    }

    @Override
    public void showToolbar() {
        mToolbar.setVisibility(View.VISIBLE);
    }
}
