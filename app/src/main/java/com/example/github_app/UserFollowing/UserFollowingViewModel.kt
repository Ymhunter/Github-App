package com.example.github_app.UserFollowing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.github_app.API.FollowingDTOItem
import com.example.github_app.API.RetrofitClient
import com.example.github_app.Commen.UserFollowing
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserFollowingViewModel(private val userName: String) : ViewModel() {
    private val _state = MutableStateFlow<UserFollowingState>(UserFollowingState.Loading)
    val state: StateFlow<UserFollowingState> = _state

    init {
        retrieveUserDetailFollowing(userName)
    }

    private fun retrieveUserDetailFollowing(userName: String) {
        headlinesFollowing(userName).enqueue(object : Callback<List<FollowingDTOItem>> {
            override fun onResponse(call: Call<List<FollowingDTOItem>>, response: Response<List<FollowingDTOItem>>) {
                if (isSuccessfulFollowing(response)) {
                    val follow = mapUserDataFollowing(response.body()!!)
                    _state.value = UserFollowingState.Success(userFollowingDetails = follow)
                } else {
                    _state.value = UserFollowingState.Failure
                }
            }

            override fun onFailure(call: Call<List<FollowingDTOItem>>, t: Throwable) {
                _state.value = UserFollowingState.Failure
            }
        })
    }
    private fun isSuccessfulFollowing(response: Response<List<FollowingDTOItem>>): Boolean {
        return response.isSuccessful && response.body() != null
    }

    private fun headlinesFollowing(userName: String): Call<List<FollowingDTOItem>> {
        return RetrofitClient.instance.getFollowing(user = userName)
    }
    private fun mapUserDataFollowing(userInfo: List<FollowingDTOItem>): List<UserFollowing> {
        return userInfo.map { dto -> UserFollowing(dto) }
    }

    companion object {
        fun factory(userName: String): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return UserFollowingViewModel(userName) as T
                }
            }
        }
    }
}
