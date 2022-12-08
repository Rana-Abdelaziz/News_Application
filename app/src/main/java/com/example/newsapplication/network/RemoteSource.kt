package com.example.newsapplication.network

import com.example.newsapplication.model.NewsModel
import retrofit2.Response

interface RemoteSource {
    suspend fun getNews(category:String): Response<NewsModel>
}