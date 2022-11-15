package com.cms.android.cleaningmanagementsystem.app.firebase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cms.android.cleaningmanagementsystem.app.data.DailyRecordData
import com.cms.android.cleaningmanagementsystem.app.data.PermissionData
import com.cms.android.cleaningmanagementsystem.app.data.StationData
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MainViewmodel : ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()

    private val stationsCollection = firestore.collection("stations")
    private val recordsCollection = firestore.collection("records")


    private var _stationDataLive = MutableLiveData<StationData>()
    var stationDataLive : LiveData<StationData> = _stationDataLive
    private var _errorStationDataLive = MutableLiveData<String>()
    var errorStationDataLive : LiveData<String> = _errorStationDataLive


    fun signInStation(stationName : String, password : String,type : String) =  viewModelScope.launch(Dispatchers.IO) {

        try {

            val users = stationsCollection.whereEqualTo("name",stationName).whereEqualTo("password",password).whereEqualTo("userType",type).get().await().toObjects(StationData::class.java)

            if(users.isNotEmpty()){

                _stationDataLive.postValue(users[0])

            }else{
                _errorStationDataLive.postValue("No User found")
            }

        } catch (e: Exception) {
            _errorStationDataLive.postValue(e.message)
        }

    }

    private var _dailyRecordLive = MutableLiveData<DailyRecordData?>()
    var dailyRecordLive : LiveData<DailyRecordData?> = _dailyRecordLive
    private var _errorDailyRecordLive = MutableLiveData<String>()
    var errorDailyRecordLive : LiveData<String> = _errorDailyRecordLive

    fun getDailyRecordByID(id : String) =  viewModelScope.launch(Dispatchers.IO) {

        try {
            val dailyRecord = recordsCollection.document(id).get().await().toObject(DailyRecordData::class.java)
            _dailyRecordLive.postValue(dailyRecord)
        } catch (e: Exception) {
            _errorDailyRecordLive.postValue(e.message)
        }
    }


    private var _updateDailyRecordLive = MutableLiveData<DailyRecordData>()
    var updateDailyRecordLive : LiveData<DailyRecordData> = _updateDailyRecordLive
    private var _errorUpdateDailyRecordLive = MutableLiveData<String>()
    var errorUpdateDailyRecordLive : LiveData<String> = _errorUpdateDailyRecordLive

    fun createDailyRecord(dailyRecordData: DailyRecordData) =  viewModelScope.launch(Dispatchers.IO) {

        try {
            recordsCollection.document(dailyRecordData.id).set(dailyRecordData).addOnSuccessListener {
                _updateDailyRecordLive.postValue(dailyRecordData)
            }.addOnFailureListener {
                _errorUpdateDailyRecordLive.postValue(it.message)
            }
        } catch (e: Exception) {
            _errorUpdateDailyRecordLive.postValue(e.message)
        }
    }


    private var _usersLive = MutableLiveData<List<StationData>>()
    var usersLive : LiveData<List<StationData>> = _usersLive
    private var _errorUsersLive = MutableLiveData<String>()
    var errorUsersLive : LiveData<String> = _errorUsersLive

    fun getUsers() =  viewModelScope.launch(Dispatchers.IO) {

        try {
            val users = stationsCollection.whereNotEqualTo("userType","Railway Admin").get().await().toObjects(StationData::class.java)
            _usersLive.postValue(users)
        } catch (e: Exception) {
            _errorUsersLive.postValue(e.message)
        }
    }

    private var _updatePermissionLive = MutableLiveData<PermissionData>()
    var updatePermissionLive : LiveData<PermissionData> = _updatePermissionLive
    private var _errorUpdatePermissionLive = MutableLiveData<String>()
    var errorUpdatePermissionLive : LiveData<String> = _errorUpdatePermissionLive

    fun updateUserPermission(permissionData: PermissionData,userId : String) =  viewModelScope.launch(Dispatchers.IO) {

        try {

            stationsCollection.document(userId).update(
                "permissionData",permissionData
            ).addOnSuccessListener {
                _updatePermissionLive.postValue(permissionData)
            }.addOnFailureListener {
                _errorUpdatePermissionLive.postValue(it.message)
            }

        } catch (e: Exception) {
            _errorUpdatePermissionLive.postValue(e.message)
        }
    }




}