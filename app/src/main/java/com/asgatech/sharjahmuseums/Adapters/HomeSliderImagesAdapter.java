package com.asgatech.sharjahmuseums.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.asgatech.sharjahmuseums.Models.AllSliderModel;
import com.asgatech.sharjahmuseums.Models.MuseumsDetailsModel;
import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.Connection.URLS;
import com.asgatech.sharjahmuseums.Tools.Utils;

import java.util.List;


public class HomeSliderImagesAdapter extends PagerAdapter {

    private Activity activity;
    List<AllSliderModel> imageList;
    private int listType;
    List<MuseumsDetailsModel.ImageListEntity> imagesList;

    public HomeSliderImagesAdapter(Activity activity, List<AllSliderModel> imagesList, int type) {
        this.activity = activity;
        this.imageList = imagesList;
        this.listType = type;
    }

    public HomeSliderImagesAdapter(List<MuseumsDetailsModel.ImageListEntity> imagesList, Activity activity, int type) {
        this.activity = activity;
        this.imagesList = imagesList;
        this.listType = type;
    }

    @Override
    public int getCount() {
        if (listType == 1) {
            return imageList.size();
        } else if (listType == 2) {
            return imagesList.size();
        } else {
            return 0;
        }
    }

    public long getItemId(int position) {
        return position;
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) container.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View convertView = inflater.inflate(R.layout.layout_row_image_slider, null);
        ImageView imageItem = (ImageView) convertView.findViewById(R.id.image_view);
        if (listType == 1) {
            if (imageList.get(position) != null) {
                Log.e("url", URLS.URL_BASE + imageList.get(position).getImage());
                Utils.loadSimplePic(activity, URLS.URL_BASE + imageList.get(position).getImage(), imageItem);
            }
        } else if (listType == 2) {
            if (imagesList.get(position) != null) {
                Log.e("url", URLS.URL_BASE + imagesList.get(position).getImage());
                Utils.loadSimplePic(activity, URLS.URL_BASE + imagesList.get(position).getImage(), imageItem);
            }
        }
        container.addView(convertView);
        return convertView;
    }

    public void clear() {
        if (imageList != null) {
            imageList.clear();
            notifyDataSetChanged();
        }
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((ViewGroup) object);
    }


}

