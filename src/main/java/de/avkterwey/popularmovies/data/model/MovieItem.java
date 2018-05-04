package de.avkterwey.popularmovies.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/*
 * Created by administrator on 19.04.18.
 */

public class MovieItem extends Item implements Parcelable {

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){

        @Override
        public Object createFromParcel(Parcel source) {
            return new MovieItem(source);
        }

        @Override
        public MovieItem[] newArray(int size) {
            return new MovieItem[size];
        }
    };

    private String id;
    private String title;
    private String thumbPath;
    private String overview;
    private String rating;
    private String releaseDateString;
    private boolean isFavorite;



    public MovieItem(){}

    private MovieItem(Parcel source) {
        this.id = source.readString();
        this.title = source.readString();
        this.thumbPath = source.readString();
        this.overview = source.readString();
        this.rating = source.readString();
        this.releaseDateString = source.readString();
        this.isFavorite = source.readByte() != 0;
    }

    public MovieItem(String id, String title, String thumbPath, String overview, String rating, String releaseDate, byte isFavorite) {
        super();
        this.id = id;
        this.title = title;
        this.thumbPath = thumbPath;
        this.overview = overview;
        this.rating =rating;
        this.releaseDateString = releaseDate;
        this.isFavorite = isFavorite != 0;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbPath() {
        return thumbPath;
    }

    public void setThumbPath(String thumbPath) {
        this.thumbPath = thumbPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReleaseDateString() {
        return releaseDateString;
    }

    public void setReleaseDateString(String releaseDateString) {
        this.releaseDateString = releaseDateString;
    }


    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }



    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("id: ");
        sb.append(id);
        sb.append("\ntitle: ");
        sb.append(title);
        sb.append("\nthumb: ");
        sb.append(thumbPath);
        sb.append("\n");
        sb.append(overview);
        sb.append("\nrating: ");
        sb.append(rating);
        sb.append("\n");
        sb.append(releaseDateString);
        sb.append(isFavorite());
        return sb.toString();
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable
     * instance's marshaled representation. For example, if the object will
     * include a file descriptor in the output of {@link #writeToParcel(Parcel, int)},
     * the return value of this method must include the
     * {@link #CONTENTS_FILE_DESCRIPTOR} bit.
     *
     * @return a bitmask indicating the set of special object types marshaled
     * by this Parcelable object instance.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(thumbPath);
        dest.writeString(overview);
        dest.writeString(rating);
        dest.writeString(releaseDateString);
        dest.writeByte((byte) (isFavorite ? 1 : 0));
    }
}
