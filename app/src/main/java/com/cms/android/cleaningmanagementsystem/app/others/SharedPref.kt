package com.cms.android.cleaningmanagementsystem.app.others

import android.content.Context
import com.cms.android.cleaningmanagementsystem.app.data.StationData
import com.google.gson.Gson

class SharedPref(context: Context) {

    val sharedpref = context.getSharedPreferences("cms_pref", Context.MODE_PRIVATE)
    val editor = sharedpref.edit()

    val STATIONDATA = "stationdata"
    val STATIONLOGIN = "stationLogin"

    fun setUserData(stationData: StationData){
        val gson =  Gson()
        val json = gson.toJson(stationData)
        editor.putString(STATIONDATA, json)
        editor.putBoolean(STATIONLOGIN,true)
        editor.commit()
    }

    fun setUserLoginStatus(status : Boolean) {
        editor.apply {
            putBoolean(STATIONLOGIN,status)
            apply()
        }
    }

    fun getUserLoginStatus() : Boolean{
        return sharedpref.getBoolean(STATIONLOGIN,false)
    }


    fun getUserData() : StationData{
        val gson = Gson()
        val json = sharedpref.getString(STATIONDATA, "null")
        return gson.fromJson(json, StationData::class.java)
    }


}