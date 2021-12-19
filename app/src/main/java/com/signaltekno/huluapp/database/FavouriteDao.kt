package com.signaltekno.huluapp.database

import androidx.room.*
import com.signaltekno.huluapp.model.DetailMovie
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteDao {
    @Query("SELECT * from favourites")
    fun getAll(): Flow<List<DetailMovie>>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFav(newFav: DetailMovie)
    @Delete
    suspend fun deleteFav(fav: DetailMovie)
    @Query("DELETE FROM favourites WHERE id = :id")
    suspend fun deleteFavById(id: Long)
    @Query("SELECT * from favourites WHERE id = :id")
    fun getById(id: Long): Flow<DetailMovie>
}