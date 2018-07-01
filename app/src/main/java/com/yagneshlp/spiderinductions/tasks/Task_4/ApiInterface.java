package com.yagneshlp.spiderinductions.tasks.Task_4;

import com.yagneshlp.spiderinductions.pojo.pojo_artist.ArtistResponse;
import com.yagneshlp.spiderinductions.pojo.pojo_artistlist.ArtistListResponse;
import com.yagneshlp.spiderinductions.pojo.pojo_track.TrackResponse;
import com.yagneshlp.spiderinductions.pojo.pojo_tracklist.TrackListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiInterface {
    @GET("chart.tracks.get")
    Call<TrackListResponse> getTopRatedSongs(@Query("apikey") String apiKey, @Query("page") int page, @Query("page_size") int page_size, @Query("country") String country);

    @GET("chart.artists.get")
    Call<ArtistListResponse> getTopRatedArtists(@Query("apikey") String apiKey, @Query("page") int page, @Query("page_size") int page_size, @Query("country") String country);

    @GET("track.get")
    Call<TrackResponse> getTrackById(@Query("apikey") String apiKey, @Query("track_id") long track_id);

    @GET("artist.get")
    Call<ArtistResponse> getArtistById(@Query("apikey") String apiKey, @Query("artist_id") long artist_id);


    @GET("track.search")
    Call<TrackListResponse> getTrackSearchResult(@Query("apikey") String apiKey, @Query("page") int page, @Query("Page_size") int page_size, @Query("q_track") String track_keywords, @Query("s_track_rating") String sorting_order, @Query("format") String format);

}
