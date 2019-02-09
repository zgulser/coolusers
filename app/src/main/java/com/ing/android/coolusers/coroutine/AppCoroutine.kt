package com.ing.android.coolusers.coroutine

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.plus

const val TAG = "Cool Users!"
const val MSG = "Coroutine crashed"

val LoggingExceptionHandler = CoroutineExceptionHandler { ctx, throwable ->
  Log.w(TAG, MSG, throwable)
}

val AppScope = GlobalScope + LoggingExceptionHandler