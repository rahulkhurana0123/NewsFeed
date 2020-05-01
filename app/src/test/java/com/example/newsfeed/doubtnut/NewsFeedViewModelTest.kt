package com.example.newsfeed.doubtnut

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.MutableLiveData
import com.example.newsfeed.doubtnut.model.data.Article
import com.example.newsfeed.doubtnut.model.data.News
import com.example.newsfeed.doubtnut.viewModel.NewsFeedViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock


class NewsFeedViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val viewModel: NewsFeedViewModel = NewsFeedViewModel()
    private var article: Article = mock(Article::class.java)
    private var status: String = "active"
    private var totalResult = 1
    private var news: News = News(listOf(article), status, totalResult)

    private var newsFeedLiveData = MutableLiveData<News>()

    @Before
    fun setUp() {
        newsFeedLiveData.value = news
        viewModel.newsFeedLiveData = newsFeedLiveData
    }

    @Test
    fun `get article at position returns a article`() {
        Assert.assertEquals(viewModel.getArticleAtIndex(0), article)
    }

    @Test
    fun `get web url from article`() {
        `when`(news.articles[0].url).thenReturn(" https://www.businesswire.com/news/home/20190724005272/en/Newegg-Extends-Bitcoin-Payment-Option-73-New")
        Assert.assertEquals(viewModel.getWebViewUrl(0)?.isNotEmpty(), true)
    }

}