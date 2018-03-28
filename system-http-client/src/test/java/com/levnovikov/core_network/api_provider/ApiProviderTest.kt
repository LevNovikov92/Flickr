package com.levnovikov.core_network.api_provider

import com.levnovikov.core_network.HttpClient
import com.levnovikov.core_network.request.Request
import com.levnovikov.core_network.response.Response
import com.levnovikov.core_network.response.ResponseBody
import junit.framework.Assert
import org.junit.Test
import java.io.ByteArrayInputStream
import java.nio.charset.StandardCharsets
import kotlin.reflect.KClass

/**
 * Author: lev.novikov
 * Date: 27/3/18.
 */
class ApiProviderTest {

    internal data class UserDataRequest(var id: String? = null)

    internal data class UserDataResponse(var name: String? = null, var age: Int = 0)

    @Test
    @Throws(Exception::class)
    fun makeRequest() {
        val converter = object : EntityConverter {

            override fun <T : Any> convertTo(entity: T): String {
                return when (entity) {
                    is ApiProviderTest.UserDataRequest -> "{'id': ${entity.id}}"
                    is UserDataResponse -> "{'name':'User name', 'age': 20}"
                    else -> throw UnsupportedOperationException("Not supported")
                }
            }

            @Suppress("UNCHECKED_CAST")
            override fun <T : Any> convertFrom(string: String, responseClass: KClass<T>): T {
                return when (responseClass) {
                    UserDataResponse::class -> UserDataResponse("User name", 20) as T
                    else -> throw UnsupportedOperationException("Not supported")
                }
            }
        }

        val client: HttpClient = object : HttpClient {
            override fun makeCall(request: Request): Response {
                val response = UserDataResponse()
                response.age = 20
                response.name = "User name"
                val responseBody = ResponseBody(
                        ByteArrayInputStream(converter.convertTo(response).toByteArray(StandardCharsets.UTF_8)), "utf-8", "")
                return Response.Builder().body(responseBody).build()
            }
        }

        val provider = ApiProvider.Builder()
                .baseUrl("https://google.com")
                .client(client)
                .contentType("text/html")
                .converter(converter)
                .build()

        val request = UserDataRequest()
        request.id = "5"
        val response = provider
                .makeRequest(Request.Method.POST, "/sdfsd/", request, null, UserDataResponse::class)
        Assert.assertEquals(20, response.age)
        Assert.assertEquals("User name", response.name)
    }

}