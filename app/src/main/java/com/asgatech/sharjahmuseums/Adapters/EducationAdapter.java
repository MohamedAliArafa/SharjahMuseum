package com.asgatech.sharjahmuseums.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.transition.ChangeBounds;
import android.support.transition.TransitionManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asgatech.sharjahmuseums.Activities.OpenWebViewActivity;
import com.asgatech.sharjahmuseums.Models.EducationListModel;
import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.Connection.ConstantUtils;
import com.asgatech.sharjahmuseums.Tools.Connection.URLS;
import com.asgatech.sharjahmuseums.Tools.GlideApp;
import com.bumptech.glide.load.Option;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

/**
 * Created by khaled.badawy on 10/4/2017.
 */

public class EducationAdapter extends RecyclerView.Adapter<EducationAdapter.ViewHolder> {
    private Context context;
    private List<EducationListModel> allMuseumsList;
    private String bookLink;
    RecyclerView recyclerView;

    int mExpandedPosition = -1;

    public EducationAdapter(Context context, List<EducationListModel> response, RecyclerView recyclerView) {
        this.context = context;
        this.allMuseumsList = response;
        this.recyclerView = recyclerView;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.education_list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Log.e("image Link", URLS.URL_BASE + allMuseumsList.get(position).getImage());
//        Utils.loadSimplePic(context, URLS.URL_BASE + allMuseumsList.get(position).getImage(), holder.edcationImageView);

        GlideApp.with(context).load(URLS.URL_BASE + allMuseumsList.get(position).getImage())
                .apply(RequestOptions.option(Option.memory(ConstantUtils.GLIDE_TIMEOUT), 0))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.image).into(holder.edcationImageView);

        bookLink = allMuseumsList.get(position).getBooklink();
//        Glide.with(context).load("https://www.frostburg.edu/fsu/assets/Image/dept/educ/education_sign_resized.jpg")
//                .placeholder(R.drawable.image).into(holder.edcationImageView);


//        background.setColorFilter(Color.parseColor(allMuseumsList.get(position).getColor().trim()), PorterDuff.Mode.LIGHTEN);
        holder.titleTextView.setText(allMuseumsList.get(position).getTitle());
        holder.titleTextViewExpand.setText(allMuseumsList.get(position).getTitle());
        holder.descriptionTextView.setText(allMuseumsList.get(position).getDescrption());
        holder.bookNowBtn.setOnClickListener(view -> {
            Intent intent = new Intent(context, OpenWebViewActivity.class);
            Log.e("bookLink", bookLink);
            intent.putExtra("bookLink", bookLink);
            context.startActivity(intent);
        });

        //expand
        final boolean isExpanded = position == mExpandedPosition;
        holder.expandedLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.itemView.setActivated(isExpanded);
        if (!isExpanded) {
            holder.arrowImageView.setRotation(0);
        }
        ChangeBounds transition = new ChangeBounds();
        transition.setDuration(125);
        holder.itemView.setOnClickListener(view -> {
            mExpandedPosition = isExpanded ? -1 : position;
            TransitionManager.beginDelayedTransition(recyclerView, transition);
            notifyDataSetChanged();
            holder.arrowImageView.setRotation(holder.arrowImageView.getRotation() + 180);
        });
    }


    @Override
    public int getItemCount() {
        return allMuseumsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView edcationImageView, arrowImageView;
        private TextView titleTextView, titleTextViewExpand, descriptionTextView;
        private LinearLayout expandedLayout;
        private Button bookNowBtn;

        ViewHolder(View view) {
            super(view);
            edcationImageView = (ImageView) view.findViewById(R.id.education_image);
            arrowImageView = (ImageView) view.findViewById(R.id.arrowIV);
            bookNowBtn = view.findViewById(R.id.book_nw);

            titleTextView = view.findViewById(R.id.education_txt);
            titleTextViewExpand = view.findViewById(R.id.education_txt_expanded);
            descriptionTextView = view.findViewById(R.id.dsc_txt_expanded);
            expandedLayout = view.findViewById(R.id.expanded_menu);
        }
    }
}
