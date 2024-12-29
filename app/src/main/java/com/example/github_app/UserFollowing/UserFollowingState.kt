package com.example.github_app.UserFollowing

import com.example.github_app.Commen.UserFollowing

sealed class UserFollowingState {
    object Loading : UserFollowingState()
    data class Success(val userFollowingDetails: List<UserFollowing>) : UserFollowingState()
    object Failure : UserFollowingState()
}
