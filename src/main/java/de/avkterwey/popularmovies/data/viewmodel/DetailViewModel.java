package de.avkterwey.popularmovies.data.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import de.avkterwey.popularmovies.MyConstants;
import de.avkterwey.popularmovies.data.model.Item;
import de.avkterwey.popularmovies.data.model.MovieItem;
import de.avkterwey.popularmovies.persistence.FavoriteMoviesContract;

/*
 * Created by Berenice
 */

public class DetailViewModel extends AndroidViewModel {
    private final static String TAG = "DetailVM";
    private JsonLiveData jsonTrailersLiveData;
    private JsonLiveData jsonReviewsLiveData;
    private JsonLiveData jsonSimilarVideosLiveData;
    private MutableLiveData<Boolean> favoriteListChangedLiveData;

    private MovieItem mMovieItem;

    public DetailViewModel(@NonNull Application application) {
        super(application);
        jsonTrailersLiveData = new JsonLiveData();
        jsonReviewsLiveData = new JsonLiveData();
        jsonSimilarVideosLiveData = new JsonLiveData();
        favoriteListChangedLiveData = new MutableLiveData<>();
    }



    public void queryApi(MovieItem movie){
        mMovieItem = movie;
        jsonTrailersLiveData.loadData(mMovieItem.getId(), MyConstants.ENDPOINT_TRAILERS);
        jsonReviewsLiveData.loadData(mMovieItem.getId(), MyConstants.ENDPOINT_REVIEWS);
        jsonSimilarVideosLiveData.loadData(mMovieItem.getId(), MyConstants.ENDPOINT_SIMILAR_MOVIES);
    }


    public LiveData<List<? extends Item>> getTrailerListLiveData(){
        return jsonTrailersLiveData;
    }

    public LiveData<List<? extends Item>> getReviewListLivedata(){
        return jsonReviewsLiveData;
    }

    public LiveData<List<? extends Item>> getJsonSimilarVideosLiveData() {
        return jsonSimilarVideosLiveData;
    }

    public MutableLiveData<Boolean> getFavoriteListChangedLiveData(){
        return favoriteListChangedLiveData;
    }


    public void toggleFavorite(ContentResolver contentResolver) {
        if (mMovieItem.isFavorite()) {
            setIsNotFavorite(contentResolver);
        } else {
            setIsFavorite(contentResolver);
        }
    }


    private void setIsFavorite(ContentResolver resolver){
        favoriteListChangedLiveData.setValue(false);
        mMovieItem.setFavorite(true);

        Uri contentUri = FavoriteMoviesContract.FavoriteMoviesEntry.CONTENT_URI;
        Uri uriInsert = resolver.insert(contentUri, createContentValues());

        /*if(uriInsert != null){
            Log.d(TAG, "inserted. Uri = " + uriInsert);
        }*/
    }


    private void setIsNotFavorite(ContentResolver resolver){
        favoriteListChangedLiveData.setValue(true);
        mMovieItem.setFavorite(false);

        String id = mMovieItem.getId();
        final Uri uri = FavoriteMoviesContract.FavoriteMoviesEntry.CONTENT_URI
                .buildUpon()
                .appendPath(id)
                .build();

        int deletedItems = resolver.delete(
                uri,
               null, // FavoriteMoviesContract.FavoriteMoviesEntry.COL_NAME_MOVIE_ID +"=?",
                null); //new String[]{id});
        Log.d(TAG, "deleted items number = " + deletedItems);
    }



    private ContentValues createContentValues(){
        ContentValues cv = new ContentValues();
        cv.put(FavoriteMoviesContract.FavoriteMoviesEntry._ID, mMovieItem.getId());
        cv.put(FavoriteMoviesContract.FavoriteMoviesEntry.COL_NAME_MOVIE_ID, mMovieItem.getId());
        cv.put(FavoriteMoviesContract.FavoriteMoviesEntry.COL_NAME_TITLE, mMovieItem.getTitle());
        cv.put(FavoriteMoviesContract.FavoriteMoviesEntry.COL_NAME_OVERVIEW, mMovieItem.getOverview());
        cv.put(FavoriteMoviesContract.FavoriteMoviesEntry.COL_NAME_VOTE_AVERAGE, mMovieItem.getRating());
        cv.put(FavoriteMoviesContract.FavoriteMoviesEntry.COL_NAME_RELEASE_DATE, mMovieItem.getReleaseDateString());
        cv.put(FavoriteMoviesContract.FavoriteMoviesEntry.COL_NAME_POSTER_PATH, mMovieItem.getThumbPath());

        return cv;
    }
}
