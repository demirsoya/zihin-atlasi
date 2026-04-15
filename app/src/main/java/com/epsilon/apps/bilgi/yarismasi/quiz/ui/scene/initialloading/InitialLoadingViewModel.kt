package com.epsilon.apps.bilgi.yarismasi.quiz.ui.scene.initialloading

import android.content.res.AssetManager
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import com.epsilon.apps.bilgi.yarismasi.quiz.room.AppDatabase
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.cases.RawQuestionsCase
import kotlinx.coroutines.delay
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
            rawQuestionsCase = RawQuestionsCase(
                assetManager = assetManager,
                appDatabase = appDatabase
            )
        ) as T
    })
}

class InitialLoadingViewModel(
    private val rawQuestionsCase: RawQuestionsCase
) : ViewModel() {

    sealed class InitialLoadingUiState {
        data class Loaded(
            val progress: Float
        ) : InitialLoadingUiState()

        data class Loading(
            val progress: Float
        ) : InitialLoadingUiState()

        data object Error : InitialLoadingUiState()
    }

    private val mUiState = MutableStateFlow<InitialLoadingUiState>(InitialLoadingUiState.Loading(progress = 0f))
    val initialLoadingUiState: StateFlow<InitialLoadingUiState> = mUiState.asStateFlow()

    init {
        viewModelScope.launch {
            runCatching {
                //TODO Remove delay
                delay(3000)
                rawQuestionsCase.loadNewQuestionsToDatabase { processedQuestions, totalQuestions ->
                    val progress = if (totalQuestions <= 0) 1f else processedQuestions.toFloat() / totalQuestions.toFloat()
                    mUiState.value = InitialLoadingUiState.Loading(progress = progress.coerceIn(0f, 1f))
                }
            }.onSuccess {
                mUiState.value = InitialLoadingUiState.Loaded(progress = 1f)
            }.onFailure {
                mUiState.value = InitialLoadingUiState.Error
            }
        }
    }
}