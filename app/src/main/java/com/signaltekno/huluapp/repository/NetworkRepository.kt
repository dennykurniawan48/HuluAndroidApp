package com.signaltekno.huluapp.repository

import com.signaltekno.huluapp.model.NetworkResult
import com.signaltekno.huluapp.model.ResponseMovie
import com.signaltekno.huluapp.network.AllServiceImpl
import dagger.hilt.android.scopes.ViewModelScoped
import io.ktor.client.*
import javax.inject.Inject

@ViewModelScoped
class NetworkRepository @Inject constructor(private val client: HttpClient) {
    suspend fun getAllMovie(url: String): NetworkResult<ResponseMovie>{
        return AllServiceImpl(client).getAllMovie(url)
    }
}