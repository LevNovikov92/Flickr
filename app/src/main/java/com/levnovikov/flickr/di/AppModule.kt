package com.levnovikov.flickr.di

import android.content.Context

class AppModule(private val context: Context) {
    fun getAppContext() = context
}