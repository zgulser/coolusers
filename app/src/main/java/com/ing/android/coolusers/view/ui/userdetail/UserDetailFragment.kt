package com.ing.android.coolusers.view.ui.userdetail

import android.content.res.Configuration
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.ing.android.coolusers.databinding.UserDetailFragmentBinding
import com.ing.android.coolusers.view.ui.BaseFragment
import kotlinx.android.parcel.Parcelize

class UserDetailFragment : BaseFragment() {

    private lateinit var detailViewModel: UserDetailViewModel

    private lateinit var detailItemBinding: UserDetailFragmentBinding

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        detailItemBinding = UserDetailFragmentBinding.inflate(layoutInflater, container, false)
        detailItemBinding.setLifecycleOwner (viewLifecycleOwner)
        return detailItemBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupDetailView()
    }

    private fun setupViewModel() {
        val uid = UserDetailFragmentArgs.fromBundle(arguments ?: Bundle()).detailParams.uid
        detailViewModel = ViewModelProviders.of(this,
                viewModelFactory {
                    UserDetailViewModel(activity!!.application, uid)
                }).get(UserDetailViewModel::class.java)
    }

    private fun setupDetailView() {
        detailViewModel.getUser().observe(this, Observer { pUser ->
            detailItemBinding.apply {
                userPresenter = UserDetailPresenter(activity!!.applicationContext, detailViewModel)
                executePendingBindings()
                Glide.with(root.context)
                        .load(pUser.imageUrl)
                        .into(imageView)
            }
        })
    }
}

@Parcelize
data class UserDetailParams(val uid: String) : Parcelable
