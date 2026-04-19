package com.epsilon.apps.bilgi.yarismasi.quiz.ui.scene.quiz.content

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.epsilon.apps.bilgi.yarismasi.quiz.model.Question
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.cases.CheckQuestionAnswerCase
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.cases.QuizContentInteractionCase
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.cases.QuizQuestionTimerCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update

@Suppress("UNCHECKED_CAST")
@Composable
fun provideQuizContentViewModel(): QuizContentViewModel {
    return viewModel(factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return QuizContentViewModel(
                checkQuestionAnswerCase = CheckQuestionAnswerCase(),
                quizContentInteractionCase = QuizContentInteractionCase(),
                quizQuestionTimerCase = QuizQuestionTimerCase(quizCountdownTimer = QuizCountdownTimer())
            ) as T
        }
    })
}

class QuizContentViewModel(
    private val checkQuestionAnswerCase: CheckQuestionAnswerCase,
    private val quizContentInteractionCase: QuizContentInteractionCase,
    private val quizQuestionTimerCase: QuizQuestionTimerCase
) : ViewModel() {

    companion object {
        private const val QUESTION_DURATION_SECONDS = 15
    }

    private val mOptionItems = MutableStateFlow<List<QuizContentOptionUi>>(emptyList())
    val optionItems: StateFlow<List<QuizContentOptionUi>> = mOptionItems.asStateFlow()

    private val mRemainingSeconds = MutableStateFlow(QUESTION_DURATION_SECONDS)
    val remainingSeconds: StateFlow<Int> = mRemainingSeconds.asStateFlow()

    private val mAnswerResults = MutableSharedFlow<QuizContentAnswerResult>(extraBufferCapacity = 1)
    val answerResults: SharedFlow<QuizContentAnswerResult> = mAnswerResults.asSharedFlow()

    private var timerJob: Job? = null
    private var currentQuestionId: String? = null
    private var isCurrentQuestionResolved = false

    fun bindQuestion(question: Question?) {
        stopTimer()
        currentQuestionId = question?.id
        isCurrentQuestionResolved = false
        mRemainingSeconds.value = QUESTION_DURATION_SECONDS

        if (question == null) {
            mOptionItems.value = emptyList()
            return
        }
        mOptionItems.value = quizContentInteractionCase.buildNeutralOptions(question)
    }

    fun onQuestionStarted(question: Question) {
        if (currentQuestionId != question.id) {
            currentQuestionId = question.id
            isCurrentQuestionResolved = false
        }
        if (isCurrentQuestionResolved) return
        if (timerJob?.isActive == true) return

        timerJob = quizQuestionTimerCase.startTimer(
            scope = viewModelScope,
            totalSeconds = QUESTION_DURATION_SECONDS,
            onTick = { seconds ->
                mRemainingSeconds.value = seconds
            },
            onTimeout = {
                onQuestionUnanswered()
            }
        )
    }

    fun onOptionClicked(question: Question, optionKey: String) {
        if (isCurrentQuestionResolved) return

        val currentOptions = mOptionItems.value
        val selectedOption = currentOptions.firstOrNull { it.key == optionKey } ?: return
        if (!selectedOption.isClickable) return

        val isCorrect = checkQuestionAnswerCase.isCorrect(
            question = question,
            selectedOptionKey = optionKey
        )

        isCurrentQuestionResolved = true
        stopTimer()

        mOptionItems.value = quizContentInteractionCase.buildAnsweredOptions(
            optionItems = currentOptions,
            selectedOptionKey = optionKey,
            isCorrect = isCorrect
        )

        mAnswerResults.tryEmit(
            QuizContentAnswerResult(
                isCorrect = isCorrect,
                remainingSeconds = mRemainingSeconds.value
            )
        )
    }

    fun onScreenExitedWithoutAnswer() {
        if (isCurrentQuestionResolved) return
        if (currentQuestionId == null) return
        onQuestionUnanswered()
    }

    private fun onQuestionUnanswered() {
        if (isCurrentQuestionResolved) return

        isCurrentQuestionResolved = true
        stopTimer()
        mRemainingSeconds.value = 0
        mOptionItems.update { items ->
            items.map { option -> option.copy(isClickable = false) }
        }
        mAnswerResults.tryEmit(
            QuizContentAnswerResult(
                isCorrect = null,
                remainingSeconds = 0
            )
        )
    }

    private fun stopTimer() {
        timerJob?.cancel()
        timerJob = null
    }

    override fun onCleared() {
        stopTimer()
        super.onCleared()
    }
}
