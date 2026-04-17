package com.epsilon.apps.bilgi.yarismasi.quiz.ui.scene.quiz.content

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.epsilon.apps.bilgi.yarismasi.quiz.R
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.epsiloncomponents.EpsilonButton
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.epsiloncomponents.EpsilonCard
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.epsiloncomponents.EpsilonText
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.helpers.nonScaledDp
import com.epsilon.apps.bilgi.yarismasi.quiz.ui.helpers.nonScaledSp
import kotlinx.coroutines.delay

@Composable
fun QuizContent(
    questionText: String?,
    modifier: Modifier = Modifier,
    answerOptions: List<String>,
    isQuestionVisible: Boolean,
    showStartButton: Boolean,
    onStartClick: () -> Unit,
    contentPadding: PaddingValues = PaddingValues(all = 0.nonScaledDp),
) {
    val options = remember(answerOptions) {
        if (answerOptions.isEmpty()) {
            emptyList()
        } else {
            answerOptions.take(5).let { current ->
                if (current.size == 5) current else current + List(5 - current.size) { "" }
            }
        }
    }

    val visibilityStates = remember(options) {
        List(options.size) { mutableStateOf(false) }
    }

    LaunchedEffect(options) {
        visibilityStates.forEach { it.value = false }
        visibilityStates.forEachIndexed { index, state ->
            delay(index * 90L)
            state.value = true
        }
    }

    var animatedQuestionText by remember { mutableStateOf("") }
    LaunchedEffect(isQuestionVisible, questionText) {
        if (!isQuestionVisible) {
            animatedQuestionText = ""
            return@LaunchedEffect
        }

        val fullText = questionText.orEmpty()
        if (fullText.isEmpty()) {
            animatedQuestionText = ""
            return@LaunchedEffect
        }

        animatedQuestionText = ""
        fullText.forEach { character ->
            animatedQuestionText += character
            delay(16L)
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(contentPadding)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.nonScaledDp),
            contentPadding = PaddingValues(top = 8.nonScaledDp, bottom = 84.nonScaledDp),
            verticalArrangement = Arrangement.spacedBy(8.nonScaledDp)
        ) {
            item {
                EpsilonCard(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.nonScaledDp, vertical = 12.nonScaledDp),
                        contentAlignment = Alignment.Center
                    ) {
                        val visibleText = if (isQuestionVisible) animatedQuestionText else "...?"
                        EpsilonText(
                            modifier = Modifier.fillMaxWidth(),
                            text = visibleText,
                            size = 14.nonScaledSp,
                            lineHeight = 16.nonScaledSp,
                            textColor = R.color.app_main_text_color,
                            fontWeight = FontWeight.Light,
                            textAlign = if (isQuestionVisible) TextAlign.Start else TextAlign.Center
                        )
                    }
                }
            }

            itemsIndexed(options) { index, option ->
                val optionLabel = ('A' + index).toString()
                AnimatedVisibility(
                    visible = visibilityStates[index].value,
                    enter =
                        fadeIn(animationSpec = tween(durationMillis = 240, delayMillis = 30)) +
                                scaleIn(
                                    animationSpec = tween(durationMillis = 260),
                                    initialScale = 0.96f
                                ) +
                                slideInVertically(
                                    animationSpec = tween(durationMillis = 280),
                                    initialOffsetY = { it / 3 }
                                )
                ) {
                    EpsilonCard(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.nonScaledDp, vertical = 12.nonScaledDp)
                        ) {
                            EpsilonText(
                                text = if (option.isBlank()) optionLabel else "$optionLabel. $option",
                                modifier = Modifier.fillMaxWidth(),
                                size = 16.nonScaledSp,
                                textColor = R.color.app_main_text_color,
                                textAlign = TextAlign.Start
                            )
                        }
                    }
                }
            }
        }

        AnimatedVisibility(
            visible = showStartButton,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 8.nonScaledDp, vertical = 12.nonScaledDp)
        ) {
            EpsilonButton(
                text = "Soruyu Gör",
                modifier = Modifier.fillMaxWidth(),
                textSize = 16.nonScaledSp,
                backgroundColor = R.color.app_button_color,
                contentPadding = PaddingValues(vertical = 10.nonScaledDp),
                fontWeight = FontWeight.Bold
            ) {
                onStartClick()
            }
        }
    }
}