package com.levnovikov.feature_image_search

import com.levnovikov.data_images.ImagesRepo
import com.levnovikov.data_images.entities.Image
import com.levnovikov.data_images.entities.PagerData
import com.levnovikov.system_endless_scroll.ScrollHandler
import com.levnovikov.feature_image_search.text_search_scroll_handler.ScrollHandlerFactory
import com.levnovikov.feature_image_search.text_search_scroll_handler.TextSearchScrollHandler
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import org.junit.Assert
import org.junit.Test

/**
 * Author: lev.novikov
 * Date: 2/4/18.
 */
class ImageSearchPresenterImplTest {
    @Test
    fun onSearchClick() {
        val view = mock<ImageSearchView>()
        val scrollHandler = mock<TextSearchScrollHandler>()
        val scrollHandlerFactory = mock<ScrollHandlerFactory> {
            on { getEndlessScrollHandler(any(), any()) } doReturn scrollHandler }
        val presenter = ImageSearchPresenterImpl(view, mock(), scrollHandlerFactory)

        presenter.onSearchClick("")
        verify(view).showHintToast()

        presenter.onSearchClick("some text")
        verify(scrollHandler).reloadData(any())
    }

    @Test
    fun onGetActive() {
        val scrollHandler = mock<TextSearchScrollHandler>()
        val scrollHandlerFactory = mock<ScrollHandlerFactory> {
            on { getEndlessScrollHandler(any(), any()) } doReturn scrollHandler }
        var presenter = ImageSearchPresenterImpl(mock(), null, scrollHandlerFactory)
        presenter.onGetActive()
        verify(scrollHandler, never()).reloadData(any())

        presenter = ImageSearchPresenterImpl(mock(), SearchScreenState("some text"), scrollHandlerFactory)
        presenter.onGetActive()
        verify(scrollHandler).reloadData(any())
    }

//    @Test
//    fun loadVO() {
//        val data = PagerData(1, 1) to listOf(
//                Image("1", "2", 1, 1),
//                Image("2", "2", 1, 1)
//        )
//        val repo = mock<ImagesRepo> { on { getImages(any(), any()) } doReturn data }
//        val presenter = ImageSearchPresenterImpl(mock(), repo, null, mock())
//        val result = presenter.loadVO(1, "text")
//
//        Assert.assertEquals(data.first, result.second)
//        Assert.assertEquals(2, result.first.size)
//        Assert.assertEquals("/1/1_2", result.first[0].path)
//        Assert.assertEquals("/1/2_2", result.first[1].path)
//    }
}