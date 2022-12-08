package com.example.newsapplication.main_activity.view_model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapplication.model.NewsModel
import com.example.newsapplication.model.RepositoryInterface
import kotlinx.coroutines.launch

class MainActivityViewModel(var repo:RepositoryInterface) : ViewModel(){
    private var newsLiveData = MutableLiveData<NewsModel>()
    var loading = MutableLiveData<Boolean>()
    var liveDataOfNews = newsLiveData

     fun getNewsFromRemoteSource(category:String){
         loading.value = true
        viewModelScope.launch {
            var res = repo.getNews(category)
            if(res.isSuccessful){
                loading.value = false
                newsLiveData.postValue(repo.getNews(category).body())
            }else{
                loading.value = false
                Log.i("result","${res.errorBody()}")
                Log.i("result","${res.code()}")

            }

        }


    }
}