package com.maad.whatnow

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.news_app.R
import com.example.news_app.databinding.NewsCardItemBinding

class NewsAdapter(
    private val a: Activity,
    private val articles: ArrayList<Article>
) :
    Adapter<NewsAdapter.NewsViewHolder>() {

    class NewsViewHolder(val binding: NewsCardItemBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val b = NewsCardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(b)
    }

    override fun getItemCount() = articles.size

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
            val i = Intent(Intent.ACTION_VIEW, url.toUri())
            a.startActivity(i)
        }

        holder.binding.shareBtn.setOnClickListener {
            ShareCompat
                .IntentBuilder(a)
                .setType("text/plain")
                .setChooserTitle("Share article with: ")
                .setText(articles[position].url)
                .startChooser()
        }

    }


}