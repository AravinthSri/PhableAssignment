package com.phablecare.phableassignment.repository.user.dataSourceImpl

import androidx.lifecycle.LiveData
import com.phablecare.phableassignment.data.dao.UserDao
import com.phablecare.phableassignment.data.entity.User
import com.phablecare.phableassignment.repository.user.dataSource.LocalDataSource
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(private val userDao: UserDao): LocalDataSource {
    override fun getUser(): LiveData<List<User>> =userDao.getUser()
    override suspend fun addUser(users: User) =userDao.insert(users)
    override suspend fun deleteUser(user: User) =userDao.delete(user)


}