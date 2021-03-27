package com.example.mobilecourse2.domain.interfaces

import androidx.paging.PagingData
import com.example.mobilecourse2.model.Film
import kotlinx.coroutines.flow.Flow

interface FilmRepository {
    suspend fun getPopularFilmsId(): List<String>
    suspend fun getFilm(id: String): Film
    suspend fun getFilmStream(): Flow<PagingData<Film>>
    suspend fun getFilms(startPoint: Int, size: Int): List<Film>
}
