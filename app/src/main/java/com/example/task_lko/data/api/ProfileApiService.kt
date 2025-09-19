package com.example.task_lko.data.api

import com.example.task_lko.data.model.User
import retrofit2.Response
import retrofit2.http.GET

interface ProfileApiService {
    @GET("data.json")
    suspend fun getProfile(): Response<User>
}