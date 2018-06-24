package com.yagneshlp.spiderinductions.pojo.pojo_tracks;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MusicGenre_ {

    @SerializedName("music_genre_id")
    @Expose
    private Long musicGenreId;
    @SerializedName("music_genre_parent_id")
    @Expose
    private Long musicGenreParentId;
    @SerializedName("music_genre_name")
    @Expose
    private String musicGenreName;
    @SerializedName("music_genre_name_extended")
    @Expose
    private String musicGenreNameExtended;
    @SerializedName("music_genre_vanity")
    @Expose
    private String musicGenreVanity;

    public Long getMusicGenreId() {
        return musicGenreId;
    }

    public void setMusicGenreId(Long musicGenreId) {
        this.musicGenreId = musicGenreId;
    }

    public Long getMusicGenreParentId() {
        return musicGenreParentId;
    }

    public void setMusicGenreParentId(Long musicGenreParentId) {
        this.musicGenreParentId = musicGenreParentId;
    }

    public String getMusicGenreName() {
        return musicGenreName;
    }

    public void setMusicGenreName(String musicGenreName) {
        this.musicGenreName = musicGenreName;
    }

    public String getMusicGenreNameExtended() {
        return musicGenreNameExtended;
    }

    public void setMusicGenreNameExtended(String musicGenreNameExtended) {
        this.musicGenreNameExtended = musicGenreNameExtended;
    }

    public String getMusicGenreVanity() {
        return musicGenreVanity;
    }

    public void setMusicGenreVanity(String musicGenreVanity) {
        this.musicGenreVanity = musicGenreVanity;
    }

}
