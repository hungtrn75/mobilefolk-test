package com.mobilefolk.test.core.util

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response

class AppInterceptor(val context: Context) : Interceptor {
    companion object {
        var token = ""
        var deviceId = ""
        val deviceName =
            "${android.os.Build.VERSION.SDK_INT}, ${android.os.Build.ID}, ${android.os.Build.MANUFACTURER}, ${android.os.Build.MODEL}"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        requestBuilder.addHeader("Authorization", "Bearer $token")
        requestBuilder.addHeader("DeviceId", deviceId)
        requestBuilder.addHeader("DeviceName", deviceName)
        return chain.proceed(requestBuilder.build())
    }
}