package de.avkterwey.popularmovies.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import de.avkterwey.popularmovies.data.viewmodel.MainPageViewModel;

/**
 * Created by administrator on 01.05.18.
 */

public class FavoriteMoviesDbHelper extends SQLiteOpenHelper {

    private final static String TAG = "DbHelper";

    private static final String DATABASE_NAME = "db_favorite_movies";
    private static final int DATABASE_VERSION = 1;


    /**
     * Create a helper object to create, open, and/or manage a database.
     * This method always returns very quickly.  The database is not actually
     * created or opened until one of {@link #getWritableDatabase} or
     * {@link #getReadableDatabase} is called.
     *  @param context to use to open or create the database
     * @param name    of the database file, or null for an in-memory database
     * @param factory to use for creating cursor objects, or null for the default
     * @param version number of the database (starting at 1); if the database is older,
*                {@link #onUpgrade} will be used to upgrade the database; if the database is
*                newer, {@link #onDowngrade} will be used to downgrade the database
     */
    public FavoriteMoviesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // create a String that contains the SQL statement to create a table
        final String SQL_CREATE_FAVORITES_TABLE = "CREATE TABLE "
                + FavoriteMoviesContract.FavoriteMoviesEntry.TABLE_NAME + " ("
                + FavoriteMoviesContract.FavoriteMoviesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + FavoriteMoviesContract.FavoriteMoviesEntry.COL_NAME_MOVIE_ID + " TEXT NON NULL, "
                + FavoriteMoviesContract.FavoriteMoviesEntry.COL_NAME_TITLE + " TEXT, "
                + FavoriteMoviesContract.FavoriteMoviesEntry.COL_NAME_OVERVIEW + " TEXT, "
                + FavoriteMoviesContract.FavoriteMoviesEntry.COL_NAME_POSTER_PATH + " TEXT, "
                + FavoriteMoviesContract.FavoriteMoviesEntry.COL_NAME_RELEASE_DATE + " TEXT, "
                + FavoriteMoviesContract.FavoriteMoviesEntry.COL_NAME_VOTE_AVERAGE + " TEXT"
                //+ ", CONSTRAINT id_constraint UNIQUE (" + FavoriteMoviesContract.FavoriteMoviesEntry.COL_NAME_MOVIE_ID +")"
                + ");";

        Log.d(TAG, "SQL_CREATE_FAVORITES_TABLE = " + SQL_CREATE_FAVORITES_TABLE);

        db.execSQL(SQL_CREATE_FAVORITES_TABLE);
    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     * <p>
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FavoriteMoviesContract.FavoriteMoviesEntry.TABLE_NAME );
        onCreate(db);
    }
}
