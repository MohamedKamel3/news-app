package com.example.news_app

import android.app.Activity
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.news_app.databinding.ActivityMainBinding
import com.example.news_app.databinding.NewsCardItemBinding


class NewsAdapter(val a: Activity, val articles: ArrayList<Article>): RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    class NewsViewHolder (val binding: NewsCardItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = NewsCardItemBinding.inflate(a.layoutInflater,parent,false)
        return NewsViewHolder(binding)
    }

    override fun getItemCount()= articles.size

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {

        holder.binding.newsTitle.text = articles[position].title

       Glide
           .with(holder.binding.newsImage.context)
           .load(articles[position].urlToImage)
           .error(R.drawable.broken_image_24)
           .transition(DrawableTransitionOptions.withCrossFade(1000))
           .into(holder.binding.newsImage)

        holder.binding.newsCard.setOnClickListener {
            val url = articles[position].url
            val intent = Intent(Intent.ACTION_VIEW,url.toUri())
            a.startActivity(intent)
        }

        holder.binding.shareBtn.setOnClickListener {
            ShareCompat
                .IntentBuilder(a)
                .setType("text/plain")
                .setChooserTitle("share article with :")
                .setText(articles[position].url)
                .startChooser()
        }
    }

}