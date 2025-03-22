package com.maad.whatnow

import retrofit2.Call
import retrofit2.http.GET

interface NewsCallable {

    @GET("/v2/top-headlines?country=us&category=general&apiKey=13761077ff5448c5bdedfb779e881639&pageSize=30")
    fun getNews(): Call<News>

}