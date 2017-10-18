package com.asgatech.sharjahmuseums.Adapters;

/**
 * Created by khaledbadawy on 9/10/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.asgatech.sharjahmuseums.Activities.EventDetailsActivity;
import com.asgatech.sharjahmuseums.Models.EventModel;
import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.Connection.ConstantUtils;
import com.asgatech.sharjahmuseums.Tools.Connection.URLS;
import com.asgatech.sharjahmuseums.Tools.GlideApp;
import com.asgatech.sharjahmuseums.Tools.Utils;
import com.bumptech.glide.load.Option;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.MyViewHolder> {

    private List<EventModel> mList = new ArrayList<>();
    private String startDate, endDate;

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.event_item_title)
        TextView mTitleTextView;

        @BindView(R.id.event_type_color_pallete)
        TextView mPalleteColorTextView;

        @BindView(R.id.event_item_date_from)
        TextView mDateFromTextView;

        @BindView(R.id.event_item_date_to)
        TextView mDateToTextView;

        @BindView(R.id.event_item_place)
        TextView mPlaceTextView;

        @BindView(R.id.event_item_image)
        ImageView mImageEventImageView;

        @BindView(R.id.event_item_share_layout)
        LinearLayout mShareLayout;

        @BindView(R.id.event_item_add_to_calender_layout)
        LinearLayout mAddToCalender;


        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    Context context;

    public EventsAdapter(Context context, List<EventModel> list) {
        mList = list;
        this.context = context;
    }

    public void updateData(List<EventModel> list) {
        mList = list;
        this.notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_row_event_recycler_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final EventModel model = mList.get(position);
        startDate = model.getStartDate();
        endDate = model.getEndDate();
        holder.mTitleTextView.setText(model.getTitle());
        //


        holder.mDateFromTextView.setText(Utils.spliteDate(startDate));
        holder.mDateToTextView.setText(Utils.spliteDate(endDate));
        holder.mPlaceTextView.setText(model.getAdress());
        GlideApp.with(context).load(URLS.URL_BASE + model.getImage())
                .apply(RequestOptions.option(Option.memory(ConstantUtils.GLIDE_TIMEOUT), 0))
                .placeholder(R.mipmap.ic_launcher).into(holder.mImageEventImageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("eventId", model.getEventsID());
//                ((HomeActivity) context).openFragment(EventDetailsFragment.class, bundle);
                Intent intent = new Intent(context, EventDetailsActivity.class);
                intent.putExtra("eventId", model.getEventsID());
                intent.putExtra("eventTitle", model.getTitle());
                context.startActivity(intent);
            }
        });

        holder.mShareLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentShare = new Intent(Intent.ACTION_SEND);
                intentShare.setType("text/plain");
                intentShare.putExtra(android.content.Intent.EXTRA_SUBJECT, context.getString(R.string.app_name));
                intentShare.putExtra(Intent.EXTRA_TEXT, URLS.URL_BASE +  model.getImage()+ "\n" +
                        "\n" + context.getResources().getString(R.string.about_museums) +":"+ model.getTitle());
                Intent chooser = Intent.createChooser(intentShare, "share");
                chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(chooser);
            }
        });
        // holder.mPalleteColorTextView.setBackgroundColor(Color.parseColor(model.getColor()));

        Drawable background = holder.mPalleteColorTextView.getBackground();
        if (model.getColor() != null) {
            Log.e("colorCodett", model.getTitle() + ":" + model.getColor());
            background.setColorFilter(Color.parseColor(model.getColor()), PorterDuff.Mode.SRC_IN);
        }
//        holder.mPalleteColorTextView.setSolidColor("#"+model.getColor());   // throws exception as data is dummy from server
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mPresenter.openEventDetails(context ,model.getEventsID());
//            }
//        });

//        holder.mShareLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mPresenter.shareActionClciked(model.getUrl());
//            }
//        });

        holder.mAddToCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "replace action in adapter", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        if (mList != null)
            return mList.size();
        return 0;
    }
}
