package com.ing.android.coolusers.exceptions

import java.lang.ArithmeticException

fun testNestedTryCatch() {
    try {
        throwExc {
            println("FF-in outer try")
            val f = 10/0
        }
    } catch(e: Exception){
        println("FF-exception caught")
    } finally {
        println("FF-second finally")
    }
    println("FF-flow goes")
}

inline fun throwExc(f: () -> Unit){
    try {
        println("FF-in inner try")
        f.invoke()
    } finally {
        println("FF-finally")
    }
}

@Throws(Exception::class)
fun testCatching() {
    try {
        val i = 10/0
    } catch (e: ArithmeticException){
        println(e.localizedMessage)
    }
}