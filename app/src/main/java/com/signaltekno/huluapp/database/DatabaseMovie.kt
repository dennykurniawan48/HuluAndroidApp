package com.signaltekno.huluapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.signaltekno.huluapp.model.DetailMovie

@Database(entities = [DetailMovie::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun todoDao(): FavouriteDao
}