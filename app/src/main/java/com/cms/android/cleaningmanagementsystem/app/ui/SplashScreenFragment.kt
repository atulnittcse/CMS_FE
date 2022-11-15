package com.cms.android.cleaningmanagementsystem.app.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.cms.android.cleaningmanagementsystem.app.R
import com.cms.android.cleaningmanagementsystem.app.others.SharedPref
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenFragment : Fragment(R.layout.fragment_splashscreen) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CoroutineScope(Dispatchers.Main).launch {

            delay(1000)

            if (SharedPref(requireContext()).getUserLoginStatus()){
                Navigation.findNavController(requireActivity(),R.id.nav_host_fragment_content_main).navigate(R.id.action_splashScreenFragment_to_dashboardFragment)
            }else{
                Navigation.findNavController(requireActivity(),R.id.nav_host_fragment_content_main).navigate(R.id.action_splashScreenFragment_to_loginFragment)
            }

        }
    }


}