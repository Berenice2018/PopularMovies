package de.avkterwey.popularmovies.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import java.net.URL;
import java.util.List;

import de.avkterwey.popularmovies.MyConstants;
import de.avkterwey.popularmovies.R;
import de.avkterwey.popularmovies.api.NetworkUtil;
import de.avkterwey.popularmovies.data.model.Item;
import de.avkterwey.popularmovies.data.model.MovieItem;
import de.avkterwey.popularmovies.data.model.TrailerItem;
import de.avkterwey.popularmovies.data.viewmodel.DetailViewModel;
import de.avkterwey.popularmovies.databinding.ActivityDetailBinding;



public class DetailActivity extends AppCompatActivity {

    private static final String TAG = DetailActivity.class.getSimpleName();

    private DetailViewModel mViewModel;
    private ActivityDetailBinding mBinding;
    private MovieItem mMovie = null;
    private ReviewItemAdapter mReviewAdapter;
    private TrailerItemAdapter mTrailerAdapter;
    private IRecyclerViewClickListener mRecyclerViewClickListener = new IRecyclerViewClickListener() {
        @Override
        public void onClick(View view, int position, Item item) {
            if(item instanceof TrailerItem){
                String videoKey = ((TrailerItem) item).getKey();
                setupYoutubeFragment(videoKey);
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Bundle bundleData = getIntent().getExtras();
        if (bundleData != null) {
            mMovie = bundleData.getParcelable("the movie");
        }
        Log.d(TAG, "mMovie = " + mMovie );

        mViewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
        mReviewAdapter = new ReviewItemAdapter();
        mTrailerAdapter = new TrailerItemAdapter(mRecyclerViewClickListener);

        // set up viewmodel observers
        setUpObservers();

        // dataBinding
        setUpUi();


    }


    // set up UI, DataBinding, FAB click Listener etc.
    private void setUpUi(){
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        mBinding.detailTitle.setText(mMovie.getTitle());
        mBinding.detailReleaseDate.setText(mMovie.getReleaseDateString());
        mBinding.detailRating.setText(mMovie.getRating());
        mBinding.detailOverview.setText(mMovie.getOverview());

        URL thumbUrl = NetworkUtil.buildThumbQueryUrl(mMovie.getThumbPath());

        if(mMovie.getThumbPath() != null && !mMovie.getThumbPath().isEmpty()){
            Glide.with(mBinding.detailThumb.getContext())
                    .load(thumbUrl)
                    .thumbnail(0.01f)
                    .into(mBinding.detailThumb
                    );
        }

        //GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(
                2, StaggeredGridLayoutManager.VERTICAL);

        mBinding.reviewsRecyclerview.setLayoutManager(gridLayoutManager);
        mBinding.reviewsRecyclerview.setAdapter(mReviewAdapter);

        LinearLayoutManager trailerLayoutManager = new LinearLayoutManager(
                this, LinearLayoutManager.HORIZONTAL,  false);
        mBinding.trailersRecyclerview.setLayoutManager(trailerLayoutManager);
        mBinding.trailersRecyclerview.setAdapter(mTrailerAdapter);


        // FAB, add this movie to favorites
        mBinding.fabDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "FAB clicked");
                // TODO add a favorite movie to SQLiteDB:
                mViewModel.toggleFavorite(getContentResolver());
                // TODO on success show a Toast
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();


            }
        });
    }


    private void setUpObservers(){
        // viewModel observers
        mViewModel.queryApi(mMovie);

        mViewModel.getTrailerListLiveData().observe(this, new Observer<List<? extends Item>>(){
            @Override
            public void onChanged(@Nullable List<? extends Item> items) {
//                Log.d(TAG, " list trailer size = " + items.size());
                mTrailerAdapter.setItemList(items);
            }
        });

        mViewModel.getReviewListLivedata().observe(this, new Observer<List<? extends Item>>() {
            @Override
            public void onChanged(@Nullable List<? extends Item> items) {
                //Log.d(TAG, " list reviews size = " + items.size());
                mReviewAdapter.setItemList(items);
            }
        });

        mViewModel.getJsonSimilarVideosLiveData().observe(this, new Observer<List<? extends Item>>() {
            @Override
            public void onChanged(@Nullable List<? extends Item> items) {
                Log.d(TAG, "similar vids size = " + items.size());
            }
        });

    }


    private void setupYoutubeFragment(final String videoKey) {
        YouTubePlayerSupportFragment youtubePlayerFragment = YouTubePlayerSupportFragment.newInstance();

        youtubePlayerFragment.initialize(MyConstants.YOUTUBE_API_KEY,
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
                        if(!wasRestored){
                            player.cueVideo(videoKey);
                            player.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                        }
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider arg0, YouTubeInitializationResult arg1) {
                        Log.e(TAG, "youtube onInitializationFailure()" + arg1);
                    }
                });

        if (youtubePlayerFragment != null){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.youtube_fragment, youtubePlayerFragment).commit();
        }
    }


}
