package com.asgatech.sharjahmuseums.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.asgatech.sharjahmuseums.Models.HighLightEntity;
import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.Connection.URLS;
import com.asgatech.sharjahmuseums.Tools.Utils;

import java.util.ArrayList;
import java.util.List;


public class HighLightAdapter extends BaseAdapter {
    private Context context;
    private List<HighLightEntity> hightLightList = new ArrayList<>();

    public HighLightAdapter(Context context,List<HighLightEntity> hightLightList ) {
        this.context = context;
        this.hightLightList=hightLightList;
    }

    public HighLightAdapter() {
    }

    public void setData(List<HighLightEntity> data) {
        this.hightLightList = data;
    }


    @Override
    public int getCount() {
        return hightLightList.size();
    }

    @Override
    public Object getItem(int pos) {
        return hightLightList.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = convertView;

        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.layout_row_image_slider, null);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.image = (ImageView) rowView
                    .findViewById(R.id.image_view);
            rowView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) rowView.getTag();
        Log.e(context.getString(R.string.tag_image), URLS.URL_BASE + hightLightList.get(position).getPhoto());
        Utils.loadSimplePic(context, URLS.URL_BASE + hightLightList.get(position).getPhoto(), holder.image);

        return rowView;
    }


    static class ViewHolder {
        public TextView text;
        public ImageView image;
    }
}
