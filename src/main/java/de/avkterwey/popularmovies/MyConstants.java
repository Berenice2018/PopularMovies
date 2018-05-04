package de.avkterwey.popularmovies;


public class MyConstants {
    // TODO remove API key, use .gitignore
    public static final String API_KEY = "";
    public static final String YOUTUBE_API_KEY = "";

    public static final String BASE_URL = "https://api.themoviedb.org/3/movie";
    public static final String ENDPOINT_POPULAR = "popular";
    public static final String ENDPOINT_TOP_RATED = "top_rated";

    /* From the API docs: Get the videos that have been added to a movie.
    /movie/{movie_id}/videos */
    public static final String ENDPOINT_TRAILERS = "videos";
    /* Get the user reviews for a movie.
    * /movie/{movie_id}/reviews */
    public static final String ENDPOINT_REVIEWS = "reviews";

    /* /movie/{movie_id}/similar*/
    public  static final String ENDPOINT_SIMILAR_MOVIES = "similar";

    public static final String THUMB_BASE_URL = "https://image.tmdb.org/t/p/";
    public static final String THUMB_SIZE = "w185/";

    public static final String YOUTUBE_THUMB_BASE_URL = "https://img.youtube.com/vi/";

    // sort order: popular, top_rated, favorite for the Mainactivity recyclerview
    public static final int SORT_POPULAR = 1;
    public static final int SORT_TOP_RATED = 2;
    public static  final int SORT_FAVORITE = 3;

}
