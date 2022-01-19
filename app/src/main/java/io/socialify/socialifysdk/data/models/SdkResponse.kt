package io.socialify.socialifysdk.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import io.socialify.socialifysdk.data.models.responses.Error

@JsonClass(generateAdapter = true)
data class SdkResponse(
     @Json(name = "success")
     val success: Boolean,

     @Json(name = "error")
     val error: Error?
)
