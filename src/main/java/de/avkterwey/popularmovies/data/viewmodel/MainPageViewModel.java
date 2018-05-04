package de.avkterwey.popularmovies.data.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.ContentResolver;
import android.support.annotation.NonNull;

import java.util.List;

import de.avkterwey.popularmovies.MyConstants;
import de.avkterwey.popularmovies.data.model.Item;

/*
 * Created by Berenice
 */


public class MainPageViewModel extends AndroidViewModel {
    private static final String TAG = "## MainPage ViewModel";

    private JsonLiveData jsonLiveData;
    private MutableLiveData<Integer> mSortOrder = new MutableLiveData<>();



    private MutableLiveData<Boolean> mFavoriteListChanged = new MutableLiveData<>();


    public MainPageViewModel(@NonNull Application application) {
        super(application);
        jsonLiveData = new JsonLiveData();
        setSortOrder(MyConstants.SORT_POPULAR);
         queryApi(MyConstants.ENDPOINT_POPULAR);
        mFavoriteListChanged.setValue(false);
    }



    public LiveData<List<? extends Item>> getMovieListLiveData(){
        return jsonLiveData; // mLiveData;
    }


    public void queryApi(String apiEndpoint){
         jsonLiveData.loadData(apiEndpoint);
    }

    public void retrieveFavoriteMovies(ContentResolver res){
         jsonLiveData.retrieveFavorites(res);
    }



    public MutableLiveData<Integer> getSortOrder( ){return mSortOrder;}

    public void setSortOrder(int order){
        mSortOrder.setValue(order);
    }


    public MutableLiveData<Boolean> getFavoriteListChanged() {
        return mFavoriteListChanged;
    }


    public void setFavoriteListChanged(boolean isFav) {
        mFavoriteListChanged.setValue(isFav);
    }

}
