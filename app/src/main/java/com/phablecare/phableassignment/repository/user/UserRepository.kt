package com.phablecare.phableassignment.repository.user

import androidx.lifecycle.LiveData
import com.phablecare.phableassignment.data.entity.User

interface UserRepository {
    fun getUserList(): LiveData<List<User>>
    suspend fun addUser(users: User):Long
    suspend fun deleteUser(user: User)
}