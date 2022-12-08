package com.example.newsapplication.news_details_activity.view

import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapplication.R
import com.example.newsapplication.databinding.ActivityNewsDetailsBinding
import com.example.newsapplication.model.NewsModel
import com.squareup.picasso.Picasso
import java.time.Duration
import java.time.Instant

class NewsDetailsActivity : AppCompatActivity() {
    lateinit var  news : NewsModel.Article
    lateinit var binding: ActivityNewsDetailsBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        news = intent.getSerializableExtra("new") as NewsModel.Article

        if(news.urlToImage==null)
        {
            binding.newImage.setImageResource(R.drawable.no_image)
        }
        else
        {
            Picasso.get().load(news?.urlToImage).fit().centerCrop().into(binding.newImage)
        }

        binding.newsTitle.text = news.title
        binding.newsDescription.text = news.content
        binding.authorName.text = news.author
        binding.linkText.text = news.url
        binding.linkText.setPaintFlags(binding.linkText.paintFlags or Paint.UNDERLINE_TEXT_FLAG)

        binding.linkText.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(news.url))
            startActivity(browserIntent)
        }

        val instant: Instant = Instant.parse(news.publishedAt) // start time in UTC
        val duration: Duration = Duration.between(instant, Instant.now() ) // the Duration
        if (duration.toMinutes()<60){
            binding.newsDate.text = "since ${duration.toMinutes()} minutes"
        }else{
            binding.newsDate.text = "since ${duration.toHours()} hours"
        }





    }
}