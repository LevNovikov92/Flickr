package com.levnovikov.system_async_helper

import android.os.Handler
import java.util.concurrent.ExecutorService
import java.util.concurrent.Future

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