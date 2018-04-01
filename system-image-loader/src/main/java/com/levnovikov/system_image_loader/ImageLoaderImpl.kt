package com.levnovikov.system_image_loader

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.util.LruCache
import com.levnovikov.core_network.HttpClient
import com.levnovikov.core_network.request.Request
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.nio.file.Files
import java.nio.file.StandardCopyOption

/**
 * Author: lev.novikov
 * Date: 27/3/18.
 */

private const val DEFAULT_IN_MEMORY_CACHE_SIZE = 200

class ImageLoaderImpl private constructor() : ImageLoader {

    private lateinit var client: HttpClient
    private lateinit var baseUrl: String
    private lateinit var persistentCache: File

    private var isTerminated = false

    private var inMemoryCacheSize = DEFAULT_IN_MEMORY_CACHE_SIZE
    private val inMemoryCache = LruCache<String, Bitmap>(inMemoryCacheSize)

    override fun loadImage(path: String, farm: Int): Bitmap? {
        if (isTerminated) throw UnsupportedOperationException("Loading after termination")
        inMemoryCache.get(path)?.let { return it.apply { Log.d(">>>IMG", "Cached image used") } }

        val img = saveImageInPersistentStorage(farm, path)
        Log.i(">>>IMG", "Image " + path + "is loaded")
        Log.d(">>>IMG", "Loaded image used")
        return BitmapFactory.decodeFile(img.absolutePath).apply { inMemoryCache.put(path, this) }
    }

    private fun saveImageInPersistentStorage(farm: Int, path: String): File {
        var img: File? = null
        var stream: InputStream? = null
        try {
            val request = Request.Builder()
                    .setMethod(Request.Method.GET)
                    .setUrl("http://farm$farm.$baseUrl${path}_s.jpg")
                    .build()
            val response = client.makeCall(request)
            img = File(persistentCache, "$path/$farm".replace('/', '_'))
            if (!img.exists()) {
                stream = response.body.contentStream
                copy(stream, img)
            }
            return img
        } catch (e: Exception) {
            img?.delete()
            Log.d(">>>IMG_DELETE", "delete file")
            throw e
        } finally {
            stream?.close()
        }
    }

    private fun copy(inputStream: InputStream, file: File) {
        val out = FileOutputStream(file)
        try {
            val buf = ByteArray(1024)
            var length: Int = inputStream.read(buf)
            while(length > 0){
                out.write(buf, 0, length)
                length = inputStream.read(buf)
            }
        } finally {
            out.close()
            inputStream.close()
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
            loader.persistentCache = dir
            return this
        }

        fun setInMemoryCacheSize(countOfImages: Int): Builder { //TODO will be better to specify size in bytes
            loader.inMemoryCacheSize = countOfImages
            return this
        }

        fun build(): ImageLoader {
            return loader
        }
    }

}
