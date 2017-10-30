package com.asgatech.sharjahmuseums.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.asgatech.sharjahmuseums.Tools.CustomFonts.TextViewBold;
import com.asgatech.sharjahmuseums.Tools.Utils;

import java.util.List;

/**
 * Created by halima.reda on 9/12/2017.
 */

public class AllMuseumsAdapter extends RecyclerView.Adapter<AllMuseumsAdapter.ViewHolder> {
    private HomeContract.UserAction presenter;
    private Context context;
    private List<MuseumsDetailsModel> allMuseumsList;

    public AllMuseumsAdapter(Context context, List<MuseumsDetailsModel> response, HomeContract.UserAction presenter) {
        this.context = context;
        this.allMuseumsList = response;
        this.presenter = presenter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_row_museums, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.e(context.getString(R.string.tag_image), URLS.URL_BASE + allMuseumsList.get(position).getImage());
        Utils.loadSimplePic(context, URLS.URL_BASE + allMuseumsList.get(position).getImage(), holder.museumsImageView);

        holder.museumsNameTextView.setText(allMuseumsList.get(position).getTitle());
        holder.museumsItemLinear.setOnClickListener(view -> {
//                Bundle bundle = new Bundle();
//                bundle.putInt(ConstantUtils.EXTRA_MUSEUMS_ID, allMuseumsList.get(position).getMus_ID());
//                bundle.putString(ConstantUtils.MUSEUM_TITLE, allMuseumsList.get(position).getTitle().trim());
//                bundle.putString(ConstantUtils.MUSEUM_COLOR, allMuseumsList.get(position).getColor());
//                presenter.openFragment(new MuseumDetailsFragment(), bundle);
            Intent intent = new Intent(context, MuseumsDetailsActivity.class);
            intent.putExtra(ConstantUtils.EXTRA_MUSEUMS_ID, allMuseumsList.get(position).getMus_ID());
            intent.putExtra(ConstantUtils.MUSEUM_TITLE,allMuseumsList.get(position).getTitle().trim());
            intent.putExtra(ConstantUtils.MUSEUM_COLOR,allMuseumsList.get(position).getColor());
            intent.putExtra(ConstantUtils.MUSEUM_IMAGE,allMuseumsList.get(position).getImage());
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
