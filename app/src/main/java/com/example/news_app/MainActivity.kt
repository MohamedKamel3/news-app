package com.example.news_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import com.example.news_app.databinding.ActivityMainBinding
import com.maad.whatnow.Article
import com.maad.whatnow.News
import com.maad.whatnow.NewsAdapter
import com.maad.whatnow.NewsCallable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadNews()

        binding.SwipeRef.setOnRefreshListener { loadNews() }

    }

    private fun loadNews() {
        val retrofit = Retrofit
            .Builder()
            .baseUrl("https://newsapi.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val c = retrofit.create(NewsCallable::class.java)
        c.getNews().enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                if (response.isSuccessful && response.body() != null) {
                    val articles = response.body()?.articles ?: ArrayList()
                    articles.removeAll { it.title == "[Removed]" }
                    showNews(articles)
                } else {
                    Log.d("trace", "Response failed: ${response.errorBody()?.string()}")
                }
                binding.progressBar.isVisible = false
                binding.SwipeRef.isRefreshing = false
            }


            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d("trace", "Error: ${t.message}")
                binding.progressBar.isVisible = false
                binding.SwipeRef.isRefreshing = false
            }
        })
    }

    private fun showNews(articles: ArrayList<Article>) {
        val adapter = NewsAdapter(this, articles)
        binding.newsRecyclerView.adapter = adapter
    }

}