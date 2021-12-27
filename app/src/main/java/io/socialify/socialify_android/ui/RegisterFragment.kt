package io.socialify.socialify_android.ui

import android.app.AlertDialog
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
import io.socialify.socialifysdk.models.SdkResponse

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
        val loginFragmentNavigator =
            RegisterFragmentDirections
                .actionRegisterFragmentToLoginFragment()

        goToLoginButton?.setOnClickListener {
            it.findNavController().navigate(loginFragmentNavigator)
        }

        registerButton?.setOnClickListener {
            val username = view?.findViewById<EditText>(R.id.registerUsernameInput)?.text.toString()
            val password = view?.findViewById<EditText>(R.id.registerPasswordInput)?.text.toString()
            val repeatedPassword = view?.findViewById<EditText>(R.id.registerRepeatPasswordInput)?.text.toString()

            registerButton.text = "Clicked!"

            val resp: SdkResponse = client.registerAccount(username, password, repeatedPassword)
            val dialogText: String?

            if(resp.success) {
                AlertDialog.Builder(requireContext())
                    .setMessage(getString(R.string.register_success))
                    .setPositiveButton(getString(R.string.ok)) { _, _ ->
                        view?.findNavController()?.navigate(loginFragmentNavigator)
                    }
                    .create()
                    .show()
            } else {
                AlertDialog.Builder(requireContext())
                    .setMessage(resp.error.toString())
                    .setPositiveButton(getString(R.string.report)) { _, _ -> }
                    .setNegativeButton(getString(R.string.cancel)) { _, _ -> }
                    .create()
                    .show()
            }
        }
    }
}
