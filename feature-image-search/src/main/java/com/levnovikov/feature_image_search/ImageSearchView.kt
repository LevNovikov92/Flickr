package com.levnovikov.feature_image_search

import android.graphics.Bitmap
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import java.util.*

/**
 * Author: lev.novikov
 * Date: 28/3/18.
 */
interface ImageSearchView {
    fun getLastVisibleItemPosition(): Int
    fun showProgress()
    fun hideProgress()
    fun showHintToast()
    fun showErrorToast(message: String?)
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