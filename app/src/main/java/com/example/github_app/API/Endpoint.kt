package com.example.github_app.API

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Endpoint {
    @GET("/users/{user}")
    fun getDetail(@Path("user") user: String): Call<UserDTO>

    @GET("search/repositories")
    fun searchRepositoriesLanguage(@Query("q") query: String): Call<LanguageDTO>

    @GET("/users/{user}/repos")
    fun getRepos(@Path("user") user: String): Call<List<ReposDTOItem>>

    @GET("/users/{user}/followers")
    fun getFollowers(@Path("user") user: String): Call<List<FollowersDTOItem>>

    @GET("/users/{user}/following")
    fun getFollowing(@Path("user") user: String): Call<List<FollowingDTOItem>>
}