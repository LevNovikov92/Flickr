package com.levnovikov.feature_image_search

import android.graphics.Bitmap
import android.widget.ImageView
import com.levnovikov.feature_image_search.utils.testAsyncHelper
import com.levnovikov.system_image_loader.ImageLoader
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doNothing
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Assert
import org.junit.Test

import org.junit.Assert.*

/**
 * Author: lev.novikov
 * Date: 2/4/18.
 */
class ImagesAdapterImplTest {

    @Test
    fun onBindViewHolder() {
        val imageLoader = mock<ImageLoader> { on { loadImage(any(), any()) } doReturn mock<Bitmap>() }
        val adapter = ImagesAdapterImpl(mock(), imageLoader, testAsyncHelper)

        val vh = getFakeViewHolder()
        adapter.data.addAll(listOf(ImageVO("path", 1), ImageVO("path", 1)))
        adapter.onBindViewHolder(vh, 1)

        verify(imageLoader).loadImage(any(), any())
        Assert.assertEquals(1, adapter.disposables.size)
        val task1 = adapter.disposables[vh.id]

        adapter.onBindViewHolder(vh, 1)
        Assert.assertEquals(1, adapter.disposables.size)
        val task2 = adapter.disposables[vh.id]

        Assert.assertNotEquals(task1, task2)
    }

    private fun getFakeViewHolder(): ViewHolder {
        val view = mock<ImageView>()
        doNothing().`when`(view).setImageBitmap(any())
        return ViewHolder(view)
    }
}