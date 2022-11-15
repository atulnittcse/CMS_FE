package com.cms.android.cleaningmanagementsystem.app.ui.assign

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.cms.android.cleaningmanagementsystem.app.R
import com.cms.android.cleaningmanagementsystem.app.adapter.UserAdapter
import com.cms.android.cleaningmanagementsystem.app.databinding.FragmentAssignroleBinding
import com.cms.android.cleaningmanagementsystem.app.databinding.FragmentHomeBinding
import com.cms.android.cleaningmanagementsystem.app.firebase.MainViewmodel
import com.cms.android.cleaningmanagementsystem.app.others.Constants

class AssignRoleFragment : Fragment(R.layout.fragment_assignrole) {

    lateinit var viewmodel : MainViewmodel
    lateinit var binding : FragmentAssignroleBinding
    lateinit var userAdapter: UserAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentAssignroleBinding.bind(view)
        setUI()

    }

    private fun setUI() {

        viewmodel =
            ViewModelProvider(this).get(MainViewmodel::class.java)
        userAdapter = UserAdapter()

        binding.rvUsers.adapter = userAdapter
        binding.rvUsers.layoutManager = LinearLayoutManager(requireContext())

        viewmodel.getUsers()

        userAdapter.setOnItemClickListener {
            Constants.curUserData = it
            Navigation.findNavController(requireActivity(),R.id.nav_host_fragment_content_main).navigate(R.id.action_assignRoleFragment_to_setPermissionFragment)
        }

        viewmodel.usersLive.observe(viewLifecycleOwner, Observer {
            binding.progressbar.visibility = View.GONE
            userAdapter.featureList = it
        })

        viewmodel.errorUsersLive.observe(viewLifecycleOwner, Observer {
            binding.progressbar.visibility = View.GONE
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })

    }

}