package com.ing.android.coolusers.domain.usecases

import com.ing.android.coolusers.domain.listeners.GenericListener

interface Command {
    fun execute(genericListener: GenericListener)
}