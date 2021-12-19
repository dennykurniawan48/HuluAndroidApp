package com.signaltekno.huluapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "favourites")
data class DetailMovie(
    @PrimaryKey
    val id: Long=0L,
    val adult: Boolean? = false,
    val backdrop_path: String? = null,
    val first_air_date: String? = null,
    val name: String? = null,
    val overview: String,
    val poster_path: String? = null,
    val release_date: String? = null,
    val title: String? = null,
    val vote_count: Int
)