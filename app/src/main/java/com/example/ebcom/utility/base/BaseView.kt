package com.example.ebcom.utility.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.example.ebcom.R

interface BaseView {

    val rootView: ViewGroup?
    val viewContext: Context?

    fun setProgressIndicator(mustShow: Boolean) {
        rootView.let {
            viewContext.let { context ->
                var loadingBar = it?.findViewById<View>(R.id.loadingView)
                if (loadingBar == null && mustShow) {
                    loadingBar = LayoutInflater.from(context).inflate(R.layout.loading_bar, it, false) as FrameLayout?
                    rootView?.addView(loadingBar)
                }
                rootView?.visibility = if (mustShow) View.VISIBLE else View.GONE
            }
        }
    }
}