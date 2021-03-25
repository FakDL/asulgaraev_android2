package com.example.mobilecourse2.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentFactory
import androidx.navigation.fragment.NavHostFragment
import com.example.mobilecourse2.BaseApplication
import com.example.mobilecourse2.R
import com.example.mobilecourse2.factories.main.MainNavHostFragment
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    lateinit var navHost: NavHostFragment

    @Inject
    lateinit var fragmentFactory: FragmentFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("Test", savedInstanceState.toString())
        if(savedInstanceState == null){
            createNavHost()
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        createNavHost()
        Log.d("Test", "re$savedInstanceState")
    }


    fun inject() {
        (application as BaseApplication).mainComponent()
            .inject(this)
    }

    private fun createNavHost(){
        Log.d("Test", "creating NAVHOST")
        navHost = MainNavHostFragment.create(
            R.navigation.main_nav_graph
        )
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.main_fragments_container,
                navHost,
                "MainNavHost"
            )
            .setPrimaryNavigationFragment(navHost)
            .commit()
    }


}
