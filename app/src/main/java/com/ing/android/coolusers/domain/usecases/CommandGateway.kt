package com.ing.android.coolusers.domain.usecases

import android.content.Context
import android.os.Bundle
import com.ing.android.coolusers.domain.listeners.GenericListener
import com.ing.android.coolusers.domain.parser.DataParser

/**
 * Desc: Class that encapsulates command gateway capabilities
 */

abstract class CommandGateway(protected val context: Context) {

    protected lateinit var dataParser : DataParser

    private val transactions by lazy {
        arrayListOf<Command>()
    }

    private fun startTransaction(command: Command) {
        command.state = State.STARTED
        transactions.add(command)
        println("${command.javaClass.canonicalName} transaction started at: ${System.currentTimeMillis()}")
    }

    open fun performTransaction(arguments: Bundle, command: Command, genericListener: GenericListener) {
        startTransaction(command)
        command.state = State.PROCESSING
        transact(arguments, genericListener)
        endTransaction(command)
    }

    // TODO: default arguments do not work with abstract classes
    protected abstract fun transact(arguments: Bundle = Bundle.EMPTY, genericListener: GenericListener)

    private fun endTransaction(command: Command) {
        command.state = State.ENDED
        println("${command.javaClass.canonicalName} transaction ended at: ${System.currentTimeMillis()}")
    }
}