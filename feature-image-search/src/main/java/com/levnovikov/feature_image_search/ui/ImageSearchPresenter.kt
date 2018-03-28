package com.levnovikov.feature_image_search.ui

import android.support.v7.widget.RecyclerView
import com.levnovikov.core_common.mvp_mvvm.Active
import com.levnovikov.feature_image_search.data.ImagesRepo
import com.levnovikov.feature_image_search.data.entities.PagerData
import com.levnovikov.feature_image_search.ui.scroll_handler.ImageVOLoader
import com.levnovikov.feature_image_search.ui.scroll_handler.PageLoadingListener
import com.levnovikov.feature_image_search.ui.scroll_handler.ScrollHandler
import com.levnovikov.feature_image_search.ui.scroll_handler.ScrollHandlerFactory

/**
 * Author: lev.novikov
 * Date: 28/3/18.
 */
interface ImageSearchPresenter : Active {
    fun onScrolled()
    fun getAdapter(): RecyclerView.Adapter<*>
}

class ImageSearchPresenterImpl(
        private val view: ImageSearchView,
        private val imagesRepo: ImagesRepo,
        scrollHandlerFactory: ScrollHandlerFactory
) : ImageSearchPresenter, ImageVOLoader, PageLoadingListener {

    private val scrollHandler: ScrollHandler
            = scrollHandlerFactory.getEndlessScrollHandler(this, this)

    override fun onGetActive() {
        scrollHandler.reloadData("dog")
    }

    override fun loadVO(page: Int, text: String): Pair<List<ImageVO>, PagerData> {
        return imagesRepo.getImages(page, text).run {
            return@run second.map { ImageVO(it.getPath(), it.farm) } to first
        }
    }

    override fun onScrolled() {
        scrollHandler.onScroll()
    }

    override fun getAdapter(): RecyclerView.Adapter<*> {
        return scrollHandler.getAdapter() as RecyclerView.Adapter<*>
    }

    override fun onStartLoading() {
        view.showProgress()
    }

    override fun onLoaded() {
        view.hideProgress()
    }
}