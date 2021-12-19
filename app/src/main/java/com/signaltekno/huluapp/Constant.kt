package com.signaltekno.huluapp

import com.signaltekno.huluapp.model.GenreData
import com.signaltekno.huluapp.navigation.Screen

object Constant {
    val API_KEY = ""
    val URL_ASSET = "https://image.tmdb.org/t/p/original"
    val LIST_GENRE = listOf(GenreData("Trending", "https://api.themoviedb.org/3/trending/all/week?api_key=${API_KEY}&language=en"),
        GenreData("Top Rated", "https://api.themoviedb.org/3/movie/top_rated?api_key=${API_KEY}&language=en"),
        GenreData("Action", "https://api.themoviedb.org/3/discover/movie?api_key=${API_KEY}&language=en-US&with_genres=28"),
        GenreData("Comedy", "https://api.themoviedb.org/3/discover/movie?api_key=${API_KEY}&language=en-US&with_genres=35"),
        GenreData("Horror", "https://api.themoviedb.org/3/discover/movie?api_key=${API_KEY}&language=en-US&with_genres=27"),
        GenreData("Romance", "https://api.themoviedb.org/3/discover/movie?api_key=${API_KEY}&language=en-US&with_genres=10749"),
        GenreData("Sci-fi", "https://api.themoviedb.org/3/discover/movie?api_key=${API_KEY}&language=en-US&with_genres=878"),
        GenreData("Mystery", "https://api.themoviedb.org/3/discover/movie?api_key=${API_KEY}&language=en-US&with_genres=9648"),
        GenreData("Adventure", "https://api.themoviedb.org/3/discover/movie?api_key=ce13b578f8c52b4310e95a8f28255cd5&language=en-US&with_genres=12"),
    )
}