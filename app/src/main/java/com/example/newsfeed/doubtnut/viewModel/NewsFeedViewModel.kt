package com.example.newsfeed.doubtnut.viewModel

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.example.newsfeed.doubtnut.DoubtNutNewsFeedApplication
import com.example.newsfeed.doubtnut.model.dao.NewsFeedDao
import com.example.newsfeed.doubtnut.model.data.Article
import com.example.newsfeed.doubtnut.model.data.News
import javax.inject.Inject

class NewsFeedViewModel : ViewModel() {

    lateinit var newsFeedLiveData: LiveData<News>

    @Inject
    lateinit var newsFeedDao: NewsFeedDao


    fun retrieveNewsFeed(endPoint: String, country: String, category: String, API_KEY: String, application: Application): LiveData<News> {
        (application as DoubtNutNewsFeedApplication).getNewsFeedDaoComponent().injectNewsFeedDaoModule(this)
        newsFeedLiveData = newsFeedDao.retrieveNewsFeed(endPoint, country, category, API_KEY, application)
        return newsFeedLiveData
    }

    fun getArticleAtIndex(position: Int?) : Article?{
        return when {
            newsFeedLiveData.value?.articles == null -> null
            newsFeedLiveData.value?.articles?.size!! > position!! -> newsFeedLiveData.value?.articles!![position]
            else -> null
        }
    }



    fun getWebViewUrl(position: Int): String? {
        return if (newsFeedLiveData.value?.articles?.size!! > position)

         newsFeedLiveData.value?.articles!![position].url
        else
            null
    }

}