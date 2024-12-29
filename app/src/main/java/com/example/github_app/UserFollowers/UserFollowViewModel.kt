package com.example.github_app.UserFollowers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.github_app.API.FollowersDTOItem
import com.example.github_app.API.RetrofitClient
import com.example.github_app.Commen.UserFollow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserFollowViewModel(private val userName: String) : ViewModel() {
    private val _state = MutableStateFlow<UserFollowState>(UserFollowState.Loading)
    val state: StateFlow<UserFollowState> = _state

    init {
        retrieveUserDetails(userName)
    }

    private fun retrieveUserDetails(userName: String) {
        headlines(userName).enqueue(object : Callback<List<FollowersDTOItem>> {
            override fun onResponse(call: Call<List<FollowersDTOItem>>, response: Response<List<FollowersDTOItem>>) {
                if (isSuccessful(response)) {
                    val follow = mapUserData(response.body()!!)
                    _state.value = UserFollowState.Success(userFollowDetails = follow)
                } else {
                    _state.value = UserFollowState.Failure
                }
            }

            override fun onFailure(call: Call<List<FollowersDTOItem>>, t: Throwable) {
                _state.value = UserFollowState.Failure
            }
        })
    }
    private fun isSuccessful(response: Response<List<FollowersDTOItem>>): Boolean {
        return response.isSuccessful && response.body() != null
    }

    private fun headlines(userName: String): Call<List<FollowersDTOItem>> {
        return RetrofitClient.instance.getFollowers(user = userName)
    }
    private fun mapUserData(userInfo: List<FollowersDTOItem>): List<UserFollow> {
        return userInfo.map { dto -> UserFollow(dto) }
    }

    companion object {
        fun factory(userName: String): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return UserFollowViewModel(userName) as T
                }
            }
        }
    }
}
