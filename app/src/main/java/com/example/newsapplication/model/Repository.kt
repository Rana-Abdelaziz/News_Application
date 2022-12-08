package com.example.newsapplication.model

import com.example.newsapplication.network.RemoteSource
import retrofit2.Response

class Repository(var remoteSource: RemoteSource):RepositoryInterface {
    companion object{
        private var repoInstance :Repository? = null

        fun getRepoInstance(remoteSource: RemoteSource):Repository{
            return repoInstance ?:Repository(remoteSource)
        }
    }

    override suspend fun getNews(category: String): Response<NewsModel> = remoteSource.getNews(category)
}