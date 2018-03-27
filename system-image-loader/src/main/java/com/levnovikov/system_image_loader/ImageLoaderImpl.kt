package com.levnovikov.system_image_loader

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.ImageView
import com.levnovikov.core_network.HttpClient
import com.levnovikov.core_network.request.Request
import java.io.File
import java.nio.file.Files
import java.nio.file.StandardCopyOption

/**
 * Author: lev.novikov
 * Date: 27/3/18.
 */

class ImageLoaderImpl private constructor() : ImageLoader {

    private lateinit var client: HttpClient
    private lateinit var baseUrl: String
    private lateinit var cache: File

    override fun loadImage(path: String): Bitmap {
        val img = saveImageInInternalStorage(path)
        Log.i(">>>IMAGE", "Image " + path + "is loaded")
        return BitmapFactory.decodeFile(img.absolutePath)
    }

    private fun saveImageInInternalStorage(path: String): File {
        val request = Request.Builder()
                .setMethod(Request.Method.GET)
                .setUrl(baseUrl + path)
                .build()
        val response = client.makeCall(request)
        val img = File(cache, path.replace('/', '_'))
        if (!img.exists()) {
            Files.copy(response.body?.contentStream, img.toPath(), StandardCopyOption.REPLACE_EXISTING)
        }
        return img
    }

    class Builder {
        private val loader = ImageLoaderImpl()

        fun setClient(client: HttpClient): Builder {
            loader.client = client
            return this
        }

        fun setBaseUrl(baseUrl: String): Builder {
            loader.baseUrl = baseUrl
            return this
        }

        fun setCache(dir: File): Builder {
            if (!dir.isDirectory) throw UnsupportedOperationException("Cache should be a directory")
            loader.cache = dir
            return this
        }

        fun build(): ImageLoader {
            return loader
        }
    }

}
