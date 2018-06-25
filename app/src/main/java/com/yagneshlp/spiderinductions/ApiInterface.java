package com.yagneshlp.spiderinductions;

import com.yagneshlp.spiderinductions.pojo.pojo_artists.ArtistResponse;
import com.yagneshlp.spiderinductions.pojo.pojo_track.TrackResponse;
import com.yagneshlp.spiderinductions.pojo.pojo_tracklist.TrackListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiInterface {
    @GET("chart.tracks.get")
    Call<TrackListResponse> getTopRatedSongs(@Query("apikey") String apiKey, @Query("page") int page, @Query("page_size") int page_size, @Query("country") String country);

    @GET("chart.artists.get")
    Call<ArtistResponse> getTopRatedArtists(@Query("apikey") String apiKey, @Query("page") int page, @Query("page_size") int page_size, @Query("country") String country);

    @GET("track.get")
    Call<TrackResponse> getTrackById(@Query("apikey") String apiKey, @Query("track_id") long track_id);


    @GET("track.search")
    Call<TrackListResponse> getMovieDetails(@Query("apikey") String apiKey, @Query("page") int page, @Query("Page_size") int page_size, @Query("q_track") String track_keywords, @Query("s_track_rating") String sorting_order, @Query("format") String format);

}
