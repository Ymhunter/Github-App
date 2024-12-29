package com.example.github_app.Repo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.github_app.API.Item
import com.example.github_app.API.LanguageDTO
import com.example.github_app.API.ReposDTOItem
import com.example.github_app.API.RetrofitClient
import com.example.github_app.Commen.Repositories
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepoViewModel(private val userName: String) : ViewModel() {
    private val _state = MutableStateFlow<UserRepoState>(UserRepoState.Loading)
    val state: StateFlow<UserRepoState> = _state

    private val _stateLang = MutableStateFlow<RepoLangState>(RepoLangState.Loading)
    val stateLang: StateFlow<RepoLangState> = _stateLang

    init {
        if (userName.last() == '=') {
            retrieveRepoLangDetails(userName.replace("=", ""))
        } else {
            retrieveRepoDetails(userName)
        }
    }

    private fun repoLang(items: List<Item?>): List<Repositories> {
        return items.map { dto -> Repositories(dto!!) }
    }

    private fun isSuccessfulLang(response: Response<LanguageDTO>): Boolean {
        return response.isSuccessful && response.body() != null
    }

    private fun retrieveRepoLangDetails(userName: String) {
        headlinesLang(userName).enqueue(object : Callback<LanguageDTO> {
            override fun onResponse(call: Call<LanguageDTO>, response: Response<LanguageDTO>) {
                if (isSuccessfulLang(response)) {
                    val repoLang = repoLang(response.body()!!.items!!)
                    _stateLang.value = RepoLangState.Success(
                        repoLangDetails = repoLang
                    )
                }
            }

            override fun onFailure(call: Call<LanguageDTO>, t: Throwable) {
                _stateLang.value = RepoLangState.Failure
            }
        })
    }

    private fun headlinesLang(userName: String): Call<LanguageDTO> {
        return RetrofitClient.instance.searchRepositoriesLanguage(query = userName)
    }

    private fun retrieveRepoDetails(userName: String) {
        headlines(userName).enqueue(object : Callback<List<ReposDTOItem>> {
            override fun onResponse(
                call: Call<List<ReposDTOItem>>,
                response: Response<List<ReposDTOItem>>
            ) {
                if (isSuccessful(response)) {
                    val repo = mapRepoData(response.body()!!)
                    _state.value = UserRepoState.Success(
                        userRepoDetails = repo
                    )
                }
            }

            override fun onFailure(call: Call<List<ReposDTOItem>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun isSuccessful(response: Response<List<ReposDTOItem>>): Boolean {
        return response.isSuccessful && response.body() != null
    }

    private fun headlines(userName: String): Call<List<ReposDTOItem>> {
        return RetrofitClient.instance.getRepos(user = userName)
    }

    private fun mapRepoData(repoInfo: List<ReposDTOItem>): List<Repositories> {
        return repoInfo.map { dto -> Repositories(dto) }
    }

    companion object {
        fun factory(userName: String): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return RepoViewModel(userName) as T
                }
            }
        }
    }
}
