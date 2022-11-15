package com.cms.android.cleaningmanagementsystem.app.ui.assign

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cms.android.cleaningmanagementsystem.app.R
import com.cms.android.cleaningmanagementsystem.app.data.NO
import com.cms.android.cleaningmanagementsystem.app.data.PermissionData
import com.cms.android.cleaningmanagementsystem.app.data.YES
import com.cms.android.cleaningmanagementsystem.app.databinding.FragmentSetpermissionBinding
import com.cms.android.cleaningmanagementsystem.app.firebase.MainViewmodel
import com.cms.android.cleaningmanagementsystem.app.others.Constants
import com.cms.android.cleaningmanagementsystem.app.others.MyDialog

class SetPermissionFragment : Fragment(R.layout.fragment_setpermission) {

    lateinit var binding : FragmentSetpermissionBinding
    lateinit var mainViewmodel: MainViewmodel
    lateinit var myDialog: MyDialog
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSetpermissionBinding.bind(view)
        setUI()

    }

    private fun setUI() {

        mainViewmodel =
            ViewModelProvider(this).get(MainViewmodel::class.java)
        myDialog = MyDialog(requireContext())

        val data  = Constants.curUserData
        binding.switchToday.isChecked = data.permissionData.writeTodayRate == YES
        binding.switchSpecificdate.isChecked = data.permissionData.writeSpecificDateRate == YES


        binding.btUpdate.setOnClickListener {
            val writeToday = if(binding.switchToday.isChecked) YES else NO
            val writeSpecificDate = if(binding.switchSpecificdate.isChecked) YES else NO
            myDialog.showProgressDialog("Please wait",this)
            mainViewmodel.updateUserPermission(PermissionData(writeToday,writeSpecificDate),data.id)
        }

        mainViewmodel.updatePermissionLive.observe(viewLifecycleOwner, Observer {
            myDialog.dismissProgressDialog()
            Toast.makeText(requireContext(), "Updated", Toast.LENGTH_SHORT).show()
        })

        mainViewmodel.errorUpdatePermissionLive.observe(viewLifecycleOwner, Observer {
            myDialog.dismissProgressDialog()
            myDialog.showErrorAlertDialog(it)
        })

    }


}