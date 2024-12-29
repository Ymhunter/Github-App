package com.example.github_app.User

import com.example.github_app.Commen.Repositories
import com.example.github_app.Commen.User

sealed class DetailsState {
    object Loading : DetailsState()
    data class Success(val userDetails: User,) : DetailsState()
    data class SuccessRepo(val repoTopDetails: List<Repositories>) : DetailsState()
    object Failure : DetailsState()
}
