package com.phablecare.phableassignment.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(@PrimaryKey(autoGenerate = true)
                var uuid: Int = 0,
                var userName: String,
                var email: String)