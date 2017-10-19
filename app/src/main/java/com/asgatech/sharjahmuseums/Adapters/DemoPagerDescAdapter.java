package com.asgatech.sharjahmuseums.Adapters;


import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.asgatech.sharjahmuseums.Models.DemoImage;
import com.asgatech.sharjahmuseums.R;

import java.util.List;

public class DemoPagerDescAdapter extends PagerAdapter {

    /**
     *
     */
    List<DemoImage> items;
    Context context;
    int indexIndecator = 0;

    public DemoPagerDescAdapter(Context context, List<DemoImage> items) {
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
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.view_pager_item_textview, collection, false);
        TextView textView = layout.findViewById(R.id.view_pager_text);
        textView.setText(items.get(position).getDescription());
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

