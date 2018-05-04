package de.avkterwey.popularmovies.view;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import de.avkterwey.popularmovies.R;
import de.avkterwey.popularmovies.data.model.Item;
import de.avkterwey.popularmovies.data.model.ReviewItem;
import de.avkterwey.popularmovies.databinding.ReviewItemBinding;

/**
 * Created by administrator on 30.04.18.
 */

public class ReviewItemAdapter extends RecyclerView.Adapter<ReviewItemAdapter.ReviewViewHolder> {


    private List<? extends Item> mItemList;


    public void setItemList(final List<? extends Item> itemList){
        mItemList = itemList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ReviewItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.review_item,
                parent,
                false
        );
        return new ReviewViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        ReviewItem item = (ReviewItem) mItemList.get(position);
        String content = item.getContent();
        holder.binding.setItem(item);
        holder.binding.reviewText.setText(content);
    }



    @Override
    public int getItemCount() {
        return mItemList == null ? 0 : mItemList.size();
    }



    class ReviewViewHolder extends RecyclerView.ViewHolder {

        ReviewItemBinding binding;

        public ReviewViewHolder(ReviewItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
