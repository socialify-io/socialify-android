package io.socialify.socialify_android

import android.os.Build
import android.os.Bundle
import io.socialify.socialifysdk.SocialifyClient
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import io.socialify.socialify_android.ui.LoginFragmentDirections
import io.socialify.socialify_android.ui.RegisterFragmentDirections


val client = SocialifyClient()

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val SDK_INT = Build.VERSION.SDK_INT
        if (SDK_INT > 8) {
            val policy = ThreadPolicy.Builder()
                .permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }
        supportActionBar?.hide()
        setContentView(R.layout.activity_add_account)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.login_nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
    }
}
