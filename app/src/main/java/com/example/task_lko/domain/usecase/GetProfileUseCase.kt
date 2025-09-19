package com.example.task_lko.domain.usecase

import com.example.task_lko.domain.model.UserProfile
import com.example.task_lko.domain.repository.ProfileRepository
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(): Result<UserProfile> {
        return repository.getProfile()
    }
}