package com.ing.android.coolusers.coroutine

import kotlinx.coroutines.*
import kotlin.concurrent.thread
import kotlin.coroutines.coroutineContext

fun doBlockingTest1() {
    runBlocking {
        GlobalScope.launch {
            repeat(100) { i ->
                println("UU-thread-name- ${Thread.currentThread().name}")
                delay(50L)
            }
        }
    }
}

fun doBlockingTest2() {
    repeat(100) { i ->
        thread {
            println("UU-thread-name- ${Thread.currentThread().name}")
            Thread.sleep(50L)
        }
    }
}

fun doSth() {

    println("UU-thread-0-name: ${Thread.currentThread().name}")

    runBlocking(Dispatchers.Default) {

        println("UU-thread-1-name: ${Thread.currentThread().name}")
        println("UU-Damn")

        delay(2000)

        val job = launch {
            println("UU-Shit")
            println("UU-thread-2-name: ${Thread.currentThread().name}")
            delay(2000L)
            println("UU-World")
        }

        println("UU-Shieeat")

        val job2 = launch {
            println("UU-Fuck")
            delay(500L)
            println("UU-Curse")
        }

        println("UU-Bok-joins")

        job.join()

        println("UU-Hello")
    }

    println("UU-thread-3-name: ${Thread.currentThread().name}")
    println("UU-End")
}

fun doSth2() {

    runBlocking {

        println("UU-thread-1-name: ${Thread.currentThread().name}")
        println("UU-Damn")

        launch {
            println("UU-Shit")
            println("UU-thread-2-name: ${Thread.currentThread().name}")
            delay(1000L)
            println("UU-World")
        }

        launch {
            println("UU-Yeah")
            println("UU-thread-3-name: ${Thread.currentThread().name}")
            delay(1000L)
            println("UU-Geez")
        }

        delay(10)
        println("UU-Hello")
    }

    println("UU-End")
}

fun doSth3() {

    GlobalScope.launch(Dispatchers.Main) {

        println("UU-thread-1-name: ${Thread.currentThread().name}")
        delay(2000)
        println("UU-Nice")
        delay(3000)
        println("UU-shirt")
        launch(Dispatchers.Default) {
            println("UU-thread-2-name: ${Thread.currentThread().name}")
            println("UU-yeaaah!")
        }
        println("UU-bitch")
    }

    println("UU-End")
}

fun doSthElse() {

    GlobalScope.launch {

        launch {
            delay(2000L)
            println("UU-Orange")
        }

        coroutineScope {

            launch {
                delay(3000L)
                println("UU-Pear")
            }

            launch {
                delay(2000L)
                println("UU-Fig")
            }
        }

        println("UU-Banana")
    }

    println("UU-Apple")

}

fun doSthTotallyDifferent(){

    GlobalScope.launch {

        val job = launch {
            delay(1000L)
            println("UU-TotallyDifferent-1")
        }
        job.join()

        println("UU-TotallyDifferent-2")
    }


}

fun doSthTotallyDifferent2(){

    GlobalScope.launch {

        runBlocking {
            delay(1000L)
            println("UU-TotallyDifferent-1")
        }

        println("UU-TotallyDifferent-2")
    }


}

fun doSthTotallyDifferent3(){

    val deferred = (1..1_000).map { i ->
        GlobalScope.async {
            i
        }
    }

    runBlocking {
        val sum = deferred.sumBy { j ->
            j.await()
        }

        println(sum)
    }
}


// using GlobalScope on each launch threats them launches as Java threads - independent execution units

fun doSthTotallyDifferent4(){

    val parent = GlobalScope.launch {

        GlobalScope.launch {
            delay(2000L)
            println("UU-yellow")
        }

        GlobalScope.launch {
            delay(2000L)
            println("UU-blue")
        }

        println("UU-red")
    }

    println("UU-white")
    Thread.sleep(100L) // give some time to parent job finish
    println("UU-orange-${parent.isCompleted}") // true
}

fun doSthTotallyDifferent5(){

    val parent = GlobalScope.launch {

        println("UU-lentil")

        launch {
            delay(2000L)
            println("UU-oat")
        }

        launch {
            delay(2000L)
            println("UU-barley")
        }

        println("UU-wheat")
    }

    Thread.sleep(100L) // give some time to parent job finish
    println("UU-kinoa-${parent.isCompleted}") // false
}


//  thread jumping

fun doSthTotallyDifferent6(){

    val parent = GlobalScope.launch {
        println("UU-thread-1-name-${Thread.currentThread().name}")

        launch(Dispatchers.Main) {
            //Thread.sleep(1000)
            println("UU-thread-2-name-${Thread.currentThread().name}")
        }

        launch(Job()) {
            println("UU-thread-3-name-${Thread.currentThread().name}")
        }

    }
}

//  thread jumping 2

fun doSthTotallyDifferent7(){

    GlobalScope.launch(Dispatchers.Main) {
        val outerContext = coroutineContext
        println("UU-thread-1-name-${Thread.currentThread().name}")

        println("UU-job-${coroutineContext[Job]}")

        launch(Dispatchers.Default) {
            println("UU-thread-2-name-${Thread.currentThread().name}")

            runBlocking {
                println("UU-thread-3-name-${Thread.currentThread().name}")
            }

            withContext(outerContext) {
                println("UU-thread-4-name-${Thread.currentThread().name}")
            }

        }
    }
}

// run launch on the same (main) thread by using runBlocking - suspension can be seen easier on inner launch

fun doSthTotallyDifferent8(){
    runBlocking {
        println("UU-patatoes-${Thread.currentThread().name}")
        launch {
            println("UU-tomatoes-${Thread.currentThread().name}")
        }
        println("UU-asparagus-${Thread.currentThread().name}")
    }
    println("UU-carrots-${Thread.currentThread().name}")
}

fun doSthTotallyDifferent9(){

    val parent = GlobalScope.launch {

        launch {
            delay(1000L)
            println("TT-jeans")
        }

        launch {
            delay(1000L)
            println("TT-pans")
        }

        launch {
            println("TT-scope-equality-${this.coroutineContext}")
            //doSuspendingThing1()
            doSuspendingThing1ScopeTweak1()
            //doSuspendingThing1ScopeTweak2()
        }
    }

    Thread.sleep(100) // make sure parent finishes
    println("TT-parent-state-1-${parent.isCompleted}")
    Thread.sleep(3000L)
    println("TT-parent-state-2-${parent.isCompleted}")
}

suspend fun doSuspendingThing1(){
    withContext(Dispatchers.Default)  {
        println("TT-scope-equality-${this.coroutineContext}")
        delay(4000L)
        println("TT-skirts")
    }
}

suspend fun doSuspendingThing1ScopeTweak1(){
    coroutineScope {
        delay(4000L)
        println("TT-skirts")
    }
}

suspend fun doSuspendingThing1ScopeTweak2(){
    GlobalScope.launch {
        delay(4000L)
        println("TT-skirts")
    }
}

fun scopeCheck(){
    GlobalScope.launch {
        doSuspendingThing2(this)
    }
}

suspend fun doSuspendingThing2(coroutineScope: CoroutineScope){
    delay(1000L)
    println(coroutineScope.coroutineContext === coroutineContext)
}