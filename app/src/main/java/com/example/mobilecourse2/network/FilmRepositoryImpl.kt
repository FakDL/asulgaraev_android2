package com.example.mobilecourse2.network

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.mobilecourse2.Constants
import com.example.mobilecourse2.domain.interfaces.FilmRepository
import com.example.mobilecourse2.model.Film
import com.example.mobilecourse2.ui.main.recyclerview.FilmPagingSource
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FilmRepositoryImpl @Inject constructor(
    private val filmService: FilmService
): FilmRepository {
    override suspend fun getPopularFilmsId(): List<String> {
        val list = filmService.getPopularFilmIdList()
        Log.d("Test", list[0])
        return list
    }
    override suspend fun getFilm(id: String): Film {
        val film =  filmService.getFilm(id).toFilm()
        Log.d("Test", film.toString())
        return film
    }

    override suspend fun getFilmStream(): Flow<PagingData<Film>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.NETWORK_PAGE_SIZE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = { FilmPagingSource(this) }
        ).flow
    }

    override suspend fun getFilms(startPoint: Int, size: Int): List<Film> {
        val list = mutableListOf<Film>()
        lateinit var def: Deferred<Boolean>
        getPopularFilmsId()
            .slice(IntRange(startPoint, startPoint + size))
            .forEach {
                def = GlobalScope.async { list.add(getFilm(it.substring(7)))
                }
            }
        def.await()
        return list
    }
}