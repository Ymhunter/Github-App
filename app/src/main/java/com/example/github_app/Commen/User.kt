package com.example.github_app.Commen

import android.os.Parcelable
import com.example.github_app.API.UserDTO
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val name: String,
    val location: String,
    val followers: Int,
    val following: Int,
    val repo: Int,
    val blog: String,
    val hireable: Boolean,
    val avatar_url: String,
    val twitter: String,
    val company: String,
    val email: String,
) : Parcelable {
    constructor(dto: UserDTO) : this(
        name = dto.name ?: "",
        location = dto.location ?: "",
        followers = dto.followers ?: 0,
        following = dto.following ?: 0,
        repo = dto.publicRepos ?: 0,
        blog = dto.blog ?: "",
        hireable = dto.hireable ?: false,
        avatar_url = dto.avatarUrl ?: "",
        twitter = dto.twitterUsername ?: "",
        company = dto.company ?: "",
        email = dto.email ?: "",
    )
}
