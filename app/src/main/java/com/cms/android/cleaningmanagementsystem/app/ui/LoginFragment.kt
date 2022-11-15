package com.cms.android.cleaningmanagementsystem.app.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.cms.android.cleaningmanagementsystem.app.R
import com.cms.android.cleaningmanagementsystem.app.databinding.FragmentLoginBinding
import com.cms.android.cleaningmanagementsystem.app.firebase.MainViewmodel
import com.cms.android.cleaningmanagementsystem.app.others.MyDialog
import com.cms.android.cleaningmanagementsystem.app.others.SharedPref

class LoginFragment : Fragment(R.layout.fragment_login) {

    lateinit var binding : FragmentLoginBinding
    lateinit var myDialog: MyDialog
    lateinit var viewmodel : MainViewmodel
    lateinit var sharedPref : SharedPref
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentLoginBinding.bind(view)
        setUI()

    }

    private fun setUI() {

        viewmodel =
            ViewModelProvider(this).get(MainViewmodel::class.java)
        myDialog = MyDialog(requireContext())
        sharedPref = SharedPref(requireContext())

        binding.fabLogin.setOnClickListener {
            val stationName = binding.edEmail.text.toString()
            val password = binding.edPassword.text.toString()
            if (stationName.isNotEmpty() && password.isNotEmpty()){
                viewmodel.signInStation(stationName,password,binding.spinnerType.selectedItem.toString())
                myDialog.showProgressDialog("Please wait",this)
            }else{
                Toast.makeText(requireContext(), "Please enter all details", Toast.LENGTH_SHORT).show()
            }
        }

        viewmodel.stationDataLive.observe(viewLifecycleOwner, Observer {
            myDialog.dismissProgressDialog()
            sharedPref.setUserData(it)
            Toast.makeText(requireContext(), "Login Successful", Toast.LENGTH_SHORT).show()
            Navigation.findNavController(requireActivity(),R.id.nav_host_fragment_content_main).navigate(R.id.action_loginFragment_to_dashboardFragment)

        })

        viewmodel.errorStationDataLive.observe(viewLifecycleOwner, Observer {
            myDialog.dismissProgressDialog()
            myDialog.showErrorAlertDialog(it)
        })




    }

}