package de.avkterwey.popularmovies.persistence;

import android.net.Uri;
import android.provider.BaseColumns;

/*
 * Created by Berenice
 */

public final class FavoriteMoviesContract {

    private FavoriteMoviesContract(){}

    public static final String AUTHORITY = "de.avkterwey.popularmovies";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String FAVORITES_PATH = "favorites";


    public static final class FavoriteMoviesEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(FAVORITES_PATH).build();

        public static final String TABLE_NAME = "favoriteMovies";
        public static final String COL_NAME_MOVIE_ID = "movieId";
        public static final String COL_NAME_TITLE = "title";
        public static final String COL_NAME_OVERVIEW = "overview";
        public static final String COL_NAME_VOTE_AVERAGE = "voteaverage";
        public static final String COL_NAME_RELEASE_DATE = "releasedate";
        public static final String COL_NAME_POSTER_PATH = "posterpath";
    }

}
