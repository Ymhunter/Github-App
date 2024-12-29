package com.example.github_app.UserFollowers

import com.example.github_app.Commen.UserFollow

sealed class UserFollowState {
    object Loading : UserFollowState()
    data class Success(val userFollowDetails: List<UserFollow>) : UserFollowState()
    object Failure : UserFollowState()
}
