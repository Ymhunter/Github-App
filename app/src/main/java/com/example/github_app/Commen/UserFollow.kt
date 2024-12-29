package com.example.github_app.Commen

import android.os.Parcelable
import com.example.github_app.API.FollowersDTOItem
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserFollow(
    val name: String,
    val avatar_url: String,
) : Parcelable {
    constructor(dto: FollowersDTOItem) : this(
        name = dto.login ?: "",
        avatar_url = dto.avatarUrl ?: "",
    )
}
