package com.ing.android.coolusers.utilities

import android.content.Context
import com.ing.android.coolusers.domain.parser.DataParser
import com.ing.android.coolusers.providers.data.LocalDataLoader
import com.ing.android.coolusers.providers.data.NetworkDataLoader
import com.ing.android.coolusers.providers.service.UserServiceImpl
import com.ing.android.coolusers.service.DataLoader
import com.ing.android.coolusers.service.UserService

/**
 * Desc: Static methods used to inject classes
 */

object InjectionUtils {

    fun provideUserService(context: Context): UserService {
        return UserServiceImpl.getInstance(context)
    }

    fun provideLocalDataLoader(context: Context): DataLoader {
        return LocalDataLoader(context)
    }

    @SuppressWarnings("not used")
    fun provideNetworkDataLoader(context: Context): DataLoader {
        return NetworkDataLoader(context)
    }

    fun provideDataParser(): DataParser {
        return DataParser()
    }
}