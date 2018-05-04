package de.avkterwey.popularmovies.view;

import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bumptech.glide.Glide;

import java.net.URL;
import java.util.List;

import de.avkterwey.popularmovies.R;
import de.avkterwey.popularmovies.data.model.Item;
import de.avkterwey.popularmovies.databinding.MovieItemBinding;
import de.avkterwey.popularmovies.data.model.MovieItem;
import de.avkterwey.popularmovies.api.NetworkUtil;
import de.avkterwey.popularmovies.persistence.FavoriteMoviesContract;

/*
 * Created by Berenice
 */

public class MovieItemAdapter extends RecyclerView.Adapter<MovieItemAdapter.ItemViewHolder> {

    private final static String TAG = "MovieAdapter";
    private List<? extends Item> mItemList;
    private final IRecyclerViewClickListener mRecyclerViewClickListener;


    public MovieItemAdapter(IRecyclerViewClickListener recyclerViewClickListener) {
        this.mRecyclerViewClickListener = recyclerViewClickListener;
    }



    public void setItemList(final List<? extends Item> itemList){
        mItemList = itemList;
        notifyDataSetChanged();

        for (int i=0; i < mItemList.size(); ++i){
            MovieItem movieItem = (MovieItem) mItemList.get(i);
            //Log.d(TAG, "movie = " + movieItem.getTitle() + " thumb= " + movieItem.getThumbPath());
        }
    }


    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     * <p>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p>
     * The new ViewHolder will be used to display items of the adapter using
     * {@link #onBindViewHolder(ViewHolder, int, List)}. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(ViewHolder, int)
     */
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MovieItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.movie_item,
                parent,
                false
        );
        return new ItemViewHolder(binding);
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     * <p>
     * Note that unlike {@link ListView}, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use {@link ViewHolder#getAdapterPosition()} which will
     * have the updated adapter position.
     * <p>
     * Override {@link #onBindViewHolder(ViewHolder, int, List)} instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        MovieItem movieItem = (MovieItem) mItemList.get(position);
        holder.binding.setItem(movieItem);
        holder.binding.rating.setText((CharSequence) movieItem.getRating());

        URL thumbUrl = NetworkUtil.buildThumbQueryUrl(movieItem.getThumbPath());
        if(movieItem.getThumbPath() != null && !movieItem.getThumbPath().isEmpty()){
            Glide.with(holder.binding.photoImageView.getContext())
                    .load(thumbUrl)
                    .thumbnail(0.01f)
                    .into(holder.binding.photoImageView
                    );
        }
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return mItemList == null ? 0 : mItemList.size();
    }





    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final MovieItemBinding binding;

        public ItemViewHolder(MovieItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.photoImageView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            MovieItem movieItem = (MovieItem) mItemList.get(pos);
            mRecyclerViewClickListener.onClick(v, pos, movieItem);
            Log.d("Adapter", " pos = " + pos);
        }
    }
}
