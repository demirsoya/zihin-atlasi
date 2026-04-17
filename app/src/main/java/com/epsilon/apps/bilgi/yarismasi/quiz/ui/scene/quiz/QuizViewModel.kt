package com.epsilon.apps.bilgi.yarismasi.quiz.ui.scene.quiz

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.epsilon.apps.bilgi.yarismasi.quiz.model.Question
import com.epsilon.apps.bilgi.yarismasi.quiz.room.AppDatabase
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.cases.QuizQuestionCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Suppress("UNCHECKED_CAST")
@Composable
fun provideQuizViewModel(
    appDatabase: AppDatabase
): QuizViewModel {
    return viewModel(factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            QuizViewModel(
                quizQuestionCase = QuizQuestionCase(appDatabase = appDatabase)
            ) as T
    })
}

class QuizViewModel(
    private val quizQuestionCase: QuizQuestionCase
) : ViewModel() {

    sealed class QuizUiState {
        data class Loaded(
            val currentQuestion: Question?,
            val isQuestionVisible: Boolean
        ) : QuizUiState()

        data object Loading : QuizUiState()
        data object Error : QuizUiState()
    }

    private val mUiState = MutableStateFlow<QuizUiState>(QuizUiState.Loading)
    val quizUiState: StateFlow<QuizUiState> = mUiState.asStateFlow()

    private var isLoaded = false
    private var isLoading = false

    fun loadQuizIfNeeded() {
        if (isLoaded || isLoading) return
        isLoading = true

        viewModelScope.launch {
            runCatching {
                withContext(Dispatchers.IO) {
                    quizQuestionCase.prepareAndGetActiveQuestions()
                }
            }.onSuccess { questions ->
                mUiState.value = QuizUiState.Loaded(
                    currentQuestion = questions.firstOrNull { !it.usedBefore } ?: questions.firstOrNull(),
                    isQuestionVisible = false
                )
                isLoaded = true
                isLoading = false
            }.onFailure {
                mUiState.value = QuizUiState.Error
                isLoading = false
            }
        }
    }

    fun onStartClicked() {
        val loadedState = mUiState.value as? QuizUiState.Loaded ?: return
        if (loadedState.isQuestionVisible) return
        mUiState.value = loadedState.copy(isQuestionVisible = true)
    }
}
