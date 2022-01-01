package io.socialify.socialifysdk

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import encryptMessage
import generateKeyPair
import getFingerprint
import io.socialify.socialifysdk.crypto.BCrypt
import io.socialify.socialifysdk.models.SdkResponse
import io.socialify.socialifysdk.models.payloads.DeviceInfo
import io.socialify.socialifysdk.models.payloads.NewDevicePayload
import io.socialify.socialifysdk.models.payloads.RegisterPayload
import io.socialify.socialifysdk.models.responses.ApiResponse
import io.socialify.socialifysdk.models.responses.PublicKeyResponse
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import java.util.*

class SocialifyClient {
    val apiVersion = "0.1"
    val route: String = "http://192.168.8.151/api/v${apiVersion}/"

    val appVersion = "0.1"
    val userAgent = "Socialify-android"
    val os = "android_11"

    val logger: HttpLoggingInterceptor = HttpLoggingInterceptor()

    var httpClient = OkHttpClient.Builder()

    init{
        logger.setLevel(HttpLoggingInterceptor.Level.BODY)
        httpClient.addInterceptor { chain ->
            val original = chain.request()

            var requestBuilder = original.newBuilder()
                .header("Content-Type", "application/json")
                .header("User-Agent", userAgent)
                .header("OS", os)
                .header("AppVersion", appVersion)
                .header("Accept", "application/json")

           val request = requestBuilder.build()
           chain.proceed(request)
        }
    }

    val okhttpClient = httpClient
        .addInterceptor(logger)
        .build()

    val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(route)
        .addConverterFactory(MoshiConverterFactory.create())
        .client(okhttpClient)
        .build()

    val api: SocialifyService = retrofit.create()

    @RequiresApi(Build.VERSION_CODES.O)
    fun registerDevice(username: String, password: String): SdkResponse {
        val builder = FormBody.Builder()
        builder.add("username", username)
            .add("password", password)

        val key: String? = getKey()
        val encPass = encryptMessage(password, key!!)
        val keypair = generateKeyPair()

        val signPubKey = Base64.getEncoder().encodeToString(keypair.public.encoded)
        val signPrivKey = Base64.getEncoder().encodeToString(keypair.private.encoded)

        val fingerprint = getFingerprint(signPrivKey)

        val timestamp: String = (System.currentTimeMillis()/1000).toString()

        Log.e("TIMESTAMP", timestamp)

        val payload = NewDevicePayload(
            username = username,
            password =  encPass,
            pubKey = key!!,
            device = DeviceInfo(
                deviceName = "android",
                signPubKey = signPubKey,
                fingerprint = fingerprint
            )
        )

        val response = api.newDevice(generateAuthToken("newDevice", timestamp), timestamp, payload)
                .execute().body()

        return SdkResponse(
            response?.success ?: false,
            response?.error
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun registerAccount(
        username: String,
        password: String,
        repeatedPassword: String): SdkResponse {
        val key: String? = getKey()
        val encryptedPassword = encryptMessage(password, key!!)

        val timestamp: String = (System.currentTimeMillis()/1000).toString()

        val payload = RegisterPayload(
            username = username,
            password = encryptedPassword,
            repeatPassword = encryptedPassword,
            pubKey = key!!
       )

        val response = api.register(generateAuthToken("register", timestamp), timestamp, payload)
            .execute().body()

        return SdkResponse(
            response?.success ?: false,
            response?.error
        )
    }

    private fun getKey(): String? {
        val timestamp: String = (System.currentTimeMillis()/1000).toString()

        val response = api.key(generateAuthToken("getKey", timestamp), timestamp)
                .execute().body()

        return response?.data?.pubKey
    }

    private fun generateAuthToken(endpoint: String, timestamp: String): String {
        val authTokenBeginHeader = "$" + "begin-${endpoint}$"
        val authTokenEndHeader = "$" + "end-${endpoint}$"

        val authTokenRaw =
            "${authTokenBeginHeader}.${appVersion}+${os}+${userAgent}#${timestamp}#.${authTokenEndHeader}"

        Log.e("authTokenRaw", authTokenRaw)

        return BCrypt.hashpw(authTokenRaw, BCrypt.gensalt())
    }

}
