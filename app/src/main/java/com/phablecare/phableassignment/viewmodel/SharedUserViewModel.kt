package com.phablecare.phableassignment.viewmodel


import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phablecare.phableassignment.data.entity.User
import com.phablecare.phableassignment.repository.user.UserRepositoryImpl
import kotlinx.coroutines.launch

class SharedUserViewModel(val repository: UserRepositoryImpl):ViewModel(), Observable {

    val userList = repository.getUserList()
    val inputName = MutableLiveData<String>()
    val inputEmail = MutableLiveData<String>()
    val userNameErrorMessage = MutableLiveData<String>()
    val emailErrorMessage = MutableLiveData<String>()
    val addAndUpdateStatusMessage = MutableLiveData<Boolean>()
    val updateUser = MutableLiveData<User>()


    fun insert(user: User) = viewModelScope.launch {
        val newRowId = repository.addUser(user)
        addAndUpdateStatusMessage.postValue((newRowId > -1))
    }

    fun update(user: User) = viewModelScope.launch {
        val noOfRows = repository.addUser(user)
        addAndUpdateStatusMessage.postValue(noOfRows > 0)
    }


    fun delete(user: User) = viewModelScope.launch {
        repository.deleteUser(user)
    }

    fun sendUser(user: User) {
        updateUser.value = user
    }


    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

}