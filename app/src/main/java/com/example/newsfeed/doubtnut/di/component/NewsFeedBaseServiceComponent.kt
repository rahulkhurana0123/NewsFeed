package com.example.newsfeed.doubtnut.di.component

import com.example.newsfeed.doubtnut.di.module.NewsFeedBaseServiceModule
import com.example.newsfeed.doubtnut.model.dao.NewsFeedDao
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NewsFeedBaseServiceModule::class])
interface NewsFeedBaseServiceComponent {
    fun injectNewsFeedBaseServiceModule(dao : NewsFeedDao)
}