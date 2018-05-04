package de.avkterwey.popularmovies.view;

import android.view.View;

import de.avkterwey.popularmovies.data.model.Item;

/*
 * Created by Berenice
 */

// TODO movie item as param needed?
public interface IRecyclerViewClickListener {
    void onClick(View view, int position, Item item);
}
