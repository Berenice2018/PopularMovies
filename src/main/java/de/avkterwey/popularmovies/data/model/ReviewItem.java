package de.avkterwey.popularmovies.data.model;

/*
 * Created by administrator on 29.04.18.
 */

public class ReviewItem extends Item {

    private String author;
    private String content;
    private String id;
    private String reviewUrl;



    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReviewUrl() {
        return reviewUrl;
    }

    public void setReviewUrl(String reviewUrl) {
        this.reviewUrl = reviewUrl;
    }




}
