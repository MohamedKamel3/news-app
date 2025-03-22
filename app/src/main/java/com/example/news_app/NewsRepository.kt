package com.example.news_app

import android.util.Log
import android.view.View
import com.example.news_app.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsRepository(private val apiKey: String,private val country: String, private val binding: ActivityMainBinding) {

    // API Key : (13761077ff5448c5bdedfb779e881639)
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://newsapi.org/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val NewsService = retrofit.create(NewsService::class.java)

    fun fetchWeatherData( onResult: (News?) -> Unit) {
//        binding.progressBar.visibility = View.VISIBLE
        NewsService.getHeadlines(apiKey,country,)
            .enqueue(object : Callback<News> {
                override fun onResponse(call: Call<News>, response: Response<News>) {
//                    binding.progressBar.visibility = View.GONE
                    if (response.isSuccessful) {
                        onResult(response.body())
                    } else {
                        onResult(null)
                    }
                }

                override fun onFailure(call: Call<News>, t: Throwable) {
//                    binding.progressBar.visibility = View.GONE
                    Log.e("NewsRepository", "Error: ${t.message}")
                    onResult(null)
                }
            })
    }
}