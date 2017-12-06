package com.asgatech.sharjahmuseums.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.asgatech.sharjahmuseums.Models.HighLightEntity;
import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.Connection.URLS;
import com.asgatech.sharjahmuseums.Tools.GlideApp;

import java.util.List;

import io.realm.RealmList;

/**
 * Created by ahmed.elmokadem on 2015-11-25.
 */
public class HighLighted extends BaseAdapter {

    private Context mContext;
    List<HighLightEntity> HightLight;

    public HighLighted(Context context, RealmList<HighLightEntity> hightLight) {
        mContext = context;
        HightLight = hightLight;
    }

//    public HighLighted(List<HighLightEntity> highlightList , String mMuseumColor) {
//        mContext = context;
//        HightLight = hightLight;
//    }

    private static class ViewHolder {
        public ImageView photo;

        public ViewHolder(View view) {
            photo = (ImageView) view.findViewById(R.id.image_cover);
        }
    }

    @Override
    public int getCount() {
        return HightLight.size();
    }

    @Override
    public Object getItem(int position) {
        return HightLight.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.fragment_high_light_overlaping, parent, false);
            holder = new ViewHolder(convertView);
            GlideApp.with(mContext).load(URLS.URL_BASE + HightLight.get(position).getPhoto()).placeholder(R.drawable.no_image).into(holder.photo);

//            Log.wtf("NEWS ADAPTER", URLS.URL_BASE + HightLight.get(position).getPhoto());

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
//        holder.photo.setOnClickListener(view -> {
//            Intent intent1 = new Intent(mContext, HighlightsDetailActivity.class);
//            intent1.putExtra(ConstantUtils.HIGHLIGHT_LIST, (Parcelable) HightLight);
//            intent1.putExtra(ConstantUtils.HIGHLIGHT_LIST_POSITION, position);
////            intent1.putExtra(ConstantUtils.HIGHLIGHT_COLOR, highlightColor);
//            mContext.startActivity(intent1);
//        });
        return convertView;
    }
}
