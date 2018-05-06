package de.avkterwey.popularmovies.data.viewmodel;

import android.arch.lifecycle.LiveData;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import de.avkterwey.popularmovies.data.model.Connection;

/**
 * Created by Berenice on 05.05.18.
 */

public class ConnectionLiveData extends LiveData<Connection>{
    private Context context;

    public ConnectionLiveData(Context context) {
        this.context = context;
    }

    @Override
    protected void onActive() {
        super.onActive();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(networkReceiver, filter);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        context.unregisterReceiver(networkReceiver);
    }

    private BroadcastReceiver networkReceiver = new BroadcastReceiver() {
        @SuppressWarnings("deprecation")
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getExtras()!=null) {
                NetworkInfo activeNetwork = (NetworkInfo) intent.getExtras().get(ConnectivityManager.EXTRA_NETWORK_INFO);
                boolean isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();
                if(isConnected) {
                    switch (activeNetwork.getType()){
                        case ConnectivityManager.TYPE_WIFI:
                            postValue(new Connection(ConnectivityManager.TYPE_WIFI,true));
                            break;
                        case ConnectivityManager.TYPE_MOBILE:
                            postValue(new Connection(ConnectivityManager.TYPE_MOBILE,true));
                            break;
                    }
                } else {
                    postValue(new Connection(0,false));
                }
            }
        }
    };
}
