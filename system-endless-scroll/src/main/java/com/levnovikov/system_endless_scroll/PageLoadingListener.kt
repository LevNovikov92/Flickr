package com.levnovikov.system_endless_scroll

interface PageLoadingListener {
    fun onStartLoading()
    fun onLoaded()
    fun onError(e: Exception)
}