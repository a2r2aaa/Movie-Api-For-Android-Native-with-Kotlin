package com.example.apiRest.ui.main

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TMDbService {
    @GET("movie/now_playing")
    fun getNowPlaying(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Call<MoviesResponse>
}