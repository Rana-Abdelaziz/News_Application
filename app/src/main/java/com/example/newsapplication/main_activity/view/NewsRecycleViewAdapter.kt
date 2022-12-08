package com.example.newsapplication.main_activity.view

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapplication.R
import com.example.newsapplication.model.NewsModel
import com.squareup.picasso.Picasso
import java.time.Duration
import java.time.Instant

class NewsRecycleViewAdapter(
    private var newsList: List<NewsModel.Article>,
    private val onClickListener: OnNewClickListener,
    private val context: Context,
    private val shareButtonClickListener: ShareButtonClickListener,
): RecyclerView.Adapter<NewsRecycleViewAdapter.NewsRecycleViewHolder>() {

    class NewsRecycleViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView){
            var newIsImage:ImageView = itemView.findViewById(R.id.news_image)
            var shareImage:ImageView = itemView.findViewById(R.id.share_button)
            var newIsTitle:TextView =itemView.findViewById(R.id.news_title)
            var newIsDate:TextView = itemView.findViewById(R.id.news_date)
            var newIsRow :CardView=itemView.findViewById(R.id.news_item)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsRecycleViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.news_list_item, parent, false)
        return NewsRecycleViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: NewsRecycleViewHolder, position: Int) {
        val instant: Instant = Instant.parse(newsList[position].publishedAt) // start time in UTC
        val duration: Duration = Duration.between(instant, Instant.now() ) // the Duration
        if (duration.toMinutes()<60){
            holder.newIsDate.text ="since ${duration.toMinutes()} minutes"
        }else{
            holder.newIsDate.text ="since ${duration.toHours()} hours"
        }

        if(newsList[position].urlToImage==null)
        {
            holder.newIsImage.setImageResource(R.drawable.no_image)
        }
        else
        {
            Picasso.get().load(newsList[position]?.urlToImage).fit().centerCrop().into(holder.newIsImage)
        }

        holder.newIsTitle.text= newsList[position]?.title
        holder.newIsRow.setOnClickListener {
            newsList[position]?.let { it1 -> onClickListener.onClick(it1) }
        }

        holder.shareImage.setOnClickListener {
            newsList[position]?.url?.let { it1 ->
                shareButtonClickListener.onShareButtonClickListener(it1)
            }
        }


    }

    override fun getItemCount(): Int {
        return newsList.count()
    }

    fun clearNewsList(){
        newsList = arrayListOf()
        notifyDataSetChanged()

    }
    fun setListOfNews(news: List<NewsModel.Article?>?){
        newsList = news as List<NewsModel.Article>
        Log.i("result","in adapter${news.size}")

        notifyDataSetChanged()
    }
}