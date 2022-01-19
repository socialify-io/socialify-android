package io.socialify.socialifysdk.data.models.responses

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

@JsonClass(generateAdapter = true)
data class NewDeviceResponse(
    @Json(name = "deviceId")
    val deviceId: Int,

    @Json(name = "userId")
    val userId: Int
)

//@JsonClass(generateAdapter = true)
//data class RegisterResponse(
//    @Json(name = "")
//)
