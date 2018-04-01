package com.levnovikov.core_api.api.entities

import com.levnovikov.core_api.api.ResponseStatus

/**
 * Author: lev.novikov
 * Date: 1/4/18.
 */

class ErrorResponse(val stat: ResponseStatus, val code: Int, message: String) : Throwable(message)