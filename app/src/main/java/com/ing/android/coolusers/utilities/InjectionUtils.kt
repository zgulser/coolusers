package com.ing.android.coolusers.utilities

import android.content.Context
import com.ing.android.coolusers.domain.parser.DataParser
import com.ing.android.coolusers.interactor.UserInteractor
import com.ing.android.coolusers.providers.data.LocalDataSource
import com.ing.android.coolusers.providers.data.NetworkDataSource
import com.ing.android.coolusers.providers.service.UserServiceImpl
import com.ing.android.coolusers.service.DataSource
import com.ing.android.coolusers.service.UserService

/**
 * Desc: Static methods used to inject classes
 */

object InjectionUtils {

    fun provideUserInteractor(context: Context): UserInteractor {
        return UserInteractor(context)
    }

    fun provideUserService(context: Context): UserService {
        return UserServiceImpl.getInstance(context)
    }

    fun provideLocalDataLoader(context: Context): DataSource {
        return LocalDataSource(context)
    }

    @SuppressWarnings("not used")
    fun provideNetworkDataLoader(context: Context): DataSource {
        return NetworkDataSource(context)
    }

    fun provideDataParser(): DataParser {
        return DataParser()
    }
}