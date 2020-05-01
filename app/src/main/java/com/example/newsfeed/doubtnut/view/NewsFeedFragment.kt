package com.example.newsfeed.doubtnut.view

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.newsfeed.doubtnut.R
import com.example.newsfeed.doubtnut.model.data.News
import com.example.newsfeed.doubtnut.util.NewsFeedHelperUtil
import com.example.newsfeed.doubtnut.viewModel.NewsFeedViewModel
import kotlinx.android.synthetic.main.fragment_news_feed.view.*

class NewsFeedFragment : Fragment(), NewsFeedAdapter.ItemClickListener {


    private lateinit var mContext: Context
    private lateinit var newsFeedViewModel: NewsFeedViewModel
    private lateinit var adapter: NewsFeedAdapter
    private lateinit var newsFeedLiveData: LiveData<News>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_news_feed, container, false)
        newsFeedViewModel = (mContext as MainActivity).getViewModel()

        initNewsFeed()

        newsFeedLiveData.observe(this,
            Observer<News> {
                if (it != null) {
                    populateNewsFeedList(view)
                } else {
                    NewsFeedHelperUtil.showErrorWithTryAgain(mContext,
                        getString(R.string.generic_error_title),
                        getString(R.string.generic_error_try_again),
                        DialogInterface.OnClickListener { _, _ -> initNewsFeed() })
                }
            })

        return view
    }

    private fun initNewsFeed() {
        newsFeedLiveData = newsFeedViewModel.retrieveNewsFeed(
            getString(R.string.news_feed_end_point),
            getString(R.string.country),
            getString(R.string.category),
            getString(R.string.api_key),
            (mContext as MainActivity).application
        )
    }


    override fun onItemClicked(position: Int) {

        if (!newsFeedViewModel.getWebViewUrl(position).isNullOrBlank()) {

            (mContext as MainActivity).supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.news_feed_container,
                    NewsFeedDetailsFragment.newInstance(newsFeedViewModel.getWebViewUrl(position)!!)
                )
                .addToBackStack(NewsFeedFragment::class.java.simpleName)
                .commit()
        } else {
            NewsFeedHelperUtil.showGenericErrorMessage(mContext,
                mContext.getString(R.string.generic_error_title),
                mContext.getString(R.string.invalid_url),
                DialogInterface.OnClickListener { dialog, _ ->
                    dialog.dismiss()
                })
        }
    }

    private fun populateNewsFeedList(view: View) {
        adapter = NewsFeedAdapter(newsFeedViewModel, mContext, this)
        view.news_feed_list_rv.adapter = adapter
        view.news_feed_list_rv.layoutManager = LinearLayoutManager(mContext)
    }

    override fun onResume() {
        super.onResume()
        try {
            (mContext as MainActivity).showToolBar()
        } catch (e: ClassCastException) {
            e.printStackTrace()
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}