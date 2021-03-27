package com.example.mobilecourse2

import android.app.Application
import com.example.mobilecourse2.di.AppComponent
import com.example.mobilecourse2.di.DaggerAppComponent
import com.example.mobilecourse2.di.main.MainComponent

class BaseApplication: Application() {

    lateinit var appComponent: AppComponent

    private var mainComponent: MainComponent? = null

    override fun onCreate() {
        super.onCreate()
        initAppComponent()
    }

    fun releaseMainComponent(){
        mainComponent = null
    }

    fun mainComponent(): MainComponent {
        if(mainComponent == null){
            mainComponent = appComponent.mainComponent().create()
        }
        return mainComponent as MainComponent
    }

    fun initAppComponent(){
        appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()
    }

}
