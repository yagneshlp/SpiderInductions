package com.yagneshlp.spiderinductions.pojo.pojo_favourites;

public class FavouriteTrack {
    long track_id;
    String track_name,album_name,artist_name,genre;
    int year;

    public FavouriteTrack(){
    }

    public String getTrack_name() {
        return track_name;
    }

    public String getArtist_name() {
        return artist_name;
    }

    public String getAlbum_name() {
        return album_name;
    }

    public long getTrack_id() {
        return track_id;
    }

    public String getGenre() {
        return genre;
    }

    public int getYear() {
        return year;
    }

    public void setTrack_name(String track_name) {
        this.track_name = track_name;
    }

    public void setTrack_id(long track_id) {
        this.track_id = track_id;
    }

    public void setArtist_name(String artist_name) {
        this.artist_name = artist_name;
    }

    public void setAlbum_name(String album_name) {
        this.album_name = album_name;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
