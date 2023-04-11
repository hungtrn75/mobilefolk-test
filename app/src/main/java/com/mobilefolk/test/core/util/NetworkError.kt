package com.mobilefolk.test.core.util

import org.json.JSONObject
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketException

object NetworkError {
    fun handleException(e: Exception): String {
        return when (e) {
            is ConnectException -> "Không có kết nối mạng"
            is HttpException -> {
                e.response()?.errorBody()?.let {
                    val json = JSONObject(String(it.bytes()))
                    return json.getString("message")
                }
                return e.message()
            }
            is SocketException -> e.message ?: "Vui lòng thử lại sau"
            else -> e.message ?: "Vui lòng thử lại sau"
        }
    }
}