package com.ing.android.coolusers.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.ing.android.coolusers.interactor.UserInteractor
import com.ing.android.coolusers.utilities.InjectionUtils

abstract class BaseViewModel constructor(application: Application) : AndroidViewModel(application) {
    protected val userInteractor : UserInteractor by lazy {
        InjectionUtils.provideUserInteractor(application.applicationContext)
    }
}