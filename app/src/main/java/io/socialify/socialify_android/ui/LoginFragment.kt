package io.socialify.socialify_android.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import io.socialify.socialify_android.R
import io.socialify.socialify_android.client


class LoginFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onStart() {
        super.onStart()

        val actionButton = view?.findViewById<Button>(R.id.actionButton)
        val createAccountButton = view?.findViewById<TextView>(R.id.createAccountTwo)

        actionButton?.setOnClickListener {
            val username = view?.findViewById<EditText>(R.id.usernameInput)?.text.toString()
            val password = view?.findViewById<EditText>(R.id.passwordInput)?.text.toString()

            client.registerDevice(username, password)

            actionButton.text = "Clicked!"
        }

        createAccountButton?.setOnClickListener {
            val action =
                LoginFragmentDirections
                    .actionLoginFragmentToRegisterFragment()
            it.findNavController().navigate(action)
        }
    }
}
