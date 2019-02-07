package com.ing.android.coolusers.view.ui.userlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.ing.android.coolusers.R
import com.ing.android.coolusers.domain.objects.User
import com.ing.android.coolusers.databinding.UserListItemBinding

class UserListAdapter(private val listener: (User) -> Unit) :
        ListAdapter<User, UserHolder>(object : ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: User, newItem: User) = true
        }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        val layoutInflater = LayoutInflater.from(parent.getContext())
        val itemBinding = UserListItemBinding.inflate(layoutInflater, parent, false)
        return UserHolder(itemBinding, listener)
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        holder.bindTo(getItem(position))
    }
}

class UserHolder(val itemBinding: UserListItemBinding, private val listener: (User) -> Unit) : ViewHolder(itemBinding.root) {
    fun bindTo(item: User) {
        itemBinding.user = item
        itemBinding.executePendingBindings()
        Glide.with(itemBinding.root.context)
                .load(itemBinding.user!!.imageThumbnailUrl)
                .into(itemBinding.thumbnail)
        itemView.setOnClickListener {
            listener.invoke(item)
        }
    }
}

