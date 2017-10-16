package com.asgatech.sharjahmuseums.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asgatech.sharjahmuseums.Activities.MuseumsDetailsActivity;
import com.asgatech.sharjahmuseums.Models.ALLMuseumsModel;
import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.Connection.ConstantUtils;
import com.asgatech.sharjahmuseums.Tools.Connection.URLS;
import com.asgatech.sharjahmuseums.Tools.CustomFonts.TextViewBold;
import com.asgatech.sharjahmuseums.Tools.Utils;

import java.util.List;

/**
 * Created by halima.reda on 9/12/2017.
 */

public class AllMuseumsAdapter extends RecyclerView.Adapter<AllMuseumsAdapter.ViewHolder> {
    private Context context;
    private List<ALLMuseumsModel> allMuseumsList;

    public AllMuseumsAdapter(Context context, List<ALLMuseumsModel> response) {
        this.context = context;
        this.allMuseumsList = response;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_row_museums, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.e("image Link", URLS.URL_BASE + allMuseumsList.get(position).getImage());
        Utils.loadSimplePic(context, URLS.URL_BASE + allMuseumsList.get(position).getImage(), holder.museumsImageView);
//        Drawable background = holder.colorBackgroundImageView.getBackground();
        allMuseumsList.get(position).setColor("#99e40d62"); // for test
//        background.setColorFilter(Color.parseColor(allMuseumsList.get(position).getColor().trim()), PorterDuff.Mode.LIGHTEN);
        holder.museumsNameTextView.setText(allMuseumsList.get(position).getTitle());
        holder.museumsItemLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MuseumsDetailsActivity.class);
                intent.putExtra(ConstantUtils.EXTRA_MUSEUMS_ID, allMuseumsList.get(position).getMus_ID());
                intent.putExtra("museumTitle",allMuseumsList.get(position).getTitle().trim());
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return allMuseumsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private FrameLayout museumsItemLinear;
        private ImageView museumsImageView;
        private ImageView colorBackgroundImageView;
        private TextViewBold museumsNameTextView;

        ViewHolder(View view) {
            super(view);
            museumsItemLinear = (FrameLayout) view.findViewById(R.id.museums_item_linear);
            museumsImageView = (ImageView) view.findViewById(R.id.museums_image_view);
//            colorBackgroundImageView = (ImageView) view.findViewById(R.id.color_background_image_view);
            museumsNameTextView = (TextViewBold) view.findViewById(R.id.museums_name_text_view);
        }
    }
}
