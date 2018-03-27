package com.levnovikov.core_network.converter

import com.levnovikov.core_network.api_provider.EntityConverter
import kotlin.reflect.KClass

/**
 * Author: lev.novikov
 * Date: 27/3/18.
 */

class GsonConverter : EntityConverter {


    override fun <T> convertTo(entity: T): String {
        TODO()
    }

    override fun <T : Any> convertFrom(string: String, responseClass: KClass<T>): T {
        TODO()
    }
}
