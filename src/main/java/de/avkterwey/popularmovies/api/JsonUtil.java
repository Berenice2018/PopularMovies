package de.avkterwey.popularmovies.api;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.avkterwey.popularmovies.data.model.MovieItem;
import de.avkterwey.popularmovies.data.model.ReviewItem;
import de.avkterwey.popularmovies.data.model.TrailerItem;

/**
 * Created by Berenice on 19.04.18.
 *
 * parse JSON from a web response
 */

public class JsonUtil {

    public static List<MovieItem> getMovieList(String jsonStringFromServer){
        List<MovieItem> moviesList = new ArrayList<MovieItem>();

        try {
            JSONObject jsonResponse = new JSONObject(jsonStringFromServer);
            JSONArray responseArray = jsonResponse.getJSONArray("results");

            for(int i=0; i< responseArray.length(); ++i){
                JSONObject obj = responseArray.getJSONObject(i);
                MovieItem item = new MovieItem();
                item.setId(obj.optString("id"));
                item.setTitle(obj.optString("title"));
                item.setRating(obj.optString("vote_average", "not rated"));
                item.setThumbPath(obj.optString("poster_path"));
                item.setOverview(obj.optString("overview"));
                item.setReleaseDateString(obj.optString("release_date"));

                moviesList.add(item);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("JsonUtil", "list size = " + moviesList.size());
        return moviesList;
    }


    public static List<TrailerItem> getTrailerVideos(String jsonStringFromServer){
        List<TrailerItem> trailerList = new ArrayList<>();

        try {
            JSONObject jsonObj = new JSONObject(jsonStringFromServer);
            JSONArray jsonArray = jsonObj.getJSONArray("results");

            for(int i = 0; i < jsonArray.length(); ++i){
                JSONObject obj = jsonArray.getJSONObject(i);
                TrailerItem item = new TrailerItem();

                item.setId(obj.optString("id"));
                item.setLanguage_iso_639_1(obj.optString("iso_639_1"));
                item.setCountry_iso_3166_1(obj.optString("iso_3166_1"));
                item.setKey(obj.optString("key"));
                item.setName(obj.optString("name"));
                item.setSite(obj.optString("site"));
                item.setSize(obj.optInt("size"));
                item.setType(obj.optString("type"));

                trailerList.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return trailerList;
    }

    public static List<ReviewItem> getReviewsList(String jsonresponse){
        List<ReviewItem> list = new ArrayList<>();

        try {
            JSONObject jsonObj = new JSONObject(jsonresponse);
            JSONArray jsonArray = jsonObj.getJSONArray("results");

            for (int i=0; i< jsonArray.length(); ++i){
                JSONObject obj = jsonArray.getJSONObject(i);
                ReviewItem item = new ReviewItem();

                item.setAuthor(obj.optString("author"));
                item.setContent(obj.optString("content"));
                item.setId(obj.optString("id"));
                item.setReviewUrl(obj.optString("url"));
                list.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }
}
