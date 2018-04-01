package com.levnovikov.core_api.api.error

/**
 * Author: lev.novikov
 * Date: 1/4/18.
 */
class RequestException(val code: Int, message: String) : Exception(message)