package com.asgatech.sharjahmuseums.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.asgatech.sharjahmuseums.Models.FaciltsEntity;
import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.Connection.URLS;
import com.asgatech.sharjahmuseums.Tools.Utils;

import java.util.List;

/**
 * Created by halima.reda on 9/13/2017.
 */

public class FacilitiesAdapter extends RecyclerView.Adapter<FacilitiesAdapter.ViewHolder> {
    private Context context;
    private List<FaciltsEntity> faciltsList;

    public FacilitiesAdapter(Context context, List<FaciltsEntity> response) {
        this.context = context;
        this.faciltsList = response;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_facilities_image, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.e(context.getString(R.string.tag_image), URLS.URL_BASE + faciltsList.get(position).getImage());
        Utils.loadSimplePic(context, URLS.URL_BASE + faciltsList.get(position).getImage(), holder.imageView);

    }


    @Override
    public int getItemCount() {

        return faciltsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        ViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.image_view);
        }
    }
}
