package com.epsilon.apps.bilgi.yarismasi.quiz.ui.scene.initialloading

import android.content.res.AssetManager
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import com.epsilon.apps.bilgi.yarismasi.quiz.room.AppDatabase
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.cases.RawQuestionsCase
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.cases.UserCase
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.cases.UserProgressCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Suppress("UNCHECKED_CAST")
@Composable
fun provideInitialLoadingViewModel(
    appDatabase: AppDatabase,
    assetManager: AssetManager
): InitialLoadingViewModel {
    return viewModel(factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T =
            InitialLoadingViewModel(
                rawQuestionsCase = RawQuestionsCase(
                    assetManager = assetManager,
                    appDatabase = appDatabase
                ),
                userCase = UserCase(appDatabase = appDatabase),
                userProgressCase = UserProgressCase(appDatabase = appDatabase)
            ) as T
    })
}

class InitialLoadingViewModel(
    private val rawQuestionsCase: RawQuestionsCase,
    private val userCase: UserCase,
    private val userProgressCase: UserProgressCase
) : ViewModel() {

    companion object {
        private const val TAG = "InitialLoadingVM"
    }

    sealed class InitialLoadingUiState {
        data object Loaded : InitialLoadingUiState()

        data object Loading : InitialLoadingUiState()

        data class Error(
            val message: String
        ) : InitialLoadingUiState()
    }

    private val mUiState =
        MutableStateFlow<InitialLoadingUiState>(InitialLoadingUiState.Loading)
    val initialLoadingUiState: StateFlow<InitialLoadingUiState> = mUiState.asStateFlow()

    init {
        viewModelScope.launch {
            delay(1500)
            runCatching {
                withContext(Dispatchers.IO) {
                    userCase.createUser()
                    userProgressCase.createUserProgress()
                    rawQuestionsCase.loadNewQuestionsToDatabase()
                }
            }.onSuccess {
                mUiState.value = InitialLoadingUiState.Loaded
            }.onFailure { error ->
                Log.e(TAG, "Initial loading failed", error)
                mUiState.value = InitialLoadingUiState.Error(
                    message = "Yükleme başarısız oldu."
                )
            }
        }
    }
}