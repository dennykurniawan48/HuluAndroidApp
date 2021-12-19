package com.signaltekno.huluapp.dependency

import android.content.Context
import androidx.room.Room
import com.signaltekno.huluapp.database.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ): MovieDatabase {
        return Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            "movie_db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideDao(database: MovieDatabase) = database.todoDao()
}