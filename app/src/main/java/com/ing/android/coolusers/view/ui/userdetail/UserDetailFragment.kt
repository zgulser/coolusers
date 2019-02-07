package com.ing.android.coolusers.view.ui.userdetail

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.ing.android.coolusers.databinding.UserDetailFragmentBinding
import com.ing.android.coolusers.view.ui.userlist.UserListAdapter
import kotlinx.android.parcel.Parcelize

class UserDetailFragment : Fragment() {

    private lateinit var detailViewModel: UserDetailViewModel

    private lateinit var detailItemBinding : UserDetailFragmentBinding

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        detailItemBinding = UserDetailFragmentBinding.inflate(layoutInflater, container, false)
        return detailItemBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupView()
    }

    private fun setupViewModel(){
        detailViewModel = ViewModelProviders.of(this,
                viewModelFactory {
                    UserDetailViewModel(activity!!.application)
                }).get(UserDetailViewModel::class.java)
        val uid = UserDetailFragmentArgs.fromBundle(arguments?: Bundle()).detailParams.uid
        detailViewModel.init(uid)
    }

    private fun setupView() {
        detailViewModel.user.observe(this@UserDetailFragment, Observer { pUser ->
            detailItemBinding.apply {
                userPresenter = UserDetailPresenter(activity!!.applicationContext, detailViewModel)
                executePendingBindings()
            }
            Glide.with(detailItemBinding.root.context)
                .load(pUser.imageUrl)
                .into(detailItemBinding.imageView)
        })
    }

    protected inline fun <VM : ViewModel> viewModelFactory(crossinline f: () -> VM) =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(aClass: Class<T>):T = f() as T
            }
}

@Parcelize
data class UserDetailParams(val uid: String) : Parcelable

// extensions
fun UserDetailParams.toBundle() = Bundle().apply { putParcelable("user_detail_params", this@toBundle) }
private fun Bundle.toUserDetailParams() = this.getParcelable<UserDetailParams>("user_detail_params")
