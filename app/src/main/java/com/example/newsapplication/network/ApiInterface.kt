package com.example.newsapplication.network

import com.example.newsapplication.model.NewsModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("top-headlines?country=us")
    suspend fun getNews(
        @Query("category") category : String,
        @Query("apiKey") apiKey : String,
    ): Response<NewsModel>
}