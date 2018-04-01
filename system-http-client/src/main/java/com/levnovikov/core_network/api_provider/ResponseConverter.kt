package com.levnovikov.core_network.api_provider

import kotlin.reflect.KClass

/**
 * Created by lev.novikov
 * Date: 23/2/18.
 */

interface ResponseConverter {
    fun <T : Any> convertFrom(string: String, responseClass: KClass<T>): T
}
