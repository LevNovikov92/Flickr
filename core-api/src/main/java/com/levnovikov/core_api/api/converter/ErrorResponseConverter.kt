package com.levnovikov.core_api.api.converter

import com.levnovikov.core_api.api.ResponseStatus
import com.levnovikov.core_api.api.entities.ErrorResponse
import com.levnovikov.core_network.api_provider.ErrorConverter
import org.json.JSONObject

/**
 * Author: lev.novikov
 * Date: 1/4/18.
 */
class ErrorResponseConverter : ErrorConverter {

    companion object {
        private const val STAT = "stat"
        private const val CODE = "code"
        private const val MESSAGE = "message"
    }

    override fun convertFrom(string: String): ErrorResponse =
            JSONObject(string).run {
                ErrorResponse(
                        ResponseStatus.getFromString(getString(ErrorResponseConverter.STAT)),
                        getInt(ErrorResponseConverter.CODE),
                        getString(ErrorResponseConverter.MESSAGE)
                )
            }
}