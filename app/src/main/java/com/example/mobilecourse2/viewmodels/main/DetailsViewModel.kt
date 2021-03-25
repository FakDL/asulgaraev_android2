package com.example.mobilecourse2.viewmodels.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilecourse2.domain.interfaces.FilmRepository
import com.example.mobilecourse2.model.Film
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    private val filmRepository: FilmRepository
): ViewModel() {

    private val _viewState = MutableLiveData<DetailsFetchingViewState>()
    val viewState : LiveData<DetailsFetchingViewState> get() = _viewState

    fun setFilmDetails(id: String) {
        _viewState.value = DetailsFetchingViewState.Loading
        viewModelScope.launch {
            val film = async{filmRepository.getFilm(id)}
            _viewState.postValue(DetailsFetchingViewState.Success(film.await()))
        }
    }

}
sealed class DetailsFetchingViewState {
    object Loading: DetailsFetchingViewState()
    data class FetchingFailed(val errorType: Int): DetailsFetchingViewState()
    data class Success(val film: Film): DetailsFetchingViewState()
}