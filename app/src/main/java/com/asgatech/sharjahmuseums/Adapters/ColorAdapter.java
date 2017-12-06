package com.asgatech.sharjahmuseums.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.asgatech.sharjahmuseums.Models.EventCategoryModel;
import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.CircleImageView;

import java.util.List;

/**
 * Created by mohamed.arafa on 11/26/2017.
 */

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ViewHolder> {
    private Context context;
    private List<EventCategoryModel> list;

    public ColorAdapter(Context context, List<EventCategoryModel> response) {
        this.context = context;
        this.list = response;
    }

    @Override
    public ColorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_recycler_row, parent, false);
        return new ColorAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ColorAdapter.ViewHolder holder, final int position) {
        holder.name.setText(list.get(position).getTitle().trim());
        Drawable background = holder.pallete.getBackground();
        if (list.get(position).getColor() != null) {
            background.setColorFilter(Color.parseColor(list.get(position).getColor()), PorterDuff.Mode.SRC_IN);

        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView pallete;
        private TextView name;

        ViewHolder(View view) {
            super(view);
            pallete = itemView.findViewById(R.id.pallete_for_filter_item);
            name = itemView.findViewById(R.id.name_for_filter_item);
        }
    }

}
