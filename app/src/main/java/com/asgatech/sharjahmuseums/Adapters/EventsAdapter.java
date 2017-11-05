package com.asgatech.sharjahmuseums.Adapters;

/**
 * Created by khaledbadawy on 9/10/2017.
 */

import android.app.Activity;
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

import com.asgatech.sharjahmuseums.Activities.Events.EventDetails.EventDetailsActivity;
import com.asgatech.sharjahmuseums.Models.EventModel;
import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.Connection.ConstantUtils;
import com.asgatech.sharjahmuseums.Tools.Connection.URLS;
import com.asgatech.sharjahmuseums.Tools.GlideApp;
import com.asgatech.sharjahmuseums.Tools.PermissionTool;
import com.asgatech.sharjahmuseums.Tools.Utils;
import com.bumptech.glide.load.Option;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;

public class EventsAdapter extends RealmRecyclerViewAdapter<EventModel, EventsAdapter.MyViewHolder> {

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

    Activity context;

    public EventsAdapter(Activity context, RealmResults<EventModel> list) {
        super(list, true);
        if (list != null)
            mList.addAll(list);
        this.context = context;
    }

    public void updateSet(List<EventModel> list) {
        if (mList != null) {
            mList.clear();
            mList.addAll(list);
        }
        this.notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_event_recycler_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final EventModel model = mList.get(position);
        startDate = model.getStartDate();
        endDate = model.getEndDate();
        holder.mTitleTextView.setText(model.getTitle());
        holder.mDateFromTextView.setText(Utils.spliteDate(startDate));
        holder.mDateToTextView.setText(Utils.spliteDate(endDate));
        holder.mPlaceTextView.setText(model.getAdress());
        GlideApp.with(context).load(URLS.URL_BASE + model.getImage())
                .apply(RequestOptions.option(Option.memory(ConstantUtils.GLIDE_TIMEOUT), 0))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.mipmap.ic_launcher).into(holder.mImageEventImageView);

        holder.itemView.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putInt("id", model.getEventsID());
            Intent intent = new Intent(context, EventDetailsActivity.class);
            intent.putExtra("id", model.getEventsID());
            intent.putExtra("title", model.getTitle());
            context.startActivity(intent);
        });

        holder.mShareLayout.setOnClickListener(view -> {
            Intent intentShare = new Intent(Intent.ACTION_SEND);
            intentShare.setType("text/plain");
            intentShare.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.app_name));
            intentShare.putExtra(Intent.EXTRA_TEXT, model.getUrl() + "\n" +
                    "\n" + context.getResources().getString(R.string.about_museums) + ":" + model.getTitle());
            Intent chooser = Intent.createChooser(intentShare, context.getString(R.string.title_share_via));
            chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(chooser);
        });
        Drawable background = holder.mPalleteColorTextView.getBackground();
        if (model.getColor() != null) {
            Log.e(context.getString(R.string.tag_color_code), model.getTitle() + ":" + model.getColor());
            background.setColorFilter(Color.parseColor(model.getColor()), PorterDuff.Mode.SRC_IN);
        }

        holder.mAddToCalender.setOnClickListener(view -> addToCalender(model));
    }

    private void addToCalender(EventModel model) {
        if (PermissionTool.checkPermission(context, PermissionTool.PERMISSION_CALENDER)) {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
            Intent intent = new Intent(Intent.ACTION_EDIT);
            intent.setType("vnd.android.cursor.item/event");
            try {
                Date today = cal.getTime();
                Date date = sdf.parse(model.getEndDate());
                if (date.before(today)) {
                    Toast.makeText(context, R.string.toast_evet_expired, Toast.LENGTH_SHORT).show();
                    return;
                }
                cal.setTime(date);
                intent.putExtra("beginTime", cal.getTimeInMillis());
                date = sdf.parse(model.getEndDate());
                cal.setTime(date);
                intent.putExtra("endTime", cal.getTimeInMillis());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            intent.putExtra("allDay", true);
            intent.putExtra("title", model.getTitle());
            context.startActivity(intent);
        }
    }


    @Override
    public int getItemCount() {
        if (mList != null)
            return mList.size();
        return 0;
    }
}
