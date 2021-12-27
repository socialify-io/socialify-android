package io.socialify.socialifysdk

import io.socialify.socialifysdk.models.payloads.*
import io.socialify.socialifysdk.models.responses.*
import org.json.JSONArray
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import java.util.*

interface SocialifyService {
    @GET("getKey")
    fun key(
        @Header("AuthToken") authToken: String,
        @Header("Timestamp") timestamp: String
    ): Call<ApiResponse<PublicKeyResponse>>

    @POST("newDevice")
    fun newDevice(
        @Header("AuthToken") authToken: String,
        @Header("Timestamp") timestamp: String,
        @Body payload: NewDevicePayload
    ): Call<ApiResponse<NewDeviceResponse>>

    @POST("register")
    fun register(
        @Header("AuthToken") authToken: String,
        @Header("Timestamp") timestamp: String,

        @Body payload: RegisterPayload
    ): Call<ApiResponse<JSONArray>>
}
