package com.example.newsapplication.main_activity.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsapplication.model.RepositoryInterface

class MainActivityViewModelFactory(val repo:RepositoryInterface):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            MainActivityViewModel(repo) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}