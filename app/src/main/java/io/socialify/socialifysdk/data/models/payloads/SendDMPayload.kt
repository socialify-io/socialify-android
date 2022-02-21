package io.socialify.socialifysdk.data.models.payloads

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SendDMPayload(
    @Json(name = "message")
    val message: String,

    @Json(name = "receiverId")
    val receiverId: Int
)
