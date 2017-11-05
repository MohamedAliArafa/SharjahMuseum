package com.asgatech.sharjahmuseums.Adapters;

import android.content.Context;
import android.support.transition.ChangeBounds;
import android.support.transition.TransitionManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asgatech.sharjahmuseums.Models.PlanYourVisitsModel;
import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.Connection.ConstantUtils;
import com.asgatech.sharjahmuseums.Tools.Connection.URLS;
import com.asgatech.sharjahmuseums.Tools.GlideApp;
import com.bumptech.glide.load.Option;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

/**
 * Created by esraa.reda on 10/8/2017.
 */

public class PlanYourVisitAdapter extends RecyclerView.Adapter<PlanYourVisitAdapter.ViewHolder> {
    private Context context;
    private List<PlanYourVisitsModel.PlanVisteEntity> allPlanVisitsList;
    private RecyclerView recyclerView;
    private int mExpandedPosition = -1;

    public PlanYourVisitAdapter(Context context, List<PlanYourVisitsModel.PlanVisteEntity> response, RecyclerView recyclerView) {
        this.context = context;
        allPlanVisitsList = response;
        this.recyclerView = recyclerView;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_plan_your_visit, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        fillView(holder, position);
        expandRecycle(holder, position);
    }

    private void fillView(final ViewHolder holder, final int position) {

        GlideApp.with(context).load(URLS.URL_BASE + allPlanVisitsList.get(position).getImage())
                .apply(RequestOptions.option(Option.memory(ConstantUtils.GLIDE_TIMEOUT), 0))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.image).into(holder.planImageView);

        holder.titleTextView.setText(allPlanVisitsList.get(position).getTitle());
        //    holder.titleTextViewExpand.setText(allPlanVisitsList.get(position).getTitle());
        holder.descriptionTextView.setText(allPlanVisitsList.get(position).getText());
        holder.titleTextViewExpand.setText(allPlanVisitsList.get(position).getTitle());

    }

    private void expandRecycle(final ViewHolder holder, final int position) {
        final boolean isExpanded = position == mExpandedPosition;
        holder.expandedLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.itemView.setActivated(isExpanded);
        if (!isExpanded) {
            holder.arrowImageView.setRotation(180);
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
        return allPlanVisitsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView planImageView, arrowImageView;
        private TextView titleTextView, titleTextViewExpand, descriptionTextView;
        private LinearLayout expandedLayout;

        ViewHolder(View view) {
            super(view);
            planImageView = view.findViewById(R.id.plan_image);
            arrowImageView = view.findViewById(R.id.arrowIV);

            titleTextView = view.findViewById(R.id.image_title_txt);
            titleTextViewExpand = view.findViewById(R.id.title_txt_expanded);
            descriptionTextView = view.findViewById(R.id.dsc_txt_expanded);
            expandedLayout = view.findViewById(R.id.expanded_menu);
        }
    }
}
