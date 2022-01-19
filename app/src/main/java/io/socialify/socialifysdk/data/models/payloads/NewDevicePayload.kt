package io.socialify.socialifysdk.data.models.payloads

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NewDevicePayload(
    @Json(name = "username")
    val username: String,

    @Json(name = "password")
    val password: String,

    @Json(name = "pubKey")
    val pubKey: String,

    @Json(name = "device")
    val device: DeviceInfo
)

@JsonClass(generateAdapter = true)
data class DeviceInfo(
    @Json(name = "deviceName")
    val deviceName: String,

    @Json(name = "signPubKey")
    val signPubKey: String
)
