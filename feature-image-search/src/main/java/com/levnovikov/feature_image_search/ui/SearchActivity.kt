package com.levnovikov.feature_image_search.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.levnovikov.core_common.getComponent
import com.levnovikov.feature_image_search.R
import com.levnovikov.feature_image_search.di.ImageSearchComponent
import com.levnovikov.feature_image_search.di.ImageSearchDependencies

class SearchActivity : AppCompatActivity(), ImageSearchView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        recycler = findViewById(R.id.recycler_view)
        setupDI()
        setupUI()
        presenter.onGetActive()
    }

    private lateinit var presenter: ImageSearchPresenter

    private fun setupDI() {
        application?.getComponent<ImageSearchDependencies>()?.let { dependencies ->
            ImageSearchComponent(this, dependencies).let {
                presenter = it.getPresenter()
            }
        }
    }

    private lateinit var recycler: RecyclerView
    private val layoutManager = GridLayoutManager(this, 3)

    private fun setupUI() {
        recycler.layoutManager = layoutManager
        recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                presenter.onScrolled()
            }
        })
        recycler.adapter = presenter.getAdapter()
    }

//    private fun loadImage(path: String = "/866/26171503967_f609bf5709", farm: String = "1") {
//        asyncHelper.doInBackground {
//            val bitmap = imageLoader.loadImage(path, farm)
//            asyncHelper.doInMainThread {
//                val view = findViewById<ImageView>(R.id.imageView)
//                view.setImageBitmap(bitmap)
//            }
//        }
//    }
//
//    private fun makeRequest() {
//        asyncHelper.doInBackground {
//            val images = imagesRepo.getImages(1, "dog")
//            images.second[0].run {
//                loadImage("/$server/${id}_$secret", farm.toString())
//            }
//        }
//    }

    override fun getLastVisibleItemPosition(): Int =
            layoutManager.findLastVisibleItemPosition()

    override fun showProgress() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideProgress() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
