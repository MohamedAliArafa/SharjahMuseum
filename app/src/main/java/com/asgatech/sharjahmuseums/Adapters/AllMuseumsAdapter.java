package com.asgatech.sharjahmuseums.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.asgatech.sharjahmuseums.Activities.Home.HomeContract;
import com.asgatech.sharjahmuseums.Activities.OurMuseums.MuseumDetails.MuseumsDetailsActivity;
import com.asgatech.sharjahmuseums.Models.MuseumsDetailsModel;
import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.Connection.ConstantUtils;
import com.asgatech.sharjahmuseums.Tools.Connection.URLS;
import com.asgatech.sharjahmuseums.Tools.CustomFonts.TextViewLight;
import com.asgatech.sharjahmuseums.Tools.GlideApp;
import com.asgatech.sharjahmuseums.Tools.Localization;
import com.asgatech.sharjahmuseums.Tools.RotateTransformation;
import com.bumptech.glide.load.Option;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

/**
 * Created by halima.reda on 9/12/2017.
 */

public class AllMuseumsAdapter extends RecyclerView.Adapter<AllMuseumsAdapter.ViewHolder> {
    private Context context;
    private List<MuseumsDetailsModel> allMuseumsList;

    public AllMuseumsAdapter(Context context, List<MuseumsDetailsModel> response, HomeContract.UserAction presenter) {
        this.context = context;
        this.allMuseumsList = response;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_museums, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (Localization.getCurrentLanguageID(context) == Localization.ARABIC_VALUE)
            GlideApp.with(context)
                    .load(URLS.URL_BASE + allMuseumsList.get(position).getImage()).placeholder(R.drawable.no_image)
                    .transform(new RotateTransformation(context))
                    .apply(RequestOptions.option(Option.memory(ConstantUtils.GLIDE_TIMEOUT), 0))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into((holder.museumsImageView));
        else
            GlideApp.with(context)
                    .load(URLS.URL_BASE + allMuseumsList.get(position).getImage()).placeholder(R.drawable.no_image)
                    .apply(RequestOptions.option(Option.memory(ConstantUtils.GLIDE_TIMEOUT), 0))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into((holder.museumsImageView));

        holder.museumsNameTextView.setText(allMuseumsList.get(position).getTitle());
        holder.museumsItemLinear.setOnClickListener(view -> {
            Intent intent = new Intent(context, MuseumsDetailsActivity.class);
            intent.putExtra(ConstantUtils.EXTRA_MUSEUMS_ID, allMuseumsList.get(position).getMus_ID());
            intent.putExtra(ConstantUtils.MUSEUM_TITLE, allMuseumsList.get(position).getTitle().trim());
            intent.putExtra(ConstantUtils.MUSEUM_COLOR, allMuseumsList.get(position).getColor());
            intent.putExtra(ConstantUtils.MUSEUM_IMAGE, allMuseumsList.get(position).getImage());
            context.startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return allMuseumsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private FrameLayout museumsItemLinear;
        private ImageView museumsImageView;
        private TextViewLight museumsNameTextView;

        ViewHolder(View view) {
            super(view);
            museumsItemLinear = view.findViewById(R.id.museums_item_linear);
            museumsImageView = view.findViewById(R.id.museums_image_view);
            museumsNameTextView = view.findViewById(R.id.museums_name_text_view);
        }
    }
}
