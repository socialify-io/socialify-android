package io.socialify.socialify_android

import android.os.Build
import android.os.Bundle
import io.socialify.socialifysdk.SocialifyClient
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.color.DynamicColors
import io.socialify.socialify_android.ui.LoginFragment
import io.socialify.socialify_android.ui.LoginFragmentDirections
import io.socialify.socialify_android.ui.StartFragmentDirections


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
        DynamicColors.applyIfAvailable(this);
        setContentView(R.layout.activity_main_activity)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.start_nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

    }
}
