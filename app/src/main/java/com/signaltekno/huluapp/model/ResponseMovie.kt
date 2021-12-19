package com.signaltekno.huluapp.model

import kotlinx.serialization.Serializable

@Serializable
data class ResponseMovie(
    val page: Int,
    val results: List<DetailMovie>,
    val total_pages: Int,
    val total_results: Int
)