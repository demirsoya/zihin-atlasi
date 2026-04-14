package com.epsilon.apps.bilgi.yarismasi.quiz.ui.scene.initialloading

import android.content.res.AssetManager
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import com.epsilon.apps.bilgi.yarismasi.quiz.room.AppDatabase
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.scene.initialloading.cases.LoadQuestionsCase
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@Suppress("UNCHECKED_CAST")
@Composable
fun provideInitialLoadingViewModel(
    appDatabase: AppDatabase,
    assetManager: AssetManager
): InitialLoadingViewModel {
    return viewModel(factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T = InitialLoadingViewModel(
            loadQuestionsCase = LoadQuestionsCase(
                assetManager = assetManager,
                appDatabase = appDatabase
            )
        ) as T
    })
}

class InitialLoadingViewModel(
    private val loadQuestionsCase: LoadQuestionsCase
) : ViewModel() {

    sealed class InitialLoadingUiState {
        data class Loaded(
            val a: Int
        ) : InitialLoadingUiState()

        data object Loading : InitialLoadingUiState()
        data object Error : InitialLoadingUiState()
    }

    private val mUiState = MutableStateFlow<InitialLoadingUiState>(InitialLoadingUiState.Loading)
    val initialLoadingUiState: StateFlow<InitialLoadingUiState> = mUiState.asStateFlow()

    init {
        viewModelScope.launch {
            runCatching {
                loadQuestionsCase.execute()
            }.onSuccess {
                mUiState.value = InitialLoadingUiState.Loaded(a = 1)
            }.onFailure {
                mUiState.value = InitialLoadingUiState.Error
            }
        }
    }
}