package com.asgatech.sharjahmuseums.Adapters;

import android.support.v4.view.GravityCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asgatech.sharjahmuseums.Activities.HomeActivity;
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
import com.asgatech.sharjahmuseums.Models.NavigationDrawerItem;
import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.SharedTool.UserData;

import java.util.Collections;
import java.util.List;

/**
 * Created by halima.reda on 12/15/2015.
 */
public class NavigationDrawerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private int selectedItem = 2;
    public static final int HEADER_VIEW_TYPE = 1;
    public static final int MENU_TYPE = 2;
    public static final int FOOTER_VIEW_TYPE = 3;
    List<NavigationDrawerItem> data = Collections.emptyList();
    private LayoutInflater inflater;
    private HomeActivity context;

    public NavigationDrawerAdapter(HomeActivity context, List<NavigationDrawerItem> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case HEADER_VIEW_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_header_navigation_view, parent, false);
                return new HeaderViewHolder(view);
            case MENU_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_row_navigation_drawer, parent, false);
                return new MenuViewHolder(view);
            case FOOTER_VIEW_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_footer_navigation_view, parent, false);
                return new FooterViewHolder(view);
        }
        return null;
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        NavigationDrawerItem object = data.get(position);
        final UserData userData = new UserData();
        Log.d("local", userData.getLocalization(context) + "");
        if (object != null) {
            switch (object.getType()) {
                case HEADER_VIEW_TYPE:
                    ((HeaderViewHolder) holder).menu.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            context.navigationViewdrawerLayout.closeDrawer(GravityCompat.START);

                        }
                    });
                    break;

                case MENU_TYPE:
                    ((MenuViewHolder) holder).mTitle.setText(object.getTitle());
                    ((MenuViewHolder) holder).linear.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            switch (position) {
                                case 1:
                                    context.toolbarHomeImageView.setVisibility(View.GONE);
                                    context.toolbarLogoImageView.setVisibility(View.VISIBLE);
                                    context.openFragment(HomeFragment.class, null);
                                    break;
                                case 2:
                                    context.toolbarHomeImageView.setVisibility(View.VISIBLE);
                                    context.toolbarLogoImageView.setVisibility(View.GONE);
                                    context.openFragment(OurMuseumsFragment.class, null);
                                    break;
                                case 3:
                                    context.toolbarHomeImageView.setVisibility(View.VISIBLE);
                                    context.toolbarLogoImageView.setVisibility(View.GONE);
                                    context.openFragment(PlanYourVisitFragment.class, null);
                                    break;
                                case 4:
                                    context.toolbarHomeImageView.setVisibility(View.VISIBLE);
                                    context.toolbarLogoImageView.setVisibility(View.GONE);
                                    context.openFragment(EventsFragment.class, null);
                                    break;
                                case 5:
                                    context.toolbarHomeImageView.setVisibility(View.VISIBLE);
                                    context.toolbarLogoImageView.setVisibility(View.GONE);
                                    context.openFragment(NearbyFragment.class, null);
                                    break;
                                case 6:
                                    context.toolbarHomeImageView.setVisibility(View.VISIBLE);
                                    context.toolbarLogoImageView.setVisibility(View.GONE);
                                    context.openFragment(EducationListFragment.class, null);
                                    break;
                                case 7:
                                    context.toolbarHomeImageView.setVisibility(View.VISIBLE);
                                    context.toolbarLogoImageView.setVisibility(View.GONE);
                                    context.openFragment(AboutUsFragment.class, null);
                                    break;
                                case 8:
                                    context.toolbarHomeImageView.setVisibility(View.VISIBLE);
                                    context.toolbarLogoImageView.setVisibility(View.GONE);
                                    context.openFragment(ContactUsFragment.class, null);
                                    break;

                                case 9:
                                    context.toolbarHomeImageView.setVisibility(View.VISIBLE);
                                    context.toolbarLogoImageView.setVisibility(View.GONE);
                                    context.openFragment(NotificationListFragment.class, null);
                                    break;
                                case 10:
                                    context.toolbarHomeImageView.setVisibility(View.VISIBLE);
                                    context.toolbarLogoImageView.setVisibility(View.GONE);
                                    context.openFragment(SettingFragment.class, null);
                                    break;
                            }
                        }
                    });

                    break;
                case FOOTER_VIEW_TYPE:
                    ((FooterViewHolder) holder).footerImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            context.navigationViewdrawerLayout.closeDrawer(GravityCompat.START);

                        }
                    });
                    break;
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (data != null) {
            NavigationDrawerItem object = data.get(position);
            if (object != null) {
                return object.getType();
            }
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        if (data == null)
            return 0;
        return data.size();
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        private ImageView menu;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            menu = (ImageView) itemView.findViewById(R.id.menu_image_view);
        }
    }

    public static class FooterViewHolder extends RecyclerView.ViewHolder {
        private ImageView footerImage;

        public FooterViewHolder(View itemView) {
            super(itemView);
            footerImage = (ImageView) itemView.findViewById(R.id.footer_image_view);
        }
    }


    public static class MenuViewHolder extends RecyclerView.ViewHolder {
        private TextView mTitle;
        private LinearLayout linear;

        public MenuViewHolder(View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.menu_item_text_view);
            linear = (LinearLayout) itemView.findViewById(R.id.linear);
        }
    }
}
