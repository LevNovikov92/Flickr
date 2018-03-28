package com.levnovikov.feature_image_search.ui

import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import com.levnovikov.core_common.AsyncHelper
import com.levnovikov.system_image_loader.ImageLoader

/**
 * Author: lev.novikov
 * Date: 28/3/18.
 */
interface ImageSearchView {
    fun getLastVisibleItemPosition(): Int
    fun showProgress()
    fun hideProgress()
}

class ViewHolder(
        private val view: ImageView,
        private val imageLoader: ImageLoader,
        private val asyncHelper: AsyncHelper
) : RecyclerView.ViewHolder(view) {

    private var lastData: ImageVO? = null

    fun bind(data: ImageVO) {
        if (data != lastData) {
            lastData = data
            asyncHelper.doInBackground {
                val img = imageLoader.loadImage(data.path, data.farm)
                asyncHelper.doInMainThread { view.setImageBitmap(img) }
            }
        }
    }
}