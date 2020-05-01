package com.example.newsfeed.doubtnut.di.module

import com.example.newsfeed.doubtnut.model.dao.NewsFeedDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class NewsFeedDaoModule {

    @Provides
    @Singleton
    fun  provideDao() : NewsFeedDao {
        return NewsFeedDao()
    }
}