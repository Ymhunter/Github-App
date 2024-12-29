package com.example.github_app.Commen

import android.os.Parcelable
import com.example.github_app.API.FollowingDTOItem
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserFollowing(
    val name: String,
    val avatar_url: String,
) : Parcelable {
    constructor(dto: FollowingDTOItem) : this(
        name = dto.login ?: "",
        avatar_url = dto.avatarUrl ?: "",
    )
}
