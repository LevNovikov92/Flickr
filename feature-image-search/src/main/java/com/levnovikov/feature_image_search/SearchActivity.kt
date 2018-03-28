package com.levnovikov.feature_image_search

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import com.levnovikov.core_common.AsyncHelper
import com.levnovikov.core_common.getComponent
import com.levnovikov.feature_image_search.di.ImageSearchComponent
import com.levnovikov.feature_image_search.di.ImageSearchDependencies
import com.levnovikov.system_image_loader.ImageLoader

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setupDI()
        loadImage()
    }

    lateinit var asyncHelper: AsyncHelper
    lateinit var imageLoader: ImageLoader

    private fun setupDI() {
        application?.getComponent<ImageSearchDependencies>()?.let { dependencies ->
            ImageSearchComponent(this, dependencies).let {
                asyncHelper = it.getAsyncHelper()
                imageLoader = it.getImageLoader()
            }
        }
    }

    private fun loadImage() {
        val path = "/866/26171503967_f609bf5709.jpg"
        asyncHelper.doInBackground {
            val bitmap = imageLoader.loadImage(path)
            asyncHelper.doInMainThread {
                val view = findViewById<ImageView>(R.id.imageView)
                view.setImageBitmap(bitmap)
            }
        }
    }
}
