package io.socialify.socialifysdk

import io.socialify.socialifysdk.models.requests.*
import io.socialify.socialifysdk.models.responses.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header

interface SocialifyService {
    @GET("getKey")
    fun key(
        @Header("AuthToken") authToken: String,
        @Header("Timestamp") timestamp: String
    ): Call<ApiResponse<PublicKeyResponse>>
}
