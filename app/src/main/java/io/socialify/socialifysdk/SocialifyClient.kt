package io.socialify.socialifysdk

import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.socialify.socialifysdk.crypto.BCrypt
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

class SocialifyClient {
    val apiVersion = "0.1"
    val route: String = "http://192.168.8.112:5000/api/v${apiVersion}/"

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

    fun registerDevice(username: String, password: String) {
        val builder = FormBody.Builder()
        builder.add("username", username)
            .add("password", password)

        val timestamp: String = System.currentTimeMillis().toString()

        val pubKey = api.key(generateAuthToken("getKey", timestamp), timestamp)
            .execute()
    }

//    private fun getKey(): String? {
//        val url = "${route}getKey"
//
//        val headers = generateHeaders("getKey")
//
//        var request = Request.Builder()
//            .url(url)
//            .headers(headers)
//            .build()
//
//        val response = getKey()
//        Log.e("RESPONSE", response.toString())

//        val pubKey: String? = ""
//        val response = okhttpClient.newCall(request).execute().body?.string()
//
//        val call = okhttpClient.newCall(request)
//        call.enqueue(object: Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                println(e)
//            }
//
//            override fun onResponse(call: Call?, response: Response) {
//                val responseData: String? = response.body()?.string()
//                try {
//                    val publicKeyType = Types.newParameterizedType(ApiResponse::class.java, PublicKeyResponse::class.java)
//                    val jsonAdapter: JsonAdapter<ApiResponse<PublicKeyResponse>> = moshi.adapter<ApiResponse<PublicKeyResponse>>(publicKeyType)
//                    val jsonResponse: ApiResponse<PublicKeyResponse>? = jsonAdapter.fromJson(responseData)
//                    pubKey = jsonResponse?.data?.pubKey
//                } catch (e: JSONException) {
//                    e.printStackTrace()
//                }
//            }
//        })
//
//        return pubKey
//    }

    private fun generateAuthToken(endpoint: String, timestamp: String): String {
        val authTokenBeginHeader = "$" + "begin-${endpoint}$"
        val authTokenEndHeader = "$" + "end-${endpoint}$"

        val authTokenRaw =
            "${authTokenBeginHeader}.${appVersion}+${os}+${userAgent}#${timestamp}#.${authTokenEndHeader}"

        Log.e("authTokenRaw", authTokenRaw)

        return BCrypt.hashpw(authTokenRaw, BCrypt.gensalt())
    }

//    fun parseResponse(response: Response): String? {
//        val body = response.body?.string()
//        return body
//    }
}
