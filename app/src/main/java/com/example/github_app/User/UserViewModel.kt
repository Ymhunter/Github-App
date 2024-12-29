package com.example.github_app.User

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.github_app.API.ReposDTOItem
import com.example.github_app.API.RetrofitClient
import com.example.github_app.API.UserDTO
import com.example.github_app.Commen.Repositories
import com.example.github_app.Commen.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel(private val userName: String) : ViewModel() {
    private val _state = MutableStateFlow<DetailsState>(DetailsState.Loading)
    val state: StateFlow<DetailsState> = _state

    init {
        retrieveUserDetails(userName)
        retrieveRepoDetails(userName)
    }
    private fun isSuccessful(response: Response<UserDTO>): Boolean {
        return response.isSuccessful && response.body() != null && response.body()!!.login != null
    }

    private fun mapUserData(response: UserDTO) {
        val userData = User(response)
        _state.value = DetailsState.Success(userData)
    }

    private fun mapRepoData(repoInfo: List<ReposDTOItem>): List<Repositories> {
        return repoInfo.map { dto -> Repositories(dto) }
    }
    private fun isSuccessfulRepo(response: Response<List<ReposDTOItem>>): Boolean {
        return response.isSuccessful && response.body() != null
    }

    private fun retrieveRepoDetails(userName: String) {
        headlinesRepo(userName).enqueue(object : Callback<List<ReposDTOItem>> {
            override fun onResponse(
                call: Call<List<ReposDTOItem>>,
                response: Response<List<ReposDTOItem>>
            ) {
                if (isSuccessfulRepo(response)) {
                    val repo = mapRepoData(response.body()!!)
                    _state.value = DetailsState.SuccessRepo(
                        repo
                    )
                }
            }

            override fun onFailure(call: Call<List<ReposDTOItem>>, t: Throwable) {
                _state.value = DetailsState.Failure
            }
        })
    }

    private fun headlinesRepo(userName: String): Call<List<ReposDTOItem>> {
        return RetrofitClient.instance.getRepos(userName)
    }
    private fun retrieveUserDetails(userName: String) {
        headlines(userName).enqueue(object : Callback<UserDTO> {
            override fun onResponse(call: Call<UserDTO>, response: Response<UserDTO>) {
                if (isSuccessful(response)) {
                    mapUserData(response.body()!!)
                } else {
                    _state.value = DetailsState.Failure
                }
            }

            override fun onFailure(call: Call<UserDTO>, t: Throwable) {
                _state.value = DetailsState.Failure
            }
        })
    }
    private fun headlines(userName: String): Call<UserDTO> {
        return RetrofitClient.instance.getDetail(userName)
    }
    companion object {
        fun factory(user: String): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return UserViewModel(user) as T
                }
            }
        }
    }
}
