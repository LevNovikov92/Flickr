package com.levnovikov.core_network.request

/**
 * Author: lev.novikov
 * Date: 27/3/18.
 */

interface RequestTransformer {
    fun transform(request: Request): Request
}
