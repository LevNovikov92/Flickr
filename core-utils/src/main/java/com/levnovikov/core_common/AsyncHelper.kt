package com.levnovikov.core_common

import android.os.Handler
import java.util.concurrent.Executor

/**
 * Author: lev.novikov
 * Date: 15/3/18.
 */

interface AsyncHelper {
    fun doInBackground(runnable: () -> Unit)
    fun doInMainThread(runnable: () -> Unit)
}

class AsyncHelperImpl constructor(
        private val backgroundExecutor: Executor,
        private val mainHandler: Handler
) : AsyncHelper {
    override fun doInBackground(runnable: () -> Unit) {
        backgroundExecutor.execute(runnable)
    }

    override fun doInMainThread(runnable: () -> Unit) {
        mainHandler.post(runnable)
    }

}