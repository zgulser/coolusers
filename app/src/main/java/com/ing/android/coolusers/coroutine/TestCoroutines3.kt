package com.ing.android.coolusers.coroutine

import android.util.Log
import kotlinx.coroutines.*
import kotlinx.coroutines.NonCancellable.cancel

@UseExperimental(InternalCoroutinesApi::class)
fun testCancellationCoroutines1(){
    GlobalScope.launch {
        val firedJobs = mutableListOf<Deferred<Int>>()
        coroutineScope {

            for (i in 1..5) {
                val deferred = async (start = CoroutineStart.LAZY) {
                    println("SS-${i}")
                    i
                }
                firedJobs.add(deferred)
            }

            var counter = 0
            for (job in firedJobs) {
//                if (counter == 3) {
//                    coroutineContext.cancelChildren() // cancel remaining jobs
//                    break
//                }
                job.start()
                job.await()
                counter++
            }
            coroutineContext.cancelChildren()
        }

        for (job in firedJobs){
            if (job.isCancelled){
                println("SS-cancelled")
            } else if (job.isCompleted){
                println("SS-completed")
            }
        }
    }
}

@UseExperimental(InternalCoroutinesApi::class)
fun testCancellationCoroutines2(){
    GlobalScope.launch {
        val firedJobs = mutableListOf<Deferred<Int>>()
        coroutineScope {

            for (i in 1..5) {
                val deferred = async{
                    delay(100)
                    println("SS-${i}")
                    i
                }
                firedJobs.add(deferred)
            }

            var counter = 0
            for (job in firedJobs) {
                if (counter == 3) {
                    coroutineContext.cancelChildren() // cancel remaining jobs
                    break
                }
                //job.await()
                counter++
            }
        }

        for (job in firedJobs){

            if (job.isActive){
                println("SS-active")
            }

            if (job.isCancelled){
                println("SS-cancelled")
            } else if (job.isCompleted){
                println("SS-completed")
            }
        }
    }
}