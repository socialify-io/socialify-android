package io.socialify.socialify_android

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.socialify.socialify_android.ui.Login
import io.socialify.socialify_android.ui.Navigation
import io.socialify.socialify_android.ui.Register
import io.socialify.socialify_android.ui.theme.SocialifyandroidTheme
import io.socialify.socialifysdk.SocialifyClient

class MainActivity : ComponentActivity() {
    companion object {
        val client = SocialifyClient()
    }

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

        setContent {
            val navController = rememberNavController()

            SocialifyandroidTheme {
                if (isUserLogged) {
                    Navigation()
                } else {
                    NavHost(navController = navController, startDestination = "login") {
                        composable("login") { Login(navController) }
                        composable("register") { Register(navController) }
                        composable("navigation") { Navigation() }
                    }
                }
            }
        }
    }
}
