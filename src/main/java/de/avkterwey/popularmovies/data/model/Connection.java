package de.avkterwey.popularmovies.data.model;

/*
 * Created by Berenice on 05.05.18.
 */

public class Connection {

    private int type;
    private boolean isConnected;

    public Connection(boolean isConnected) {
        this.isConnected = isConnected;
    }

    public Connection(int type, boolean isConnected) {
        this.type = type;
        this.isConnected = isConnected;
    }

    public int getType() {
        return type;
    }

    public boolean getIsConnected() {
        return isConnected;
    }
}