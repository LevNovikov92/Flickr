package com.levnovikov.flickr.di

import android.content.Context
import android.os.Handler
import com.levnovikov.core_common.AsyncHelper
import com.levnovikov.core_common.AsyncHelperImpl
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Author: lev.novikov
 * Date: 27/3/18.
 */

class ThreadingModule(private val context: Context) {

    private fun getBackgroundExecutor(): ExecutorService =
            Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())

    private fun getMainHandler(): Handler =
            Handler(context.mainLooper)

    fun getAsyncHelper(): AsyncHelper {
        return AsyncHelperImpl(getBackgroundExecutor(), getMainHandler())
    }
}
