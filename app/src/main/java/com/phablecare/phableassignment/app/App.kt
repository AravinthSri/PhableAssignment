package com.phablecare.phableassignment.app

import android.app.Application
import com.phablecare.phableassignment.di.component.AppComponent
import com.phablecare.phableassignment.di.component.DaggerAppComponent
import com.phablecare.phableassignment.di.module.AppModule

class App:Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        initializeDagger()
    }

    private fun initializeDagger() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()

        appComponent.inject(this)


    }
}