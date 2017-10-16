package com.asgatech.sharjahmuseums.Activities;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import com.asgatech.sharjahmuseums.Fragments.AboutUsFragment;
import com.asgatech.sharjahmuseums.Fragments.ContactUsFragment;
import com.asgatech.sharjahmuseums.Fragments.EducationListFragment;
import com.asgatech.sharjahmuseums.Fragments.EventsFragment;
import com.asgatech.sharjahmuseums.Fragments.HomeFragment;
import com.asgatech.sharjahmuseums.Fragments.NearbyFragment;
import com.asgatech.sharjahmuseums.Fragments.NotificationListFragment;
import com.asgatech.sharjahmuseums.Fragments.OurMuseumsFragment;
import com.asgatech.sharjahmuseums.Fragments.PlanYourVisitFragment;
import com.asgatech.sharjahmuseums.Fragments.SettingFragment;
import com.asgatech.sharjahmuseums.Models.InsertDevicetokenRequestModel;
import com.asgatech.sharjahmuseums.Models.NavigationDrawerItem;
import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.Connection.ServerTool;
import com.asgatech.sharjahmuseums.Tools.CustomFonts.TextViewBold;
import com.asgatech.sharjahmuseums.Tools.SharedTool.UserData;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    public DrawerLayout navigationViewdrawerLayout;
    private ActionBarDrawerToggle toogleButtonActionBarDrawerToggle;
    public Toolbar toolbar;
    public ImageView toolbarLogoImageView, toolbarHomeImageView;
    public TextViewBold ToolbarTitleTextView;

    private RecyclerView drawerRecyclerView;
    UserData userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        setContentView(R.layout.activity_home);
        setUpNavigation();
        initView();
    }

    private void setUpNavigation() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.GreenLight));
        setSupportActionBar(toolbar);
        navigationViewdrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbarLogoImageView = (ImageView) findViewById(R.id.toolbar_logo_image_view);
        toolbarHomeImageView = (ImageView) findViewById(R.id.toolbar_home_image_view);
        ToolbarTitleTextView=findViewById(R.id.tv_toolbar_title);
        toolbarHomeImageView.setOnClickListener(this);
        toogleButtonActionBarDrawerToggle = new ActionBarDrawerToggle(this, navigationViewdrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toogleButtonActionBarDrawerToggle.setDrawerIndicatorEnabled(false);
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_side_menu, this.getTheme());
        toogleButtonActionBarDrawerToggle.setHomeAsUpIndicator(drawable);
        toogleButtonActionBarDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (navigationViewdrawerLayout.isDrawerVisible(GravityCompat.START)) {
                    navigationViewdrawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    navigationViewdrawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });
    }

    private void initView() {
        userData=new UserData();
        drawerRecyclerView = (RecyclerView) findViewById(R.id.drawer_recycler_view); // Assigning the RecyclerView Object to the xml View
        drawerRecyclerView.setHasFixedSize(true);                             // Setting the adapter to RecyclerView
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);                 // Creating a layout Manager
        drawerRecyclerView.setLayoutManager(mLayoutManager);
        NavigationDrawerAdapter adapter = new NavigationDrawerAdapter(this, getData());
        drawerRecyclerView.setAdapter(adapter);
        toolbarHomeImageView.setVisibility(View.GONE);
        toolbarLogoImageView.setVisibility(View.VISIBLE);
        openFragment(HomeFragment.class, null);
        if (!userData.getUserStateOfInsertToken(this)){
            insertDeviceToken();
        }



    }

   public void insertDeviceToken(){
        String androidID = Settings.Secure.getString(HomeActivity.this.getContentResolver(), Settings.Secure.ANDROID_ID);

//        InsertDevicetokenRequestModel insertDevicetokenRequestModel=new InsertDevicetokenRequestModel(androidID, FirebaseInstanceId.getInstance().getToken());
        InsertDevicetokenRequestModel insertDevicetokenRequestModel=new InsertDevicetokenRequestModel(androidID,"");
        InsertDeviceToken(insertDevicetokenRequestModel);
    }

    private void InsertDeviceToken(InsertDevicetokenRequestModel insertDevicetokenRequestModel) {
        ServerTool.InsertDeviceToken(this, insertDevicetokenRequestModel, new ServerTool.APICallBack<Integer>() {
            @Override
            public void onSuccess(Integer response) {
                if (response == 1) {
                    Log.e("InsertDeviceToken","Success");
                   userData.saveUserStateOfInsertToken(HomeActivity.this,true,userData.TAG_INSERT_TOKEN);

                } else {
                    Log.e("InsertDeviceToken","failure to insert token");
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

    public void openFragment(Class fragmentClass, Bundle bundle) {
        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        transaction.replace(R.id.content_main, fragment);
        transaction.commit();
        navigationViewdrawerLayout.closeDrawer(GravityCompat.START);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_home_image_view:
                break;
        }
    }


    @Override
    public void onBackPressed() {

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content_main);
//        if (fragment instanceof EventDetailsFragment) {
//            openFragment(EventsFragment.class,null);

//        } else
            if (fragment instanceof EventsFragment) {
            openFragment(HomeFragment.class, null);
        } else if (fragment instanceof ContactUsFragment) {
            openFragment(HomeFragment.class, null);

        } else if (fragment instanceof NotificationListFragment) {
            openFragment(HomeFragment.class, null);

        } else if (fragment instanceof SettingFragment) {
            openFragment(HomeFragment.class, null);

        } else if (fragment instanceof OurMuseumsFragment) {
            openFragment(HomeFragment.class, null);

        }else if (fragment instanceof EducationListFragment) {
                openFragment(HomeFragment.class,null);

        }else if (fragment instanceof AboutUsFragment) {
            openFragment(HomeFragment.class,null);

        }else if (fragment instanceof NearbyFragment) {
            openFragment(HomeFragment.class,null);
        }else if (fragment instanceof PlanYourVisitFragment) {
            openFragment(HomeFragment.class,null);
        } else  {

            super.onBackPressed();
        }

    }
}
