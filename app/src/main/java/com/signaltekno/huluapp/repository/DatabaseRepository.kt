package com.signaltekno.huluapp.repository

import com.signaltekno.huluapp.database.FavouriteDao
import com.signaltekno.huluapp.model.DetailMovie
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DatabaseRepository {
    @ViewModelScoped
    class DatabaseRepository @Inject constructor(private val diagnosisDao: FavouriteDao) {
        val readAllData : Flow<List<DetailMovie>> =  diagnosisDao.getAll()
        suspend fun addData(detail: DetailMovie){
            diagnosisDao.addFav(detail)
        }

    }
}