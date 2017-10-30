package com.asgatech.sharjahmuseums.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asgatech.sharjahmuseums.Models.OpeningHoursListEntity;
import com.asgatech.sharjahmuseums.Models.PriceCategorySublistEntity;
import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.CustomFonts.TextViewLight;
import com.asgatech.sharjahmuseums.Tools.Localization;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by halima.reda on 9/13/2017.
 */

public class TextAdapter extends RecyclerView.Adapter<TextAdapter.ViewHolder> {
    private Context context;
    private List<OpeningHoursListEntity> openingHoursList;
    private List<PriceCategorySublistEntity> entryFeesList;
    private int listType;

    public TextAdapter(Context context, List<OpeningHoursListEntity> response, int type) {
        this.context = context;
        this.openingHoursList = response;
        this.listType = type;
    }

    public TextAdapter(List<PriceCategorySublistEntity> response, Context context, int type) {
        this.context = context;
        this.entryFeesList = response;
        this.listType = type;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_row_text, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (listType == 1) { // type to opening hours
            Calendar from = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aa", Localization.getCurrentLocale(context));
            from.set(Calendar.HOUR, openingHoursList.get(position).getFrom());
            from.set(Calendar.MINUTE, openingHoursList.get(position).getFormMinute());
            Calendar to = Calendar.getInstance();
            to.set(Calendar.HOUR, openingHoursList.get(position).getTo());
            to.set(Calendar.MINUTE, openingHoursList.get(position).getToMinute());
            if (openingHoursList.get(position).getISCLOSED()) {
                holder.titleTextView.setText(String.format("%s:%s", openingHoursList.get(position).getTitle(), context.getResources().getString(R.string.closed)));
            } else {
                holder.titleTextView.setText(String.format("%s: %s %s - %s %s",
                        openingHoursList.get(position).getTitle(),
                        context.getString(R.string.From_),
                        sdf.format(from.getTime()),
                        context.getString(R.string.To_),
                        sdf.format(to.getTime())));
            }
        } else if (listType == 2) { // type to entry fees
            for (int i = 0; i < entryFeesList.get(position).getSublist().size(); i++) {
                holder.titleTextView.setText(String.format(Localization.getCurrentLocale(context),
                        "%s(%s):%d%s",
                        entryFeesList.get(position).getTitle(),
                        entryFeesList.get(position).getSublist().get(i).getTitle(),
                        entryFeesList.get(position).getSublist().get(i).getPrice(),
                        context.getString(R.string.AED)));
            }

        }

    }


    @Override
    public int getItemCount() {
        if (listType == 1) { // type to opening hours
            return openingHoursList.size();
        } else if (listType == 2) {
            return entryFeesList.size();
        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextViewLight titleTextView;

        ViewHolder(View view) {
            super(view);
            titleTextView = (TextViewLight) view.findViewById(R.id.title_text_view);
        }
    }
}
