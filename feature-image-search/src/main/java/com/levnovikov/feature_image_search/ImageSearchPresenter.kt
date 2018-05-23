package com.levnovikov.feature_image_search

import android.annotation.SuppressLint
import android.os.Parcelable
import android.support.v7.widget.RecyclerView
import com.levnovikov.core_api.api.error.RequestException
import com.levnovikov.core_common.Active
import com.levnovikov.data_images.ImagesRepo
import com.levnovikov.data_images.entities.PagerData
import com.levnovikov.feature_image_search.scroll_handler.ImageVOLoader
import com.levnovikov.feature_image_search.scroll_handler.PageLoadingListener
import com.levnovikov.feature_image_search.scroll_handler.ScrollHandler
import com.levnovikov.feature_image_search.scroll_handler.ScrollHandlerFactory
import com.levnovikov.system_image_loader.ImageLoader
import kotlinx.android.parcel.Parcelize

/**
 * Author: lev.novikov
 * Date: 28/3/18.
 */
interface ImageSearchPresenter : Active {
    fun onScrolled()
    fun onSearchClick(text: String)
    fun getState(): Parcelable
}

class ImageSearchPresenterImpl(
        private val view: ImageSearchView,
        private val initialState: SearchScreenState?,
        scrollHandlerFactory: ScrollHandlerFactory
) : ImageSearchPresenter, PageLoadingListener {

    private var text: String = initialState?.text ?: ""

    override fun onSearchClick(text: String) {
        if (text.isEmpty()) {
            view.showHintToast()
        } else {
            this.text = text
            scrollHandler.reloadData(text)
        }
    }

    private val scrollHandler: ScrollHandler
            = scrollHandlerFactory.getEndlessScrollHandler(this, view)

    override fun onGetActive() {
        if (initialState != null && initialState.text.isNotBlank()) {
            scrollHandler.reloadData(initialState.text)
        }
    }

    override fun onScrolled() {
        scrollHandler.onScroll()
    }

    override fun onStartLoading() {
        view.showProgress()
    }

    override fun onLoaded() {
        view.hideProgress()
    }

    override fun onError(e: Exception) {
        view.showErrorToast(e.message)
    }

    override fun getState(): Parcelable = SearchScreenState(text)

}

@SuppressLint("ParcelCreator")
@Parcelize
data class SearchScreenState(val text: String): Parcelable