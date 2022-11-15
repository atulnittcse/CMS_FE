package com.cms.android.cleaningmanagementsystem.app.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.cms.android.cleaningmanagementsystem.app.MainActivity
import com.cms.android.cleaningmanagementsystem.app.R
import com.cms.android.cleaningmanagementsystem.app.data.VIEW
import com.cms.android.cleaningmanagementsystem.app.data.YES
import com.cms.android.cleaningmanagementsystem.app.databinding.FragmentDashboardBinding
import com.cms.android.cleaningmanagementsystem.app.others.SharedPref
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class DashboardFragment : Fragment(R.layout.fragment_dashboard) {


    lateinit var binding : FragmentDashboardBinding
    lateinit var sharedPref : SharedPref
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentDashboardBinding.bind(view)
        setUI()

    }

    private fun setUI() {

        sharedPref = SharedPref(requireContext())

        setData()

        binding.tvRatingToday.setOnClickListener {
            Navigation.findNavController(requireActivity(),R.id.nav_host_fragment_content_main).navigate(R.id.action_dashboardFragment_to_viewRatingTodayFragment)
        }
        binding.tvRatingSpecificdate.setOnClickListener {
            Navigation.findNavController(requireActivity(),R.id.nav_host_fragment_content_main).navigate(R.id.action_dashboardFragment_to_viewRatingSpecificDateFragment)
        }
        binding.tvWriteratingOnToday.setOnClickListener {
            Navigation.findNavController(requireActivity(),R.id.nav_host_fragment_content_main).navigate(R.id.action_dashboardFragment_to_writeTodayRatingFragment)
        }
        binding.tvWriteratingOnSpecificdate.setOnClickListener {
            Navigation.findNavController(requireActivity(),R.id.nav_host_fragment_content_main).navigate(R.id.action_dashboardFragment_to_homeFragment)
        }
        binding.tvAssigneditablespecialrequest.setOnClickListener {
            Navigation.findNavController(requireActivity(),R.id.nav_host_fragment_content_main).navigate(R.id.action_dashboardFragment_to_assignRoleFragment)
        }

        binding.tvLogout.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Logout!")
                .setMessage("Are you sure you want to logout ?")
                .setPositiveButton("Yes"){  _,_ ->
                    sharedPref.setUserLoginStatus(false)
                    startActivity(Intent(requireContext(), MainActivity::class.java))
                }
                .setNegativeButton("No",null)
                .show()
        }

    }

    private fun setData() {

      val userData =  sharedPref.getUserData()
      if (userData.userType == "Railway Admin"){
          binding.tvAssigneditablespecialrequest.visibility = View.VISIBLE
          binding.tvWriteratingOnToday.visibility = View.VISIBLE
          binding.tvWriteratingOnSpecificdate.visibility = View.VISIBLE
      }else{
          if (userData.permissionData.writeTodayRate == YES){
              binding.tvWriteratingOnToday.visibility = View.VISIBLE
          }
          if (userData.permissionData.writeSpecificDateRate == YES){
              binding.tvWriteratingOnSpecificdate.visibility = View.VISIBLE
          }
      }

    }

}