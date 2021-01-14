package com.ing.android.coolusers.view.ui.userlist

import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavHost
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ing.android.coolusers.R
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DefaultItemAnimator
import com.ing.android.coolusers.coroutine.*
import com.ing.android.coolusers.exceptions.testCatching
import com.ing.android.coolusers.exceptions.testNestedTryCatch
import com.ing.android.coolusers.view.ui.BaseFragment
import com.ing.android.coolusers.view.ui.userdetail.UserDetailParams
import com.ing.android.coolusers.viewmodels.UserListViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.test.TestCoroutineContext
import java.lang.ArithmeticException
import java.lang.Exception

class UserListFragment : BaseFragment() {

    private val TAG = "TAG"

    private lateinit var listViewModel: UserListViewModel

    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.user_list_fragment, container, false)


    @InternalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModels()
        setupList(view)

        val handler = CoroutineExceptionHandler { _, exception ->
            println("TAG-User Exception Caught")
        }

        //doBlockingTest1()
        //doBlockingTest2()
        //doSth()
        //doSth2()
        //doSth3()
        //doSthTotallyDifferent8()
        //doSthTotallyDifferent5()
        //doSequential()
        //doSequential2()
        //doConcurrent()
        //doConcurrent1()
        //doConcurren3t()
        //doConcurren4t()
        //doSthTotallyDifferent7()
        //doConcurren6t1()
        //doConcurren6t2()
        //doConcurren6t3()
        //doConcurren6t4()
        //doConcurrent7()
        //doConcurrent8()
        //doConcurrent9()
        //println("SS-champaigne")
        //doConcurrent10()
        //println("SS-kimiz")
        //doConcurrent11()
        //doConcurrent12()
        //doConcurrent13()
        //doConcurrent14()
        //testCancellationCoroutines1()
        //testCancellationCoroutines2()
        //testUnconfined()
        //testNestedTryCatch()
        //testCatching()

        //testParentChildNoException()
        //testParentChildWithExceptionAsync()
        //testParentChildWithExceptionAsync2()
        //testParentChildWithExceptionAsync3()
//        scope.launch {
//            //testParentChildWithExceptionAsync4()
//            testParentChildWithExceptionAsync5()
//        }

//        scope.launch  {
//            try {
//                // LAUNCH AUTOMATICALLY PROPAGATES THE EXCEPTION
//                testParentChildWithExceptionlaunch()
//            } catch (exception : Exception){
//            println("HH-exception-handled")
//        }
//        }


//        scope.launch(handler) {
//
//            val job1 = launch {
//                println("TAG-first job is running")
//                delay(200)
//            }
//            job1.cancel()
//
////            try {
//                println("TAG-second job is running")
//                testParentChildWithExceptionWithoutSupervision()
////            } catch(e: Exception){
////                //println("TAG-${e}")
////            }
//            //testParentChildWithExceptionWithSupervision()
//            //testParentChildWithExceptionWithoutSupervisionAsync()
//            println("TAG-parent child cancelled: ${job1.isCancelled}")
//
//            launch {
//                println("TAG-another job is running")
//            }
//        }

//        scope.launch(handler) {
//
//            launch {
//                println("HH-first job is running")
//                delay(200)
//            }
//
//            // second job
//            //testParentChildWithExceptionWithSupervision()
//            //testParentChildWithExceptionWithoutSupervision()
//            //testParentChildWithExceptionWithoutSupervisionAsync()
//
//            // third job
//            launch {
//                println("HH-third job is running")
//            }
//        }

//        scope.launch {
//            testAsyncExceptionProp()
//        }

        println("TAG-continue...")
    }

    private fun setupViewModels() {
        listViewModel = ViewModelProviders.of(this,
                viewModelFactory {
                    UserListViewModel(activity!!.application)
                }).get(UserListViewModel::class.java)
    }

    private fun setupList(view: View) {
        view.findViewById<RecyclerView>(R.id.repoList).apply {
            addItemDecoration(DividerItemDecoration(activity!!, DividerItemDecoration.VERTICAL))
            setHasFixedSize(true)li
            itemAnimator = DefaultItemAnimator()
            layoutManager = LinearLayoutManager(view.context)
            adapter = UserListAdapter {
                (activity as NavHost).navController.navigate(
                        UserListFragmentDirections.showUserDetail(UserDetailParams(it.id))
                )
            }
            listViewModel.getUserList().observe(viewLifecycleOwner, Observer { users ->
                val headUsers = users?.take(10)
                (adapter as? UserListAdapter ?: throw IllegalArgumentException("wrong adapter!")).submitList(headUsers)
                (adapter as UserListAdapter).notifyItemChanged(0)
            })
        }
    }
}
