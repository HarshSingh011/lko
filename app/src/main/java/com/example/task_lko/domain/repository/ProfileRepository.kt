package com.example.task_lko.domain.repository

import com.example.task_lko.domain.model.UserProfile

interface ProfileRepository {
    suspend fun getProfile(): Result<UserProfile>
}