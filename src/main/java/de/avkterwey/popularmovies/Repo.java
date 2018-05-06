package de.avkterwey.popularmovies;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.ContentResolver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import de.avkterwey.popularmovies.api.JsonUtil;
import de.avkterwey.popularmovies.api.NetworkUtil;
import de.avkterwey.popularmovies.data.model.Item;
import de.avkterwey.popularmovies.data.model.MovieItem;
import de.avkterwey.popularmovies.persistence.FavoriteMoviesContract;
import de.avkterwey.popularmovies.persistence.FavoriteMoviesDbHelper;

import static de.avkterwey.popularmovies.persistence.FavoriteMoviesContract.FavoriteMoviesEntry.CONTENT_URI;

/*
 * Created by Berenice
 */

public class Repo {

    private final static String TAG = "Repo";

    private List<? extends Item> mList;
    private MutableLiveData<List<? extends Item>> dataList = new MutableLiveData<>();
    private Cursor mCursor;
    int mCursorCount;



    @SuppressLint("StaticFieldLeak")
    public LiveData<List<? extends Item>> loadData(final String endpoint){

        new AsyncTask<Void, Void, List<? extends Item>>(){
            List<? extends Item> tempList;

            @Override
            protected List<? extends Item> doInBackground(Void... voids) {
                URL queryUrl = NetworkUtil.buildQueryUrl(endpoint);

                try {
                    String movieDataAsJsonString = NetworkUtil.getResponseFromHttpUrl(queryUrl);
                    tempList = parse(endpoint, movieDataAsJsonString);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return tempList;
            }

            @Override
            protected  void onPostExecute(List<? extends Item> data){
                dataList.setValue(tempList);
            }


        }.execute();

        return dataList;
    }



    @SuppressLint("StaticFieldLeak")
    public LiveData<List<? extends Item>> loadData(final String movieId, final String endpoint){

        MutableLiveData<List<? extends Item>> liveData = new MutableLiveData<>();

        new AsyncTask<Void, Void, List<? extends Item>>(){
         List<? extends Item> tempList;

            @Override
            protected List<? extends Item> doInBackground(Void... voids) {
                URL queryUrl = NetworkUtil.buildQueryUrl(movieId, endpoint);
                try {
                    String dataAsJsonString = NetworkUtil.getResponseFromHttpUrl(queryUrl);
                    tempList = parse(endpoint, dataAsJsonString);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return tempList;
            }

            @Override
            protected  void onPostExecute(List<? extends Item> data){
                liveData.setValue(data);
            }

        }.execute();

        return liveData;
    }




    @SuppressLint("StaticFieldLeak")
    public LiveData<List<? extends Item>> retrieveFavorites(ContentResolver resolver){
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
                return null;
            }

            @Override
            protected  void onPostExecute(List<? extends Item> data){
                dataList.setValue(mList);
            }

        }.execute();

        return dataList;
    }





    @SuppressLint("StaticFieldLeak")
    public boolean isAlreadyFavorited(ContentResolver contentResolver, final String movieId){
        Cursor cursor = contentResolver.query(CONTENT_URI,
                new String[]{FavoriteMoviesContract.FavoriteMoviesEntry.COL_NAME_MOVIE_ID},
                "movieId=?",
                new String[]{movieId},
                null);
        /*
        new AsyncTask<Void, Void, Cursor>(){
            @Override
            protected Cursor doInBackground(Void... voids) {
                mCursor = contentResolver.query(CONTENT_URI,
                        new String[]{FavoriteMoviesContract.FavoriteMoviesEntry.COL_NAME_MOVIE_ID},
                        "movieId=?",
                        new String[]{movieId},
                        null);
                return mCursor;
            }

            @Override
            protected void onPostExecute(Cursor c){
                mCursorCount = mCursor.getCount();
                Log.d(TAG, "cursor.getCount() = " + mCursor.getCount());
            }
        };*/
        return cursor.getCount() > 0;
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



    private List<? extends Item> parse(String endpoint, String jsonResponse){
        List<? extends Item> list = new ArrayList<>();
        switch(endpoint){
            case MyConstants.ENDPOINT_POPULAR:
                list = JsonUtil.getMovieList(jsonResponse);
                break;
            case MyConstants.ENDPOINT_TOP_RATED:
                list = JsonUtil.getMovieList(jsonResponse);
                break;
            case MyConstants.ENDPOINT_TRAILERS:
                list = JsonUtil.getTrailerVideos(jsonResponse);
                break;
            case MyConstants.ENDPOINT_REVIEWS:
                list = JsonUtil.getReviewsList(jsonResponse);
                break;
            case MyConstants.ENDPOINT_SIMILAR_MOVIES:
                list = JsonUtil.getMovieList(jsonResponse);
                break;

        }

        return list;
    }

}
