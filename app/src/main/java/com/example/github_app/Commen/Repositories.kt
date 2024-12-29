package com.example.github_app.Commen

import android.os.Parcelable
import com.example.github_app.API.Item
import com.example.github_app.API.ReposDTOItem
import kotlinx.parcelize.Parcelize

@Parcelize
data class Repositories(
    val name: String,
    val description: String,
    val language: String,
    val date: String,
    val license: String
) : Parcelable {
    constructor(dto: ReposDTOItem) : this(
        name = dto.name ?: "",
        description = dto.description ?: "",
        language = dto.language ?: "",
        date = dto.createdAt?.substringBeforeLast("T") ?: "",
        license = dto.license.toString().substringAfter("name=").substringBeforeLast("node") ?: "",
    )
    constructor(dto: Item) : this(
        name = dto.name ?: "",
        description = dto.description ?: "",
        language = dto.language ?: "",
        date = dto.createdAt?.substringBeforeLast("T") ?: "",
        license = dto.license.toString().substringAfter("name=").substringBeforeLast("node") ?: "",
    )
}
