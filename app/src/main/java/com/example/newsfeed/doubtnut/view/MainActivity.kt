package com.example.newsfeed.doubtnut.view

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.newsfeed.doubtnut.R
import com.example.newsfeed.doubtnut.viewModel.NewsFeedViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var newsFeedViewModel: NewsFeedViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // attaching the view model to activity so that all fragments within the scope can get hold of view model
        newsFeedViewModel = ViewModelProviders.of(this).get(NewsFeedViewModel::class.java)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.news_feed_container, NewsFeedFragment())
            .addToBackStack(NewsFeedFragment::class.java.simpleName)
            .commit()
    }

    fun getViewModel(): NewsFeedViewModel {
        return newsFeedViewModel
    }

    fun hideToolBar() {
        try {
            news_feed_tool_bar.visibility = View.GONE
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun showToolBar() {
        try {
            news_feed_tool_bar.visibility = View.VISIBLE
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        // Finish the activity when back pressed from NewsFeedFragment
        if (supportFragmentManager.backStackEntryCount == 0) {
            finish()
        }
    }


}
