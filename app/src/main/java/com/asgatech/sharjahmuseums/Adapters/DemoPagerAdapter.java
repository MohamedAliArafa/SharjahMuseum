package com.asgatech.sharjahmuseums.Adapters;


import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.asgatech.sharjahmuseums.Models.DemoImage;
import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.Connection.ConstantUtils;
import com.asgatech.sharjahmuseums.Tools.Connection.URLS;
import com.asgatech.sharjahmuseums.Tools.GlideApp;
import com.asgatech.sharjahmuseums.Tools.Localization;
import com.asgatech.sharjahmuseums.Tools.SharedTool.UserData;
import com.bumptech.glide.load.Option;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class DemoPagerAdapter extends PagerAdapter {

    /**
     *
     */
    List<DemoImage> items;
    Context context;
    int indexIndecator = 0;

    public DemoPagerAdapter(Context context, List<DemoImage> items) {
        this.context = context;
        this.items = items;
    }


    @Override
    public int getCount() {
        return items.size();
    }


    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.view_pager_item_imageview, collection, false);
        ImageView img = layout.findViewById(R.id.view_paget_image);
        if (UserData.getLocalization(context) == Localization.ARABIC_VALUE) {
            GlideApp.with(context).load(URLS.URL_BASE + items.get(position).getImage())
                    .apply(RequestOptions.option(Option.memory(ConstantUtils.GLIDE_TIMEOUT), 0))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.mipmap.ic_launcher).into(img);
        }else {
            GlideApp.with(context).load(URLS.URL_BASE + items.get(position).getImageEN())
                    .apply(RequestOptions.option(Option.memory(ConstantUtils.GLIDE_TIMEOUT), 0))
                    .placeholder(R.mipmap.ic_launcher).into(img);
        }
        collection.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        //if i removed the view recycler view that was deleted goes blank
//        collection.removeView((View) view);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


}

