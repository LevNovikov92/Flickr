package com.levnovikov.core_network.response

/**
 * Created by lev.novikov
 * Date: 27/3/18.
 */

interface ResponseTransformer {
    fun transform(response: Response): Response
}
