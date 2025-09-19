package com.example.task_lko.presentation.model

import com.example.task_lko.domain.model.UserProfile

sealed class ProfileUiState {
    object Loading : ProfileUiState()
    data class Success(val profile: UserProfile) : ProfileUiState()
    data class Error(val message: String) : ProfileUiState()
}