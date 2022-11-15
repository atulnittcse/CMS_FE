package com.cms.android.cleaningmanagementsystem.app

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate


class CMSApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setTheme(R.style.Theme_CleaningManagementSystem)


    }

}