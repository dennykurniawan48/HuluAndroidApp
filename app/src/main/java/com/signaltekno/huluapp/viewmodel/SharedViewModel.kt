package com.signaltekno.huluapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.signaltekno.huluapp.model.NetworkResult
import com.signaltekno.huluapp.model.ResponseMovie
import com.signaltekno.huluapp.repository.DatastoreRepository
import com.signaltekno.huluapp.repository.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(private val datastoreRepository: DatastoreRepository, private val repository: NetworkRepository): ViewModel() {
    val onboard = datastoreRepository.flowBoard
    val dataMovie = MutableLiveData<NetworkResult<ResponseMovie>>(NetworkResult.Idle())

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

    fun setLoading(){
        dataMovie.value = NetworkResult.Loading()
    }
}