package com.ing.android.coolusers.coroutine

import android.app.PendingIntent
import kotlinx.coroutines.*
import kotlinx.coroutines.NonCancellable.isCancelled
import java.io.IOException
import java.lang.ArithmeticException
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

fun testUnconfined() {

    println("running on 0 ${Thread.currentThread().name}")

    runBlocking {

        println("running on 1 ${Thread.currentThread().name}")

        launch(Dispatchers.Unconfined) {
            println("running on 2 ${Thread.currentThread().name}")
            delay(400)
            println("running on 3 ${Thread.currentThread().name}")
            withContext(Dispatchers.IO) {
                delay(200)
            }
            println("running on 4 ${Thread.currentThread().name}")
            withContext(Dispatchers.Default) {
                delay(2000)
            }
            println("running on 5 ${Thread.currentThread().name}")
        }

        println("running on 6 ${Thread.currentThread().name}")
    }
}

fun testParentChildNoException() {

    val parent = runBlocking {

        val job1 = launch {
            delay(1000)
            println("GG-job 1")
        }

        val job2 = launch {
            delay(500)
            println("GG-job 2")
        }

        val job3 = launch {
            delay(100)
            println("GG-job 3")
        }

        //job3.cancel()
        coroutineContext.cancelChildren()

        println("GG-job1 cancelled: ${job1.isCancelled}")
        println("GG-job2 cancelled: ${job2.isCancelled}")
        println("GG-job3 cancelled: ${job3.isCancelled}")

        println("GG-job cancelled: ${coroutineContext[Job]}")
        println("GG-job cancelled: ${coroutineContext[Job]?.isCancelled}")
    }
}

// launch exception handling - globalscope
fun testParentChildWithExceptionAsync() {

    val supervisor = SupervisorJob()

    GlobalScope.launch(supervisor + Dispatchers.Default) {

            runBlocking {

                    val jobResults = mutableListOf<Deferred<Any>>()
                    for (i in 1..4) {
                        val def = GlobalScope.async {
                            println("GG-executing ${i}th coroutine")
                            if (i == 3) {
                                delay(100)
                                throw ArithmeticException()
                            }
                            i
                        }
                        jobResults.add(def)
                    }

                    jobResults.forEach { result ->
                        try {
                            val res = result.await()
                            println("GG-RESULT ${res}")
                        } catch (e: ArithmeticException) {
                            println("GG-Caught ArithmeticException")
                        }
                    }

            }

        println("GG-ENDING")
    }
}

// launch exception handling - non-globalscope
// inner coroutine has a parent and when it's cancelled, regardless the inner coroutine handles the exception,
// the exception ios propagated to the parent.
fun testParentChildWithExceptionAsync2() {

    val handler = CoroutineExceptionHandler { _, exception ->
        println("Caught $exception")
    }

    val supervisor = SupervisorJob()

    GlobalScope.launch(handler + supervisor + Dispatchers.Default) {
        var parentContext : CoroutineContext?
        val job = launch {
            parentContext = coroutineContext
            val jobResults = mutableListOf<Deferred<Any>>()
            for (i in 1..4) {
                val def = async {
                    //                            try {
                    println("GG-executing ${i}th coroutine")
                    if (i == 3) {
                        delay(100)
                        throw ArithmeticException()
                    }
                    i
                }
                jobResults.add(def)
            }

            jobResults.forEach { result ->
                try {
                    val res = result.await()
                    println("GG-RESULT ${res}")
                } catch (e: ArithmeticException) {
                    println("GG-Caught ArithmeticException")
                    println("GG-parent ${parentContext!![Job]}")
                    println("GG-parent-cancelled ${parentContext!![Job]?.isCancelled}")
                }
            }
        }

        println("GG-ENDING")
    }
}

fun testParentChildWithExceptionAsync3() {

//    val supervisor = SupervisorJob()

//    GlobalScope.launch(supervisor + Dispatchers.Default) {

        runBlocking {

            val jobResults = mutableListOf<Deferred<Any>>()
            for (i in 1..4) {
                try {
                    val def = async {
//                        try {
                            println("GG-executing ${i}th coroutine")
                            if (i == 3) {
                                delay(100)
                                throw ArithmeticException()
                            }
                            i
//                        } catch (e: ArithmeticException) {
//                            println("GG-Caught ArithmeticException")
//                        }
                    }
                    jobResults.add(def)
                } catch (e: ArithmeticException) {
                    println("GG-Caught ArithmeticException")
                }
            }

            jobResults.forEach { result ->
                val res = result.await()
                println("GG-RESULT ${res}")
            }

        }

        println("GG-ENDING")
//    }
}

suspend fun testParentChildWithExceptionAsync4() {

    //runBlocking {

        supervisorScope {

            val jobResults = mutableListOf<Deferred<Any>>()
            for (i in 1..5) {
                val def = async {
                    println("TAG-executing ${i}th coroutine")
                    if (i == 3) {
                        delay(100)
                        throw ArithmeticException()
                    }
                    i
                }
                jobResults.add(def)
            }

            jobResults.forEach { result ->
                try{
                    val res = result.await()
                    println("TAG-RESULT ${res}")
                } catch (e: ArithmeticException) {
                    println("TAG-Caught ArithmeticException")
                }
            }

        }

    //}

    println("TAG-ENDING")
//    }
}


suspend fun testParentChildWithExceptionAsync5() {

    coroutineScope {

        val jobResults = mutableListOf<Deferred<Any>>()
        for (i in 1..5) {
            val def = async {
                println("TAG-executing ${i}th coroutine")
                if (i == 3) {
                    delay(100)
                    throw ArithmeticException()
                }
                i
            }
            jobResults.add(def)
        }

        jobResults.forEach { result ->
            try{
                val res = result.await()
                println("TAG-RESULT ${res}")
            } catch (e: ArithmeticException) {
                println("TAG-Caught ArithmeticException")
            }
        }

    }

    println("TAG-ENDING")
//    }
}

suspend fun testParentChildWithExceptionAsync6NoTryCatch() {

    coroutineScope {

        val jobResults = mutableListOf<Deferred<Any>>()
        for (i in 1..5) {
            val def = async {
                println("HH-executing ${i}th coroutine")
                if (i == 3) {
                    delay(100)
                    throw ArithmeticException()
                }
                i
            }
            jobResults.add(def)
        }

        jobResults.forEach { result ->
            val res = result.await()
            println("GG-RESULT ${res}")
        }

    }

    println("GG-ENDING")
}

suspend fun testParentChildWithExceptionWithoutSupervision() {

    coroutineScope {

        val job1 = launch {
            println("TAG-running first (inner) child")
            delay(200)
            throw ArithmeticException()
        }

        val job2 = launch {
            println("TAG-running second (inner) child")
            delay(500)
        }

        try {
            job1.join()
        } finally {
            println("TAG-second child is cancelled: ${job2.isCancelled}")
        }

        //println("TAG-ENDING")
    }
}

suspend fun testParentChildWithExceptionWithSupervision() {

    supervisorScope {

        val job1 = launch {
            println("TAG-running first (inner) child")
            delay(200)
            throw ArithmeticException()
        }

        val job2 = launch {
            delay(500)
        }

        job1.join()
        println("TAG-second child is cancelled: ${job2.isCancelled}")

        println("TAG-ENDING")
    }
}

suspend fun testParentChildWithExceptionWithoutSupervisionAsync() {

//    try {
    coroutineScope {

        val deferred = async {
            println("HH-doing first child")
            delay(200)
            throw ArithmeticException()
        }

        val deferred2 = async {
            delay(500)
        }


        try {
            deferred.await()
        } finally {
            println("TAG-second child is cancelled: ${deferred2.isCancelled}")
        }
    }
//    } catch (e: ArithmeticException) {R
//        println("GG-Caught ArithmeticException")
//    }
}

fun testParentChildWithExceptionlaunch() {

    runBlocking{

        launch {

            for (i in 1..4) {
                launch {
                    println("HH-executing ${i}th coroutine")
                    if (i == 3) {
                        delay(100)
                        throw ArithmeticException() // UNHANDLED
                    }
                }
            }

        }

        println("HH-ENDING")
    }
}

@InternalCoroutinesApi
suspend fun testAsyncExceptionProp(){

//    runBlocking {
//
//        val def1 = async {
//            println("YYY-first-async-job-started")
//            delay(1000)
//            throw ArithmeticException()
//            5
//        }
//
//        val def2 = async {
//            println("YYY-second-async-job-started")
//            delay(1000)
//            6
//        }
//
//        val res1 = try {
//            def1.await()
//        } catch (e: ArithmeticException){
//            7
//        }
//        println("YYY-result: ${ res1 + def2.await()}")
//    }
//
//        coroutineScope {
//
//            val def = async { throw ArithmeticException() }
//            try {
//                def.await()
//            } catch (ex: ArithmeticException) {
//                println("TAG-Catching ex in runFailingCoroutine() #1: $ex")
//            }
//
//            val def2 = async { 7 }
//            println("TAG-second-failed-${def2.isCancelled}")
//            def2.await()
//        }

//        supervisorScope {
//
//            launch {
//                try {
//                    throw ArithmeticException()
//                } catch (ex: ArithmeticException) {
//                    println("TAG-Catching ex in runFailingCoroutine() #1: $ex")
//                }
//            }
//
//            val def2 = async { 7 }
//            println("TAG-second-failed-${def2.isCancelled}")
//            def2.await()
//        }

    try{
        supervisorScope {
            val job1 = launch {
                println("TAG-executing-job1")
                delay(100)
                throw ArithmeticException()
            }

            val job2 = launch {
                println("TAG-executing-job2")
                delay(400)
            }

            try {
                job2.join()
            } finally {
                println("TAG-job1-has-cancelled: ${job1.isCancelled}")
                println("TAG-job2-has-cancelled: ${job2.isCancelled}")
            }
        }
    } catch (ex: ArithmeticException){
        println("TAG-exception-caught-twice")
    }
}

typealias Foo = () -> Unit

fun Foo.navigateAndLaunch(block: () -> Unit){
    try {
        block()
    } catch (exception: Exception) {
        //
    }
}