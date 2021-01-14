package com.ing.android.coolusers.coroutine

import kotlinx.coroutines.*
import java.io.IOException
import java.io.InvalidObjectException
import java.io.UncheckedIOException
import java.lang.Exception
import java.lang.IllegalArgumentException
import java.util.concurrent.TimeoutException
import kotlin.concurrent.thread
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext
import kotlin.system.measureTimeMillis

// using suspend keyword only does NOT make the suspending function non-blocking by default!!

fun doSequential() {

    GlobalScope.launch {

        val time = measureTimeMillis {
            println("SS-tombul-0")
            val num1 = doSuspend1()
            println("SS-tombul-1")
            val num2 = doSuspend2()
            println("SS-tombul-2")

            println("SS-sum is: ${num1 + num2}")
        }
        println("SS-time-spent-${time}")
    }
}

fun doSequential2() {

    GlobalScope.launch(Dispatchers.Default) {

        println("SS-thread-1-name ${Thread.currentThread().name}")

        doSuspend2()

        println("SS-thread-2-name ${Thread.currentThread().name}")

        doSuspend2()

        println("SS-thread-3-name ${Thread.currentThread().name}")
    }

}

fun doConcurrent() {

    GlobalScope.launch {

        println("SS-context is $coroutineContext")
        println("SS-context is ${Thread.currentThread().name}")

        val time = measureTimeMillis {
            println("SS-sisko-0")
            val num1 = async {
                doSuspend1()
            }
            println("SS-sisko-1")
            val num2 = async {
                doSuspend2()
            }
            println("SS-sisko-2")

            println("SS-sum is: ${num1.await() + num2.await()}")
        }
        println("SS-time-spent-${time}")
    }

    runBlocking {
        println("SS-context is $coroutineContext")
        println("SS-context is ${Thread.currentThread().name}")
    }
}

fun doConcurrent1() {

    GlobalScope.launch {

        println("SS-context is $coroutineContext")
        println("SS-context is ${Thread.currentThread().name}")

        val time = measureTimeMillis {
            println("SS-tos-0")
            val j1 = async {
                println("SS-async-1")
                println("SS-async-1 ${Thread.currentThread().name}")
                delay(4000L)
                5
            }

            println("SS-tos-1")

            //delay(400L)

            val j2 = launch {
                println("SS-async-2")
                println("SS-async-2 ${Thread.currentThread().name}")
                delay(1000L)
                6
            }

            println("SS-tos-2")

            val res = j1.await() // suspends the current coroutine, not the one that contains the job

            println("SS-tos-3")

            val res2 = 6

            println("SS-res-: ${res + res2}")

        }
        println("SS-time-spent-${time}")
    }

    runBlocking {
        println("SS-context is $coroutineContext")
        println("SS-context is ${Thread.currentThread().name}")
    }
}

fun doConcurren2t() {

    GlobalScope.launch {

        println(coroutineContext)

        val time = measureTimeMillis {
            println("SS-sisko-0")
            val num1 = launch {
                doSuspend1()
            }
            println("SS-sisko-1")
            val num2 = launch {
                doSuspend2()
            }
            println("SS-sisko-2")

            //println("SS-sum is: ${num1 + num2}") // compiler error = launch vs async
        }
        println("SS-time-spent-${time}")
    }
}

fun doConcurren3t() {

    GlobalScope.launch {
        println("SS-thread-1-name-${Thread.currentThread().name}")

        val j1 = launch {
            println("SS-thread-2-name-${Thread.currentThread().name}")
            delay(1000)
        }

        withContext(Dispatchers.Default) {
            println("SS-thread-3-name-${Thread.currentThread().name}")
            delay(2500)
        }

        coroutineScope {
            println("SS-thread-4-name-${Thread.currentThread().name}")
            delay(1500)
        }
        delay(5000)
    }
}

// scope and dispatcher checks

fun doConcurren4t() {

    val j = GlobalScope.launch {
        println("SS-thread-1-name-${Thread.currentThread().name}")
        println("SS-dispatcher-1: ${coroutineContext}")
        val parentScope = this

        launch {
            println("SS-equal-to-parent-scope-1: ${parentScope === this}")
            println("SS-dispatcher-2: ${coroutineContext}")
            println("SS-thread-2-name-${Thread.currentThread().name}")
            delay(1000)
        }

        withContext(Dispatchers.Main) {
            println("SS-equal-to-parent-scope-2: ${parentScope === this}")
            println("SS-dispatcher-3: ${coroutineContext}")
            println("SS-thread-2-name-${Thread.currentThread().name}")
            delay(1000)
        }
    }

    thread {
        Thread.sleep(1000)
        println("SS-parent-job-complete-${j.isCompleted}")
    }
}


// async-await

fun doConcurren5t() {

    val def = GlobalScope.async {
        delay(2000)
        5
    }

    runBlocking {
        val res = def.await()
    }
}


suspend fun doSuspend1() : Int {
    coroutineScope {
        delay(2000L)

    }
    return 13
}

suspend fun doSuspend2() : Int {
    println("SS-thread-name-V1 ${Thread.currentThread().name}")
    delay(2000L)
    println("SS-thread-name-V2 ${Thread.currentThread().name}")
    return 40
}

// parent-child

fun doConcurren6t1() {

    GlobalScope.launch {

        val j  = launch {
            delay(100L)
        }
        coroutineContext[Job]?.cancel() // cancelling parent cancels children
        println("SS-child-cancelled @doConcurren6t1(): ${j.isCancelled}")
    }
}

fun doConcurren6t2() {

    GlobalScope.launch {

        val j1  = launch {
            delay(100L)
        }

        val j2  = launch {
            delay(100L)
        }

        j1.cancel() // cancelling child does not cancels the parent and the other children
        println("SS-child-1-cancelled @doConcurren6t2(): ${j1.isCancelled}")
        println("SS-child-2-cancelled @doConcurren6t2(): ${j2.isCancelled}")
        println("SS-parent-cancelled @doConcurren6t2(): ${coroutineContext[Job]?.isCancelled}")
    }
}

fun doConcurren6t3() {

    GlobalScope.launch {

        println("SS-thread-0-name-${Thread.currentThread().name}")
        val parentContext = coroutineContext

        val p  = launch {

            println("SS-${this.coroutineContext === parentContext}")
            println("SS-thread-1-name-${Thread.currentThread().name}")

            //delay(500L) // SUSPENDING FUNC: causes this coroutine to switch it's thread for further execution - see SS-thread-4-name

            val c1 = async {
                println("SS-thread-2-name-${Thread.currentThread().name}")
                delay(1000L)
                println("SS-job1-finished-fine")
            }

            println("SS-1")

            // SUSPENDING FUNC: causes this coroutine to switch it's thread for further execution - see SS-thread-4-name
            withContext(Dispatchers.Main) {
                println("SS-thread-3-name-${Thread.currentThread().name}")
                delay(5000L)
                //for(i in 1..2000000000);
                println("SS-job2-finished-fine")
            }

            println("SS-thread-4-name-${Thread.currentThread().name}")

            println("SS-2")

            c1.cancel()

            println("SS-child-1-cancelled: ${c1.isCancelled}")
            println("SS-parent-cancelled: ${coroutineContext[Job]?.isCancelled}")
        }
    }
}

fun doConcurren6t4() {

    val parent = GlobalScope.launch {

        //println("SS-thread-1-name-${Thread.currentThread().name}")

        //delay(5000L) // SUSPENDS THE PARENT JOB/COROUTINE

        //println("SS-thread-2-name-${Thread.currentThread().name}")

        launch {
            delay(1000)
        }

        val job1 = launch {
            delay(3000)
        }

        val job2 = launch {
            delay(500)
            UncheckedIOException("sad", null)
        }

        //delay(1000L)

    }

    Thread.sleep(5000)
    println("SS-parent-cancelled: ${parent.isCancelled}")
}

fun doConcurrent7() {

    GlobalScope.launch {
        doThis(1)
        doThis(2)
        doThis(3)
        doThis(4)
    }
}

suspend fun doThis(count: Int){
    delay(1000)
    println("SS-doing... $count")
}

fun doConcurrent8() {

    GlobalScope.launch(Dispatchers.Main) {

        launch {
            delay(1000L)
            println("SS-Cucumber")
        }

        launch {
            delay(2000L)
            println("SS-Tomatoes")
        }

        //delay(3000L)

        launch {
            delay(1000L)
            println("SS-Potatoes")
        }

        println("SS-Parsley")
    }

    println("SS-Salary")
}

fun doConcurrent9() {

    val job1 = GlobalScope.launch(Dispatchers.Main) {

        println("SS-kabak-T1: ${Thread.currentThread().name}")

        val child1 = launch(Dispatchers.Default) {
            println("SS-cabbage")
            delay(500)
            println("SS-patlican")
        }

        println("SS-salatalik")

        val child2 =launch(Dispatchers.Main) {
            println("SS-bamya")
            delay(300)
            println("SS-havuc")
        }

        println("SS-biber")

        val sth = withContext(Dispatchers.IO){
            println("SS-domates-T1: ${Thread.currentThread().name}")
            delay(1000)
            println("SS-armut-T1: ${Thread.currentThread().name}")
            5
        }

        println("SS-sosis: ${sth}")

        println("SS-etli puro")
    }
}

fun doConcurrent10() {
    println("SS-gin-tonic")
    val job1 = GlobalScope.launch(Dispatchers.Main) {
        println("SS-beer: ${Thread.currentThread().name}")
        println("SS-moscow-mule-zerzevad-2 ${coroutineContext[Job]}")
        doIt()
        println("SS-martini: ${Thread.currentThread().name}")
    }
    println("SS-mohito")
}

suspend fun doIt(){
    withContext(Dispatchers.Default) {
        println("SS-wine")
        println("SS-jager-zerzevad-2 ${coroutineContext[Job]}")
        delay(1000)
        println("SS-raki")
    }
}

fun doConcurrent11() {


    GlobalScope.launch {

        val num1 = async {
            println("SS-TV")
            delay(2000)
            5
        }

        val num2 = async {
            println("SS-Fridge")
            delay(2000)
            6
        }

        println("SS-Table ${num1.await()}")
        println("SS-Chair")
        println("SS-Sofa ${num2.await()}")
    }

}

fun doConcurrent12() {

    GlobalScope.launch {

        println("SS-lufer ${Thread.currentThread().name}")
        delay(1000)
        println("SS-palamut ${Thread.currentThread().name}")
    }

}

fun doConcurrent13() {

    println("SS-red ${Thread.currentThread().name}")

    runBlocking (Dispatchers.Default) {
        println("SS-yellow ${Thread.currentThread().name}")
        delay(1000)
        println("SS-purple ${Thread.currentThread().name}")
    }

    println("SS-brown ${Thread.currentThread().name}")
}

fun doConcurrent14() {

    println("SS-arm ${Thread.currentThread().name}")

    GlobalScope.launch {

        println("SS-eye ${Thread.currentThread().name}")

        val job1 = launch {
            println("SS-feet ${Thread.currentThread().name}")
            //delay(500)
            for(i in 1..10000000);
            println("SS-butt ${Thread.currentThread().name}")
        }

        println("SS-finger-1 ${Thread.currentThread().name}")
        job1.join() // join is also suspending
        println("SS-finger-2 ${Thread.currentThread().name}")

        var sth = withContext(coroutineContext) {
            println("SS-eyebrow ${Thread.currentThread().name}")
            delay(2000)
            println("SS-hip ${Thread.currentThread().name}")
            10
        }

        sth = coroutineScope {
            println("SS-eyebrow ${Thread.currentThread().name}")
            delay(2000)
            println("SS-hip ${Thread.currentThread().name}")
            10
        }

        println("SS-wrist ${Thread.currentThread().name}")
    }

    println("SS-head ${Thread.currentThread().name}")
}