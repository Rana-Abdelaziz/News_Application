package com.example.newsapplication.main_activity.view

import com.example.newsapplication.model.NewsModel

interface OnNewClickListener {
    fun onClick(newsModel: NewsModel.Article)
}