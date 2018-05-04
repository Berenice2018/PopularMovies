package de.avkterwey.popularmovies.data.model;

/*
 * Created by Berenice on 29.04.18.
 */

public class TrailerItem extends Item {

    private String id;
    private String language_iso_639_1; // "en"
    private String country_iso_3166_1; // "US"
    private String key; // for example a youtube key, "6ZfuNTqbHE8"
    private String name;
    private String site; // YouTube
    private int size; // 1080, 720 …



    // constructors
    public TrailerItem(){}

    public TrailerItem(String id) {
        this.id = id;
    }

    public TrailerItem(String id, String language_iso_639_1, String country_iso_3166_1, String key, String name, String site, int size, String type) {
        this.id = id;
        this.language_iso_639_1 = language_iso_639_1;
        this.country_iso_3166_1 = country_iso_3166_1;
        this.key = key;
        this.name = name;
        this.site = site;
        this.size = size;
        this.type = type;
    }



    // Getter, setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLanguage_iso_639_1() {
        return language_iso_639_1;
    }

    public void setLanguage_iso_639_1(String language_iso_639_1) {
        this.language_iso_639_1 = language_iso_639_1;
    }

    public String getCountry_iso_3166_1() {
        return country_iso_3166_1;
    }

    public void setCountry_iso_3166_1(String country_iso_3166_1) {
        this.country_iso_3166_1 = country_iso_3166_1;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String type; // trailer, clip, featurette, teaser


}
