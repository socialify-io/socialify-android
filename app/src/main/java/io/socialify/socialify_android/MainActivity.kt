package io.socialify.socialify_android

import android.os.Bundle
import android.provider.ContactsContract
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import io.socialify.socialify_android.ui.Register
import io.socialify.socialify_android.ui.theme.SocialifyandroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreference: SharedPreference = SharedPreference(this)
        val isUserLogged: Boolean = sharedPreference.getValue("isUserLogged")



        if(isUserLogged) {
            setContent {
                SocialifyandroidTheme {
                    Text("Jesteś zalogowany, czy coś, nie wiem xd")
                }
            }
        } else {
            setContent {
                val navController = rememberNavController()

                SocialifyandroidTheme {
                    NavHost(navController = navController, startDestination = "login") {
                        composable("login") { Login(navController) }
                        composable("register") { Register(navController) }
                    }
                }
            }
        }
    }
}
