package com.signaltekno.huluapp.network

import com.signaltekno.huluapp.model.NetworkResult
import com.signaltekno.huluapp.model.ResponseMovie

interface AllService {
    suspend fun getAllMovie(url: String): NetworkResult<ResponseMovie>
    suspend fun getSearch(query: String): NetworkResult<ResponseMovie>
}