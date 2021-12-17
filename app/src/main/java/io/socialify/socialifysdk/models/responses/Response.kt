package io.socialify.socialifysdk.models.responses

import android.support.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiResponse<T>(
    @Json(name = "data")
    val data: T?,

    @Json(name = "error")
    val error: Error?,

    @Json(name = "success")
    val success: Boolean
)

@JsonClass(generateAdapter = true)
data class Error(
    @Json(name = "code")
    val code: Int,

    @Json(name = "reason")
    val reason: String?
)

@JsonClass(generateAdapter = true)
data class PublicKeyResponse(
    @Json(name = "pubKey")
    val pubKey: String
)
