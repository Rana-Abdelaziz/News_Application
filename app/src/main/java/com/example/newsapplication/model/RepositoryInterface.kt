package com.example.newsapplication.model

import retrofit2.Response

interface RepositoryInterface {
    suspend fun getNews(category: String ):Response<NewsModel>
}