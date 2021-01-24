package com.phablecare.phableassignment.viewmodelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.phablecare.phableassignment.repository.user.UserRepositoryImpl
import com.phablecare.phableassignment.viewmodel.SharedUserViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@Suppress("UNCHECKED_CAST")
class SharedUserViewModelFactory @Inject constructor(val repository: UserRepositoryImpl):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SharedUserViewModel(repository) as T
    }
}