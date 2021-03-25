package com.example.mobilecourse2.ui.main.recyclerview

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mobilecourse2.Constants
import com.example.mobilecourse2.domain.interfaces.FilmRepository
import com.example.mobilecourse2.model.Film
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import java.io.IOException

class FilmPagingSource (
    private val filmRepository: FilmRepository
):PagingSource<Int, Film>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Film> {
        val position = params.key ?: Constants.FILM_STARTING_PAGE_INDEX
        return try {
            val films = GlobalScope.async{
                filmRepository.getFilms(position, params.loadSize*2/3-1)
            }.await()
            val nextKey = if (films.isEmpty()) {
                null
            } else {
                position + params.loadSize*2/3 + 1
            }
            LoadResult.Page(
                data = films,
                prevKey = if (position == Constants.FILM_STARTING_PAGE_INDEX) null else position,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Film>): Int? {
        return state.anchorPosition
    }

}