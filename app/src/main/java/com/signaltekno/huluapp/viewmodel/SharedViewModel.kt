package com.signaltekno.huluapp.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.signaltekno.huluapp.database.FavouriteDao
import com.signaltekno.huluapp.model.DetailMovie
import com.signaltekno.huluapp.model.NetworkResult
import com.signaltekno.huluapp.model.ResponseMovie
import com.signaltekno.huluapp.repository.DatastoreRepository
import com.signaltekno.huluapp.repository.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(private val datastoreRepository: DatastoreRepository, private val repository: NetworkRepository, private val favouriteDao: FavouriteDao): ViewModel() {
    val onboard = datastoreRepository.flowBoard
    val dataMovie = MutableLiveData<NetworkResult<ResponseMovie>>(NetworkResult.Idle())
    val detail = mutableStateOf<DetailMovie?>(null)
    val dataSearch = MutableLiveData<NetworkResult<ResponseMovie>>(NetworkResult.Idle())
    var isFav by mutableStateOf(false)

    fun setFinishOnboard(){
        viewModelScope.launch {
            datastoreRepository.saveOnBoard()
        }
    }

    fun getAllMovie(url: String){
        dataMovie.value = NetworkResult.Loading()
        viewModelScope.launch {
            delay(300L)
            dataMovie.value = repository.getAllMovie(url)
        }
    }

    fun setDetail(detailMovie: DetailMovie){
        detail.value = detailMovie
        viewModelScope.launch {
            isFav = favouriteDao.isFav(detailMovie.id)
        }
    }

    fun addFavourite(detailMovie: DetailMovie){
        viewModelScope.launch {
            favouriteDao.addFav(detailMovie)
        }
        isFav = true
    }

    fun deleteFavourite(detailMovie: DetailMovie){
        viewModelScope.launch {
            favouriteDao.deleteFav(detailMovie)
        }
        isFav = false
    }

    fun doSearch(query: String){
        dataSearch.value = NetworkResult.Loading()
        viewModelScope.launch {
            delay(300)
            dataSearch.value = repository.getSearch(query)
        }
    }
}