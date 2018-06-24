package com.yagneshlp.spiderinductions.pojo.pojo_artists;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ArtistList {

    @SerializedName("artist")
    @Expose
    private Artist artist;

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

}