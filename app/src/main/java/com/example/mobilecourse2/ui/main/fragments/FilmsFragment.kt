package com.example.mobilecourse2.ui.main.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.RequestManager
import com.example.mobilecourse2.DownloadImageHelper
import com.example.mobilecourse2.R
import com.example.mobilecourse2.ui.main.MainActivity
import com.example.mobilecourse2.viewmodels.main.FilmsViewModel
import com.example.mobilecourse2.viewmodels.main.ListFetchingViewState
import com.example.mobilecourse2.ui.main.recyclerview.FilmAdapter
import com.example.mobilecourse2.ui.main.recyclerview.loadstate.FilmLoadStateAdapter
import kotlinx.android.synthetic.main.fragment_films.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


class FilmsFragment
@Inject
constructor(
    private val viewModelFactory: ViewModelProvider.Factory,
    requestManager: RequestManager
) : Fragment() {

    private lateinit var adapter: FilmAdapter

    private val viewModel: FilmsViewModel by viewModels {
        viewModelFactory
    }

    private val downloadImageHelper = DownloadImageHelper(requestManager)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_films, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        setupAdapter()
        loadList()
        setupLayoutManager()
        setupObservers()
    }

    private fun loadList() {
        Log.d("Test", "Start")
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.fetchData().collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun setupLayoutManager() {
        rv_films.layoutManager =
            GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
    }

    private fun setListeners() {
        swipe_refresh.setOnRefreshListener {
            swipe_refresh.isRefreshing = true
            loadList()
            swipe_refresh.isRefreshing = false
        }
    }


    private fun setupObservers() {
        viewModel.viewState.observe(viewLifecycleOwner, { state ->
            when (state) {
                is ListFetchingViewState.Success -> {
                }
                is ListFetchingViewState.Loading -> {
                }
                is ListFetchingViewState.FetchingFailed -> {
                    Toast.makeText(activity, "Ошибка загрузки", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun setupAdapter() {
        val clickLambda: (String) -> Unit = { id ->
            Log.d("Test", "in fragm $id")
            val action = FilmsFragmentDirections.actionFilmsFragmentToDetailsFragment(id)
            (activity as MainActivity).navHost.navController.navigate(action)
        }
        adapter = FilmAdapter(clickLambda, downloadImageHelper)
        rv_films.adapter = adapter.withLoadStateFooter(
            footer = FilmLoadStateAdapter { adapter.retry() }
        )
    }


}