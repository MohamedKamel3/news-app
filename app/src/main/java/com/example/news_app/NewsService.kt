package com.example.news_app

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("top-headlines")
    fun getHeadlines(
        @Query("apikey") apiKey: String,
        @Query("country") country: String,
        @Query("pageSize") pageSize: Int = 40
    ): Call<News>
}