package com.epsilon.apps.bilgi.yarismasi.quiz.ui.scene.quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsIgnoringVisibility
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.epsilon.apps.bilgi.yarismasi.quiz.R
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.scene.quiz.content.QuizContent
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.scene.quiz.content.QuizContentViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun QuizScene(
    viewModel: StoryModeQuizViewModel,
    quizContentViewModel: QuizContentViewModel,
    edgeToEdgePadding: PaddingValues
) {
    val uiState = viewModel.quizUiState.collectAsStateWithLifecycle().value
    val optionItems = quizContentViewModel.optionItems.collectAsStateWithLifecycle().value
    val activeQuestion = (uiState as? StoryModeQuizViewModel.StoryModeQuizUiState.Loaded)?.currentQuestion
    val statusBarTopPadding =
        WindowInsets.statusBarsIgnoringVisibility.asPaddingValues().calculateTopPadding()

    LaunchedEffect(viewModel) {
        viewModel.loadQuizIfNeeded()
    }

    LaunchedEffect(activeQuestion?.id) {
        quizContentViewModel.bindQuestion(activeQuestion)
    }

    Box(
        modifier = Modifier
            .padding(edgeToEdgePadding)
            .fillMaxSize()
            .background(color = colorResource(id = R.color.app_white))
    ) {
        when (uiState) {
            StoryModeQuizViewModel.StoryModeQuizUiState.Error -> {
                QuizContent(
                    questionText = null,
                    optionItems = emptyList(),
                    isQuestionVisible = false,
                    showStartButton = false,
                    onStartClick = {},
                    onOptionClick = {},
                    contentPadding = PaddingValues(top = statusBarTopPadding)
                )
            }

            StoryModeQuizViewModel.StoryModeQuizUiState.Loading -> {
                QuizContent(
                    questionText = null,
                    optionItems = emptyList(),
                    isQuestionVisible = false,
                    showStartButton = false,
                    onStartClick = {},
                    onOptionClick = {},
                    contentPadding = PaddingValues(top = statusBarTopPadding)
                )
            }

            is StoryModeQuizViewModel.StoryModeQuizUiState.Loaded -> {
                val currentQuestion = uiState.currentQuestion
                QuizContent(
                    questionText = currentQuestion?.questionText,
                    optionItems = optionItems,
                    isQuestionVisible = uiState.isQuestionVisible,
                    showStartButton = !uiState.isQuestionVisible,
                    onStartClick = {
                        viewModel.onStartClicked()
                    },
                    onOptionClick = { optionKey ->
                        if (uiState.isQuestionVisible && !uiState.isCompleted) {
                            currentQuestion?.let { question ->
                                val answerResult = quizContentViewModel.onOptionClicked(
                                    question = question,
                                    optionKey = optionKey
                                )
                                if (answerResult != null) {
                                    viewModel.onQuizContentAnswered(answerResult)
                                }
                            }
                        }
                    },
                    contentPadding = PaddingValues(top = statusBarTopPadding)
                )
            }
        }
    }
}
