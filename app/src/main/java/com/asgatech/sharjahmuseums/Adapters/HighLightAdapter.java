package com.asgatech.sharjahmuseums.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.asgatech.sharjahmuseums.Models.HighLightEntity;
import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.Connection.URLS;
import com.asgatech.sharjahmuseums.Tools.Utils;

import java.util.ArrayList;
import java.util.List;


public class HighLightAdapter extends RecyclerView.Adapter<HighLightAdapter.ViewHolder> {
    private Context context;
    private List<HighLightEntity> hightLightList = new ArrayList<>();
    getItemClick getItemClick;

    public HighLightAdapter(Context context, List<HighLightEntity> hightLightList, getItemClick getItemClick) {
        this.context = context;
        this.hightLightList = hightLightList;
        this.getItemClick = getItemClick;
    }

    public HighLightAdapter() {
    }

    @Override
    public HighLightAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_image_slider, parent, false);
        return new HighLightAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HighLightAdapter.ViewHolder holder, final int position) {
        Log.e(context.getString(R.string.tag_image), URLS.URL_BASE + hightLightList.get(position).getPhoto());
        Utils.loadSimplePic(context, URLS.URL_BASE + hightLightList.get(position).getPhoto(), holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getItemClick.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {

        return hightLightList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        ViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.image_view);
        }
    }

    public interface getItemClick {
        void onItemClick(int position);
    }

}
