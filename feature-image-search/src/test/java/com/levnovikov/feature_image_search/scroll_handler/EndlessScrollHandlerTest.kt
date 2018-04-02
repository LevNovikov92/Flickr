package com.levnovikov.feature_image_search.scroll_handler

import com.levnovikov.data_images.entities.PagerData
import com.levnovikov.feature_image_search.ImageSearchView
import com.levnovikov.feature_image_search.ImageVO
import com.levnovikov.feature_image_search.ImagesAdapter
import com.levnovikov.feature_image_search.utils.testAsyncHelper
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doNothing
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Test

/**
 * Author: lev.novikov
 * Date: 1/4/18.
 */
class EndlessScrollHandlerTest {

    @Test
    fun loadNextPage_loadOnePage() {
        val adapter = mock<ImagesAdapter>()
        doNothing().whenever(adapter).addItems(any())
        val imageLoader = object : ImageVOLoader {
            override fun loadVO(page: Int, text: String): Pair<List<ImageVO>, PagerData> =
                    getData(1)
        }

        val view = mock<ImageSearchView>()
        val pageLoadingListener = mock<PageLoadingListener>()
        val handler = EndlessScrollHandler(adapter, imageLoader, pageLoadingListener, testAsyncHelper, view)

        handler.loadNextPage()

        verify(adapter).addItems(any())
        verify(pageLoadingListener).onLoaded()
    }

    @Test
    fun loadNextPage_loadSeveralPages() {
        val adapter = object : ImagesAdapter {

            private val data = mutableListOf<ImageVO>()
            override fun addItems(items: List<ImageVO>) {
                data.addAll(items)
            }

            override fun clearData() {
                data.clear()
            }

            override fun itemsCount(): Int = data.size
        }

        val imageLoader = object : ImageVOLoader {
            override fun loadVO(page: Int, text: String): Pair<List<ImageVO>, PagerData> =
                    getData(20)
        }
        val view = mock<ImageSearchView> { on { getLastVisibleItemPosition() } doReturn 10 }
        val pageLoadingListener = mock<PageLoadingListener>()
        val handler = EndlessScrollHandler(adapter, imageLoader, pageLoadingListener, testAsyncHelper, view)

        handler.loadNextPage()

        verify(pageLoadingListener, times(2)).onLoaded()
    }

    fun getData(totalPages: Int): Pair<List<ImageVO>, PagerData> {
        val list = mutableListOf<ImageVO>()
        for (i in 1..20) {
            list.add(ImageVO("path", 1))
        }
        return list to PagerData(1, totalPages)
    }
}