package com.levnovikov.feature_image_search.ui

import android.graphics.Bitmap
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import com.levnovikov.feature_image_search.R
import java.util.*

/**
 * Author: lev.novikov
 * Date: 28/3/18.
 */
interface ImageSearchView {
    fun getLastVisibleItemPosition(): Int
    fun showProgress()
    fun hideProgress()
}

class ViewHolder(private val view: ImageView) : RecyclerView.ViewHolder(view) {

    val id = "${Random().nextLong()}--${Random().nextLong()}"

    fun bind(img: Bitmap) {
        view.setImageBitmap(img)
    }

    fun showPlaceHolder() {
        view.setImageResource(R.drawable.ic_launcher_foreground)
    }
}