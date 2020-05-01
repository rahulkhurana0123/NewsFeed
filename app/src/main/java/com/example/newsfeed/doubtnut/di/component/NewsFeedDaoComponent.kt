package com.example.newsfeed.doubtnut.di.component

import com.example.newsfeed.doubtnut.di.module.NewsFeedDaoModule
import com.example.newsfeed.doubtnut.viewModel.NewsFeedViewModel
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [NewsFeedDaoModule::class])
interface NewsFeedDaoComponent {
    fun injectNewsFeedDaoModule(viewModel : NewsFeedViewModel)
}