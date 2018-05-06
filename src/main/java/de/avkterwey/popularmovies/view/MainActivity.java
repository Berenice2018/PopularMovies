package de.avkterwey.popularmovies.view;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import de.avkterwey.popularmovies.MyConstants;
import de.avkterwey.popularmovies.R;
import de.avkterwey.popularmovies.data.viewmodel.ConnectionLiveData;
import de.avkterwey.popularmovies.data.model.Connection;
import de.avkterwey.popularmovies.data.model.Item;
import de.avkterwey.popularmovies.data.viewmodel.MainPageViewModel;
import de.avkterwey.popularmovies.databinding.ActivityMainBinding;
import de.avkterwey.popularmovies.data.model.MovieItem;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "## MainActivity";

    private MainPageViewModel mViewModel;
    private MovieItemAdapter mAdapter;
    private ConnectionLiveData mConnectionLiveData ;
    private ActivityMainBinding mBinding ;

    private static final int DETAIL_REQUEST_CODE = 10;
    private static final int SPAN_COUNT = 2;
    private static final int SPAN_COUNT_LANDSCAPE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mConnectionLiveData = new ConnectionLiveData(getApplicationContext());
        mViewModel = ViewModelProviders.of(this).get(MainPageViewModel.class);

        IRecyclerViewClickListener listener = (view, position, item) -> {
            MovieItem movieItem = (MovieItem) item ;
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra("the movie", movieItem);
            if(mConnectionLiveData.getValue().getIsConnected())
                startActivityForResult(intent, DETAIL_REQUEST_CODE);
            else
                showNoConnectionMessage();
        };
        mAdapter = new MovieItemAdapter(listener);

        setUpObservers();

        setUpUi();

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == DETAIL_REQUEST_CODE){
            Log.d(TAG, "onActivityResult(), data = " + data);

            if(resultCode == RESULT_OK){
                byte updateList = data.getByteExtra("favListChanged", (byte) 0);
                if(updateList == 1)
                    mViewModel.retrieveFavoriteMovies(getContentResolver());
                else
                    Log.d(TAG, "retrieving list again is not necessary");
            }
        }
    }


    private void setUpObservers() {
        mViewModel.getMovieListLiveData().observe(this, new Observer<List<? extends Item>>() {
            @Override
            public void onChanged(@Nullable List<? extends Item> movieItems) {
                // update UI
                mAdapter.setItemList(movieItems);
               mBinding.progressBar.setVisibility(View.INVISIBLE);
            }
        });


        mViewModel.getSortOrder().observe(this, integer -> {
            switch (integer) {
                case MyConstants.SORT_POPULAR:
                    mViewModel.queryApi(MyConstants.ENDPOINT_POPULAR);
                    break;
                case MyConstants.SORT_TOP_RATED:
                    mViewModel.queryApi(MyConstants.ENDPOINT_TOP_RATED);
                    break;
                case MyConstants.SORT_FAVORITE:
                    mViewModel.retrieveFavoriteMovies(getContentResolver());
                    break;
                default:
                    Log.d(TAG, "case in switch(integer) observer, does not exist");
                    break;
            }
        });


        mConnectionLiveData.observe(this, new Observer<Connection>() {
            @Override
            public void onChanged(@Nullable Connection connection) {
                if (!connection.getIsConnected()) {
                    showNoConnectionMessage();
                }
            }
        });

    }



    private void setUpUi(){

        boolean isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        int spanCount = isLandscape ? SPAN_COUNT_LANDSCAPE : SPAN_COUNT;

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, spanCount);
        mBinding.recyclerview.setLayoutManager(gridLayoutManager);
        mBinding.recyclerview.setAdapter(mAdapter);


        // the toolbar
        mBinding.toolbar.inflateMenu(R.menu.menu_main);
        mBinding.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                mBinding.progressBar.setVisibility(View.VISIBLE);

                switch(item.getItemId()){
                    case R.id.action_popular:
                        showNoConnectionMessage();
                         mViewModel.setSortOrder(MyConstants.SORT_POPULAR);
                        return true;
                    case R.id.action_topvoted:
                        showNoConnectionMessage();
                         mViewModel.setSortOrder(MyConstants.SORT_TOP_RATED);
                        return true;
                    case R.id.action_favorites:
                         mViewModel.setSortOrder(MyConstants.SORT_FAVORITE);
                        return true;
                    default:
                        Log.d(TAG, "clicked item does not exist");
                        return false;
                }

            }
        });

    }


    private void showNoConnectionMessage(){
        if (!mConnectionLiveData.getValue().getIsConnected()) {
            showToast("Please, turn your network connection on.");
        }
    }

    private void showToast(String msg){
        Toast.makeText(MainActivity.this, msg,
                Toast.LENGTH_SHORT)
                .show();
    }
}

