package com.phablecare.phableassignment.repository.user

import androidx.lifecycle.LiveData
import com.phablecare.phableassignment.data.entity.User
import com.phablecare.phableassignment.repository.user.dataSource.LocalDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(private val localDataSource: LocalDataSource):UserRepository {


    override fun getUserList(): LiveData<List<User>> {
       return localDataSource.getUser()
    }

    override suspend fun addUser(users: User): Long {
        return withContext(Dispatchers.IO){
            return@withContext localDataSource.addUser(users)
        }
    }

    override suspend fun deleteUser(user: User) {
        withContext(Dispatchers.IO){
            return@withContext localDataSource.deleteUser(user)
        }
    }


}