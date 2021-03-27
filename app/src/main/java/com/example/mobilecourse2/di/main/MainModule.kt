package com.example.mobilecourse2.di.main


import android.app.Application
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.example.mobilecourse2.R
import com.example.mobilecourse2.domain.interfaces.FilmRepository
import com.example.mobilecourse2.factories.main.MainFragmentFactory
import com.example.mobilecourse2.glide.GlideApp
import com.example.mobilecourse2.network.FilmRepositoryImpl
import com.example.mobilecourse2.network.FilmService
import dagger.Module
import dagger.Provides

@Module
object MainModule {

    @MainScope
    @Provides
    fun provideFragmentFactory(
        viewModelFactory: ViewModelProvider.Factory,
        requestManager: RequestManager
    ): FragmentFactory {
        return MainFragmentFactory(
            viewModelFactory,
            requestManager
        )
    }


    @MainScope
    @Provides
    fun provideRequestOptions(): RequestOptions {
        return RequestOptions
            .placeholderOf(R.drawable.placeholder)
            .error(R.drawable.error)
    }

    @MainScope
    @Provides
    fun provideGlideInstance(
        application: Application,
        requestOptions: RequestOptions
    ): RequestManager {
        return GlideApp.with(application)
            .setDefaultRequestOptions(requestOptions)
    }
    @MainScope
    @Provides
    fun provideFilmRepo(
        filmService: FilmService
    ): FilmRepository {
        return FilmRepositoryImpl(filmService)
    }

}
