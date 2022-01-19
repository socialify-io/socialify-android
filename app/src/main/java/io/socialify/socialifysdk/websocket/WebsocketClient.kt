package io.socialify.socialifysdk.websocket

import android.app.Activity
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import io.socialify.socialifysdk.SocialifyClient
import io.socialify.socialifysdk.data.db.entities.User
import io.socialify.socialifysdk.data.models.payloads.SendDMPayload
import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.security.KeyStore
import java.security.MessageDigest
import java.security.Signature
import java.util.*
import java.util.Collections.singletonList

@RequiresApi(Build.VERSION_CODES.O)
class WebsocketClient: SocialifyClient() {
    var socket: Socket? = null

    fun establishConnection() {
        viewModelScope.launch(Dispatchers.IO) {
            val opts = IO.Options()
            opts.extraHeaders = getWebsocketHeaders()

            socket = IO.socket("http://192.168.8.199:82", opts)
            socket?.connect()
        }
    }

    fun closeConnection() {
        socket?.disconnect()
    }

    fun sendDM(message: String, receiverId: Int) {
        val payload = SendDMPayload(
            message = message,
            receiverId = receiverId
        )

        socket?.emit("send_dm", payload)
    }

    fun getDM(activity: Activity) {
        socket?.on("send_dm") { args ->
            if (args[0] != null) {
                activity.runOnUiThread {
                    Log.e("ARGS", args.toString())
                }
            }
        }
    }

    private suspend fun getWebsocketHeaders(): Map<String, List<String>> {
        val timestamp: String = (System.currentTimeMillis() / 1000).toString()
        val authToken = generateAuthToken("connect", timestamp)

        val headers: MutableMap<String, List<String>> = HashMap<String, List<String>>()

        val userDao = db.userDao

        val users: List<User> = userDao.getAll()
        val actualUser: User = users[0]

        val ks: KeyStore = KeyStore.getInstance("AndroidKeyStore").apply {
            load(null)
        }

        val entry: KeyStore.Entry = ks.getEntry("DeviceSignKeypair", null)

        var signature: ByteArray? = null

        val any = if (entry !is KeyStore.PrivateKeyEntry) {
            Log.w("POPSUŁO SIE D:", "Not an instance of a PrivateKeyEntry")
        } else {
            val headersString =
                "Content-Type=application/json&User-Agent=$userAgent&OS=$os&Timestamp=$timestamp&AppVersion=$appVersion&AuthToken=$authToken&UserId=${actualUser.userId}&DeviceId=${actualUser.deviceId}&"
            val signData =
                "headers=$headersString&body={}&timestamp=$timestamp&authToken=$authToken&endpointUrl=/api/v0.1/connect&"

//            val digest = MessageDigest.getInstance("SHA-1")
//            val result = digest.digest(signData.toByteArray(Charsets.UTF_8))
//
//            val sb = StringBuilder()
//            for (b in result) {
//                sb.append(String.format("%02X", b))
//            }
//            val hashedString = sb.toString().toLowerCase()
//            Log.e("DIGEST: ", hashedString)

            Signature.getInstance("SHA1withRSA").run {
                initSign(entry.privateKey)
                update(signData.toByteArray())
                sign()
            }.also { signature = it }
        }

        val b64sign: String = Base64.getEncoder().encodeToString(signature)

        Log.e("DeviceID", actualUser.deviceId.toString())

        headers["Content-Type"] = singletonList("application/json")
        headers["User-Agent"] = singletonList(userAgent)
        headers["OS"] = singletonList(os)
        headers["AppVersion"] = singletonList(appVersion)
        headers["Accept"] = singletonList("application/json")
        headers["Timestamp"] = singletonList(timestamp)
        headers["AuthToken"] = singletonList(authToken)
        headers["UserId"] = singletonList(actualUser.userId.toString())
        headers["DeviceId"] = singletonList(actualUser.deviceId.toString())
        headers["Signature"] = singletonList(b64sign)

        Log.e("SIGN", b64sign)

        return headers
    }
}

