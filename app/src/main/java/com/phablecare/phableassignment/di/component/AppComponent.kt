package com.phablecare.phableassignment.di.component

import com.phablecare.phableassignment.app.App
import com.phablecare.phableassignment.di.module.AppModule
import com.phablecare.phableassignment.view.fragment.AddOrUpdateUserFragment
import com.phablecare.phableassignment.view.fragment.ListOfUserFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(app: App)
    fun inject(fragment: AddOrUpdateUserFragment)
    fun inject(fragment: ListOfUserFragment)

}