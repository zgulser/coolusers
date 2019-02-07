package com.ing.android.coolusers.domain.usecases

import android.content.Context
import android.os.Bundle
import com.ing.android.coolusers.domain.listeners.GenericListener
import com.ing.android.coolusers.utilities.InjectionUtils

abstract class CommandGateway (protected val context: Context) {

    protected val dataParser by lazy {
        InjectionUtils.provideDataParser()
    }

    private fun startTransaction(command: Command) =
            println("${command.javaClass.canonicalName} transaction started @${System.currentTimeMillis()}")

    // template method
    open fun performTransaction(arguments: Bundle,
                                command: Command,
                                genericListener: GenericListener) {
        startTransaction(command)
        transact(arguments, genericListener)
        endTransaction(command)
    }

    // TODO: default arguments do not work with abstract classes. find sth else
    abstract fun transact(arguments: Bundle = Bundle.EMPTY, genericListener: GenericListener)

    private fun endTransaction(command: Command) =
            println("${command.javaClass.canonicalName} transaction ended @${System.currentTimeMillis()}")
}