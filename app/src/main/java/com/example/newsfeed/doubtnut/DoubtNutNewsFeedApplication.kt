package com.example.newsfeed.doubtnut

import android.app.Application
import com.example.newsfeed.doubtnut.di.component.DaggerNewsFeedBaseServiceComponent
import com.example.newsfeed.doubtnut.di.component.DaggerNewsFeedDaoComponent
import com.example.newsfeed.doubtnut.di.component.NewsFeedBaseServiceComponent
import com.example.newsfeed.doubtnut.di.component.NewsFeedDaoComponent
import com.example.newsfeed.doubtnut.di.module.NewsFeedBaseServiceModule
import com.example.newsfeed.doubtnut.di.module.NewsFeedDaoModule

class DoubtNutNewsFeedApplication : Application() {

    private lateinit var newsFeedDaoComponent: NewsFeedDaoComponent

    private lateinit var newsFeedBaseServiceComponent: NewsFeedBaseServiceComponent


    override fun onCreate() {
        super.onCreate()

        newsFeedDaoComponent = DaggerNewsFeedDaoComponent.builder()
            .newsFeedDaoModule(NewsFeedDaoModule())
            .build()

        newsFeedBaseServiceComponent = DaggerNewsFeedBaseServiceComponent.builder()
            .newsFeedBaseServiceModule(NewsFeedBaseServiceModule(this))
            .build()
    }

    fun getNewsFeedDaoComponent(): NewsFeedDaoComponent {
        return newsFeedDaoComponent
    }

    fun getNewsFeedBaseServiceComponent(): NewsFeedBaseServiceComponent {
        return newsFeedBaseServiceComponent
    }

}