package com.example.mobilecourse2.ui.main.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.RequestManager
import com.example.mobilecourse2.DownloadImageHelper
import com.example.mobilecourse2.R
import com.example.mobilecourse2.viewmodels.main.DetailsFetchingViewState
import com.example.mobilecourse2.viewmodels.main.DetailsViewModel
import kotlinx.android.synthetic.main.fragment_details.*
import javax.inject.Inject

class DetailsFragment
@Inject constructor(
    private val viewModelFactory: ViewModelProvider.Factory,
    requestManager: RequestManager
): Fragment() {

    private val downloadImageHelper = DownloadImageHelper(requestManager)


    private val viewModel: DetailsViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        val args: DetailsFragmentArgs by navArgs()
        val filmId = args.filmId
        Log.d("Test","in details - $filmId")
        viewModel.setFilmDetails(filmId)
    }

    private fun setupObservers() {
        viewModel.viewState.observe(viewLifecycleOwner, {state ->
            when(state){
                is DetailsFetchingViewState.Success -> {
                    progress_bar.isVisible = false
                    tv_year.isVisible = true
                    iv_poster.isVisible = true
                    tv_title.isVisible = true
                    val film = state.film
                    tv_title.text = film.title
                    tv_year.text = film.year.toString()
                    downloadImageHelper.setImage(iv_poster,film.img_url)
                }
                is DetailsFetchingViewState.Loading -> {
                    progress_bar.isVisible = true
                    tv_year.isVisible = false
                    iv_poster.isVisible = false
                    tv_title.isVisible = false
                }
            }
        })
    }
}