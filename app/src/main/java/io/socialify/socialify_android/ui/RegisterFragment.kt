package io.socialify.socialify_android.ui

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import io.socialify.socialify_android.R
import io.socialify.socialify_android.client

class RegisterFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {
        super.onStart()

        val registerButton = view?.findViewById<Button>(R.id.registerButton)
        val goToLoginButton = view?.findViewById<TextView>(R.id.goToLogInTwo)

        goToLoginButton?.setOnClickListener {
            val action =
                RegisterFragmentDirections
                    .actionRegisterFragmentToLoginFragment()
            it.findNavController().navigate(action)
        }

        registerButton?.setOnClickListener {
            val username = view?.findViewById<EditText>(R.id.registerUsernameInput)?.text.toString()
            val password = view?.findViewById<EditText>(R.id.registerPasswordInput)?.text.toString()
            val repeatedPassword = view?.findViewById<EditText>(R.id.registerRepeatPasswordInput)?.text.toString()

            client.registerAccount(username, password, repeatedPassword)

            registerButton.text = "Clicked!"
        }
    }
}
