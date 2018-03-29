package com.levnovikov.system_image_loader

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.levnovikov.core_network.HttpClient
import com.levnovikov.core_network.request.Request
import java.io.File
import java.io.IOException
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

    override fun loadImage(path: String, farm: Int): Bitmap? {
        val img = saveImageInInternalStorage(farm, path)
        Log.i(">>>IMAGE", "Image " + path + "is loaded")
        return BitmapFactory.decodeFile(img.absolutePath)
    }

    private fun saveImageInInternalStorage(farm: Int, path: String): File {
        var img: File? = null
        try {
            val request = Request.Builder()
                    .setMethod(Request.Method.GET)
                    .setUrl("http://farm$farm.$baseUrl$path.jpg")
                    .build()
            val response = client.makeCall(request)
            img = File(cache, "$path/$farm".replace('/', '_'))
            if (!img.exists()) {
                val stream = response.body?.contentStream
                Files.copy(stream, img.toPath(), StandardCopyOption.REPLACE_EXISTING)
                stream?.close()
            }
            return img
        } catch (e: Exception) {
            img?.delete()
            Log.d(">>>IMG_DELETE", "delete file")
            throw e
        }
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
