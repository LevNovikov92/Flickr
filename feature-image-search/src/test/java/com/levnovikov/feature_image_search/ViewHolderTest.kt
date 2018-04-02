package com.levnovikov.feature_image_search

import com.nhaarman.mockito_kotlin.mock
import org.junit.Assert
import org.junit.Test

import org.junit.Assert.*

/**
 * Author: lev.novikov
 * Date: 2/4/18.
 */
class ViewHolderTest {
    @Test
    fun getId() {
        val vh = ViewHolder(mock())
        Assert.assertEquals(vh.id, vh.id)
    }

}