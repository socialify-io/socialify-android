package io.socialify.socialifysdk.data.models.payloads

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RegisterPayload(
    @Json(name = "username")
    val username: String,

    @Json(name = "password")
    val password: String,

    @Json(name = "repeat_password")
    val repeatPassword: String,

    @Json(name = "pubKey")
    val pubKey: String
)
