package com.yagneshlp.spiderinductions.pojo.pojo_favourites;

public class FavouriteArtist {
    long artist_id;
    String artist_name,genre;
    int rating;

    public FavouriteArtist(){

    }

    public String getGenre() {
        return genre;
    }

    public String getArtist_name() {
        return artist_name;
    }

    public long getArtist_id() {
        return artist_id;
    }

    public int getRating() {
        return rating;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setArtist_name(String artist_name) {
        this.artist_name = artist_name;
    }

    public void setArtist_id(long artist_id) {
        this.artist_id = artist_id;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
