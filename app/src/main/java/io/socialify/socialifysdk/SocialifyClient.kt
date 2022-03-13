package io.socialify.socialifysdk

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import encryptMessage
import generateKeyPair
import io.socialify.socialify_android.MainActivity.Companion.applicationContext
import io.socialify.socialifysdk.crypto.BCrypt
import io.socialify.socialifysdk.data.db.AppDatabase
import io.socialify.socialifysdk.data.db.entities.Account
import io.socialify.socialifysdk.data.models.SdkResponse
import io.socialify.socialifysdk.data.models.payloads.DeviceInfo
import io.socialify.socialifysdk.data.models.payloads.NewDevicePayload
import io.socialify.socialifysdk.data.models.payloads.RegisterPayload
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import java.util.*

open class SocialifyClient: ViewModel() {
    val apiVersion = "0.1"
    val route: String = "http://api.socialify.cf/api/v${apiVersion}/"

    val appVersion = "0.1"
    val userAgent = "Socialify-android"
    val os = "android_11"

    val logger: HttpLoggingInterceptor = HttpLoggingInterceptor()

    var httpClient = OkHttpClient.Builder()

    val db = Room.databaseBuilder(
        applicationContext(),
        AppDatabase::class.java, "socialify-db"
    ).build()

    init {
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

        val timestamp: String = (System.currentTimeMillis() / 1000).toString()

        Log.e("TIMESTAMP", timestamp)

        val payload = NewDevicePayload(
            username = username,
            password = encPass,
            pubKey = key!!,
            device = DeviceInfo(
                deviceName = "android",
                signPubKey = signPubKey
            )
        )

        val response = api.newDevice(generateAuthToken("newDevice", timestamp), timestamp, payload)
            .execute().body()

        Log.e("response", response!!.toString())

        if (!response.success) {
            return SdkResponse(
                response?.success ?: false,
                response?.error
            )
        }

        val newAccount: Account = Account(
            username = username,
            deviceId = response!!.data!!.deviceId,
            userId   = response!!.data!!.userId.toLong()
        )

        viewModelScope.launch(Dispatchers.IO) {
            db.accountDao.insertAll(account = newAccount)
        }

        return SdkResponse(
            response?.success ?: false,
            response?.error
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun registerAccount(
        username: String,
        password: String,
        repeatedPassword: String
    ): SdkResponse {
        val key: String? = getKey()
        val encryptedPassword = encryptMessage(password, key!!)

        val timestamp: String = (System.currentTimeMillis() / 1000).toString()

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
        val timestamp: String = (System.currentTimeMillis() / 1000).toString()

        val response = api.key(generateAuthToken("getKey", timestamp), timestamp)
            .execute().body()

        return response?.data?.pubKey
    }

    fun generateAuthToken(endpoint: String, timestamp: String): String {
        val authTokenBeginHeader = "$" + "begin-${endpoint}$"
        val authTokenEndHeader = "$" + "end-${endpoint}$"

        val authTokenRaw =
            "${authTokenBeginHeader}.${appVersion}+${os}+${userAgent}#${timestamp}#.${authTokenEndHeader}"

        Log.e("authTokenRaw", authTokenRaw)

        return BCrypt.hashpw(authTokenRaw, BCrypt.gensalt())
    }
}
