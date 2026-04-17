package com.epsilon.apps.bilgi.yarismasi.quiz.ui.scene.quiz

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.epsilon.apps.bilgi.yarismasi.quiz.model.Question
import com.epsilon.apps.bilgi.yarismasi.quiz.model.QuestionCategoryEnum
import com.epsilon.apps.bilgi.yarismasi.quiz.room.AppDatabase
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.cases.QuizQuestionCase
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.cases.UserProgressCase
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.scene.quiz.content.QuizContentAnswerResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Suppress("UNCHECKED_CAST")
@Composable
fun provideStoryModeQuizViewModel(
    appDatabase: AppDatabase
): StoryModeQuizViewModel {
    return viewModel(factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            StoryModeQuizViewModel(
                quizQuestionCase = QuizQuestionCase(appDatabase = appDatabase),
                userProgressCase = UserProgressCase(appDatabase = appDatabase)
            ) as T
    })
}

class StoryModeQuizViewModel(
    private val quizQuestionCase: QuizQuestionCase,
    private val userProgressCase: UserProgressCase
) : ViewModel() {

    companion object {
        private const val MAX_STORY_QUESTIONS = 12
    }

    sealed class StoryModeQuizUiState {
        data class Loaded(
            val currentQuestion: Question?,
            val currentCategory: QuestionCategoryEnum?,
            val isQuestionVisible: Boolean,
            val currentQuestionNumber: Int,
            val totalQuestionCount: Int,
            val isCompleted: Boolean,
            val showLevelCompletedPopup: Boolean
        ) : StoryModeQuizUiState()

        data object Loading : StoryModeQuizUiState()
        data object Error : StoryModeQuizUiState()
    }

    private val mUiState = MutableStateFlow<StoryModeQuizUiState>(StoryModeQuizUiState.Loading)
    val quizUiState: StateFlow<StoryModeQuizUiState> = mUiState.asStateFlow()

    private val mNavigateToHome = MutableStateFlow(false)
    val navigateToHome: StateFlow<Boolean> = mNavigateToHome.asStateFlow()

    private var isLoaded = false
    private var isLoading = false
    private var isAnswerTransitionInProgress = false

    private var storyQuestions: List<Question> = emptyList()
    private var currentQuestionIndex = 0
    private val usedQuestionIds = mutableSetOf<String>()

    fun loadQuizIfNeeded() {
        if (isLoaded || isLoading) return
        isLoading = true

        viewModelScope.launch {
            runCatching {
                withContext(Dispatchers.IO) {
                    quizQuestionCase.prepareAndGetActiveQuestions()
                }
            }.onSuccess { questions ->
                storyQuestions = questions.take(MAX_STORY_QUESTIONS)
                currentQuestionIndex = storyQuestions.indexOfFirst { !it.usedBefore }.let { index ->
                    if (index >= 0) index else 0
                }
                usedQuestionIds.clear()

                val firstQuestion = storyQuestions.getOrNull(currentQuestionIndex)
                if (firstQuestion == null) {
                    mUiState.value = StoryModeQuizUiState.Error
                    isLoading = false
                    return@onSuccess
                }

                mUiState.value = StoryModeQuizUiState.Loaded(
                    currentQuestion = firstQuestion,
                    currentCategory = resolveCategory(firstQuestion.categoryId),
                    isQuestionVisible = false,
                    currentQuestionNumber = currentQuestionIndex + 1,
                    totalQuestionCount = storyQuestions.size,
                    isCompleted = false,
                    showLevelCompletedPopup = false
                )
                isLoaded = true
                isLoading = false
            }.onFailure {
                mUiState.value = StoryModeQuizUiState.Error
                isLoading = false
            }
        }
    }

    private fun resolveCategory(categoryId: Int): QuestionCategoryEnum? {
        return QuestionCategoryEnum.entries.firstOrNull { it.id == categoryId }
    }

    fun onStartClicked() {
        val loadedState = mUiState.value as? StoryModeQuizUiState.Loaded ?: return
        val currentQuestion = loadedState.currentQuestion ?: return
        if (loadedState.isQuestionVisible) return
        mUiState.value = loadedState.copy(isQuestionVisible = true)
        viewModelScope.launch {
            markQuestionAsUsed(currentQuestion.id)
        }
    }

    fun onQuizContentAnswered(answerResult: QuizContentAnswerResult) {
        val loadedState = mUiState.value as? StoryModeQuizUiState.Loaded ?: return
        if (!loadedState.isQuestionVisible || loadedState.isCompleted || isAnswerTransitionInProgress) return

        isAnswerTransitionInProgress = true

        viewModelScope.launch {
            val transitionDelay = if (answerResult.isCorrect) 450L else 450L
            delay(transitionDelay)

            val isLastQuestion = currentQuestionIndex >= storyQuestions.lastIndex
            if (isLastQuestion) {
                val stateAfterAnswer = mUiState.value as? StoryModeQuizUiState.Loaded ?: return@launch
                mUiState.value = stateAfterAnswer.copy(
                    isCompleted = true,
                    showLevelCompletedPopup = true
                )
                isAnswerTransitionInProgress = false
                return@launch
            }

            currentQuestionIndex += 1
            val nextQuestion = storyQuestions.getOrNull(currentQuestionIndex) ?: run {
                val stateAfterAnswer = mUiState.value as? StoryModeQuizUiState.Loaded
                if (stateAfterAnswer != null) {
                    mUiState.value = stateAfterAnswer.copy(
                        isCompleted = true,
                        showLevelCompletedPopup = true
                    )
                }
                isAnswerTransitionInProgress = false
                return@launch
            }
            mUiState.value = StoryModeQuizUiState.Loaded(
                currentQuestion = nextQuestion,
                currentCategory = resolveCategory(nextQuestion.categoryId),
                isQuestionVisible = false,
                currentQuestionNumber = currentQuestionIndex + 1,
                totalQuestionCount = storyQuestions.size,
                isCompleted = false,
                showLevelCompletedPopup = false
            )
            isAnswerTransitionInProgress = false
        }
    }

    fun onLevelCompletedDismissRequested() {
        val loadedState = mUiState.value as? StoryModeQuizUiState.Loaded ?: return
        mUiState.value = loadedState.copy(showLevelCompletedPopup = false)
    }

    fun onLevelCompletedPrimaryAction() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                userProgressCase.advanceToNextLevel()
            }
            mNavigateToHome.value = true
        }
    }

    fun onNavigateToHomeHandled() {
        mNavigateToHome.value = false
    }

    private suspend fun markQuestionAsUsed(questionId: String) {
        if (usedQuestionIds.contains(questionId)) return
        withContext(Dispatchers.IO) {
            quizQuestionCase.markActiveQuestionAsUsed(questionId)
        }
        usedQuestionIds.add(questionId)
    }
}
