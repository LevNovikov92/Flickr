package com.levnovikov.core_common

import android.os.Handler
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Future
import java.util.concurrent.ThreadPoolExecutor

/**
 * Author: lev.novikov
 * Date: 15/3/18.
 */

interface AsyncHelper {
    fun doInBackground(runnable: () -> Unit): Future<*>
    fun doInMainThread(runnable: () -> Unit)
}

class AsyncHelperImpl constructor(
        private val backgroundExecutor: ExecutorService,
        private val mainHandler: Handler
) : AsyncHelper {
    override fun doInBackground(runnable: () -> Unit): Future<*> =
        backgroundExecutor.submit(runnable)

    override fun doInMainThread(runnable: () -> Unit) {
        mainHandler.post(runnable)
    }

}