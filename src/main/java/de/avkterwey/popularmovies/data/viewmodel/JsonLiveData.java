package de.avkterwey.popularmovies.data.viewmodel;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import de.avkterwey.popularmovies.MyConstants;
import de.avkterwey.popularmovies.api.JsonUtil;
import de.avkterwey.popularmovies.api.NetworkUtil;
import de.avkterwey.popularmovies.data.model.Item;
import de.avkterwey.popularmovies.data.model.MovieItem;
import de.avkterwey.popularmovies.persistence.FavoriteMoviesContract;
import de.avkterwey.popularmovies.persistence.FavoriteMoviesDbHelper;

/*
 * Created by Berenice
 */

class JsonLiveData extends LiveData<List<? extends Item>> {
    private final static String TAG = "JsonLiveData";

    private List<? extends Item> mList;

    private FavoriteMoviesDbHelper mDbHelper;
    private SQLiteDatabase mDb;
    private Cursor mCursor;

   /* private WeakReference<Context> contextRef = null;

     JsonLiveData(Context context) {
        contextRef = new WeakReference<Context>(context);
    }

    JsonLiveData(){};
*/


    @SuppressLint("StaticFieldLeak")
    public void loadData(final String endpoint){

        new AsyncTask<Void, Void, List<? extends Item>>(){

            @Override
            protected List<? extends Item> doInBackground(Void... voids) {
                URL queryUrl = NetworkUtil.buildQueryUrl(endpoint);

                try {
                    String movieDataAsJsonString = NetworkUtil.getResponseFromHttpUrl(queryUrl);
                    parseJson(endpoint, movieDataAsJsonString);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return mList;
            }

            @Override
            protected  void onPostExecute(List<? extends Item> data){
                setValue(mList);
            }

        }.execute();
    }



    @SuppressLint("StaticFieldLeak")
    public void loadData(final String movieId, final String endpoint){
        new AsyncTask<Void, Void, List<? extends Item>>(){

            @Override
            protected List<? extends Item> doInBackground(Void... voids) {
                URL queryUrl = NetworkUtil.buildQueryUrl(movieId, endpoint);
                try {
                    String dataAsJsonString = NetworkUtil.getResponseFromHttpUrl(queryUrl);
                    parseJson(endpoint, dataAsJsonString);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return mList;
            }

            @Override
            protected void onPostExecute(List<? extends Item> data){ setValue(mList);}

        }.execute();
    }




    @SuppressLint("StaticFieldLeak")
    public void retrieveFavorites(ContentResolver resolver){
        new AsyncTask<Void, Void, List<? extends Item>>(){

            @Override
            protected List<? extends Item> doInBackground(Void... voids) {
                //Log.d("JsonLive", "content URI = " + FavoriteMoviesContract.FavoriteMoviesEntry.CONTENT_URI);
                mCursor = resolver.query(FavoriteMoviesContract.FavoriteMoviesEntry.CONTENT_URI,
                        null,
                        null,
                        null,
                        null);

                //Log.d("LiveData", "cursor items count = " + mCursor.getCount());
                mList = getFavoritesListFromCursor();
                return mList;
            }

            @Override
            protected void onPostExecute(List<? extends Item> data){
                setValue(mList);
            }

        }.execute();
    }



    public List<? extends Item> getFavoritesListFromCursor(){
        List<MovieItem> favList = new ArrayList<>();

        if(mCursor == null)
            return null;

        for (int i=0; i< mCursor.getCount(); ++i){
            mCursor.moveToPosition(i);
            String id = mCursor.getString(mCursor.getColumnIndex(FavoriteMoviesContract.FavoriteMoviesEntry.COL_NAME_MOVIE_ID));
            String title = mCursor.getString(mCursor.getColumnIndex(FavoriteMoviesContract.FavoriteMoviesEntry.COL_NAME_TITLE));
            String overview = mCursor.getString(mCursor.getColumnIndex(FavoriteMoviesContract.FavoriteMoviesEntry.COL_NAME_OVERVIEW));
            String rating = mCursor.getString(mCursor.getColumnIndex(FavoriteMoviesContract.FavoriteMoviesEntry.COL_NAME_VOTE_AVERAGE));
            String releaseDate = mCursor.getString(mCursor.getColumnIndex(FavoriteMoviesContract.FavoriteMoviesEntry.COL_NAME_RELEASE_DATE));
            String thumbPath = mCursor.getString(mCursor.getColumnIndex(FavoriteMoviesContract.FavoriteMoviesEntry.COL_NAME_POSTER_PATH));

            MovieItem movieItem = new MovieItem(id, title, thumbPath, overview, rating, releaseDate, (byte) 1);
            favList.add(movieItem);

        }
        mCursor.close();
        return favList;
    }



    private void parseJson(String endpoint, String jsonResponse){
        switch(endpoint){
            case MyConstants.ENDPOINT_POPULAR:
                mList = JsonUtil.getMovieList(jsonResponse);
                break;
            case MyConstants.ENDPOINT_TOP_RATED:
                mList = JsonUtil.getMovieList(jsonResponse);
                break;
            case MyConstants.ENDPOINT_TRAILERS:
                mList = JsonUtil.getTrailerVideos(jsonResponse);
                break;
            case MyConstants.ENDPOINT_REVIEWS:
                mList = JsonUtil.getReviewsList(jsonResponse);
                break;
            case MyConstants.ENDPOINT_SIMILAR_MOVIES:
                mList = JsonUtil.getMovieList(jsonResponse);
                break;

        }
    }

}
