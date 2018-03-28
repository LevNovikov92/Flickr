package com.levnovikov.core_network.transformers

import com.levnovikov.core_network.request.Request
import com.levnovikov.core_network.request.RequestTransformer

/**
 * Author: lev.novikov
 * Date: 27/3/18.
 */

class AuthTransformer(private val apiKey: String) : RequestTransformer {

    override fun transform(request: Request): Request {
        return Request.Builder(request)
                .addPathParam(API_KEY_NAME, apiKey)
                .build()
    }

    companion object {
        private const val API_KEY_NAME = "api_key"
    }
}
