package com.example.mobilecourse2.di.main
import com.example.mobilecourse2.ui.main.MainActivity

import dagger.Subcomponent

@Subcomponent(modules = [
    MainModule::class,
    NetworkModule::class,
    MainViewModelModule::class
])
@MainScope
interface MainComponent {

    @Subcomponent.Factory
    interface Factory{
        fun create(): MainComponent
    }

    fun inject(activity: MainActivity)
}
