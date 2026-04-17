package com.epsilon.apps.bilgi.yarismasi.quiz.ui.scene.quiz.content

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
    answerOptions: List<String>,
    isQuestionVisible: Boolean,
    showStartButton: Boolean,
    onStartClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val options = remember(answerOptions) {
        answerOptions.take(5).let { current ->
            if (current.size == 5) current else current + List(5 - current.size) { "" }
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

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 12.nonScaledDp, vertical = 12.nonScaledDp),
        verticalArrangement = Arrangement.spacedBy(12.nonScaledDp)
    ) {
        EpsilonCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.nonScaledDp, vertical = 18.nonScaledDp)
            ) {
                EpsilonText(
                    modifier = Modifier.fillMaxWidth(),
                    text = if (isQuestionVisible) questionText.orEmpty() else "",
                    size = 18.nonScaledSp,
                    textColor = R.color.app_black,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Start
                )
            }
        }

        options.forEachIndexed { index, option ->
            val optionLabel = ('A' + index).toString()
            AnimatedVisibility(
                visible = visibilityStates[index].value,
                enter =
                    fadeIn(animationSpec = tween(durationMillis = 240, delayMillis = 30)) +
                        scaleIn(animationSpec = tween(durationMillis = 260), initialScale = 0.96f) +
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
                            .padding(horizontal = 12.nonScaledDp, vertical = 14.nonScaledDp)
                    ) {
                        EpsilonText(
                            text = if (option.isBlank()) optionLabel else "$optionLabel. $option",
                            modifier = Modifier.fillMaxWidth(),
                            size = 16.nonScaledSp,
                            textColor = R.color.app_black,
                            textAlign = TextAlign.Start
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        AnimatedVisibility(visible = showStartButton) {
            EpsilonButton(
                text = "Başla",
                modifier = Modifier.fillMaxWidth(),
                textSize = 16.nonScaledSp,
                backgroundColor = R.color.app_one,
                contentPadding = PaddingValues(vertical = 10.nonScaledDp)
            ) {
                onStartClick()
            }
        }
    }
}