package io.socialify.socialify_android.ui

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import io.socialify.socialify_android.R
import io.socialify.socialify_android.SharedPreference
import io.socialify.socialify_android.client


class LoginFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {
        super.onStart()

        val actionButton = view?.findViewById<Button>(R.id.actionButton)
        val createAccountButton = view?.findViewById<TextView>(R.id.createAccountTwo)

        actionButton?.setOnClickListener {
            val username = view?.findViewById<EditText>(R.id.usernameInput)?.text.toString()
            val password = view?.findViewById<EditText>(R.id.passwordInput)?.text.toString()

            val resp = client.registerDevice(username, password)

            if(resp.success) {
                actionButton.text = "Logged in"

                val sharedPreference: SharedPreference = SharedPreference(requireContext())
                sharedPreference.save("isUserLogged", true)

            } else {
                MaterialAlertDialogBuilder(requireContext())
                    .setMessage(resp.error.toString())
                    .setPositiveButton(getString(R.string.report)) { _, _ -> }
                    .setNegativeButton(getString(R.string.cancel)) { _, _ -> }
                    .create()
                    .show()
            }
        }

        createAccountButton?.setOnClickListener {
            val action =
                LoginFragmentDirections
                    .actionLoginFragmentToRegisterFragment()
            it.findNavController().navigate(action)
        }
    }
}
