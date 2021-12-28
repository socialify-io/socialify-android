package io.socialify.socialify_android.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import io.socialify.socialify_android.R
import io.socialify.socialify_android.SharedPreference

class StartFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_start, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreference: SharedPreference = SharedPreference(context!!)

        val isUserLogged: Boolean = sharedPreference.getValue("isUserLogged")

        if(isUserLogged) {
            Log.e("DUPA", "LOGGED")

            val action = StartFragmentDirections
                .actionStartFragmentToMainFragment()
            view?.findNavController()?.navigate(action)
        } else {
            Log.e("DUPA", "UNLOGGED")
            val action = StartFragmentDirections
                .actionStartFragmentToAddAccountFragment()
            view?.findNavController()?.navigate(action)
        }
    }
}
