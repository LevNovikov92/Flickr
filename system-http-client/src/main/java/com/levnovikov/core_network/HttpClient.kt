package com.levnovikov.core_network

import com.levnovikov.core_network.request.Request
import com.levnovikov.core_network.response.Response

/**
 * Author: lev.novikov
 * Date: 27/3/18.
 */

interface HttpClient {
    fun makeCall(request: Request): Response
}
