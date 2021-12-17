package io.socialify.socialify_android

import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.EditText
import io.socialify.socialifysdk.SocialifyClient
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.view.View


class MainActivity : AppCompatActivity() {
    val client = SocialifyClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val SDK_INT = Build.VERSION.SDK_INT
        if (SDK_INT > 8) {
            val policy = ThreadPolicy.Builder()
                .permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }
        supportActionBar?.hide()
        setContentView(R.layout.login)

        val actionButton = findViewById<Button>(R.id.actionButton)

        actionButton.setOnClickListener {
            val username = findViewById<EditText>(R.id.usernameInput).text.toString()
            val password = findViewById<EditText>(R.id.passwordInput).text.toString()

            client.registerDevice(username, password)

            actionButton.text = "Clicked!"
        }
    }
}
