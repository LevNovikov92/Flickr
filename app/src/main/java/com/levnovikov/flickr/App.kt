package com.levnovikov.flickr

import android.app.Application
import com.levnovikov.core_common.ComponentProvider
import com.levnovikov.feature_image_search.di.ImageSearchDependencies
import com.levnovikov.flickr.di.AppComponent
import kotlin.reflect.KClass

/**
 * Author: lev.novikov
 * Date: 27/3/18.
 */

class App : Application(), ComponentProvider {

    override fun onCreate() {
        super.onCreate()
        setupDI()
    }

    private lateinit var component: AppComponent

    private fun setupDI() {
        component = AppComponent(this)
    }

    @Suppress("UNCHECKED_CAST")
    override fun <C : Any> getComponent(clazz: KClass<C>): C? {
        return when(clazz) {
            ImageSearchDependencies::class -> component as C
            else -> throw UnsupportedOperationException("Unsupported component")
        }
    }
}
