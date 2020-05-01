package com.example.newsfeed.doubtnut.view

import android.content.Context
import android.content.DialogInterface
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import com.example.newsfeed.doubtnut.R
import com.example.newsfeed.doubtnut.util.NewsFeedHelperUtil
import com.example.newsfeed.doubtnut.util.withArgs
import kotlinx.android.synthetic.main.fragment_news_feed_details.view.*
import java.lang.Exception

class NewsFeedDetailsFragment : Fragment() {

    private lateinit var mContext: Context
    private var isDialogShown = false

    companion object {
        private const val URL_KEY = "URL_KEY"

        fun newInstance(url: String) = NewsFeedDetailsFragment().withArgs {
            putString(URL_KEY, url)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_news_feed_details, container, false)
    }

    override fun onResume() {
        super.onResume()
        try {
            (mContext as MainActivity).hideToolBar()
        } catch (e: ClassCastException) {
            e.printStackTrace()
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }
        setUpWebView(view)


        view?.news_feed_details_web_view?.webViewClient = object : WebViewClient() {

            override fun onPageStarted(webView: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(webView, url, favicon)
                view?.news_feed_details_progress?.visibility = View.VISIBLE
            }

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                super.onReceivedError(view, request, error)
                view?.news_feed_details_progress?.visibility = View.GONE
                if (isAdded && !isDialogShown) {
                    isDialogShown = true
                    NewsFeedHelperUtil.showGenericErrorMessage(mContext,
                        getString(R.string.generic_error_title),
                        getString(R.string.generic_error_message),
                        DialogInterface.OnClickListener { _, _ ->

                            try {
                                if ((mContext as MainActivity).supportFragmentManager.backStackEntryCount > 1)
                                    (mContext as MainActivity).supportFragmentManager.popBackStack()
                            } catch (e: java.lang.ClassCastException) {
                                e.printStackTrace()
                            }
                        })
                }


            }

            override fun onPageFinished(webView: WebView?, url: String?) {
                super.onPageFinished(webView, url)
                view?.news_feed_details_progress?.visibility = View.GONE

            }


        }

        view?.news_feed_details_web_view?.webChromeClient = object : WebChromeClient() {

            override fun onProgressChanged(webView: WebView?, newProgress: Int) {
                view?.news_feed_details_progress?.progress = newProgress
                if (newProgress == 100) {
                    view?.news_feed_details_progress?.visibility = View.GONE
                }

            }
        }

        try {
            if (arguments != null && arguments?.containsKey(URL_KEY)!!)
                view?.news_feed_details_web_view?.loadUrl(arguments?.getString(URL_KEY))
        } catch (e: Exception) {
            NewsFeedHelperUtil.showGenericErrorMessage(mContext,
                getString(R.string.generic_error_title),
                getString(R.string.generic_error_message),
                DialogInterface.OnClickListener { _, _ ->

                    if ((mContext as MainActivity).supportFragmentManager.backStackEntryCount > 1)
                        (mContext as MainActivity).supportFragmentManager.popBackStack()
                })
        }


    }


    private fun setUpWebView(view: View?) {
        view?.news_feed_details_web_view?.settings?.javaScriptEnabled = true
        view?.news_feed_details_web_view?.settings?.domStorageEnabled = true
        view?.news_feed_details_web_view?.settings?.useWideViewPort = true
        view?.news_feed_details_web_view?.settings?.displayZoomControls = true
        view?.news_feed_details_web_view?.settings?.setSupportZoom(true)
        view?.news_feed_details_web_view?.settings?.builtInZoomControls = true
        view?.news_feed_details_web_view?.clearCache(true)
        view?.news_feed_details_web_view?.clearHistory()
        view?.news_feed_details_web_view?.clearFormData()
        view?.news_feed_details_web_view?.settings?.cacheMode = WebSettings.LOAD_NO_CACHE
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

}