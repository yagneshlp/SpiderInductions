
package com.yagneshlp.spiderinductions.pojo.pojo_artistlist;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Artist {

    @SerializedName("artist_id")
    @Expose
    private Long artistId;
    @SerializedName("artist_mbid")
    @Expose
    private String artistMbid;
    @SerializedName("artist_name")
    @Expose
    private String artistName;
    @SerializedName("artist_name_translation_list")
    @Expose
    private List<ArtistNameTranslationList> artistNameTranslationList = null;
    @SerializedName("artist_comment")
    @Expose
    private String artistComment;
    @SerializedName("artist_country")
    @Expose
    private String artistCountry;
    @SerializedName("artist_alias_list")
    @Expose
    private List<ArtistAliasList> artistAliasList = null;
    @SerializedName("artist_rating")
    @Expose
    private Long artistRating;
    @SerializedName("primary_genres")
    @Expose
    private PrimaryGenres primaryGenres;
    @SerializedName("secondary_genres")
    @Expose
    private SecondaryGenres secondaryGenres;
    @SerializedName("artist_twitter_url")
    @Expose
    private String artistTwitterUrl;
    @SerializedName("artist_vanity_id")
    @Expose
    private String artistVanityId;
    @SerializedName("artist_edit_url")
    @Expose
    private String artistEditUrl;
    @SerializedName("artist_share_url")
    @Expose
    private String artistShareUrl;
    @SerializedName("artist_credits")
    @Expose
    private ArtistCredits artistCredits;
    @SerializedName("restricted")
    @Expose
    private Long restricted;
    @SerializedName("managed")
    @Expose
    private Long managed;
    @SerializedName("updated_time")
    @Expose
    private String updatedTime;

    public Long getArtistId() {
        return artistId;
    }

    public void setArtistId(Long artistId) {
        this.artistId = artistId;
    }

    public String getArtistMbid() {
        return artistMbid;
    }

    public void setArtistMbid(String artistMbid) {
        this.artistMbid = artistMbid;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public List<ArtistNameTranslationList> getArtistNameTranslationList() {
        return artistNameTranslationList;
    }

    public void setArtistNameTranslationList(List<ArtistNameTranslationList> artistNameTranslationList) {
        this.artistNameTranslationList = artistNameTranslationList;
    }

    public String getArtistComment() {
        return artistComment;
    }

    public void setArtistComment(String artistComment) {
        this.artistComment = artistComment;
    }

    public String getArtistCountry() {
        return artistCountry;
    }

    public void setArtistCountry(String artistCountry) {
        this.artistCountry = artistCountry;
    }

    public List<ArtistAliasList> getArtistAliasList() {
        return artistAliasList;
    }

    public void setArtistAliasList(List<ArtistAliasList> artistAliasList) {
        this.artistAliasList = artistAliasList;
    }

    public Long getArtistRating() {
        return artistRating;
    }

    public void setArtistRating(Long artistRating) {
        this.artistRating = artistRating;
    }

    public PrimaryGenres getPrimaryGenres() {
        return primaryGenres;
    }

    public void setPrimaryGenres(PrimaryGenres primaryGenres) {
        this.primaryGenres = primaryGenres;
    }

    public SecondaryGenres getSecondaryGenres() {
        return secondaryGenres;
    }

    public void setSecondaryGenres(SecondaryGenres secondaryGenres) {
        this.secondaryGenres = secondaryGenres;
    }

    public String getArtistTwitterUrl() {
        return artistTwitterUrl;
    }

    public void setArtistTwitterUrl(String artistTwitterUrl) {
        this.artistTwitterUrl = artistTwitterUrl;
    }

    public String getArtistVanityId() {
        return artistVanityId;
    }

    public void setArtistVanityId(String artistVanityId) {
        this.artistVanityId = artistVanityId;
    }

    public String getArtistEditUrl() {
        return artistEditUrl;
    }

    public void setArtistEditUrl(String artistEditUrl) {
        this.artistEditUrl = artistEditUrl;
    }

    public String getArtistShareUrl() {
        return artistShareUrl;
    }

    public void setArtistShareUrl(String artistShareUrl) {
        this.artistShareUrl = artistShareUrl;
    }

    public ArtistCredits getArtistCredits() {
        return artistCredits;
    }

    public void setArtistCredits(ArtistCredits artistCredits) {
        this.artistCredits = artistCredits;
    }

    public Long getRestricted() {
        return restricted;
    }

    public void setRestricted(Long restricted) {
        this.restricted = restricted;
    }

    public Long getManaged() {
        return managed;
    }

    public void setManaged(Long managed) {
        this.managed = managed;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }

}