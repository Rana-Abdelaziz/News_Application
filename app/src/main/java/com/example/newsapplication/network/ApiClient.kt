package com.example.newsapplication.network

import com.example.newsapplication.model.NewsModel
import com.example.newsapplication.utils.Constants
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient private constructor():RemoteSource {

    companion object{
        private var instance:ApiClient? = null
        fun getInstance():ApiClient?{
            return instance ?:ApiClient()
        }
    }

    object RetrofitHelper{
        const val newsUrl ="https://newsapi.org/v2/"


        fun getInstance(): Retrofit {
            return Retrofit.Builder().baseUrl(newsUrl)
                .addConverterFactory(GsonConverterFactory.create())
                // we need to add converter factory to
                // convert JSON object to Java object
                .build()
        }
    }

    override suspend fun getNews(category: String): Response<NewsModel> {
        val newsService = RetrofitHelper.getInstance().create(ApiInterface::class.java)
        return newsService.getNews(category,Constants.APIKEY)
    }
}