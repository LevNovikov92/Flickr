package com.levnovikov.core_network.api_provider

import kotlin.reflect.KClass

/**
 * Created by lev.novikov
 * Date: 23/2/18.
 */

interface EntityConverter {
    fun <T : Any> convertTo(entity: T): String
    fun <T : Any> convertFrom(string: String, responseClass: KClass<T>): T
}
