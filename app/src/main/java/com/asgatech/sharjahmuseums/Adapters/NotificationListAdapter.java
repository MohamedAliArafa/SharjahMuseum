package com.asgatech.sharjahmuseums.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.asgatech.sharjahmuseums.Activities.Events.EventDetails.EventDetailsActivity;
import com.asgatech.sharjahmuseums.Activities.NotificationDetailActivity;
import com.asgatech.sharjahmuseums.Models.NotificationListResponseModel;
import com.asgatech.sharjahmuseums.Models.Request.UpdateRequestModel;
import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.CircleImageView;
import com.asgatech.sharjahmuseums.Tools.Connection.ConstantUtils;
import com.asgatech.sharjahmuseums.Tools.Connection.ServerTool;
import com.asgatech.sharjahmuseums.Tools.Connection.URLS;
import com.asgatech.sharjahmuseums.Tools.CustomFonts.TextViewBold;
import com.asgatech.sharjahmuseums.Tools.CustomFonts.TextViewLight;
import com.asgatech.sharjahmuseums.Tools.GlideApp;
import com.bumptech.glide.load.Option;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import okhttp3.ResponseBody;

/**
 * Created by Esraa on 10/5/2017.
 */

public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.MyViewHolder> {
    Activity context;
    List<NotificationListResponseModel> data;

    public NotificationListAdapter(Activity context, List<NotificationListResponseModel> data) {
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

        GlideApp.with(context).load(URLS.URL_BASE + data.get(position).getImage())
                .apply(RequestOptions.option(Option.memory(ConstantUtils.GLIDE_TIMEOUT), 0))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.no_image).into(holder.notificationIv);

        holder.linearLayout.setOnClickListener(view -> {
            Intent intent;
            if (data.get(position).getNoticationType() == 1) {
                intent = new Intent(context, NotificationDetailActivity.class);
                intent.putExtra("id", data.get(position).getUserNotfactionID());
            } else {
                intent = new Intent(context, EventDetailsActivity.class);
                intent.putExtra("id", data.get(position).getDestiationID());
            }
            intent.putExtra("title", data.get(position).getTitle());
            intent.putExtra("text", data.get(position).getText());
            intent.putExtra("image", data.get(position).getImage());
            context.startActivity(intent);
        });
        holder.deleteItem.setOnClickListener(view -> {
            UpdateRequestModel updateRequestModel = new UpdateRequestModel(data.get(position).getUserNotfactionID());
            UpdateNotifiList(updateRequestModel);
        });
    }

    private void UpdateNotifiList(UpdateRequestModel updateRequestModel) {
        ServerTool.UpdateNotificationList(context, updateRequestModel, new ServerTool.APICallBack<Integer>() {
            @Override
            public void onSuccess(Integer response) {
                if (response == 1) {
                    for (int i = 0; i < data.size(); i++) {
                        if (updateRequestModel.getID() == data.get(i).getUserNotfactionID()) {
                            data.remove(i);
                            notifyDataSetChanged();
                            break;
                        }

                    }
                } else {
                }
            }

            @Override
            public void onFailed(int statusCode, ResponseBody responseBody) {

            }
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
        private ImageView deleteItem;
        private TextViewBold titleTv;
        private TextViewLight describtionTv;
        private ConstraintLayout linearLayout;


        public MyViewHolder(View view) {
            super(view);
            notificationIv = view.findViewById(R.id.notification_iv);
            titleTv = view.findViewById(R.id.title_tv);
            describtionTv = view.findViewById(R.id.describtion_tv);
            deleteItem = view.findViewById(R.id.delete_item);
            linearLayout = view.findViewById(R.id.linearLayout);


        }
    }
}
