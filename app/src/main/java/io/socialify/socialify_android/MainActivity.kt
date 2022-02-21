package io.socialify.socialify_android

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.provider.ContactsContract
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.socialify.socialify_android.ui.Content
import io.socialify.socialify_android.ui.Login
import io.socialify.socialify_android.ui.Register
import io.socialify.socialify_android.ui.theme.SocialifyandroidTheme
import io.socialify.socialifysdk.SocialifyClient
import io.socialify.socialifysdk.websocket.WebsocketClient

class MainActivity : ComponentActivity() {

    init {
        instance = this
    }

    companion object {
        private var instance: MainActivity? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }

        var client: SocialifyClient? = null
        var socketClient: WebsocketClient? = null

        var receiverID: Int? = null
        var receiverName: String? = null
    }


    @ExperimentalMaterial3Api
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val SDK_INT = Build.VERSION.SDK_INT
        if (SDK_INT > 8) {
            val policy = StrictMode.ThreadPolicy.Builder()
                .permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }

        val sharedPreference: SharedPreference = SharedPreference(this)
        val isUserLogged: Boolean = sharedPreference.getValue("isUserLogged")

        client = SocialifyClient()
        socketClient = WebsocketClient()

        setContent {
            val navController = rememberNavController()

            SocialifyandroidTheme {
                if (isUserLogged) {
                    Content()
                } else {
                    NavHost(navController = navController, startDestination = "login") {
                        composable("login") { Login(navController) }
                        composable("register") { Register(navController) }
                        composable("content") { Content() }
                    }
                }
            }
        }
    }
}
