package de.avkterwey.popularmovies.data.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import de.avkterwey.popularmovies.MyConstants;
import de.avkterwey.popularmovies.Repo;
import de.avkterwey.popularmovies.data.model.Item;
import de.avkterwey.popularmovies.data.model.MovieItem;
import de.avkterwey.popularmovies.persistence.FavoriteMoviesContract;

import static de.avkterwey.popularmovies.persistence.FavoriteMoviesContract.FavoriteMoviesEntry.COL_NAME_MOVIE_ID;
import static de.avkterwey.popularmovies.persistence.FavoriteMoviesContract.FavoriteMoviesEntry.CONTENT_URI;

/*
 * Created by Berenice
 */

public class DetailViewModel extends AndroidViewModel {
    private final static String TAG = "DetailVM";

    private Repo repo = new Repo();
    private LiveData<List<? extends Item>> jsonTrailersLiveData;
    private LiveData<List<? extends Item>> jsonReviewsLiveData;
    //private MutableLiveData<List<? extends Item>> jsonSimilarVideosLiveData;
    private MutableLiveData<Boolean> favoriteListChangedLiveData;

    private MovieItem mMovieItem;


    public DetailViewModel(@NonNull Application application) {
        super(application);
        favoriteListChangedLiveData = new MutableLiveData<>();
        favoriteListChangedLiveData.setValue(false);
    }



    public void queryApi(MovieItem movie, String endpoint){
        mMovieItem = movie;
        if(endpoint.equals(MyConstants.ENDPOINT_TRAILERS))
            jsonTrailersLiveData= repo.loadData(mMovieItem.getId(), MyConstants.ENDPOINT_TRAILERS);
        else if (endpoint.equals(MyConstants.ENDPOINT_REVIEWS))
            jsonReviewsLiveData = repo.loadData(mMovieItem.getId(), MyConstants.ENDPOINT_REVIEWS);
    }


    public LiveData<List<? extends Item>> getTrailerListLiveData(){
        return jsonTrailersLiveData;
    }

    public LiveData<List<? extends Item>> getReviewListLivedata(){
        return jsonReviewsLiveData;
    }



    public MutableLiveData<Boolean> getFavoriteListChangedLiveData(){
        return favoriteListChangedLiveData;
    }


    public boolean toggleFavorite(ContentResolver resolver) {
        // check if movie already exists in table
        boolean alreadyFav = repo.isAlreadyFavorited(resolver, mMovieItem.getId());
        Log.d(TAG, "alreadyFav = " + alreadyFav);
        if(alreadyFav){
            setIsNotFavorite(resolver);
            return false;
        } else {
            setIsFavorite(resolver);
            return true;
        }
    }



    /*
    Private helper methods
     */
    private void setIsFavorite(ContentResolver resolver){
        favoriteListChangedLiveData.setValue(false);
        mMovieItem.setFavorite(true);

        Uri contentUri = CONTENT_URI;
        Uri uriInsert = resolver.insert(contentUri, createContentValues());

        if(uriInsert != null){
            Log.d(TAG, "inserted. Uri = " + uriInsert);
        }
    }


    private void setIsNotFavorite(ContentResolver resolver){
        favoriteListChangedLiveData.setValue(true);
        mMovieItem.setFavorite(false);
        String id = mMovieItem.getId();
        final Uri uri = CONTENT_URI;
        int deletedItems = resolver.delete(
                uri,
               COL_NAME_MOVIE_ID +"=?",
                new String[]{id});
        Log.d(TAG, "deleted items number = " + deletedItems);
    }



    private ContentValues createContentValues(){
        ContentValues cv = new ContentValues();
        //cv.put(FavoriteMoviesContract.FavoriteMoviesEntry._ID, mMovieItem.getId());
        cv.put(COL_NAME_MOVIE_ID, mMovieItem.getId());
        cv.put(FavoriteMoviesContract.FavoriteMoviesEntry.COL_NAME_TITLE, mMovieItem.getTitle());
        cv.put(FavoriteMoviesContract.FavoriteMoviesEntry.COL_NAME_OVERVIEW, mMovieItem.getOverview());
        cv.put(FavoriteMoviesContract.FavoriteMoviesEntry.COL_NAME_VOTE_AVERAGE, mMovieItem.getRating());
        cv.put(FavoriteMoviesContract.FavoriteMoviesEntry.COL_NAME_RELEASE_DATE, mMovieItem.getReleaseDateString());
        cv.put(FavoriteMoviesContract.FavoriteMoviesEntry.COL_NAME_POSTER_PATH, mMovieItem.getThumbPath());

        return cv;
    }
}
