package com.signaltekno.huluapp.network

import android.util.Log
import com.signaltekno.huluapp.Constant
import com.signaltekno.huluapp.model.NetworkResult
import com.signaltekno.huluapp.model.ResponseMovie
import io.ktor.client.*
import io.ktor.client.request.*
import java.net.URL
import kotlin.text.get

class AllServiceImpl(private val client: HttpClient): AllService {
    override suspend fun getAllMovie(url: String): NetworkResult<ResponseMovie> {
        return try{
            val response: ResponseMovie = client.get{
                url(url)
            }
            NetworkResult.Success(response)
        }catch (e: Exception){
            Log.d("Error", e.toString())
            NetworkResult.Error(e.message)
        }
    }

    override suspend fun getSearch(query: String): NetworkResult<ResponseMovie> {
        return try{
            val response: ResponseMovie = client.get{
                url(Constant.URL_SEARCH + "&query=${query}")
            }
            NetworkResult.Success(response)
        }catch (e: Exception){
            Log.d("Error", e.toString())
            NetworkResult.Error(e.message)
        }
    }
}