package com.ing.android.coolusers.view.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.ing.android.coolusers.service.UserService
import com.ing.android.coolusers.utilities.InjectionUtils

abstract class BaseViewModel constructor(application: Application) : AndroidViewModel(application) {

    protected val userService : UserService by lazy {
        InjectionUtils.provideUserService(application.applicationContext)
    }
}