package com.example.newsfeed.doubtnut.util

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import com.example.newsfeed.doubtnut.R

object NewsFeedHelperUtil {

    /**
     * @param context
     * @param title String
     * @param message String
     * @param listener DialogInterface.OnClickListener
     */
    fun showGenericErrorMessage(context : Context, title : String, message : String, listener : DialogInterface.OnClickListener){

        AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setCancelable(false)
            .setNegativeButton("Ok", listener)
            .create()
            .show()
    }

    /**
     * @param context
     * @param title String
     * @param message String
     * @param tryAgainListener DialogInterface.OnClickListener
     */
    fun showErrorWithTryAgain(context: Context, title : String, message: String, tryAgainListener : DialogInterface.OnClickListener){
        android.support.v7.app.AlertDialog.Builder(context).setTitle(title)
            .setMessage(message)
            .setPositiveButton(context.getString(R.string.try_again), tryAgainListener)
            .setNegativeButton(context.getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }.setCancelable(false)
            .create()
            .show()
    }
}