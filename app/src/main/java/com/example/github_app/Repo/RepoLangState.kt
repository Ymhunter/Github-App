package com.example.github_app.Repo

import com.example.github_app.Commen.Repositories

sealed class RepoLangState {
    object Loading : RepoLangState()
    data class Success(val repoLangDetails: List<Repositories>) : RepoLangState()
    object Failure : RepoLangState()
}
