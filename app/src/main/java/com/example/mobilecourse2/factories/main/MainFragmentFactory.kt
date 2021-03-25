package com.example.mobilecourse2.factories.main

import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.RequestManager
import com.example.mobilecourse2.di.main.MainScope
import com.example.mobilecourse2.ui.main.fragments.DetailsFragment
import com.example.mobilecourse2.ui.main.fragments.FilmsFragment
import javax.inject.Inject

@MainScope
class MainFragmentFactory
@Inject
constructor(
    private val viewModelFactory: ViewModelProvider.Factory,
    private val requestManager: RequestManager
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String) =

        when (className) {

            FilmsFragment::class.java.name -> {
                FilmsFragment(viewModelFactory, requestManager)
            }

            DetailsFragment::class.java.name -> {
                DetailsFragment(viewModelFactory,requestManager)
            }

            else -> {
                FilmsFragment(viewModelFactory, requestManager)
            }
        }
}
