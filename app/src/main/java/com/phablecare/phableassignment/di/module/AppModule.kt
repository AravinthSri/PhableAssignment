package com.phablecare.phableassignment.di.module


import androidx.room.Room
import com.phablecare.phableassignment.app.App
import com.phablecare.phableassignment.data.dao.UserDao
import com.phablecare.phableassignment.data.db.AppDatabase
import com.phablecare.phableassignment.repository.user.UserRepositoryImpl
import com.phablecare.phableassignment.repository.user.dataSourceImpl.LocalDataSourceImpl
import com.phablecare.phableassignment.viewmodelFactory.SharedUserViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule constructor(private val context: App) {

    @Provides @Singleton fun provideApp() = context

    @Provides
    @Singleton
    fun providesDatabase(): AppDatabase {
        return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "phablecare.db")
                .build()
    }

    @Provides
    @Singleton
    fun providesUserDao(): UserDao {
        return providesDatabase().userDao()
    }

    @Provides
    @Singleton
    fun providesUserLocalDataSource(): LocalDataSourceImpl {
        return LocalDataSourceImpl(providesUserDao())
    }


    @Provides
    @Singleton
    fun providesUserRepository(): UserRepositoryImpl {
        return UserRepositoryImpl(providesUserLocalDataSource())
    }

    @Provides
    @Singleton
    fun providesUserViewModelFactory(): SharedUserViewModelFactory {
        return SharedUserViewModelFactory(providesUserRepository())
    }




}
