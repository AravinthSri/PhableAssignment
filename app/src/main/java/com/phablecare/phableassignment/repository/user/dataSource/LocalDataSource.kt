package com.phablecare.phableassignment.repository.user.dataSource

import androidx.lifecycle.LiveData
import com.phablecare.phableassignment.data.entity.User

interface LocalDataSource {
    fun getUser(): LiveData<List<User>>
    suspend fun addUser(users:User):Long
    suspend fun deleteUser(user:User)
}