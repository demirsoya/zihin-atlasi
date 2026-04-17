package com.epsilon.apps.bilgi.yarismasi.quiz.ui.scene.quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.epsilon.apps.bilgi.yarismasi.quiz.R
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.scene.quiz.content.QuizContent

@Composable
fun QuizScene(
    viewModel: QuizViewModel,
    edgeToEdgePadding: PaddingValues
) {
    val uiState = viewModel.quizUiState.collectAsStateWithLifecycle().value

    Box(
        modifier = Modifier
            .padding(edgeToEdgePadding)
            .fillMaxSize()
            .background(color = colorResource(id = R.color.app_white))
    ) {
        when (uiState) {
            QuizViewModel.QuizUiState.Error -> {
                QuizContent(
                    questionText = null,
                    answerOptions = emptyList(),
                    isQuestionVisible = false,
                    showStartButton = false,
                    onStartClick = {}
                )
            }

            QuizViewModel.QuizUiState.Loading -> {
                QuizContent(
                    questionText = null,
                    answerOptions = emptyList(),
                    isQuestionVisible = false,
                    showStartButton = false,
                    onStartClick = {}
                )
            }

            is QuizViewModel.QuizUiState.Loaded -> {
                val currentQuestion = uiState.currentQuestion
                QuizContent(
                    questionText = currentQuestion?.questionText,
                    answerOptions = listOf(
                        currentQuestion?.optionA.orEmpty(),
                        currentQuestion?.optionB.orEmpty(),
                        currentQuestion?.optionC.orEmpty(),
                        currentQuestion?.optionD.orEmpty(),
                        currentQuestion?.optionE.orEmpty()
                    ),
                    isQuestionVisible = uiState.isQuestionVisible,
                    showStartButton = !uiState.isQuestionVisible,
                    onStartClick = {
                        viewModel.onStartClicked()
                    }
                )
            }
        }
    }
}
