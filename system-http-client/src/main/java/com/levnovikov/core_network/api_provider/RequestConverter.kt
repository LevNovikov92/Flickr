package com.levnovikov.core_network.api_provider

/**
 * Created by lev.novikov
 * Date: 23/2/18.
 */

interface RequestConverter {
    fun <T : Any> convertTo(entity: T): String
}
