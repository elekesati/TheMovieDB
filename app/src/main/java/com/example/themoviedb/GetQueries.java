package com.example.themoviedb;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetQueries {
    @GET("movie/popular")
    Call<Page> getPopularMovies(@Query("page") int page, @Query("api_key") String apiKey);

    @GET("search/movie")
    Call<Page> searchMovie(@Query("page") int page, @Query("api_key") String apiKey, @Query("query") String query);
}
