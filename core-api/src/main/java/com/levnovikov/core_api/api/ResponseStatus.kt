package com.levnovikov.core_api.api

/**
 * Author: lev.novikov
 * Date: 1/4/18.
 */
enum class ResponseStatus(val stat: String) {
    OK("ok"),
    FAIL("fail");

    companion object {
        fun getFromString(stat: String): ResponseStatus =
                when(stat) {
                    OK.stat -> OK
                    FAIL.stat -> FAIL
                    else -> throw UnsupportedOperationException("Status '$stat' is not supported")
                }
    }
}