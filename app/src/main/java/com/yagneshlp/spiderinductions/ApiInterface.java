package com.yagneshlp.spiderinductions;

import com.yagneshlp.spiderinductions.pojo.pojo_artists.ArtistResponse;
import com.yagneshlp.spiderinductions.pojo.pojo_tracks.MusicTrackResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiInterface {
    @GET("chart.tracks.get")
    Call<MusicTrackResponse> getTopRatedSongs(@Query("apikey") String apiKey, @Query("page") int page, @Query("page_size") int page_size, @Query("country") String country);

    @GET("chart.artists.get")
    Call<ArtistResponse> getTopRatedArtists(@Query("apikey") String apiKey, @Query("page") int page, @Query("page_size") int page_size, @Query("country") String country);


    @GET("track.search")
    Call<MusicTrackResponse> getMovieDetails(@Query("apikey") String apiKey,@Query("page") int page, @Query("Page_size") int page_size,@Query("q_track") String track_keywords, @Query("s_track_rating") String sorting_order, @Query("format") String format);

}
