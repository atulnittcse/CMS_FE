package com.cms.android.cleaningmanagementsystem.app.data

data class StationData(
    val id : String = "",
    val name : String = "",
    val password : String = "",
    val userType : String = "",
    val permissionData : PermissionData = PermissionData()
)
data class PermissionData(
    var writeTodayRate : String = YES,
    var writeSpecificDateRate : String = YES
)

const val YES = "Yes"
const val NO  = "No"

const val PENDING = "Pending"
const val COMPLETED = "Completed"