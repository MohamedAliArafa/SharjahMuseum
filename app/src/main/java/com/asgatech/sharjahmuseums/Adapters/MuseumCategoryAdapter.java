package com.asgatech.sharjahmuseums.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.asgatech.sharjahmuseums.Activities.NotificationDetailActivity;
import com.asgatech.sharjahmuseums.Models.MuseumCategoryResponse;
import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.Connection.ConstantUtils;
import com.asgatech.sharjahmuseums.Tools.Connection.URLS;
import com.asgatech.sharjahmuseums.Tools.CustomFonts.TextViewBold;
import com.asgatech.sharjahmuseums.Tools.GlideApp;
import com.bumptech.glide.load.Option;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

/**
 * Created by Esraa on 10/5/2017.
 */

public class MuseumCategoryAdapter extends RecyclerView.Adapter<MuseumCategoryAdapter.MyViewHolder> {
    Context context;
    private List<MuseumCategoryResponse> data;

    public MuseumCategoryAdapter(Context context, List<MuseumCategoryResponse> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_search_category_list_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.titleTextView.setText(data.get(position).getTitle());

        GlideApp.with(context).load(URLS.URL_BASE + data.get(position).getImage())
                .apply(RequestOptions.option(Option.memory(ConstantUtils.GLIDE_TIMEOUT), 0))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.no_image).into(holder.categoryImageView);

        holder.itemView.setOnClickListener(view -> {
            Intent intent= new Intent(context, NotificationDetailActivity.class);
            intent.putExtra("title",data.get(position).getTitle());
            intent.putExtra("image",data.get(position).getImage());
            intent.putExtra("id",data.get(position).getID());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView categoryImageView;
        private TextViewBold titleTextView;


        MyViewHolder(View view) {
            super(view);
            categoryImageView = view.findViewById(R.id.category_image);
            titleTextView = view.findViewById(R.id.category_title);
        }
    }
}
