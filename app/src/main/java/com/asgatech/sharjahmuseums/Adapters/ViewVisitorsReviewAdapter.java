package com.asgatech.sharjahmuseums.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;

import com.asgatech.sharjahmuseums.Activities.ViewVisitorsReviewActivity;
import com.asgatech.sharjahmuseums.Models.ReviewVisitorsResponse;
import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.CustomFonts.TextViewBold;
import com.asgatech.sharjahmuseums.Tools.CustomFonts.TextViewLight;

import java.util.List;

/**
 * Created by esraa.reda on 10/11/2017.
 */

public class ViewVisitorsReviewAdapter extends RecyclerView.Adapter<ViewVisitorsReviewAdapter.ViewHolder> {

    Context context;
    List<ReviewVisitorsResponse> data;

    public ViewVisitorsReviewAdapter(Context context, List<ReviewVisitorsResponse> data) {
        this.data = data;

    }

    @Override
    public ViewVisitorsReviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_visitor_review_row, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewVisitorsReviewAdapter.ViewHolder holder, int position) {

        holder.tvReviewText.setText(data.get(position).getText());
        if (!data.get(position).getReply().isEmpty()) {
            holder.layoutMoreDesc.setVisibility(View.VISIBLE);
            holder.tvReviewReply.setText(data.get(position).getReply());

        }
        holder.tvReviewNumber.setText(String.format("%s%%", String.valueOf(data.get(position).getRate())));
        getRate(holder, position);

    }

    void getRate(ViewHolder holder, int position) {
        if (data.get(position).getRate() != 0) {

            if (data.get(position).getRate() ==1) {

                holder.tvReviewNumber.setText("20%");
               holder.barReviewStars.setRating(5f);
               holder.barReviewStars.setNumStars(1);

        }else if (data.get(position).getRate() ==2) {
                holder.tvReviewNumber.setText("40%");
                holder.barReviewStars.setNumStars(2);
                holder.barReviewStars.setRating(5f);

          } else if (data.get(position).getRate() ==3) {
                holder.tvReviewNumber.setText("60%");
                holder.barReviewStars.setRating(5f);
                holder.barReviewStars.setNumStars(3);

        } else if (data.get(position).getRate() ==4) {
                holder.tvReviewNumber.setText("80%");
                holder.barReviewStars.setRating(5f);
                holder.barReviewStars.setNumStars(4);

            }else if (data.get(position).getRate() ==5){
                holder.tvReviewNumber.setText("100%");
                holder.barReviewStars.setRating(5f);
                holder.barReviewStars.setNumStars(5);

            }
        } else {
            holder.tvReviewNumber.setVisibility(View.GONE);
            holder.barReviewStars.setVisibility(View.GONE);
//            holder.barReviewStars.setRating(0f);
        }

// else if (response.getItem().getRate() == 1) {
//            rateTextView.setText(response.getItem().getRatevalue() + "%");
//            ratingBar.setRating(5f);
//            ratingBar.setNumStars(1);
//        }
// else if (response.getItem().getRate() == 2) {
//            rateTextView.setText(response.getItem().getRatevalue() + "%");
//            ratingBar.setNumStars(2);
//            ratingBar.setRating(5f);
//        } else if (response.getItem().getRate() == 3) {
//            rateTextView.setText(response.getItem().getRatevalue() + "%");
//            ratingBar.setRating(5f);
//            ratingBar.setNumStars(3);
//        } else if (response.getItem().getRate() == 4) {
//            rateTextView.setText(response.getItem().getRatevalue() + "%");
//            ratingBar.setRating(5f);
//            ratingBar.setNumStars(4);
//        } else if (response.getItem().getRate() == 5) {
//            rateTextView.setText(response.getItem().getRatevalue() + "%");
//            ratingBar.setRating(5f);
//            ratingBar.setNumStars(5);
//        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    protected class ViewHolder extends RecyclerView.ViewHolder {
        private TextViewLight tvReviewText;
        private TextViewBold tvReviewNumber;
        private RatingBar barReviewStars;
        private LinearLayout layoutMoreDesc;
        private TextViewLight tvReviewReply;

        public ViewHolder(View view) {
            super(view);
            tvReviewText = (TextViewLight) view.findViewById(R.id.tv_review_text);
            tvReviewNumber = (TextViewBold) view.findViewById(R.id.tv_review_number);
            barReviewStars = (RatingBar) view.findViewById(R.id.bar_review_stars);
            layoutMoreDesc = (LinearLayout) view.findViewById(R.id.layout_more_desc);
            tvReviewReply = (TextViewLight) view.findViewById(R.id.tv_review_reply);
        }
    }


}
