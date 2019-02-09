package com.ing.android.coolusers.domain.usecases

import com.ing.android.coolusers.domain.listeners.GenericListener

interface Command {
    var state: State
        get() = State.CREATED
        set(value){}
    fun execute(genericListener: GenericListener)
}

enum class State {
    CREATED,
    STARTED,
    PROCESSING,
    ENDED
}