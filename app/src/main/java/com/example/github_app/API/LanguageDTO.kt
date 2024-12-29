package com.example.github_app.API

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LanguageDTO(
    @Json(name = "incomplete_results")
    val incompleteResults: Boolean? = false,
    @Json(name = "items")
    val items: List<Item?>? = listOf(),
    @Json(name = "total_count")
    val totalCount: Int? = 0
)
