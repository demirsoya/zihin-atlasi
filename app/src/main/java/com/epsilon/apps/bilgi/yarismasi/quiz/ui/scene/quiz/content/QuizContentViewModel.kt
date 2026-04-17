package com.epsilon.apps.bilgi.yarismasi.quiz.ui.scene.quiz.content

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.epsilon.apps.bilgi.yarismasi.quiz.model.Question
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.cases.CheckQuestionAnswerCase
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.cases.QuizContentInteractionCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@Suppress("UNCHECKED_CAST")
@Composable
fun provideQuizContentViewModel(): QuizContentViewModel {
    return viewModel(factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return QuizContentViewModel(
                checkQuestionAnswerCase = CheckQuestionAnswerCase(),
                quizContentInteractionCase = QuizContentInteractionCase()
            ) as T
        }
    })
}

class QuizContentViewModel(
    private val checkQuestionAnswerCase: CheckQuestionAnswerCase,
    private val quizContentInteractionCase: QuizContentInteractionCase
) : ViewModel() {

    private val mOptionItems = MutableStateFlow<List<QuizContentOptionUi>>(emptyList())
    val optionItems: StateFlow<List<QuizContentOptionUi>> = mOptionItems.asStateFlow()

    fun bindQuestion(question: Question?) {
        if (question == null) {
            mOptionItems.value = emptyList()
            return
        }
        mOptionItems.value = quizContentInteractionCase.buildNeutralOptions(question)
    }

    fun onOptionClicked(question: Question, optionKey: String): QuizContentAnswerResult? {
        val currentOptions = mOptionItems.value
        val selectedOption = currentOptions.firstOrNull { it.key == optionKey } ?: return null
        if (!selectedOption.isClickable) return null

        val isCorrect = checkQuestionAnswerCase.isCorrect(
            question = question,
            selectedOptionKey = optionKey
        )

        mOptionItems.value = quizContentInteractionCase.buildAnsweredOptions(
            optionItems = currentOptions,
            selectedOptionKey = optionKey,
            isCorrect = isCorrect
        )

        return QuizContentAnswerResult(isCorrect = isCorrect)
    }
}
