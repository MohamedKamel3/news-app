package com.example.news_app

data class News(
    val articles: ArrayList<Article>,
)

data class Article(
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
)