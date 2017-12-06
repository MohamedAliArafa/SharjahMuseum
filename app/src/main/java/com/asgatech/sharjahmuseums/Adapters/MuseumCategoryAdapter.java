package com.asgatech.sharjahmuseums.Adapters;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asgatech.sharjahmuseums.Models.MuseumCategoryResponse;
import com.asgatech.sharjahmuseums.R;
import com.asgatech.sharjahmuseums.Tools.CustomFonts.TextViewBold;
import com.asgatech.sharjahmuseums.Tools.CustomFonts.TextViewLight;

import java.util.List;

/**
 * Created by Esraa on 10/5/2017.
 */

public class MuseumCategoryAdapter extends RecyclerView.Adapter<MuseumCategoryAdapter.MyViewHolder> {
    FragmentManager fragmentManager;
    Context context;
    List<MuseumCategoryResponse> data;
    onClickAdapter onClickAdapter;

    public MuseumCategoryAdapter(Context context, List<MuseumCategoryResponse> data, FragmentManager fragmentManager, onClickAdapter onClickAdapter) {
        this.context = context;
        this.data = data;
        this.onClickAdapter = onClickAdapter;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_search_category, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.titleTextView.setText(data.get(position).getTitle());
//        holder.categoryImageView.setText("\u2022");
//        GlideApp.with(context).load(URLS.URL_BASE + data.get(position).getImage())
//                .apply(RequestOptions.option(Option.memory(ConstantUtils.GLIDE_TIMEOUT), 0))
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .placeholder(R.drawable.no_image).into(holder.categoryImageView);

        holder.itemView.setOnClickListener(view -> {
//            Intent intent= new Intent(context, NotificationDetailActivity.class);
//            intent.putExtra("title",data.get(position).getTitle());
//            intent.putExtra("image",data.get(position).getImage());
//            intent.putExtra("id",data.get(position).getID());
//            context.startActivity(intent);
            onClickAdapter.onClickItem(data.get(position).getID() , "");



//            FragmentTransaction transaction = fragmentManager.beginTransaction();
//            transaction.replace(R.id.fragment_container,
//                    SearchMuseumResultFragment.newInstance(data.get(position).getID(), ""));
//            transaction.addToBackStack(null);
//            transaction.commit();
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextViewLight categoryImageView;
        private TextViewBold titleTextView;


        MyViewHolder(View view) {
            super(view);
            categoryImageView = view.findViewById(R.id.category_image);
            titleTextView = view.findViewById(R.id.category_title);
        }
    }

   public interface onClickAdapter {
        void onClickItem(int ID , String keyword);
    }
}
