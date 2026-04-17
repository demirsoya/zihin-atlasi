package com.epsilon.apps.bilgi.yarismasi.quiz.ui.scene.quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsIgnoringVisibility
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.res.colorResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.epsilon.apps.bilgi.yarismasi.quiz.R
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.epsiloncomponents.EpsilonButton
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.epsiloncomponents.EpsilonDialog
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.epsiloncomponents.EpsilonDialogContainer
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.epsiloncomponents.EpsilonText
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.helpers.nonScaledDp
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.helpers.nonScaledSp
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.scene.quiz.content.QuizContent
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.scene.quiz.content.QuizContentViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun QuizScene(
    viewModel: StoryModeQuizViewModel,
    quizContentViewModel: QuizContentViewModel,
    edgeToEdgePadding: PaddingValues,
    onNavigateHome: () -> Unit
) {
    val uiState = viewModel.quizUiState.collectAsStateWithLifecycle().value
    val optionItems = quizContentViewModel.optionItems.collectAsStateWithLifecycle().value
    val activeQuestion = (uiState as? StoryModeQuizViewModel.StoryModeQuizUiState.Loaded)?.currentQuestion
    val shouldNavigateHome = viewModel.navigateToHome.collectAsStateWithLifecycle().value
    val statusBarTopPadding =
        WindowInsets.statusBarsIgnoringVisibility.asPaddingValues().calculateTopPadding()

    LaunchedEffect(viewModel) {
        viewModel.loadQuizIfNeeded()
    }

    LaunchedEffect(activeQuestion?.id) {
        quizContentViewModel.bindQuestion(activeQuestion)
    }

    LaunchedEffect(shouldNavigateHome) {
        if (!shouldNavigateHome) return@LaunchedEffect
        onNavigateHome()
        viewModel.onNavigateToHomeHandled()
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

                EpsilonDialog(
                    showDialog = uiState.showLevelCompletedPopup,
                    onDismissRequest = {
                        viewModel.onLevelCompletedDismissRequested()
                    },
                    canBeDismissed = true,
                    size = 1f
                ) {
                    EpsilonDialogContainer(title = "Seviye Tamamlandı") {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.nonScaledDp),
                            verticalArrangement = Arrangement.spacedBy(10.nonScaledDp)
                        ) {
                            EpsilonText(
                                text = "Sonraki seviyeye hazırsın.",
                                textColor = R.color.app_main_text_color,
                                size = 14.nonScaledSp
                            )
                            EpsilonButton(
                                text = "Ana Sayfaya Dön",
                                modifier = Modifier.fillMaxWidth(),
                                textSize = 14.nonScaledSp,
                                backgroundColor = R.color.app_button_color,
                                fontWeight = FontWeight.Bold,
                                contentPadding = PaddingValues(vertical = 8.nonScaledDp)
                            ) {
                                viewModel.onLevelCompletedPrimaryAction()
                            }
                        }
                    }
                }
            }
        }
    }
}
