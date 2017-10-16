package com.asgatech.sharjahmuseums.Adapters;


import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.asgatech.sharjahmuseums.Models.EventImage;
import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.Connection.ConstantUtils;
import com.asgatech.sharjahmuseums.Tools.Connection.URLS;
import com.asgatech.sharjahmuseums.Tools.GlideApp;
import com.bumptech.glide.load.Option;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {

    /**
     *
     */
    List<EventImage> items;
    Context context;
    int indexIndecator = 0;

    public ViewPagerAdapter(Context context, List<EventImage> items) {
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
        GlideApp.with(context).load(URLS.URL_BASE +items.get(position).getImage())
                .apply(RequestOptions.option(Option.memory(ConstantUtils.GLIDE_TIMEOUT), 0))
                .placeholder(R.mipmap.ic_launcher).into(img);
//        List<EventImage> list = new ArrayList<>();
//        RecyclerView recyclerView = (RecyclerView) layout.findViewById(R.id.recyclerView);
//        for (int i = indexIndecator; i < indexIndecator + 4; i++) {
//            if (i < items.size())
//                list.add(items.get(i));
//        }
//        indexIndecator+=4;
//        MainActivityPagerRecyclerAdapter adapter = new MainActivityPagerRecyclerAdapter(context, list);
//        recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
//        recyclerView.setAdapter(adapter);
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

