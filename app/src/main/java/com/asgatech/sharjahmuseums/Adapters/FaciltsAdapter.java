package com.asgatech.sharjahmuseums.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.asgatech.sharjahmuseums.Models.MuseumsDetailsModel;
import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.Connection.URLS;
import com.asgatech.sharjahmuseums.Tools.Utils;

import java.util.List;

/**
 * Created by halima.reda on 9/13/2017.
 */

public class FaciltsAdapter extends RecyclerView.Adapter<FaciltsAdapter.ViewHolder> {
    private Context context;
    private List<MuseumsDetailsModel.FaciltsEntity> faciltsList;

    public FaciltsAdapter(Context context, List<MuseumsDetailsModel.FaciltsEntity> response) {
        this.context = context;
        this.faciltsList = response;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_row_facilities_image, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.e("image Link", URLS.URL_BASE + faciltsList.get(position).getImage());
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
