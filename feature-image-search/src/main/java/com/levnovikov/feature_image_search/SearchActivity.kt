package com.levnovikov.feature_image_search

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import com.levnovikov.core_common.AsyncHelper
import com.levnovikov.core_common.getComponent
import com.levnovikov.feature_image_search.data.ImagesRepo
import com.levnovikov.feature_image_search.di.ImageSearchComponent
import com.levnovikov.feature_image_search.di.ImageSearchDependencies
import com.levnovikov.system_image_loader.ImageLoader

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setupDI()
//        loadImage()
        makeRequest()
    }

    private lateinit var asyncHelper: AsyncHelper
    private lateinit var imageLoader: ImageLoader
    private lateinit var imagesRepo: ImagesRepo

    private fun setupDI() {
        application?.getComponent<ImageSearchDependencies>()?.let { dependencies ->
            ImageSearchComponent(this, dependencies).let {
                asyncHelper = it.getAsyncHelper()
                imageLoader = it.getImageLoader()
                imagesRepo = it.getImagesRepo()
            }
        }
    }

    private fun loadImage(path: String = "/866/26171503967_f609bf5709.jpg", farm: String = "1") {
        asyncHelper.doInBackground {
            val bitmap = imageLoader.loadImage(path, farm)
            asyncHelper.doInMainThread {
                val view = findViewById<ImageView>(R.id.imageView)
                view.setImageBitmap(bitmap)
            }
        }
    }

    private fun makeRequest() {
        asyncHelper.doInBackground {
            val images = imagesRepo.getImages(1, "dog")
            images.second[0].run {
                loadImage("/$server/${id}_$secret", farm.toString())
            }
        }
    }
}
