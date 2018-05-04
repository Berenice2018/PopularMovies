package de.avkterwey.popularmovies.view;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import de.avkterwey.popularmovies.MyConstants;
import de.avkterwey.popularmovies.R;
import de.avkterwey.popularmovies.data.model.Item;
import de.avkterwey.popularmovies.data.viewmodel.DetailViewModel;
import de.avkterwey.popularmovies.data.viewmodel.MainPageViewModel;
import de.avkterwey.popularmovies.databinding.ActivityMainBinding;
import de.avkterwey.popularmovies.data.model.MovieItem;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "## MainActivity";

    private MainPageViewModel mViewModel;
    private MovieItemAdapter mAdapter;
    private static final int SPAN_COUNT = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = ViewModelProviders.of(this).get(MainPageViewModel.class);

        IRecyclerViewClickListener listener = (view, position, item) -> {
            MovieItem movieItem = (MovieItem) item ;
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra("the movie", movieItem);
            startActivity(intent);
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



    private void setUpObservers(){
        mViewModel.getMovieListLiveData().observe(this, new Observer<List<? extends Item>>() {
            @Override
            public void onChanged(@Nullable List<? extends Item> movieItems) {
                // update UI
                mAdapter.setItemList(movieItems);

            }
        });


         mViewModel.getSortOrder().observe(this, integer -> {
            switch(integer){
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


         DetailViewModel detailVM =  ViewModelProviders.of(this).get(DetailViewModel.class);
        detailVM.getFavoriteListChangedLiveData().observe(this, new Observer<Boolean>() {
             @Override
             public void onChanged(@Nullable Boolean aBoolean) {
                 Log.d(TAG, "### getFavoriteListChangedLiveData CHANGED");
                 mViewModel.retrieveFavoriteMovies(getContentResolver());
             }
         });
    }


    private void setUpUi(){

        ActivityMainBinding mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, SPAN_COUNT);
        mBinding.recyclerview.setLayoutManager(gridLayoutManager);
        mBinding.recyclerview.setAdapter(mAdapter);


        // the toolbar
        mBinding.toolbar.inflateMenu(R.menu.menu_main);
        mBinding.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch(item.getItemId()){
                    case R.id.action_popular:
                         mViewModel.setSortOrder(MyConstants.SORT_POPULAR);
                        return true;
                    case R.id.action_topvoted:
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

}

