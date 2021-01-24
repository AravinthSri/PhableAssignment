package com.phablecare.phableassignment.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.phablecare.phableassignment.data.entity.User

@Dao
interface UserDao {

    @Query("SELECT * FROM User")
    fun getUser(): LiveData<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User):Long

    @Delete
    suspend fun delete(user: User)

}