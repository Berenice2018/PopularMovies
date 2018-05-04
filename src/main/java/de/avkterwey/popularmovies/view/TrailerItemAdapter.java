package de.avkterwey.popularmovies.view;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bumptech.glide.Glide;

import java.net.URL;
import java.util.List;

import de.avkterwey.popularmovies.R;
import de.avkterwey.popularmovies.api.NetworkUtil;
import de.avkterwey.popularmovies.data.model.Item;
import de.avkterwey.popularmovies.data.model.TrailerItem;
import de.avkterwey.popularmovies.databinding.TrailerItemBinding;


/*
 * Created by Berenice on 30.04.18.
 */

public class TrailerItemAdapter extends RecyclerView.Adapter<TrailerItemAdapter.TrailerViewHolder> {
    private static final String TAG = "TrailerItemAdapter";

    private List<? extends Item> mItemList;
    private final IRecyclerViewClickListener mRecyclerViewClickListener;
    private int mCount;

    public TrailerItemAdapter(IRecyclerViewClickListener recyclerViewClickListener) {
        this.mRecyclerViewClickListener = recyclerViewClickListener;
    }

    public TrailerItemAdapter(IRecyclerViewClickListener recyclerViewClickListener, int itemCount) {
        this.mRecyclerViewClickListener = recyclerViewClickListener;
        mCount = itemCount;
    }




    public void setItemList(final List<? extends Item> itemList){
        mItemList = itemList;
        notifyDataSetChanged();
    }

    public int getmCount() {
        return mCount;
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
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        TrailerItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.trailer_item,
                parent,
                false);
        return new TrailerViewHolder(binding, mRecyclerViewClickListener);
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
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {
        TrailerItem item = (TrailerItem) mItemList.get(position);
        holder.binding.setTrailer(item);

        String videoKey = item.getKey();
        URL thumbUrl = NetworkUtil.buildYoutubeThumbQueryUrl(videoKey);
        if(thumbUrl != null ){
            Glide.with(holder.binding.youtubeThumbImageView.getContext())
                    .load(thumbUrl)
                    .thumbnail(0.01f)
                    .into(holder.binding.youtubeThumbImageView
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



/*
* ViewHolder class
 */


    class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        final TrailerItemBinding binding;
        private IRecyclerViewClickListener clickListener;

        public TrailerViewHolder(TrailerItemBinding binding, IRecyclerViewClickListener clickListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.clickListener = clickListener;
            this.binding.youtubeThumbImageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            TrailerItem trailerItem = (TrailerItem) mItemList.get(pos);
            clickListener.onClick(v, pos, trailerItem);
        }


    }

}
