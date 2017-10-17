package com.asgatech.sharjahmuseums.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asgatech.sharjahmuseums.Activities.Events.EventsListFragment;
import com.asgatech.sharjahmuseums.Activities.Home.HomeContract;
import com.asgatech.sharjahmuseums.Activities.OurMuseums.OurMuseumsFragment;
import com.asgatech.sharjahmuseums.Fragments.AboutUsFragment;
import com.asgatech.sharjahmuseums.Fragments.ContactUsFragment;
import com.asgatech.sharjahmuseums.Fragments.EducationListFragment;
import com.asgatech.sharjahmuseums.Fragments.NearbyFragment;
import com.asgatech.sharjahmuseums.Fragments.NotificationListFragment;
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
    private HomeContract.UserAction presenter;
    private int selectedItem = 2;
    private static final int HEADER_VIEW_TYPE = 1;
    private static final int MENU_TYPE = 2;
    private static final int FOOTER_VIEW_TYPE = 3;
    private List<NavigationDrawerItem> data = Collections.emptyList();
    private LayoutInflater inflater;
    private HomeContract.ModelView view;
    private Context context;

    public NavigationDrawerAdapter(Context context, HomeContract.ModelView view,HomeContract.UserAction presenter, List<NavigationDrawerItem> data) {
        this.presenter = presenter;
        this.view = view;
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
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
                            view.closeDrawer();

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
                                    presenter.openHome();
                                    break;
                                case 2:
//                                    view.toolbarHomeImageView.setVisibility(View.VISIBLE);
//                                    view.toolbarLogoImageView.setVisibility(View.GONE);
                                    presenter.openFragment(new OurMuseumsFragment(), null);
                                    break;
                                case 3:
                                    presenter.openFragment(new PlanYourVisitFragment(), null);
                                    break;
                                case 4:
                                    presenter.openFragment(new EventsListFragment(), null);
                                    break;
                                case 5:
                                    presenter.openFragment(new NearbyFragment(), null);
                                    break;
                                case 6:
                                    presenter.openFragment(new EducationListFragment(), null);
                                    break;
                                case 7:
                                    presenter.openFragment(new AboutUsFragment(), null);
                                    break;
                                case 8:
                                    presenter.openFragment(new ContactUsFragment(), null);
                                    break;

                                case 9:
                                    presenter.openFragment(new NotificationListFragment(), null);
                                    break;
                                case 10:
                                    presenter.openFragment(new SettingFragment(), null);
                                    break;
                            }
                        }
                    });

                    break;
                case FOOTER_VIEW_TYPE:
                    ((FooterViewHolder) holder).footerImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            view.closeDrawer();
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
