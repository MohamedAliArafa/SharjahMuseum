package com.asgatech.sharjahmuseums.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asgatech.sharjahmuseums.Activities.NotificationDetailActivity;
import com.asgatech.sharjahmuseums.Models.NotificationListResponseModel;
import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.CircleImageView;
import com.asgatech.sharjahmuseums.Tools.Connection.ConstantUtils;
import com.asgatech.sharjahmuseums.Tools.Connection.URLS;
import com.asgatech.sharjahmuseums.Tools.CustomFonts.TextViewBold;
import com.asgatech.sharjahmuseums.Tools.CustomFonts.TextViewLight;
import com.asgatech.sharjahmuseums.Tools.GlideApp;
import com.bumptech.glide.load.Option;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

/**
 * Created by Esraa on 10/5/2017.
 */

public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.MyViewHolder> {
    Context context;
    List<NotificationListResponseModel> data;

    public NotificationListAdapter(Context context, List<NotificationListResponseModel> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_notification_list_row, parent, false);


        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {


        holder.describtionTv.setText(data.get(position).getText());
        holder.titleTv.setText(data.get(position).getTitle());
//
        GlideApp.with(context).load(URLS.URL_BASE + data.get(position).getImage())
                .apply(RequestOptions.option(Option.memory(ConstantUtils.GLIDE_TIMEOUT), 0))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.no_image).into(holder.notificationIv);

        holder.itemView.setOnClickListener(view -> {
            Intent intent= new Intent(context, NotificationDetailActivity.class);
            intent.putExtra("title",data.get(position).getTitle());
            intent.putExtra("text",data.get(position).getText());
            intent.putExtra("image",data.get(position).getImage());
            intent.putExtra("id",data.get(position).getUserNotfactionID());

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

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView notificationIv;
        private TextViewBold titleTv;
        private TextViewLight describtionTv;


        public MyViewHolder(View view) {
            super(view);
            notificationIv = (CircleImageView) view.findViewById(R.id.notification_iv);
            titleTv = (TextViewBold) view.findViewById(R.id.title_tv);
            describtionTv = (TextViewLight) view.findViewById(R.id.describtion_tv);


        }
    }
}
