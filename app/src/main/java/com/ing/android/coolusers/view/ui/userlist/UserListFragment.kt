package com.ing.android.coolusers.view.ui.userlist

import android.os.Bundle
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
import com.ing.android.coolusers.view.ui.BaseFragment
import com.ing.android.coolusers.view.ui.userdetail.UserDetailParams
import viewmodels.UserListViewModel

class UserListFragment : BaseFragment() {

    private lateinit var listViewModel: UserListViewModel

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.user_list_fragment, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModels()
        setupList(view)
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
            setHasFixedSize(true)
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
