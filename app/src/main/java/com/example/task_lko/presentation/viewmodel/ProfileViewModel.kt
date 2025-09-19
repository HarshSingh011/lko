package com.example.task_lko.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task_lko.domain.usecase.GetProfileUseCase
import com.example.task_lko.presentation.model.ProfileUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        loadProfile()
    }

    fun loadProfile() {
        viewModelScope.launch {
            _uiState.value = ProfileUiState.Loading
            getProfileUseCase().fold(
                onSuccess = { profile ->
                    _uiState.value = ProfileUiState.Success(profile)
                },
                onFailure = { exception ->
                    _uiState.value = ProfileUiState.Error(
                        exception.message ?: "Unknown error occurred"
                    )
                }
            )
        }
    }

    fun retry() {
        loadProfile()
    }
}