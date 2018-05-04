package de.avkterwey.popularmovies.api;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.io.IOException;
import java.net.URL;

public class MovieDataTaskLoader extends AsyncTaskLoader<String> {

    private static final String TAG = "MovieDataTaskLoader";

    private URL mQueryUrl;
    private String mMovieDataAsJsonString;

    public MovieDataTaskLoader(Context context, URL queryUrl) {
        super(context);
        mQueryUrl = queryUrl;
    }

    /**
     * Called on a worker thread to perform the actual load and to return
     * the result of the load operation.
     * <p>
     * Implementations should not deliver the result directly, but should return them
     * from this method, which will eventually end up calling {@link #deliverResult} on
     * the UI thread.  If implementations need to process the results on the UI thread
     * they may override {@link #deliverResult} and do so there.
     * <p>
     * To support cancellation, this method should periodically check the value of
     * {@link #isLoadInBackgroundCanceled} and terminate when it returns true.
     * Subclasses may also override {@link #cancelLoadInBackground} to interrupt the load
     * directly instead of polling {@link #isLoadInBackgroundCanceled}.
     * <p>
     * When the load is canceled, this method may either return normally or throw
     * {@link OperationCanceledException}.  In either case, the {@link Loader} will
     * call {@link #onCanceled} to perform post-cancellation cleanup and to dispose of the
     * result object, if any.
     *
     * @return The result of the load operation.
     * @throws OperationCanceledException if the load is canceled during execution.
     * @see #isLoadInBackgroundCanceled
     * @see #cancelLoadInBackground
     * @see #onCanceled
     */
    @Override
    public String loadInBackground() {
        try {
            mMovieDataAsJsonString = NetworkUtil.getResponseFromHttpUrl(mQueryUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return mMovieDataAsJsonString;
    }


    @Override
    protected void onStartLoading() {
        //like AsyncTask onPreExecute() method, start the progress bar, at the end call forceLoad();
        if(mMovieDataAsJsonString != null){
            deliverResult(mMovieDataAsJsonString);
        } else {
            forceLoad();
        }
    }


    // this is on the UI thread, keep it short
    @Override
    public void deliverResult(String data){
        mMovieDataAsJsonString = data;
        super.deliverResult(data);
    }
}