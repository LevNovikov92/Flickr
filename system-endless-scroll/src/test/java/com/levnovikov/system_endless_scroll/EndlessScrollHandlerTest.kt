package com.levnovikov.system_endless_scroll

import com.levnovikov.system_async_helper.AsyncHelper
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doNothing
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.spy
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.util.concurrent.Future
import java.util.concurrent.FutureTask

/**
 * Author: lev.novikov
 * Date: 25/5/18.
 */
class EndlessScrollHandlerTest {

    private val asyncHelper = object : AsyncHelper {
        override fun doInBackground(runnable: () -> Unit): Future<*> {
            return FutureTask { runnable.invoke() }
        }

        override fun doInMainThread(runnable: () -> Unit) {
            runnable.invoke()
        }

    }

    private lateinit var pageLoader: PageLoader
    private lateinit var pageLoadingListener: PageLoadingListener
    private lateinit var positionProvider: PositionProvider
    private lateinit var scrollHandler: EndlessScrollHandler

    @Before
    fun setUp() {
        pageLoader = mock()
        pageLoadingListener = mock()
        positionProvider = mock()
        scrollHandler = EndlessScrollHandler(
                pageLoader, pageLoadingListener, positionProvider, asyncHelper)
    }

    @Test
    fun resetHandlerState() {
        scrollHandler.run {
            currentPage = 100
            totalPages = 200
            loadingInProgress = true

            resetHandlerState()
            Assert.assertEquals(0, currentPage)
            Assert.assertEquals(1, totalPages)
            Assert.assertFalse(loadingInProgress)
            verify(pageLoader).clearData()
        }
    }

    @Test
    fun reloadData() {
        doNothing().whenever(pageLoader).loadNextPage(any(), any(), any())
        scrollHandler.run {
            currentPage = 100
            totalPages = 200
            loadingInProgress = true

            reloadData()
            Assert.assertEquals(0, currentPage)
            Assert.assertEquals(1, totalPages)
            Assert.assertTrue(loadingInProgress)
            verify(pageLoader).clearData()
            verify(pageLoader).loadNextPage(any(), any(), any())
        }
    }

    @Test
    fun loadNextPage_success() {
        pageLoader = object : PageLoader {
            override fun clearData() { }

            override fun getItemCount(): Int = 0

            override fun loadNextPage(
                    page: Int,
                    onSuccess: (totalPages: Int) -> Unit,
                    onError: (e: Exception) -> Unit) {
                onSuccess.invoke(2)
            }
        }

        val handler = spy(EndlessScrollHandler(
                pageLoader, pageLoadingListener, positionProvider, asyncHelper))

        doNothing().whenever(handler).onScroll()
        handler.run {

            loadNextPage()

            verify(pageLoadingListener).onLoaded()
            Assert.assertEquals(1, currentPage)
            Assert.assertEquals(2, totalPages)
            Assert.assertFalse(loadingInProgress)
        }

    }

    @Test
    fun loadNextPage_error() {
        var clearDataCalls = 0
        pageLoader = object : PageLoader {
            override fun clearData() {
                clearDataCalls++
            }

            override fun getItemCount(): Int = 0

            override fun loadNextPage(
                    page: Int,
                    onSuccess: (totalPages: Int) -> Unit,
                    onError: (e: Exception) -> Unit) {
                onError.invoke(Exception("Error"))
            }
        }

        val handler = spy(EndlessScrollHandler(
                pageLoader, pageLoadingListener, positionProvider, asyncHelper))
        handler.totalPages = 100
        handler.currentPage = 99
        handler.run {

            loadNextPage()

            verify(pageLoadingListener, never()).onLoaded()
            Assert.assertEquals(0, currentPage)
            Assert.assertEquals(1, totalPages)
            Assert.assertFalse(loadingInProgress)
            Assert.assertEquals(1, clearDataCalls)

            verify(pageLoadingListener).onError(any())
        }

    }

    @Test
    fun onScroll() {
        doNothing().whenever(pageLoader).loadNextPage(any(), any(), any())
        scrollHandler.run {
            loadingInProgress = true

            onScroll()
            verify(pageLoader, never()).loadNextPage(any(), any(), any())

            loadingInProgress = false
            currentPage = 1
            totalPages = 100
            whenever(pageLoader.getItemCount()).thenReturn(40)
            whenever(positionProvider.getLastVisibleItemPosition()).thenReturn(10)
            onScroll()
            verify(pageLoader, never()).loadNextPage(any(), any(), any())

            whenever(positionProvider.getLastVisibleItemPosition()).thenReturn(30)
            onScroll()
            verify(pageLoader).loadNextPage(any(), any(), any())
        }

    }

}