package com.phablecare.phableassignment.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.phablecare.phableassignment.R
import com.phablecare.phableassignment.data.entity.User
import com.phablecare.phableassignment.databinding.UserListItemLayoutBinding

class UserListAdapter:RecyclerView.Adapter<UserListAdapter.UserListViewHolder>() {

    private val userList = ArrayList<User>()
    private lateinit var listener: UserClickListener

    fun setList(user: ArrayList<User>,listener:UserClickListener){
        this.listener=listener
        userList.clear()
        userList.addAll(user)
        notifyDataSetChanged()
    }

    fun removeUser(user: User,position: Int){
        userList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position,userList.size)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding : UserListItemLayoutBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.user_list_item_layout,
            parent,
            false
        )
        return UserListViewHolder(binding,listener)
    }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        holder.bind(userList[position])
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class UserListViewHolder(val binding: UserListItemLayoutBinding,val listener: UserClickListener):
        RecyclerView.ViewHolder(binding.root){
        fun bind(user: User){
            binding.tvUserName.text = user.userName
            binding.tvEMail.text = user.email
            binding.root.setOnClickListener {
                listener.onClicked(user,layoutPosition)
            }
        }
    }

    interface UserClickListener{
        fun onClicked(user: User,position: Int)
    }
}