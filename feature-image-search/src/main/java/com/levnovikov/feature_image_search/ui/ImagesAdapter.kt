package com.levnovikov.feature_image_search.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.levnovikov.core_common.AsyncHelper
import com.levnovikov.feature_image_search.R
import com.levnovikov.system_image_loader.ImageLoader

/**
 * Author: lev.novikov
 * Date: 13/3/18.
 */

interface ImagesAdapter {
    fun addItems(items: List<ImageVO>)
    fun clearData()
    fun itemsCount(): Int
}

class ImagesAdapterImpl constructor(
        private val inflater: LayoutInflater,
        private val imageLoader: ImageLoader,
        private val asyncHelper: AsyncHelper
) : RecyclerView.Adapter<ViewHolder>(), ImagesAdapter {

    override fun addItems(items: List<ImageVO>) {
        data.addAll(items)
//        notifyDataSetChanged()
    }

    override fun clearData() {
        data.clear()
        notifyDataSetChanged()
    }

    private val data: MutableList<ImageVO> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder =
        ViewHolder(inflater.inflate(R.layout.image_item, parent, false) as ImageView,
                imageLoader, asyncHelper)

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun itemsCount(): Int = data.size
}

data class ImageVO(val path: String, val farm: Int)