package com.levnovikov.feature_image_search.utils

import com.levnovikov.core_common.AsyncHelper
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

/**
 * Author: lev.novikov
 * Date: 2/4/18.
 */

val testAsyncHelper = object : AsyncHelper {
    override fun doInBackground(runnable: () -> Unit): Future<*> {
        runnable.invoke()
        return fakeFuture()
    }

    private fun fakeFuture(): Future<*> = object : Future<Any> {
        override fun isDone(): Boolean = false

        override fun get(): Any = Any()

        override fun get(p0: Long, p1: TimeUnit?): Any = Any()

        override fun cancel(p0: Boolean): Boolean = true

        override fun isCancelled(): Boolean = false
    }

    override fun doInMainThread(runnable: () -> Unit) {
        runnable.invoke()
    }

}