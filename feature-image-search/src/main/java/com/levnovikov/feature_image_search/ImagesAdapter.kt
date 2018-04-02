package com.levnovikov.feature_image_search

import android.support.annotation.VisibleForTesting
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.levnovikov.core_common.AsyncHelper
import com.levnovikov.system_image_loader.ImageLoader
import java.io.IOException
import java.util.concurrent.Future

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

    @VisibleForTesting
    internal val disposables = mutableMapOf<String, Future<*>>()

    override fun addItems(items: List<ImageVO>) {
        data.addAll(items)
        notifyItemRangeChanged(data.size - items.size, items.size)
    }

    override fun clearData() {
        data.clear()
        notifyDataSetChanged()
    }

    @VisibleForTesting
    internal val data: MutableList<ImageVO> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder =
            ViewHolder(inflater.inflate(R.layout.image_item, parent, false) as ImageView)

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.showPlaceHolder()
        disposables[holder.id]?.cancel(true)
        val task = asyncHelper.doInBackground {
            try {
                val data = data[position]
                val img = imageLoader.loadImage(data.path, data.farm) ?: throw BitmapEncodingException()
                asyncHelper.doInMainThread {
                    holder.bind(img)
                    disposables.remove(holder.id)
                }
            } catch (e: IOException) {
                //do nothing
            } catch (e: Exception) {
                Log.i(">>>EXCEPTION!!!", e.message)
            }
        }
        disposables[holder.id] = task
    }

    override fun itemsCount(): Int = data.size
}

data class ImageVO(val path: String, val farm: Int)

class BitmapEncodingException : Exception() {
    override val message: String?
        get() = "Bitmap encoding exception"
}