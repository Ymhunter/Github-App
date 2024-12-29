package com.example.github_app.Repo

import com.example.github_app.Commen.Repositories

sealed class TopLangSate {

    object Loading : TopLangSate()
    data class Success(val userRepoDetails: List<Repositories>) : TopLangSate()
    object Failure : TopLangSate()
}
