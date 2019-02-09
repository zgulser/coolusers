package com.ing.android.coolusers.app

import android.app.Application

class CoolUsersApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        private lateinit var instance: CoolUsersApplication

        fun getInstance() : CoolUsersApplication = instance
    }
}