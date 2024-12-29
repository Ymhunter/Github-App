package com.example.github_app.Repo

import com.example.github_app.Commen.Repositories

sealed class UserRepoState {
    object Loading : UserRepoState()
    data class Success(val userRepoDetails: List<Repositories>) : UserRepoState()
    object Failure : UserRepoState()
}
