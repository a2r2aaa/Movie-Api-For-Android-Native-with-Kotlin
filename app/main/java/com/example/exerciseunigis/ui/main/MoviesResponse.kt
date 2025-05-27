package com.example.apiRest.ui.main

data class MoviesResponse(
    val results: List<Movie>
)

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val poster_path: String,
    val original_language: String,
    val release_date: String,
)
