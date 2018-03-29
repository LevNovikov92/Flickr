package com.levnovikov.feature_image_search.ui

import android.graphics.Bitmap
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.util.LruCache
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.levnovikov.core_common.AsyncHelper
import com.levnovikov.feature_image_search.R
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

    private val inMemoryCache = LruCache<String, Bitmap>(200)
    private val disposables = mutableMapOf<String, Future<*>>()

    override fun addItems(items: List<ImageVO>) {
        data.addAll(items)
        notifyItemRangeChanged(data.size - items.size, items.size)
    }

    override fun clearData() {
        data.clear()
        notifyDataSetChanged()
    }

    private val data: MutableList<ImageVO> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder =
        ViewHolder(inflater.inflate(R.layout.image_item, parent, false) as ImageView)

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.showPlaceHolder()
        disposables[holder.id]?.apply { Log.d(">>>IMG", "Task canceled") }?.cancel(true)
        val task = asyncHelper.doInBackground {
            try {
                val data = data[position]
                val cachedImg = inMemoryCache.get(data.path)
                val img = if (cachedImg == null) {
                    val loadedImg = imageLoader.loadImage(data.path, data.farm) ?: throw BitmapEncodingException()
                    inMemoryCache.put(data.path, loadedImg)
                    Log.d(">>>IMG", "Loaded image used")
                    loadedImg
                } else {
                    Log.d(">>>IMG", "Cached image used")
                    cachedImg
                }
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