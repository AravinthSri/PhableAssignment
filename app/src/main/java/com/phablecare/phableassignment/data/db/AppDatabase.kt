package com.phablecare.phableassignment.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.phablecare.phableassignment.data.dao.UserDao
import com.phablecare.phableassignment.data.entity.User


private const val DB_VERSION=1
@Database(entities = [User::class], version = DB_VERSION,exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
}