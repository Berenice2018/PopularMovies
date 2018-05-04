package de.avkterwey.popularmovies.api;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import static de.avkterwey.popularmovies.MyConstants.API_KEY;
import static de.avkterwey.popularmovies.MyConstants.BASE_URL;
import static de.avkterwey.popularmovies.MyConstants.THUMB_BASE_URL;
import static de.avkterwey.popularmovies.MyConstants.THUMB_SIZE;
import static de.avkterwey.popularmovies.MyConstants.YOUTUBE_THUMB_BASE_URL;

/*
 * Created by Berenice
 */

public class NetworkUtil {

    private static final String TAG = "NetworkUtil";


    // build the URL

    public static URL buildQueryUrl(String endpoint){
       /* if(!endpoint.equals(ENDPOINT_POPULAR) || !endpoint.equals(ENDPOINT_TOP_RATED)){
            // TODO throw exception
            return null;
        }*/

        Uri builtUri = Uri.parse(BASE_URL + "/" + endpoint).buildUpon()
                .appendQueryParameter("api_key", API_KEY )
                .build();

        URL queryUrl = null;

        try{
            queryUrl = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "queryUrl =  " + queryUrl);
        return queryUrl;
    }


    public static URL buildQueryUrl(String movieId, String endpoint){
        Uri builtUri = Uri.parse(BASE_URL + "/" + movieId +"/"+ endpoint).buildUpon()
                .appendQueryParameter("api_key", API_KEY)
                .build();

        URL queryUrl = null;

        try {
            queryUrl = new URL((builtUri.toString()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "queryUrl =  " + queryUrl);
        return queryUrl;
    }




    public static URL buildThumbQueryUrl(String thumbPath){
        Uri builtUri = Uri.parse(THUMB_BASE_URL + THUMB_SIZE + thumbPath ).buildUpon()
                .build();

        URL queryUrl = null;
        try{
            queryUrl = new URL(builtUri.toString());
        } catch (MalformedURLException e){
            e.printStackTrace();
        }

        return queryUrl;
    }


    public static URL buildYoutubeThumbQueryUrl(String videoKey){
        // https://img.youtube.com/vi/ "here goes your video id"/0.jpg
        Uri builtUri = Uri.parse(YOUTUBE_THUMB_BASE_URL + videoKey + "/mqdefault.jpg" );
        URL queryUrl = null;
        try {
            queryUrl = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return queryUrl;
    }

    // This method returns the entire result from the HTTP response.
    public static String getResponseFromHttpUrl(URL queryUrl) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) queryUrl.openConnection();
        //int statusCode = urlConnection.getResponseCode();


        try{
            InputStream input  = urlConnection.getInputStream();
            //BufferedReader buffReader = new BufferedReader(new InputStreamReader(input));
            Scanner scanner = new Scanner(input);
            scanner.useDelimiter("\\A");

            if(scanner.hasNext()){
                return scanner.next();
            } else {
                return null;
            }

        } finally{
            urlConnection.disconnect();
        }
    }
}
