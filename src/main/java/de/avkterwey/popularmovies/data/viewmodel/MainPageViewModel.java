package de.avkterwey.popularmovies.data.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.ContentResolver;
import android.support.annotation.NonNull;

import java.util.List;

import de.avkterwey.popularmovies.MyConstants;
import de.avkterwey.popularmovies.Repo;
import de.avkterwey.popularmovies.data.model.Item;

/*
 * Created by Berenice
 */


public class MainPageViewModel extends AndroidViewModel {
    private static final String TAG = "## MainPage ViewModel";

    private Repo mRepo;
    private LiveData<List<? extends Item>> listLiveData;
    private MutableLiveData<Integer> mSortOrder = new MutableLiveData<>();



    public MainPageViewModel(@NonNull Application application) {
        super(application);
        mRepo = new Repo();
        listLiveData = new MutableLiveData<>();
        setSortOrder(MyConstants.SORT_POPULAR);
         queryApi(MyConstants.ENDPOINT_POPULAR);

    }



    public LiveData<List<? extends Item>> getMovieListLiveData(){
        return listLiveData; //jsonLiveData; // mLiveData;
    }


    public void queryApi(String apiEndpoint) {
        listLiveData = mRepo.loadData(apiEndpoint);
    }

    public void retrieveFavoriteMovies(ContentResolver res){
        listLiveData = mRepo.retrieveFavorites(res);
    }



    public MutableLiveData<Integer> getSortOrder( ){return mSortOrder;}

    public void setSortOrder(int order){
        mSortOrder.postValue(order);
    }



}
